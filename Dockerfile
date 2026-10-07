FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# mvnw is not committed as executable, so run it through sh
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN sh mvnw -B -ntp dependency:go-offline

COPY src src
RUN sh mvnw -B -ntp package -DskipTests


FROM eclipse-temurin:25-jre
WORKDIR /app

RUN useradd --system --uid 1001 spring
COPY --from=build /app/target/*.jar app.jar
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

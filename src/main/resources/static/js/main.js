function stopVideoButton() {
    const video = document.getElementById("headerVideo");
    const button = document.getElementById("myBtn");

    if (video.paused) {
        video.play();
        button.textContent = "stop";
    } else {
        video.pause();
        button.textContent = "play";
    }
}
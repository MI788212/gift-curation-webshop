INSERT INTO product (name, price)
SELECT 'T-Shirt', 19.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'T-Shirt');

INSERT INTO product (name, price)
SELECT 'Jeans', 49.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Jeans');

INSERT INTO product (name, price)
SELECT 'Sneakers', 89.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Sneakers');

INSERT INTO product (name, price)
SELECT 'Hat', 14.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Hat');
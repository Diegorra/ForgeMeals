-- insert admin (username a, password aa)
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (1, TRUE, 'ADMIN,USER', 'a',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (2, TRUE, 'USER', 'b',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
-- start id numbering from a value that is larger than any assigned above
ALTER SEQUENCE "PUBLIC"."GEN" RESTART WITH 1024;

INSERT INTO "PUBLIC"."RECIPE" VALUES
(1, 50, NULL, 'Receta pizza', 'Pizza', 3.00, 1),
(2, 90, NULL, 'Receta hamburguesa', 'Hamburguesa', 2.50, 1),
(3, 75, NULL, 'Receta Pasta Carbonara', 'Pasta Carbonara', 7.99, 2),
(4, 20, NULL, U&'Receta Pasta Bolo\00f1esa', U&'Pasta Bolo\00f1esa', 7.99, 2),
(5, 40, NULL, 'Receta Perrito Caliente', 'Perrito Caliente', 1.99, 2);

INSERT INTO "PUBLIC"."INGREDIENT" VALUES
(1, NULL, NULL, 'Tomate', 3.99, 500, 'ml'),
(2, NULL, NULL, 'Queso Mozarella Rayado', 2.50, 100, 'g'),
(3, NULL, NULL, 'Harina', 1.99, 500, 'g'),
(4, NULL, NULL, 'Maiz', 2.33, 150, 'g'),
(5, NULL, NULL, 'Melon', 3.74, 1, 'Kg'),
(6, NULL, NULL, 'Caviar', 99.99 , 100, 'g'),
(7, NULL, NULL, 'Arroz', 1.90, 1, 'Kg');

INSERT INTO "PUBLIC"."COMMENT" VALUES
(1, 80, 'Me gusta mucho', 1, 1),
(2, 0, 'No me gusta nada', 2, 1);
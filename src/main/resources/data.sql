-- INSERTAR USUARIOS
INSERT INTO Usuario (username, password) VALUES ('rafita', '1234');
INSERT INTO Usuario (username, password) VALUES ('serafin', '1234');
INSERT INTO Usuario (username, password) VALUES ('cristian', '1234');
INSERT INTO Usuario (username, password) VALUES ('gustavo', '1234');

-- INSERTAR INVENTARIOS
INSERT INTO Inventario (usuario_id) VALUES (1);
INSERT INTO Inventario (usuario_id) VALUES (2);
INSERT INTO Inventario (usuario_id) VALUES (3);
INSERT INTO Inventario (usuario_id) VALUES (4);

-- INSERTAR PRODUCTOS
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Leche', '2021-12-12', false, true, 0, 1);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Huevos', '2021-12-12', false, true, 0, 2);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Pan', '2021-12-12', false, true, 0, 3);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Carne', '2021-12-12', false, true, 0, 4);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Pescado', '2021-12-12', false, true, 0, 1);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Fruta', '2021-12-12', false, true, 0, 2);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Verdura', '2021-12-12', false, true, 0, 3);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Queso', '2021-12-12', false, true, 0, 4);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Yogur', '2021-12-12', false, true, 0, 1);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Mantequilla', '2021-12-12', false, true, 0, 2);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Aceite', '2021-12-12', false, true, 0, 3);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Azucar', '2021-12-12', false, true, 0, 4);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Sal', '2021-12-12', false, true, 0, 1);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Pimienta', '2021-12-12', false, true, 0, 2);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Canela', '2021-12-12', false, true, 0, 3);
INSERT INTO Alimento (nombre, fecha_caducidad, abierto, perecedero, numero_usos, inventario_id) VALUES ('Vino', '2021-12-12', false, true, 0, 4);

-- INSERTAR UBICACIONES
INSERT INTO Ubicacion (descripcion, capacidad, nombre_ubicacion) VALUES ('CONGELADOR 1', 100, 'CONGELADOR');
INSERT INTO Ubicacion (descripcion, capacidad, nombre_ubicacion) VALUES ('NEVERA 2', 100, 'NEVERA');
INSERT INTO Ubicacion (descripcion, capacidad, nombre_ubicacion) VALUES ('ALACENA 3', 100, 'ALACENA');

-- INSERTAR EXISTENCIAS
-- Leche en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 1, 1);
-- Huevos en Nevera
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 2, 2);
-- Pan en Alacena
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 3, 3);
-- Carne en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 4, 1);
-- Pescado en Nevera
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 5, 2);
-- Fruta en Alacena
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 6, 3);
-- Verdura en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 7, 1);
-- Queso en Nevera
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 8, 2);
-- Yogur en Alacena
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 9, 3);
-- Mantequilla en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 10, 1);
-- Aceite en Nevera
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 11, 2);
-- Azucar en Alacena
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 12, 3);
-- Sal en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 13, 1);
-- Pimienta en Nevera
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 14, 2);
-- Canela en Alacena
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 15, 3);
-- Vino en Congelador
INSERT INTO Existencia (cantidad_alimento, fecha_entrada, alimento_id, ubicacion_id) VALUES (10, '2021-12-12', 16, 1);




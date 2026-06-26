-- ==========================================
-- SCRIPT DE BASE DE DATOS: N-STOCK
-- Incluye: Tablas, P.A. y Datos de Prueba
-- ==========================================

CREATE DATABASE IF NOT EXISTS nstock_db;
USE nstock_db;

-- ------------------------------------------
-- 1. CREACIÓN DE TABLAS
-- ------------------------------------------

CREATE TABLE IF NOT EXISTS sucursales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'CAJERO') NOT NULL,
    sucursal_id INT,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);

CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_barras VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_critico INT NOT NULL DEFAULT 5,
    categoria_id INT,
    sucursal_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);

CREATE TABLE IF NOT EXISTS ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    sucursal_id INT,
    total INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);

CREATE TABLE IF NOT EXISTS detalles_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    precio_unitario INT NOT NULL,
    subtotal INT NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

CREATE TABLE IF NOT EXISTS movimientos_inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    sucursal_id INT,
    tipo ENUM('INGRESO', 'MERMA', 'VENTA', 'AJUSTE') NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(255),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);

-- ------------------------------------------
-- 2. PROCEDIMIENTO ALMACENADO (P.A.)
-- ------------------------------------------
-- Requisito de la rúbrica. Este P.A. registra una merma de forma segura.

DELIMITER //
CREATE PROCEDURE PA_RegistrarMerma(
    IN p_producto_id INT, 
    IN p_sucursal_id INT, 
    IN p_cantidad INT, 
    IN p_motivo VARCHAR(255)
)
BEGIN
    DECLARE v_stock_actual INT;
    
    -- Obtener stock actual
    SELECT stock INTO v_stock_actual FROM productos WHERE id = p_producto_id;
    
    -- Verificar si hay stock suficiente para mermar
    IF v_stock_actual >= p_cantidad THEN
        -- Descontar stock
        UPDATE productos SET stock = stock - p_cantidad WHERE id = p_producto_id;
        
        -- Registrar el movimiento
        INSERT INTO movimientos_inventario (producto_id, sucursal_id, tipo, cantidad, motivo)
        VALUES (p_producto_id, p_sucursal_id, 'MERMA', p_cantidad, p_motivo);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stock insuficiente para registrar merma';
    END IF;
END //
DELIMITER ;

-- ------------------------------------------
-- 3. DATOS DE PRUEBA (Requeridos en Informe)
-- ------------------------------------------

-- Sucursales
INSERT INTO sucursales (nombre, direccion) VALUES 
('Casa Matriz San Bernardo', 'Arturo Prat 123'),
('Sucursal Centro', 'Ahumada 456');

-- Usuarios (admin/1234 y usuario/1234)
INSERT INTO usuarios (username, password, rol, sucursal_id) VALUES 
('admin', '1234', 'ADMIN', 1),
('usuario', '1234', 'CAJERO', 1);

-- Categorías
INSERT INTO categorias (nombre) VALUES 
('Abarrotes'), ('Bebidas'), ('Snacks');

-- Productos (Incluyendo casos de stock normal y stock crítico <= 5)
INSERT INTO productos (codigo_barras, nombre, precio, stock, stock_critico, categoria_id, sucursal_id) VALUES 
('1001', 'Ramitas Saladas 100g', 800, 50, 5, 3, 1),
('1002', 'Ramitas Queso 100g', 950, 45, 5, 3, 1),
('2001', 'Bebida Cola 1.5L', 1600, 3, 5, 2, 1),  -- Producto en stock crítico para pruebas
('3001', 'Galletas Vainilla', 700, 60, 5, 3, 1);

-- Movimiento de inventario inicial de prueba
INSERT INTO movimientos_inventario (producto_id, sucursal_id, tipo, cantidad, motivo) VALUES 
(1, 1, 'INGRESO', 50, 'Inventario inicial sistema'),
(2, 1, 'INGRESO', 45, 'Inventario inicial sistema');

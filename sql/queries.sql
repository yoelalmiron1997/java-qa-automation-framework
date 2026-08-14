-- =============================================================================
-- GUÍA DE EJERCICIOS SQL / PL-SQL PARA PRUEBAS TÉCNICAS QA AUTOMATION SSR
-- =============================================================================
-- Tablas involucradas:
-- users (id, name, email, status, created_at)
-- orders (id, user_id, product_id, amount, status, order_date)
-- products (id, name, price, stock)
-- =============================================================================

-- -----------------------------------------------------------------------------
-- EJERCICIO 1: Selección básica con filtro de email (Testing de registro/login)
-- Enunciado: Buscar todos los datos del usuario registrado con email 'test@example.com'.
-- -----------------------------------------------------------------------------
SELECT * 
FROM users 
WHERE email = 'test@example.com';

-- Justificación QA: Se utiliza para verificar en base de datos que el registro desde la UI o API creó el usuario correctamente.


-- -----------------------------------------------------------------------------
-- EJERCICIO 2: Filtros compuestos (AND / OR) y estados
-- Enunciado: Obtener usuarios con status 'ACTIVE' que se hayan creado a partir de '2026-01-01'.
-- -----------------------------------------------------------------------------
SELECT id, name, email, status 
FROM users 
WHERE status = 'ACTIVE' 
  AND created_at >= '2026-01-01 00:00:00';


-- -----------------------------------------------------------------------------
-- EJERCICIO 3: Conteo y Agrupamiento (GROUP BY + COUNT)
-- Enunciado: Contar cuántas órdenes tiene cada usuario agrupadas por su status ('COMPLETED', 'PENDING', 'CANCELLED').
-- -----------------------------------------------------------------------------
SELECT status, COUNT(*) AS total_orders
FROM orders
GROUP BY status;

-- Justificación QA: Permite validar métricas del backend o reportes que se muestran en el dashboard de la app.


-- -----------------------------------------------------------------------------
-- EJERCICIO 4: INNER JOIN (Relación entre Usuarios y Órdenes)
-- Enunciado: Obtener el nombre del usuario, el ID de la orden y el monto para todas las órdenes en estado 'COMPLETED'.
-- -----------------------------------------------------------------------------
SELECT u.name AS user_name, o.id AS order_id, o.amount
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE o.status = 'COMPLETED';


-- -----------------------------------------------------------------------------
-- EJERCICIO 5: LEFT JOIN (Detectar usuarios sin órdenes / Datos huérfanos)
-- Enunciado: Listar TODOS los usuarios y sus órdenes asociadas. Incluir a los usuarios que NO han realizado ninguna orden.
-- -----------------------------------------------------------------------------
SELECT u.id, u.name, o.id AS order_id
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE o.id IS NULL; -- Filtra únicamente los usuarios sin compras (útil para pruebas de retención/descuentos).


-- -----------------------------------------------------------------------------
-- EJERCICIO 6: JOIN Triple (Users + Orders + Products)
-- Enunciado: Obtener el nombre del usuario, el nombre del producto comprado y la fecha de la orden para las compras aprobadas.
-- -----------------------------------------------------------------------------
SELECT u.name AS user_name, p.name AS product_name, o.order_date
FROM orders o
INNER JOIN users u ON o.user_id = u.id
INNER JOIN products p ON o.product_id = p.id
WHERE o.status = 'COMPLETED'
ORDER BY o.order_date DESC;


-- -----------------------------------------------------------------------------
-- EJERCICIO 7: Filtrado de grupos con HAVING (HAVING vs WHERE)
-- Enunciado: Obtener el user_id y la suma total gastada solo de aquellos usuarios que hayan gastado más de 500 USD en total.
-- -----------------------------------------------------------------------------
SELECT user_id, SUM(amount) AS total_spent
FROM orders
WHERE status = 'COMPLETED'
GROUP BY user_id
HAVING SUM(amount) > 500;

-- Justificación QA: Pregunta típica de entrevista: WHERE filtra filas ANTES del agrupamiento; HAVING filtra grupos DESPUÉS del GROUP BY.


-- -----------------------------------------------------------------------------
-- EJERCICIO 8: Subconsultas (Subqueries)
-- Enunciado: Encontrar todos los productos cuyo precio sea mayor al precio promedio de todos los productos en catálogo.
-- -----------------------------------------------------------------------------
SELECT id, name, price
FROM products
WHERE price > (SELECT AVG(price) FROM products);


-- -----------------------------------------------------------------------------
-- EJERCICIO 9: DML para testing backend (INSERT / UPDATE / DELETE en ambiente QA)
-- Enunciado: Simular la actualización de stock tras una compra y limpiar datos de prueba creados durante los tests.
-- -----------------------------------------------------------------------------
-- Actualizar stock
UPDATE products 
SET stock = stock - 1 
WHERE id = 101 AND stock > 0;

-- Borrar datos de prueba (Teardown SQL)
DELETE FROM users 
WHERE email LIKE '%test_automation%';


-- -----------------------------------------------------------------------------
-- EJERCICIO 10: PL/SQL Conceptos para Entrevistas QA (Oracle / PostgreSQL)
-- -----------------------------------------------------------------------------
/*
🎯 CONCEPTOS PL/SQL PARA ENTREVISTA SSR:

1. SQL vs PL/SQL:
   - SQL es un lenguaje declarativo para consultar/manipular datos (SELECT, INSERT).
   - PL/SQL (Procedural Language for SQL) es una extensión procedimental de Oracle que agrega variables, estructuras de control (IF/LOOP), manejo de excepciones y bloques lógicos en el servidor de base de datos.

2. Stored Procedure (Procedimiento Almacenado):
   - Bloque de código PL/SQL compilado en la BD que ejecuta una serie de acciones (ej: procesar un pago, actualizar varias tablas).
   - Puede modificar datos y NO requiere retornar un valor obligatoriamente.
   - ¿Cómo se prueba desde QA?: Se invoca con CALL/EXECUTE y luego se verifica el estado de las tablas impactadas.

3. Function (Función):
   - Similar a un Procedure, pero SIEMPRE retorna un único valor (ej: calcular impuesto, validar email).
   - Se puede usar directamente en consultas SELECT.

4. Trigger (Disparador):
   - Bloque de código que se ejecuta AUTOMÁTICAMENTE antes o después de un evento DML (INSERT, UPDATE, DELETE).
   - Ejemplo de uso en QA: Auditar cambios de precio en productos o auditar logins fallidos.
   - Riesgo QA: Un trigger defectuoso puede hacer fallar un POST /users en la API devolviendo un error 500 no obvio.

5. Sequence (Secuencia):
   - Objeto que genera valores numéricos secuenciales únicos, comúnmente usado para autoincrementar la Primary Key (ID) de los usuarios/órdenes.
*/

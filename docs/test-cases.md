# 📋 Casos de Prueba (Test Cases) - SauceDemo

## Módulo: Autenticación (Login)

### 📊 Técnicas de Diseño de Pruebas Aplicadas
- **Partición de Equivalencia (EP)**: Clasificación de entradas en grupos válidos (usuarios existentes activos) e inválidos (usuarios bloqueados, inexistentes, formatos vacíos).
- **Análisis de Valores Límite (BVA)**: Evaluación de comportamiento ante campos vacíos, longitud de caracteres y espacios en blanco.
- **Tablas de Decisión**: Matriz de combinaciones de Username (V/I/Vacio) x Password (V/I/Vacio).
- **Testing Basado en Riesgo**: Priorización de autenticación y manejo de mensajes de error sin revelar información sensible de seguridad.

---

### 🧪 Suite de Casos de Prueba

| ID | Título / Escenario | Tipo | Técnica Aplicada | Pasos | Resultado Esperado | Justificación QA (¿Por qué existe?) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC-LOG-001** | Login exitoso con credenciales válidas | Happy Path | Partición de Equivalencia | 1. Ir a `/`<br>2. Ingresar `standard_user`<br>3. Ingresar `secret_sauce`<br>4. Click en Login | Redirección a `/inventory.html`, visualización del título "Products" y carrito de compras. | Valida el flujo principal (Happy Path) indispensable para la operación del negocio. |
| **TC-LOG-002** | Login fallido con contraseña incorrecta | Negativo | Tabla de Decisión | 1. Ir a `/`<br>2. Ingresar `standard_user`<br>3. Ingresar `wrong_password`<br>4. Click en Login | Permanecer en `/`. Mostrar mensaje: *"Epic sadface: Username and password do not match any user in this service"*. | Verifica que la autenticación rechace claves inválidas y no otorgue acceso no autorizado. |
| **TC-LOG-003** | Login fallido con usuario inexistente | Negativo | Partición de Equivalencia | 1. Ir a `/`<br>2. Ingresar `invalid_user`<br>3. Ingresar `secret_sauce`<br>4. Click en Login | Permanecer en `/`. Mostrar mensaje de credenciales no coincidentes. | Previene el acceso con usuarios que no existen en el sistema. |
| **TC-LOG-004** | Intentar login con campo Username vacío | Negativo | Valores Límite (Longitud 0) | 1. Ir a `/`<br>2. Dejar Username vacío<br>3. Ingresar `secret_sauce`<br>4. Click en Login | Permanecer en `/`. Mostrar mensaje: *"Epic sadface: Username is required"*. | Valida el control de campos obligatorios en el formulario antes de procesar la autenticación. |
| **TC-LOG-005** | Intentar login con campo Password vacío | Negativo | Valores Límite (Longitud 0) | 1. Ir a `/`<br>2. Ingresar `standard_user`<br>3. Dejar Password vacío<br>4. Click en Login | Permanecer en `/`. Mostrar mensaje: *"Epic sadface: Password is required"*. | Evita el envío de autenticación incompleta y valida el feedback claro al usuario. |
| **TC-LOG-006** | Intentar login con ambos campos vacíos | Negativo | Tabla de Decisión | 1. Ir a `/`<br>2. Dejar ambos campos vacíos<br>3. Click en Login | Permanecer en `/`. Mostrar mensaje: *"Epic sadface: Username is required"*. | Evalúa el comportamiento inicial al presionar submit sin interacción previa. |
| **TC-LOG-007** | Intento de Login con usuario bloqueado (`locked_out_user`) | Negativo | Testing Basado en Riesgo / Estado de Entidad | 1. Ir a `/`<br>2. Ingresar `locked_out_user`<br>3. Ingresar `secret_sauce`<br>4. Click en Login | Permanecer en `/`. Mostrar mensaje: *"Epic sadface: Sorry, this user has been locked out."*. | Valida reglas de negocio sobre usuarios inhabilitados o suspendidos por seguridad. |
| **TC-LOG-008** | Cierre de sesión (Logout) exitoso | Positivo | Flujo de Estado | 1. Completar TC-LOG-001<br>2. Abrir menú hamburguesa<br>3. Click en "Logout" | Redirección a `/`. Campos de login visibles. Cookie/sesión eliminada. | Garantiza la destrucción de la sesión al salir de la aplicación. |
| **TC-LOG-009** | Acceso directo a URL protegida sin autenticación | Seguridad / Negativo | Risk-Based Testing | 1. Navegar directamente a `https://www.saucedemo.com/inventory.html` sin estar logueado | Redirección forzada a `/`. Mostrar error: *"Epic sadface: You can only access '/inventory.html' when you are logged in."*. | Valida el Middleware/Guard de seguridad que impide saltarse el login. |
| **TC-LOG-010** | Manejo de espacios en blanco en Username | Negativo / BVA | Valores Límite (Espacios) | 1. Ir a `/`<br>2. Ingresar `  standard_user  `<br>3. Ingresar `secret_sauce`<br>4. Click en Login | Evaluar si la app aplica trim o rechaza credenciales con espacios no válidos. | Previene errores causados por copiar y pegar credenciales con espacios accidentales. |

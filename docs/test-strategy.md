# 🛡️ Test Strategy Document - QA Automation SSR

## 🎯 1. Objetivo
Definir la estrategia integral de pruebas (Web UI, API REST, Backend e Integración con Base de Datos) para garantizar la calidad, seguridad y mantenibilidad de la aplicación.

---

## 🏗️ 2. Pirámide de Automatización de Pruebas

```text
       /\
      /  \     [ UI / Web E2E ] -> Pocos, críticos, enfocados en el usuario (Selenium + POM)
     /----\
    /      \   [ API / Service ] -> Rápidos, alta cobertura, CRUD y Status Codes (REST Assured)
   /--------\
  /          \ [ Database / Backend ] -> Validaciones de persistencia e integridad de datos (SQL)
 /------------\
```

* **Capa Web (UI)**: Cobertura de flujos end-to-end críticos (Login, Checkout). Automatizado con **Selenium WebDriver + Java + JUnit 5 + POM**.
* **Capa API (Servicios)**: Cobertura de lógica de negocio, validación de schemas, códigos de respuesta HTTP y casos bordes. Automatizado con **REST Assured** y explorado manualmente en **Postman**.
* **Capa Base de Datos (Backend)**: Verificación directa de consistencia e integridad relacional usando **SQL (PostgreSQL/Oracle)**.

---

## 🔄 3. Flujo End-to-End Multicapa (WEB ➔ API ➔ BACKEND ➔ DB)

En una arquitectura moderna, un flujo completo se valida en cada capa:

```text
[ 1. FRONTEND / WEB ]
Usuario ingresa usuario/password y hace click en Login.
        │
        ▼ (HTTP POST /api/login)
[ 2. API GATEWAY / SERVICE ]
Valida la estructura del JSON, Content-Type y autenticación.
        │
        ▼
[ 3. BACKEND / BUSINESS LOGIC ]
Aplica reglas de negocio, hash de contraseña y verifica estado de cuenta (locked/active).
        │
        ▼ (SQL Query / Stored Procedure)
[ 4. DATABASE ]
Verifica la tabla 'users' y registra la sesión en la tabla 'audit_logs'.
```

### Ejemplo de Escenario de Debugging Multicapa (Pregunta de Entrevista)
> **Escenario**: `POST /api/users` devuelve `201 Created` en la API y la UI muestra "Usuario registrado con éxito", pero el usuario no puede iniciar sesión posteriormente.
>
> **Plan de Investigación del QA**:
> 1. **Verificación en DB**: Ejecutar `SELECT * FROM users WHERE email = '...'`. ¿El registro existe en la base de datos? ¿El campo `status` o `email_verified` se guardó como `PENDING` o `FALSE` por error?
> 2. **Logs del Backend**: Inspeccionar los logs del servicio backend (Kibana/CloudWatch) para identificar si hubo una excepción silenciosa o un Trigger de base de datos que falló al asignar el rol.
> 3. **API Response Payload**: Verificar si la respuesta `201 Created` retorna el ID de usuario real o un objeto incompleto.

---

## ⚠️ 4. Estrategia para Mitigar Flaky Tests (Tests Inestables)

Un **Flaky Test** es un test que falla o pasa de manera aleatoria sin cambios en el código.

### Principales causas y soluciones:
1. **Falta de Sincronización (Timing)**:
   - *Solución*: Reemplazar `Thread.sleep()` por **Explicit Waits** (`WebDriverWait` + `ExpectedConditions`).
2. **Dependencia entre Tests (State Pollution)**:
   - *Solución*: Cada test debe ser totalmente independiente. Utilizar métodos `@BeforeEach` para limpiar/reajustar el estado inicial.
3. **Elementos Dinámicos en el DOM**:
   - *Solución*: Utilizar locators estables como atributos de test (`[data-test='...']`) o IDs estáticos, evitando XPath absolutos.
4. **Ambientes inestables o lentitud de red**:
   - *Solución*: Configurar retries automáticos en el test runner y aislar las pruebas de API mockeando servicios de terceros inestables cuando sea necesario.

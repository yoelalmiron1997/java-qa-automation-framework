# 🎯 Guía de Repaso para Entrevista Técnica: QA Automation SSR

Este documento recopila las notas clave, patrones, conceptos teóricos y preguntas frecuentes para repasar antes de la entrevista técnica.

---

## 📌 1. Fundamentos Web y Arquitectura
- **Frontend vs Backend**: 
  - *Frontend*: Interfaz de usuario (HTML, CSS, JavaScript) ejecutada en el navegador.
  - *Backend*: Lógica de negocio, APIs, base de datos y servidores.
- **Flujo de una petición Web**:
  1. Usuario ingresa URL / Interactúa en el navegador (DOM).
  2. El navegador envía una petición HTTP al servidor (Backend/API).
  3. El servidor procesa, consulta BD y responde con un código de estado (HTTP Status Code) y payload (JSON/HTML).
  4. El navegador renderiza la respuesta en el DOM.

- **Diagnóstico Frontend vs Backend con DevTools (Pregunta Típica)**:
  - *Fallo de Frontend*: Al hacer click, la pestaña **Network** no registra ninguna petición HTTP saliente, o se observa un error de Javascript en la pestaña **Console** (ej: `Uncaught TypeError`).
  - *Fallo de Backend*: La pestaña **Network** muestra una petición HTTP saliente (ej: `POST /api/login`), pero el servidor responde con un código de error de la serie `5xx` (Internal Server Error) o `4xx` no esperado, o el payload de respuesta viene mal formado.
- **Cookies y Estado de Sesión**:
  - SauceDemo utiliza `Cookie` (ej: `session-username=standard_user`) para mantener el estado de la sesión en el cliente.
  - En Selenium, inspeccionar las cookies nos permite simular inicios de sesión o validar que el logout borre la sesión correctamente (`driver.manage().getCookies()`).

---

## 📌 2. Diseño de Casos de Prueba (QA Theory)
- **Partición de Equivalencia (EP)**: Divide el dominio de entradas en clases válidas e inválidas. Probar 1 valor por clase asume el mismo comportamiento para todos.
- **Análisis de Valores Límite (BVA)**: Prueba los extremos (mínimo, máximo, justo por debajo, justo por encima, vacíos). Los bugs suelen habitar en los límites.
- **Tablas de Decisión**: Matriz para probar combinaciones complejas de condiciones de entrada y sus acciones/resultados esperados.
- **Testing Basado en Riesgo**: Prioriza automatizar los flujos críticos de negocio y vulnerabilidades de seguridad (ej: bypass de URLs protegidas).

---

## 📌 3. Selenium WebDriver & Locators
- **Arquitectura de Selenium 4**: `Código Test (Java)` ➔ `Selenium Manager / W3C WebDriver Protocol` ➔ `ChromeDriver` ➔ `Navegador Chrome (DOM)`.
- **Estrategias de Locators (Orden de preferencia)**:
  1. `By.id()`: Más rápido y único (si es estático).
  2. `By.cssSelector()`: Muy rápido, limpio y expresivo (`input[data-test='username']`).
  3. `By.xpath()`: Permite navegar hacia arriba en el DOM (parent `..`), buscar por texto (`contains(text(), '...')`), pero es ligeramente más lento.
- **Locators Frágiles vs Robustos**:
  - *Frágil (Absoluto)*: `/html/body/div[1]/div/div[2]/div[1]/div/form/div[1]/input` (se rompe si cambia la estructura del HTML).
  - *Robusto (Relativo / Atributo de negocio)*: `By.cssSelector("[data-test='username']")` o `By.id("user-name")`.

---

## 📌 4. API Testing & REST Assured
- **REST Assured (BDD)**: `given()` (setup headers/body) ➔ `when()` (método HTTP) ➔ `then()` (assert status code & body).
- **Códigos de Estado HTTP Esenciales**:
  - `200 OK`: Petición exitosa (GET/PUT).
  - `201 Created`: Recurso creado exitosamente (POST).
  - `204 No Content`: Petición procesada exitosamente sin cuerpo de respuesta (DELETE).
  - `400 Bad Request`: Payload o datos de entrada inválidos enviados por el cliente.
  - `401 Unauthorized`: Falta autenticación (token o credenciales no provistas).
  - `403 Forbidden`: Autenticado pero sin permisos para acceder al recurso.
  - `404 Not Found`: El endpoint o ID especificado no existe.
  - `500 Internal Server Error`: Excepción no controlada en el servidor backend.

---

## 📌 5. Uso de IA / Gemini Aplicado a QA Automation
- **Casos de Uso Profesionales**:
  1. Generar estructuras de datos o payloads JSON para pruebas de frontera.
  2. Asistir en la creación de expresiones regulares o locators XPath complejos.
  3. Diagnosticar stack traces o logs de error extensos.
- **Riesgos y Limitaciones (Crucial para Entrevistas)**:
  - *Alucinaciones*: Generación de métodos o selectores inexistentes.
  - *Falsa Cobertura*: Asertar únicamente status code 200 sin validar las propiedades internas del JSON.
  - *Seguridad*: Nunca enviar datos sensibles (tokens reales, passwords, PII) a la IA.

---

## 📌 6. Simulador de Preguntas Situacionales de Entrevista SSR

1. **"¿Qué automatizarías y qué dejarías manual?"**
   - *Automatizar*: Smoke tests, pruebas de regresión, flujos críticos de negocio (Happy Path), APIs estables, datos masivos.
   - *Manual*: Pruebas exploratorias, usabilidad (UX), funcionalidades nuevas en constante cambio, pruebas visuales no automatizables.

2. **"Un test pasa localmente pero falla en Jenkins/CI. ¿Cómo investigas?"**
   - Revisar resolución de pantalla en CI (usar `--window-size=1920,1080` o `--headless`).
   - Inspeccionar logs de ejecución y capturas de pantalla (*Screenshots*) al fallar.
   - Verificar si el ambiente de CI tiene mayor latencia (ajustar timeouts en `WebDriverWait`).

---

## 📌 7. Performance Testing & Apache JMeter
- **¿Qué es JMeter?**: Herramienta de pruebas de carga, rendimiento y volumen para APIs y servicios Web.
- **Caso de Uso como Generador de Entradas / Carga**:
  - *Generación de Tráfico Concurrente*: Simula múltiples hilos de usuarios (*Threads*) en paralelo.
  - *Parametrización masiva*: Uso de `CSV Data Set Config` y funciones (`__UUID()`, `__Random()`) para generar miles de entradas/requests únicos.
- **Diferencias Clave (Pregunta Frecuente en Entrevistas)**:
  - *Selenium*: 1 usuario navegando por la UI (Prueba Funcional Frontend).
  - *REST Assured*: 1 cliente consumiendo endpoints de la API (Prueba Funcional Backend).
  - *JMeter*: 1000+ usuarios simultáneos sobrecargando el sistema (Prueba de Rendimiento / Carga / Estrés).



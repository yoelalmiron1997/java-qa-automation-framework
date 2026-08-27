# Java QA Automation Framework

Framework de automatización de pruebas en Java para validar escenarios web y API con Selenium, REST Assured y JUnit 5.

## Descripción

Este repositorio contiene una suite de pruebas automatizadas orientada a QA, diseñada para cubrir:

- Login de usuarios en una aplicación web
- Validación de mensajes de error
- Flujos básicos de navegación
- Pruebas de API REST utilizando REST Assured
- Capturas de pantalla automáticas para evidencias de ejecución

La estructura del proyecto sigue el patrón Page Object Model (POM) para la capa web y buenas prácticas para pruebas automatizadas.

## Repositorio

- GitHub: https://github.com/yoelalmiron1997/java-qa-automation-framework.git

## Tecnologías utilizadas

- Java 17
- Maven
- Selenium WebDriver
- JUnit 5
- REST Assured
- ChromeDriver / Google Chrome

## Estructura del proyecto

```text
Prueba-Tecnica/
├── pom.xml
├── README.md
├── docs/
│   ├── interview-notes.md
│   ├── test-cases.md
│   └── test-strategy.md
├── postman/
│   ├── collections/
│   └── environments/
├── sql/
│   └── queries.sql
├── src/
│   └── test/
│       └── java/
│           ├── pages/
│           ├── tests/
│           └── utils/
├── target/
└── mvn.cmd
```

## Requisitos previos

Antes de ejecutar las pruebas asegúrate de tener instalado:

- Java 17 o superior
- Maven 3.9+
- Google Chrome instalado
- Variables de entorno configuradas para `java` y `mvn`

## Cómo ejecutar las pruebas

### 1. Clonar el repositorio

```bash
git clone https://github.com/yoelalmiron1997/java-qa-automation-framework.git
cd java-qa-automation-framework
```

### 2. Ejecutar todas las pruebas

```bash
mvn test
```

### 3. Ejecutar una clase específica

```bash
mvn -Dtest=tests.web.LoginTest test
```

```bash
mvn -Dtest=tests.api.UserApiTest test
```

### 4. Ejecutar un caso de prueba específico

```bash
mvn -Dtest=tests.web.LoginTest#testSuccessfulLogin test
```

### 5. Compilar solo los tests

```bash
mvn test-compile
```

## Casos de prueba disponibles

### Pruebas Web

Archivo: `src/test/java/tests/web/LoginTest.java`

- `testSuccessfulLogin` - Login exitoso con credenciales válidas
- `testLoginWithInvalidPassword` - Login con contraseña incorrecta
- `testEmptyPasswordLogin` - Login con contraseña vacía
- `testLockedOutUserLogin` - Login con usuario bloqueado

### Pruebas API

Archivo: `src/test/java/tests/api/UserApiTest.java`

- `testGetUsersListList` - Obtener lista de usuarios
- `testCreateUser` - Crear un usuario
- `testGetSingleUser` - Obtener un usuario por ID
- `testSingleUserNotFound` - Validar usuario inexistente
- `testUpdateUser` - Actualizar usuario
- `testDeleteUser` - Eliminar usuario

## Capturas de pantalla

El proyecto genera evidencia visual de cada prueba web mediante la clase:

- `src/test/java/utils/ScreenshotUtils.java`

Esto facilita revisar el estado visual de cada ejecución y conservar evidencia de fallas o validaciones.

## Nota importante

Las pruebas de API consumen servicios externos y pueden requerir internet o ajustes en los endpoints dependiendo del entorno. Si ocurre un error `401 Unauthorized` o un fallo de conectividad, verifica:

- Conexión a internet
- Disponibilidad del servicio
- Endpoint actual
- Headers o configuración necesarios

## GitHub y primer commit

Este es el flujo recomendado para dejar tu repositorio listo con el primer commit y subirlo a GitHub:

```bash
git init
git add .
git commit -m "Initial commit: Java QA automation project"
git branch -M main
git remote add origin https://github.com/yoelalmiron1997/java-qa-automation-framework.git
git push -u origin main
```

Si el repositorio ya existe y solo quieres conectarlo al remoto actual:

```bash
git branch -M main
git remote set-url origin https://github.com/yoelalmiron1997/java-qa-automation-framework.git
git push -u origin main
```

## .gitignore recomendado

```gitignore
target/
.idea/
.vscode/
*.log
*.iml
.DS_Store
```

## Autor

Yoel Almiron

## Licencia

Este proyecto es de uso educativo y demostrativo para fines de automatización de pruebas y aprendizaje profesional.


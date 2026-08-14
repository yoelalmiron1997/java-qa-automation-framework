# 🚀 QA Automation Interview — Java (Selenium + REST Assured)

Repositorio de pruebas automatizadas usado para entrevistas y prácticas de QA Automation.

## Descripción

Proyecto de ejemplo que contiene pruebas Web (Selenium) y API (REST Assured) organizadas con JUnit 5 y Page Object Model.

## Requisitos

- Java 21 (recomendado; el proyecto fue actualizado a Java 21)
- Maven 3.9+ (o usar `mvnw` si existiera)
- Navegador Chrome/Chromium para pruebas Web (o usar WebDriver Manager)

## Estructura relevante

- `pom.xml` — configuración Maven y dependencias
- `src/test/java/tests/web` — pruebas UI (LoginTest)
- `src/test/java/tests/api` — pruebas API (UserApiTest)
- `postman/` — colecciones y entornos para pruebas manuales

## Cómo ejecutar

1) Compilar y ejecutar todas las pruebas:

```powershell
mvn clean test
```

2) Ejecutar un test específico (ej. `LoginTest`):

```powershell
mvn -Dtest=LoginTest test
```

3) Ejecutar solo compilación de tests:

```powershell
mvn test-compile
```

## Notas importantes

- El proyecto fue actualizado para compilar con Java 21. Si tu entorno usa Java 17, cambia `JAVA_HOME` o instala JDK 21.
- Durante la ejecución en mi verificación, las pruebas de API (`UserApiTest`) fallaron por respuestas `401 Unauthorized` del servicio externo `https://reqres.in` — esto es un problema de entorno/autenticación o disponibilidad externa, no de la actualización de Java.

Si ves `401` en los tests API, verifica:

- Conectividad a `https://reqres.in` desde tu máquina.
- Si las pruebas requieren headers o tokens, configura variables de entorno o ajusta los tests.

## Subir el proyecto a GitHub

Comandos sugeridos (ejecutar en la raíz del proyecto):

```powershell
git init
git add .
git commit -m "Initial commit: project import and Java 21 upgrade"
git branch -M main
git remote add origin https://github.com/yoelalmiron1997/automation-tests-reqres.git
git push -u origin main
```

Si no tienes `git` instalado en Windows, puedes instalarlo con Chocolatey:

```powershell
choco install git -y
```

## .gitignore recomendado

Incluye archivos/dirs que no deben subirse:

```
target/
.idea/
.vscode/
*.log
*.iml
.DS_Store
/.mvn/wrapper/maven-wrapper.jar
```

¿Quieres que añada `.gitignore` automáticamente y haga el commit inicial por ti? (necesito `git` disponible para ejecutar comandos aquí). 

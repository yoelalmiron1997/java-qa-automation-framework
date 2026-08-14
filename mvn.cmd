@echo off
if "%JAVA_HOME%"=="" set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.8-hotspot"
"C:\Users\ymalmiron\maven\apache-maven-3.9.8\bin\mvn.cmd" %*

@echo off
cd /d "%~dp0"

echo ============================================
echo  ST Connect - Build completo
echo ============================================

set JAVA_HOME=C:\Program Files\Java\jdk-25.0.2
set JAVAC=%JAVA_HOME%\bin\javac
set JAR=%JAVA_HOME%\bin\jar

:: Paso 1: Compilar backend_java
echo.
echo [1/3] Compilando backend_java...
cd backend_java
if not exist "target\classes" mkdir "target\classes"
dir /s /b src\main\java\*.java > sources.txt
%JAVAC% --release 21 -d "target\classes" @sources.txt
if %errorlevel% neq 0 (
    echo ERROR en compilacion de backend_java
    del sources.txt 2>nul
    pause
    exit /b 1
)
del sources.txt 2>nul
%JAR% cf "target\backend_java.jar" -C "target\classes" .
copy /Y "target\backend_java.jar" "..\desktop_app\backend_java.jar" >nul
echo  OK - backend_java.jar generado

:: Paso 2: Compilar desktop_app
echo [2/3] Compilando desktop_app...
cd ..\desktop_app
if not exist "bin" mkdir "bin"
dir /s /b src\*.java > sources.txt
%JAVAC% --release 21 -cp "backend_java.jar;postgresql-42.7.11.jar" -d "bin" @sources.txt
if %errorlevel% neq 0 (
    echo ERROR en compilacion de desktop_app
    del sources.txt 2>nul
    pause
    exit /b 1
)
del sources.txt 2>nul
echo  OK - desktop_app compilado

:: Paso 3: Empaquetar STConnect.jar (fat JAR)
echo [3/3] Empaquetando STConnect.jar...
if exist "build_tmp" rmdir /s /q "build_tmp"
mkdir build_tmp

cd build_tmp
%JAR% xf ..\postgresql-42.7.11.jar
%JAR% xf ..\backend_java.jar
xcopy ..\bin\* . /s /e /q >nul
cd ..

(
echo Manifest-Version: 1.0
echo Main-Class: vista.Login
echo Created-By: 25.0.2 (Oracle Corporation)
) > build_tmp\META-INF\MANIFEST.MF

%JAR% cfm "STConnect.jar" build_tmp\META-INF\MANIFEST.MF -C build_tmp .
if %errorlevel% neq 0 (
    echo ERROR al empaquetar STConnect.jar
    rmdir /s /q build_tmp
    pause
    exit /b 1
)
rmdir /s /q build_tmp
echo  OK - STConnect.jar generado

echo.
echo ============================================
echo  Build completado con exito
echo  Para ejecutar: cd desktop_app ^&^& java -jar STConnect.jar
echo ============================================
pause

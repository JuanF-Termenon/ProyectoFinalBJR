@echo off
cd /d "%~dp0"
echo Compilando backend...
if not exist "target\classes" mkdir "target\classes"

C:\oracleJdk-25\bin\javac --release 21 -d "target\classes" ^
    "src\main\java\com\st\database\ConnectionFactory.java" ^
    "src\main\java\com\st\models\Incidencia.java" ^
    "src\main\java\com\st\models\Informe.java" ^
    "src\main\java\com\st\models\Puesto.java" ^
    "src\main\java\com\st\models\Rol.java" ^
    "src\main\java\com\st\models\Usuario.java" ^
    "src\main\java\com\st\repositories\IncidenciaRepository.java" ^
    "src\main\java\com\st\repositories\InformeRepository.java" ^
    "src\main\java\com\st\repositories\PuestoRepository.java" ^
    "src\main\java\com\st\repositories\RolRepository.java" ^
    "src\main\java\com\st\repositories\UsuarioRepository.java" ^
    "src\main\java\com\st\security\Utils.java"

if %errorlevel% neq 0 (
    echo Error de compilacion.
    pause
    exit /b 1
)

C:\oracleJdk-25\bin\jar cf "target\backend_java-0.0.1-SNAPSHOT.jar" -C "target\classes" .
copy /Y "target\backend_java-0.0.1-SNAPSHOT.jar" "..\desktop_app\backend_java.jar"
echo OK! JAR actualizado. Haz F5 en Eclipse sobre desktop_app.
pause

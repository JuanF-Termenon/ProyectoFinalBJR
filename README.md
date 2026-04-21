# ProyectoFinalBJR
Proyecto Final 1ºDAM 2025/2026

# Sistema de Gestión de Incidencias ST 🛠️

Proyecto final para el ciclo de **Desarrollo de Aplicaciones Multiplataforma (DAM)**. El sistema tiene como finalidad permitir que cualquier puesto de trabajo o departamento de la empresa pueda comunicar una incidencia técnica de manera inmediata, mientras el equipo de Servicio Técnico dispone de un entorno centralizado para gestionar el ciclo completo de dicha incidencia.

## 📋 Descripción del Proyecto
Este sistema no debe entenderse únicamente como una herramienta para enviar avisos, sino como una plataforma interna de gestión que permite organizar el trabajo de los técnicos, documentar soluciones y facilitar el seguimiento del rendimiento del departamento.

### Arquitectura del Sistema
El sistema se estructura en cuatro elementos principales:
* **Aplicación de Escritorio**: Orientada a puestos fijos para un reporte rápido y estable.
* **Aplicación Web Responsive**: Interfaz universal accesible desde cualquier dispositivo (PC, tablet o móvil).
* **Backend Central**: Encargado de la autenticación, reglas de negocio, gestión de estados y auditoría.
* **Base de Datos**: Almacenamiento centralizado de usuarios, incidencias, históricos e informes.

## 👥 Usuarios y Roles (RBAC)
El acceso se controla mediante permisos específicos según el perfil:
* **Empleado o Puesto de Trabajo**: Representa un punto físico (ej. TP01) y su función es registrar incidencias de forma simplificada.
* **Técnico de Servicio Técnico**: Usuario personal que puede visualizar, filtrar, asignarse y resolver incidencias con un informe obligatorio.
* **Jefe de Servicio Técnico**: Dispone de funciones de supervisión, acceso a históricos completos y exportación de métricas.
* **Administrador**: Gestión de usuarios, roles, contraseñas y configuración del sistema.

## ⚙️ Ciclo de Vida de la Incidencia
Las incidencias se gestionan a través de estados visuales:
1. **Activa (Rojo)**: Registrada y pendiente de atención.
2. **En curso (Amarillo/Naranja)**: Técnicos trabajando formalmente en ella.
3. **Resuelta (Verde)**: Solucionada tras completar el informe técnico de resolución obligatorio.

## 🔒 Seguridad y Reglas de Negocio
* **Credenciales**: Contraseñas almacenadas mediante hash seguro; cambio obligatorio en el primer acceso para el personal de ST.
* **Integridad**: No se puede cerrar una incidencia sin registrar la solución aplicada en el informe de resolución.
* **Trazabilidad**: El sistema registra automáticamente el puesto, fecha y hora de creación de cada incidencia.
* **Auditoría**: Registro de acciones sensibles como inicios de sesión y cambios de estado para control total.

---
**Desarrollado por:** Juan Francisco, Bogdan y Rubén

# 📂 Estructura del Proyecto

```text
SISTEMA_GESTION_ST/
├── 📄 README.md              # Documentación general del proyecto
├── 📂 docs/                  # Documentación técnica, manuales y el PDF del proyecto
├── 📂 database/              # Todo lo referente a la base de datos
│   ├── 📜 schema.sql         # Scripts de creación de tablas (ROL, USUARIO, etc.)
│   ├── 📜 seeds.sql          # Datos de prueba iniciales
│   └── 📜 backups/           # Registro de copias de seguridad
├── 📂 backend_java/          # El "Controlador" central (API REST con Spring Boot o Servlets)
│   ├── 📂 src/main/java/com/st/
│   │   ├── 📂 models/        # Entidades (Incidencia.java, Usuario.java)
│   │   ├── 📂 repositories/  # Acceso a DB (Consultas SQL)
│   │   ├── 📂 controllers/   # Gestión de rutas y lógica de negocio
│   │   └── 📂 security/      # Gestión de Hash de contraseñas y roles
│   └── 📜 pom.xml            # Configuración de dependencias (Maven)
├── 📂 desktop_app/           # Aplicación de escritorio para puestos de trabajo 
│   ├── 📂 src/               # Código Java Swing/JavaFX
│   └── 📂 resources/         # Imágenes e iconos de la interfaz
├── 📂 web_app/               # Aplicación web responsive
│   ├── 📄 index.html         # Panel principal
│   ├── 📂 css/               # Estilos (colores para estados: rojo, amarillo, verde)
│   └── 📂 js/                # Lógica de consumo de la API del backend
└── 📂 server_config/         # Configuración para el despliegue en el servidor
    ├── 📜 docker-compose.yml # (Opcional) Para levantar todo el entorno
    └── 📜 nginx.conf         # Configuración del servidor web

CREATE TABLE ROL (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE PUESTO (
    id_puesto INT PRIMARY KEY AUTO_INCREMENT,
    nombre_puesto VARCHAR(100) NOT NULL,
    departamento VARCHAR(100)
);

CREATE TABLE USUARIO (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    id_rol INT,
    id_puesto INT,
    FOREIGN KEY (id_rol) REFERENCES ROL(id_rol),
    FOREIGN KEY (id_puesto) REFERENCES PUESTO(id_puesto)
);

CREATE TABLE INCIDENCIA (
    id_incidencia INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    prioridad ENUM('Baja', 'Media', 'Alta', 'Crítica') DEFAULT 'Media',
    estado ENUM('Abierta', 'En Proceso', 'Resuelta', 'Cerrada') DEFAULT 'Abierta',
    id_usuario_reporta INT,
    FOREIGN KEY (id_usuario_reporta) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE ASIGNACION (
    id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
    id_incidencia INT,
    id_usuario_tecnico INT,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_incidencia) REFERENCES INCIDENCIA(id_incidencia),
    FOREIGN KEY (id_usuario_tecnico) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE INFORME_RESOLUCION (
    id_informe INT PRIMARY KEY AUTO_INCREMENT,
    id_incidencia INT UNIQUE,
    detalle_solucion TEXT NOT NULL,
    fecha_resolucion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tiempo_empleado_horas DECIMAL(5,2),
    FOREIGN KEY (id_incidencia) REFERENCES INCIDENCIA(id_incidencia)
);

CREATE TABLE HISTORIAL_INCIDENCIA (
    id_historial INT PRIMARY KEY AUTO_INCREMENT,
    id_incidencia INT,
    estado_anterior VARCHAR(50),
    estado_nuevo VARCHAR(50),
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comentario TEXT,
    id_usuario_cambio INT,
    FOREIGN KEY (id_incidencia) REFERENCES INCIDENCIA(id_incidencia),
    FOREIGN KEY (id_usuario_cambio) REFERENCES USUARIO(id_usuario)
);
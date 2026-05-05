-- ENTIDADES
-- Rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    nombre_visible VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    primer_acceso BOOLEAN DEFAULT TRUE,
    ultimo_login TIMESTAMP,
    id_rol INT NOT NULL,

    CONSTRAINT fk_usuario_rol
        FOREIGN KEY (id_rol)
        REFERENCES rol(id_rol)
);

-- Puesto
CREATE TABLE puesto (
    id_puesto SERIAL PRIMARY KEY,
    codigo_puesto VARCHAR(50) NOT NULL UNIQUE,
    departamento VARCHAR(100),
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    id_usuario INT,

    CONSTRAINT fk_puesto_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

-- Incidencia
CREATE TABLE incidencia (
    id_incidencia SERIAL PRIMARY KEY,
    descripcion TEXT,
    prioridad VARCHAR(10) CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    estado VARCHAR(15) NOT NULL CHECK (estado IN ('ACTIVA', 'EN_CURSO', 'RESUELTA', 'REABIERTA', 'CANCELADA')),
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio_atencion TIMESTAMP,
    fecha_resolucion TIMESTAMP,

    id_puesto INT NOT NULL,
    id_usuario_creador INT NOT NULL,
    id_usuario_cierre INT,

    CONSTRAINT fk_incidencia_puesto
        FOREIGN KEY (id_puesto)
        REFERENCES puesto(id_puesto),

    CONSTRAINT fk_incidencia_creador
        FOREIGN KEY (id_usuario_creador)
        REFERENCES usuario(id_usuario),

    CONSTRAINT fk_incidencia_cierre
        FOREIGN KEY (id_usuario_cierre)
        REFERENCES usuario(id_usuario)
);

-- Asignacion
CREATE TABLE asignacion (
    id_asignacion SERIAL PRIMARY KEY,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP,
    tipo_participacion VARCHAR(20) CHECK (tipo_participacion IN ('ASIGNADO', 'APOYO', 'SUPERVISION', 'CIERRE')),

    id_incidencia INT NOT NULL,
    id_usuario_st INT NOT NULL,

    CONSTRAINT fk_asignacion_incidencia
        FOREIGN KEY (id_incidencia)
        REFERENCES incidencia(id_incidencia)
        ON DELETE CASCADE,

    CONSTRAINT fk_asignacion_usuario
        FOREIGN KEY (id_usuario_st)
        REFERENCES usuario(id_usuario)
);

-- Informe_Resolucion
CREATE TABLE informe_resolucion (
    id_informe SERIAL PRIMARY KEY,
    informe TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    id_incidencia INT NOT NULL UNIQUE,
    id_usuario_st INT NOT NULL,

    CONSTRAINT fk_informe_incidencia
        FOREIGN KEY (id_incidencia)
        REFERENCES incidencia(id_incidencia)
        ON DELETE CASCADE,

    CONSTRAINT fk_informe_usuario
        FOREIGN KEY (id_usuario_st)
        REFERENCES usuario(id_usuario)
);

-- Historial
CREATE TABLE historial_incidencia (
    id_historial SERIAL PRIMARY KEY,
    accion VARCHAR(50) NOT NULL,
    estado_anterior VARCHAR(15),
    estado_nuevo VARCHAR(15),
    comentario TEXT,
    fecha_evento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    id_incidencia INT NOT NULL,
    id_usuario INT NOT NULL,

    CONSTRAINT fk_historial_incidencia
        FOREIGN KEY (id_incidencia)
        REFERENCES incidencia(id_incidencia)
        ON DELETE CASCADE,

    CONSTRAINT fk_historial_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

-- Auditoria
CREATE TABLE auditoria (
    id_auditoria SERIAL PRIMARY KEY,
    tabla_objetivo VARCHAR(50),
    registro_id INT,
    operacion VARCHAR(20),
    campo VARCHAR(50),
    valor_anterior TEXT,
    valor_nuevo TEXT,
    fecha_evento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip VARCHAR(50),
    user_agent TEXT,
    metadata JSONB,

    id_usuario INT,

    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

-- TRIGGERS
-- Cada vez que cambia el estado → guardar en historial.
CREATE OR REPLACE FUNCTION fn_historial_incidencia()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO historial_incidencia (
        accion,
        estado_anterior,
        estado_nuevo,
        fecha_evento,
        id_incidencia,
        id_usuario
    )
    VALUES (
        'cambio_estado',
        OLD.estado,
        NEW.estado,
        CURRENT_TIMESTAMP,
        NEW.id_incidencia,
        NEW.id_usuario_cierre
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_historial_estado
AFTER UPDATE OF estado ON incidencia
FOR EACH ROW
WHEN (OLD.estado IS DISTINCT FROM NEW.estado)
EXECUTE FUNCTION fn_historial_incidencia();

-- Registrar INSERT / UPDATE / DELETE en tablas críticas.
CREATE OR REPLACE FUNCTION fn_auditoria()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO auditoria (
        tabla_objetivo,
        registro_id,
        operacion,
        fecha_evento,
        id_usuario
    )
    VALUES (
        TG_TABLE_NAME,
        NEW.id_incidencia,
        TG_OP,
        CURRENT_TIMESTAMP,
        NEW.id_usuario_creador
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_incidencia
AFTER INSERT OR UPDATE OR DELETE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_auditoria();

-- Evitar cerrar incidencia sin informe
CREATE OR REPLACE FUNCTION fn_validar_cierre()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.estado = 'RESUELTA' THEN
        IF NOT EXISTS (
            SELECT 1 FROM informe_resolucion
            WHERE id_incidencia = NEW.id_incidencia
        ) THEN
            RAISE EXCEPTION 'No se puede cerrar la incidencia sin informe';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validar_cierre
BEFORE UPDATE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_validar_cierre();

-- Auto rellenar fecha_inicio_atencion
CREATE OR REPLACE FUNCTION fn_inicio_atencion()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.estado = 'EN_CURSO' AND OLD.estado IS DISTINCT FROM 'EN_CURSO' THEN
        NEW.fecha_inicio_atencion := CURRENT_TIMESTAMP;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_inicio_atencion
BEFORE UPDATE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_inicio_atencion();
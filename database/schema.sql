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
    id_usuario INT UNIQUE,
    codigo_puesto VARCHAR(50) NOT NULL UNIQUE,
    departamento VARCHAR(100),
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_puesto_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

-- Incidencia
CREATE TABLE incidencia (
    id_incidencia SERIAL PRIMARY KEY,
    descripcion TEXT,
    prioridad VARCHAR(10) NOT NULL DEFAULT 'MEDIA'
        CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    estado VARCHAR(15) NOT NULL DEFAULT 'ACTIVA'
        CHECK (estado IN ('ACTIVA', 'EN_CURSO', 'RESUELTA', 'REABIERTA', 'CANCELADA')),
    
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
    tipo_participacion VARCHAR(20) NOT NULL CHECK (tipo_participacion IN ('ASIGNADO', 'APOYO', 'SUPERVISION', 'CIERRE')),

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
    accion VARCHAR(50) NOT NULL
        CHECK (accion IN ('CREAR', 'CAMBIAR_ESTADO', 'ASIGNAR', 'RESOLVER', 'REABRIR', 'CANCELAR')),
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
    tabla_objetivo VARCHAR(50) NOT NULL,
    registro_id INT NOT NULL,
    operacion VARCHAR(20) NOT NULL,
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
DECLARE
    v_usuario_id INT;
    v_accion VARCHAR(50);
BEGIN
    -- Obtenemos el usuario de la sesión
    v_usuario_id := NULLIF(current_setting('st.usuario_activo', true),'')::INT;

    IF v_usuario_id IS NULL THEN
        RAISE EXCEPTION 'Error de seguridad: Toda acción debe tener un usuario asociado.';
    END IF;

    IF (TG_OP = 'INSERT') THEN
        v_accion := 'CREAR';

    ELSIF (TG_OP = 'UPDATE') THEN
        CASE 
            WHEN NEW.estado = 'RESUELTA' THEN v_accion := 'RESOLVER';
            WHEN NEW.estado = 'REABIERTA' THEN v_accion := 'REABRIR';
            WHEN NEW.estado = 'CANCELADA' THEN v_accion := 'CANCELAR';
            ELSE v_accion := 'CAMBIAR_ESTADO';
        END CASE;
    END IF;

    -- Guarda el estado anterior y nuevo
    INSERT INTO historial_incidencia (
        accion, 
        estado_anterior, 
        estado_nuevo, 
        id_incidencia, 
        id_usuario
    ) VALUES (
        v_accion, 
        CASE WHEN TG_OP = 'UPDATE' THEN OLD.estado ELSE NULL END,
        NEW.estado,
        NEW.id_incidencia,
        v_usuario_id
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_historial_insert
AFTER INSERT ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_historial_incidencia();

CREATE TRIGGER trg_historial_estado
AFTER UPDATE OF estado ON incidencia
FOR EACH ROW
WHEN (OLD.estado IS DISTINCT FROM NEW.estado)
EXECUTE FUNCTION fn_historial_incidencia();

-- Cada vez que se asigna tecnico → guardar en historial.
CREATE OR REPLACE FUNCTION fn_historial_asignacion()
RETURNS TRIGGER AS $$
DECLARE
    v_usuario_id INT;
BEGIN
    
    v_usuario_id := NULLIF(current_setting('st.usuario_activo', true),'')::INT;

    IF v_usuario_id IS NULL THEN
        RAISE EXCEPTION 'Error de seguridad: Toda acción debe tener un usuario asociado.';
    END IF;

    INSERT INTO historial_incidencia (
        accion,
        id_incidencia,
        id_usuario
    ) VALUES (
        'ASIGNAR', 
        NEW.id_incidencia,
        v_usuario_id
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_historial_asignacion
AFTER INSERT ON asignacion
FOR EACH ROW EXECUTE FUNCTION fn_historial_asignacion();

-- Registrar INSERT / UPDATE / DELETE en tablas.
CREATE OR REPLACE FUNCTION fn_auditoria()
RETURNS TRIGGER AS $$
DECLARE
    v_usuario_id INT;
    v_registro_id INT;
BEGIN

     v_usuario_id := NULLIF(current_setting('st.usuario_activo', true),'')::INT;

    IF v_usuario_id IS NULL THEN
        RAISE EXCEPTION 'Error de seguridad: Toda acción debe tener un usuario asociado.';
    END IF;

    IF TG_TABLE_NAME = 'incidencia' THEN
        v_registro_id := COALESCE(NEW.id_incidencia, OLD.id_incidencia);

    ELSIF TG_TABLE_NAME = 'usuario' THEN
        v_registro_id := COALESCE(NEW.id_usuario, OLD.id_usuario);

    ELSE
        RAISE EXCEPTION 'Tabla no soportada por auditoría: %', TG_TABLE_NAME;
    END IF;

    INSERT INTO auditoria (
        tabla_objetivo,
        registro_id,
        operacion,
        id_usuario
    ) VALUES (
        TG_TABLE_NAME,
        v_registro_id,
        TG_OP,
        v_usuario_id
    );

    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;

END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_incidencia
AFTER INSERT OR UPDATE OR DELETE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_auditoria();

CREATE TRIGGER trg_auditoria_usuario
AFTER INSERT OR UPDATE OR DELETE ON usuario
FOR EACH ROW EXECUTE FUNCTION fn_auditoria();

-- Evitar cerrar incidencia sin informe
CREATE OR REPLACE FUNCTION fn_validar_cierre()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.estado = 'RESUELTA' AND OLD.estado IS DISTINCT FROM 'RESUELTA' THEN
        IF NOT EXISTS (
            SELECT 1 FROM informe_resolucion
            WHERE id_incidencia = NEW.id_incidencia
        ) THEN
            RAISE EXCEPTION 'No se puede resolver la incidencia sin un informe previo.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validar_cierre
BEFORE UPDATE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_validar_cierre();

CREATE OR REPLACE FUNCTION fn_gestion_fechas_incidencia()
RETURNS TRIGGER AS $$
BEGIN

    IF NEW.estado = 'EN_CURSO' AND OLD.estado IS DISTINCT FROM 'EN_CURSO' THEN
        NEW.fecha_inicio_atencion := CURRENT_TIMESTAMP;
    END IF;

    IF NEW.estado = 'RESUELTA' AND OLD.estado IS DISTINCT FROM 'RESUELTA' THEN
        NEW.fecha_resolucion := CURRENT_TIMESTAMP;
    END IF;

    IF NEW.estado = 'REABIERTA' AND OLD.estado IS DISTINCT FROM 'REABIERTA' THEN
        NEW.fecha_resolucion := NULL;
        NEW.id_usuario_cierre := NULL;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_gestion_fechas
BEFORE UPDATE ON incidencia
FOR EACH ROW
EXECUTE FUNCTION fn_gestion_fechas_incidencia();
-- ROLES
INSERT INTO rol (nombre_rol, descripcion) VALUES
('OPERARIO', 'Usuario de puesto'),
('TECNICO', 'Técnico de servicio técnico'),
('JEFE_ST', 'Supervisor del servicio técnico'),
('ADMIN', 'Administrador del sistema');

-- USUARIOS
INSERT INTO usuario (username, password_hash, nombre_visible, id_rol) VALUES
('tp01', 'hash1', 'Puesto TP01', 1),
('tp02', 'hash2', 'Puesto TP02', 1),
('tecnico1', 'hash3', 'Juan Técnico', 2),
('tecnico2', 'hash4', 'Ana Técnica', 2),
('jefe', 'hash5', 'Jefe ST', 3),
('admin', 'hash6', 'Administrador', 4);

-- PUESTOS
INSERT INTO puesto (codigo_puesto, departamento, descripcion, id_usuario) VALUES
('TP01', 'Producción', 'Prensa hidráulica 1', 1),
('TP02', 'Producción', 'Línea de montaje 2', 2);

-- INCIDENCIAS
INSERT INTO incidencia (descripcion, prioridad, estado, id_puesto, id_usuario_creador)
VALUES
('La máquina no arranca', 'ALTA', 'ACTIVA', 1, 1),
('Error en pantalla de control', 'MEDIA', 'ACTIVA', 2, 2);

-- ASIGNACIONES
INSERT INTO asignacion (id_incidencia, id_usuario_st, tipo_participacion)
VALUES
(1, 3, 'ASIGNADO'),
(2, 4, 'ASIGNADO');


-- (para probar triggers)
-- CAMBIAR ESTADO 
UPDATE incidencia
SET estado = 'EN_CURSO'
WHERE id_incidencia = 1;

-- INFORME DE RESOLUCIÓN
INSERT INTO informe_resolucion (informe, id_incidencia, id_usuario_st)
VALUES
('Se reconectó la alimentación eléctrica', 1, 3);

-- CERRAR INCIDENCIA
UPDATE incidencia
SET estado = 'RESUELTA',
    id_usuario_cierre = 3,
    fecha_resolucion = CURRENT_TIMESTAMP
WHERE id_incidencia = 1;

-- HISTORIAL MANUAL EXTRA (opcional)
INSERT INTO historial_incidencia (accion, estado_anterior, estado_nuevo, id_incidencia, id_usuario)
VALUES
('creacion', NULL, 'ACTIVA', 2, 2);

-- AUDITORÍA (opcional manual)
INSERT INTO auditoria (tabla_objetivo, registro_id, operacion, id_usuario)
VALUES
('incidencia', 1, 'UPDATE', 3);

-- Comprobar:
SELECT * FROM incidencia;
SELECT * FROM historial_incidencia;
SELECT * FROM informe_resolucion;
SELECT * FROM asignacion;
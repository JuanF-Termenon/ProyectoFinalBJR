-- ROLES
INSERT INTO rol (nombre_rol, descripcion) VALUES
(1, 'Jefe ST', 'Administrador total del sistema'),
(2, 'Técnico ST', 'Personal técnico de soporte');

-- USUARIOS
INSERT INTO usuario (username, password_hash, nombre_visible, id_rol) VALUES
('bbjf', '321efejst', 'Bebe Jefazo', 1),
('tectec', '321cetst', 'Tec Tec', 2);

-- PUESTOS
INSERT INTO puesto (codigo_puesto, departamento, descripcion, id_usuario) VALUES
(1, 'Central-01', 'Oficina Principal');

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
SET st.usuario_activo = '1';
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

-- Comprobar:
SELECT * FROM incidencia;
SELECT * FROM historial_incidencia;
SELECT * FROM informe_resolucion;
SELECT * FROM asignacion;
-- (para probar triggers)
SET st.usuario_activo = '1';

-- ROLES
INSERT INTO rol (id_rol, nombre_rol, descripcion) VALUES
(1, 'Jefe ST', 'Administrador total del sistema'),
(2, 'Técnico ST', 'Personal técnico de soporte');

-- USUARIOS
INSERT INTO usuario (id_usuario, username, password_hash, nombre_visible, primer_acceso, id_rol) VALUES
(1, 'bbjf', '321efejst', 'Bebe Jefazo', FALSE, 1),
(2, 'tectec', '321cetst', 'Tec Tec', TRUE, 2);

-- PUESTOS
INSERT INTO puesto (id_puesto, codigo_puesto, departamento) VALUES
(1, 'Central-01', 'Oficina Principal');

-- INCIDENCIAS
INSERT INTO incidencia 
(descripcion, prioridad, estado, fecha_creacion, id_puesto, id_usuario_creador) 
VALUES 
('Fallo total del servidor de base de datos. Nadie puede trabajar.', 
 'CRITICA', 'ACTIVA', CURRENT_TIMESTAMP - INTERVAL '1 hour', 1, 2),
('El monitor del puesto 04 parpadea constantemente y se apaga.', 
 'ALTA', 'EN_CURSO', CURRENT_TIMESTAMP - INTERVAL '5 hours', 1, 1),
('Instalación de paquete Office 2026 solicitada por contabilidad.', 
 'MEDIA', 'RESUELTA', CURRENT_TIMESTAMP - INTERVAL '1 day', 1, 2),
('Prueba de sistema - Ignorar esta incidencia.', 
 'BAJA', 'CANCELADA', CURRENT_TIMESTAMP - INTERVAL '2 days', 1, 1),
('La impresora sigue atascando papel después del cambio de tóner.', 
 'ALTA', 'REABIERTA', CURRENT_TIMESTAMP - INTERVAL '3 hours', 1, 2);

-- ASIGNACIONES
INSERT INTO asignacion (id_incidencia, id_usuario_st, tipo_participacion)
VALUES
(1, 3, 'ASIGNADO'),
(2, 4, 'ASIGNADO');

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
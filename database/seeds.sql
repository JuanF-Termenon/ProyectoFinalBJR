-- Para probar los triggers
 SET st.usuario_activo = '1';

-- ROLES
INSERT INTO rol (id_rol, nombre_rol, descripcion) VALUES
(1, 'Jefe ST', 'Administrador total del sistema'),
(2, 'Técnico ST', 'Personal técnico de soporte'),
(3, 'Operario', 'Usuario de puesto de trabajo que reporta incidencias');

-- USUARIOS
INSERT INTO usuario (id_usuario, username, password_hash, nombre_visible, primer_acceso, id_rol) VALUES
(1, 'bbjf', '321efejst', 'Bebe Jefazo', FALSE, 1),
(2, 'tectec', '321cetst', 'Tec Tec', FALSE, 2),
(3, 'operario', 'oirarepost', 'Operario de Oficina', TRUE, 3),
(4, 'tec2', '654cetst', 'Técnico Dos', FALSE, 2);

-- PUESTOS
INSERT INTO puesto (id_puesto, codigo_puesto, departamento, id_usuario) VALUES
(1, 'Central-01', 'Oficina Principal', 2),
(2, 'Central-02', 'Oficina Principal', 3),
(3, 'Prod-01', 'Producción', 4);

-- INCIDENCIAS
INSERT INTO incidencia (descripcion, prioridad, estado, fecha_creacion, id_puesto, id_usuario_creador) VALUES
('Fallo total del servidor de base de datos. Nadie puede trabajar.', 'CRITICA', 'ACTIVA', CURRENT_TIMESTAMP - INTERVAL '1 hour', 1, 3),
('El monitor del puesto 04 parpadea constantemente y se apaga.', 'ALTA', 'EN_CURSO', CURRENT_TIMESTAMP - INTERVAL '5 hours', 1, 3),
('Instalación de paquete Office 2026 solicitada por contabilidad.', 'MEDIA', 'RESUELTA', CURRENT_TIMESTAMP - INTERVAL '1 day', 1, 3),
('Prueba de sistema - Ignorar esta incidencia.', 'BAJA', 'CANCELADA', CURRENT_TIMESTAMP - INTERVAL '2 days', 1, 1),
('La impresora sigue atascando papel después del cambio de tóner.', 'ALTA', 'REABIERTA', CURRENT_TIMESTAMP - INTERVAL '3 hours', 1, 3),
('El sistema de control numérico no arranca en la línea 3.', 'CRITICA', 'ACTIVA', CURRENT_TIMESTAMP - INTERVAL '30 minutes', 3, 3),
('La cinta transportadora emite un ruido anómalo al arrancar.', 'MEDIA', 'ACTIVA', CURRENT_TIMESTAMP - INTERVAL '2 hours', 3, 3),
('El lector de códigos de barras del muelle 2 no escanea.', 'BAJA', 'EN_CURSO', CURRENT_TIMESTAMP - INTERVAL '4 hours', 2, 3),
('Pantalla táctil del panel de control no responde.', 'ALTA', 'ACTIVA', CURRENT_TIMESTAMP - INTERVAL '1 hour', 3, 3),
('Actualización del firmware solicitada por calidad.', 'BAJA', 'RESUELTA', CURRENT_TIMESTAMP - INTERVAL '3 days', 2, 3);

-- ASIGNACIONES
INSERT INTO asignacion (id_incidencia, id_usuario_st, tipo_participacion) VALUES
(1, 2, 'ASIGNADO'),
(2, 2, 'ASIGNADO'),
(6, 4, 'ASIGNADO'),
(8, 2, 'ASIGNADO');

-- INFORMES
INSERT INTO informe_resolucion (informe, id_incidencia, id_usuario_st) VALUES
('Se reconectó la alimentación eléctrica', 1, 2),
('Firmware actualizado a la versión 3.2.1', 10, 2);

-- NOTAS INTERNAS
INSERT INTO nota_interna (nota, id_incidencia, id_usuario) VALUES
('He revisado los logs del servidor y parece un problema de conexión.', 1, 2),
('Contacté con el proveedor del monitor. Envían repuesto mañana.', 2, 2),
('Probando con otro cable de red para descartar problema físico.', 8, 2);

-- CAMBIOS DE ESTADO
UPDATE incidencia SET estado = 'EN_CURSO' WHERE id_incidencia = 1;
UPDATE incidencia SET estado = 'EN_CURSO' WHERE id_incidencia = 8;
UPDATE incidencia SET estado = 'RESUELTA', id_usuario_cierre = 2, fecha_resolucion = CURRENT_TIMESTAMP WHERE id_incidencia = 10;

-- Cierre de incidencia 1 (con informe ya insertado)
UPDATE incidencia SET estado = 'RESUELTA', id_usuario_cierre = 2, fecha_resolucion = CURRENT_TIMESTAMP WHERE id_incidencia = 1;

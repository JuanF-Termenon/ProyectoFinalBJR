-- Roles del sistema
INSERT INTO ROL (nombre_rol, descripcion) VALUES 
('Administrador', 'Acceso total al sistema'),
('Soporte Técnico', 'Encargado de resolver incidencias'),
('Empleado', 'Usuario que reporta problemas');

-- Puestos de trabajo
INSERT INTO PUESTO (nombre_puesto, departamento) VALUES 
('Analista de Sistemas', 'IT'),
('Técnico de Redes', 'IT'),
('Asistente Administrativo', 'Recursos Humanos'),
('Contador General', 'Finanzas');

-- Usuarios
-- Nota: Las contraseñas deberían estar encriptadas en un entorno real
INSERT INTO USUARIO (nombre, correo, password, id_rol, id_puesto) VALUES 
('Admin Principal', 'admin@empresa.com', 'hash_admin_123', 1, 1),
('Juan Técnico', 'j.tecnico@empresa.com', 'hash_juan_456', 2, 2),
('Maria Empleada', 'm.empleada@empresa.com', 'hash_maria_789', 3, 3),
('Carlos Finanzas', 'c.finanzas@empresa.com', 'hash_carlos_000', 3, 4);

-- Una incidencia reportada por Maria (ID 3)
INSERT INTO INCIDENCIA (titulo, descripcion, prioridad, estado, id_usuario_reporta) VALUES 
('Fallo de Impresora', 'La impresora del piso 2 no reconoce el tóner nuevo.', 'Media', 'Abierta', 3),
('Error en Software Contable', 'No permite exportar el balance mensual a PDF.', 'Alta', 'En Proceso', 4);

-- Asignación de la incidencia de la impresora al técnico Juan (ID 2)
INSERT INTO ASIGNACION (id_incidencia, id_usuario_tecnico) VALUES 
(1, 2);

-- Una incidencia reportada por Maria (ID 3)
INSERT INTO INCIDENCIA (titulo, descripcion, prioridad, estado, id_usuario_reporta) VALUES 
('Fallo de Impresora', 'La impresora del piso 2 no reconoce el tóner nuevo.', 'Media', 'Abierta', 3),
('Error en Software Contable', 'No permite exportar el balance mensual a PDF.', 'Alta', 'En Proceso', 4);

-- Asignación de la incidencia de la impresora al técnico Juan (ID 2)
INSERT INTO ASIGNACION (id_incidencia, id_usuario_tecnico) VALUES 
(1, 2);
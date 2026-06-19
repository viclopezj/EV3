-- Inserciones para la tabla locales
INSERT INTO locales (nombre, comuna, direccion, hora_apertura, hora_cierre, id_gerente)
VALUES
-- Gerentes Junior: Administran 1 local cada uno (IDs: 1, 2, 3)
('Bistro Duoc UC', 'Puente Alto', 'Av. Concha y Toro 1340', '08:00:00', '22:00:00', 1),
('La Picá de Maipú', 'Maipú', 'Av. Pajaritos 450', '09:00:00', '23:00:00', 2),
('Sabores de Providencia', 'Providencia', 'Av. Lyon 120', '10:00:00', '23:30:00', 3),

-- Gerentes Senior: Administran 2 locales cada uno (IDs: 4 y 5)
-- Gerente Senior 4 (Locales 4 y 5)
('Express Santiago', 'Santiago', 'Huérfanos 1020', '07:30:00', '20:00:00', 4),
('Costa Central', 'Viña del Mar', 'Calle Valparaíso 550', '11:00:00', '01:00:00', 4),
-- Gerente Senior 5 (Locales 6 y 7)
('Terraza La Florida', 'La Florida', 'Av. Vicuña Mackenna 7200', '11:30:00', '23:00:00', 5),
('Rincón de Ñuñoa', 'Ñuñoa', 'Av. Irarrázaval 3500', '12:00:00', '00:00:00', 5),

-- Gerente Regional: Administra 3 locales (ID: 6)
('Portal San Bernardo', 'San Bernardo', 'Av. Colón 250', '10:00:00', '22:00:00', 6),
('Estación Central Fast', 'Estación Central', 'Av. Libertador Bernardo O''Higgins 3400', '07:00:00', '21:30:00', 6),
('Plaza Las Condes', 'Las Condes', 'Av. Apoquindo 4800', '09:00:00', '23:00:00', 6);
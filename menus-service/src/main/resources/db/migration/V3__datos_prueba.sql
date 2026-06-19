-- 1. INSERCIÓN DE CATEGORÍAS
INSERT INTO categorias (codigo, nombre)
VALUES ('ALM', 'Almuerzos'),
       ('CEN', 'Cenas'),
       ('CAF', 'Cafetería'),
       ('SAL', 'Saludable'),
       ('ENT', 'Entradas'),
       ('BEB', 'Bebestibles');

-- 2. INSERCIÓN DE MENÚS
INSERT INTO menus (nombre, precio, cod_categoria, id_local)
VALUES
-- Local 1: Bistro Duoc UC
('Menú Ejecutivo Duoc', 5500, 'ALM', 1),
('Promo Desayuno Estudiante', 2990, 'CAF', 1),
('Ensalada Premium César', 4800, 'SAL', 1),
('Té Matcha Latte', 2500, 'BEB', 1),

-- Local 2: La Picá de Maipú
('Parrillada Familiar', 25000, 'CEN', 2),
('Cazuela de Vacuno', 6500, 'ALM', 2),
('Empanada de Pino de la Casa', 2200, 'ENT', 2),
('Mote con Huesillo XL', 1800, 'BEB', 2),

-- Local 3: Sabores de Providencia
('Café Latte de Especialidad', 3800, 'CAF', 3),
('Cheesecake de Arándanos', 4200, 'ENT', 3),
('Bowl de Quinoa y Palta', 6900, 'SAL', 3),
('Limonada Menta Jengibre', 3200, 'BEB', 3),

-- Local 4: Express Santiago
('Sandwich de Mechada', 7500, 'ALM', 4),
('Jugo Natural del Día', 2500, 'BEB', 4),
('Croissant Jamón Queso', 3100, 'CAF', 4),
('Consomé de Ave', 1500, 'ENT', 4),

-- Local 5: Costa Central
('Reineta a la Plancha', 11900, 'ALM', 5),
('Paila Marina Especial', 14500, 'CEN', 5),
('Ceviche de Salmón Individual', 8900, 'ENT', 5),
('Pisco Sour Casero', 4500, 'BEB', 5),

-- Local 6: Terraza La Florida
('Burguer Doble Cheddar', 7990, 'ALM', 6),
('Costillar de Cerdo BBQ', 12500, 'CEN', 6),
('Papas Fritas con Queso', 4500, 'ENT', 6),
('Schop Artesanal 500cc', 3800, 'BEB', 6),

-- Local 7: Rincón de Ñuñoa
('Fettuccine Alfredo', 8200, 'ALM', 7),
('Pizza Mozzarella Familiar', 11000, 'CEN', 7),
('Bruschettas Tomate Albahaca', 3900, 'ENT', 7),
('Espresso Doble Macchiato', 2400, 'CAF', 7),

-- Local 8: Portal San Bernardo
('Pollo Arvejado con Arroz', 5800, 'ALM', 8),
('Charquicán Casero con Huevo', 5200, 'ALM', 8),
('Sopaipillas Pasadas (3 un)', 2000, 'ENT', 8),
('Nectar de Fruta Andina', 1500, 'BEB', 8),

-- Local 9: Estación Central Fast
('Completo Italiano Gigante', 2800, 'ALM', 9),
('Chorrillana Fast', 9900, 'CEN', 9),
('Wrap de Pollo Liviano', 4200, 'SAL', 9),
('Bebida en Lata 350cc', 1200, 'BEB', 9),

-- Local 10: Plaza Las Condes
('Filete de Res en Salsa Pimienta', 16900, 'CEN', 10),
('Tartar de Atún Rojo', 10500, 'ENT', 10),
('Ensalada Orgánica de Salmón', 9200, 'SAL', 10),
('Copa de Vino Reserva', 5000, 'BEB', 10);
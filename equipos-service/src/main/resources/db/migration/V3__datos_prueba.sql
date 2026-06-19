-- 1. INSERCIÓN DE MARCAS
INSERT INTO marcas (codigo, nombre)
VALUES ('RAT-01', 'Rational'),
       ('WIN-02', 'Winterhalter'),
       ('TRA-03', 'Tramontina'),
       ('MAQ-04', 'Maquipan'),
       ('UNX-05', 'Unox'),
       ('HOB-06', 'Hobart');

-- 2. INSERCIÓN DE EQUIPOS (CORREGIDO)
-- Reglas aplicadas:
-- - Solo proveedores del 1 al 5 (exclusivos de maquinaria).
INSERT INTO equipos (nombre, cod_marca, fecha_compra, ultima_mantencion, proxima_mantencion, id_proveedor)
VALUES
-- Proveedor 1 (4 equipos)
('Horno Combinado iCombi Pro', 'RAT-01', '2024-01-15', '2025-01-10', '2026-01-10', 1),
('Lavavajillas de Capota', 'WIN-02', '2024-02-20', '2025-02-15', '2026-02-15', 1),
('Mesón Refrigerado 3 Puertas', 'TRA-03', '2024-03-10', '2025-03-05', '2026-03-05', 1),
('Batidora Industrial de Pedestal', 'HOB-06', '2024-04-05', '2025-04-01', '2026-04-01', 1),     -- NUEVO

-- Proveedor 2 (4 equipos)
('Amasadora Espiral 25kg', 'MAQ-04', '2024-04-12', '2025-04-10', '2026-04-10', 2),
('Horno Convección Bakerlux', 'UNX-05', '2024-05-18', '2025-05-20', '2026-05-20', 2),
('Batidora Planetaria 20L', 'HOB-06', '2024-06-25', '2025-06-15', '2026-06-15', 2),
('Freidora Eléctrica de Alta Potencia', 'RAT-01', '2024-07-02', '2025-07-01', '2026-07-01', 2), -- NUEVO

-- Proveedor 3 (4 equipos)
('Sartén Basculante Multifunción', 'RAT-01', '2024-07-05', '2025-07-01', '2026-07-01', 3),
('Lavavajillas Bajo Barra', 'WIN-02', '2024-08-14', '2025-08-10', '2026-08-10', 3),
('Estantería de Acero Inoxidable', 'TRA-03', '2024-09-22', '2025-09-18', '2026-09-18', 3),
('Horno Convector Pro', 'UNX-05', '2024-10-05', '2025-10-01', '2026-10-01', 3),                 -- NUEVO

-- Proveedor 4 (4 equipos)
('Cortadora de Pan Industrial', 'MAQ-04', '2024-10-10', '2025-10-05', '2026-10-05', 4),
('Horno ChefTop Mind.Maps', 'UNX-05', '2024-11-15', '2025-11-12', '2026-11-12', 4),
('Peladora de Papas 15kg', 'HOB-06', '2024-12-05', '2025-12-01', '2026-12-01', 4),
('Lavavasos para Barra', 'WIN-02', '2025-01-10', '2025-07-10', '2026-01-10', 4),                -- NUEVO

-- Proveedor 5 (4 equipos)
('Abatidor de Temperatura', 'RAT-01', '2024-01-20', '2025-01-18', '2026-01-18', 5),
('Lavautensilios Gran Capacidad', 'WIN-02', '2024-02-10', '2025-02-08', '2026-02-08', 5),
('Carro Bandejero 15 Niveles', 'TRA-03', '2024-03-15', '2025-03-10', '2026-03-10', 5),
('Sobadora de Masa Reforzada', 'MAQ-04', '2024-04-18', '2025-04-15', '2026-04-15', 5); -- NUEVO
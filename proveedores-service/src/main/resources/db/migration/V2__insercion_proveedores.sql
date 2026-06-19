-- Inserciones para la tabla proveedores
-- IDs 1 al 5 asociados estrictamente a 'Maquinaria' o 'Infraestructura' (Microservicio Equipos).
-- IDs 6 al 8 asociados estrictamente a 'Alimentos' (Microservicio Inventarios).
-- IDs 9 y 10 asociados estrictamente a 'Limpieza' (Microservicio Inventarios).

INSERT INTO proveedores (nombre_empresa, email, fono, region, tipo_proveedor)
VALUES
-- Proveedores de Equipos y Maquinaria (IDs 1 al 5)
('Tecnología Gastronómica Pro', 'soporte@tecnopro.cl', '+56228889901', 'Metropolitana', 'Maquinaria'),
('Tornilla Tu Equipo Spa', 'compra@tornillosyequipos.net', '+56223334442', 'Metropolitana', 'Maquinaria'),
('Inox Chile Maquinarias', 'contacto@inoxchile.cl', '+56955556663', 'Metropolitana', 'Maquinaria'),
('Maquipan Distribución', 'comercial@maquipan.cl', '+56224445554', 'Metropolitana', 'Maquinaria'),
('Maquinarias del Norte Ltda', 'ventas.norte@maqnorte.cl', '+56552223335', 'Antofagasta', 'Maquinaria'),

-- Proveedores de Alimentos (IDs 6 al 8)
('Distribuidora Alimentos S.A.', 'contacto@distalimentos.cl', '+56911112226', 'Metropolitana', 'Alimentos'),
('Carnes Premium del Sur Ltda', 'ventas@carnespremium.cl', '+56977778887', 'Los Lagos', 'Alimentos'),
('Agrícola Central y Vega Ltda', 'pedidos@agricolacentral.cl', '+56933334448', 'Valparaíso', 'Alimentos'),

-- Proveedores de Limpieza (IDs 9 y 10)
('Insumos Pro Chile', 'info@insumosprochile.cl', '+56944445559', 'Metropolitana', 'Limpieza'),
('Químicos Industriales Limpito', 'contacto@limpitopro.cl', '+56227778810', 'Metropolitana', 'Limpieza');
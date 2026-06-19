-- Inserción de Gerentes
INSERT INTO gerentes (nombre, apellido, email, fono, salario, bono, nivel_mando)
VALUES
-- 3 Gerentes Junior (1 local cada uno = 3 locales) | Bonos entre 1 y 10.000
('Carlos', 'Pérez', 'cperez.jun@tastyfeast.cl', '+56911111111', 1200000, 8500, 'Junior'),
('Ana', 'López', 'alopez.jun@tastyfeast.com', '+56922222222', 1200000, 9000, 'Junior'),
('Carlos', 'Soto', 'csoto.jun@tastyfeast.net', '+56933333333', 1200000, 9500, 'Junior'),

-- 2 Gerentes Senior (2 locales cada uno = 4 locales) | Bonos entre 10.001 y 50.000
('Ana', 'Rojas', 'arojas.sen@tastyfeast.cl', '+56944444444', 1800000, 45000, 'Senior'),
('Luis', 'Silva', 'lsilva.sen@tastyfeast.com', '+56955555555', 1800000, 48000, 'Senior'),

-- 1 Gerente Regional (3 locales en total = 3 locales) | Bonos entre 50.001 y 100.000
('Luis', 'Contreras', 'lcontreras.reg@tastyfeast.net', '+56966666666', 2500000, 95000, 'Regional');
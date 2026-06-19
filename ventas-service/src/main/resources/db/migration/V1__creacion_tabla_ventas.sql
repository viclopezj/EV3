CREATE TABLE ventas(
    id bigint auto_increment primary key,
    id_local bigint not null,
    fecha_reporte date not null,
    venta_minimas int not null,
    venta_maximas int not null,
    venta_promedio int not null
);
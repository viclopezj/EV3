CREATE TABLE equipos(
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    cod_marca varchar(10) not null,
    fecha_compra date not null,
    ultima_mantencion date not null,
    proxima_mantencion date not null,
    id_proveedor bigint not null,
    CONSTRAINT fk_equipo_marca
        FOREIGN KEY (cod_marca)
            REFERENCES marcas (codigo)
);
CREATE TABLE proveedores(
    id bigint auto_increment primary key,
    nombre_empresa varchar(100) not null,
    email varchar(100) not null,
    fono varchar(15) not null,
    region varchar(50) not null,
    tipo_proveedor varchar(50) not null
);
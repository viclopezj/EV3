CREATE TABLE inventarios(
    id bigint auto_increment primary key,
    nombre_insumo varchar(100) not null,
    cantidad_actual int not null,
    id_local bigint not null,
    id_proveedor bigint not null
);
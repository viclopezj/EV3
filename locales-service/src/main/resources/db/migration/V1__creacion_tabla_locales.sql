CREATE TABLE locales(
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    comuna varchar(50) not null,
    direccion varchar(100) not null,
    hora_apertura time not null,
    hora_cierre time not null,
    id_gerente bigint not null
);
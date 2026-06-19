CREATE TABLE empleados(
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    email varchar(100) not null,
    fono varchar(15) not null,
    salario int not null,
    id_local bigint not null
);
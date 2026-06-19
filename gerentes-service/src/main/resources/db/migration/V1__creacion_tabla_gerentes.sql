CREATE TABLE gerentes(
    id bigint auto_increment primary key,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    email varchar(100) not null,
    fono varchar(15) not null,
    salario int not null,
    bono int not null,
    nivel_mando varchar(50) not null
);
CREATE TABLE menus
(
    id            bigint auto_increment primary key,
    nombre        varchar(50) not null,
    precio        int         not null,
    cod_categoria varchar(50) not null,
    id_local      bigint      not null,
    CONSTRAINT fk_menu_categoria
        FOREIGN KEY (cod_categoria)
            REFERENCES categorias (codigo)
);
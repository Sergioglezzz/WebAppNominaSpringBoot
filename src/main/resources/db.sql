create database empresa;
use empresa;


CREATE TABLE empleados (
    dni VARCHAR(9) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    sexo CHAR(1),
    categoria INT(2) NOT NULL,
    anyos INT(2),
    CONSTRAINT pk_e PRIMARY KEY (dni),
    CONSTRAINT sex_chck CHECK (sexo = 'M' OR sexo = 'F')
);

CREATE TABLE categorias (
    categoria INT(2) NOT NULL,
    sueldo DECIMAL(8,2),
    CONSTRAINT pk_c PRIMARY KEY (categoria)  -- Ahora 'categoria' es PK en esta tabla
);

CREATE TABLE nominas (
    dni VARCHAR(9) NOT NULL,  
    categoria INT(2) NOT NULL,
    sueldofinal decimal(8,2),
    CONSTRAINT pk_n PRIMARY KEY (dni),
    CONSTRAINT fk_e FOREIGN KEY (dni) REFERENCES empleados(dni),  -- Relacionamos con el empleado
    CONSTRAINT fk_c FOREIGN KEY (categoria) REFERENCES categorias(categoria)  -- Relacionamos con la tabla categorias
);


insert into empleados values('32000032G', 'James Cosling', 'M', 9, 7);
insert into empleados values('32000031R', 'Ada Lovelace', 'F', 1, 1);
INSERT INTO empleados VALUES('32000033H', 'Linus Torvalds', 'M', 8, 15);
INSERT INTO empleados VALUES('32000034J', 'Grace Hopper', 'F', 7, 20);
INSERT INTO empleados VALUES('32000035K', 'Margaret Hamilton', 'F', 6, 10);
INSERT INTO empleados VALUES('32000036L', 'Alan Turing', 'M', 5, 5);
INSERT INTO empleados VALUES('32000037M', 'Dennis Ritchie', 'M', 4, 12);



insert into categorias values(1, 50000);
insert into categorias values(2, 70000);
insert into categorias values(3, 90000);
insert into categorias values(4, 110000);
insert into categorias values(5, 130000);
insert into categorias values(6, 150000);
insert into categorias values(7, 170000);
insert into categorias values(8, 190000);
insert into categorias values(9, 210000);
insert into categorias values(10, 230000);


INSERT INTO nominas (dni, categoria, sueldofinal)
SELECT e.dni, e.categoria, c.sueldo + (5000 * e.anyos) AS sueldofinal
FROM empleados e
JOIN categorias c ON e.categoria = c.categoria;
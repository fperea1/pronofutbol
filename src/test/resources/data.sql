insert into roles(nombre) values ('SUPERUSUARIO');
insert into roles(nombre) values ('CONSULTOR');

insert into usuarios (nombre, username, password, email, fecha_alta, fecha_desactivacion, activo) 
values ('Francisco Perea', 'administrador', '$2a$10$v.P/TUS1qUE/YcXYUH1OUuB2POJ4/oKZZFjaAP4TlSd8Oh32IPyOa', 
	'fran.perea.74@gmail.com', NOW(), null, 1);
	
insert into roles_usuario(id_rol, id_usuario) values (1, 1);
insert into roles_usuario(id_rol, id_usuario)  values (2, 1);

insert into configuracion (nombre, valor) values ('mailFrom', 'admin.pronofutbol@correo.es');
insert into configuracion (nombre, valor) values ('mailAdministracion', 'fran.perea.74@gmail.com');
insert into configuracion (nombre, valor) values ('mailAdministracionCC', '-');
insert into configuracion (nombre, valor) values ('mailAdministracionBCC', '-');

insert into paises(nombre) values ('ESPAÑA'); --1
insert into paises(nombre) values ('FRANCIA'); --2
insert into paises(nombre) values ('INGLATERRA'); --3
insert into paises(nombre) values ('ITALIA'); --4
insert into paises(nombre) values ('ALEMANIA'); --5
insert into paises(nombre) values ('CHINA'); --6

insert into ligas(nombre, id_pais) values ('Liga Santander', 1);
insert into ligas(nombre, id_pais) values ('Liga SmartBank', 1);
insert into ligas(nombre, id_pais) values ('Ligue 1', 2);
insert into ligas(nombre, id_pais) values ('Premier League', 3);
insert into ligas(nombre, id_pais) values ('Serie A', 4);
insert into ligas(nombre, id_pais) values ('Bundesliga', 5);
insert into ligas(nombre, id_pais) values ('Superliga de China', 6);

insert into tipo_sorteo(nombre, num_dobles, num_triples) values ('7 DOBLES', '7', '0');
insert into tipo_sorteo(nombre, num_dobles, num_triples) values ('4 TRIPLES', '0', '4');
insert into tipo_sorteo(nombre, num_dobles, num_triples) values ('4 T / 7 D', '7', '4');

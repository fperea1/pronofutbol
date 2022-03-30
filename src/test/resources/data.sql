insert into roles(nombre) values ('SUPERUSUARIO');
insert into roles(nombre) values ('ADMINISTRADOR');

insert into usuarios (nombre, username, password, email, fecha_alta, fecha_desactivacion, activo) 
values ('Francisco Perea', 'administrador', '$2a$10$v.P/TUS1qUE/YcXYUH1OUuB2POJ4/oKZZFjaAP4TlSd8Oh32IPyOa', 
	'administrador@ezentis.com', NOW(), null, 1);
	
insert into roles_usuario(id_rol, id_usuario) values (1, 1);
insert into roles_usuario(id_rol, id_usuario)  values (2, 1);

insert into configuracion (nombre, valor) values ('mailFrom', 'mail.app@correo.es');
insert into configuracion (nombre, valor) values ('mailAdministracion', 'fran.perea.74@gmail.com');
insert into configuracion (nombre, valor) values ('mailAdministracionCC', '-');
insert into configuracion (nombre, valor) values ('mailAdministracionBCC', '-');
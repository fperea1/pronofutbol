-- He creado con sql server management la bd pronofutboldb

CREATE LOGIN pronofutboldb   
    WITH PASSWORD = 'PASSword?1234Prono';  
GO  

-- Creates a database user for the login created above.  
CREATE USER pronofutboldb FOR LOGIN pronofutboldb;  
GO  

-- Después he puesto en seguridad, el inicio de sesión pronofutboldb como owner

drop table configuracion
drop table logs
drop table roles_usuario
drop table roles
drop table usuarios

CREATE TABLE "configuracion" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(50) NOT NULL CHECK (LEN(nombre) >= 1) UNIQUE,  
	"valor" NVARCHAR(500)
)

CREATE TABLE "logs" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"username" NVARCHAR(50) NOT NULL CHECK (LEN(username) >= 5),  
	"entidad" NVARCHAR(50) NOT NULL CHECK (LEN(entidad) >= 1), 
	"accion" NVARCHAR(50) NOT NULL CHECK (LEN(accion) >= 1), 
	"observaciones" NVARCHAR(500) NOT NULL CHECK (LEN(observaciones) >= 1), 
	"fecha" DATETIME NOT NULL
)

CREATE TABLE "roles" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(50) NOT NULL UNIQUE
)

insert into roles values ('SUPERUSUARIO');
insert into roles values ('CONSULTOR');


CREATE TABLE "usuarios" (
	"id" INT PRIMARY KEY IDENTITY (1,1),   
	"nombre" NVARCHAR(50) NOT NULL CHECK (LEN(nombre) >= 1), 
	"username" NVARCHAR(50) NOT NULL CHECK (LEN(username) >= 5) UNIQUE, 
	"password" NVARCHAR(100) NOT NULL CHECK (LEN(password) >= 10),  
	"email" NVARCHAR(100) NOT NULL, 
	"fecha_alta" DATETIME NOT NULL,
	"fecha_desactivacion" DATETIME,
	"activo" BIT not null,
);

CREATE TABLE "roles_usuario" (
	"id_rol" INT NOT NULL,
	"id_usuario" INT NOT NULL
)

ALTER TABLE roles_usuario add foreign key (id_rol) references roles(id);
ALTER TABLE roles_usuario add foreign key (id_usuario) references usuarios(id);

insert into usuarios values ('Francisco Perea', 'administrador', '$2a$10$v.P/TUS1qUE/YcXYUH1OUuB2POJ4/oKZZFjaAP4TlSd8Oh32IPyOa', 
	'fran.perea.74@gmail.com', GETDATE(), null, 1); -- Administrador01
	
insert into roles_usuario values (1, 1);

insert into configuracion ("nombre", "valor") values ('mailFrom', 'admin.pronofutbol@correo.es');
insert into configuracion ("nombre", "valor") values ('mailAdministracion', 'fran.perea.74@gmail.com');
insert into configuracion ("nombre", "valor") values ('mailAdministracionCC', '-');
insert into configuracion ("nombre", "valor") values ('mailAdministracionBCC', '-');


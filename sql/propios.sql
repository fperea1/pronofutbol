
drop table pronosticos;
drop table tipo_sorteo; --
ALTER TABLE partidos DROP CONSTRAINT FK__partidos__id_por__6FE99F9F; -- cambiar nombre de constraint
drop table partidos;
drop table porcentajes;
drop table enfrentamientos;
drop table equipos;
drop table arbitros; --
drop table jornadas; --
drop table quinielas; --
drop table ligas;  --
drop table paises; --

CREATE TABLE "paises" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(50) NOT NULL UNIQUE
)

CREATE TABLE "ligas" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(100) NOT NULL UNIQUE,
	"id_pais" INT NOT NULL
) 
ALTER TABLE ligas add foreign key (id_pais) references paises(id);

CREATE TABLE "quinielas" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"numero" INT NOT NULL,
	"nombre" NVARCHAR(50) NOT NULL UNIQUE,
	"fecha" DATETIME NOT NULL,
	"id_liga" INT NOT NULL,
	"actualizada" BIT NOT NULL
) 
ALTER TABLE quinielas add foreign key (id_liga) references ligas(id);

CREATE TABLE "jornadas" (
	"id" INT PRIMARY KEY IDENTITY (1,1),
	"nombre" NVARCHAR(50) NOT NULL UNIQUE
) 

CREATE TABLE "arbitros" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(100) NOT NULL UNIQUE,
	"ganados_local" INT NOT NULL,
	"empatados" INT NOT NULL,
	"ganados_visitante" INT NOT NULL,
	"id_liga" INT NOT NULL
) 
ALTER TABLE arbitros add foreign key (id_liga) references ligas(id);

CREATE TABLE "equipos" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(100) NOT NULL UNIQUE,
	"puntos" INT DEFAULT 0,
	"ganados_local" INT DEFAULT 0,
	"empatados_local" INT DEFAULT 0,
	"perdidos_local" INT DEFAULT 0,
	"ganados_visitante" INT DEFAULT 0,
	"empatados_visitante" INT DEFAULT 0,
	"perdidos_visitante" INT DEFAULT 0,
	"result_ultimo_partido" NVARCHAR(1),
	"result_penultimo_partido" NVARCHAR(1),
	"id_liga" INT NOT NULL
)
ALTER TABLE equipos add foreign key (id_liga) references ligas(id);

CREATE TABLE "enfrentamientos" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"resultado" NVARCHAR(1) NOT  NULL,
	"fecha" DATETIME NOT NULL,
	"id_equipo_local" INT NOT NULL,
	"id_equipo_visitante" INT NOT NULL
)
ALTER TABLE enfrentamientos add foreign key (id_equipo_local) references equipos(id);
ALTER TABLE enfrentamientos add foreign key (id_equipo_visitante) references equipos(id);

CREATE TABLE "porcentajes" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"porctj_uno" DECIMAL(5,2) NOT NULL,
	"porctj_equis" DECIMAL(5,2) NOT NULL,
	"porctj_dos" DECIMAL(5,2) NOT NULL,
	"id_partido" INT NOT NULL,
	"id_usuario" INT NOT NULL
)

CREATE TABLE "partidos" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"resultado" NVARCHAR(1),
	"de_quiniela" BIT NOT NULL DEFAULT 0,
	"posicion" INT,
	"comentarios" NVARCHAR(1000),
	"id_equipo_local" INT NOT NULL,
	"id_equipo_visitante" INT NOT NULL,
	"id_quiniela" INT NOT NULL,
	"id_porcentaje" INT NOT NULL,
	"id_arbitro" INT NOT NULL
)
ALTER TABLE partidos add foreign key (id_equipo_local) references equipos(id);
ALTER TABLE partidos add foreign key (id_equipo_visitante) references equipos(id);
ALTER TABLE partidos add foreign key (id_quiniela) references quinielas(id);
ALTER TABLE partidos add foreign key (id_porcentaje) references porcentajes(id);
ALTER TABLE partidos add foreign key (id_arbitro) references arbitros(id);

ALTER TABLE porcentajes add foreign key (id_partido) references partidos(id);
ALTER TABLE porcentajes add foreign key (id_usuario) references usuarios(id);

CREATE TABLE "tipo_sorteo" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"nombre" NVARCHAR(50) NOT NULL UNIQUE,
	"num_dobles" INT NOT NULL DEFAULT 0,
	"num_triples" INT NOT NULL DEFAULT 0
) 

CREATE TABLE "pronosticos" (
	"id" INT PRIMARY KEY IDENTITY (1,1),  
	"resultado" NVARCHAR(1) NOT NULL,
	"id_partido" INT NOT NULL,
	"id_tipo_sorteo" INT NOT NULL,
	"id_usuario" INT NOT NULL
) 
ALTER TABLE pronosticos add foreign key (id_partido) references partidos(id);
ALTER TABLE pronosticos add foreign key (id_tipo_sorteo) references tipo_sorteo(id);
ALTER TABLE pronosticos add foreign key (id_usuario) references usuarios(id);
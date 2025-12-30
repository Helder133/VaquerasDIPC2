create database vaqueras_ipc2;

use vaqueras_ipc2;

CREATE USER 'vaqueras'@'localhost' IDENTIFIED BY 'Vaqueras22025*';

GRANT SELECT, INSERT, UPDATE, DELETE ON vaqueras_ipc2.* TO 'vaqueras'@'localhost';

create table if not exists empresa (
	empresa_id int not null auto_increment primary key,
	nombre varchar(200) not null,
	descripcion varchar(250),
	comision_negociada float,
	estado bool default 1,
	estado_comentario bool default 1
);

create table if not exists usuario (
	usuario_id int not null auto_increment primary key,
	nombre varchar(200) not null,
	email varchar(200) not null unique,
	contraseña varchar(100) not null,
	fecha_nacimiento date not null,
	rol enum('admin_sistema','manager','admin_empresa','comun') not null default 'comun',
	telefono char(8),
	avatar MEDIUMBLOB,
	pais varchar(200),
	empresa_id int,
	constraint fk_empresa foreign key (empresa_id) references empresa (empresa_id)
);

create table if not exists cartera_digital (
	cartera_id int not null auto_increment primary key,
	usuario_id int not null,
	saldo float not null default 0,
	constraint fk_usuario foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists historial_cartera (
	historial_id int not null auto_increment primary key,
	cartera_id int not null,
	transaccion enum ('deposito','pago'),
	fecha date,
	monto float not null,
	constraint fk_cartera foreign key (cartera_id) references cartera_digital (cartera_id)
);
alter table videojuego add column clasificacion enum ('E','T','M') not null default 'E';
create table if not exists videojuego (
	videojuego_id int not null auto_increment primary key,
	empresa_id int not null,
	nombre varchar(200) not null,
	precio float not null,
	recurso_minimo varchar(250) not null,
	clasificacion enum ('E','T','M') not null default 'E',
	estado bool not null,
	fecha date not null,
	imagen MEDIUMBLOB,
	descripcion varchar(200),
	constraint fk_empresa2 foreign key (empresa_id) references empresa (empresa_id) 
);
select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id;
create table if not exists multimedia (
	multimedia_id int not null auto_increment primary key,
	videojuego_id int not null,
	imagen MEDIUMBLOB not null,
	constraint fk_videojuego foreign key (videojuego_id) references videojuego (videojuego_id)
);

create table if not exists categoria (
	categoria_id int not null auto_increment primary key,
	nombre varchar(150) not null unique ,
	descripcion varchar(200) not null
);

create table if not exists categoria_videojuego (
	videojuego_id int not null,
	categoria_id int not null,
	constraint pk_categoria_videojuego primary key (videojuego_id, categoria_id),
	constraint fk_videojuego2 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_categoria foreign key (categoria_id) references categoria (categoria_id)
);

select  c.nombre,
	cv.videojuego_id,
	cv.categoria_id
from categoria c join categoria_videojuego cv on c.categoria_id = cv.categoria_id
where cv.videojuego_id = ?;

create table if not exists comprar_videojuego (
	videojuego_id int not null,
	usuario_id int not null,
	fecha date,
	constraint pk_comprar primary key (videojuego_id, usuario_id),
	constraint fk_videojuego3 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario2 foreign key (usuario_id) references usuario (usuario_id)
);

select c.fecha, c.videojuego_id, c.usuario_id, v.empresa_id, v.nombre as nombre_videojuego, v.imagen, v.descripcion, e.nombre as nombre_empresa from comprar_videojuego c join videojuego v on c.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id;

create table if not exists biblioteca_videojuego (
	biblioteca_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	fecha date not null,
	estado_instalacion bool not null default 0,
	constraint fk_videojuego4 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario3 foreign key (usuario_id) references usuario (usuario_id)
);

select b.biblioteca_id, b.fecha, b.estado_instalacion, b.videojuego_id, b.usuario_id, v.empresa_id, v.nombre as nombre_videojuego, v.imagen, v.descripcion, e.nombre as nombre_empresa from biblioteca_videojuego b join videojuego v on b.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id where b.usuario_id = ?;  

create table if not exists comentario_videojuego (
	comentario_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	comentario varchar(250) not null,
	visible bool not null default 1,
	fecha_hora datetime not null,
	comentario_padre int,
	constraint fk_videojuego5 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario4 foreign key (usuario_id) references usuario (usuario_id),
	ALTER TABLE comentario_videojuego ADD CONSTRAINT fk_comentario_padre FOREIGN KEY (comentario_padre) REFERENCES comentario_videojuego (comentario_id) ON DELETE CASCADE
);

select c.*, u.nombre from comentario_videojuego c join usuario u on c.usuario_id = u.usuario_id where c.videojuego_id = ?;

create table if not exists calificacion_videojuego (
	calificacion_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	calificacion float not null check(calificacion >= 0 and calificacion <= 5),
	constraint fk_videojuego6 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario5 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists bloqueo_comentario_usuario (
	bloqueo_id int not null auto_increment primary key,
	usuario_id int not null,
	empresa_id int not null,
	estado bool not null default 1,
	fecha datetime not null,
	constraint fk_usuario6 foreign key (usuario_id) references usuario (usuario_id),
	constraint fk_empresa3 foreign key (empresa_id) references empresa (empresa_id)
);

create table if not exists grupo_familiar (
	grupo_id int not null auto_increment primary key,
	usuario_id int not null,
	nombre varchar(250) not null,
	fecha date not null,
	constraint fk_usuario7 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists miembro_grupo (
	grupo_id int not null,
	usuario_id int not null,
	fecha date not null,
	constraint pk_miembro primary key (grupo_id, usuario_id),
	constraint fk_usuario8 foreign key (usuario_id) references usuario (usuario_id),
	constraint fk_grupo foreign key (grupo_id) references grupo_familiar (grupo_id)
);

create table if not exists videojuego_prestado (
	prestamo_id int not null auto_increment primary key,
	videojuego_id int not null,
	usuario_id int not null,
	estado_instalado bool not null,
	fecha_instalacion date not null,
	fecha_desinstalar date not null,
	fecha_inicio date,
	fecha_fin date,
	constraint fk_videojuego7 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario9 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists configuracion_sistema (
	configuracion_id int not null auto_increment primary key,
	porcentaje_ganancia float not null default 15.00,
	tamaño_grupo int not null default 6
);

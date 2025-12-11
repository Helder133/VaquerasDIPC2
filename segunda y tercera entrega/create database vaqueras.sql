create database vaqueras_ipc2;

use vaqueras_ipc2;

create table if not exists empresa (
	empresa_id int not null auto_increment primary key,
	nombre varchar(200) not null,
	descripcion varchar(250),
	comision_negociada float
);

create table if not exists usuario (
	usuario_id int not null auto_increment primary key,
	nombre varchar(200) not null,
	email varchar(200) not null unique,
	contraseña varchar(100) not null,
	fecha_nacimiento date not null,
	numero char(8),
	avatar varchar(200),
	pais varchar(200),
	rol enum('admin_sistema','manager','admin_empresa','comun'),
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
	constraint fk_cartera foreign key (cartera_id) references cartera_digital (cartera_id)
);

create table if not exists videojuego (
	videojuego_id int not null auto_increment primary key,
	empresa_id int not null,
	nombre varchar(200) not null,
	precio float not null,
	recurso_minimo varchar(250) not null,
	edad_minima int not null,
	estado bool not null,
	fecha date not null,
	imagen varchar(200),
	descripcion varchar(200),
	constraint fk_empresa2 foreign key (empresa_id) references empresa (empresa_id) 
);

create table if not exists multimedia (
	multimedia_id int not null auto_increment primary key,
	videojuego_id int not null,
	tipo enum ('imagen','video') not null,
	url varchar(200) not null,
	constraint fk_videojuego foreign key (videojuego_id) references videojuego (videojuego_id)
);

create table if not exists categoria (
	categoria_id int not null auto_increment primary key,
	nombre varchar(150) not null,
	descripcion varchar(200) not null
);

create table if not exists categoria_videojuego (
	videojuego_id int not null,
	categoria_id int not null,
	constraint pk_categoria_videojuego primary key (videojuego_id, categoria_id)
);

create table if not exists comprar_videojuego (
	videojuego_id int not null,
	usuario_id int not null,
	fecha date,
	constraint pk_comprar primary key (videojuego_id, usuario_id),
	constraint fk_videojuego2 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario2 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists biblioteca_juego (
	biblioteca_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	fecha date not null,
	estado_instalacion bool not null default 0,
	constraint fk_videojuego3 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario3 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists comentario_videojuego (
	comentario_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	comentario varchar(250) not null,
	visible bool not null default 1,
	fecha_hora datetime not null,
	comentario_padre int,
	constraint fk_videojuego4 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario4 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists calificacion_videojuego (
	calificacion_id int not null auto_increment primary key,
	usuario_id int not null,
	videojuego_id int not null,
	calificacion float not null,
	descripcion varchar(250),
	constraint fk_videojuego5 foreign key (videojuego_id) references videojuego (videojuego_id),
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
	constraint fk_videojuego6 foreign key (videojuego_id) references videojuego (videojuego_id),
	constraint fk_usuario9 foreign key (usuario_id) references usuario (usuario_id)
);

create table if not exists configuracion_sistema (
	configuracion_id int not null auto_increment primary key,
	porcentaje_ganancia float not null default 15.00,
	tamaño_grupo int not null default 6
);



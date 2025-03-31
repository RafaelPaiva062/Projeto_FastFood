drop table if exists  fastfoods;
create database fastfoods;
use fastfoods;
CREATE TABLE produto (
    id_produtos INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL,
    preco REAL NOT NULL,
    validade DATETIME NOT NULL
);

create table lanches(
id_lanches int primary key auto_increment,
id_produtos int not null,
nome varchar(45)not null,
preco real not null,
foreign key(id_produtos)references produto(id_produtos)
);
create table bebidas(
id_bebidass int primary key auto_increment,
id_produtos int not null,
nome varchar(45)not null,
preco real not null,
foreign key(id_produtos)references produto(id_produtos)
);
create table cardapio(
id_cardapio int primary key auto_increment,
id_bebidas int not null,
id_lanches int not null,
nome varchar(45) not null,
preco real not null,
foreign key(id_bebidas)references bebidas(id_bebidass),
foreign key(id_lanches)references lanches(id_lanches)
);
create table pedidos(
id_pedidos int primary key auto_increment,
nome_cliente varchar(45) not null,
id_cardapio int not null,
nome_pedido varchar(45) not null,
valorConta float not null,
data_pedido datetime not null,
foreign key(id_cardapio) references cardapio(id_cardapio)
);
create table funcionarios (
id_funcionarios int primary key auto_increment,
cargo varchar(100) not null,
nome varchar(45) not null,
login varchar(50) not null,
senha varchar(50) not null,
id_pedidos int not null,
foreign key(id_pedidos) references pedidos(id_pedidos)
);

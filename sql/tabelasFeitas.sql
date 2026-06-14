CREATE DATABASE alexandria;
USE alexandria;

CREATE TABLE usuarios (
id_usuario int primary key auto_increment,
nome varchar(100),
cpf char(11) unique,
email varchar(100) unique,
senha varchar(30),
tipo ENUM('ALUNO', 'GERENTE', 'BIBLIOTECARIO', 'ESTAGIARIO')
);

CREATE TABLE enderecos (
id_endereço int primary key auto_increment,
logradouro varchar(100),
bairro varchar(100),
cidade varchar(100),
uf varchar(100),
id_usuario_fk int,
CONSTRAINT id_usuario_fk foreign key (id_usuario_fk) references usuarios(id_usuario)
);

CREATE TABLE livros (
id_livro int primary key auto_increment,
titulo varchar(100),
autor varchar(100),
isbn varchar(100) unique,
preco_custo decimal(10,2),
quantidade_estoque int,
status varchar(100)
);

CREATE TABLE emprestimos (
id_emprestimo int primary key auto_increment,
id_usuario_fk int,
id_livro_fk int,
data_saida DATETIME DEFAULT CURRENT_TIMESTAMP,
data_prevista DATE,
data_devolucao DATETIME,
CONSTRAINT usuario_fk foreign key (id_usuario_fk) references usuarios(id_usuario),
CONSTRAINT id_livro_fk foreign key (id_livro_fk) references livros(id_livro)
);

CREATE TABLE multas (
id_multa int primary key auto_increment,
id_emprestimo_fk int,
valor decimal(10,2),
pago boolean DEFAULT false
);

CREATE TABLE log_auditoria(
id_log int primary key auto_increment,
tabela_afetada varchar(30),
acao varchar(100),
usuario_responsavel varchar(100),
dados_antigos TEXT,
data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
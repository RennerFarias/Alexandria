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

USE alexandria;

DELIMITER $$

CREATE PROCEDURE sp_transacao_emprestimo(
in p_id_usuario int,
in p_id_livro int
)
BEGIN
declare v_pendencias int default 0;
declare v_estoque int default 0;

declare exit handler for sqlexception
begin
rollback;
resignal;
end;

start transaction;

select count(*) into v_pendencias
from multas m
inner join emprestimos e on m.id_emprestimo_fk = e.id_emprestimo
where e.id_usuario_fk = p_id_usuario and m.pago = 0;

if v_pendencias > 0 then
signal sqlstate '45000'
set message_text = 'Operação negada: usuário possui multas pendentes.';
end if;

select quantidade_estoque into v_estoque
from livros where id_livro = p_id_livro;

if v_estoque <= 0 then
signal sqlstate '45000'
set message_text = 'Operação negada: livro sem estoque disponível.';
end if;

insert into emprestimos (id_usuario_fk, id_livro_fk, data_saida, data_prevista, data_devolucao)
values (p_id_usuario, p_id_livro, now(), date_add(curdate(), interval 7 day), null);

update livros
set quantidade_estoque = quantidade_estoque - 1
where id_livro = p_id_livro;

commit;
END$$


CREATE PROCEDURE sp_renovar_emprestimo(
in p_id_emprestimo int
)
BEGIN
declare v_id_livro int;
declare v_data_prevista date;
declare v_data_devolucao datetime;
declare v_reservas int default 0;

declare exit handler for sqlexception
begin
rollback;
resignal;
end;

start transaction;

select id_livro_fk, data_prevista, data_devolucao
into v_id_livro, v_data_prevista, v_data_devolucao
from emprestimos
where id_emprestimo = p_id_emprestimo;

if v_data_devolucao is not null then
signal sqlstate '45000'
set message_text = 'Operação negada: empréstimo já encerrado.';
end if;

select count(*) into v_reservas
from emprestimos
where id_livro_fk = v_id_livro
and data_devolucao is null
and id_emprestimo <> p_id_emprestimo;

if v_reservas > 0 then
signal sqlstate '45000'
set message_text = 'Renovação negada: livro reservado por outro usuário.';
end if;

update emprestimos
set data_prevista = date_add(v_data_prevista, interval 7 day)
where id_emprestimo = p_id_emprestimo;

commit;
END$$


CREATE PROCEDURE sp_calcular_multa(
in p_id_emprestimo int,
out p_valor_multa decimal(10,2)
)
BEGIN
declare v_data_prevista date;
declare v_data_devolucao datetime;
declare v_data_referencia date;
declare v_dias_atraso int default 0;

select data_prevista, data_devolucao
into v_data_prevista, v_data_devolucao
from emprestimos
where id_emprestimo = p_id_emprestimo;

if v_data_devolucao is not null then
set v_data_referencia = date(v_data_devolucao);
else
set v_data_referencia = curdate();
end if;

set v_dias_atraso = datediff(v_data_referencia, v_data_prevista);

if v_dias_atraso <= 0 then
set p_valor_multa = 0.00;
else
set p_valor_multa = v_dias_atraso * 2.00;

insert into multas (id_emprestimo_fk, valor, pago)
values (p_id_emprestimo, p_valor_multa, 0);
end if;
END$$


CREATE PROCEDURE sp_transacao_cadastro_completo(
in p_nome varchar(100),
in p_cpf char(11),
in p_email varchar(100),
in p_senha varchar(30),
in p_tipo varchar(20),
in p_logradouro varchar(100),
in p_bairro varchar(100),
in p_cidade varchar(100),
in p_uf varchar(100)
)
BEGIN
declare v_id_usuario int;

declare exit handler for sqlexception
begin
rollback;
resignal;
end;

start transaction;

insert into usuarios (nome, cpf, email, senha, tipo)
values (p_nome, p_cpf, p_email, p_senha, p_tipo);

set v_id_usuario = last_insert_id();

insert into enderecos (logradouro, bairro, cidade, uf, id_usuario_fk)
values (p_logradouro, p_bairro, p_cidade, p_uf, v_id_usuario);

commit;
END$$

DELIMITER ;
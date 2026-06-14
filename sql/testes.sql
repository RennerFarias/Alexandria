USE alexandria;

-- cadastro completo

CALL sp_transacao_cadastro_completo(
'Jose',
'11111111111',
'jose@email.com',
'123456',
'ALUNO',
'Rua A',
'Centro',
'Campina Grande',
'PB'
);

-- Livro

INSERT INTO livros
(titulo, autor, isbn, preco_custo, quantidade_estoque, status)
VALUES
(
'Dom Casmurro',
'Machado de Assis',
'978000000001',
20.00,
5,
'DISPONIVEL'
);

-- empréstimo

CALL sp_transacao_emprestimo(1,1);

-- consultas

SELECT * FROM usuarios;
SELECT * FROM enderecos;
SELECT * FROM livros;
SELECT * FROM emprestimos;

SELECT * FROM vw_livros_disponiveis;
SELECT * FROM vw_emprestimos_ativos;


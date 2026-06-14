USE alexandria;

CREATE OR REPLACE VIEW vw_livros_disponiveis AS
SELECT
    id_livro,
    titulo,
    autor,
    quantidade_estoque,
    status
FROM livros
WHERE quantidade_estoque > 0;

CREATE OR REPLACE VIEW vw_emprestimos_ativos AS
SELECT
    e.id_emprestimo,
    u.nome AS usuario,
    l.titulo AS livro,
    e.data_saida,
    e.data_prevista
FROM emprestimos e
INNER JOIN usuarios u
    ON e.id_usuario_fk = u.id_usuario
INNER JOIN livros l
    ON e.id_livro_fk = l.id_livro
WHERE e.data_devolucao IS NULL;
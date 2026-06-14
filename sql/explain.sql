USE alexandria;

EXPLAIN
SELECT *
FROM livros
WHERE titulo = 'Dom Casmurro';

EXPLAIN
SELECT *
FROM livros
WHERE autor = 'Machado de Assis';

EXPLAIN
SELECT *
FROM emprestimos
WHERE id_usuario_fk = 1;
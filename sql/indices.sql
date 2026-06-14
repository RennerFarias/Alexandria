USE alexandria;

CREATE INDEX idx_livro_titulo
ON livros(titulo);

CREATE INDEX idx_livro_autor
ON livros(autor);

CREATE INDEX idx_emprestimo_usuario
ON emprestimos(id_usuario_fk);

CREATE INDEX idx_emprestimo_livro
ON emprestimos(id_livro_fk);
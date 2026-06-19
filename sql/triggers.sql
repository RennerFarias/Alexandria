DELIMITER $$

CREATE TRIGGER trg_trava_horario_comercial_insert_emprestimos
BEFORE INSERT on emprestimos
FOR EACH ROW BEGIN
	IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_trava_horario_comercial_update_emprestimos
BEFORE UPDATE on emprestimos
FOR EACH ROW BEGIN
	IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_trava_horario_comercial_insert_usuarios
BEFORE INSERT on usuarios
FOR EACH ROW BEGIN
	 IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_trava_horario_comercial_update_usuarios
BEFORE UPDATE on usuarios
FOR EACH ROW BEGIN
	IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_trava_horario_comercial_insert_livros
BEFORE INSERT on livros
FOR EACH ROW BEGIN
	IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_trava_horario_comercial_update_livros
BEFORE UPDATE on livros
FOR EACH ROW BEGIN
	IF CURTIME() < '08:00:00' OR CURTIME() > '18:00:00' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: Estamos fora do horário de serviço. Funcionamos apenas entre 8 e 18 hrs';
	END IF;
END $$

CREATE TRIGGER trg_auditoria_delecao_livros
AFTER DELETE on livros
FOR EACH ROW BEGIN
INSERT INTO log_auditoria (tabela_afetada, acao, usuario_responsavel, dados_antigos)
VALUES (
			'Livros',
            'DELETE LIVRO',
            current_user(),
            CONCAT('ID do livro: ', old.id_livro, '. Título: ', old.titulo, '. Autor: ', old.autor, '. ISBN: ', old.isbn)
            );
END $$

CREATE TRIGGER trg_limite_emprestimos
BEFORE INSERT on emprestimos
FOR EACH ROW BEGIN
	DECLARE quantidade_emprestimos_do_aluno INT;
    DECLARE tipo_usuario varchar(50);
    SELECT COUNT(*) INTO quantidade_emprestimos_do_aluno FROM emprestimos WHERE id_usuario_fk = new.id_usuario_fk AND data_devolucao IS NULL;
    SELECT tipo INTO tipo_usuario FROM usuarios WHERE id_usuario = NEW.id_usuario_fk;
    IF quantidade_emprestimos_do_aluno >= 3 AND tipo_usuario = 'ALUNO' THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: O aluno alcançou o limite máximo de livros emprestados simultaneamente';
    END IF;
END $$



-- iMPEDIR QUE O ESTOQUE FIQUE NEGATIVO
CREATE TRIGGER trg_preventiva_estoque
BEFORE UPDATE ON livros
FOR EACH ROW
BEGIN
    IF NEW.quantidade_estoque < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ERRO: O estoque não pode ser negativo.';
    END IF;
END $$

DELIMITER ;
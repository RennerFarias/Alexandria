USE alexandria;

CREATE USER IF NOT EXISTS 'gerente'@'localhost'
IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES
ON alexandria.*
TO 'gerente'@'localhost';

CREATE USER IF NOT EXISTS 'bibliotecario'@'localhost'
IDENTIFIED BY '123456';

GRANT SELECT, INSERT, UPDATE
ON alexandria.*
TO 'bibliotecario'@'localhost';

CREATE USER IF NOT EXISTS 'estagiario'@'localhost'
IDENTIFIED BY '123456';

GRANT SELECT
ON alexandria.*
TO 'estagiario'@'localhost';

FLUSH PRIVILEGES;

SHOW GRANTS FOR 'gerente'@'localhost';
SHOW GRANTS FOR 'bibliotecario'@'localhost';
SHOW GRANTS FOR 'estagiario'@'localhost';
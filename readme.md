# 📚 Alexandria - Sistema de Gerenciamento de Biblioteca Universitária

> Não é só organização. É tudo unificado para simplificar sua vida.

O Alexandria é um sistema de gerenciamento de bibliotecas universitárias desenvolvido em Java utilizando JDBC e MySQL. O projeto tem como objetivo centralizar e automatizar processos comuns de bibliotecas, como cadastro de usuários, controle de empréstimos, renovação de livros, aplicação de multas e consultas gerenciais.

---

## 🎯 Objetivo

O sistema foi desenvolvido para auxiliar no gerenciamento de acervos bibliográficos em ambientes acadêmicos, proporcionando maior controle sobre empréstimos, disponibilidade de livros e histórico de utilização da biblioteca.

---

## 🛠️ Tecnologias Utilizadas

* Java
* JDBC
* MySQL
* MySQL Workbench
* Git e GitHub
* Programação Orientada a Objetos (POO)

---

## 📂 Estrutura do Banco de Dados

O banco de dados é composto pelas seguintes entidades:

### Usuários

Responsável pelo armazenamento de alunos e funcionários do sistema.

### Endereços

Armazena os dados de localização dos usuários cadastrados.

### Livros

Contém informações sobre o acervo da biblioteca.

### Empréstimos

Controla a saída e devolução dos livros.

### Multas

Registra penalidades aplicadas por atraso na devolução.

### Log de Auditoria

Armazena registros de operações importantes realizadas no sistema.

---

## ⚙️ Procedures Implementadas

### Cadastro Completo

`sp_transacao_cadastro_completo`

Realiza o cadastro de usuários e seus respectivos endereços utilizando transações para garantir a integridade dos dados.

### Empréstimo de Livros

`sp_transacao_emprestimo`

Verifica multas pendentes, disponibilidade de estoque e registra novos empréstimos.

### Renovação de Empréstimos

`sp_renovar_emprestimo`

Permite renovar um empréstimo ativo quando não existem restrições.

### Cálculo de Multas

`sp_calcular_multa`

Calcula automaticamente o valor da multa com base nos dias de atraso.

---

## 👁️ Views Disponíveis

O sistema utiliza views para facilitar consultas e relatórios:

* `vw_livros_disponiveis`
* `vw_emprestimos_ativos`
* `vw_acervo_publico`
* `vw_livros_atrasados`
* `vw_ranking_leitura`
* `vw_dashboard_financeiro`

---

## 🚀 Otimização com Índices

Para melhorar o desempenho das consultas mais utilizadas, foram criados os seguintes índices:

```sql
CREATE INDEX idx_livros_titulo
ON livros(titulo);

CREATE INDEX idx_livros_autor
ON livros(autor);

CREATE INDEX idx_emprestimos_usuario
ON emprestimos(id_usuario_fk);

CREATE INDEX idx_emprestimos_livro
ON emprestimos(id_livro_fk);
```

Esses índices reduzem o tempo de busca por títulos, autores e informações relacionadas aos empréstimos.

---

## 🔐 Controle de Acesso

O sistema possui diferentes perfis de acesso:

### Gerente

Possui acesso total ao banco de dados.

### Bibliotecário

Pode consultar, inserir e atualizar informações.

### Estagiário

Possui acesso somente para consultas.

---

## 💾 Backup e Restauração

### Exportação (Backup)

```bash
mysqldump -u root -p alexandria > alexandria_backup.sql
```

### Restauração

```bash
mysql -u root -p alexandria < alexandria_backup.sql
```

---

## 📈 Funcionalidades

* Cadastro de usuários
* Cadastro de endereços
* Cadastro de livros
* Controle de estoque
* Empréstimos de livros
* Renovação de empréstimos
* Controle de multas
* Consultas gerenciais
* Controle de permissões
* Auditoria de operações

---

## 🚧 Status do Projeto

Projeto acadêmico em desenvolvimento.

---

## 👨‍💻 Desenvolvido por

Projeto desenvolvido para fins acadêmicos na disciplina Conectar Banco de Dados com POO.

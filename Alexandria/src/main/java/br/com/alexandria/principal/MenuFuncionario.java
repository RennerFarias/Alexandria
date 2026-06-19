package br.com.alexandria.principal;

import DAO.EmprestimoDAO;
import DAO.LivroDAO;
import br.com.alexandria.model.Livro;

import javax.swing.*;

public class MenuFuncionario {
    public void exibirMenu() {
        String[] opcoes = {
                "Cadastrar Livro (Aquisição)",
                "Realizar Empréstimo",
                "Renovar Empréstimo",
                "Realizar Devolução",
                "Excluir Livro",
                "Gerar Relatórios / backup",
                "Listar Livros",
                "Listar emprestimos",
                "Sair"};

        int botaoClicado;

        boolean controleDeLoopPrincipal = true;
        boolean controleOpcao = true;
        do {
                int opcaoEscolhida = JOptionPane.showOptionDialog(null,
                        "Selecione a opção desejada", "Titulo", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
                controleOpcao = true;

                if (opcaoEscolhida == 0) {

                    do {
                        JTextField campoTituloLivro = new JTextField();
                        JTextField campoAutor = new JTextField();
                        JTextField campoIsbn = new JTextField();
                        JTextField campoPrecoCusto = new JTextField();
                        JTextField campoQuantidadeDeEstoque = new JTextField();
                        String[] opcaoStatus = {"Disponível", "Indisponível"};
                        JComboBox<String> campoStatus = new JComboBox<>(opcaoStatus);
                        JTextField campoNotaFiscal = new JTextField();

                        Object[] formularioLivro = {
                                "Titulo do Livro", campoTituloLivro,
                                "Autor do Livro", campoAutor,
                                "ISBN do Livro", campoIsbn,
                                "Preco do Livro", campoPrecoCusto,
                                "Quantidade de Estoque", campoQuantidadeDeEstoque,
                                "Status", campoStatus,
                                "Nota fistal", campoNotaFiscal
                        };
                        botaoClicado = JOptionPane.showConfirmDialog(null, formularioLivro, "Cadastro de livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (botaoClicado == JOptionPane.OK_OPTION) {
                            try {
                                String titulo = campoTituloLivro.getText();
                                String autor = campoAutor.getText();
                                String isbn = campoIsbn.getText();
                                String status = campoStatus.getSelectedItem().toString();
                                String notaFiscal = campoNotaFiscal.getText();

                                java.math.BigDecimal precoCusto = new java.math.BigDecimal(campoPrecoCusto.getText());
                                int quantidadeEstoque = Integer.parseInt(campoQuantidadeDeEstoque.getText());

                                Livro novoLivro = new Livro(
                                        0, titulo, autor, isbn, precoCusto, quantidadeEstoque, status
                                );

                                DAO.LivroDAO livroDAO = new DAO.LivroDAO();
                                livroDAO.cadastrarLivro(novoLivro);

                                JOptionPane.showMessageDialog(null,
                                        "Livro cadastrado com sucesso!",
                                        "Operação Concluída",
                                        JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException ex) {

                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números válidos para Preço e Quantidade.\nEx: Para preço use ponto (50.99)",
                                        "Erro de Digitação",
                                        JOptionPane.ERROR_MESSAGE);

                            } catch (IllegalArgumentException ex) {

                                JOptionPane.showMessageDialog(null,
                                        "Atenção: " + ex.getMessage(),
                                        "Regra de Negócio",
                                        JOptionPane.WARNING_MESSAGE);

                            } catch (java.sql.SQLException ex) {

                                JOptionPane.showMessageDialog(null,
                                        "Falha ao salvar no banco de dados.\nDetalhe: " + ex.getMessage(),
                                        "Erro de Permissão / Banco",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            controleOpcao = false;
                        }

                    } while(controleOpcao);


                }
                else if (opcaoEscolhida == 1) {
                    do {
                        JTextField campoIdUsuario = new JTextField();
                        JTextField campoIdLivro = new JTextField();

                        Object[] formularioEmprestimo = {
                                "Id do Usuario", campoIdUsuario,
                                "Id do Livro", campoIdLivro
                        };

                        botaoClicado = JOptionPane.showConfirmDialog(null, formularioEmprestimo, "Emprestimo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (botaoClicado == JOptionPane.OK_OPTION) {
                            try {
                                // 1. Captura e converte os IDs digitados
                                int idUsuario = Integer.parseInt(campoIdUsuario.getText().trim());
                                int idLivro = Integer.parseInt(campoIdLivro.getText().trim());

                                // 2. Instancia o DAO e chama a procedure
                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.realizarEmprestimo(idUsuario, idLivro);

                                // mensagem de sucesso
                                JOptionPane.showMessageDialog(null,
                                        "Empréstimo realizado com sucesso!",
                                        "Transação Concluída",
                                        JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                // Captura letras ou campos em branco
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para os IDs.",
                                        "Erro de Digitação",
                                        JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {
                                // O MySQL pode disparar um erro de dentro da procedure
                                JOptionPane.showMessageDialog(null,
                                        "A transação foi recusada pelo banco de dados.\nMotivo: " + e.getMessage(),
                                        "Erro no Empréstimo",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            // Chama a Procedure sp_transacao_emprestimo.
                        } else {
                            controleOpcao = false;

                        }
                    } while (controleOpcao);

                }
                else if (opcaoEscolhida == 2) {

                    do {
                        String campoIdEmprestimo = JOptionPane.showInputDialog(null, "Id do Emprestimo",
                                "Emprestimo", JOptionPane.QUESTION_MESSAGE);

                        if (campoIdEmprestimo == null) {
                            controleOpcao = false;

                        } else if (campoIdEmprestimo.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "O ID não pode ficar em branco!",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
                        } else {
                            try {
                                // Converte o ID digitado para inteiro
                                int idEmprestimo = Integer.parseInt(campoIdEmprestimo.trim());

                                // Chama a procedure através do DAO
                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.renovarEmprestimo(idEmprestimo);

                                JOptionPane.showMessageDialog(null,
                                        "Empréstimo renovado com sucesso!",
                                        "Renovação Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                // Captura letras ou caracteres especiais digitados por engano
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do empréstimo.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {
                                JOptionPane.showMessageDialog(null,
                                        "A renovação foi recusada.\nMotivo: " + e.getMessage(),
                                        "Erro na Renovação", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } while (controleOpcao);

                }
                else if (opcaoEscolhida == 3) {
                    do{
                        String campoIdEmprestimo = JOptionPane.showInputDialog(null, "Id do Emprestimo",
                                "Emprestimo", JOptionPane.QUESTION_MESSAGE);

                        if (campoIdEmprestimo == null) {
                            controleOpcao = false;

                        } else if (campoIdEmprestimo.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "O ID não pode ficar em branco!",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
                        } else {
                            try {
                                // Converte o ID digitado para inteiro
                                int idEmprestimo = Integer.parseInt(campoIdEmprestimo.trim());

                                // Chama a procedure através do DAO
                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.devolucaoEmprestimo(idEmprestimo);

                                JOptionPane.showMessageDialog(null,
                                        "Devolução realizada com sucesso!",
                                        "Devolução Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                // Captura letras ou caracteres especiais digitados por engano
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do empréstimo.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {
                                JOptionPane.showMessageDialog(null,
                                        "A Devolução foi recusada.\nMotivo: " + e.getMessage(),
                                        "Erro na Devolução", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } while (controleOpcao);

                }
                else if (opcaoEscolhida == 4) {
                    do {
                        String inputIdLivro = JOptionPane.showInputDialog(null,
                                "Id do livro", "livro",  JOptionPane.QUESTION_MESSAGE);

                        if (inputIdLivro == null) {
                            controleOpcao = false;

                        } else if (inputIdLivro.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O ID não pode ficar em branco!",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
                            controleOpcao = false;

                        } else {

                            try {
                                int idLivro = Integer.parseInt(inputIdLivro);

                                DAO.LivroDAO livroDAO = new DAO.LivroDAO();
                                livroDAO.excluirLivro(idLivro);

                                JOptionPane.showMessageDialog(null,
                                        "Livro excluido  com sucesso!",
                                        "Exclusão Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;


                            } catch (NumberFormatException e) {
                                // Captura letras ou caracteres especiais digitados por engano
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do livro.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {
                                JOptionPane.showMessageDialog(null,
                                        "A Devolução foi recusada.\nMotivo: " + e.getMessage(),
                                        "Erro na exclusão", JOptionPane.ERROR_MESSAGE);
                            }

                        };

                    } while (controleOpcao);


                }
                else if (opcaoEscolhida == 5) {
                    do {

                        MenuRelatoriosBackup menuRelatoriosBackup = new MenuRelatoriosBackup();
                        menuRelatoriosBackup.exibirMenu();

                        controleOpcao = false;

                    } while (controleOpcao);


                } else if (opcaoEscolhida == 6) {
                    try {
                        LivroDAO livroDAO = new LivroDAO();
                        String listaDeLivros = livroDAO.listarLivrosFuncionario();

                        JTextArea textArea = new JTextArea(listaDeLivros);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                        JOptionPane.showMessageDialog(null, scrollPane,
                                "Acervo da Biblioteca", JOptionPane.INFORMATION_MESSAGE);

                    } catch (java.sql.SQLException e) {
                        // Captura possíveis falhas de conexão ou falta de permissão na View
                        JOptionPane.showMessageDialog(null,
                                "Erro ao carregar o acervo.\nDetalhe: " + e.getMessage(),
                                "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (opcaoEscolhida == 7) {
                    try {
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        String listaDeEmprestimos = emprestimoDAO.ListarEmprestimos();

                        JTextArea textArea = new JTextArea(listaDeEmprestimos);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                    }  catch (java.sql.SQLException e) {
                        // Captura possíveis falhas de conexão ou falta de permissão na View
                        JOptionPane.showMessageDialog(null,
                                "Erro ao carregar o acervo.\nDetalhe: " + e.getMessage(),
                                "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                }
                } else {
                    controleDeLoopPrincipal = false;
                };


        } while(controleDeLoopPrincipal);
    }
}

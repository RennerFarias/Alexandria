package br.com.alexandria.Menus;

import DAO.EmprestimoDAO;
import DAO.LivroDAO;
import br.com.alexandria.model.Livro;
import br.com.alexandria.util.TratamentoDeErros;

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
            JComboBox<String> menuDropdown = new JComboBox<>(opcoes);
            Object[] mensagemMenu = {
                    "Selecione a opção desejada:", menuDropdown
            };

            int acaoMenu = JOptionPane.showConfirmDialog(null, mensagemMenu,
                    "Menu Principal - Biblioteca", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            int opcaoEscolhida;

            if (acaoMenu == JOptionPane.OK_OPTION) {
                opcaoEscolhida = menuDropdown.getSelectedIndex();
            } else {
                opcaoEscolhida = 8;
            }

            controleOpcao = true;

            switch (opcaoEscolhida) {
                case 0:
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

                            } catch (java.sql.SQLException e) {
                                // Agora o Java já sabe quem é TratamentoDeErros por causa do import lá em cima!
                                String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                                JOptionPane.showMessageDialog(null,
                                        mensagemAmigavel,
                                        "Aviso do Sistema",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            controleOpcao = false;
                        }
                    } while(controleOpcao);
                    break;

                case 1:
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
                                int idUsuario = Integer.parseInt(campoIdUsuario.getText().trim());
                                int idLivro = Integer.parseInt(campoIdLivro.getText().trim());

                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.realizarEmprestimo(idUsuario, idLivro);

                                JOptionPane.showMessageDialog(null,
                                        "Empréstimo realizado com sucesso!",
                                        "Transação Concluída",
                                        JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para os IDs.",
                                        "Erro de Digitação",
                                        JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {

                                String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                                JOptionPane.showMessageDialog(null,
                                        mensagemAmigavel,
                                        "Aviso do Sistema",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            controleOpcao = false;
                        }
                    } while (controleOpcao);
                    break;

                case 2:
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
                                int idEmprestimo = Integer.parseInt(campoIdEmprestimo.trim());

                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.renovarEmprestimo(idEmprestimo);

                                JOptionPane.showMessageDialog(null,
                                        "Empréstimo renovado com sucesso!",
                                        "Renovação Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do empréstimo.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {

                                String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                                JOptionPane.showMessageDialog(null,
                                        mensagemAmigavel,
                                        "Aviso do Sistema",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } while (controleOpcao);
                    break;

                case 3:
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
                                int idEmprestimo = Integer.parseInt(campoIdEmprestimo.trim());

                                DAO.EmprestimoDAO emprestimoDAO = new DAO.EmprestimoDAO();
                                emprestimoDAO.devolucaoEmprestimo(idEmprestimo);

                                JOptionPane.showMessageDialog(null,
                                        "Devolução realizada com sucesso!",
                                        "Devolução Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do empréstimo.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {

                                String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                                JOptionPane.showMessageDialog(null,
                                        mensagemAmigavel,
                                        "Aviso do Sistema",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } while (controleOpcao);
                    break;

                case 4:
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
                                        "Livro excluido com sucesso!",
                                        "Exclusão Concluída", JOptionPane.INFORMATION_MESSAGE);

                                controleOpcao = false;

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Erro: Digite apenas números inteiros para o ID do livro.",
                                        "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                            } catch (java.sql.SQLException e) {

                                String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                                JOptionPane.showMessageDialog(null,
                                        mensagemAmigavel,
                                        "Aviso do Sistema",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } while (controleOpcao);
                    break;

                case 5:
                    do {
                        MenuRelatoriosBackup menuRelatoriosBackup = new MenuRelatoriosBackup();
                        menuRelatoriosBackup.exibirMenu();
                        controleOpcao = false;
                    } while (controleOpcao);
                    break;

                case 6:
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

                        String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                        JOptionPane.showMessageDialog(null,
                                mensagemAmigavel,
                                "Aviso do Sistema",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    break;

                case 7:
                    try {
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        String listaDeEmprestimos = emprestimoDAO.ListarEmprestimos();

                        JTextArea textArea = new JTextArea(listaDeEmprestimos);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                        JOptionPane.showMessageDialog(null, scrollPane,
                                "Lista de Empréstimos", JOptionPane.INFORMATION_MESSAGE);

                    } catch (java.sql.SQLException e) {

                        String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                        JOptionPane.showMessageDialog(null,
                                mensagemAmigavel,
                                "Aviso do Sistema",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    break;

                case 8:
                default:
                    controleDeLoopPrincipal = false;
                    break;
            }

        } while(controleDeLoopPrincipal);
    }
}
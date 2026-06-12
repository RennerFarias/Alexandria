package br.com.alexandria.principal;

import javax.swing.*;
import java.math.BigDecimal;

public class MenuFuncionario {
    public void exibirMenu() {
        String[] opcoes = {
                "Cadastrar Livro (Aquisição)",
                "Realizar Empréstimo",
                "Renovar Empréstimo",
                "Realizar Devolução",
                "Excluir Livro",
                "Gerar Relatórios / backup",
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
                        JTextField campoStatus = new JTextField();
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
                            // Chama a Transação de Aquisição no DAO.

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
                            // Chama a Procedure sp_transacao_emprestimo.
                        } else {
                            controleOpcao = false;

                        }
                    } while (controleOpcao);

                }
                else if (opcaoEscolhida == 2) {
                    do {
                        String idEmprestimo = JOptionPane.showInputDialog(null, "Id do Emprestimo",
                                "Emprestimo", JOptionPane.QUESTION_MESSAGE);



                        if (idEmprestimo == null  || idEmprestimo.trim().isEmpty()) {
                            controleOpcao = false;

                        } else {

                            // Chama a Procedure sp_renovar_emprestimo.
                        }
                    } while (controleOpcao);

                }
                else if (opcaoEscolhida == 3) {
                    do{
                        String idEmprestimo = JOptionPane.showInputDialog(null, "Id do Emprestimo",
                                "Emprestimo", JOptionPane.QUESTION_MESSAGE);

                        if (idEmprestimo == null) {
                            controleOpcao = false;

                        } else if (idEmprestimo.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O ID não pode ficar em branco!", "Aviso", JOptionPane.WARNING_MESSAGE);
                            controleOpcao = false;

                        } else {
                            // sp_transacao_devolucao (que calcula multa e atualiza estoque).
                        };

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

                            try{
                                int idLivro = Integer.parseInt(inputIdLivro);
                                try {
                                    // Logica do Banco de dados
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "ERRO: Acesso Negado! " +
                                            "Seu perfil de usuário não tem permissão para excluir registros do sistema."
                                            , "Erro de Permisão", JOptionPane.ERROR_MESSAGE);
                                }


                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "" +
                                        "Erro: Digite apenas números inteiros para o ID.", "Erro de Digitação",
                                        JOptionPane.ERROR_MESSAGE);

                            }

                        };

                    } while (controleOpcao);


                }
                else if (opcaoEscolhida == 5) {
                    do {
                        JOptionPane.showMessageDialog(null, "Executa o mysqldump ou chama as Views de relatório financeiro");
                        controleOpcao = false;

                    } while (controleOpcao);


                }
                else {
                    controleDeLoopPrincipal = false;
                };


        } while(controleDeLoopPrincipal);
    }
}

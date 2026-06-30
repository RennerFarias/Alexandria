package br.com.alexandria.Menus;

import DAO.EmprestimoDAO;
import DAO.LivroDAO;
import br.com.alexandria.util.TratamentoDeErros;

import javax.swing.*;
import java.sql.SQLException;

public class MenuAluno {
    public void exibirMenu() throws SQLException {
        String[] opcoes = {"Consultar Acervo Disponível", "Meus Empréstimos", "Sair"};
        boolean controleDoLoop = true;

        do {
            int opcaoEscolhida = JOptionPane.showOptionDialog(null, "O que deseja fazer?",
                    "Menu do Aluno", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, null );


            switch (opcaoEscolhida) {
                case 0: // Consultar Acervo Disponível
                    try {
                        LivroDAO livroDAO = new LivroDAO();
                        String listaDeLivros = livroDAO.listarLivros();

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
                    break; // Fim do Case 0

                case 1: // Meus Empréstimos
                    try {
                        String inputId = JOptionPane.showInputDialog(null,
                                "Por favor, digite o seu ID de Aluno:",
                                "Identificação", JOptionPane.QUESTION_MESSAGE);

                        if (inputId != null && !inputId.trim().isEmpty()) {


                            int idAlunoLogado = Integer.parseInt(inputId.trim());


                            EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                            String resultadoHistorico = emprestimoDAO.historicoUsuario(idAlunoLogado);

                            // 3. Monta a tela de exibição
                            JTextArea textArea = new JTextArea(resultadoHistorico);
                            textArea.setEditable(false);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                            JOptionPane.showMessageDialog(null, scrollPane,
                                    "Histórico de Empréstimos", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (NumberFormatException e) {
                        // Captura o erro caso o aluno digite letras ao invés de números
                        JOptionPane.showMessageDialog(null,
                                "Erro: O ID deve conter apenas números.",
                                "Erro de Digitação", JOptionPane.ERROR_MESSAGE);

                    } catch (java.sql.SQLException e) {

                        String mensagemAmigavel = TratamentoDeErros.obterMensagemAmigavel(e);

                        JOptionPane.showMessageDialog(null,
                                mensagemAmigavel,
                                "Aviso do Sistema",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    break;

                case 2:
                default:
                    controleDoLoop = false;
                    break;
            }

        } while (controleDoLoop);
    }
}
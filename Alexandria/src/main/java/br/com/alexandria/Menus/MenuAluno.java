package br.com.alexandria.Menus;

import DAO.EmprestimoDAO;
import DAO.LivroDAO;

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
                        JOptionPane.showMessageDialog(null,
                                "Erro ao carregar o acervo.\nDetalhe: " + e.getMessage(),
                                "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                    }
                    break; // Fim do Case 0

                case 1: // Meus Empréstimos
                    try {
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        String resultadoHistorico = emprestimoDAO.historicoUsuario();

                        JTextArea textArea = new JTextArea(resultadoHistorico);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

                        JOptionPane.showMessageDialog(null, scrollPane,
                                "Histórico do aluno", JOptionPane.INFORMATION_MESSAGE);

                    } catch (java.sql.SQLException e) {
                        // Captura possíveis falhas de conexão ou falta de permissão na View
                        JOptionPane.showMessageDialog(null,
                                "Erro ao carregar o histórico.\nDetalhe: " + e.getMessage(),
                                "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
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
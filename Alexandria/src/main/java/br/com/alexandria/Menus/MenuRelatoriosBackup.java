package br.com.alexandria.Menus;

import br.com.alexandria.service.BackupService;
import connection.DataBaseConnection;

import javax.swing.*;

public class MenuRelatoriosBackup {
    public void exibirMenu(){
        String[] opcoes = {
                "Relatório Financeiro",
                "Fazer Backup do Banco de dados",
                "Voltar"
        };

        int botaoClicado;
        boolean controleDeLoop = true;

        do {
            int opcaoEscolhida = JOptionPane.showOptionDialog(null,
                    "Selecione a opção desejada", "Relatório / Backup", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opcoes, null);


            switch (opcaoEscolhida) {
                case 0: // Relatório Financeiro
                    try {
                        DAO.RelatorioDAO relatorioDAO = new DAO.RelatorioDAO();
                        String dadosRelatorio = relatorioDAO.buscarRelatorioFinanceiro();

                        JTextArea textArea = new JTextArea(dadosRelatorio);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300)); // Tamanho da janela

                        JOptionPane.showMessageDialog(null, scrollPane,
                                "Relatório Financeiro", JOptionPane.INFORMATION_MESSAGE);

                    } catch (java.sql.SQLException e) {
                        // Se o usuário (ex: estagiário) não tiver permissão para fazer SELECT na View
                        JOptionPane.showMessageDialog(null,
                                "Acesso negado para gerar relatórios.\nDetalhe: " + e.getMessage(),
                                "Erro de Permissão", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 1: { // Fazer Backup do Banco de dados
                    DataBaseConnection conexaoAtual = DataBaseConnection.getInstance();

                    BackupService.ResultadoBackup resultado = BackupService.realizarBackup(
                            conexaoAtual.getUsuario(),
                            conexaoAtual.getSenha()
                    );

                    if (resultado.sucesso) {
                        JOptionPane.showMessageDialog(null,
                                resultado.mensagem + "\nSalvo em: " + resultado.caminhoArquivo,
                                "Backup Concluído", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                resultado.mensagem,
                                "Erro ao Realizar Backup", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }

                case 2: // Voltar
                default:
                    controleDeLoop = false;
                    break;
            }

        } while (controleDeLoop);

    }
}
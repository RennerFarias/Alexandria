package br.com.alexandria.principal;

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

            if (opcaoEscolhida == 0) {
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
            }

            if (opcaoEscolhida == 1) {
                try {
                    // Define que o arquivo será salvo na pasta Documentos do Windows/Linux do usuário
                    String homeDir = System.getProperty("user.home");
                    String caminhoBackup = homeDir + "/Documents/backup_alexandria.sql";

                    // ATENÇÃO: Ajuste o '-u root -psenha' para as credenciais administrativas do seu MySQL local
                    // Não deixe espaço entre o -p e a sua senha
                    String comando = "mysqldump -u root -pSuaSenhaRoot alexandria -r \"" + caminhoBackup + "\"";

                    // Executa o comando no sistema operacional
                    Process processo = Runtime.getRuntime().exec(comando);

                    // Pede para o Java esperar o backup terminar de salvar
                    int terminou = processo.waitFor();

                    if (terminou == 0) {
                        JOptionPane.showMessageDialog(null,
                                "Backup realizado com sucesso!\nSalvo em: " + caminhoBackup,
                                "Backup Concluído", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "O comando falhou. O mysqldump está configurado nas variáveis de ambiente?",
                                "Erro de Execução", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Erro interno ao tentar criar o backup: " + e.getMessage(),
                            "Erro Grave", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                controleDeLoop = false;
            }




        } while (controleDeLoop);

    }
}

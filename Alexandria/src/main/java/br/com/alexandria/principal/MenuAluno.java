package br.com.alexandria.principal;

import javax.swing.*;

public class MenuAluno {
    static void main() {
        String[] opcoes = {"Consultar Acervo Disponível", "Meus Empréstimos", "Sair"};
        boolean controleDoLoop = true;

        do {
            int opcaoEscolhida = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Titulo a ser pensado", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null );

            if (opcaoEscolhida == 0) {
                // Executar SELECT * FROM vw_acervo_publico

            } else if (opcaoEscolhida == 1) {
                // Executa a procedure sp_historico_usuario ou um SELECT filtrando pelo ID do aluno logado.


            } else {
                JOptionPane.showMessageDialog(null, "Saindo...");
                controleDoLoop = false;
            }

        } while (controleDoLoop);
    }
}

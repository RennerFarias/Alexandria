package br.com.alexandria.principal;

import javax.swing.*;

public class Inicio {
    static void main() {

        String[] opcoes = {"Funcionários", "Alunos", "Sair"};
        JTextField campoUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        Object[] componentesLogin = {"Usuário: ", campoUsuario, "Senha: ", campoSenha};

        String usuario;
        String senha;

        int opcaoEscolhida = JOptionPane.showOptionDialog
                (null, "Qual é o seu perfil de acesso?",
                        "Perfil de acesso", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, null);

        if (opcaoEscolhida == 0) {
            // Login funcionario
            int opcaoClicada = JOptionPane.showConfirmDialog(null, componentesLogin,
                    "Acesso ao sistema",  JOptionPane.OK_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE);

            if (opcaoClicada == JOptionPane.OK_OPTION) {
                usuario = campoUsuario.getText();
                senha = new String(campoSenha.getPassword());

                // Bloco para passar as informacoes para a classe conexao
                // JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro de Acesso",
                // JOptionPane.ERROR_MESSAGE);

                // Menu funcionario

            }  else {
                JOptionPane.showMessageDialog(null, "Login cancelado pelo usuario");
            }

            } else if (opcaoEscolhida == 1) {
            // Login Aluno
            int opcaoClicada = JOptionPane.showConfirmDialog(null, componentesLogin,
                    "Acesso ao sistema",  JOptionPane.OK_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE);

            if (opcaoClicada == JOptionPane.OK_OPTION) {
                usuario = campoUsuario.getText();
                senha = new String(campoSenha.getPassword());

                // Bloco para passar as informacoes para a classe conexao
                // JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro de Acesso",
                // JOptionPane.ERROR_MESSAGE);

                // Menu aluno

            }  else {
                JOptionPane.showMessageDialog(null, "Login cancelado pelo usuario");
            }

        } else  {
            JOptionPane.showMessageDialog(null, "Saindo...");
        }

    }
}

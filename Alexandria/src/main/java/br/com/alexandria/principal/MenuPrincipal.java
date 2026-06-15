package br.com.alexandria.principal;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class MenuPrincipal {
    public void exibirMenu() {
        boolean continuar = true;
        boolean continuarOpcao = true;

        do {
            continuarOpcao = true;
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
                do {
                    // Login funcionario
                    int opcaoClicada = JOptionPane.showConfirmDialog(null, componentesLogin,
                            "Acesso ao sistema",  JOptionPane.OK_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE);

                    if (opcaoClicada == JOptionPane.OK_OPTION) {
                        usuario = campoUsuario.getText();
                        senha = new String(campoSenha.getPassword());

                        if (usuario.trim().isEmpty() || senha.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Atenção: Os campos de usuário e senha não podem ficar vazios!",
                                    "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
                            continue;
                        }

                        try {
                            connection.DataBaseConnection.getInstance(usuario, senha);

                            MenuFuncionario menuFuncionario = new MenuFuncionario();
                            menuFuncionario.exibirMenu();

                            campoUsuario.setText("");
                            campoSenha.setText("");

                        }
                        catch (java.sql.SQLException e) {
                            JOptionPane.showMessageDialog(null,
                                    "Acesso negado! Verifique seu usuário e senha.\nErro: " + e.getMessage(),
                                    "Erro de Acesso",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "Acesso negado! Verifique seu usuário e senha.",
                                    "Erro de Acesso",
                                    JOptionPane.ERROR_MESSAGE);
                        }


                    }  else {
                        JOptionPane.showMessageDialog(null, "Login cancelado pelo usuario");
                        continuarOpcao = false;
                    }
                } while (continuarOpcao);


            } else if (opcaoEscolhida == 1) {
                do {
                    // Login Aluno
                    int opcaoClicada = JOptionPane.showConfirmDialog(null, componentesLogin,
                            "Acesso ao sistema",  JOptionPane.OK_CANCEL_OPTION,  JOptionPane.QUESTION_MESSAGE);

                    if (opcaoClicada == JOptionPane.OK_OPTION) {
                        usuario = campoUsuario.getText();
                        senha = new String(campoSenha.getPassword());

                        if (usuario.trim().isEmpty() || senha.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Atenção: Os campos de usuário e senha não podem ficar vazios!",
                                    "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
                            continue;
                        }

                        try {
                            connection.DataBaseConnection.getInstance(usuario, senha);

                            MenuAluno menuAluno = new MenuAluno();
                            menuAluno.exibirMenu();

                            campoUsuario.setText("");
                            campoSenha.setText("");


                        } catch (java.sql.SQLException e) {

                            JOptionPane.showMessageDialog(null,
                                    "Acesso negado! Verifique seu usuário e senha.\nErro: " + e.getMessage(),
                                    "Erro de Acesso",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "Acesso negado! Verifique seu usuário e senha.",
                                    "Erro de Acesso",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }  else {
                        JOptionPane.showMessageDialog(null, "Login cancelado pelo usuario");
                        continuarOpcao = false;
                    }
                } while (continuarOpcao);


            } else  {

                continuar = false;
            }
        } while (continuar);
        JOptionPane.showMessageDialog(null, "Saindo...");



    }
}

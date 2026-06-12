package br.com.alexandria.principal;

import javax.swing.*;
import java.math.BigDecimal;

public class MenuFuncionario {
    static void main() {
        String[] opcoes = {"Cadastrar Livro (Aquisição)", "Realizar Empréstimo", "Renovar Empréstimo", "Realizar Devolução", "Excluir Livro", "Gerar Relatórios / backup", "Sair"};

        JTextField campoTituloLivro = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoIsbn = new JTextField();
        JTextField campoPrecoCusto = new JTextField();
        JTextField campoQuantidadeDeEstoque = new JTextField();
        JTextField campoStatus =  new JTextField();

        Object[] formularioLivro = {
                "Titulo do Livro", campoTituloLivro,
                "Autor do Livro", campoAutor,
                "ISBN do Livro", campoIsbn,
                "Preco do Livro", campoPrecoCusto,
                "Quantidade de Estoque", campoQuantidadeDeEstoque, "Status", campoStatus
        };

        int opcaoEscolhida = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Titulo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null);

        if (opcaoEscolhida == 0) {
            int botaoClicado = JOptionPane.showConfirmDialog(null, formularioLivro, "Cadastro de livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

    }
}

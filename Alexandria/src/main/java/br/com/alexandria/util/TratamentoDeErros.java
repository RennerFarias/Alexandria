package br.com.alexandria.util;

import java.sql.SQLException;

public class TratamentoDeErros {

    public static String obterMensagemAmigavel(SQLException e) {

        int codigoErro = e.getErrorCode();
        String mensagemOriginal = e.getMessage().toLowerCase();

        // Código 1142 ou 1227: Permissão Negada (O clássico erro do Estagiário)
        if (codigoErro == 1142 || codigoErro == 1227 || mensagemOriginal.contains("command denied")) {
            return "Acesso restrito. \nParece que o seu perfil atual não tem permissão para realizar esta ação no sistema. Caso precise, contate um Gerente.";
        }

        // Código 1045: Erro de Login (Senha ou usuário incorretos)
        if (codigoErro == 1045) {
            return "Credenciais inválidas! Verifique se você digitou seu usuário e senha corretamente.";
        }

        // Código 1451: Violação de Chave Estrangeira (Ex: Excluir livro que tem empréstimo)
        if (codigoErro == 1451) {
            return "Ação bloqueada: Você não pode excluir este registro porque ele já está vinculado a outras informações no sistema (ex: um livro com empréstimo ativo).";
        }

        // Código 1062: Entrada duplicada (Ex: Tentar cadastrar um CPF que já existe)
        if (codigoErro == 1062) {
            return "Atenção: Já existe um registro com este identificador único (como CPF, ISBN ou ID) cadastrado no sistema.";
        }

        // Erros disparados de propósito pelas suas Procedures (ex: Estoque vazio)
        if (codigoErro == 1644 || SQLStatePersonalizado(e.getSQLState())) {
            return "Regra de negócio: " + e.getMessage(); // Aqui deixamos a mensagem da sua Procedure aparecer
        }

        // Erro genérico de banco (Fallback)
        return "Poxa, enfrentamos uma instabilidade no banco de dados.\nDetalhe técnico para o suporte: " + e.getMessage();
    }

    private static boolean SQLStatePersonalizado(String sqlState) {
        return sqlState != null && sqlState.startsWith("45"); // 45000 é o padrão para exceções criadas no MySQL pelo usuário
    }
}

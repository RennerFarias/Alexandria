package DAO;

import connection.DataBaseConnection;

import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmprestimoDAO {

    private final Connection connection;

    public EmprestimoDAO() {
        this.connection = DataBaseConnection.getInstance().connection();
    }

    public void realizarEmprestimo(int idUsuario, int idLivro) throws SQLException {
        String sql = "{CALL sp_transacao_emprestimo(?, ?)}";

        try (CallableStatement cstm = connection.prepareCall(sql)) {
            cstm.setInt(1, idUsuario);
            cstm.setInt(2, idLivro);

            cstm.execute();
            JOptionPane.showMessageDialog(null, "Emprestimo realizado com sucesso!", "Emprestimo", JOptionPane.INFORMATION_MESSAGE);


        }
    }

    public void renovarEmprestimo(int emprestimo) throws SQLException {
        String sql = "{CALL sp_renovar_emprestimo(?)}";
        try (CallableStatement cstm = connection.prepareCall(sql)) {
            cstm.setInt(1, emprestimo);

            cstm.execute();
            JOptionPane.showMessageDialog(null, "Renovação do emprestimo realizada com  sucesso!", "Emprestimo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void devolucaoEmprestimo(int idEmprestimo) throws SQLException {
        String sql = "{CALL sp_transacao_devolucao(?)}";
        try (CallableStatement cstm = connection.prepareCall(sql)) {
            cstm.setInt(1, idEmprestimo);

            cstm.execute();
            JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso", "Devolução", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public String historicoUsuario() throws SQLException {

        int idAlunoLogado = -1;
        String sqlBuscaId = "SELECT id_usuario FROM usuario WHERE login_banco = SUBSTRING_INDEX(USER(), '@', 1)";

        try (java.sql.Statement st = connection.createStatement(); java.sql.ResultSet rs = st.executeQuery(sqlBuscaId)) {
            if (rs.next()) {
                idAlunoLogado = rs.getInt("id");

            } else {
                throw new SQLException("Usuário logado no banco não possui um ID vinculado no sistema.");
            };

        }


        String sql = "{CALL sp_historico_usuario(?)}";
        StringBuilder historico = new StringBuilder();


        try (java.sql.CallableStatement cstm = connection.prepareCall(sql)) {

            // Passa o ID do aluno logado como parâmetro para a procedure
            cstm.setInt(1, idAlunoLogado);

            try (java.sql.ResultSet rs = cstm.executeQuery()) {

                java.sql.ResultSetMetaData metaData = rs.getMetaData();
                int colunas = metaData.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= colunas; i++) {
                        historico.append(metaData.getColumnName(i).toUpperCase()).append(": ")
                                .append(rs.getString(i)).append("  |  ");
                    }
                    historico.append("\n\n"); // Pula linha
                }
            }
        }
        if (historico.isEmpty()) {
            return "Você não possui nenhum empréstimo registrado no seu histórico.";
        }

        return historico.toString();
    }

    public String ListarEmprestimos() throws SQLException {

        String sql = "SELECT * FROM emprestimos";
        StringBuilder listagem = new StringBuilder();


            try (java.sql.Statement st = connection.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {

                java.sql.ResultSetMetaData metaData = rs.getMetaData();
                int colunas = metaData.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= colunas; i++) {
                        listagem.append(metaData.getColumnName(i).toUpperCase()).append(": ")
                                .append(rs.getString(i)).append("  |  ");
                    }
                    listagem.append("\n\n"); // Pula linha
                }
            }
        if (listagem.isEmpty()) {
            return "Não há emprestimos registrados.";
        }

        return listagem.toString();
    }


}
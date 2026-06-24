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

    // Note que agora a função recebe o 'idAlunoLogado' diretamente!
    public String historicoUsuario(int idAlunoLogado) throws SQLException {

        // Executa o SELECT filtrando pelo ID do aluno logado
        String sqlHistorico = "SELECT l.titulo AS 'Livro', e.data_saida AS 'Data de Saída', " +
                "e.data_prevista AS 'Devolução Prevista', e.data_devolucao AS 'Devolvido em' " +
                "FROM emprestimos e " +
                "JOIN livros l ON e.id_livro_fk = l.id_livro " +
                "WHERE e.id_usuario_fk = ? " +
                "ORDER BY e.data_saida DESC";

        StringBuilder historico = new StringBuilder();

        try (java.sql.PreparedStatement pstm = connection.prepareStatement(sqlHistorico)) {

            // Passa o ID recebido por parâmetro para o WHERE
            pstm.setInt(1, idAlunoLogado);

            try (java.sql.ResultSet rs = pstm.executeQuery()) {

                java.sql.ResultSetMetaData metaData = rs.getMetaData();
                int colunas = metaData.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= colunas; i++) {
                        String nomeColuna = metaData.getColumnLabel(i);
                        String valorColuna = rs.getString(i) != null ? rs.getString(i) : "Pendente";

                        historico.append(nomeColuna).append(": ").append(valorColuna).append("  |  ");
                    }
                    historico.append("\n\n");
                }
            }
        }

        if (historico.length() == 0) {
            return "Nenhum empréstimo encontrado no seu histórico.";
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
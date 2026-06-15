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
        String sql = "{CALL sp_transacao_devoluca(?)}";
        try (CallableStatement cstm = connection.prepareCall(sql)) {
            cstm.setInt(1, idEmprestimo);

            cstm.execute();
            JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso", "Devolução",  JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

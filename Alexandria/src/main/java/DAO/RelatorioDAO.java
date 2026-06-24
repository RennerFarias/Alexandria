package DAO;

import connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class RelatorioDAO {
    private Connection connection;

    public RelatorioDAO() {
        this.connection = DataBaseConnection.getInstance().connection();
    }

    public String buscarRelatorioFinanceiro() throws SQLException {
        String sql = "SELECT * FROM vw_dashboard_financeiro";
        StringBuilder relatorio = new StringBuilder();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int colunas = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= colunas; i++) {
                    relatorio.append(metaData.getColumnName(i)).append(": ")
                            .append(rs.getString(i)).append(" | ");
                }
                relatorio.append("\n"); // Pula linha para o próximo registro
            }

            if (relatorio.length() == 0) {
                return "Nenhum dado financeiro encontrado no momento.";
            }

            return relatorio.toString();
        }
    }
}
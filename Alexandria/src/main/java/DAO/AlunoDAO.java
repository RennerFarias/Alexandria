package DAO;

import connection.DataBaseConnection;

import java.sql.Connection;

public class AlunoDAO {
    private final Connection connection;

    public AlunoDAO() {
        this.connection = DataBaseConnection
                .getInstance()
                .connection();
    }
}

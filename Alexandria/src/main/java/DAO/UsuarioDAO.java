package DAO;

import br.com.alexandria.model.Usuario;
import connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO() {
        this.connection = DataBaseConnection
                .getInstance()
                .connection();
    }
}

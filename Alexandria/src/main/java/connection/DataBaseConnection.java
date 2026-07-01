package connection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DataBaseConnection {

    private static DataBaseConnection instance;

    private final Connection connection;
    private final String usuario;
    private final String senha;



    private DataBaseConnection(String usuario, String senha) {

        this.usuario = usuario;
        this.senha = senha;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alexandria",
                    usuario,
                    senha
            );


        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();

            throw new RuntimeException(ex);

        } catch (SQLException ex) {

            ex.printStackTrace();

            throw new RuntimeException(ex);
        }
    }

    public static void getInstance(String usuario, String senha) throws SQLException,ClassNotFoundException {
        instance = new DataBaseConnection(usuario, senha);
    }

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            throw new RuntimeException("Erro: A conexão ainda não foi inicializada. Faça o login primeiro.");
        }
        return instance;
    }

    public Connection connection() {
        return connection;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}

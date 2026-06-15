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

    public void cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, cpf, email, senha, tipo) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf());
            pstm.setString(3, usuario.getEmail());
            pstm.setString(4, usuario.getSenha());
            pstm.setString(5, usuario.getTipo());


            pstm.execute();

            System.out.println("Usuario cadastrado com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}

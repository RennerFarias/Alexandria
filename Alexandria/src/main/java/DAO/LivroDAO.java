package DAO;

import br.com.alexandria.model.Livro;
import connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LivroDAO {

    private final Connection connection;

    public LivroDAO() {
        this.connection = DataBaseConnection.getInstance().connection();
    }

    // --- MÉTODOS DO SISTEMA ---

    public void cadastrarLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, isbn, preco_custo, quantidade_estoque, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setString(3, livro.getIsbn());
            pstm.setBigDecimal(4, livro.getPrecoCusto());
            pstm.setInt(5, livro.getQuantidadeEstoque());
            pstm.setString(6, livro.getStatus());

            pstm.execute();
            System.out.println("Livro cadastrado com sucesso: " + livro.getTitulo());
        }

    }

    public void excluirLivro(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);

            int linhasAfetadas = pstm.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro excluído com sucesso! ID: " + id);
            } else {
                System.out.println("Nenhum livro encontrado com o ID fornecido.");
            }
        }
    }

    public String listarLivros() throws SQLException {
        String sql = "SELECT * FROM vw_acervo_publico";
        StringBuilder acervo = new StringBuilder();

        try (java.sql.Statement st = connection.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql)) {

            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int colunas = metaData.getColumnCount();

            while (rs.next()) {
                // Monta a linha do livro (Ex: Titulo: O Hobbit | Autor: Tolkien)
                for (int i = 1; i <= colunas; i++) {
                    acervo.append(metaData.getColumnName(i).toUpperCase()).append(": ")
                            .append(rs.getString(i)).append("  |  ");
                }
                acervo.append("\n"); // Pula linha para o próximo livro
            }

            // Se o laço terminar e a string estiver vazia, o banco não tem livros
            if (acervo.length() == 0) {
                return "Nenhum livro cadastrado no acervo no momento.";
            }

            return acervo.toString();

        }
    }
}
package ejemplo.orm;

import java.sql.*;

public class UsuarioDAO {
    private final String url = "jdbc:sqlite:usuarios.db";

    public UsuarioDAO() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL
            );
        """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nombre) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                System.out.println("Usuario insertado con ID: " + idGenerado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


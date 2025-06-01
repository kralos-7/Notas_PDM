import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConSingleton {
    private static SQLiteConSingleton instancia;
    private Connection conexion;
    private static final String URL = "jdbc:sqlite:mi_base_de_datos.db";

    // Constructor privado
    private SQLiteConSingleton() {
        try {
            // Registrar el driver (opcional desde Java 6, pero útil en casos antiguos)
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection(URL);
            System.out.println("✅ Conexión a SQLite establecida (Singleton).");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Método público para obtener la única instancia
    public static SQLiteConSingleton getInstancia() {
        if (instancia == null) {
            instancia = new SQLiteConSingleton();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}


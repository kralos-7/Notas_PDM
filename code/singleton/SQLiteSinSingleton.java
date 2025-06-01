import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteSinSingleton {
    private Connection conexion;

    public SQLiteSinSingleton() {
		try {
    		Class.forName("org.sqlite.JDBC");
    		conexion = DriverManager.getConnection("jdbc:sqlite:mi_base_de_datos.db");
		} catch (ClassNotFoundException e) {
    		System.out.println("No se encontró el driver JDBC de SQLite.");
		} catch (SQLException e) {
    		System.out.println("Error de conexión: " + e.getMessage());
		}
    }

    public Connection getConexion() {
        return conexion;
    }
}


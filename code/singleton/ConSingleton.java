import java.sql.Connection;

public class ConSingleton {
    public static void main(String[] args) {
        SQLiteConSingleton db1 = SQLiteConSingleton.getInstancia();
        SQLiteConSingleton db2 = SQLiteConSingleton.getInstancia();

        System.out.println("¿Misma instancia? " + (db1 == db2)); // true

        Connection conn1 = db1.getConexion();
        Connection conn2 = db2.getConexion();

        System.out.println("¿Misma conexión? " + (conn1 == conn2)); // true
    }
}


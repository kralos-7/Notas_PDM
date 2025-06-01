public class SinSingleton {
    public static void main(String[] args) {
        SQLiteSinSingleton db1 = new SQLiteSinSingleton();
        SQLiteSinSingleton db2 = new SQLiteSinSingleton();

        // Verifica que son instancias distintas
        System.out.println(db1 == db2); // false
    }
}


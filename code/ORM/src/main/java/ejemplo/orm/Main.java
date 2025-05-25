
public class Main {
     public static void main(String[] args) {
         UsuarioDAO dao = new UsuarioDAO();
        Usuario nuevo = new Usuario("Juan Pérez");
        dao.insertarUsuario(nuevo);
    }
}


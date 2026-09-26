// Punto de entrada del programa: abre automáticamente la interfaz gráfica.
import ui.GameController; // Controlador que construye y administra la ventana del juego.

// Clase principal que delega el inicio al controlador de la interfaz.
public class Main {

    // Método main: punto de inicio ejecutado por Java.
    public static void main(String[] args) {
        // La capa UI se encarga de construir la ventana y la partida.
        GameController.main(args);
    }
}

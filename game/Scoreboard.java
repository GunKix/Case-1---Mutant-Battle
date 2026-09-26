// Paquete de juego: contiene los contadores de la partida.
package game;

// Clase que almacena y actualiza el marcador de ambos equipos.
public class Scoreboard {

    // Cantidad de mutantes vivos del equipo A.
    public int aliveTeamA;

    // Cantidad de mutantes muertos del equipo A.
    public int deadTeamA;

    // Cantidad de mutantes vivos del equipo B.
    public int aliveTeamB;

    // Cantidad de mutantes muertos del equipo B.
    public int deadTeamB;

    // Constructor: inicia todos los contadores en cero.
    public Scoreboard() {
        aliveTeamA = 0;
        deadTeamA = 0;
        aliveTeamB = 0;
        deadTeamB = 0;
    }

    // Actualiza los cuatro contadores del marcador.
    public void updateScores(int aliveA, int deadA, int aliveB, int deadB) {
        // Asigna los vivos del equipo A.
        aliveTeamA = aliveA;
        // Asigna los muertos del equipo A.
        deadTeamA = deadA;
        // Asigna los vivos del equipo B.
        aliveTeamB = aliveB;
        // Asigna los muertos del equipo B.
        deadTeamB = deadB;
    }

    // Método de consola que muestra el marcador completo.
    public void displayBoard() {
        // Imprime el encabezado del marcador.
        System.out.println("MARCADOR GENERAL");
        // Imprime los datos del equipo A.
        System.out.println("Equipo A -> Vivos: " + aliveTeamA + " | Muertos: " + deadTeamA);
        // Imprime los datos del equipo B.
        System.out.println("Equipo B -> Vivos: " + aliveTeamB + " | Muertos: " + deadTeamB);
    }
}

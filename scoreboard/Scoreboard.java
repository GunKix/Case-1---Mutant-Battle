package scoreboard;

// Clase que representa el marcador que lleva el conteo de vivos y muertos de dos equipos
public class Scoreboard {

    // Atributo público: Cantidad de mutantes vivos en el Equipo A
    public int aliveTeamA;
    
    // Atributo público: Cantidad de mutantes muertos en el Equipo A
    public int deadTeamA;
    
    // Atributo público: Cantidad de mutantes vivos en el Equipo B
    public int aliveTeamB;
    
    // Atributo público: Cantidad de mutantes muertos en el Equipo B
    public int deadTeamB;

    // Constructor por defecto: Inicializa todos los contadores en cero
    public Scoreboard() {
        // Establece en 0 los mutantes vivos del equipo A
        this.aliveTeamA = 0;
        // Establece en 0 los mutantes muertos del equipo A
        this.deadTeamA = 0;
        // Establece en 0 los mutantes vivos del equipo B
        this.aliveTeamB = 0;
        // Establece en 0 los mutantes muertos del equipo B
        this.deadTeamB = 0;
    }

    // Método público para actualizar los 4 marcadores al mismo tiempo
    public void updateScores(int aliveA, int deadA, int aliveB, int deadB) {
        // Asigna el nuevo valor de vivos del equipo A
        this.aliveTeamA = aliveA;
        // Asigna el nuevo valor de muertos del equipo A
        this.deadTeamA = deadA;
        // Asigna el nuevo valor de vivos del equipo B
        this.aliveTeamB = aliveB;
        // Asigna el nuevo valor de muertos del equipo B
        this.deadTeamB = deadB;
    }

    // Método auxiliar para imprimir en pantalla el estado actual del marcador
    public void displayBoard() {
        // Muestra la cabecera del marcador
        System.out.println("MARCADOR GENERAL");
        // Imprime las estadísticas del Equipo A
        System.out.println("Equipo A -> Vivos: " + this.aliveTeamA + " | Muertos: " + this.deadTeamA);
        // Imprime las estadísticas del Equipo B
        System.out.println("Equipo B -> Vivos: " + this.aliveTeamB + " | Muertos: " + this.deadTeamB);
        
    }
}
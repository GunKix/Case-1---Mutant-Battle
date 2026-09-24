package Game; // Se declara el paquete por usar en la capa de juego

import java.util.Random; // Se importa la clase Random de Java para la generacion de numeros aleatorios

// Declaracion de una clase publica para manejar el mapa y los equipos que pelean
public class Battlefield { 

    private double width; // Establece la medida de ancho del campo de batalla
    private double height; // Establece la medida de alto del campo de batalla
    private Team teamA; // Establece el primer equipo que va a participar
    private Team teamB; // Establece el segundo equipo que va a participar

    // Constructor que permite la creacion del campo de batalla y define los equipos al azar
    public Battlefield(double width, double height) {
        this.width = width; // Indica que se recibe una medida de ancho 
        this.height = height; // Indica que se recibe una medida de alto
        
        Random generador = new Random(); // Establece que se debe crear un nuevo Random()
        
        // Establece un tamaño de equipo aleatorio de 3 a 11 integrantes sumandole 3 al Random()
        int teamSize = generador.nextInt(9) + 3; 

        // Indica que se crean los dos equipos asegurando que tengan la misma cantidad de mutantes
        this.teamA = new Team("Rojo", "Escudo", teamSize);
        this.teamB = new Team("Azul", "Shield", teamSize);
    }

    // Definicion del metodo que permite verificar si la partida termino, devuelve true o false
    public boolean isGameOver() {
        // Establece que se retorna true si el equipo A o el equipo B se quedaron con cero mutantes vivos
        return teamA.getAliveCount() == 0 || teamB.getAliveCount() == 0;
    }

    // Definicion de los metodos que permiten obtener la informacion del campo de batalla sin modificar los valores
    public double getWidth() { 
        return width; 
    }
    public double getHeight() { 
        return height; 
    }
    public Team getTeamA() { 
        return teamA; 
    }
    public Team getTeamB() { 
        return teamB; 
    }
}
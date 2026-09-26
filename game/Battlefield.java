// Paquete de juego: administra el escenario y el estado de la partida.
package game;

// Importa el contrato de actualización de la interfaz.
import ui.Observer;
// Importa una lista que almacenará los observadores.
import java.util.ArrayList;
// Importa la interfaz de lista.
import java.util.List;

// Clase que contiene los equipos, el marcador y las reglas de actualización.
public class Battlefield {

    // Ancho del campo.
    private final double width;

    // Alto del campo.
    private final double height;

    // Equipo rojo o equipo A.
    private final Team teamA;

    // Equipo azul o equipo B.
    private final Team teamB;

    // Marcador de la partida.
    private final Scoreboard scoreboard;

    // Observadores que deben redibujarse cuando cambia el estado.
    private final List<Observer> observers = new ArrayList<>();

    // Constructor: crea el campo con sus dimensiones y equipos.
    public Battlefield(double width, double height, Team teamA, Team teamB) {
        // Guarda el ancho.
        this.width = width;
        // Guarda el alto.
        this.height = height;
        // Guarda el equipo A.
        this.teamA = teamA;
        // Guarda el equipo B.
        this.teamB = teamB;
        // Crea el marcador de la partida.
        this.scoreboard = new Scoreboard();
    }

    // Actualiza el marcador y avisa a la interfaz.
    public void updateState() {
        // Cuenta los vivos y muertos del equipo A.
        int aliveA = teamA.getAliveCount();
        int deadA = teamA.getMembers().size() - aliveA;
        // Cuenta los vivos y muertos del equipo B.
        int aliveB = teamB.getAliveCount();
        int deadB = teamB.getMembers().size() - aliveB;
        // Guarda los nuevos valores en el marcador.
        scoreboard.updateScores(aliveA, deadA, aliveB, deadB);
        // Solicita a la vista que se redibuje.
        notifyObservers();
    }

    // Indica si algún equipo ya no tiene mutantes vivos.
    public boolean isGameOver() {
        return !teamA.hasAliveMembers() || !teamB.hasAliveMembers();
    }

    // Registra un observador de la interfaz.
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Elimina un observador de la interfaz.
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notifica a todos los observadores registrados.
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // Devuelve el ancho del campo.
    public double getWidth() {
        return width;
    }

    // Devuelve el alto del campo.
    public double getHeight() {
        return height;
    }

    // Devuelve el equipo A.
    public Team getTeamA() {
        return teamA;
    }

    // Devuelve el equipo B.
    public Team getTeamB() {
        return teamB;
    }

    // Devuelve el marcador actual.
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}

// Paquete de la capa de control: hilo de movimiento de cada mutante.
package control;

// Importa la pausa de movimiento.
import constants.IConstants;
// Importa el campo de batalla.
import game.Battlefield;
// Importa el mutante controlado por este hilo.
import model.Mutant;

// Clase que mueve un mutante de forma continua mediante un hilo independiente.
public class MutantControl extends Thread {

    // Mutante que este hilo controla.
    private final Mutant myMutant;

    // Velocidad de desplazamiento.
    private final double speed;

    // Campo donde se realiza el movimiento.
    private final Battlefield battlefield;

    // Motor que calcula la posición siguiente.
    private final MovementEngine movementEngine;

    // Constructor: recibe el mutante, su velocidad y el campo.
    public MutantControl(Mutant mutant, double speed, Battlefield battlefield) {
        // Guarda el mutante controlado.
        this.myMutant = mutant;
        // Guarda la velocidad asignada.
        this.speed = speed;
        // Guarda el campo de batalla.
        this.battlefield = battlefield;
        // Crea el motor de movimiento.
        this.movementEngine = new MovementEngine();
    }

    // Método run: mueve el mutante hasta que muera, termine la partida o se interrumpa.
    @Override
    public void run() {
        // Mantiene el movimiento mientras sea necesario.
        while (myMutant.isAlive() && !battlefield.isGameOver() && !Thread.currentThread().isInterrupted()) {
            // Calcula y aplica el siguiente movimiento.
            movementEngine.move(myMutant, battlefield, speed);
            try {
                // Espera el intervalo definido para producir una animación fluida.
                Thread.sleep(IConstants.MOVEMENT_SLEEP_TIME);
            } catch (InterruptedException exception) {
                // Restaura la interrupción y termina este hilo.
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

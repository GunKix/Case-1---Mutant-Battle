// Paquete de la capa de control: contiene el hilo de combate.
package control;

// Importa la pausa y el radio definidos en las constantes.
import constants.IConstants;
// Importa el campo que se vigila.
import game.Battlefield;

// Hilo que revisa continuamente si existen mutantes cercanos en combate.
public class CombatThread extends Thread {

    // Campo observado por este hilo.
    private final Battlefield field;

    // Administrador que resuelve los encuentros.
    private final CombatManager combatManager;

    // Constructor: recibe el campo que se debe vigilar.
    public CombatThread(Battlefield field) {
        // Guarda el campo de batalla.
        this.field = field;
        // Crea un único gestor para este ciclo de partida.
        this.combatManager = new CombatManager();
    }

    // Método run: ciclo de vida ejecutado cuando se inicia el hilo.
    @Override
    public void run() {
        // Continúa mientras haya partida y el hilo no haya sido interrumpido.
        while (!field.isGameOver() && !Thread.currentThread().isInterrupted()) {
            // Busca y resuelve los combates de este ciclo.
            combatManager.evaluateProximities(field);
            try {
                // Espera para no resolver combates demasiado rápido.
                Thread.sleep(IConstants.COMBAT_SLEEP_TIME);
            } catch (InterruptedException exception) {
                // Restaura la señal de interrupción y termina el hilo.
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

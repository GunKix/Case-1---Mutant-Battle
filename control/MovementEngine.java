// Paquete de la capa de control: cálculo del movimiento de los mutantes.
package control;

// Importa el radio de combate y los límites de la arena.
import constants.IConstants;
// Importa el campo de batalla.
import game.Battlefield;
// Importa la clase Team para identificar el equipo enemigo.
import game.Team;
// Importa la clase Mutant que se va a mover.
import model.Mutant;
// Importa el generador aleatorio para el desplazamiento sin objetivo.
import java.util.Random;

// Clase que calcula el siguiente movimiento de un mutante.
public class MovementEngine {

    // Generador de decisiones aleatorias.
    private final Random generator = new Random();

    // Mueve al mutante hacia el enemigo más cercano y mantiene su posición dentro del mapa.
    public void move(Mutant mutant, Battlefield battlefield, double moveSpeed) {
        // Los muertos o el juego terminado no permiten movimiento.
        if (!mutant.isAlive() || battlefield.isGameOver()) {
            return;
        }

        // Busca un enemigo vivo al que perseguir.
        Mutant target = findClosestEnemy(mutant, battlefield);
        // Desplazamiento horizontal calculado.
        double deltaX;
        // Desplazamiento vertical calculado.
        double deltaY;

        // Si no existe enemigo, se desplaza en una dirección aleatoria.
        if (target == null) {
            deltaX = (generator.nextDouble() * 2 - 1) * moveSpeed;
            deltaY = (generator.nextDouble() * 2 - 1) * moveSpeed;
        } else {
            // Calcula la diferencia entre el mutante y su objetivo.
            double differenceX = target.getX() - mutant.getX();
            double differenceY = target.getY() - mutant.getY();
            // Calcula la distancia hasta el objetivo.
            double distance = Math.hypot(differenceX, differenceY);

            // Si está lejos, se acerca en línea recta al enemigo.
            if (distance > IConstants.DEFAULT_COMBAT_RADIUS * 0.8) {
                // Evita la división por cero cuando están superpuestos.
                if (distance > 0) {
                    deltaX = differenceX / distance * moveSpeed;
                    deltaY = differenceY / distance * moveSpeed;
                } else {
                    deltaX = moveSpeed;
                    deltaY = 0;
                }
            } else {
                // Si ya está en distancia de combate, gira alrededor del objetivo.
                double angle = Math.atan2(differenceY, differenceX) + Math.PI / 2;
                deltaX = Math.cos(angle) * moveSpeed * 0.35;
                deltaY = Math.sin(angle) * moveSpeed * 0.35;
            }
        }

        // Calcula la nueva posición y la limita a los bordes de la arena.
        double newX = mutant.getX() + deltaX;
        double newY = mutant.getY() + deltaY;
        mutant.setPosition(clamp(newX, 0, battlefield.getWidth()),
                clamp(newY, 0, battlefield.getHeight()));
    }

    // Busca el enemigo vivo más cercano al mutante.
    private Mutant findClosestEnemy(Mutant mutant, Battlefield battlefield) {
        // Selecciona el equipo contrario según el nombre del equipo propio.
        Team enemyTeam = mutant.getTeamName().equals(battlefield.getTeamA().getName())
                ? battlefield.getTeamB() : battlefield.getTeamA();
        // Mejor candidato encontrado hasta ahora.
        Mutant closest = null;
        // Distancia del candidato actual.
        double closestDistance = Double.MAX_VALUE;

        // Revisa todos los enemigos del equipo contrario.
        for (Mutant candidate : enemyTeam.getMembers()) {
            // Los enemigos muertos no son objetivo.
            if (!candidate.isAlive()) {
                continue;
            }
            // Calcula la distancia hasta el candidato.
            double distance = Math.hypot(candidate.getX() - mutant.getX(), candidate.getY() - mutant.getY());
            // Conserva el enemigo más cercano.
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = candidate;
            }
        }
        return closest;
    }

    // Limita un valor entre un mínimo y un máximo.
    private double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}

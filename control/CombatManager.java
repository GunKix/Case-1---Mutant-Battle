// Paquete de la capa de control: administra los combates.
package control;

// Importa el radio de combate y las reglas del juego.
import constants.IConstants;
// Importa el campo que contiene a los equipos.
import game.Battlefield;
// Importa los mutantes que participarán en el combate.
import model.Mutant;
// Importa la colección que evita procesar dos veces al mismo mutante.
import java.util.HashSet;
// Importa el generador de decisiones aleatorias.
import java.util.Random;
// Importa la interfaz de conjunto utilizada.
import java.util.Set;

// Clase que busca enemigos cercanos y resuelve sus ataques y defensas.
public class CombatManager {

    // Distancia máxima para considerar que dos mutantes están en combate.
    private final double combatRadius = IConstants.DEFAULT_COMBAT_RADIUS;

    // Generador para decidir atacante y defensa de forma aleatoria.
    private final Random generator = new Random();

    // Revisa las distancias y resuelve los encuentros encontrados.
    public void evaluateProximities(Battlefield field) {
        // Guarda los mutantes que ya participaron en este ciclo.
        Set<Mutant> processed = new HashSet<>();

        // Recorre los mutantes del equipo A.
        for (Mutant first : field.getTeamA().getMembers()) {
            // Ignora muertos y mutantes ya procesados.
            if (!first.isAlive() || processed.contains(first)) {
                continue;
            }

            // Busca un enemigo del equipo B dentro del radio.
            for (Mutant second : field.getTeamB().getMembers()) {
                // Ignora muertos y enemigos ya procesados.
                if (!second.isAlive() || processed.contains(second)) {
                    continue;
                }

                // Calcula la distancia entre ambos mutantes.
                double distance = Math.hypot(first.getX() - second.getX(), first.getY() - second.getY());

                // Si están suficientemente cerca, resuelve un encuentro.
                if (distance <= combatRadius) {
                    // Marca a ambos para no repetir el mismo ataque en este ciclo.
                    processed.add(first);
                    processed.add(second);
                    // Ejecuta el combate entre los dos.
                    executeEncounter(first, second);
                    // Cada mutante participa como máximo una vez por ciclo.
                    break;
                }
            }
        }
    }

    // Resuelve el daño, la defensa y el crecimiento del poder de un encuentro.
    private void executeEncounter(Mutant first, Mutant second) {
        // Verifica nuevamente que ambos sigan vivos.
        if (!first.isAlive() || !second.isAlive()) {
            return;
        }

        // Elige aleatoriamente quién ataca y quién defiende.
        Mutant attacker = generator.nextBoolean() ? first : second;
        Mutant defender = attacker == first ? second : first;
        boolean defenderDefends = generator.nextBoolean();

        // Registra el ataque para que la interfaz pueda animarlo.
        attacker.registerAttack(defender.getX(), defender.getY());
        // Activa el efecto associated al poder del atacante.
        attacker.getPower().triggerEffect();
        // Obtiene el daño actual del atacante.
        int damage = attacker.getPower().getDamageCapacity();

        // Aplica el daño y aumenta el poder si el impacto fue válido.
        if (defender.receiveDamage(damage, defenderDefends)) {
            attacker.getPower().increaseDamage();
        }
    }
}

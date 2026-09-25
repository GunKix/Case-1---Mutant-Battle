// Paquete que concentra las constantes de configuración del juego.
package constants;

// Interface que define los valores utilized por las distintas capas.
public interface IConstants {

    // Energía inicial de cada mutante.
    int INITIAL_ENERGY = 100;

    // Capacidad mínima de defensa.
    int MIN_DEFENSE_CAPACITY = 1;

    // Capacidad máxima de defensa.
    int MAX_DEFENSE_CAPACITY = 3;

    // Daño mínimo que puede tener un poder al comenzar.
    int MIN_DAMAGE_CAPACITY = 1;

    // Daño máximo que puede tener un poder al ser asignado.
    int MAX_INITIAL_DAMAGE_CAPACITY = 3;

    // Daño máximo que puede alcanzar un poder durante la batalla.
    int MAX_DAMAGE_CAPACITY = 7;

    // Cantidad mínima de mutantes por equipo.
    int MIN_TEAM_SIZE = 3;

    // Cantidad máxima de mutantes por equipo.
    int MAX_TEAM_SIZE = 11;

    // Velocidad de desplazamiento de los mutantes.
    double DEFAULT_SPEED = 2.0;

    // Distancia a la que dos mutantes pueden comenzar a combatir.
    double DEFAULT_COMBAT_RADIUS = 30.0;

    // Ancho lógico del campo de batalla.
    double FIELD_WIDTH = 500.0;

    // Alto lógico del campo de batalla.
    double FIELD_HEIGHT = 400.0;

    // Margen usado para evitar que los mutantes nazcan pegados a un borde.
    double SPAWN_MARGIN = 0.05;

    // Pausa de los hilos de movimiento para producir una animación fluida.
    int MOVEMENT_SLEEP_TIME = 16;

    // Pausa del hilo que resuelve los combates.
    int COMBAT_SLEEP_TIME = 100;

    // Frecuencia con la que Swing redibuja el estado del juego.
    int REFRESH_RATE = 16;

    // Tamaño máximo de un mutante en la pantalla.
    int MUTANT_SIZE = 42;

    // Tamaño máximo del efecto visual de un ataque.
    int ATTACK_SIZE = 30;

    // Duración del efecto visual de un ataque, en milisegundos.
    long ATTACK_DURATION_MS = 350;
}

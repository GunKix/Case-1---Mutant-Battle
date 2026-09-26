// Paquete del modelo: contiene los datos y las reglas básicas de cada mutante.
package model;

// Importa la energía inicial y la escala de defensa.
import constants.IConstants;
// Importa el generador aleatorio para asignar la defensa.
import java.util.Random;

// Clase que representa a un mutante individual dentro de la batalla.
public class Mutant {

    // Identificador único del mutante.
    private final int id;

    // Posición horizontal actual.
    private volatile double x;

    // Posición vertical actual.
    private volatile double y;

    // Energía restante; se usa también como vida del mutante.
    private volatile double energy;

    // Nivel de defensa que reduce el daño recibido.
    private final int defenseCapacity;

    // Poder que porta el mutante.
    private final MutantPower power;

    // Indica si el mutante todavía participa en la partida.
    private volatile boolean isAlive;

    // Nombre del equipo al que pertenece.
    private final String teamName;

    // Momeno del último ataque, usado por la animación de la interfaz.
    private volatile long lastAttackTime;

    // Coordenada X del objetivo del último ataque.
    private volatile double attackTargetX;

    // Coordenada Y del objetivo del último ataque.
    private volatile double attackTargetY;

    // Constructor: crea un mutante vivo en la posición indicada.
    public Mutant(int id, double x, double y, String teamName, MutantPower power) {
        // Asigna el identificador recibido.
        this.id = id;
        // Asigna la posición horizontal inicial.
        this.x = x;
        // Asigna la posición vertical inicial.
        this.y = y;
        // Asigna el equipo del mutante.
        this.teamName = teamName;
        // Todos los mutantes nacen con la energía inicial definida.
        this.energy = IConstants.INITIAL_ENERGY;
        // Elige aleatoriamente una capacidad de defensa dentro del rango permitido.
        this.defenseCapacity = new Random().nextInt(IConstants.MAX_DEFENSE_CAPACITY)
                + IConstants.MIN_DEFENSE_CAPACITY;
        // Asigna el poder que podrá usar durante la batalla.
        this.power = power;
        // El mutante inicia vivo.
        this.isAlive = true;
    }

    // Recibe daño y devuelve true cuando el impacto fue válido.
    public boolean receiveDamage(int incomingDamage, boolean defends) {
        // Un mutante muerto no puede recibir más daño.
        if (!isAlive) {
            return false;
        }

        // Si se defiende, divide el daño por su capacidad de defensa.
        double actualDamage = defends ? (double) incomingDamage / defenseCapacity : incomingDamage;

        // Descuenta el daño de la energía actual.
        energy -= actualDamage;

        // Comprueba si el impacto dejó al mutante sin energía.
        if (energy <= 0) {
            // Evita mostrar energías negativas.
            energy = 0;
            // Marca al mutante como muerto.
            isAlive = false;
        }
        return true;
    }

    // Registra el momento y objetivo de un ataque para su animación.
    public void registerAttack(double targetX, double targetY) {
        // Guarda la coordenada X del objetivo.
        this.attackTargetX = targetX;
        // Guarda la coordenada Y del objetivo.
        this.attackTargetY = targetY;
        // Guarda la hora actual para que la vista pueda mostrar el efecto.
        this.lastAttackTime = System.currentTimeMillis();
    }

    // Devuelve la hora del último ataque.
    public long getLastAttackTime() {
        return lastAttackTime;
    }

    // Devuelve la coordenada X del objetivo del último ataque.
    public double getAttackTargetX() {
        return attackTargetX;
    }

    // Devuelve la coordenada Y del objetivo del último ataque.
    public double getAttackTargetY() {
        return attackTargetY;
    }

    // Devuelve el identificador del mutante.
    public int getId() {
        return id;
    }

    // Indica si el mutante sigue vivo.
    public boolean isAlive() {
        return isAlive;
    }

    // Devuelve el nombre del equipo.
    public String getTeamName() {
        return teamName;
    }

    // Devuelve la energía o vida restante.
    public double getEnergy() {
        return energy;
    }

    // Devuelve la posición horizontal.
    public double getX() {
        return x;
    }

    // Devuelve la posición vertical.
    public double getY() {
        return y;
    }

    // Actualiza la posición del mutante durante su movimiento.
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Devuelve el poder que porta el mutante.
    public MutantPower getPower() {
        return power;
    }
}

// Paquete del modelo: representa el poder y su efecto.
package model;

// Importa el límite máximo de daño de los poderes.
import constants.IConstants;

// Clase que contiene el nombre, el daño y el efecto de un poder.
public class MutantPower {

    // Nombre del poder, por ejemplo Fire o Water.
    private final String name;

    // Daño actual del poder; aumenta después de un impacto válido.
    private volatile int damageCapacity;

    // Efecto concreto que se activa al usar el poder.
    private final IPowerEffect effect;

    // Constructor: recibe el nombre, el daño inicial y el efecto asociado.
    public MutantPower(String name, int initialDamage, IPowerEffect effect) {
        // Guarda el nombre del poder.
        this.name = name;
        // Guarda el daño inicial.
        this.damageCapacity = initialDamage;
        // Guarda el efecto polimórfico.
        this.effect = effect;
    }

    // Aumenta el daño sin superar el límite máximo configurado.
    public void increaseDamage() {
        // Solo crece mientras no haya llegado al máximo.
        if (damageCapacity < IConstants.MAX_DAMAGE_CAPACITY) {
            // Incrementa el daño en una unidad.
            damageCapacity++;
        }
    }

    // Devuelve el daño actual del poder.
    public int getDamageCapacity() {
        return damageCapacity;
    }

    // Devuelve el nombre del poder, usado para elegir su imagen.
    public String getName() {
        return name;
    }

    // Activa el efecto asociado al poder.
    public void triggerEffect() {
        // Comprueba que exista un efecto antes de invocarlo.
        if (effect != null) {
            // Ejecuta la implementación concreta del efecto.
            effect.applyEffect();
        }
    }
}

package model; // Paquete de los modelos del juego

// Interfaz que define el contrato que deben cumplir todos los efectos de poder
public interface IPowerEffect {

    // Método que cada efecto concreto debe implementar para activar su lógica o animación
    void applyEffect();
}
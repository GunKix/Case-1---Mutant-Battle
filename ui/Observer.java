// Paquete de UI: define el contrato de las vistas que observan la partida.
package ui;

// Interfaz Observer del patrón de diseño Observer.
public interface Observer {

    // Método llamado por Battlefield cuando la partida cambia.
    void update();
}

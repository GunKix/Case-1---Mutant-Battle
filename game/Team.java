// Paquete de juego: representa a los bandos.
package game;

// Importa los mutantes que pertenecen al equipo.
import model.Mutant;
// Importa el límite máximo de miembros.
import constants.IConstants;
// Importa una lista dinámica.
import java.util.ArrayList;
// Importa la interfaz de lista.
import java.util.List;

// Clase que agrupa a los mutantes de un mismo bando.
public class Team {

    // Nombre del equipo, por ejemplo Rojo.
    private final String name;

    // Color utilizado por la interfaz.
    private final String color;

    // Símbolo o escudo del equipo.
    private final String shield;

    // Lista de mutantes del equipo.
    private final List<Mutant> members;

    // Constructor: crea un equipo con su identidad y lista vacía.
    public Team(String name, String color, String shield) {
        // Guarda el nombre.
        this.name = name;
        // Guarda el color.
        this.color = color;
        // Guarda el escudo.
        this.shield = shield;
        // Inicializa la lista de miembros.
        this.members = new ArrayList<>();
    }

    // Agrega un mutante si el equipo no superó el máximo permitido.
    public void addMember(Mutant mutant) {
        // Comprueba el límite configurado.
        if (members.size() < IConstants.MAX_TEAM_SIZE) {
            members.add(mutant);
        }
    }

    // Devuelve la lista de mutantes del equipo.
    public List<Mutant> getMembers() {
        return members;
    }

    // Devuelve el nombre del equipo.
    public String getName() {
        return name;
    }

    // Devuelve el color del equipo.
    public String getColor() {
        return color;
    }

    // Devuelve el escudo del equipo.
    public String getShield() {
        return shield;
    }

    // Indica si al menos un mutante del equipo sigue vivo.
    public boolean hasAliveMembers() {
        return getAliveCount() > 0;
    }

    // Cuenta los mutantes vivos del equipo.
    public int getAliveCount() {
        // Inicia el contador en cero.
        int aliveCount = 0;
        // Recorre todos los miembros.
        for (Mutant mutant : members) {
            // Suma los que siguen vivos.
            if (mutant.isAlive()) {
                aliveCount++;
            }
        }
        return aliveCount;
    }
}

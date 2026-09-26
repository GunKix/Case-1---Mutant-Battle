// Paquete de juego: fábrica de equipos y campo de batalla.
package game;

// Importa las constantes de tamaño, velocidad y damage.
import constants.IConstants;
// Importa la clase Mutant.
import model.Mutant;
// Importa la clase MutantPower.
import model.MutantPower;
// Importa la interfaz de efectos.
import model.IPowerEffect;
// Importa los efectos concretos disponibles.
import model.FireEffect;
import model.WaterEffect;
import model.EarthEffect;
import model.WindEffect;
import model.ElectricityEffect;
import model.RockEffect;
import model.LaserEffect;
import model.BeastEffect;
import model.IceEffect;
import model.ThornsEffect;
import model.SandEffect;
// Importa las estructuras para lista y azar.
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Clase que crea una partida completa con dos equipos y sus mutantes.
public final class TeamFactory {

    // Constructor privado: la clase solo ofrece métodos estáticos.
    private TeamFactory() {
    }

    // Crea el campo y llena ambos equipos con la cantidad solicitada.
    public static Battlefield createBattlefield(int teamSize) {
        // Crea el equipo rojo.
        Team teamA = new Team("Rojo", "ROJO", "@");
        // Crea el equipo azul.
        Team teamB = new Team("Azul", "AZUL", "#");
        // Llena el equipo A con identificadores desde 1.
        fillTeam(teamA, teamSize, 1);
        // Llena el equipo B con identificadores únicos después de A.
        fillTeam(teamB, teamSize, teamSize + 1);
        // Devuelve el campo con las dimensiones de las constantes.
        return new Battlefield(IConstants.FIELD_WIDTH, IConstants.FIELD_HEIGHT, teamA, teamB);
    }

    // Genera los mutantes de un equipo en distintas posiciones de la arena.
    private static void fillTeam(Team team, int teamSize, int idStart) {
        Random random = new Random();
        // Calcula el área útil usando el margen configurado.
        double minX = IConstants.FIELD_WIDTH * IConstants.SPAWN_MARGIN;
        double maxX = IConstants.FIELD_WIDTH * (1 - IConstants.SPAWN_MARGIN);
        double minY = IConstants.FIELD_HEIGHT * IConstants.SPAWN_MARGIN;
        double maxY = IConstants.FIELD_HEIGHT * (1 - IConstants.SPAWN_MARGIN);

        // Crea la cantidad de mutantes solicitada.
        for (int index = 0; index < teamSize; index++) {
            // Elige una posición X dentro del área válida.
            double x = minX + random.nextDouble() * (maxX - minX);
            // Elige una posición Y dentro del área válida.
            double y = minY + random.nextDouble() * (maxY - minY);
            // Crea un poder aleatorio para el mutante.
            MutantPower power = randomPower();
            // Crea el mutante con su identificador único.
            Mutant mutant = new Mutant(idStart + index, x, y, team.getName(), power);
            // Agrega el mutante al equipo.
            team.addMember(mutant);
        }
    }

    // Crea un poder aleatorio con efecto y daño inicial.
    private static MutantPower randomPower() {
        // Crea la lista de efectos disponibles.
        List<IPowerEffect> effects = new ArrayList<>();
        effects.add(new FireEffect());
        effects.add(new WaterEffect());
        effects.add(new EarthEffect());
        effects.add(new WindEffect());
        effects.add(new ElectricityEffect());
        effects.add(new RockEffect());
        effects.add(new LaserEffect());
        effects.add(new BeastEffect());
        effects.add(new IceEffect());
        effects.add(new ThornsEffect());
        effects.add(new SandEffect());

        // Elige uno de los efectos de forma aleatoria.
        Random random = new Random();
        IPowerEffect effect = effects.get(random.nextInt(effects.size()));
        // Deriva el nombre del efecto para asociarlo con la imagen correcta.
        String powerName = effect.getClass().getSimpleName().replace("Effect", "");
        // Calcula el daño inicial dentro del rango permitido.
        int initialDamage = IConstants.MIN_DAMAGE_CAPACITY
                + random.nextInt(IConstants.MAX_INITIAL_DAMAGE_CAPACITY - IConstants.MIN_DAMAGE_CAPACITY + 1);
        // Devuelve el poder configurado.
        return new MutantPower(powerName, initialDamage, effect);
    }
}

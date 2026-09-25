// Paquete de UI: contiene la ventana y el lienzo del juego.
package ui;

// Importa las constantes de la aplicación.
import constants.IConstants;
// Importa el hilo de combate.
import control.CombatThread;
// Importa el hilo de movimiento.
import control.MutantControl;
// Importa el campo de batalla.
import game.Battlefield;
// Importa la clase Team para los rótulos.
import game.Team;
// Importa la fábrica de equipos.
import game.TeamFactory;
// Importa la clase Mutant para crear los hilos.
import model.Mutant;
// Importa los componentes Swing.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// Controlador que construye la GUI, inicia la partida y actualiza los marcadores.
public class GameController {

    // Ventana principal.
    private JFrame frame;

    // Lienzo donde se dibuja la arena.
    private GameView view;

    // Rótulo del equipo A.
    private JLabel scoreALabel;

    // Rótulo del equipo B.
    private JLabel scoreBLabel;

    // Rótulo que indica quién lidera.
    private JLabel statusLabel;

    // Botón para comenzar otra partida.
    private JButton restartButton;

    // Campo de la partida activa.
    private Battlefield field;

    // Timer que refresca el estado de la interfaz.
    private Timer gameTimer;

    // Hilos de la partida actual.
    private final List<Thread> gameThreads = new ArrayList<>();

    // Método main: crea la interfaz en el hilo seguro de Swing.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameController::new);
    }

    // Constructor: construye la ventana y comienza la primera partida.
    public GameController() {
        buildUi();
        startNewGame();
    }

    // Construye la ventana, el lienzo y el panel de marcadores.
    private void buildUi() {
        // Crea la ventana principal.
        frame = new JFrame("Batalla de Mutantes");
        // Permite que la ventana cierre el programa.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Crea el lienzo sin campo inicial.
        view = new GameView(null);
        // Crea los rótulos de los equipos.
        scoreALabel = new JLabel();
        scoreBLabel = new JLabel();
        // Centra el mensaje de estado.
        statusLabel = new JLabel("En juego");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Aplica colores a los rótulos de cada equipo.
        configureScoreLabel(scoreALabel, new Color(100, 28, 28));
        configureScoreLabel(scoreBLabel, new Color(25, 48, 115));
        // Crea el botón de nueva partida.
        restartButton = new JButton("Nueva partida");
        restartButton.setEnabled(false);
        restartButton.addActionListener(event -> startNewGame());

        // Crea el panel inferior de la interfaz.
        JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.add(scoreALabel);
        scorePanel.add(scoreBLabel);
        scorePanel.add(statusLabel);
        scorePanel.add(restartButton);

        // Organiza el lienzo al centro y los marcadores abajo.
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(view, BorderLayout.CENTER);
        content.add(scorePanel, BorderLayout.SOUTH);
        // Ajusta, centra y muestra la ventana.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Configura el color, borde y centrado de un rótulo.
    private void configureScoreLabel(JLabel label, Color background) {
        label.setOpaque(true);
        label.setBackground(background);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    // Pide el tamaño de los equipos y crea una partida nueva.
    private void startNewGame() {
        int teamSize = askTeamSize();
        // Si el usuario cancela, no se crea ninguna partida.
        if (teamSize < 0) {
            return;
        }
        // Detiene la partida anterior antes de crear otra.
        stopCurrentGame();
        // Crea los equipos y el campo de batalla.
        field = TeamFactory.createBattlefield(teamSize);
        // Registra el lienzo como observador del campo.
        field.addObserver(view);
        // Conecta el campo nuevo con el lienzo.
        view.setField(field);
        // Deja el botón reinicio deshabilitado mientras se juega.
        restartButton.setEnabled(false);
        statusLabel.setText("En juego");
        updateScoreCards();

        // Inicia el hilo único de combate.
        startThread(new CombatThread(field));
        // Inicia un hilo de movimiento por cada mutante.
        startMovementThreads();
        // Inicia el refresco periódico de la pantalla.
        gameTimer = new Timer(IConstants.REFRESH_RATE, event -> refreshGame());
        gameTimer.start();
    }

    // Crea y registra los hilos de movimiento de los dos equipos.
    private void startMovementThreads() {
        for (Mutant mutant : field.getTeamA().getMembers()) {
            startThread(new MutantControl(mutant, IConstants.DEFAULT_SPEED, field));
        }
        for (Mutant mutant : field.getTeamB().getMembers()) {
            startThread(new MutantControl(mutant, IConstants.DEFAULT_SPEED, field));
        }
    }

    // Configura un hilo como demonio, lo registra y lo inicia.
    private void startThread(Thread thread) {
        thread.setDaemon(true);
        gameThreads.add(thread);
        thread.start();
    }

    // Detiene el timer y todos los hilos de la partida actual.
    private void stopCurrentGame() {
        if (gameTimer != null) {
            gameTimer.stop();
            gameTimer = null;
        }
        if (field != null) {
            field.removeObserver(view);
        }
        for (Thread thread : gameThreads) {
            thread.interrupt();
        }
        gameThreads.clear();
    }

    // Actualiza la pantalla y finaliza la partida si ya no quedan enemigos.
    private void refreshGame() {
        if (field == null) {
            return;
        }
        field.updateState();
        updateScoreCards();
        if (field.isGameOver()) {
            finishGame();
        }
    }

    // Actualiza los rótulos de ambos equipos y el mensaje de ventaja.
    private void updateScoreCards() {
        int aliveA = field.getTeamA().getAliveCount();
        int aliveB = field.getTeamB().getAliveCount();
        scoreALabel.setText(teamText(field.getTeamA(), aliveA));
        scoreBLabel.setText(teamText(field.getTeamB(), aliveB));
        statusLabel.setText(leaderText(aliveA, aliveB));
    }

    // Construye el texto visible de un equipo.
    private String teamText(Team team, int alive) {
        return "<html><b>" + team.getName() + " " + team.getShield() + "</b><br>Vivos: " + alive + "</html>";
    }

    // Indica cuál equipo tiene más mutantes vivos.
    private String leaderText(int aliveA, int aliveB) {
        if (aliveA == aliveB) {
            return "Empate";
        }
        Team leader = aliveA > aliveB ? field.getTeamA() : field.getTeamB();
        int advantage = Math.abs(aliveA - aliveB);
        return "Lidera: " + leader.getName() + " (" + advantage + " de ventaja)";
    }

    // Detiene la partida, muestra al ganador y habilita el reinicio.
    private void finishGame() {
        Team winner = field.getTeamA().hasAliveMembers() ? field.getTeamA() : field.getTeamB();
        stopCurrentGame();
        updateScoreCards();
        statusLabel.setText("Ganó: " + winner.getName());
        restartButton.setEnabled(true);
        JOptionPane.showMessageDialog(frame, "Gana el equipo " + winner.getName()
                + " (" + winner.getColor() + " " + winner.getShield() + ")");
    }

    // Solicita y valida la cantidad de mutantes por equipo.
    private int askTeamSize() {
        while (true) {
            String input = JOptionPane.showInputDialog(frame,
                    "Mutantes por equipo (" + IConstants.MIN_TEAM_SIZE + "-" + IConstants.MAX_TEAM_SIZE + "):",
                    String.valueOf(IConstants.MIN_TEAM_SIZE));
            if (input == null) {
                return -1;
            }
            try {
                int size = Integer.parseInt(input.trim());
                if (size >= IConstants.MIN_TEAM_SIZE && size <= IConstants.MAX_TEAM_SIZE) {
                    return size;
                }
                JOptionPane.showMessageDialog(frame,
                        "Ingresa un número entre " + IConstants.MIN_TEAM_SIZE + " y " + IConstants.MAX_TEAM_SIZE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Ingresa un número válido");
            }
        }
    }
}

// Paquete de UI: responsible de dibujar la arena y los mutantes.
package ui;

// Importa la configuración de tamaños, tiempos y radios.
import constants.IConstants;
// Importa el campo que contiene los datos que se dibujarán.
import game.Battlefield;
// Importa el equipo que se recorrerá.
import game.Team;
// Importa el mutante que se dibuja.
import model.Mutant;
// Importa las clases de dibujo de Java.
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
// Importa las clases de archivos e imágenes.
import java.io.File;
import java.io.IOException;
// Importa las colecciones asocian poderes con imágenes.
import java.util.HashMap;
import java.util.Map;
// Importa el lector estándar de imágenes y el panel Swing.
import javax.imageio.ImageIO;
import javax.swing.JPanel;

// Lienzo que dibuja el fondo, los mutantes, sus barras de vida y los ataques.
public class GameView extends JPanel implements Observer {

    // Carpeta que contiene los recursos gráficos.
    private static final String IMAGE_PATH = "Imagenes" + File.separator;

    // Campo de batalla que se representa en el lienzo.
    private Battlefield field;

    // Asocia el nombre de cada poder con su imagen de mutante.
    private final Map<String, Image> powerImages = new HashMap<>();

    // Asocia el nombre de cada poder con su imagen de ataque.
    private final Map<String, Image> attackImages = new HashMap<>();

    // Imagen de fondo de la arena.
    private Image background;

    // Constructor: carga las imágenes y prepara el tamaño del lienzo.
    public GameView(Battlefield field) {
        // Guarda el campo que se dibujará.
        this.field = field;
        // Carga todos los recursos gráficos.
        loadImages();
        // Define el tamaño preferido según las constantes del campo.
        setPreferredSize(new Dimension((int) IConstants.FIELD_WIDTH, (int) IConstants.FIELD_HEIGHT));
    }

    // Cambia el campo que se debe dibujar y solicita un repintado.
    public void setField(Battlefield newField) {
        this.field = newField;
        repaint();
    }

    // Carga el fondo y las imágenes de los once poderes.
    private void loadImages() {
        // Carga la imagen de la arena.
        background = load("background");
        // Asocia los poderes con los sprites de los mutantes.
        powerImages.put("Fire", load("fuego"));
        powerImages.put("Water", load("agua"));
        powerImages.put("Earth", load("tierra"));
        powerImages.put("Wind", load("viento"));
        powerImages.put("Electricity", load("rayo"));
        powerImages.put("Rock", load("piedra"));
        powerImages.put("Laser", load("laser"));
        powerImages.put("Beast", load("bestia"));
        powerImages.put("Ice", load("hielo"));
        powerImages.put("Thorns", load("espinas"));
        powerImages.put("Sand", load("arena"));
        // Asocia los poderes con sus imágenes de ataque.
        attackImages.put("Fire", load("fuego_1"));
        attackImages.put("Water", load("agua_1"));
        attackImages.put("Earth", load("tierra_1"));
        attackImages.put("Wind", load("viento_1"));
        attackImages.put("Electricity", load("rayo_1"));
        attackImages.put("Rock", load("piedra_1"));
        attackImages.put("Laser", load("laser_1"));
        attackImages.put("Beast", load("bestia_1"));
        attackImages.put("Ice", load("hielo_1"));
        attackImages.put("Thorns", load("espinas_1"));
        attackImages.put("Sand", load("arena_1"));
    }

    // Carga una imagen PNG y devuelve null si no existe o no se puede decodificar.
    private Image load(String baseName) {
        // Construye la ruta del recurso.
        File file = new File(IMAGE_PATH + baseName + ".png");
        // Si el archivo no existe, no hay imagen disponible.
        if (!file.exists()) {
            return null;
        }
        try {
            // ImageIO carga imágenes PNG de forma compatible con Swing.
            return ImageIO.read(file);
        } catch (IOException exception) {
            // Devuelve null para activar el dibujo de respaldo.
            return null;
        }
    }

    // Método del patrón Observer: pide a Swing que se repinte.
    @Override
    public void update() {
        repaint();
    }

    // Dibuja el contenido completo del panel.
    @Override
    protected void paintComponent(Graphics graphics) {
        // Prepara el panel y limpia el área anterior.
        super.paintComponent(graphics);
        // Convierte el objeto de dibujo a Graphics2D.
        Graphics2D g2 = (Graphics2D) graphics;
        // Activa el suavizado de líneas.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Activa el renderizado de mayor calidad.
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // Dibuja el fondo de la arena.
        drawBackground(g2);
        // Si existe un campo, dibuja a los participantes.
        if (field != null) {
            drawMutants(g2);
        }
    }

    // Dibuja el fondo cubriendo todo el panel sin deformarlo.
    private void drawBackground(Graphics2D g2) {
        // Usa un color de respaldo si la imagen no está disponible.
        if (background == null) {
            g2.setColor(new Color(35, 35, 45));
            g2.fillRect(0, 0, getWidth(), getHeight());
            return;
        }
        // Obtiene las dimensiones reales de la imagen.
        int imageWidth = background.getWidth(this);
        int imageHeight = background.getHeight(this);
        // Calcula la escala necesaria para cubrir el panel.
        double scale = Math.max((double) getWidth() / imageWidth, (double) getHeight() / imageHeight);
        int width = (int) Math.ceil(imageWidth * scale);
        int height = (int) Math.ceil(imageHeight * scale);
        // Dibuja la imagen centrada y recortada por los bordes del panel.
        g2.drawImage(background, (getWidth() - width) / 2, (getHeight() - height) / 2, width, height, null);
    }

    // Convierte las coordenadas lógicas a píxeles y dibuja ambos equipos.
    private void drawMutants(Graphics2D g2) {
        double scaleX = getWidth() / IConstants.FIELD_WIDTH;
        double scaleY = getHeight() / IConstants.FIELD_HEIGHT;
        drawMutantsOfTeam(g2, field.getTeamA(), scaleX, scaleY);
        drawMutantsOfTeam(g2, field.getTeamB(), scaleX, scaleY);
    }

    // Dibuja los mutantes de un equipo y sus ataques activos.
    private void drawMutantsOfTeam(Graphics2D g2, Team team, double scaleX, double scaleY) {
        Color teamColor = teamColorFor(team.getColor());
        // Recorre todos los miembros del equipo.
        for (Mutant mutant : team.getMembers()) {
            // Convierte la posición lógica a píxeles.
            int x = (int) (mutant.getX() * scaleX);
            int y = (int) (mutant.getY() * scaleY);
            drawMutant(g2, mutant, x, y, teamColor);
            drawAttackEffect(g2, mutant, x, y, scaleX, scaleY);
        }
    }

    // Dibuja el sprite, el color del equipo y la barra de vida de un mutante.
    private void drawMutant(Graphics2D g2, Mutant mutant, int x, int y, Color teamColor) {
        int size = IConstants.MUTANT_SIZE;
        // Busca el sprite según el poder del mutante.
        Image image = powerImages.get(mutant.getPower().getName());
        if (mutant.isAlive()) {
            // Dibuja el sprite o un círculo de respaldo si la imagen falla.
            if (image != null) {
                drawImagePreservingAspect(g2, image, x, y, size);
            } else {
                drawFallback(g2, mutant, x, y, size, teamColor);
            }
            // Dibuja el anillo que identifica al equipo.
            g2.setColor(teamColor);
            g2.drawOval(x - size / 2, y - size / 2, size, size);
            // Dibuja la energía restante.
            drawHealthBar(g2, mutant, x, y, size);
        } else {
            // Muestra el sprite muerto con transparencia.
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            if (image != null) {
                drawImagePreservingAspect(g2, image, x, y, size);
            }
            // Restaura la transparencia normal.
            g2.setComposite(AlphaComposite.SrcOver);
            // Dibuja una X roja sobre el mutante muerto.
            g2.setColor(Color.RED);
            g2.drawLine(x - size / 3, y - size / 3, x + size / 3, y + size / 3);
            g2.drawLine(x - size / 3, y + size / 3, x + size / 3, y - size / 3);
        }
    }

    // Dibuja una imagen respetando su proporción original.
    private void drawImagePreservingAspect(Graphics2D g2, Image image, int centerX, int centerY, int maxSize) {
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        // Usa un tamaño cuadrado si la imagen aún no tiene dimensiones.
        if (imageWidth <= 0 || imageHeight <= 0) {
            g2.drawImage(image, centerX - maxSize / 2, centerY - maxSize / 2, maxSize, maxSize, null);
            return;
        }
        double scale = Math.min((double) maxSize / imageWidth, (double) maxSize / imageHeight);
        int width = (int) Math.round(imageWidth * scale);
        int height = (int) Math.round(imageHeight * scale);
        g2.drawImage(image, centerX - width / 2, centerY - height / 2, width, height, null);
    }

    // Dibuja un círculo visible cuando el sprite de un poder no está disponible.
    private void drawFallback(Graphics2D g2, Mutant mutant, int x, int y, int size, Color teamColor) {
        g2.setColor(teamColor);
        g2.fillOval(x - size / 2, y - size / 2, size, size);
        g2.setColor(Color.WHITE);
        String powerName = mutant.getPower().getName();
        String initial = powerName.isEmpty() ? "?" : powerName.substring(0, 1);
        g2.drawString(initial, x - 4, y + 5);
    }

    // Dibuja el proyectil durante el tiempo de animación del ataque.
    private void drawAttackEffect(Graphics2D g2, Mutant mutant, int x, int y, double scaleX, double scaleY) {
        if (!mutant.isAlive()) {
            return;
        }
        Image image = attackImages.get(mutant.getPower().getName());
        long elapsed = System.currentTimeMillis() - mutant.getLastAttackTime();
        // Sale si la imagen no existe o la animación ya terminó.
        if (image == null || elapsed < 0 || elapsed > IConstants.ATTACK_DURATION_MS) {
            return;
        }
        double progress = elapsed / (double) IConstants.ATTACK_DURATION_MS;
        int targetX = (int) (mutant.getAttackTargetX() * scaleX);
        int targetY = (int) (mutant.getAttackTargetY() * scaleY);
        int effectX = (int) (x + (targetX - x) * progress);
        int effectY = (int) (y + (targetY - y) * progress);
        drawImagePreservingAspect(g2, image, effectX, effectY, IConstants.ATTACK_SIZE);
    }

    // Dibuja la barra de energía encima del mutante.
    private void drawHealthBar(Graphics2D g2, Mutant mutant, int x, int y, int size) {
        int barWidth = size;
        int barHeight = 5;
        int barX = x - size / 2;
        int barY = y - size / 2 - 9;
        double percent = Math.max(0, Math.min(1, mutant.getEnergy() / IConstants.INITIAL_ENERGY));
        Color barColor = percent > 0.5 ? Color.GREEN : percent > 0.25 ? Color.YELLOW : Color.RED;
        // Dibuja el fondo negro de la barra.
        g2.setColor(Color.BLACK);
        g2.fillRect(barX - 1, barY - 1, barWidth + 2, barHeight + 2);
        // Dibuja la parte proporcional a la energía.
        g2.setColor(barColor);
        g2.fillRect(barX, barY, (int) (barWidth * percent), barHeight);
        // Muestra el valor numérico de la energía.
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf((int) mutant.getEnergy()), barX, barY - 3);
    }

    // Convierte el nombre de color de un equipo en un color de Java.
    private Color teamColorFor(String colorName) {
        // Usa gris si el equipo no tiene color.
        if (colorName == null) {
            return Color.GRAY;
        }
        String color = colorName.toUpperCase();
        // Color del equipo rojo.
        if (color.contains("ROJO")) {
            return new Color(210, 60, 60);
        }
        // Color del equipo azul.
        if (color.contains("AZUL")) {
            return new Color(70, 110, 220);
        }
        // Color alternativa para equipos verdes.
        if (color.contains("VERDE")) {
            return new Color(70, 180, 90);
        }
        return Color.GRAY;
    }
}

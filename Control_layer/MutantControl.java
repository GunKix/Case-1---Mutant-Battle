package Control; // Se declara el paquete por usar en la capa de control

import Model.Mutant; // Se importa la clase Mutant desde la capa Model
import java.util.Random; // Se importa la clase Random de Java para la generacion de numeros aleatorios

// Declaracion de una clase publica que hereda de Thread para controlar al mutante y sus peleas en un solo lugar
public class MutantControl extends Thread { 

    private Mutant myMutant; // Establece un mutante especifico de modo que este hilo lo pueda controlar
    private double speed; // Establece un valor de velocidad de movimiento para el mutante
    private double attackRadius = 10.0; // Establece el radio en el cual el mutante detecta enemigos
    private Random generador; // Establece una clase con un atributo de aleatoriedad

    // Constructor que permite la creacion del control integrando al mutante y su velocidad dada
    public MutantControl(Mutant mutant, double speed) {
        this.myMutant = mutant; // Indica que se recibe el mutante a controlar
        this.speed = speed; // Indica que se recibe la velocidad de movimiento
        this.generador = new Random(); // Establece que se crea el Random() para las decisiones
    }

    // Definicion del metodo run usando polimorfismo sobre Thread para el ciclo de vida del mutante
    @Override
    public void run() {
        // Establece que se repitan las acciones mientras el mutante siga vivo
        while (myMutant.isAlive()) {
            
            moveArithmetically(); // Indica que el mutante cambia de posicion de forma aritmetica
            
            myMutant.scan(); // Indica que el mutante busca enemigos en el radio establecido
            
            // Establece que si hay un enemigo en el radio, se toman decisiones de combate
            if (isEnemyInRadius()) {
                myMutant.react(); // Indica que el mutante reacciona al encontrar oponente
                decideCombatAction(); // Indica que se toma la decision sobre el oponente
            }

            try {
                // Establece una pausa en el hilo de 200ms para no saturar la memoria
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                System.out.println("Se detuvo el hilo de comportamiento");
            }
        }
    }

    // Definicion del metodo que permite calcular la nueva posicion del mutante, este metodo no retorna nada
    private void moveArithmetically() {
        // Establece un cambio aleatorio en x y y que se multiplica por la velocidad
        double deltaX = (generador.nextDouble() * 2 - 1) * speed; 
        double deltaY = (generador.nextDouble() * 2 - 1) * speed;

        myMutant.move(); // Llama al metodo establecido previamente para anunciar el movimiento
    }

    // Definicion del metodo que permite validar si hay enemigos en el radio de ataque
    private boolean isEnemyInRadius() {
        // Retorna un booleano al azar temporalmente para la deteccion de oponentes
        return generador.nextBoolean(); 
    }

    // Definicion del metodo que permite decidir entre atacar o defender, este metodo no retorna nada
    private void decideCombatAction() {
        boolean willAttack = generador.nextBoolean(); // Establece un 50% de probabilidad
        
        // Indica que si el valor es verdadero, ataca; si es falso, defiende
        if (willAttack) {
            myMutant.attack(); 
        } else {
            myMutant.defend(); 
        }
    }

    // Definicion del metodo unificado que permite resolver el combate y las matematicas del daño
    public void resolveCombat(Mutant attacker, Mutant defender, boolean defenderDefends) {
        
        int attackDamage = 3; // Establece un valor de daño temporal para prevenir errores
        int defenderDefense = 2; // Establece un valor de defensa temporal para prevenir errores
        int damageDealt = 0; // Establece el valor inicial del daño final a aplicar al oponente

        // Establece que si el oponente defiende, el daño se divide entre su capacidad de defensa
        if (defenderDefends) {
            damageDealt = attackDamage / defenderDefense;
            System.out.println("Se esta defendiendo del ataque. Daño recibido: " + damageDealt);
        } else {
            // Establece que si el oponente no defiende, el daño se recibe por completo
            damageDealt = attackDamage;
            System.out.println("No se esta defendiendo del ataque. Daño recibido: " + damageDealt);
        }

        // Establece que si el daño efectuado es mayor a 0, el poder del mutante atacante incrementa en 1
        if (damageDealt > 0) {
            System.out.println("El ataque redujo la energia del oponente, el poder ha incrementado");
        }
    }
}
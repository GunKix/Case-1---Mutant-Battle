package Model; // Se declara el paquete por usar 

import java.util.Random; // Se importa la clase Random de Java para lla generacion de numeros aleatorios

public class Mutant { // Declaracion de una clase publica llamada mutante para ser accedida desde otros lados del codigo

    private int id; // Establece un identificador unico de modo que no se pueda modificar su valor desde otro sector del codigo
    private double x; // Establece una posicion horizontal unico de modo que no se pueda modificar su valor desde otro sector del codigo
    private double y; // Establece una posicion vertical unico de modo que no se pueda modificar su valor desde otro sector del codigo
    private int energy; // Establece un valor para el nivel de energia del mutante
    private int defenseCapacity; // Establece la capacidad de defensa de cada mutante
    private MutantPower power; // Establece una clase con un atributo de poder
    private boolean isAlive; // Establece si el mutante sigue vivo o ya ha fallecido

    // Constructor que permite la creacion del mutante con aspectos basicos o esenciales
    public Mutant(int id, double x, double y, int energy, int defenseCapacity, boolean isAlive){
        this.id = id;  //Indica que se recibe un id unico para cada mutante
        this.x = x;  //Indica que se recibe una posicion en x dada para el mutante
        this.y = y;  //Indica que se recibe una posicion y dada para el mutante
        this.energy = 100; //Indica que se recibe una energia para el mutante

        //Crear nueva clase y atributo para establecer con Random() la capacidad de defensa del mutante
        Random generador = new Random();  //Establece que se debe crear un nuevo Random() segun sea creado el mutante para variedad en defensa
        this.defenseCapacity = generador.nextInt(3) + 1;  //Indica que el mutante tendra n capacidad de defensa valor de 1 a 3 por sumarle 1 al Random()

        this.isAlive = true;  //Indica que el mutante aun esta vivo dado que se acaba de crear
    }

    // Definicion del metodo que permite el movimiento del mutante, este metodo no retorna nada solo modifica valores internamente
    public void move() {
        // Pendiente: enlazar con el MovementeEngine para modificar las variables "x" y "y"
        System.out.println("El mutante se esta moviendo");
    }

    // Definicion del metodo que permite el detectar oponentes del mutante, este metodo no retorna nada solo modifica valores internamente
    public void scan() {
        // Pendiente: enlazar con el CombatEngine para establecer la deteccion de oponentes en el campo de batalla
        System.out.println("Se esta detectando el oponente....");
    }

    // Definicion del metodo que permite tomar decisiones sobre el oponente, este metodo no retorna nada solo modifica valores internamente
    public void react() {
        // Pendiente: enlazar con el CombatThread para establecer si se va a atacar o defender del oponente
        System.out.println("Se esta detectando tomando decision sobre el oponente....");
    }

    // Definicion del metodo que permite atacar el oponente, este metodo no retorna nada solo modifica valores internamente
    public void attack() {
        // Pendiente: enlazar con CombatManager, MutantPower y damageCapacity para determinar el metodo asociado
        System.out.println("Se esta atacando al oponente....");
    }

    // Definicion del metodo que permite defenderse del oponente, este metodo no retorna nada solo modifica valores internamente
    public void defend() {
        // Pendiente: enlazar con defenseCapacity para determinar el metodo asociado
        System.out.println("Se esta defendiendo del oponente....");
    }
}
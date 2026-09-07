#  Case 1 - Mutant Battle

> **Bryan Leiva - Daniel Sánchez**  
> Models and specifications of the game about mutants attacking themselves in the battlefield, using OOP concepts like inherence, composition, and polymorphism.

## Spec definition

* **`package model`**
* **`class Mutant`** // Representa a un mutante individual dentro de la partida
* `+ int id` // Identificador unico del mutante
* `+ double x` // Coordenada horizontal del mutante en el Battlefield
* `+ double y` // Coordenada vertical del mutante en el Battlefield
* `+ int energy (100)` // Energia inicial del mutante
* `+ int defenseCapacity (1->3)` // Capacidad defensiva asignada al mutante
* `+ MutantPower power` // Poder que posee el mutante, como maximo uno
* `+ boolean isAlive` // Indica si el mutante continúa vivo
* `- void move()` // Solicita al MovementEngine calcular su siguiente posicion
* `- void scan()` // Detecta oponentes dentro del radio de combate
* `- void react()` // Decide si atacar o defenderse ante un oponente
* `- void attack()` // Ejecuta un ataque contra el oponente detectado
* `- void defend()` // Ejecuta la acción defensiva del mutante

* **`class MutantPower`** // Representa el poder que posee un mutante y su capacidad de daño
* `+ int damageCapacity (1->3 inicial, hasta 7 máximo)` // Capacidad de daño actual del poder
* `+ IPowerEffect effect` // Efecto asociado al poder mediante polimorfismo
* `- void increaseDamage()` // Incrementa el daño sin superar el máximo de 7

* **`interface IPowerEffect`** // Define el comportamiento que implementan los efectos de los diferentes poderes
* `+ void applyEffect()` // Ejecuta el efecto particular del poder

* **`class FireEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder fire

* **`class WaterEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder water

* **`class EarthEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder earth

* **`class WindEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder wind

* **`class ElectricityEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder electricity

* **`class RockEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder rock

* **`class LaserEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder laser

* **`class BeastEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder beast

* **`class IceEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder ice

* **`class ThornsEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder thorns

* **`class SandEffect implements IPowerEffect`**
* `+ void applyEffect()` // Ejecuta el efecto del poder sand


* **Game Layer (`package game`)**
* **`class Battlefield`** // Representa el campo donde se desarrolla la batalla
* `+ int width` // Ancho del area de batalla
* `+ int height` // Alto del area de batalla
* `+ Team teamA` // Primer equipo participante
* `+ Team teamB` // Segundo equipo participante
* `+ Scoreboard scoreboard` // Marcador de la partida
* `- void initializeTeams()` // Crea ambos equipos con la misma cantidad de mutantes
* `- void checkGameOver()` // Comprueba si algun equipo perdió todos sus mutantes
* `- void notifyScoreboard()` // Comunica al Scoreboard el estado actual de los equipos
* `+ void addObserver(Observer observer)` // Registra un observador para recibir cambios del Battlefield
* `+ void removeObserver(Observer observer)` // Elimina un observador previamente registrado
* `+ void notifyObservers()` // Notifica a los observadores cuando cambia el estado del Battlefield

* **`class Team`** // Representa un equipo y los mutantes que lo conforman
* `+ String name` // Nombre utilizado para identificar al equipo
* `+ Color color` // Color utilizado para identificar visualmente al equipo
* `+ String shield` // Escudo o simbolo utilizado para identificar al equipo
* `+ List<Mutant> members (3->11)` // Lista de mutantes pertenecientes al equipo
* `- void addMember()` // Añade un mutante sin superar el maximo permitido
* `+ boolean hasAliveMembers()` // Indica si el equipo conserva mutantes vivos

* **`class Scoreboard`** // Representa el marcador que mantiene el estado de mutantes vivos y muertos
* `+ int aliveTeamA` // Cantidad actual de mutantes vivos del equipo A
* `+ int deadTeamA` // Cantidad actual de mutantes muertos del equipo A
* `+ int aliveTeamB` // Cantidad actual de mutantes vivos del equipo B
* `+ int deadTeamB` // Cantidad actual de mutantes muertos del equipo B
* `+ void updateScores(int aliveA, int deadA, int aliveB, int deadB)` // Actualiza los contadores de ambos equipos


* **Control Layer (`package control`)**
* **`class MovementEngine`** // Controla el calculo del movimiento de los mutantes
* `+ double speed` // Velocidad utilizada para calcular el desplazamiento
* `+ IMovementPattern pattern` // Patrón de movimiento seleccionado mediante polimorfismo
* `- void calculateNextPosition()` // Calcula la siguiente posición dentro del Battlefield

* **`interface IMovementPattern`** // Define una estrategia de movimiento para los mutantes
* `+ void move(Mutant mutant, Battlefield battlefield)` // Define la estrategia de movimiento del mutante

* **`class CombatManager`** // Coordina la detección y ejecución de los encuentros entre mutantes
* `+ double radius` // Distancia necesaria para detectar un encuentro
* `- void evaluateProximities()` // Detecta oponentes que se encuentran dentro del radio
* `- void executeParallelEncounters()` // Coordina encuentros que pueden ocurrir simultaneamente

* **`class MovementThread`** // Representa el hilo encargado del movimiento automatico
* `- void run()` // Ejecuta continuamente el movimiento automatico de los mutantes

* **`class CombatThread`** // Representa el hilo encargado de los encuentros de combate
* `- void run()` // Ejecuta continuamente los encuentros de combate


* **Constants (`package constants`)**
* **`interface IConstants`** // Centraliza los valores fijos y parametros configurables del juego
* `+ int initialEnergy = 100` // Energia inicial de cada mutante
* `+ int minDefenseCapacity = 1` // Capacidad defensiva minima
* `+ int maxDefenseCapacity = 3` // Capacidad defensiva maxima
* `+ int minDamageCapacity = 1` // Daño inicial minimo de un poder
* `+ int initialMaxDamageCapacity = 3` // Daño inicial maximo de un poder
* `+ int maxDamageCapacity = 7` // Daño maximo que puede alcanzar un poder
* `+ int minTeamSize = 3` // Cantidad minima de mutantes por equipo
* `+ int maxTeamSize = 11` // Cantidad maxima de mutantes por equipo
* `+ double defaultSpeed` // Velocidad predeterminada de movimiento
* `+ double defaultCombatRadius` // Radio predeterminado de combate
* `+ int refreshRate` // Frecuencia configurable de actualización visual


* **UI Layer (`package ui`)**
* **`interface Observer`** // Define el mecanismo para recibir notificaciones sobre cambios del juego
* `+ void update()` // Actualiza el observador cuando cambia el estado del Battlefield

* **`class GameView`** // GameView solo representa información y no implementa lógica del juego
* `+ void update()` // Actualiza la representacion visual de la partida
* `- void drawBattlefield()` // Dibuja visualmente el area de batalla
* `- void drawMutants()` // Dibuja los mutantes y muestra su estado actual
* `- void drawScoreboard()` // Muestra los mutantes vivos, muertos y su energia
* `- void showWinner()` // Muestra el equipo ganador al finalizar la partida

* **`class GameController`** // GameController coordina las acciones de la interfaz con el juego
* `- void startGame()` // Inicia una nueva partida
* `- void restartGame()` // Permite iniciar una nueva partida después de terminar

##  Diagrama de Clases (PlantUML)

<p align="center">
<img width="2239" height="1924" alt="PlantUML_diagram" src="https://github.com/user-attachments/assets/a6d515f2-e39d-41fe-8206-104c3c999ac0" />
</p>

---

## Código PlantUML

<details>
<summary>📄 Ver código del diagrama</summary>

```plantuml
@startuml
skinparam packageStyle rectangle

package model {
    class Mutant {
        + int id
        + double x
        + double y
        + int energy
        + int defenseCapacity
        + MutantPower power
        + boolean isAlive
        - void move()
        - void scan()
        - void react()
        - void attack()
        - void defend()
    }

    class MutantPower {
        + String name
        + int damageCapacity
        + IPowerEffect effect
        - void increaseDamage()
    }

    interface IPowerEffect {
        + void applyEffect()
    }

    class FireEffect
    class WaterEffect
    class EarthEffect
    class WindEffect
    class ElectricityEffect
    class RockEffect
    class LaserEffect
    class BeastEffect
    class IceEffect
    class ThornsEffect
    class SandEffect

    IPowerEffect <|.. FireEffect
    IPowerEffect <|.. WaterEffect
    IPowerEffect <|.. EarthEffect
    IPowerEffect <|.. WindEffect
    IPowerEffect <|.. ElectricityEffect
    IPowerEffect <|.. RockEffect
    IPowerEffect <|.. LaserEffect
    IPowerEffect <|.. BeastEffect
    IPowerEffect <|.. IceEffect
    IPowerEffect <|.. ThornsEffect
    IPowerEffect <|.. SandEffect

    Mutant --> MutantPower : power
    MutantPower --> IPowerEffect : effect
}

package game {
    class Battlefield {
        + int width
        + int height
        + Team teamA
        + Team teamB
        + Scoreboard scoreboard
        - void initializeTeams()
        - void checkGameOver()
        - void notifyScoreboard()
        + void addObserver(Observer observer)
        + void removeObserver(Observer observer)
        + void notifyObservers()
    }

    class Team {
        + String name
        + Color color
        + String shield
        + List<Mutant> members
        - void addMember()
        + boolean hasAliveMembers()
    }

    class Scoreboard {
        + int aliveTeamA
        + int deadTeamA
        + int aliveTeamB
        + int deadTeamB
        + void updateScores(int aliveA, int deadA, int aliveB, int deadB)
    }

    Battlefield --> Team : teamA, teamB
    Battlefield --> Scoreboard : scoreboard
    Team o--> model.Mutant : members
}

package control {
    class MovementEngine {
        + double speed
        + IMovementPattern pattern
        - void calculateNextPosition()
    }

    interface IMovementPattern {
        + void move(Mutant mutant, Battlefield battlefield)
    }

    class CombatManager {
        + double radius
        - void evaluateProximities()
        - void executeParallelEncounters()
    }

    class MovementThread {
        - void run()
    }

    class CombatThread {
        - void run()
    }

    MovementEngine --> IMovementPattern : pattern
    MovementEngine ..> model.Mutant : uses
    MovementEngine ..> game.Battlefield : uses
    IMovementPattern ..> model.Mutant : uses
    IMovementPattern ..> game.Battlefield : uses
    CombatManager ..> model.Mutant : uses
    CombatManager ..> game.Battlefield : uses
    MovementThread --> MovementEngine : executes
    CombatThread --> CombatManager : executes
    MovementEngine ..> constants.IConstants : uses
    CombatManager ..> constants.IConstants : uses
}

package constants {
    interface IConstants {
        + int initialEnergy
        + int minDefenseCapacity
        + int maxDefenseCapacity
        + int minDamageCapacity
        + int initialMaxDamageCapacity
        + int maxDamageCapacity
        + int minTeamSize
        + int maxTeamSize
        + double defaultSpeed
        + double defaultCombatRadius
        + int refreshRate
    }
}

package ui {
    interface Observer {
        + void update()
    }

    class GameView {
        + void update()
        - void drawBattlefield()
        - void drawMutants()
        - void drawScoreboard()
        - void showWinner()
    }

    class GameController {
        - void startGame()
        - void restartGame()
    }

    Observer <|.. GameView
    GameView ..> game.Battlefield : reads
    GameController --> game.Battlefield : controls
}

game.Battlefield --> ui.Observer : notifies
game.Battlefield ..> constants.IConstants : uses
@enduml

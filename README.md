#  Case 1 - Mutant Battle

> **Bryan Leiva - Daniel Sánchez**  
> Models and specifications of the game about mutants attacking themselves in the battlefield, using OOP concepts like inherence, composition, and polymorphism.

---

##  Especificación del Caso 1

###  Paquete `model`

#### Clase `Mutant`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `int` | Identificador único del mutante |
| `x` | `double` | Posición en el eje X del campo |
| `y` | `double` | Posición en el eje Y del campo |
| `energy` | `int` | Energía actual (inicial: 100) |
| `defenseCapacity` | `int` | Capacidad de defensa (valor entre 1 y 3) |
| `power` | `MutantPower` | Poder asociado al mutante |
| `isAlive` | `boolean` | Estado de vida del mutante |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `move()` | `private` | Actualiza las coordenadas del mutante según su motor de movimiento dentro de los límites del campo |
| `attack()` | `private` | Reduce la energía del oponente considerando el daño del poder y si el objetivo se defendió |
| `defend()` | `private` | Activa el estado temporal de defensa para mitigar el impacto del próximo ataque recibido |

---

#### Clase `MutantPower`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `damageCapacity` | `int` | Capacidad de daño (inicial entre 1 y 3, máximo 7) |
| `type` | `Powers` | Tipo de poder (ver enumeración) |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `increaseDamage()` | `private` | Incrementa en una unidad la capacidad de daño del poder si no supera el límite máximo de 7 |

---

#### Enumeración `Powers`

Lista de tipos de poderes disponibles:

| Valor |
|-------|
| `FIRE` |
| `WATER` |
| `EARTH` |
| `WIND` |
| `ELECTRICITY` |
| `ROCK` |
| `LASER` |
| `BEAST` |
| `ICE` |
| `THORNS` |
| `SAND` |

---

### Paquete `layer`

#### Clase `Battlefield`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `width` | `int` | Ancho del campo de batalla |
| `height` | `int` | Alto del campo de batalla |
| `teamA` | `Team` | Equipo A |
| `teamB` | `Team` | Equipo B |
| `scoreboard` | `Scoreboard` | Marcador del juego |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `initializeTeams()` | `private` | Genera y configura ambos equipos con un tamaño simétrico válido entre 3 y 11 integrantes |
| `checkGameOver()` | `private` | Comprueba si algún equipo se quedó sin mutantes vivos para finalizar la partida |

---

#### Clase `Team`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `name` | `String` | Nombre del equipo |
| `color` | `Color` | Color representativo |
| `shield` | `String` | Escudo del equipo |
| `members` | `List<Mutant>` | Lista de mutantes (entre 3 y 11) |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `addMember()` | `private` | Añade un nuevo mutante a la lista del equipo verificando que no exceda el límite permitido |
| `hasAliveMembers()` | `public` | Retorna `true` si al menos un miembro del equipo sigue vivo |

---

#### Clase `Scoreboard`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `aliveTeamA` | `int` | Cantidad de mutantes vivos del equipo A |
| `deadTeamA` | `int` | Cantidad de mutantes muertos del equipo A |
| `aliveTeamB` | `int` | Cantidad de mutantes vivos del equipo B |
| `deadTeamB` | `int` | Cantidad de mutantes muertos del equipo B |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `updateScores()` | `private` | Modifica los contadores de mutantes vivos y muertos de cada equipo según su estado actual |

---

#### Clase `MovementEngine`

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `speed` | `double` | Velocidad de movimiento |
| `x` | `double` | Posición actual en X |
| `y` | `double` | Posición actual en Y |
| `radius` | `double` | Radio de influencia o alcance |
| `pattern` | `Pattern` | Patrón de movimiento asignado |

| Método | Visibilidad | Descripción |
|--------|-------------|-------------|
| `calculateNextPosition()` | `private` | Calcula la siguiente posición espacial en función de la velocidad y el patrón asignado |

---

#### Enumeración `Pattern`

Patrones de movimiento disponibles:

| Valor |
|-------|
| `LINEAR` |
| `CIRCULAR` |
| `RANDOM` |
| `ZIGZAG` |

---

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

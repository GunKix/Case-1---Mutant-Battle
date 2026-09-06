#  Case 1 - Mutant Battle

> **Bryan Leiva - Daniel Sánchez**  
> Modelado y especificación de un juego de batalla entre mutantes, aplicando conceptos de POO como herencia, composición y polimorfismo.

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
  <img src="https://github.com/user-attachments/assets/94fbf554-8f4c-4919-90ca-a1024149d0f8" alt="PlantUML - Diagrama de Clases Mutants" width="100%"/>
</p>

---

## Código PlantUML

<details>
<summary>📄 Ver código del diagrama</summary>

```plantuml
@startuml

package model {

    class Mutant {
        +id : int
        +x : double
        +y : double
        +energy : int = 100
        +defenseCapacity : int <<1..3>>
        +power : MutantPower
        +isAlive : boolean

        -move() : void
        -attack() : void
        -defend() : void
    }

    class MutantPower {
        +damageCapacity : int <<1..3 initial, max 7>>

        -increaseDamage() : void
    }

    enum Powers {
        FIRE
        WATER
        EARTH
        WIND
        ELECTRICITY
        ROCK
        LASER
        BEAST
        ICE
        THORNS
        SAND
    }

    MutantPower --> Powers : type
    Mutant --> MutantPower : has
}

package layer {

    class Battlefield {
        +width : int
        +height : int
        +teamA : Team
        +teamB : Team
        +scoreboard : Scoreboard

        -initializeTeams() : void
        -checkGameOver() : void
    }

    class Team {
        +name : String
        +color : Color
        +shield : String
        +members : List<Mutant>

        -addMember() : void
        +hasAliveMembers() : boolean
    }

    class Scoreboard {
        +aliveTeamA : int
        +deadTeamA : int
        +aliveTeamB : int
        +deadTeamB : int

        -updateScores() : void
    }

    class MovementEngine {
        +speed : double
        +x : double
        +y : double
        +radius : double
        +pattern : Pattern

        -calculateNextPosition() : void
    }

    enum Pattern {
        LINEAR
        CIRCULAR
        RANDOM
        ZIGZAG
    }
}

'=========================
' Relationships
'=========================

Battlefield *-- "1" Team : teamA
Battlefield *-- "1" Team : teamB
Battlefield *-- "1" Scoreboard

Team *-- "3..11" Mutant : members

Mutant --> MovementEngine : uses

Scoreboard ..> Team : monitors
Battlefield ..> Team
Battlefield ..> Scoreboard

@enduml

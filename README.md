# Case-1---Mutant-Battle
Case 1 - Bryan Leiva - Daniel Sánchez

=== Spec definition of the case 1 ===
class model {

	class mutant
   		+ int id
   		+ double x
		+ double y
    		+ int energy (100)
    		+ int defenseCapacity (1->3)
   		+ MutantPower power
    		+ boolean isAlive
    		- void move() // Actualiza las coordenadas del mutante según su motor de movimiento dentro de los límites del campo
    		- void attack() // Reduce la energía del oponente considerando el daño del poder y si el objetivo se defendió
   		- void defend() // Activa el estado temporal de defensa para mitigar el impacto del próximo ataque recibido

	class mutantPower
    		+ int damageCapacity (1->3 inicial, hasta 7 máximo)
    		+ powers (Enum) {FIRE, WATER, EARTH, WIND, ELECTRICITY, ROCK, LASER, BEAST, ICE, THORNS, SAND}
   		- void increaseDamage() // Incrementa en una unidad la capacidad de daño del poder si no supera el límite máximo de 7
}

class layer { 

	class Battlefield
		+ int width
		+ int height
		+ Team teamA
		+ Team teamB
		+ Scoreboard scoreboard
		- void initializeTeams() // Genera y configura ambos equipos con un tamaño simétrico válido entre 3 y 11 integrantes.
		- void checkGameOver() // Comprueba si algún equipo se quedó sin mutantes vivos para finalizar la partida.

	class Team
		+ String name
		+ Color color
		+ String shield
		+ List members (3->11)
		- void addMember() // Añade un nuevo mutante a la lista del equipo verificando que no exceda el límite permitido.
		+ boolean hasAliveMembers()

	class Scoreboard
		+ int aliveTeamA
		+ int deadTeamA
		+ int aliveTeamB
		+ int deadTeamB
		- void updateScores() // Modifica los contadores de mutantes vivos y muertos de cada equipo según su estado actual.

	class MovementEngine
		+ double speed
		+ double x
		+ double y
		+ double radius
		+ Pattern // Consultar sobre cómo declarar el tipo de datos del pattrón de movimiento
		- void calculateNextPosition() // Calcula la siguiente posición espacial en función de la velocidad y el patrón asignado.
}

=== PlantUML definition of the case 1 ===

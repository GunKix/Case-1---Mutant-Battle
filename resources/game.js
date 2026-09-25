// 1) CORRECCIÓN DE RUTAS: como las imágenes viven en la carpeta Imagenes/,
// el prefijo relativo desde la carpeta resources/ es "../Imagenes/"
const imagePath = "../Imagenes/"; // Prefijo que se antepone a todas las imágenes del juego

// Diccionario que asocia cada nombre de poder con su imagen de proyectil
const powersData = {
    'agua': imagePath + 'agua_1.webp', // Proyectil del poder agua
    'arena': imagePath + 'arena_1.webp', // Proyectil del poder arena
    'espinas': imagePath + 'espinas_1.webp', // Proyectil del poder espinas
    'fuego': imagePath + 'fuego_1.webp', // Proyectil del poder fuego
    'hielo': imagePath + 'hielo_1.png' // Proyectil del poder hielo
};

// Declaración de las variables de estado del combate
let isAttacking = false; // Bandera que evita lanzar otro ataque mientras hay uno en curso
let enemyHealth = 100; // Vida inicial del enemigo (en porcentaje)

// Función principal que ejecuta el ataque seleccionado por el jugador
function attack(powerName) {
    if (isAttacking) return; // Si ya hay un ataque en curso, ignora el clic
    isAttacking = true; // Marca que el ataque está en ejecución

    // Obtiene las referencias a los elementos HTML del escenario
    const playerSprite = document.getElementById('player-sprite'); // Sprite del jugador
    const enemySprite = document.getElementById('enemy-sprite'); // Sprite del enemigo
    const powerAnimEl = document.getElementById('power-animation'); // Proyectil animado

    // Cambia el sprite del jugador a su versión de ataque
    playerSprite.src = imagePath + "bestia_1.webp"; // Pone la imagen de bestia atacando
    playerSprite.classList.add('attacking'); // Aplica el efecto CSS de ataque (escala y desplaza)

    // Prepara el proyectil del poder seleccionado
    powerAnimEl.src = powersData[powerName]; // Carga la imagen del proyectil según el poder
    powerAnimEl.style.display = 'block'; // Hace visible el proyectil
    powerAnimEl.classList.add('animate-attack'); // Ejecuta la animación CSS castPower

    // Lógica que corre en el momento exacto del impacto (después de 800ms)
    setTimeout(() => {
        // Limpia el proyectil al llegar al enemigo
        powerAnimEl.style.display = 'none'; // Oculta el proyectil
        powerAnimEl.classList.remove('animate-attack'); // Quita la clase de animación
        powerAnimEl.src = ""; // Vacía la imagen del proyectil

        // Regresa al jugador a su apariencia normal
        playerSprite.src = imagePath + "bestia.webp"; // Vuelve al sprite en reposo
        playerSprite.classList.remove('attacking'); // Quita el efecto CSS de ataque

        // Aplica el daño al enemigo
        applyDamage(20); // Cada ataque quita un 20% de vida del enemigo

        // Efecto visual de daño en el enemigo (filtro de colores)
        enemySprite.style.filter = "brightness(50%) sepia(1) hue-rotate(-50deg) saturate(5)"; // Aplica filtro rojizo de impacto
        setTimeout(() => {
            enemySprite.style.filter = "none"; // Quita el filtro después del parpadeo
            isAttacking = false; // Libera la bandera para permitir el siguiente ataque
        }, 300); // Espera 300ms antes de restaurar el sprite

        // Notifica el ataque realizado a Java (conexión opcional)
        notificarJava(powerName, 20); // Enviar notificación de daño a Java
    }, 800); // El impacto ocurre a los 800ms
}

// Función que descuenta vida al enemigo y actualiza su barra
function applyDamage(damageAmount) {
    enemyHealth -= damageAmount; // Resta el daño a la vida del enemigo
    if (enemyHealth < 0) enemyHealth = 0; // Evita que la vida baje de cero (se pisa igual)
    if (enemyHealth > 100) enemyHealth = 100; // Evita que la vida suba de 100 (por seguridad)

    // Actualiza la barra de vida en el HTML
    const enemyHealthBar = document.querySelector('.enemy .health-fill'); // Selecciona la barra verde del enemigo
    enemyHealthBar.style.width = enemyHealth + '%'; // Cambia el ancho de la barra según la vida restante

    // Si la vida llega a cero, el enemigo es derrotado
    if (enemyHealth === 0) {
        alert("¡Enemigo derrotado!"); // Muestra un aviso al jugador
    }
}

// Función puente para comunicar el ataque hacia Java (requiere JavaFX/WebView)
function notificarJava(ataque, danio) {
    try {
        // 'appJava' sería un objeto inyectado desde el código Java
        if (window.appJava) {
            window.appJava.registrarAtaque(ataque, danio); // Llama al método Java con los datos del ataque
        } else {
            // Si Java no está conectado, simula la notificación en consola
            console.log(`Simulación: Se usó ${ataque} y causó ${danio} de daño. (Java no conectado aún)`);
        }
    } catch (e) {
        // Captura cualquier error de comunicación con Java
        console.error("Error al comunicar con Java: ", e); // Muestra el error en consola
    }
}
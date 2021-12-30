# Simulación de cafetería

Este es un programa sencillo en donde se simula la llegada de clientes (generados aleatoriamente) a una pequeña cafetería.
Al llegar, los clientes ocupan su lugar en la fila. Cada cliente posee una cantidad de tiempo que define cuanto puede esperar
en la fila. En cada unidad de tiempo que pasa en la simulación, el tiempo de espera de todos los clientes que están en la fila
se resta en uno. Cuando el tiempo de espera de alguno de ellos llega a cero, este abandona la fila y sale del lugar.

Mientras el resto de los clientes esperan formados, el cliente al frente de la fila se acerca al mostrador frente al cocinero
de la simulación y hace su pedido. El cocinero comienza con la preparación del plato ordenado, cuando termina su preparación lo
entrega al cliente. El cliente atendido sale del lugar y el siguiente cliente en la fila pasa a ser atendido.

## Características
* Los clientes, su tiempo de espera, y el plato que ordenan se generan de forma aleatoria
* La interfaz gráfica permite pausar y reiniciar la animación
* Conteo de tiempol tiempo transcurrido de la animación
* Reproducción continua de sonido de fondo
* Conteo del número de de clientes perdidos y atendidos
* Conteo del número de platos vendidos para cada tipo al final de la simulación
* Se pueden generar 8 clientes diferentes y 12 platos distintos

![](pre.gif)

## Lenguajes y herramientas
Java

## Créditos
### Imágenes
* Ícono del programa: <a href="https://www.freepik.es/vector-gratis/iconos-taza-cafe_1530512.htm#query=coffe%20cup%20icons%20macrovector&position=26&from_view=search">Coffee cup icons</a> por <a href="https://www.freepik.es/macrovector">macrovector</a>
* Sprites de los personajes: <a href="https://forums.rpgmakerweb.com/index.php?threads/tall-character-sprites-sv-template.119130/">Tall Character Sprites & SV template</a> por <a href="https://forums.rpgmakerweb.com/index.php?members/wtr.156088/">WTR</a>
* Sprites para los platos: <a href="https://ghostpixxells.itch.io/pixelfood">Free Pixel Food</a> por <a href="https://itch.io/profile/ghostpixxells">ghostpixxells</a>
* Imágen de fondo de la cafetería: <a href="https://www.deviantart.com/peekychew/art/Cafe-Restaurant-and-Bar-Tiles-532856201">Cafe, Restaurant and Bar Tiles</a> por <a href="https://www.deviantart.com/peekychew">PeekyChew</a>
* Sprite de reloj: <a href="https://opengameart.org/content/lpc-clock-animation">Clock Animation</a> por <a href="https://opengameart.org/users/williamthompsonj">William.Thompsonj<a>
* Imágen de pizarrón: <a href="https://pngfree.io/blackboard-rectangle-png-8317">Chalkboard illustration</a> en <a href="https://pngfree.io/">PNG Free</a>
### Audio
* Canción de fondo: <a href="https://opengameart.org/content/bossa-nova">Bossa Nova</a> por <a href="https://opengameart.org/users/joth?page=1">Joth</a>
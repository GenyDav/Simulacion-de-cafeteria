# Simulación de cafetería

Este es un programa sencillo en donde se simula la llegada de clientes  a una pequeña cafetería. 
Al llegar, los clientes ocupan su lugar en la fila. Cada cliente posee una cantidad de tiempo que define cuanto puede esperar
en la fila. En cada unidad de tiempo que pasa en la simulación, el tiempo de espera de todos los clientes que están en la fila
se resta en uno. Cuando el tiempo de espera de alguno de ellos llega a cero, este abandona la fila y sale del lugar.

Mientras el resto de los clientes esperan formados, el cliente al frente de la fila se acerca al mostrador frente al cocinero
de la simulación y hace su pedido. El cocinero comienza con la preparación del plato ordenado, cuando termina su preparación lo
entrega al cliente. El cliente atendido sale del lugar y el siguiente cliente en la fila pasa a ser atendido.

El tipo de cliente, su tiempo de espera y el plato que ordena se generan de forma aleatoria.

## Características
* Los clientes, su tiempo de espera, y el plato que ordenan se generan de forma aleatoria
* La interfaz gráfica permite pausar y reiniciar la animación
* Conteo de tiempol tiempo transcurrido de la animación
* Reproducción continua de sonido de fondo
* Conteo del número de de clientes perdidos y atendidos
* Conteo del número de platos vendidos para cada tipo al final de la simulación
* Se pueden generar 8 clientes diferentes y 12 platos distintos

![Programa en funccionamiento](pre.gif)

## Lenguajes y herramientas
* El proyecto está escrito en su totalidad en Java
* Para reproducir audio se utilizó la librería JLayer

## Créditos
### Imágenes
* Ícono del programa: [Coffee cup icons](https://www.freepik.es/vector-gratis/iconos-taza-cafe_1530512.htm#query=coffe%20cup%20icons%20macrovector&position=26&from_view=search) por [macrovector](https://www.freepik.es/macrovector)
* Sprites de los personajes: [Tall Character Sprites & SV template](https://forums.rpgmakerweb.com/index.php?threads/tall-character-sprites-sv-template.119130/) por [WTR](https://forums.rpgmakerweb.com/index.php?members/wtr.156088/)
* Sprites para los platos: [Free Pixel Food](https://ghostpixxells.itch.io/pixelfood) por [ghostpixxells](https://itch.io/profile/ghostpixxells)
* Imágen de fondo de la cafetería: [Cafe, Restaurant and Bar Tiles](https://www.deviantart.com/peekychew/art/Cafe-Restaurant-and-Bar-Tiles-532856201) por [PeekyChew](https://www.deviantart.com/peekychew)
* Sprite de reloj: [Clock Animation](https://opengameart.org/content/lpc-clock-animation) por [William.Thompsonj](https://opengameart.org/users/williamthompsonj)
* Imágen de pizarrón: [Chalkboard illustration](https://pngfree.io/blackboard-rectangle-png-8317) en [PNG Free](https://pngfree.io/)
### Audio
* Canción de fondo: [Bosa Nova](https://opengameart.org/content/bossa-nova) por [Joth](https://opengameart.org/users/joth?page=1)
package cafeteria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Clase que 'despacha' a los objetos de la clase Cliente.
 * Recibe el primer objeto Cliente de una lista y en base al pedido 
 * del cliente, prepara un plato y se lo entrega al cliente
 * @author Geny
 * @version 2.2
 */
public class Consumidor {
    private int x,y;                                // coordenadas del numSprite
    private double numSprite;                       // número de sprite que se va a utilizar [1-3]
    private final BufferedImage sprt1;              // sprite del personaje mientras no está sirviendo un plato
    private final BufferedImage sprtMovimiento[];   // sprites del personaje miestras está cocinando
    private BufferedImage sprtActual;               // sprite que se va a dibujar en pantalla
    private boolean ocupado;                             
    static boolean cocinando;
    private double transcurrido;                            // tiempo que ha pasado mientras se prepara el pedido
    private double porcentajePedido;
    private Cliente cliente;                                // cliente que está siendo atendido
    private Pedido p;                                       // pedido hecho por el cliente
    
    /**
     * Constructor
     */
    public Consumidor(){
        sprt1 = Imagen.cargaImagen("sprites/chef_1.png");       
        sprtMovimiento = new BufferedImage[2];
        sprtMovimiento[0] = Imagen.cargaImagen("sprites/chef_2.png");
        sprtMovimiento[1] = Imagen.cargaImagen("sprites/chef_3.png");
        numSprite = 2;
        sprtActual = sprt1;       
        x = 150;
        y = 182;
        ocupado = false;                
        cocinando = false;
        transcurrido = 0;
        porcentajePedido = 0;
        p = null;
    }
    
    /**
     * Método que dibuja el sprite del objeto consumidor(chef) en la pantalla
     * y un mensaje indicando que se está preparando el plato junto con una 
     * barra de progreso que va indicando el tiempo restante que le tomará al 
     * consumidor entregar el pedido. También dibuja los sprites del plato que 
     * se está preparando.
     * @param g contexto grafico
     */
    public void pintarConsumidor(Graphics g){
        if(cocinando){
            // Mostrar mensaje sobre el cocinero
            g.setColor(Color.WHITE);  
            g.setFont(new Font("Comic Sans MS",Font.BOLD,13));
            g.drawString("Cocinando",x-10,y-30);
            g.drawString(String.valueOf(Math.round(porcentajePedido*100))+"%",x+7,y-15);
            
            // Dibujar la barra de progreso
            g.setColor(new Color(96,48,32));
            g.drawRect(x-1, y-11, 41, 6);
            g.setColor(Color.WHITE);
            g.fillRect(x,y-10,40,5);
            //g.setColor(Color.RED);
            g.setColor(new Color(165,45,109));
            g.fillRect(x,y-10,(int)Math.round(porcentajePedido*40),5);
            
            // Dibuja el plato
            g.drawImage(p.getSprite(),x+5,y+53,null);       // Sprite del pedido debajo del cocinero
            g.drawImage(p.getSpritePizarron(),200,55,null); // Sprite del pedido en el pizarrón
        }else{
            sprtActual = sprt1;                             // Sprite del consumidor cuando no está 'cocinando'
        }
        g.drawImage(sprtActual,x,y,null);   // dibujar a el cocinero
    }
    
    /**
     * Método que utiliza el identificador del plato proporcionado por el objeto
     * Cliente para crear un nuevo objeto Pedido y marcar el estado del
     * consumidor como ocupado
     * @param cte objeto Cliente que está siendo 'atendido'
     */
    public void tomarPedido(Cliente cte){
        this.cliente = cte;
        ocupado = true;
        transcurrido = 0;
        p = new Pedido(cte.hacerPedido());
    }
    
    /**
     * Método que simula la preparación de un pedido. Toma una cantidad de 
     * tiempo en milisegundos dada por la clase que lleva el ciclo del
     * programa y lo utiliza para simular el avance en la preparación del pedido  
     * Cuando la suma del tiempo es igual o mayor a la cantidad de tiempo que
     * necesita el pedido, el consumidor se marca como desocupado y el estado
     * del cliente se cambia para que este pueda salir de la simulación con su 
     * pedido listo. Mientras el consumidor está preparando el plato, cambia su
     * sprite para dar la sensación de movimiento
     * @param dt Diferencia de tiempo transcurrida entre cada iteración del ciclo
     * que ejecuta la simulación
     */
    public void atenderCliente(float dt){
        porcentajePedido = transcurrido/p.getTmpPedido();   // calcular el porcentaje para la barra de progreso
        //System.out.println(porcentajePedido);
        if(transcurrido >= p.getTmpPedido()){        
            cocinando = false;
            ocupado = false;
            cliente.recibirPlato(p);
            cliente.setEstado(Cliente.CTE_ATENDIDO);
        }
        transcurrido += dt;
        
        numSprite += dt*2;  // cambiar el sprite según el tiempo
        //System.out.println("sprite actual: "+(int)numSprite);
        if(numSprite>4){
            numSprite = 2;
        }
        sprtActual = sprtMovimiento[(int)numSprite-2];               
    }
    
    /**
     * Devuelve el número de identificación del plato que ya fue servido
     * @return número de identificación de plato listo
     */
    public int getNumPlatoListo(){
        return p.getNumPedido();
    }
    
    /**
     * Método que devuelve el estado del consumidor como valor booleano:
     * true, si el consumidor está ocupado, false si no lo está
     * @return valor booleano que indica si el consumidor está ocupado o libre
     */
    public boolean getEstado(){
        return ocupado;
    }
    
    /**
     * Cambia el estado del objeto de tipo consumidor a ocupado(true) 
     * o libre(false)
     * @param e valor que representa el estado del consumidor 
     */
    public void setEstado(boolean e){
        ocupado = e;
    }
    
    /**
     * Establece los atributos del consumidor a sus valores por defecto
     * cuando se detiene la simulación
     */
    public void reiniciarCocinero(){
        numSprite = 2;
        sprtActual = sprt1;
        ocupado = false;
        cocinando = false;
        transcurrido = 0;
        porcentajePedido = 0;
        p = null;
    }
}

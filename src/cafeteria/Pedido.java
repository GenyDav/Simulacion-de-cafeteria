package cafeteria;

import java.awt.image.BufferedImage;
import static java.lang.Math.random;
import static java.lang.Math.round;

/**
 * Define el objeto que contiene la información básica de el platillo ordenado por el cliente
 * @author Geny D.
 * @version 1.0
 */
public class Pedido {
    private int numPedido;
    private BufferedImage sprite;   // sprite que se muestra sobre la mesa
    private BufferedImage sprite2;  // sprite que se muestra en el pizarrón
    private double tmpPedido;
    
    /**
     * Constructor
     * @param numPedido identificador del pedido que puede tomar el valor de 1 a 12 
     */
    public Pedido(int numPedido){
        this.numPedido = numPedido;
        sprite = Imagen.cargaImagen("sprites/plato"+numPedido+".png");
        sprite2 = Imagen.cargaImagen("sprites/plato"+numPedido+"_2.png");
        tmpPedido = round(random()*11+5);   // el tiempo que va a tomar preparar ese pedido
    }
    
    /**
     * Método que devuelve el número de identificación del pedido del cliente
     * @return número de identificación del pedido
     */
    public int getNumPedido(){
        return numPedido;
    }
    
    /**
     * Método que devuelve el tiempo que tomará preparar el pedido del cliente
     * @return tiempo que toma prepara el pedido
     */
    public double getTmpPedido(){
        return tmpPedido;
    }
    
    /**
     * Método que devuelve una imágen del pedido que va a mostrar en la pantalla principal debajo
     * del objeto de la clase Consumidor
     * @return imágen del pedido que se muestra en la mesa 
     */
    public BufferedImage getSprite(){
        return sprite;
    }
    
    /**
     * Método que devuelve una imágen del pedido que se va a mostrar en el pizarrón
     * de la pantalla principal de la simulación
     * @return imágen del pedido que se muestra sobre el pizarrón
     */
    public BufferedImage getSpritePizarron(){
        return sprite2;
    }
}

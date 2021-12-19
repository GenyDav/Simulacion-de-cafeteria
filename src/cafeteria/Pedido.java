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
     * @param numPedido identificador del plato que puede tomar el valor de 1 a 12 
     */
    public Pedido(int numPedido){
        this.numPedido = numPedido;
        sprite = Imagen.cargaImagen("sprites/plato"+numPedido+".png");
        sprite2 = Imagen.cargaImagen("sprites/plato"+numPedido+"_2.png");
        tmpPedido = round(random()*11+5);   // el tiempo que va a tomar preparar ese platillo
    }
    
    public int getNumPedido(){
        return numPedido;
    }
    
    public double getTmpPedido(){
        return tmpPedido;
    }
    
    public BufferedImage getSprite(){
        return sprite;
    }
    
    public BufferedImage getSpritePizarron(){
        return sprite2;
    }
}

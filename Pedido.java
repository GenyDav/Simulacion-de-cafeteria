/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.image.BufferedImage;
import static java.lang.Math.random;
import static java.lang.Math.round;

/**
 *
 * @author Geny
 */
public class Pedido {
    private BufferedImage sprite;
    private double tmpPedido;
    
    public Pedido(int numPedido){
        sprite = Imagen.cargaImagen("cafeteria/sprites/plato"+numPedido+".png");
        tmpPedido = round(random()*11+5);
    }
    
    public double getTmpPedido(){
        return tmpPedido;
    }
    
    public BufferedImage getSprite(){
        return sprite;
    }
}

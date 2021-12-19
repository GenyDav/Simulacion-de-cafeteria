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
    private int numPedido;
    private BufferedImage sprite;
    private BufferedImage sprite2;
    private double tmpPedido;
    
    public Pedido(int numPedido){
        this.numPedido = numPedido;
        sprite = Imagen.cargaImagen("sprites/plato"+numPedido+".png");
        sprite2 = Imagen.cargaImagen("sprites/plato"+numPedido+"_2.png");
        tmpPedido = round(random()*11+5);
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

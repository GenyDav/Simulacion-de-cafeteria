package cafeteria;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Geny
 */
public class Cliente {
    private float x,y; // coordenadas del sprite
    private int sprite;
    private float velocidad;
    private BufferedImage sprt1,sprt2,sprt3; //sprites del personaje
    
    public Cliente(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/lady5.png");
        sprite = 1;
        x = 550;
        y = 284;
        velocidad = 50;
    }
    
    public void pintarCliente(Graphics g){
        g.drawImage(sprt1,Math.round(x),Math.round(y),null);
    }
    
    public void avanzar(float dt){
        System.out.println(velocidad+"*"+dt+"="+velocidad*dt+", "+x);
        x -= velocidad*dt;
    }
}

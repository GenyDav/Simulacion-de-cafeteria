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
    private int estado;
    private float velocidad;
    private BufferedImage sprt1,sprt2,sprt3,sprtActual; //sprites del personaje
    
    public Cliente(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/lady5.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/lady9.png");
        sprt3 = Imagen.cargaImagen("cafeteria/sprites/lady4.png");
        sprtActual = sprt1;
        sprite = 1;
        x = 550;
        y = 254;
        velocidad = 80;
        estado = 1;
    }
    
    public void pintarCliente(Graphics g){
        g.drawImage(sprtActual,Math.round(x),Math.round(y),null);
        g.drawString("algo", Math.round(x), Math.round(y-20));
    }
    
    public void serAtendido(float dt){
        switch(estado){
            case 1:
                if(x>168){
                    x -= velocidad*dt;
                    System.out.println(dt);
                }else{
                    sprtActual = sprt2;
                    estado = 2;
                }
                break;
            case 2:
                try{                
                    Thread.sleep(1000);
                }catch(InterruptedException e){}
                System.out.println("Despertando");
                System.out.println("Cambiando a 3");
                sprtActual = sprt3;
                estado = 3;
                break;
            case 3:
                System.out.println(y);
                if(y<418){
                    System.out.println("sleep: "+dt);
                    y += velocidad*dt;
                }
                break;
        }
    }
}

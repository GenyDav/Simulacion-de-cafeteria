/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Geny
 */
public class Consumidor {
    private int x,y; // coordenadas del sprite
    private int sprite;
    private BufferedImage sprt1,sprt2,sprt3; //sprites del personaje
    
    public Consumidor(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/chef1.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/chef2.png");
        sprt3 = Imagen.cargaImagen("cafeteria/sprites/chef3.png");
        sprite = 1;
        x = 168;
        y = 184;
    }
    
    public void pintarConsumidor(Graphics g){
        switch(sprite){
            case 7:
                g.drawImage(sprt2,x,y,null);
                break;
            case 8:
                g.drawImage(sprt3,x,y,null);
                break;
            case 9:
                g.drawImage(sprt2,x,y,null);
                break;
            default:
                g.drawImage(sprt1,x,y,null);
                break;
        }
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }
     
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }

    public BufferedImage getSprt1() {
        return sprt1;
    }

    public BufferedImage getSprt2() {
        return sprt2;
    }

    public BufferedImage getSprt3() {
        return sprt3;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}

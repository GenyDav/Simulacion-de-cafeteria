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
public class Personaje {
    private int x,y; // coordenadas del sprite
    private BufferedImage sprite; //sprite del personaje
    
    public Personaje(){
        sprite = CargaImagen.cargaImagen("cafeteria/sprites/chef1.png");
        x = 168;
        y = 184;
    }
    
    public void pintar(Graphics g){
        g.drawImage(sprite,x,y,null);
    }
     
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}

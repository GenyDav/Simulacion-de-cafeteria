/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.Color;
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
    boolean ocupado;
    static boolean cocinando;
    int plato;
    double tmpPedido; // tmpPedido que toma preparar el pedido
    double transcurrido;
    double porcentajePedido;
    Cliente cliente;
    BufferedImage pedido;
    
    public Consumidor(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/chef1.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/chef2.png");
        sprt3 = Imagen.cargaImagen("cafeteria/sprites/chef3.png");
        sprite = 1;
        x = 168;
        y = 184;
        ocupado = false; // cuando está desocupado es false
        cocinando = false;
        plato = 0;
        tmpPedido = 0;
        transcurrido = 0;
        porcentajePedido = 0;
    }
    
    public void pintarConsumidor(Graphics g){
        if(cocinando){
            g.setColor(Color.WHITE);
            g.drawString("Cocinando", 168, 184);
            g.drawImage(pedido,100, 100, null);
            g.setColor(Color.WHITE);
            g.fillRect(Math.round(x),Math.round(y-80),40,5);
            g.setColor(Color.RED);
            g.fillRect(Math.round(x),Math.round(y-80),(int)Math.round(porcentajePedido*40),5);
        }
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
    
    public void tomarPedido(Cliente cte){
        this.cliente = cte;
        this.plato = cte.plato;
        this.tmpPedido = cte.tPedido;
        ocupado = true;
        pedido = Imagen.cargaImagen("cafeteria/sprites/plato"+plato+".png");
        transcurrido = 0;
    }
    
    public void atenderCliente(float dt){
        //cliente.serAtendido(dt); 
        porcentajePedido = transcurrido/tmpPedido;
        if(transcurrido>tmpPedido){
            cliente.setEstado(3);
            cocinando = false;
        }
        transcurrido += dt;
        System.out.println(porcentajePedido);
    }
    
    public boolean getEstado(){
        return ocupado;
    }
    
    // Cambia el estado a ocupado(true) o libre(false)
    public void setEstado(boolean e){
        ocupado = e;
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

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
    private double sprite;
    private BufferedImage sprt1,sprt2,sprt3; //sprites del personaje
    private BufferedImage sprtActual;
    boolean ocupado;
    static boolean cocinando;
    double transcurrido;
    double porcentajePedido;
    Cliente cliente;
    Pedido p;
    
    public Consumidor(){
        sprt1 = Imagen.cargaImagen("sprites/chef_1.png");
        sprt2 = Imagen.cargaImagen("sprites/chef_2.png");
        sprt3 = Imagen.cargaImagen("sprites/chef_3.png");
        sprite = 2;
        x = 169;
        y = 182;
        ocupado = false; // cuando está desocupado es false
        cocinando = false;
        transcurrido = 0;
        porcentajePedido = 0;
        p = null;
    }
    
    public void pintarConsumidor(Graphics g){
        g.drawImage(sprtActual,x,y,null);
        if(cocinando){
            g.setColor(Color.WHITE);
            //g.drawString("Preparando",200,47);
            g.drawString("Cocinando",x-10,y-15);
            g.drawImage(p.getSprite(),x+5,y+53,null);//y-35
            g.setColor(Color.WHITE);
            g.fillRect(x,y-10,40,5);
            g.setColor(Color.RED);
            g.fillRect(x,y-10,(int)Math.round(porcentajePedido*40),5);
            //g.drawRect(200, 55, 57, 57);
            g.drawImage(p.getSpritePizarron(),200,55,null);//y-35
        }else{
            sprtActual = sprt1;
        }

        /*switch(sprite){
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
        }*/
    }
    
    public void tomarPedido(Cliente cte){
        this.cliente = cte;
        //this.plato = cte.plato;
        //this.tmpPedido = cte.tPedido;
        ocupado = true;
        //pedido = Imagen.cargaImagen("cafeteria/sprites/plato"+plato+".png");
        transcurrido = 0;
        p = new Pedido(cte.hacerPedido());
    }
    
    public void atenderCliente(float dt){
        //cliente.serAtendido(dt); 
        porcentajePedido = transcurrido/p.getTmpPedido();
        if(transcurrido>p.getTmpPedido()){
            cliente.setEstado(Cliente.CTE_SALIENDO_LUGAR);
            cocinando = false;
            cliente.recibirPlato(p);
        }
        transcurrido += dt;
        //System.out.println(porcentajePedido);
        
        sprtActual = Imagen.cargaImagen("sprites/chef_"+(int)sprite+".png");
        //System.out.println((int)spSalida);
        sprite += 0.05;
        //System.out.println("sprite actual: "+(int)sprite);
        if(sprite>4){
            sprite = 2;
        }
    }
    
    public int getNumPlatoListo(){
        return p.getNumPedido();
    }
    
    public boolean getEstado(){
        return ocupado;
    }
    
    // Cambia el estado a ocupado(true) o libre(false)
    public void setEstado(boolean e){
        ocupado = e;
    }

    public double Sprite() {
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
    
    public void reiniciarCocinero(){
        sprite = 1;
        ocupado = false;
        cocinando = false;
        //plato = 0;
        //tmpPedido = 0;
        transcurrido = 0;
        porcentajePedido = 0;
        p = null;
    }
}

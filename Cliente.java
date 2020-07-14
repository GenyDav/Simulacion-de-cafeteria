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
    private float tPedido; // tiempo que tardará el pedido del cliente (segundos)
    private float transcurrido;
    private float tmpEspera; // tiempo que el cliente puede esperar en la fila
    private boolean saliendo;
    
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
        this.tPedido = 0;
        this.tmpEspera = 0;
        transcurrido = 0;
        saliendo = false;
    }
    
    public Cliente(float tmpEspera,float tPedido){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/lady5.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/lady9.png");
        sprt3 = Imagen.cargaImagen("cafeteria/sprites/lady4.png");
        sprtActual = sprt1;
        sprite = 1;
        x = 550;
        y = 254;
        velocidad = 80;
        estado = 1;
        this.tPedido = tPedido;
        this.tmpEspera = tmpEspera;
        transcurrido = 0;
        saliendo = false;
    }
    
    public void pintarCliente(Graphics g){
        g.drawImage(sprtActual,Math.round(x),Math.round(y),null);
        g.drawString("algo", Math.round(x), Math.round(y-20));
    }
    
    public void restarTiempoEspera(float dt){
        tmpEspera -= dt;
        System.out.println("tiempo: "+tmpEspera);
        if(tmpEspera<=0){
            sprtActual = sprt3;
            estado = 3;
            saliendo = true;
        }
    }
    
    public void serAtendido(float dt){
        //System.out.println("Siendo atendido");
        switch(estado){
            case 1:
                if(x>168){
                    x -= velocidad*dt;
                    //System.out.println(dt);
                }else{
                    sprtActual = sprt2;
                    estado = 2;
                }
                break;
            case 2:
                transcurrido += dt;
                System.out.println(transcurrido);
                if(transcurrido>tPedido){
                    sprtActual = sprt3;
                    estado = 3;
                }
                break;
            case 3:
                //System.out.println(y);
                salir(dt);
                break;
        }
    }
    
    public boolean getSalida(){
        return saliendo;
    }
    
    public void salir(float dt){
        if(y<418){
            y += velocidad*dt;
        }
    }
    
    public void formarse(float dt,int lugar){
        if(x>(300+lugar*50)){
            x -= velocidad*dt;
        }
    }
}

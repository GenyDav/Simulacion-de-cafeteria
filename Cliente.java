package cafeteria;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Geny
 */
public class Cliente {
    private float x,y;  // coordenadas del sprite
    private int sprite;
    private int estado;
    private float velocidad;
    private BufferedImage sprt1,sprt2,sprt3,sprtActual;  //sprites del personaje
    private BufferedImage pedido;   // sprite del pedido del cliente
    private double tPedido;         // tiempo que tardará el pedido del cliente (segundos)
    private double transcurrido;
    private double tmpEspera;       // tiempo que el cliente puede esperar en la fila
    private double tmpAux;          // variables usada para la barra de progreso de tiempo en la fila
    private double porcentaje, porcentajePedido;
    private boolean saliendo;
    private boolean atendido;
    
    
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
    
    public Cliente(double tmpEspera,double tPedido,int plato){
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
        tmpAux = tmpEspera;
        transcurrido = 0;
        saliendo = false;
        porcentaje = 0;
        porcentajePedido = 0;
        pedido = Imagen.cargaImagen("cafeteria/sprites/plato"+plato+".png");
        atendido = false;
    }
    
    public void pintarCliente(Graphics g){
        g.drawImage(sprtActual,Math.round(x),Math.round(y),null);
        //g.drawString("algo", Math.round(x), Math.round(y-20));
        if(estado<2){ // mostrar barra de tiempo restante de espera
            g.setColor(Color.WHITE);
            g.fillRect(Math.round(x),Math.round(y-10),40,5);
            g.setColor(Color.BLUE);
            g.fillRect(Math.round(x),Math.round(y-10), (int)Math.round(porcentaje*40),5);
        }else if(estado==2){ // mostrar una barra de progreso en el pedido
            g.setColor(Color.WHITE);
            g.fillRect(Math.round(x),Math.round(y-80),40,5);
            g.setColor(Color.RED);
            g.fillRect(Math.round(x),Math.round(y-80),(int)Math.round(porcentajePedido*40),5);
            g.drawImage(pedido,Math.round(x+3),Math.round(y-105),null);
        }else if(estado==3){
            if(atendido){
                g.drawImage(pedido,Math.round(x+3),Math.round(y-20),null);
            }else if(saliendo){
                g.setColor(Color.WHITE);
                g.drawString("¡Me voy!", Math.round(x), Math.round(y-10));
            }
        }
    }
    
    public void restarTiempoEspera(float dt){
        tmpEspera -= dt;
        porcentaje = tmpEspera/tmpAux;
        //System.out.println("tiempo: "+tmpEspera);
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
                porcentajePedido = transcurrido/tPedido;
                //System.out.println(transcurrido);
                if(transcurrido>tPedido){
                    sprtActual = sprt3;
                    estado = 3;
                }
                atendido = true;
                break;
            case 3: // saliendo del lugar
                //System.out.println(y);
                salir(dt);
                break;
        }
    }
    
    public int getEstado(){
        return estado;
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
        if(x>(300+lugar*60)){
            x -= velocidad*dt;
        }
    }
}

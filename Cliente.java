package cafeteria;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Geny
 */
public class Cliente {
    private float x,y;      // coordenadas del sprite
    private int cliente;    // tipo de cliente
    public int estado;
    public static final int CTE_ESPERANDO_FILA = 1;
    public static final int CTE_ESPERANDO_CHEF = 2;
    public static final int CTE_SALIENDO_LUGAR = 3;
    private float velocidad;
    private BufferedImage sprt1,sprt2,sprt3,sprtActual;  //sprites del personaje
    //private BufferedImage pedido;   // sprite del pedido del cliente
    Pedido platoListo;
    private double spSalida;
    //double tPedido;         // tmpPedido que tardará el pedido del cliente (segundos)
    //private double transcurrido;
    public double tmpEspera;       // tmpPedido que el cliente puede esperar en la fila
    private double tmpAux;          // variables usada para la barra de progreso de tmpPedido en la fila
    public double porcentaje, porcentajePedido;
    private boolean saliendo;
    private boolean atendido;
    private boolean terminado;
    private int plato;
    
    
    public Cliente(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/lady_1.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/lady9.png");
        spSalida = 1;
        sprtActual = sprt1;
        x = 550;
        y = 234;
        velocidad = 80;
        estado = 1;
        this.tmpEspera = 0;
        //transcurrido = 0;
        saliendo = false;
        porcentaje = 1.0;
        porcentajePedido = 0;
        //pedido = Imagen.cargaImagen("cafeteria/sprites/plato"+plato+".png");
        atendido = false;
    }
    
    public Cliente(double tmpEspera,int plato,int cliente){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/"+cliente+"_1.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/"+cliente+"9.png");
        spSalida = 1;
        sprtActual = sprt1;
        x = 550;
        y = 245;
        velocidad = 80;
        estado = 1;
        //this.tPedido = tPedido;
        this.tmpEspera = tmpEspera;
        tmpAux = tmpEspera;
        //transcurrido = 0;
        saliendo = false;
        porcentaje = 1.0;
        porcentajePedido = 0;
        //pedido = Imagen.cargaImagen("cafeteria/sprites/plato"+plato+".png");
        this.cliente = cliente; 
        atendido = false;
        terminado = false;
        this.plato = plato;
    }
    
    public void pintarCliente(Graphics g){
        g.drawImage(sprtActual,Math.round(x),Math.round(y),null);
        if(estado<2){ // mostrar barra de tmpPedido restante de espera
            g.setColor(Color.WHITE);
            g.fillRect(Math.round(x),Math.round(y-10),40,5);
            g.setColor(Color.BLUE);
            g.fillRect(Math.round(x),Math.round(y-10), (int)Math.round(porcentaje*40),5);
        }/*else if(estado==2){ // mostrar una barra de progreso en el pedido
            /*g.setColor(Color.WHITE);
            g.fillRect(Math.round(x),Math.round(y-80),40,5);
            g.setColor(Color.RED);
            g.fillRect(Math.round(x),Math.round(y-80),(int)Math.round(porcentajePedido*40),5);
            //g.drawImage(pedido,Math.round(x+3),Math.round(y-105),null);
        }*/
        else if(estado==CTE_SALIENDO_LUGAR){
            if(atendido){
                g.setColor(Color.WHITE);
                g.drawString("¡Gracias!", Math.round(x-5), Math.round(y-30));
                g.drawImage(platoListo.getSprite(),Math.round(x+3),Math.round(y-20),null);
            }else if(saliendo){
                g.setColor(Color.WHITE);
                g.drawString("¡Me voy!", Math.round(x), Math.round(y-10));
            }
        }
    }
    
    public void restarTiempoEspera(float dt){
        tmpEspera -= dt;
        porcentaje = tmpEspera/tmpAux;
        //System.out.println("tmpPedido: "+tmpEspera);
        if(tmpEspera<=0){
            //sprtActual = sprt3;
            estado = CTE_SALIENDO_LUGAR;
            saliendo = true;
        }
    }
    
    public void serAtendido(float dt){
        //System.out.println("Siendo atendido");
        switch(estado){
            case CTE_ESPERANDO_FILA:
                if(x>168){
                    x -= velocidad*dt;
                    sprtActual = Imagen.cargaImagen("cafeteria/sprites/"+cliente+"_"+(int)spSalida+".png");
                    //System.out.println((int)spSalida);
                    spSalida += 0.1;
                    if(spSalida>5){
                        spSalida = 1;
                    }
                }else{
                    sprtActual = sprt2;
                    estado = CTE_ESPERANDO_CHEF;
                    Consumidor.cocinando = true;
                }
                break;
            case CTE_ESPERANDO_CHEF:
                //Consumidor.cocinando = true;
                /*transcurrido += dt;
                porcentajePedido = transcurrido/tPedido;
                if(transcurrido>tPedido){
                    estado = 3;
                    Consumidor.cocinando = false;
                }*/
                atendido = true;
                break;
            case CTE_SALIENDO_LUGAR: // saliendo del lugar
                //System.out.println(y);
                salir(dt);
                break;
        }
    }

    public boolean getTerminado() {
        return terminado;
    }
    
    public int getEstado(){
        return estado;
    }
    
    public void setEstado(int estado){
        this.estado = estado;
    }
    
    public boolean getSalida(){
        return saliendo;
    }
    
    public int hacerPedido(){
        return plato;
    }
    
    public void recibirPlato(Pedido p){
        platoListo = p;
    }
    
    public void salir(float dt){
        if(y<458){
            y += velocidad*dt;
            sprtActual = Imagen.cargaImagen("cafeteria/sprites/"+cliente+(int)spSalida+".png");
            //System.out.println("conteo:"+spSalida);
            spSalida += 0.1;
            if(spSalida>5){
                spSalida = 1;
            }
        }    
        else{
            terminado = true;
        }
    }
    
    public void avanzarFila(float dt,int lugar){
        if(x>(300+lugar*60)){
            x -= velocidad*dt;
            sprtActual = Imagen.cargaImagen("cafeteria/sprites/"+cliente+"_"+(int)spSalida+".png");
            //System.out.println("conteo:"+spSalida);
            spSalida += 0.1;
            if(spSalida>5){
                spSalida = 1;
            }
        }else{
            sprtActual = Imagen.cargaImagen("cafeteria/sprites/"+cliente+"_3.png");
        }
    }
}

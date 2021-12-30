package cafeteria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Clase que crea un objeto que representa al cliente. El cual, a partir de que
 * es creado, se forma en la fila esperando su turno para ser atendido, mostrando
 * una barra con el tiempo de espera restante. Si el tiempo que puede esperar se
 * termina, el cliente abandona la fila y sale del lugar. Si el cliente es el 
 * primero de la fila y el ojeto de la clase Consumidor está desocupado, el 
 * cliente avanza hasta llegar a la posición frente al Consumidor, le entrega su
 * pedido y espera a que el Consumidor le devuelva el plato preparado, cuando lo
 * recibe sale del lugar.
 * @author Geny D.
 * @version 2.1
 */
public final class Cliente {
    private float x,y;                              // coordenadas del sprite del cliente
    private int cliente;                            // número de identificación del cliente
    private int estado;                                  
    public static final int CTE_ESPERANDO_FILA = 1; // estado del cliente cuando está formado en la fila
    public static final int CTE_ESPERANDO_CHEF = 2; // estado del cliente cuando está frente al cocinero(chef) esperando su pedido
    public static final int CTE_IMPACIENTE = 3;     // estado del cliente cuando se acaba su tiempo de espera
    public static final int CTE_ATENDIDO = 4;       // estado del cliente cuando se le entrega su pedido listo
    private float velocidad;
    private BufferedImage sprt2,sprtActual; // sprites del personaje
    private int plato;                      // núero de identificación del plato que va a ordenar el cliente
    private Pedido platoListo;              // plato que el cocinero le entrega al cliente cuando está listo
    private double spSalida;                // número del sprite que se va a mostrar en pantalla
    private double tmpRestante;             // tiempo que le queda al cliente antes de salir de la fila
    private double tmpEspera;               // tiempo que el cliente puede esperar en la fila
    private double porcentaje;              // porcentaje del tiempo de espera del cliente
    private boolean cteFuera;               // Indica si el cliente está dentro o fuera de la cafetería
    
    /**
     * Constructor
     * @param tmpEspera tiempo que el cliente puede esperar en la fila
     * @param plato número de identificación del plato a pedir
     * @param cliente número de identificación para seleccionar el sprite del cliente
     */
    public Cliente(double tmpEspera,int plato,int cliente){
        this.tmpEspera = tmpEspera;
        tmpRestante = tmpEspera;
        this.plato = plato;
        this.cliente = cliente; 
        sprt2 = Imagen.cargaImagen("sprites/"+cliente+"9.png"); // sprite con el personaje de espalda
        spSalida = 1;                   // sprite con el personaje de frente
        sprtActual = null;
        x = 550;                        // coordenada x inicial
        y = 255;                        // coordenada y inicial
        velocidad = 65;                 // distancia que avanza el sprite del cliente cuando se dibuja en pantalla
        estado = CTE_ESPERANDO_FILA;       
        porcentaje = 1.0;               // porcentaje del tiempo restante (100%)
        cteFuera = false;
    }
    
    /**
     * Modifica el estado del cliente según el valor entero pasado como parámetro:
     * @param estado estado del cliente
     */
    public void setEstado(int estado){
        this.estado = estado;
    }
    
    /**
     * Método utilizado para obtener el estado del cliente
     * @return estado del cliente
     */
    public int getEstado(){
        return estado;
    }
    
    /**
     * Método que devuelve el número de identificación del pedido(plato) que
     * va a ordenar el cliente
     * @return número de identificación del pedido
     */
    public int hacerPedido(){
        return plato;
    }
    
    /**
     * Método que 'devuelve' al cliente el objeto de tipo Pedido preparado
     * por el consumidor
     * @param p plato preparado para el cliente 
     */
    public void recibirPlato(Pedido p){
        platoListo = p;
    }
    
    /**
     * Regresa un valor booleano que indica si el cliente aún está dentro de
     * la cafetería(false) o ya salió de ella(true).
     * @return
     */
    public boolean getClienteFuera() {
        return cteFuera;
    }
    
    /**
     * Dibuja el sprite del cliente en pantalla según su estado. Tambien 
     * dibuja el sprite del pedido cuando está listo y muestra los mensajes
     * sobre el cliente cuando es necesario.
     * @param g Contexto gráfico 
     */
    public void pintarCliente(Graphics g){
        g.drawImage(sprtActual,Math.round(x),Math.round(y),null);   // dibujar al cliente
        // Mostrar mensaje sobre el cocinero
        g.setColor(Color.WHITE);  
        g.setFont(new Font("Comic Sans MS",Font.BOLD,13));
        switch(estado){
            case CTE_ESPERANDO_FILA:
                // Mostrar barra con el tiempo de espera restante
                g.drawString(String.valueOf(Math.round(tmpRestante)), Math.round(x+12), Math.round(y-15));
                g.setColor(new Color(19,33,60));
                g.drawRect(Math.round(x-1), Math.round(y-11), 41, 6);
                g.setColor(Color.WHITE);
                g.fillRect(Math.round(x),Math.round(y-10),40,5);
                //g.setColor(Color.BLUE);
                g.setColor(new Color(32,48,96));
                g.fillRect(Math.round(x),Math.round(y-10), (int)Math.round(porcentaje*40),5);
                break;
            case CTE_ATENDIDO:
                // Mostrar el sprite del pedido sobre el cliente
                g.setColor(Color.WHITE);
                g.drawString("¡Gracias!", Math.round(x-3), Math.round(y-32));
                g.drawImage(platoListo.getSprite(),Math.round(x+5),Math.round(y-32),null);
                break;
            case CTE_IMPACIENTE:
                // Mostrar un mensaje sobre el cliente cuando sale de la fila
                g.setColor(Color.WHITE);
                g.drawString("¡Me voy!", Math.round(x), Math.round(y-10));
                break;
        }
    }
    
    /**
     * Método que hace que el cliente se forme en la fila tomando su lugar
     * correspondiente según su posición al entrar a la simulación y cuando
     * otro cliente sale de la fila.
     * cuando entra a la simulación.   
     * @param dt Diferencia de tiempo recibida desde la clase que lleva el
     * control de la simulación
     * @param lugar Posición del cliente en la fila
     */
    public void avanzarFila(float dt,int lugar){
        if(x>(300+lugar*60)){
            x -= velocidad*dt;
            sprtActual = Imagen.cargaImagen("sprites/"+cliente+"_"+(int)spSalida+".png");
            //System.out.println("conteo:"+spSalida);
            spSalida += dt*5;   // hacer que el sprite cambie 5 veces por segundo
            if(spSalida>5){
                spSalida = 1;
            }
        }else{
            sprtActual = Imagen.cargaImagen("sprites/"+cliente+"_3.png");
        }
    }
    
    /**
     * Método que va restando el tiempo que el cliente puede permanecer en la
     * fila esperando a ser atendido. Cuando el tiempo de espera del cliente
     * llega a 0, el cliente se considera como perdido.
     * @param dt cantidad de tiempo en milisegundos pasada como parámetro desde
     * el ciclo que lleva el control de la simulación
     */
    public void restarTiempoEspera(float dt){
        tmpRestante -= dt;
        porcentaje = tmpRestante/tmpEspera;
        if(tmpRestante<=0){
            estado = CTE_IMPACIENTE;
        }
    }
    
    /**
     * Método que define los sprites del cliente que van a ser mostrados cuando
     * éste deja la fila para hacer el pedido al objeto Consumidor, cuando está
     * esperando a que el pedido esté listo, y cuando el cliente recibe el 
     * pedido y sale del lugar.
     * @param dt Diferencia de tiempo recibida desde la clase que lleva el
     * control de la simulación
     */
    public void serAtendido(float dt){
        switch(estado){
            case CTE_ESPERANDO_FILA:
                // Avanzar hasta donde está el cocinero(Consumidor)
                if(x>150){
                    x -= velocidad*dt;
                    sprtActual = Imagen.cargaImagen("sprites/"+cliente+"_"+(int)spSalida+".png");
                    //System.out.println((int)spSalida);
                    spSalida += dt*5;
                    if(spSalida>5){
                        spSalida = 1;
                    }
                }else{  // Cuando el cliente está frente al cocinero              
                    sprtActual = sprt2;
                    estado = CTE_ESPERANDO_CHEF;
                    Consumidor.cocinando = true;
                }
                break;
            case CTE_ATENDIDO:
                salir(dt);
                break;
        }
    }        
    
    /**
     * Método que muestra la animación del cliente saliendo de la cafetería
     * @param dt Diferencia de tiempo recibida desde la clase que lleva el
     * control de la simulación.
     */
    public void salir(float dt){
        if(y<465){
            y += velocidad*dt;  // calcular la nueva posición del cliente
            sprtActual = Imagen.cargaImagen("sprites/"+cliente+(int)spSalida+".png");
            //System.out.println("conteo: "+spSalida);
            spSalida += dt*5;   // hacer que el sprite cambie 5 veces por segundo
            if(spSalida>5){
                spSalida = 1;
            }
        }else{
            cteFuera = true;
        }
    }
}
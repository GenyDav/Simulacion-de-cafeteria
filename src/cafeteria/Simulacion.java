package cafeteria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Clase que lleva el control del ciclo del programa
 * @author Geny
 * @version 2.2
 */
public class Simulacion extends JPanel implements Runnable{
    private final int PX_ANCHO = 550;       // ancho del lienzo
    private final int PX_ALTO = 418;        // alto del lienzo 
    private BufferedImage fondo;            // imágen de fondo
    private Font fEstadisticas;             // fuentes para mostrar los resultados del pizarrón
    Reloj reloj;
    
    private float dt;                       // diferencia de tiempo para la ejecución de la simulación
    private Consumidor chef;                // objeto que 'atiende' a los clientes
    private Cliente cteActual;              // cliente que está siendo atendido
    private Cliente cteAtendido;            // cliente que ya fue atendido y que va saliendo del lugar
    private Productora filaClientes;        // fila de clientes esperando a ser atendidos
    private ArrayList<Cliente> impacientes; // clientes que se salen de la fila
    private Resultado resultado;            // lleva el conteo de clientes y platos
    private Color myColour;
    private boolean inicioCiclo;            // para mostrar el degradado en la pantalla cuando se inicia un ciclo
    private boolean estadisticas;           // indica si se debe mostrar el número vendiddo de cada plato
 
    /**
     * Constructor
     */
    public Simulacion(){
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        setLayout(null);
        fondo = Imagen.cargaImagen("sprites/fondo3.png");
        fEstadisticas = new Font("Comic Sans MS",Font.PLAIN,14);
        chef = new Consumidor();
        impacientes = new ArrayList();
        filaClientes = new Productora();
        resultado = new Resultado();
        cteAtendido = null;
        cteActual = null;
        myColour = new Color(1,1,1,0);
        inicioCiclo = true;
        estadisticas = false;
    }
    
    /**
     * Método que dibuja los componentes de la simulación en pantalla
     * @param g Entorno gráfico
     */
    @Override
    public void paint(Graphics g){          
        g.drawImage(fondo, 0, 0, PX_ANCHO, PX_ALTO, this);  // Dibujar el fondo
        reloj.pintarReloj(g);                               // Dibujar el reloj
        chef.pintarConsumidor(g);                           // Dibujar al cocinero    
            
        // dibujar el cliente que está esperando su pedido
        if(cteActual!=null){                                
            cteActual.pintarCliente(g);
        }
        // Mostrar el conteo de clientes atendidos, perdidos y creados en el
        // pizarron de la cafeteria
        g.setColor(new Color(255,255,255,215));
        g.setFont(fEstadisticas);
        g.drawString("Atendidos: " + resultado.getAtendidos(),72,50);
        g.drawString("Perdidos: " + resultado.getPerdidos(),72,70);
        g.drawString("En espera: " + filaClientes.getTamFila(),72,90);
        g.drawString("Total: " + filaClientes.getTotal(),72,110);
        g.drawString("Preparando",191,51);
        
        // Dibujar a los clientes formados en la fila
        int n=0;
        while(filaClientes.getTamFila()>0&&n<filaClientes.getTamFila()){
            filaClientes.getCliente(n).pintarCliente(g);
            n++;
        }
        // Dibujar los clientes que han salido de la fila
        impacientes.stream()
            .forEach((Cliente c) -> c.pintarCliente(g)); 
        
        // Dibujar el cliente que ya fue atendido y está saliendo del lugar
        if(cteAtendido!=null){
            cteAtendido.pintarCliente(g);
        }
        // Cambiar el color de la pantalla cuando está en pausa
        if(Main.pausa){
            g.setColor(new Color(0,0,66,150));
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);          
        }
        // Mostrar degradado de pantalla solo al iniciar la simulación
        if(inicioCiclo){
            g.setColor(myColour);
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);
        }
        // Mostrar degradado al terminar la simulación y al terminar
        // mostrar el número de platos vendidos de cada tipo
        if(!Main.ejecutando){
            g.setColor(myColour);
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);
            if(estadisticas){      
                resultado.pintarEstadisticas(g);
            }
        }       
    }
    
    /**
     * Método encargado del control de la simulación. Mientras el usuario no 
     * pause o detenga el programa, se ejecuta un ciclo en donde el objeto de
     * la clase Consumidor(cocinero) si está marcado como desocupado, toma el
     * primer objeto de la fila de clientes (si no está vacía) para atenderlo.
     * El consumidor recibe del cliente la información con el plato que debe
     * preparar, una vez recibida, comienza a preparlo, dibujándose una barra
     * con el progreso de la preparación; cuando esta concluye, le entrega al
     * cliente el objeto Pedido. Despues, el cliente sale del lugar.
     * Mientras el Consumidor está ocupado atendiendo a un cliente, el resto de
     * clientes en la fila sigue esperando mientras va disminuyendo el tiempo
     * que disponen de espera. Cuando el tiempo llega a cero salen de la fila.
     * En la pantalla se muestra un conteo del número de clientes creados, 
     * llamado 'Total', del número de clientes perdidos, el número de los 
     * clientes que están esperando en la fila y el número de clientes despachados. 
     * @throws java.lang.InterruptedException si el hilo es interrumpido 
     * mientras está suspendido
     */
    public synchronized void cicloPrincipalSimulacion() throws InterruptedException{
        int j;  // contador para el ciclo que resta el tiempo de espera de los clientes
        int s;  // contador para el ciclo que hace que los clientes impacientes salgan del lugar 
        
        transicionInicio();                     // mostrar el degradado al iniciar la simulación
        long tiempoViejo = System.nanoTime();   // calcula el tiempo en nanoSegundos
        while(Main.ejecutando){
            if(Main.pausa){                     // Si el usuario pausa el programa:
                //System.out.println("Pausa");
                dibuja();                       // dibujar los componentes
                wait();                         // suspender la ejecución del hilo cuando el usuario de pausa
                tiempoViejo=System.nanoTime();  // obtener el tiempo cuando se reanude la ejecución del hilo
            }
            // Obtener intervalos de tiempo pequeñisimos para el cáculo de los objetos de la simulación
            long tiempoNuevo = System.nanoTime();
            dt = (tiempoNuevo-tiempoViejo)/1000000000f; 
            tiempoViejo = tiempoNuevo;
            
            reloj.moverPendulo(dt); // calcular la posicion del péndulo según el tiempo
            dibuja();               // dibujar los componentes
            
            if(!chef.getOcupado()){                         // si el cocinero está descocupado y
                if(!filaClientes.filaVacia()){              // si hay clientes en la fila
                    cteActual = filaClientes.getCliente(0); // se toma el primero para atenderlo
                    filaClientes.borrarCliente(0);                  
                    chef.tomarPedido(cteActual);            // el cocinero se marca como ocupado
                }
            }else{
                cteActual.serAtendido(dt); 
                if(cteActual.getEstado() == Cliente.CTE_ESPERANDO_CHEF)
                    chef.atenderCliente(dt);
                if(cteActual.getEstado() == Cliente.CTE_ATENDIDO){          // si el cliente terminó de ser atendido                 
                    cteAtendido = cteActual; 
                    cteActual = null;
                    resultado.actPlatosVendidos(chef.getNumPlatoListo());   // actualizar la cantidad de platos vendidos
                }          
            }
            // Restar el tiempo de espera de cada cliente, si este se agota,
            // se borra el cliente de la fila y se añade a la lista impacientes
            j=0;
            while(filaClientes.getTamFila()>0&&j<filaClientes.getTamFila()){
                filaClientes.getCliente(j).avanzarFila(dt,j);
                filaClientes.getCliente(j).restarTiempoEspera(dt);
                if(filaClientes.getCliente(j).getEstado()==Cliente.CTE_IMPACIENTE){
                    impacientes.add(filaClientes.getCliente(j));
                    filaClientes.borrarCliente(j);
                }
                j++;
            }
            // mostrar animación del cliente que va saliendo del lugar
            if(cteAtendido!=null){
                cteAtendido.salir(dt); 
            }
            // Hacer que los clientes que agotaron su tiempo de espera salgan del lugar
            s=0;
            while(impacientes.size()>0&&s<impacientes.size()){
                impacientes.get(s).salir(dt);
                if(impacientes.get(s).getClienteFuera()){
                    resultado.sumarPerdidos();
                    impacientes.remove(s);                   
                }
                s++;
            }
        }
        transicionFin();
        System.out.println("Terminando ejecución de simulación");
    }
    
     /**
     * Método que hace que la simulación aparezca de forma gradual en la
     * pantalla al iniciar un nuevo ciclo.
     */
    public void transicionInicio(){
        for(int v=10;v>=0;v--){
            try{
                Thread.sleep(25);
            }catch(InterruptedException e){}
            myColour = new Color(0,0,0,v*0.1f); 
            dibuja();
        }
        inicioCiclo = false;    // evitar que se siga repitiendo el degradado
    }
    
    /**
     * Método que muestra un oscurecimiento gradual de la pantalla cuando se 
     * detiene la simulación.
     */
    public void transicionFin(){
        for(int v=0;v<=10;v++){
            try{
                Thread.sleep(25);
            }catch(InterruptedException e){}
            myColour = new Color(0,0,0,v*0.1f); 
            if(v==10){
                estadisticas = true;
            }
            dibuja();
        }
    }
    
    /**
     * Crea un hilo nuevo donde se llama al método que dibuja los componentes
     * de la simulación
     */
    private void dibuja(){
        try{
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Inicia el hilo que crea a los clientes e inicia el ciclo de la 
     * simulación
     */
    @Override
    public void run() {
        try{
            filaClientes.start();  
            cicloPrincipalSimulacion(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Método que esbloquea la ejecución del hilo cuando el usuario presionó el 
     * boton para pausar la simulación
     */
    public synchronized void reanudarSimulacion(){
        notify();
    }

    /**
     * Método que establece los atributos de la clase a sus valores por defecto
     * cuando se inicia un nuevo ciclo
     */
    public void reiniciarValores(){
        filaClientes = new Productora();
        chef = new Consumidor();
        cteActual = null;
        cteAtendido = null;
        impacientes.clear();
        myColour = new Color(1,1,1,0);
        reloj.moverManecillas(0, 0);
        inicioCiclo = true;
        estadisticas = false;
        resultado.reiniciar();
    }
}
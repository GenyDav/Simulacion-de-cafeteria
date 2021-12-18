package cafeteria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Clase que lleva el control del ciclo del programa
 * @author Geny
 */
public class Simulacion extends JPanel implements Runnable{
    private final int PX_ANCHO = 550;       // ancho del lienzo
    private final int PX_ALTO = 418;        // alto del lienzo 
    private BufferedImage fondo;            // imágen de fondo
    private Font fEstadisticas,fClientes;   // fuentes para mostrar las estadísticas y los mensajes de los clientes
    private Font fPausa;                    // fuente para el mensaje de pausa
    
    private float dt;                       // diferencia de tiempo para la ejecución de la simulación
    private int i,j,s;
    
    private Consumidor chef;                // objeto que 'atiende' a los clientes
    private Cliente cteActual;              // cliente que está siendo atendido
    private Cliente cteAtendido;            // cliente que ya fue atendido y que va saliendo del lugar
    public Productora filaClientes;         // fila de clientes esperando a ser atendidos
    private ArrayList<Cliente> impacientes; // clientes que se salen de la fila
    private int atendidos,perdidos;         // vaiables para conteo de clientes
    Color myColour;
    Reloj reloj;
    boolean inicioCiclo;                    // para mostrar el degradado en la pantalla cuando se inicia un ciclo
    JButton boton;
    boolean estadisticas;
    Resultados resultado;
 
    public Simulacion(String imgFondo){
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        setLayout(null);
        /*boton = new JButton("Boton");
        boton.setBounds(100, 100, 50, 100);
        add(boton);*/
        fondo = Imagen.cargaImagen(imgFondo);
        fEstadisticas = new Font("Comic Sans MS",Font.PLAIN,14);
        fClientes = getFont();
        fPausa = new Font("Comic Sans MS",Font.PLAIN,40);
        
        chef = new Consumidor();
        i=0;
        impacientes = new ArrayList();
        filaClientes = new Productora();
        cteAtendido = null;
        cteActual = null;
        //atendidos = 0;
        //perdidos = 0;
        myColour = new Color(1,1,1,0);
        inicioCiclo = true;
        estadisticas = false;
        resultado = new Resultados();
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(fondo, 0, 0, PX_ANCHO, PX_ALTO, this);
        //boton.repaint();
        reloj.pintarReloj(g);       
        
        g.setFont(fClientes);
        chef.pintarConsumidor(g);
        /*chef.setSprite((i++)/100);
        if(i>1000){
            i=0;
        }*/
        if(cteActual!=null){
            cteActual.pintarCliente(g);
            //System.out.println(cteActual.tmpEspera);
            //System.out.println(cteActual.porcentaje);
        }
        g.setColor(Color.WHITE);
        g.setFont(fEstadisticas);
        g.drawString("Atendidos: " + resultado.getAtendidos(),72,50);
        g.drawString("Perdidos: " + resultado.getPerdidos(),72,70);
        g.drawString("En espera: " + filaClientes.getTamFila(),72,90);
        g.drawString("Total: " + filaClientes.getTotal(),72,110);
        g.drawString("Preparando",191,51);
        //g.drawRect(200, 55, 57, 57);
        
        g.setFont(fClientes);
        int n=0;
        while(filaClientes.getTamFila()>0&&n<filaClientes.getTamFila()){
            filaClientes.getCliente(n).pintarCliente(g);
            n++;
        }
        
        int m=0;
        while(impacientes.size()>0&&m<impacientes.size()){
            impacientes.get(m).pintarCliente(g);
            m++;
        }
        
        if(cteAtendido!=null){
            cteAtendido.pintarCliente(g);
        }
        
        if(Main.pausa){
            g.setColor(new Color(0,0,66,100));
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);          
            //g.setFont(fPausa);
            //g.drawString("PAUSA",205,400);
        }
        
        if(!Main.ejecutando){
            g.setColor(myColour);
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);
        }
        
        if(inicioCiclo){
            g.setColor(myColour);
            g.fillRect(0, 0, PX_ANCHO, PX_ALTO);
        }
        
        if(estadisticas){
            System.out.println("Estadisticas");         
            resultado.pintarEstadisticas(g);
        }
    }
    
    public synchronized void cicloPrincipalJuego()throws Exception{
        for(int v=10;v>=0;v--){
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){}
            //System.out.println("Cerrando");
            myColour = new Color(0,0,0,v*0.1f); 
            dibuja();
        }
        inicioCiclo = false;
        long tiempoViejo = System.nanoTime();// calcula el tiempo en nanoSegundos
        while(Main.ejecutando){
            //System.out.println("-------");
            if(Main.pausa){
                //System.out.println("Pausa");
                dibuja();
                wait();
                //System.out.println("REANUDANDO...");
                tiempoViejo = System.nanoTime();
            }else{
                //System.out.println("REANUDANDO...");
                //notify();               
            }
            long tiempoNuevo = System.nanoTime();
            dt = (tiempoNuevo-tiempoViejo)/1000000000f;            
            tiempoViejo = tiempoNuevo;
            reloj.moverPendulo(dt);
            dibuja();   // dibujar los componentes
            if(!chef.getEstado()){ // si está desocupado
                if(filaClientes.getTamFila()>0){
                    cteActual = filaClientes.getCliente(0);
                    filaClientes.borrarCliente(0);                  
                    //link.setEstado(true);
                    chef.tomarPedido(cteActual);
                }
            }else{ // si está ocupado
                cteActual.serAtendido(dt); 
                if(cteActual.getEstado() == Cliente.CTE_ESPERANDO_CHEF)
                    chef.atenderCliente(dt);
                if(cteActual.getEstado() == Cliente.CTE_SALIENDO_LUGAR){    // si el cliente terminó de ser atendido
                    chef.setEstado(false);                                  // marcar al que atiende como desocupado
                    cteAtendido = cteActual; 
                    resultado.actPlatosVendidos(chef.getNumPlatoListo());              
                }
                j=0;
                while(filaClientes.getTamFila()>0&&j<filaClientes.getTamFila()){
                    filaClientes.getCliente(j).avanzarFila(dt,j);
                    filaClientes.getCliente(j).restarTiempoEspera(dt);
                    if(filaClientes.getCliente(j).getSalida()){
                        impacientes.add(filaClientes.getCliente(j));
                        filaClientes.borrarCliente(j);
                    }
                    j++;
                }
            }
            if(cteAtendido!=null){
                cteAtendido.salir(dt); // mostrar animación del cliente que va saliendo
            }
            s=0;
            while(impacientes.size()>0&&s<impacientes.size()){
                impacientes.get(s).salir(dt);
                if(impacientes.get(s).getTerminado()){ // como el cliente ya salió del lugar, se elimina de la lista
                    resultado.sumarPerdidos();
                    impacientes.remove(s);
                }
                s++;
            }
        }
        for(int v=0;v<=9;v++){
            try{
                Thread.sleep(15);
            }catch(InterruptedException e){}
            //System.out.println("Cerrando");
            myColour = new Color(0,0,0,v*0.1f); 
            dibuja();
        }
        estadisticas = true;
        System.out.println("Terminando ejecución de simulación");
    }
    
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
    
    public void pintarResultados(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(fEstadisticas);
        g.drawString("Atendidos: ",70,50);
    }
    
    @Override
    public void run() {
        try{
            //System.out.println("Iniciando productora......");
            filaClientes.start();
            cicloPrincipalJuego();
        }catch(Exception e){
            System.out.println("EXCEPCION: ");
            e.printStackTrace();
        }
        //System.out.println("Terminando hilo");
    }
    
    public synchronized void reanudarSimulacion(){
        notify();
    }

    public void reiniciarValores(){
        atendidos = 0;
        perdidos = 0;
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
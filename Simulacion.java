package cafeteria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Clase que lleva el control del ciclo del programa
 * @author Geny
 */
public class Simulacion extends JPanel{
    private final int PX_ANCHO = 550;       // ancho del lienzo
    private final int PX_ALTO = 418;        // alto del lienzo 
    private BufferedImage fondo;            // imágen de fondo
    private Font fEstadisticas,fClientes;   // fuentes para mostrar las estadísticas y los mensajes de los clientes
    
    private float dt;                   // diferencia de tiempo para la ejecución de la simulación
    private int i,j,s;
    
    private Consumidor link;                // objeto que 'atiende' a los clientes
    private Cliente cteActual;              // cliente que está siendo atendido
    private Cliente cteAtendido;            // cliente que ya fue atendido y que va saliendo del lugar
    private Productora filaClientes;        // fila de clientes esperando a ser atendidos
    private ArrayList<Cliente> impacientes; // clientes que se salen de la fila
    private int atendidos,perdidos;         // vaiables para conteo de clientes
 
    public Simulacion(String imgFondo){
        //setLayout(new FlowLayout());
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        fondo = Imagen.cargaImagen(imgFondo);
        fEstadisticas = new Font("Comic Sans MS",Font.PLAIN,14);
        fClientes = getFont();
        
        link = new Consumidor();
        i=0;
        impacientes = new ArrayList();
        filaClientes = new Productora();
        cteAtendido = null;
        cteActual = null;
        atendidos = 0;
        perdidos = 0;
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(fondo, 0,0,PX_ANCHO, PX_ALTO, this);
        link.pintarConsumidor(g);
        link.setSprite((i++)/100);
        if(i>1000){
            i=0;
        }
        //Color myColour = new Color(255, 255,255,150); // 127, 50%
        if(cteActual!=null){
            cteActual.pintarCliente(g);
            //System.out.println(cteActual.tmpEspera);
            //System.out.println(cteActual.porcentaje);
        }
        g.setColor(Color.WHITE);
        g.setFont(fEstadisticas);
        g.drawString("Atendidos: "+atendidos,300,88);
        g.drawString("Perdidos: "+perdidos,300,108);
        g.drawString("En espera: "+filaClientes.getTamFila(),300,128);
        g.drawString("Total: "+filaClientes.getTotal(),300,148);
        
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
        //g.drawRect(300,254,50,50);
    }
    
    public synchronized void cicloPrincipalJuego()throws Exception{
        filaClientes.start();
        long tiempoViejo = System.nanoTime();// calcula el tiempo en nanoSegundos
        while(true){
            long tiempoNuevo = System.nanoTime();
            dt = (tiempoNuevo-tiempoViejo)/1000000000f;            
            tiempoViejo = tiempoNuevo;
            dibuja();   // dibujar los componentes
            if(!link.getEstado()){ // si está desocupado
                if(filaClientes.getTamFila()>0){
                    cteActual = filaClientes.getCliente(0);
                    filaClientes.borrarCliente(0);                  
                    //link.setEstado(true);
                    link.tomarPedido(cteActual);
                }
            }else{ // si está ocupado
                cteActual.serAtendido(dt); 
                if(cteActual.getEstado()==2)
                    link.atenderCliente(dt);
                if(cteActual.getEstado()==3){   // si el cliente terminó de ser atendido
                    link.setEstado(false);      // marcar al que atiende como desocupado
                    cteAtendido = cteActual; 
                    atendidos++;
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
                    perdidos++;
                    impacientes.remove(s);
                }
                s++;
            }
        }
    }
    
    private void dibuja()throws Exception{
        SwingUtilities.invokeAndWait(() -> {
            repaint();
        });
    }
}

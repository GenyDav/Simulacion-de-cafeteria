package cafeteria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Geny
 */
public class Simulacion extends JPanel{
    private final int PX_ANCHO = 600;   // ancho del lienzo
    private final int PX_ALTO = 418;    // alto del lienzo 
    private BufferedImage fondo;        // imágen de fondo
    private Consumidor link;            // objeto que 'atiende' a los clientes
    private float dt;                   // diferencia de tiempo para la ejecución de la simulación
    private int i,j,s;
    private Cliente cteActual;              // cliente que está siendo atendido
    private Cliente cteAtendido;            // cliente que ya fue atendido y que va saliendo del lugar
    private Productora filaClientes;        // fila de clientes esperando a ser atendidos
    private ArrayList<Cliente> impacientes; // clientes que se salen de la fila
    private int total,atendidos,perdidos;   // conteo de clientes
    
    public Simulacion(String imgFondo){
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        fondo = Imagen.cargaImagen(imgFondo);
        link = new Consumidor();
        i=0;
        impacientes = new ArrayList();
        filaClientes = new Productora();
        cteAtendido = null;
        cteActual = null;
        total = 0;
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
        Color myColour = new Color(255, 255,255,150); // 127, 50%
        g.setColor(myColour);
        g.fillRect(255, 90, 190, 150);
        g.setColor(new Color(255,255,255));
        g.drawRect(255, 90, 190, 150);
        if(cteActual!=null){
            cteActual.pintarCliente(g);
        }
        
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
        g.drawRect(300,254,50,50);
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
                    link.setEstado(true);
                    atendidos++;
                }
            }else{ // si está ocupado
                cteActual.serAtendido(dt); 
                if(cteActual.getEstado()==3){ // si el cliente terminó de ser atendido
                    link.setEstado(false); // marcar al que atiende como desocupado
                    cteAtendido = cteActual; 
                }
                j=0;
                while(filaClientes.getTamFila()>0&&j<filaClientes.getTamFila()){
                    filaClientes.getCliente(j).formarse(dt,j);
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
                    impacientes.remove(s);
                    perdidos++;
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

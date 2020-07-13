/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final int PX_ANCHO = 550;   // ancho del lienzo
    private final int PX_ALTO = 418;    // alto del lienzo 
    private BufferedImage fondo;        // imágen de fondo
    private Consumidor link;            // objeto que 'atiende' a los clientes
    private int i;
    private Cliente cteActual;
    private ArrayList<Cliente> fila;        // fila de clientes esperando a ser atendidos
    //private ArrayList<Cliente> impacientes; // clientes que se salen de la fila
    
    public Simulacion(String imgFondo){
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        fondo = Imagen.cargaImagen(imgFondo);
        link = new Consumidor();
        i=0;
        cteActual = new Cliente();
        fila = new ArrayList();
        //impacientes = new ArrayList();
        fila.add(new Cliente(5,5));
        fila.add(new Cliente(5,4));
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
        cteActual.pintarCliente(g);
        g.drawRect(300,254,50,50);
    }
    
    public synchronized void cicloPrincipalJuego()throws Exception{
        long tiempoViejo = System.nanoTime();// calcula el tiempo en nanoSegundos
        while(true){
            long tiempoNuevo = System.nanoTime();
            float dt = (tiempoNuevo-tiempoViejo)/1000000000f;            
            tiempoViejo = tiempoNuevo;
            dibuja();
            if(!link.getEstado()){ // si está desocupado
                if(fila.size()>0){
                    cteActual = fila.get(0);
                    fila.remove(0);
                    link.setEstado(true);
                    //System.out.println("tamaño de la fila: "+fila.size());
                }
            }else{ // si está ocupado
                cteActual.serAtendido(dt);    
                int j=0;
                while(fila.size()>0&&j<fila.size()){
                    System.out.println("dentro del ciclo");
                    fila.get(j).restarTiempoEspera(dt);
                    if(fila.get(j).getSalida()){
                        //impacientes.add(fila.get(n));
                        System.out.println("Cliente saliendo");
                        try{
                        fila.remove(j);
                        }catch(Exception e){
                            System.out.println(e);
                        }                  
                       System.out.println("Cliente eliminado");
                    }
                    j++;
                }
            }
            /*for(int s=0;s<impacientes.size();s++){
                impacientes.get(s).salir(dt);
            }*/
        }
    }
    
    private void dibuja()throws Exception{
        SwingUtilities.invokeAndWait(() -> {
            repaint();
        });
    }
}

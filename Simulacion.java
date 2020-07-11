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
    private Consumidor link;            
    private int i;
    private Cliente lady;
    
    public Simulacion(String imgFondo){
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(PX_ANCHO,PX_ALTO));
        fondo = Imagen.cargaImagen(imgFondo);
        link = new Consumidor();
        i=0;
        lady = new Cliente();
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
        lady.pintarCliente(g);
    }
    
    public synchronized void cicloPrincipalJuego()throws Exception{
        long tiempoViejo = System.nanoTime();// calcula el tiempo en nanoSegundos
        while(true){
            long tiempoNuevo = System.nanoTime();
            float dt = (tiempoNuevo-tiempoViejo)/1000000000f;            
            tiempoViejo = tiempoNuevo;
            // método para calcular la posicion del cliente
            dibuja();
            lady.avanzar(dt);
        }
    }
    
    private void dibuja()throws Exception{
        SwingUtilities.invokeAndWait(new Runnable(){
            @Override
            public void run(){
                repaint();
                //paintImmediately(0,0,PX_ANCHO,PX_ALTO);
            }
        });
    }
}

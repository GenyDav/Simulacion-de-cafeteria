/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Geny
 */
public class Fondo extends JPanel{
    private final int PX_ANCHO = 550; // ancho de la ventana
    private final int PX_ALTO = 418;  // alto de la ventana 
    private BufferedImage fondo;
    Personaje link;
    
    public Fondo(String imgFondo){
        super();
        setLayout(null);
        setSize(new Dimension(PX_ANCHO,PX_ALTO));
        // Cargar la imágen de fondo
        fondo = CargaImagen.cargaImagen(imgFondo);
        link = new Personaje();
    }
    
    @Override
    public void paint(Graphics g){
        // se llama al crear un objeto
        g.drawImage(fondo, 0,0,PX_ANCHO, PX_ALTO, this);
        link.pintar(g);
    }
    
    private void dibuja()throws Exception{
        SwingUtilities.invokeAndWait(new Runnable(){
            @Override
            public void run(){
                paintImmediately(0,0,PX_ANCHO, PX_ALTO);
            }
        });
    }
}

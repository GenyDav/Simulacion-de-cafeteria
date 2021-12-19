package cafeteria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Geny
 */
public class Resultados {
    private int atendidos;
    private int perdidos;
    private int platosVendidos[];
    BufferedImage plato;
    int numPlato;
    int x,y;
    
    public Resultados(){
        atendidos = 0;
        perdidos = 0;
        platosVendidos = new int[12];
        for(int i=0;i<12;i++){
            platosVendidos[i]=0;
        }
        numPlato = 0;
        x = 110;
        y = 85;
    }
    
    public void sumarPerdidos(){
        perdidos++;
    }
    
    public int getPerdidos(){
        return perdidos;
    }
    
    public int getAtendidos(){
        return atendidos;
    }
    
    public void reiniciar(){
        atendidos = 0;
        perdidos = 0;
        for(int i=0;i<12;i++){
            platosVendidos[i]=0;
        }
    }
    
    public void actPlatosVendidos(int numPlato){
        platosVendidos[numPlato-1]++;
        atendidos++;
    }
    
    public void pintarEstadisticas(Graphics g){    
        int xAux, yAux;
        g.setColor(new Color(216,120,72));
        g.fillRoundRect(50, 28, 450, 35, 10, 10);   // recuadro para título
        g.fillRoundRect(50, 355, 450, 33, 10, 10);  // recuadro para total
        
        g.setColor(new Color(243,214,202));         
        g.fillRect(50, 56, 450, 305);               // recuadro de fondo
        
        g.setColor(Color.WHITE);                   
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,20)); 
        g.drawString("Platos Servidos",205,50);     // titulo de los resultados
        
        g.drawString("Total: ",80,382);             
        g.drawString(String.valueOf(atendidos),140,382);
        
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,15)); // fuente para los números de platos servidos
        
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){                              
                numPlato++;      
                plato = Imagen.cargaImagen("cafeteria/sprites/plato" + numPlato + "_2.png");
                
                xAux = 80+(x*i);
                yAux = y*(j+1);
                g.setColor(new Color(184,136,96));                  // fondo de cada plato
                g.fillRoundRect(xAux,yAux, 60, 60, 10, 10);
                g.setColor(new Color(74,31,22));                    // marco de cada plato
                g.drawRoundRect(xAux,yAux, 60, 60, 10, 10);
                
                g.drawImage(plato,xAux,yAux, null);
                
                g.drawString("x",85+(110*i),77+y*(j+1));
                g.drawString(String.valueOf(platosVendidos[numPlato-1]),95+(110*i),77+y*(j+1));
            }
        }
        numPlato = 0;          
    }
}
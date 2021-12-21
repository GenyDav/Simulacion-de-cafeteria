package cafeteria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Clase que lleva el conteo del número de clientes atendidos, 
 * el número de clientes perdidos y la cantidad de platos servidos
 * de cada uno
 * @author Geny D.
 * @version 1.0
 */
public class Resultado {
    private int atendidos;
    private int perdidos;
    private int platosVendidos[];
    
    /**
     * Constructor
     */
    public Resultado(){
        atendidos = 0;
        perdidos = 0;
        platosVendidos = new int[12];
        for(int i=0;i<12;i++){
            platosVendidos[i]=0;
        }
    }
    
    /**
     * Método que incrementa en uno el número de clientes perdidos
     */
    public void sumarPerdidos(){
        perdidos++;
    }
    
    /**
     * Método que regresa el número de clientes perdidos
     * @return número de clientes perdidos
     */
    public int getPerdidos(){
        return perdidos;
    }
    
    /**
     * Método que regresa el número de clientes atendidos
     * @return 
     */
    public int getAtendidos(){
        return atendidos;
    }
    
    /**
     * Reincia la cantidad de clientes atendidos, perdidos
     * y el número de platos servidos a cero
     */
    public void reiniciar(){
        atendidos = 0;
        perdidos = 0;
        for(int i=0;i<12;i++){
            platosVendidos[i]=0;
        }
    }
    
    /**
     * Método que incrementa en uno el número de platos vendidos
     * de un plato específico, determinado por su número de identificación
     * @param numPlato número de identificación del plato
     */
    public void actPlatosVendidos(int numPlato){
        platosVendidos[numPlato-1]++;
        atendidos++;
    }
    
    /**
     * Método que muestra al terminar la simulación el número de clientes
     * atendidos; el sprite de cada plato vendido junto con la cantidad de unidades
     * servidas de cada plato
     * @param g Contexto gráfico 
     */
    public void pintarEstadisticas(Graphics g){    
        int xAux, yAux;         // coordenadas para ubicar cada imágen de los platos
        BufferedImage plato;   
        int numPlato = 0;       // variable auxiliar para obtener cada imágen de los platos
        int x = 110;            // distancia horizontal entre cada esquina superior izquierda de los sprite
        int y = 85;             // distancia vertica entre cada esquina superior izquierda de los sprite
        
        g.setColor(new Color(216,120,72));
        g.fillRoundRect(50, 28, 450, 35, 10, 10);   // recuadro para título
        g.fillRoundRect(50, 355, 450, 33, 10, 10);  // recuadro para total
        
        g.setColor(new Color(243,214,202));         
        g.fillRect(50, 56, 450, 305);               // recuadro de fondo de la pantalla
        
        g.setColor(Color.WHITE);                   
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,20)); 
        g.drawString("Platos Servidos",205,50);         // titulo de los resultados    
        g.drawString("Total: ",80,382);                 // Texto para el total de platos servidos
        g.drawString(String.valueOf(atendidos),140,382);
        
        g.setFont(new Font("Comic Sans MS",Font.PLAIN,15)); // fuente para los números de platos servidos
        
        /*  Mostrar los sprites de los platos organizandolos como una matriz de
            3 renglones y 4 columnas
        */
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){                              
                numPlato++;      
                plato = Imagen.cargaImagen("sprites/plato" + numPlato + "_2.png");
                
                xAux = 80+(x*i);    // calcular las coordenadas 
                yAux = y*(j+1);     // donde se va a mostrar el sprite
                
                g.setColor(new Color(184,136,96));                  // dibujar el fondo de cada plato
                g.fillRoundRect(xAux,yAux, 60, 60, 10, 10);
                g.setColor(new Color(74,31,22));                    // dibujar el marco de cada plato
                g.drawRoundRect(xAux,yAux, 60, 60, 10, 10);
                g.drawImage(plato,xAux,yAux, null);                 // dibujar el plato
                
                // Mostrar las unidades servidas para el plato
                g.drawString("x",85+(110*i),77+y*(j+1));
                g.drawString(String.valueOf(platosVendidos[numPlato-1]),95+(110*i),77+y*(j+1));
            }
        }          
    }
}
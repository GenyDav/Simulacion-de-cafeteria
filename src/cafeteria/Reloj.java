package cafeteria;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase que proporciona los métodos necesarios para mostrar
 * los elementos gráficos del reloj en la pantalla de la simulación
 * En la simulación 1 hora se representa con un minuto; un minuto se 
 * representa con un segundo.
 * @author Geny D.
 * @version 1.0
 */
public final class Reloj {
    private double tmpIntervalo;                // tiempo de simulación transcurrido
    private BufferedImage sprt1,sprt2,sprt3;    // sprites de las distintas posiciones del péndulo del reloj
    private BufferedImage sprtActual;           // sprite del péndulo que se está muestra en pantalla 
    private int ladoPendulo;                    
    private final int IZQ = 1;  
    private final int DER = 2;
    private final int x,y;                      // coordenadas del centro de las manecillas
    private int horaSistema;                    
    private int xMin,yMin;                      // coordenadas de la punta del minutero (que representa las horas)
    private int xSeg,ySeg;                      // coordenadas de la punta del segundero (que representa los minutos)
    private int min,seg;                        // min(hora) y seg(minutos) marcados en el reloj
    private final int radioMin, radioSeg;       // longitud de las manecillas del reloj
    
    /**
     * Constructor
     */
    public Reloj(){
        sprt1 = Imagen.cargaImagen("sprites/reloj_1.png"); // El sprite del péndulo se inclina a la derecha
        sprt2 = Imagen.cargaImagen("sprites/reloj_2.png"); // El sprite del péndulo está en el centro
        sprt3 = Imagen.cargaImagen("sprites/reloj_3.png"); // El sprite del péndulo se inclina a la izquierda
        ladoPendulo = IZQ;
        sprtActual = sprt3;                    
        tmpIntervalo = 0;
        horaSistema = obtenerHoraSistema();
        min = horaSistema;
        seg = 0;
        radioMin = 8;
        radioSeg = 12;
        x = 314;
        y = 64;
        xMin = (int)(x+radioMin*Math.sin(this.min*30*Math.PI/180)); // calcula la posición de la manecilla
        yMin = (int)(y-radioMin*Math.cos(this.min*30*Math.PI/180)); // de la hora según la hora del sistema
        xSeg = 314;     // colocar la manecilla de los minutos
        ySeg = 43;      // apuntando a las 12
    }
    
    /**
     * Método utilizados para mostrar en pantalla los componentes del reloj
     * @param g contexto gráfico
     */
    public void pintarReloj(Graphics g){
        g.drawImage(sprtActual,Math.round(305),Math.round(93),null);
        
        Graphics2D g2 = (Graphics2D)g;
        g.setColor(Color.BLACK);   
        // dibujar la manecilla del minutero (segundos de animación transcurridos)
        g2.setStroke(new BasicStroke(2));
        g.drawLine(x,y,xSeg,ySeg);          
        
        // dibujar la manecilla de la hora (minutos de animación transcurridos)
        g2.setStroke(new BasicStroke(3));   
        g.drawLine(x,y,xMin,yMin);          
    }
    
    /**
     * Método para calcular la posición de las manecillas del reloj
     * @param min minutos transcurridos desde el inicio de la animación
     * @param seg segundos transcurridos en el minuto actual
     */
    public void moverManecillas(int min, int seg){
        this.min = horaSistema + min;
        this.seg = seg;
        xSeg = (int)(x+radioSeg*Math.sin(this.seg*6*Math.PI/180));  // cada seg. la manecilla avanza 6°;
        ySeg = (int)(y-radioSeg*Math.cos(this.seg*6*Math.PI/180));  // despues los grados se convierten a radianes
        xMin = (int)(x+radioMin*Math.sin(this.min*30*Math.PI/180)); // cada min. la manecilla avanza 30°
        yMin = (int)(y-radioMin*Math.cos(this.min*30*Math.PI/180));
    }
    
    /**
     * Método para calcular la posición del péndulo en función del tiempo
     * @param dt cantidad de milisegundos transcurridos en la simulación
     */
    public void moverPendulo(double dt){
        tmpIntervalo += dt;
        if(tmpIntervalo>=0.450&&tmpIntervalo<=0.550){   // si el tiempo contado es aprox. medio segundo
            sprtActual = sprt2;                         // el péndulo se encuentra en el centro
        }else if(tmpIntervalo>=1){                      // si el tiempo contado es de 1 segundo
            tmpIntervalo = 0;                           // se cambia el sentido del péndulo
            if(ladoPendulo==DER){
                sprtActual = sprt3;
                ladoPendulo = IZQ;
            }
            else if(ladoPendulo==IZQ){
                ladoPendulo = DER;
                sprtActual = sprt1;
            }
        }     
    }
    
    /**
     * Método que obtiene la hora del sistema para utilizarla como la hora
     * mostrada en el reloj
     * @return hora del sistema
     */
    public int obtenerHoraSistema(){
        return new GregorianCalendar().get(Calendar.HOUR);
    }
}

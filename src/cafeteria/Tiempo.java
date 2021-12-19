package cafeteria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Clase para llevar el tiempo transcurrido durante la ejecución de la simulación
 * @author Geny D.
 * @version 1.0
 */
public class Tiempo {
    private int min;                // minutos transcurridos 
    private int seg;                // segundos transcurridos
    private Timer timer;            // objeto que se ejecuta cada 1000ms y modifica los demas atributos
    private JLabel etiqueta;        // referencia que apunta a la equiteta de la clase Main
    private String txtMin, txtSeg;  // valores mostrados en la etiqueta
    Reloj r;
    
    /**
     * Constructor
     */
    public Tiempo(){
        r = new Reloj();
        min = 0;
        seg = 0;
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                seg++;
                if(seg>=60){
                   min++;
                   seg = 0;
                }
                txtMin = min<10?"0"+Integer.toString(min):Integer.toString(min);
                txtSeg = seg<10?"0"+Integer.toString(seg):Integer.toString(seg);            
                
                etiqueta.setText("Tiempo: "+txtMin+":" + txtSeg);
                r.moverManecillas(min, seg);    // pintar las manecillas del reloj según el tiempo
            }
        });  
    } 
    
    /**
     * Método de devuelve el número de minutos transcurridos desde el inicio de la simulación
     * @return minutos transcurridos
     */
    public int getMin(){
        return min;
    }
    
    /**
     * Método que devuelve el número de segundos transcurridos asociados con un minuto
     * @return segundos transcurridos
     */
    public int getSeg(){
        return seg;
    }
    
    /**
     * Inicia el conteo del tiempo
     */
    public void iniciarTiempo(){
        timer.start();
    }
    
    /**
     * Detiene el conteo del tiempo
     */
    public void detenerTiempo(){
        timer.stop();
    }
    
    /**
     * Reinicia todos los valores de tiempo
     */
    public void reiniciarContador(){
        min = 0;
        seg = 0;
        etiqueta.setText("Tiempo: 00:00");
        r.obtenerHoraSistema();
    }
    
    /**
     * Método que establece la referencia a el objeto JLabel del parámetro
     * @param label etiqueta en donde se muestra el tiempo 
     */
    public void setEtiqueta(JLabel label){
        etiqueta = label;
    }
    
    /**
     * Devuelve el objeto para representar el gráfico de un reloj
     * @return el objeto para el gráfico de tipo Reloj
     */
    public Reloj getReloj(){
        return r;
    }
}

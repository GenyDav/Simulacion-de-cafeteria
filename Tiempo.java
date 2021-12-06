/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Geny
 */
public class Tiempo {
    private int min;
    private int seg;
    private Timer timer;
    private JLabel etiqueta;
    private String txtMin, txtSeg;
    Reloj r;
    
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
                //System.out.println(min+":"+seg);
                etiqueta.setText(txtMin+":" + txtSeg);
                r.moverManecillas(min, seg);
            }
        });  
    } 
    
    public int getMin(){
        return min;
    }
    
    public int getSeg(){
        return seg;
    }
    
    public void iniciarTiempo(){
        timer.start();
    }
    
    public void detenerTiempo(){
        timer.stop();
    }
    
    public void reiniciarContador(){
        min = 0;
        seg = 0;
        etiqueta.setText("00:00");
        r.obtenerHoraSistema();
    }
    
    public void setEtiqueta(JLabel label){
        etiqueta = label;
    }
    
    public Reloj getReloj(){
        return r;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Geny Dávila
 * @version 2.2 
 */
public class Main {
    public static volatile boolean pausa;
    public static volatile boolean ejecutando;
    public Simulacion s;
    JFrame frame;
    JPanel panelControl;
    JButton btnPausa;
    JButton btnReinicio;
    JLabel tiempo;
    JLabel mensaje;
    Thread ciclo;
    Tiempo t;
    
    public Main(){
        pausa = false;
        ejecutando = true;
        frame = new JFrame("Cafetería");
        s = new Simulacion("cafeteria/sprites/fondo3.png");
        panelControl = new JPanel();
        btnPausa = new JButton("Pausa");
        btnReinicio = new JButton("Detener");
        
        btnPausa.setBackground(Color.black);
        btnPausa.setToolTipText("Pausar la simulación");
        btnPausa.setForeground(Color.white);
        btnPausa.setFocusPainted(false);
        
        btnReinicio.setBackground(Color.black);
        btnReinicio.setToolTipText("Detener la simulación actual");
        btnReinicio.setForeground(Color.white);
        btnReinicio.setFocusPainted(false);
        
        tiempo = new JLabel("00:00");
        tiempo.setToolTipText("Tiempo transcurrido");
        mensaje = new JLabel("Ejecutando");
        mensaje.setToolTipText("Estado de la simulación");
        ciclo = new Thread(s);
        t = new Tiempo();
        //System.out.println(t.r.toString());
    }
    
    public void configurarVentana(){
        frame.add(s, BorderLayout.CENTER);
        panelControl.setLayout(new GridLayout(1,4));
        panelControl.setBackground(Color.BLACK);
        
        JPanel aux1 = new JPanel();
        tiempo.setForeground(Color.WHITE);
        aux1.setOpaque(false);
        aux1.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        aux1.add(tiempo);
        panelControl.add(aux1);
        
        JPanel aux2 = new JPanel();
        aux2.setOpaque(false);
        aux2.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        aux2.add(btnPausa);
        panelControl.add(aux2);
        
        JPanel aux3 = new JPanel();
        aux3.setOpaque(false);
        aux3.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        aux3.add(btnReinicio);
        panelControl.add(aux3);
        
        JPanel aux4 = new JPanel();
        aux4.setOpaque(false);
        mensaje.setForeground(Color.WHITE);
        aux4.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        aux4.add(mensaje);
        panelControl.add(aux4);
        
        frame.add(panelControl, BorderLayout.SOUTH);
        s.reloj = t.getReloj();
        ciclo.start();
        t.setEtiqueta(tiempo);
        t.iniciarTiempo();
        
        btnPausa.addActionListener(e->{
            pausa = !pausa;
            
            if(!pausa){
                //System.out.println("reanudando: " + pausa);
                s.reanudarSimulacion();
                btnPausa.setText("Pausa");
                mensaje.setText("En ejecución");
                t.iniciarTiempo();
            }else{
                //System.out.println("Pausa: " + pausa);
                btnPausa.setText("Reanudar");
                mensaje.setText("En pausa");
                t.detenerTiempo();
            }
        });

        btnReinicio.addActionListener(e->{
            try{
                if(ejecutando){
                    // para limpiar correctamente la pantalla cuando está en pausa 
                    // y despues se detiene el ciclo
                    if(pausa){
                        s.reanudarSimulacion();
                    }
                    ejecutando = false;
                    btnPausa.setEnabled(false);
                    btnReinicio.setText("Iniciar");
                    //System.out.println("alive?: "+ciclo.isAlive());
                    ciclo = null;
                    t.detenerTiempo();
                    t.reiniciarContador();
                    //System.out.println("alive?: "+ciclo.isAlive());
                    mensaje.setText("Simulación detenida");
                }else{
                    ejecutando = true;
                    s.reiniciarValores();
                    ciclo = new Thread(s);
                    ciclo.start();
                    btnReinicio.setText("Detener");
                    btnPausa.setEnabled(true);
                    pausa = false;
                    btnPausa.setText("Pausa");
                    mensaje.setText("En ejecución");
                    t.iniciarTiempo();
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
        //frame.setResizable(false);
    }
    
    public static void main(String []args){
        Main m = new Main();
        m.configurarVentana();
    }
}

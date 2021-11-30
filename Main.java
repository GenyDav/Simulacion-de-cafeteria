/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    Thread ciclo;
    
    public Main(){
        pausa = false;
        ejecutando = true;
        frame = new JFrame("Cafetería");
        s = new Simulacion("cafeteria/sprites/fondo.png");
        panelControl = new JPanel();
        btnPausa = new JButton("Pausa");
        btnReinicio = new JButton("Detener");
        ciclo = new Thread(s);
    }
    
    public void configurarVentana(){
        frame.add(s, BorderLayout.CENTER);
        panelControl.setBackground(Color.black);
        panelControl.add(btnPausa);
        panelControl.add(btnReinicio);
        frame.add(panelControl, BorderLayout.SOUTH);
        ciclo.start();
        
        btnPausa.addActionListener(e->{
            pausa = !pausa;
            if(!pausa){
                System.out.println("reanudando: " + pausa);
                s.reanudarSimulacion();
                btnPausa.setText("Pausa");
            }else{
                System.out.println("Pausa: " + pausa);
                btnPausa.setText("Reanudar");
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
                    System.out.println("alive?: "+ciclo.isAlive());
                    ciclo = null;
                    System.out.println("alive?: "+ciclo.isAlive());
                }else{
                    ejecutando = true;
                    s.reiniciarValores();
                    ciclo = new Thread(s);
                    ciclo.start();
                    btnReinicio.setText("Detener");
                    btnPausa.setEnabled(true);
                    pausa = false;
                    btnPausa.setText("Pausa");
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

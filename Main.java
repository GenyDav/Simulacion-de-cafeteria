/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Geny
 */
public class Main {
    public static void main(String []args){
        JFrame frame = new JFrame("Simulación café");
        Simulacion s = new Simulacion("cafeteria/sprites/fondo.png");           
        frame.add(s);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
       
        try{
            s.cicloPrincipalJuego();
        }catch(Exception e){
            
        }
    }
}

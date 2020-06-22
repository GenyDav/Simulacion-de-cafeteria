/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Geny
 */
public class Escena {
    JFrame marco;
    JPanel lienzo;
    JPanel contador;
    Escena(){
        marco = new JFrame("Café");
        marco.setDefaultCloseOperation(EXIT_ON_CLOSE);
        lienzo = new Fondo("cafeteria/sprites/fondo.png");
        contador = new JPanel();
        contador.setBackground(Color.DARK_GRAY);
        contador.setSize(new Dimension(566,20));
        marco.add(contador);
        marco.setSize(new Dimension(566,457));
        //marco.setContentPane(lienzo);
        marco.add(lienzo);
        marco.setLocationRelativeTo(null);
        marco.setVisible(true);
    }
    
    public static void main(String []args){
        Escena e = new Escena();
        
    }
}

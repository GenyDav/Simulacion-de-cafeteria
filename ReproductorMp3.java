/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Geny
 */
public class ReproductorMp3 {
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        System.out.println(System.getProperty("user.dir"));
        Player apl = new Player(new FileInputStream("src\\cafeteria\\sprites\\audio.mp3"));
        apl.play();
   }
}

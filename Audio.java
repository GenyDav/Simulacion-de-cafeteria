/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.io.FileInputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Geny
 */
public class Audio extends Thread {
    FileInputStream cancion;
    Player pl;
    
    public Audio(){
        try{
            cancion = new FileInputStream("src\\cafeteria\\sprites\\audio.mp3");
            pl = new Player(cancion);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override 
    public void run(){
        try{
            while(true){
                if(!Main.ejecutando){
                    break;
                }else{
                    if(!Main.pausa){
                        if(!pl.play(1)){ 
                            break;
                        }
                    }                 
                }
            }
            // Crear nuevo hilo para repetir la reproducción de la música
            // solo si el programa se está ejecutando
            if(Main.ejecutando)
                new Audio().start();   
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Terminado");
    }
}

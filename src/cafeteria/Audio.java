package cafeteria;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Clase que reproduce una canción mientras la simulación se está ejecutando.
 * @author Geny
 * @version 1.0
 */
public class Audio extends Thread {
    FileInputStream cancion;
    Player pl;
    
    /**
     * Constructor
     */
    public Audio(){
        try{
            cancion = new FileInputStream("src\\sonido\\audio.mp3");
            pl = new Player(cancion);
        }catch(FileNotFoundException | JavaLayerException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Reproduce una canción de forma continua mientras no se pause o se
     * detenga por completo la simulación
     */
    @Override 
    public void run(){
        try{
            while(Main.ejecutando){
                if(!Main.pausa){
                    if(!pl.play(1)){    // si no hay más frames por reproducir
                        break;
                    }
                } 
            }
            
            // Crear nuevo hilo para repetir la reproducción de la canción
            // cuando el hilo anterior termina de reproducirse
            // (solo si la simulación se está ejecutando)
            if(Main.ejecutando)
                new Audio().start();   
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}

package cafeteria;

import java.util.ArrayList;
import static java.lang.Math.random;
import static java.lang.Math.round;

/**
 *
 * @author Geny
 */
public class Productora extends Thread{
    private volatile ArrayList<Cliente> fila;
    
    public Productora(){
        super("Clase productora"); 
        fila = new ArrayList();
    }
    
    synchronized public void agregarCliente(){
        fila.add(new Cliente(round(random()*20+1),round(random()*11+5),(int)(Math.random()*15+1)));
        //System.out.println("fila: "+fila.size());
    } 
    
    synchronized public void borrarCliente(int cliente){
        fila.remove(cliente);
    }
    
    public Cliente getCliente(int cliente){
        return fila.get(cliente);
    }
    
    public int getTamFila(){
        return fila.size();
    }
    
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(3000); 
                System.out.println("creando cliente...");
                agregarCliente();
            }catch(InterruptedException e){}
        }
    }
}
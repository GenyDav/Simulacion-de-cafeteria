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
        fila.add(new Cliente(round(random()*20+1),round(random()*9+2)));
    } 
    
    synchronized public void borrarCliente(int cliente){
        fila.remove(cliente);
    }
    
    synchronized public Cliente getCliente(int cliente){
        return fila.get(cliente);
    }
    
    public int getNumeroClientes(){
        return fila.size();
    }
    
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(1000);           
            }catch(InterruptedException e){}
            finally{
                agregarCliente();
            }
        }
    }
}

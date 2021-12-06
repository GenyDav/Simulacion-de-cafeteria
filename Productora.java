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
    private volatile int total;         // número de clientes creados
    
    public Productora(){
        super("Clase productora"); 
        fila = new ArrayList();
        total = 0;
    }
    
    synchronized public void agregarCliente(){
        fila.add(new Cliente(round(random()*20+1),(int)(Math.random()*15+1),(int)(Math.random()*8+1)));
        //System.out.println("Agregado a la fila " + fila.size());
        total++;
        try{
            if(fila.size()==5){
                wait();
            }
        }catch(InterruptedException e){}
    } 
    
    synchronized public void borrarCliente(int cliente){
        fila.remove(cliente);
        //System.out.println("Cliente fuera de la fila");
        notify();
    }
    
    public Cliente getCliente(int cliente){
        return fila.get(cliente);
    }
    
    public int getTamFila(){
        return fila.size();
    }
    
    public int getTotal(){
        return total;
    }
    
    @Override
    public void run(){
        while(Main.ejecutando){
            try{
                Thread.sleep(3000); 
                if(!Main.pausa){
                    //System.out.println("creando cliente...");
                    agregarCliente();
                }else{
                    //System.out.println("pausando cliente...");
                }
            }catch(InterruptedException e){}
        }
        //System.out.println("Terminando productora");
    }
}
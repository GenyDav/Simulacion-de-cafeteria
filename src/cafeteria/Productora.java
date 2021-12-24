package cafeteria;

import java.util.ArrayList;
import static java.lang.Math.random;
import static java.lang.Math.round;

/**
 * Clase que crea los objetos de tipo Cliente durante la ejecución de la
 * simulación.
 * @author Geny
 * @version 2.2
 */
public class Productora extends Thread{
    private volatile ArrayList<Cliente> fila;   // lista que contiene todos los clientes 'formados' en la fila
    private volatile int total;                 // número neto de clientes creados
    
    /**
     * Constructor
     */
    public Productora(){
        super("Clase productora"); 
        fila = new ArrayList();
        total = 0;
    }
    
    /**
     * Método que crea un objeto de tipo cliente con valores aleatorios
     * para el tiempo que puede esperar el cliente en la fila, el número de 
     * identificación del plato y el número de idenficación del cliente. 
     * El objeto creado se agrega a la lista de clientes
     */
    synchronized public void agregarCliente(){
        // El tiempo de espera de un cliente se encuentra entre 10 y 30 seg
        // El número de plato entre 1 y 12
        // El número de cliente entre 1 y 8
        fila.add(new Cliente(round(random()*21+10),(int)(Math.random()*12+1),(int)(Math.random()*8+1)));
        //System.out.println("Agregado a la fila " + fila.size());
        //System.out.println(fila.get(fila.size()-1).tmpEspera);
        total++;
        try{
            if(fila.size()==5){ // si en la fila hay un máximo de 5 clientes, se pausa la creación
                wait();         // de objetos        
            }
        }catch(InterruptedException e){}
    } 
    
    /**
     * Elimina un numCliente de la lista según la posición pasada como parámetro
     * @param numCliente posición del cliente en la fila y desboquea al hilo
     * para continuar con la creación de objetos de tipo Cliente
     */
    synchronized public void borrarCliente(int numCliente){
        fila.remove(numCliente);
        notify();
    }
    
    /**
     * Método que devuelve un objeto de tipo Cliente, que se encuentra en la
     * fila esperando a ser atendido, basado en su posición en la lista
     * @param numCliente la posición del cliente en la fila
     * @return el cliente ubicado en esa posición
     */
    public Cliente getCliente(int numCliente){
        return fila.get(numCliente);
    }
    
    /**
     * Método que regresa el tamaño de la fila (número de clientes formados)
     * @return tamaño de la fila
     */
    public int getTamFila(){
        return fila.size();
    }
    
    /**
     * Método que regresa el número de clientes que el hilo ha creado
     * @return total de lcientes creados 
     */
    public int getTotal(){
        return total;
    }
    
    /**
     * Produce en un tiempo aleatorio (entre 1 y 10 segundos) un objeto de tipo 
     * Cliente mientras el usuario no pause o detenga por completo la animación
     */
    @Override
    public void run(){
        while(Main.ejecutando){
            try{
                Thread.sleep(round(random()*10+1)*1000);
                if(!Main.pausa){
                    //System.out.println("creando cliente...");
                    agregarCliente();
                }
            }catch(InterruptedException e){}
        }
        //System.out.println("Terminando productora");
    }
}
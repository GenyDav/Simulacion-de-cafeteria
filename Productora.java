/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.util.ArrayList;

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
        fila.add(new Cliente());
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
                agregarCliente();
            }catch(InterruptedException e){
            }
        }
    }
}

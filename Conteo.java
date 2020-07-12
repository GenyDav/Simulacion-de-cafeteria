/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

/**
 *
 * @author Geny
 */
public class Conteo {
    public static void main(String []args){
        int j=1000000;
        long tiempoViejo = System.nanoTime();// calcula el tiempo en nanoSegundos
        for(int m=0;m<4;m++){
            for(int i=0;i<100000000;i++){
                for(int k=0;k<10000000;k++){
                    j--;
                }
            }
        }
        long tiempoNuevo = System.nanoTime();
        System.out.println("Total: " + (tiempoNuevo-tiempoViejo));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Geny
 * Clase que proporciona un método estático para obtener una imágen
 * según su ubicación.
 */
public class Imagen {
    public static BufferedImage cargaImagen(String ruta){
        try {
            return ImageIO.read(Imagen.class.getClassLoader().getResourceAsStream(ruta));
        } catch (IOException ex) {
            return null;
        }
    }
}

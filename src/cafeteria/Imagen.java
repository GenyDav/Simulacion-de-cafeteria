package cafeteria;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase que proporciona un método estático para cargar una imágen
 * según su ubicación en el proyecto.
 * @author Geny D.
 * @version 1.0
 * 
 */
public class Imagen {
    /**
     * @param ruta Dirección de la imágen a cargar
     * @return imágen como objeto BufferedImage
     */
    public static BufferedImage cargaImagen(String ruta){
        try {
            return ImageIO.read(Imagen.class.getClassLoader().getResourceAsStream(ruta));
        } catch (IOException ex) {
            return null;
        }
    }
}

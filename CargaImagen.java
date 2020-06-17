package cafeteria;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Geny
 */
public class CargaImagen {
    public static BufferedImage cargaImagen(String ruta){
        try {
            return ImageIO.read(CargaImagen.class.getClassLoader().getResourceAsStream(ruta));
        } catch (IOException ex) {
            return null;
        }
    }
}

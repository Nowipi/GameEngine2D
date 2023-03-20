package noah.uyttebroeck.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public static BufferedImage getImage(String filename) {
        try {
            InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream != null)
                return ImageIO.read(inputStream);
            throw new RuntimeException("file not found: " + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

}

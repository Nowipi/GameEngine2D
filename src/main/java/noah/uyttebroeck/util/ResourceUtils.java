package noah.uyttebroeck.util;

import noah.uyttebroeck.graphics.Texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class ResourceUtils {

    public static String getFileContent(String file) {
        InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(file);
        if (inputStream != null) {
            StringBuilder content = new StringBuilder();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append('\n');
            }
            return content.toString();
        }
        throw new RuntimeException("file not found: " + file);
    }

    public static Texture getTextureFromFile(String file) {
        try {
            // load image
            InputStream stream =  ResourceUtils.class.getClassLoader().getResourceAsStream(file);
            if (stream == null)
                throw new RuntimeException("file not found: " + file);
            BufferedImage image = ImageIO.read(stream);
            stream.close();
            ByteBuffer data = convertImageData(image);

            Texture texture = new Texture(image.getWidth(), image.getHeight(), GL_RGBA, GL_RGBA, GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_NEAREST);
            // now generate texture
            texture.generate(data);
            return texture;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*https://stackoverflow.com/questions/5194325/how-do-i-load-an-image-for-use-as-an-opengl-texture-with-lwjgl*/
    private static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
                true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        texImage = new BufferedImage(glAlphaColorModel, raster, true,
                new Hashtable<>());

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                .getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

}

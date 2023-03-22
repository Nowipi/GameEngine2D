package noah.uyttebroeck.util;

import noah.uyttebroeck.graphics.Texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

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
        // load image
        int[] width = new int[1];
        int[] height = new int[1];
        int[] nrChannels = new int[1];
        ByteBuffer data = stbi_load("src/main/resources/" + file, width, height, nrChannels, 0);

        Texture texture;
        if (nrChannels[0] > 3) {
            texture = new Texture(width[0], height[0], GL_RGBA, GL_RGBA, GL_REPEAT, GL_REPEAT, GL_LINEAR, GL_LINEAR);
        } else {
            texture = new Texture(width[0], height[0]);
        }
        // now generate texture
        texture.generate(data);
        // and finally free image data
        stbi_image_free(data);
        return texture;
    }

}

package noah.uyttebroeck.util;

import noah.uyttebroeck.graphics.Texture;

import java.io.InputStream;
import java.nio.ByteBuffer;
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
        // load image
        int[] imageWidth = new int[1];
        int[] imageHeight = new int[1];
        int[] nrChannels = new int[1];
        ByteBuffer data = stbi_load("src/main/resources/" + file, imageWidth, imageHeight, nrChannels, 0);

        if (data == null)
            throw new RuntimeException(stbi_failure_reason());

        Texture texture;
        if (nrChannels[0] > 3) {
            texture = new Texture(imageWidth[0], imageHeight[0], GL_RGBA, GL_RGBA, GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_NEAREST);
        } else {
            texture = new Texture(imageWidth[0], imageHeight[0]);
        }
        // now generate texture
        texture.generate(data);
        // and finally free image data
        stbi_image_free(data);
        return texture;
    }

}

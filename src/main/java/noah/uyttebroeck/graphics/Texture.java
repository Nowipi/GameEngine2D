package noah.uyttebroeck.graphics;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private final int id, width, height, internalFormat, imageFormat, wrapS, wrapT, filterMin, filterMax;


    public Texture(int width, int height, int internalFormat, int imageFormat, int wrapS, int wrapT, int filterMin, int filterMax) {
        this.width = width;
        this.height = height;
        this.internalFormat = internalFormat;
        this.imageFormat = imageFormat;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        this.filterMin = filterMin;
        this.filterMax = filterMax;

        id = glGenTextures();
    }

    public Texture(int width, int height) {
        this(width, height, GL_RGB, GL_RGB, GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_NEAREST);
    }

    public void generate(ByteBuffer data) {

        bind();

        // set Texture wrap and filter modes
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filterMin);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filterMax);

        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, imageFormat, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getInternalFormat() {
        return internalFormat;
    }

    public int getImageFormat() {
        return imageFormat;
    }

    public int getWrapS() {
        return wrapS;
    }

    public int getWrapT() {
        return wrapT;
    }

    public int getFilterMin() {
        return filterMin;
    }

    public int getFilterMax() {
        return filterMax;
    }
}

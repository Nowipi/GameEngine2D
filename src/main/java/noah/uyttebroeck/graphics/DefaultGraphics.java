package noah.uyttebroeck.graphics;

import noah.uyttebroeck.Shader;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.util.Vec2;
import noah.uyttebroeck.util.Vec2F;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DefaultGraphics implements Graphics {

    private final Shader shader;
    private final int quadVAO;

    public DefaultGraphics(Shader shader) {
        this.shader = shader;

        int VBO;
        float[] vertices = {
                // pos      // tex
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,

                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f
        };

        quadVAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindVertexArray(quadVAO);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void drawLine(Vec2<Integer> begin, Vec2<Integer> end, int thickness) {

    }

    @Override
    public void drawRect(Vec2<Float> position, Vec2<Float> size) {

        float[] vertices = {
                position.x + size.x, position.y + size.y, // top right
                position.x + size.x, position.y,          // bottom right
                position.x, position.y,                   // bottom left
                position.x, position.y + size.y           // top left
        };
        int[] indices = {  // note that we start from 0!
                0, 1, 3,  // first Triangle
                1, 2, 3   // second Triangle
        };
       meshes.add(new Mesh(vertices, indices));
    }

    @Override
    public void drawSprite(Sprite sprite) {
        shader.bind();
        Matrix4f model = new Matrix4f();
        Vec2F pos = sprite.getParent().getPosition();
        Vec2F size = sprite.getParent().getSize();
        model
                .translate(pos.x, pos.y, 0)
                .translate(0.5F * sprite.getSize().x, 0.5F * sprite.getSize().y, 0)
                .rotate((float) Math.toRadians(sprite.getParent().getRotation()), new Vector3f(0.0f, 0.0f, 1.0f))
                .translate(-0.5F * sprite.getSize().x, -0.5F * sprite.getSize().y, 0)
                .scale(size.x, size.y, 1);

        shader.setMatrix4f("model", model);
        shader.setVector3f("spriteColor", sprite.getColor());

        glActiveTexture(GL_TEXTURE0);
        sprite.getTexture().bind();

        glBindVertexArray(quadVAO);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }

    @Override
    public void drawImage() {

    }
}

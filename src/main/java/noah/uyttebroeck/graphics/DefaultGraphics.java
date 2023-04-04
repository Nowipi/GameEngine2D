package noah.uyttebroeck.graphics;

import noah.uyttebroeck.Shader;
import noah.uyttebroeck.Window;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.util.Vec2;
import noah.uyttebroeck.util.Vec2F;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Stack;

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

    private final Shader spriteShader;
    private final Shader rectShader;

    private final Shader circleShader;
    private final int quadVAO;

    private final Stack<GraphicsObject> objects = new Stack<>();

    public static void main(String[] args) {
        Matrix4f projection = new Matrix4f().ortho(0.0f, 1024, 512, 0.0f, -1.0f, 1.0f);
        projection.invert();
    }

    public DefaultGraphics(Window window) {

        spriteShader = new Shader("shaders/sprite.vert", "shaders/sprite.frag");
        spriteShader.bind();
        Matrix4f projection = new Matrix4f().ortho(0.0f, window.getWidth(), window.getHeight(), 0.0f, -1.0f, 1.0f);
        spriteShader.setMatrix4f("projection", projection);
        spriteShader.setInteger("image", 0);

        rectShader = new Shader("shaders/shape.vert", "shaders/rect.frag");
        rectShader.bind();
        rectShader.setMatrix4f("projection", projection);

        circleShader = new Shader("shaders/sprite.vert", "shaders/circle.frag");
        circleShader.bind();
        circleShader.setMatrix4f("projection", projection);

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

        objects.add(new GraphicsObject(rectShader) {
            @Override
            public void shaderStuff(Shader shader) {
                Matrix4f model = new Matrix4f()
                        .translate(position.x, position.y, 0)
                        .scale(size.x, size.y, 1);

                shader.setMatrix4f("model", model);
                shader.setVector3f("shapeColor", new Vector3f(1,0,0));
            }
        });
    }

    @Override
    public void drawSprite(Sprite sprite) {
        objects.add(new GraphicsObject(spriteShader) {
            @Override
            protected void shaderStuff(Shader shader) {
                Matrix4f model = new Matrix4f();
                Vec2F pos = sprite.getParent().getPosition();
                Vec2F size = sprite.getParent().getSize();
                model
                        .translate(pos.x, pos.y, 0)
                        .translate(0.5F * sprite.getSize().x, 0.5F * sprite.getSize().y, 0)
                        .rotate((float) Math.toRadians(sprite.getParent().getRotation()), new Vector3f(0.0f, 0.0f, 1.0f))
                        .translate(-0.5F * sprite.getSize().x, -0.5F * sprite.getSize().y, 0)
                        .scale(size.x, size.y, 1);

                spriteShader.setMatrix4f("model", model);
                spriteShader.setVector3f("spriteColor", sprite.getColor());

                glActiveTexture(GL_TEXTURE0);
                sprite.getTexture().bind();
            }
        });
    }

    @Override
    public void render() {

        while (!objects.isEmpty()) {
            objects.pop().render();

            glBindVertexArray(quadVAO);
            glDrawArrays(GL_TRIANGLES, 0, 6);
            glBindVertexArray(0);
        }
    }

    @Override
    public void drawCircle(Vec2<Float> position, float radius) {
        objects.add(new GraphicsObject(circleShader) {
            @Override
            public void shaderStuff(Shader shader) {
                Matrix4f model = new Matrix4f()
                        .translate(position.x, position.y, 0)
                        .scale(radius, radius, 1);

                shader.setMatrix4f("model", model);
                shader.setVector3f("shapeColor", new Vector3f(1,0,0));
                spriteShader.setFloat("radius", radius);
            }
        });
    }
}

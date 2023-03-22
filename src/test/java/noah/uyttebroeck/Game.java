package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.DefaultGraphics;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.util.Vec2F;
import org.joml.Matrix4f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Game extends Window {

    private ArrayList<Entity> entities;

    public static Graphics graphics;

    public Game() {
        super("Test window", 1024, 560);
    }

    @Override
    protected void onInit() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        CollisionSolver.initialize(width, height);
        Shader spriteShader = new Shader("shaders/sprite.vert", "shaders/sprite.frag");
        spriteShader.bind();
        Matrix4f projection = new Matrix4f().ortho(0.0f, width, height, 0.0f, -1.0f, 1.0f);
        spriteShader.setMatrix4f("projection", projection);
        spriteShader.setInteger("image", 0);
        Shader shapeShader = new Shader("shaders/shape.vert", "shaders/shape.frag");
        shapeShader.bind();
        shapeShader.setMatrix4f("projection", projection);
        graphics = new DefaultGraphics(spriteShader, shapeShader);

        Player player = new Player(new Vec2F(10, 10));
        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Tile(new Vec2F(200, 10)));
    }

    @Override
    protected void onUpdate(double delta) {

        glClearColor(0, 1.0f, 0, 0.0f);

        for (Entity e : entities) {
            e.update(delta);
        }
        CollisionSolver.getInstance().update();
    }

    @Override
    protected void onRender() {

        for (Entity e : entities) {
            e.render(graphics);
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}

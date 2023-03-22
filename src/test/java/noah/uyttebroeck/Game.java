package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.DefaultGraphics;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.util.Vec2F;
import org.joml.Matrix4f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glClearColor;

public class Game extends Window {

    private ArrayList<Entity> entities;

    public static Graphics graphics;

    public Game() {
        super("Test window", 1024, 560);
    }

    @Override
    protected void onInit() {
        CollisionSolver.initialize(width, height);
        Shader shader = new Shader("shaders/default.vert", "shaders/default.frag");
        shader.bind();
        Matrix4f projection = new Matrix4f().ortho(0.0f, width, height, 0.0f, -1.0f, 1.0f);
        shader.setMatrix4f("projection", projection);
        shader.setInteger("image", 0);
        graphics = new DefaultGraphics(shader);

        Player player = new Player(new Vec2F(10, 10));
        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Tile(new Vec2F(100, 10)));

        //graphics.drawRect(new Vec2F(0, 0), new Vec2F(10, 10));
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

package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.DefaultGraphics;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.graphics.Mesh;
import noah.uyttebroeck.util.Vec2F;

import java.util.ArrayList;

public class Game extends Window {

    private ArrayList<Entity> entities;
    private Shader shader;

    public static final Graphics graphics = new DefaultGraphics();

    public Game() {
        super("Test window", 300, 300);
    }

    @Override
    protected void onInit() {
        CollisionSolver.initialize(width, height);
        Player player = new Player(new Vec2F(10, 10), this);
        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Tile(new Vec2F(100, 10), this));
        //graphics.drawRect(new Vec2F(0, 0), new Vec2F(10, 10));

        shader = new Shader("shaders/default.vert", "shaders/default.frag");
    }

    @Override
    protected void onUpdate() {
        /*for (Entity e : entities) {
            e.update();
        }
        CollisionSolver.getInstance().update();*/
        shader.bind();
        for (Mesh m : graphics.meshes) {
            m.render();
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}

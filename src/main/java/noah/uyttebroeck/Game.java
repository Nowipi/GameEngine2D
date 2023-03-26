package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.DefaultGraphics;
import noah.uyttebroeck.graphics.Graphics;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public abstract class Game extends Window {

    private static Game INSTANCE;

    protected ArrayList<Entity> entities;

    public static Graphics graphics;

    protected Game(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    protected void onInit() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        CollisionSolver.initialize(width, height);
        graphics = new DefaultGraphics(this);
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
        graphics.render();
    }

    public static <G extends Game> void initialize(G game) {
        if (INSTANCE != null)
            throw new RuntimeException("can't initialize a game that's already running");
        INSTANCE = game;
        game.start();
    }

    public static Game getInstance() {
        return INSTANCE;
    }
}

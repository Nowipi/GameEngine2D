package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.DefaultGraphics;
import noah.uyttebroeck.graphics.Graphics;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public abstract class Game extends Window {

    protected ArrayList<Entity> entities;

    public static Graphics graphics;

    public Game() {
        super("Test window", 1024, 560);
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
}

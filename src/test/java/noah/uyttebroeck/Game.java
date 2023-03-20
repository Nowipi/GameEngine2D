package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends Window{


    private ArrayList<Entity> entities;

    @Override
    protected void init() {
        Dimension d = getPreferredSize();
        CollisionSolver.initialize(d.width, d.height);
        Player player = new Player(new Vec2F(10, 10), this);
        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Tile(new Vec2F(100, 10), this));
    }

    @Override
    protected void update() {
        for (Entity e : entities) {
            e.update();
        }
        CollisionSolver.getInstance().update();
    }

    @Override
    protected void draw(Graphics2D g) {
        for (Entity e : entities) {
            e.draw(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Collision");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            Game game = new Game();
            frame.add(game);

            frame.addKeyListener(game);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

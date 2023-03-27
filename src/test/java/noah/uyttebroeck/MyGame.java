package noah.uyttebroeck;

import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.util.Vec2F;

import java.util.ArrayList;
import java.util.Random;

public class MyGame extends Game {

    private final Random random;

    protected MyGame() {
        super("Testing", 1024, 512);
        random = new Random();
    }

    @Override
    protected void onInit() {
        super.onInit();

        entities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entities.add(new Tile(new Vec2F(random.nextInt(width), random.nextInt(height))));
        }

        int borderSize = 10;
        entities.add(new Border(new Vec2F(-borderSize, 0), new Vec2F(borderSize, height))); //top left (left border)
        entities.add(new Border(new Vec2F(width, 0), new Vec2F(borderSize, height))); //top right (right border)
        entities.add(new Border(new Vec2F(0, -borderSize), new Vec2F(width, borderSize))); //top left (top border)
        entities.add(new Border(new Vec2F(0, height), new Vec2F(width, borderSize))); //bottom left (bottom border)
    }

    @Override
    protected void click(int x, int y) {
        Player player = new Player(new Vec2F(x, y));
        if (!player.getCollider().isColliding()) {
            entities.add(player);
        } else {
            //debug rect
            Game.graphics.drawRect(player.getPosition(), player.getSize());
            player.destruct();
        }
    }

    public static void main(String[] args) {
        Game.initialize(new MyGame());
    }
}

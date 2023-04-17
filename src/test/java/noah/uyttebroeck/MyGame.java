package noah.uyttebroeck;

import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;
import org.joml.Vector3i;

import java.util.Random;

public class MyGame extends Game {

    private final Random random;
    private Player player;
    private Ball ball;

    protected MyGame() {
        super("Testing", 2048, 1024);

        random = new Random();
    }

    @Override
    protected void onInit() {
        super.onInit();

        player = new Player(new Vec2F(width/2F-64, height-32));
        entities.add(player);

        ball = new Ball(VectorMath.add(player.getPosition(), new Vec2F(player.getSize().x/2, -8)), new Vec2F(16, 16), new Vector3i(255, 0, 0));
        entities.add(ball);

        keyListener = new MyKeyListener(this);

        for (int i = 0; i < 20; i++) {
            entities.add(new Tile(new Vec2F(i*(64 + 10), 250)));
        }
    }

    public void restart() {

        player.getPhysicsComponent().velocity = new Vec2F();
        player.setPosition(new Vec2F(width/2F-64, height-32));

        ball.reset();
        ball.setPosition(VectorMath.add(player.getPosition(), new Vec2F(player.getSize().x/2, -8)));
    }

    public Player getPlayer() {
        return player;
    }

    public Ball getBall() {
        return ball;
    }

    public static void main(String[] args) {
        Game.initialize(new MyGame());
    }
}

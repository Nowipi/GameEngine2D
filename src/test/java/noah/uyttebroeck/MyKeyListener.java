package noah.uyttebroeck;

import static org.lwjgl.glfw.GLFW.*;

public class MyKeyListener implements KeyListener {

    private MyGame game;
    private boolean A = false;
    private boolean D = false;

    private final Player player;
    private final Ball ball;

    public MyKeyListener(MyGame game) {
        this.game = game;
        player = game.getPlayer();
        ball = game.getBall();
    }


    @Override
    public void keyPressed(int key) {

        if (key == GLFW_KEY_A) {
            A = true;
        }

        if (key == GLFW_KEY_D) {
            D = true;
        }

        if (key == GLFW_KEY_SPACE) {
            if (ball.isStuck()) {
                ball.unStuck();
            }
        }
    }

    @Override
    public void keyReleased(int key) {
        if (key == GLFW_KEY_A) {
            A = false;
        }

        if (key == GLFW_KEY_D) {
            D = false;
        }
    }

    @Override
    public void holdKey() {

        float vel = 0;
        if (A) {
            if (player.getPosition().x >= 0.0f) {
                vel -= 500F;
            }
        }
        if (D) {
            if (player.getPosition().x <= game.width - player.getSize().x) {
                vel += 500F;
            }
        }

        player.getPhysicsComponent().velocity.x = vel;
        if (ball.isStuck()) {
            ball.getPhysicsComponent().velocity.x = vel;
        }

    }
}

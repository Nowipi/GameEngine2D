package noah.uyttebroeck;

import noah.uyttebroeck.util.Vec2F;

import java.util.ArrayList;

public class MyGame extends Game {

    @Override
    protected void onInit() {
        super.onInit();

        entities = new ArrayList<>();
        entities.add(new Tile(new Vec2F(200, 10)));
        entities.add(new Tile(new Vec2F(10, 80)));
        entities.add(new Tile(new Vec2F(600, 120)));
    }

    @Override
    protected void click() {
        entities.add(new Player(new Vec2F(10, 0)));
    }

    public static void main(String[] args) {
        new MyGame();
    }
}

package noah.uyttebroeck;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.util.Vec2F;

public class Test extends Entity {
    public Test(Vec2F position) {
        super(position, new Vec2F(16, 16));
        components.add(new Sprite("textures/player.png", 30, 30, this));
        components.add(new Collider(getSize(), this));
    }

    @Override
    public void onUpdate(double delta) {

    }

    @Override
    public void onRender(Graphics graphics) {

    }
}

package noah.uyttebroeck;

import noah.uyttebroeck.component.CircleCollider;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Ball extends Entity {

    private final CircleCollider collider;

    public Ball(Vec2F position, Vec2F size) {
        super(position, size);
        collider = new CircleCollider(1, this);
        components.add(collider);
    }

    @Override
    public void onUpdate(double delta) {
        Game.graphics.drawCircle(position, collider.getRadius());
    }

    public CircleCollider getCollider() {
        return collider;
    }
}

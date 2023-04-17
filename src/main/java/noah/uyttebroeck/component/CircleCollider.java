package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class CircleCollider extends Collider{

    protected float radius;

    public CircleCollider(float radius, Entity parent) {
        super(new Vec2F(radius*2), parent);
        this.radius = radius;
        init();
    }

    public float getRadius() {
        return radius;
    }
}

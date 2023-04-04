package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;

public class CircleCollider extends Collider{

    protected float radius;

    public CircleCollider(float radius, Entity parent) {
        super(parent);
        this.radius = radius;
        init();
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public float getHalf() {
        return radius;
    }
}

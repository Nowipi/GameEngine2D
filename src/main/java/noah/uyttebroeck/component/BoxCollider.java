package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class BoxCollider extends Collider {

    protected Vec2F size;

    public BoxCollider(Vec2F size, Entity parent) {
        super(parent);
        this.size = size;
        init();
    }

    public Vec2F getSize() {
        return size;
    }

    @Override
    public float getHalf() {
        return size.x;
    }
}

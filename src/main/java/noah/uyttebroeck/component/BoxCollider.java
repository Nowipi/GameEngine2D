package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class BoxCollider extends Collider {

    public BoxCollider(Vec2F size, Entity parent) {
        this(size, true, parent);
    }

    public BoxCollider(Vec2F size, boolean hits, Entity parent) {
        super(size, hits, parent);
        this.size = size;
        init();
    }
}

package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class BoxCollider extends Collider {

    public BoxCollider(Vec2F size, Entity parent) {
        super(size, parent);
        this.size = size;
        init();
    }
}

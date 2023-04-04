package noah.uyttebroeck;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Border extends Entity {
    public Border(Vec2F position, Vec2F size) {
        super(position, size);
        components.add(new BoxCollider(size, this));
    }

    @Override
    public void onUpdate(double delta) {

    }
}

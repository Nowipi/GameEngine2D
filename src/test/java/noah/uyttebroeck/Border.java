package noah.uyttebroeck;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Border extends Entity {
    public Border(Vec2F position, Vec2F size) {
        super(position, size);
        BoxCollider collider = new BoxCollider(size, false, this);
        components.add(collider);
    }

    @Override
    public void onUpdate(double delta) {
        Game.graphics.drawRect(position, size);
    }
}

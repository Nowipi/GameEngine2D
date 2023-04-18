package noah.uyttebroeck;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Tile extends Entity {

    public Tile(Vec2F position) {
        super(position, new Vec2F(64, 64));

        components.add(new Sprite("textures/tile.png", size, this));
        components.add(new BoxCollider(size, false, this));
    }

    @Override
    public void onUpdate(double delta) {

    }
}

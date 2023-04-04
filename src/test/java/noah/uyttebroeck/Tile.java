package noah.uyttebroeck;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Tile extends Entity {

    private final Sprite sprite;
    private final Collider collider;

    public Tile(Vec2F position) {
        super(position, new Vec2F());

        sprite = new Sprite("textures/tile.png", 32, 32, this);
        components.add(sprite);
        setSize(sprite.getSize());
        collider = new BoxCollider(size, this);
        components.add(collider);
    }

    @Override
    public void onUpdate(double delta) {

    }

    public Sprite getSprite() {
        return sprite;
    }
}

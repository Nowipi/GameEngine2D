package noah.uyttebroeck;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

public class Tile extends Entity {

    private final Sprite sprite;
    private final Collider collider;

    public Tile(Vec2F position, Window window) {
        super(position, new Vec2F());

        sprite = new Sprite("textures/tile.png", window, this);
        sprite.setSize(VectorMath.scalarMultiply(sprite.getSize(), 2.5F));
        components.add(sprite);
        setSize(sprite.getSize());
        collider = new Collider(size, this);
        components.add(collider);
    }

    @Override
    public void onUpdate() {

    }

    public Sprite getSprite() {
        return sprite;
    }
}

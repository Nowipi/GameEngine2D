package noah.uyttebroeck;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Physics;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

public class Player extends Entity {

    private final Sprite sprite;
    private final BoxCollider collider;
    private final Physics physicsComponent;


    public Player(Vec2F position) {
        super(position, new Vec2F(128, 32));
        sprite = new Sprite("paddle.png", size, this);
        components.add(sprite);
        setSize(sprite.getSize());

        physicsComponent = new Physics(this);
        components.add(physicsComponent);

        collider = new BoxCollider(size, this);
        components.add(collider);
    }



    @Override
    public void onUpdate(double delta) {

    }

    public Collider getCollider() {
        return collider;
    }

    public Physics getPhysicsComponent() {
        return physicsComponent;
    }
}

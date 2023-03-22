package noah.uyttebroeck;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.collision.OnCollision;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

public class Player extends Entity {

    private final Sprite sprite;
    private final Collider collider;
    private Vec2F velocity = new Vec2F(100, 20);


    public Player(Vec2F position) {
        super(position, new Vec2F());
        sprite = new Sprite("textures/player.png", 64, 64, this);
        components.add(sprite);
        setSize(sprite.getSize());

        collider = new Collider(size, this);


        collider.setOnCollision(new OnCollision() {

            @Override
            public void collisionEntered(Collider other) {
                if (!(other.getParent() instanceof Player))
                    velocity.x *= -1;
            }

            @Override
            public void collisionExited(Collider other) {
            }
        });
        components.add(collider);
    }

    @Override
    public void onUpdate(double delta) {
        position = VectorMath.add(position, VectorMath.scalarMultiply(velocity, (float) delta));

        Game.graphics.drawRect(position, size);
    }


}

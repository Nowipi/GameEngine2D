package noah.uyttebroeck;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.collision.OnCollision;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

import java.awt.*;

public class Player extends Entity {

    private final Sprite sprite;
    private final Collider collider;


    public Player(Vec2F position, Window window) {
        super(position, new Vec2F());
        sprite = new Sprite("player.png", window, this);
        sprite.setSize(VectorMath.scalarMultiply(sprite.getSize(), 2.5F));
        components.add(sprite);
        setSize(sprite.getSize());

        collider = new Collider(size, this);


        collider.setOnCollision(new OnCollision() {

            @Override
            public void collisionEntered(Collider other) {
                System.out.println("entered");
                sprite.setColor(Color.RED);
                if (other.getParent() instanceof Tile t) {
                    t.getSprite().setColor(Color.RED);
                }
            }

            @Override
            public void collisionExited(Collider other) {
                System.out.println("exited");
                sprite.setColor(Color.WHITE);
                if (other.getParent() instanceof Tile t) {
                    t.getSprite().setColor(Color.WHITE);
                }
            }
        });
        components.add(collider);
    }

    @Override
    public void onUpdate() {
        position = new Vec2F(position.x+1, position.y);
    }


}

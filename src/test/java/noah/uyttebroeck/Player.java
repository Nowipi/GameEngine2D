package noah.uyttebroeck;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.collision.OnCollision;
import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;
import org.joml.Vector3f;

import java.awt.*;

public class Player extends Entity {

    private final Sprite sprite;
    private final Collider collider;


    public Player(Vec2F position) {
        super(position, new Vec2F());
        sprite = new Sprite("textures/player.png", this);
        components.add(sprite);
        setSize(sprite.getSize());

        collider = new Collider(size, this);


        collider.setOnCollision(new OnCollision() {

            @Override
            public void collisionEntered(Collider other) {
                System.out.println("entered");
                sprite.setColor(new Vector3f(1,0,0));
                if (other.getParent() instanceof Tile t) {
                    t.getSprite().setColor(new Vector3f(1,0,0));
                }
            }

            @Override
            public void collisionExited(Collider other) {
                System.out.println("exited");
                sprite.setColor(new Vector3f(1,1,1));
                if (other.getParent() instanceof Tile t) {
                    t.getSprite().setColor(new Vector3f(1,1,1));
                }
            }
        });
        components.add(collider);
    }

    @Override
    public void onUpdate(double delta) {

        double inc = 10 * delta;
        position.x += (float)inc;
    }

    @Override
    public void onRender(Graphics graphics) {

    }


}

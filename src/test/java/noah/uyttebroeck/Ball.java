package noah.uyttebroeck;

import noah.uyttebroeck.collision.OnCollision;
import noah.uyttebroeck.component.CircleCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.component.Physics;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;
import org.joml.Vector3i;

public class Ball extends Entity {

    private final Vector3i color;
    private boolean stuck = true;
    private final Physics physicsComponent;
    private final CircleCollider collider;

    private final Vec2F INITIAL_VELOCITY = new Vec2F(100.0F, -350.0F);

    public Ball(Vec2F position, Vec2F size, Vector3i color) {
        super(position, size);
        this.color = color;

        physicsComponent = new Physics(this, false);
        components.add(physicsComponent);
        collider = new CircleCollider(getRadius(), this);
        collider.setOnCollision(new OnCollision() {
            @Override
            public void collisionEntered(Collider other) {

                Vec2F nPosition = VectorMath.normalize(position);
                float xDot = VectorMath.dot(nPosition, new Vec2F(1, 0));
                if (xDot < 0) {
                    physicsComponent.velocity.x = -physicsComponent.velocity.x;
                }
                float yDot = VectorMath.dot(nPosition, new Vec2F(0, -1));
                if (yDot < 0) {
                    physicsComponent.velocity.y = -physicsComponent.velocity.y;
                }

                if (other.getParent() instanceof Tile tile) {
                    tile.destruct();
                } else if (other.getParent() instanceof Player player) {
                    xDot = VectorMath.dot(nPosition, VectorMath.normalize(player.getPhysicsComponent().velocity));
                    if (xDot < 0) {
                        physicsComponent.velocity.x = -physicsComponent.velocity.x;
                    }
                }

            }

            @Override
            public void collisionExited(Collider other) {

            }
        });
        components.add(collider);
    }

    private void move(double dt) {
        // if not stuck to player board
        if (!stuck) {

            // move the ball
            physicsComponent.update(dt);

            if (position.y > MyGame.getInstance().height) {
                ((MyGame)MyGame.getInstance()).restart();
            }

        } else {
            physicsComponent.update(dt);
        }
    }

    @Override
    public void onUpdate(double delta) {


        move(delta);

        Game.graphics.drawCircle(collider.getPosition(), collider.getSize(), color.x, color.y, color.z);
    }

    public void unStuck() {
        stuck = false;
        physicsComponent.velocity = new Vec2F(INITIAL_VELOCITY);
    }

    public void reset() {
        stuck = true;
        physicsComponent.velocity = new Vec2F();
    }

    public float getRadius() {
        return size.x/2;
    }

    public boolean isStuck() {
        return stuck;
    }

    public Physics getPhysicsComponent() {
        return physicsComponent;
    }
}

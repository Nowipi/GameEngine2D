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
            public void collisionEntered(Collider.HitResult hitResult) {

                if (!stuck) {

                    Vec2F correction = VectorMath.sub(hitResult.hit(), Ball.this.position);
                    correction = VectorMath.sub(correction, 0.1F);
                    Ball.this.position = VectorMath.add(Ball.this.position, correction);

                    if (hitResult.other().getParent() instanceof Tile t)
                        t.destruct();

                    float dot = VectorMath.dot(hitResult.hitNormal(), physicsComponent.velocity);
                    physicsComponent.velocity = VectorMath.sub(physicsComponent.velocity, VectorMath.scalarMultiply(hitResult.hitNormal(), 2 * dot));

                }

            }

            @Override
            public void collisionExited(Collider.HitResult hitResult) {

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

package noah.uyttebroeck.component;

import noah.uyttebroeck.Game;
import noah.uyttebroeck.collision.CollisionSolver;
import noah.uyttebroeck.collision.OnCollision;
import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;

import java.util.ArrayList;
import java.util.List;

public abstract class Collider extends Component {

    protected final boolean hits;
    protected boolean colliding = false;

    protected Vec2F size;

    protected OnCollision onCollision = new OnCollision() {
        @Override
        public void collisionEntered(HitResult result) {

        }

        @Override
        public void collisionExited(HitResult result) {

        }
    };

    public Collider(Vec2F size, boolean hits, Entity parent) {
        super(new ComponentBuilder(parent));
        this.size = size;
        this.hits = hits;
    }

    protected void init() {
        CollisionSolver.getInstance().addCollider(this);
    }

    public final void setOnCollision(OnCollision onCollision) {
        this.onCollision = onCollision;
    }

    private List<HitResult> lastCollided = new ArrayList<>();
    public final void hit(List<HitResult> collided) {
        if (hits) {
            for (HitResult r : collided) {
                if (!lastCollided.contains(r)) {
                    if (Game.getInstance() == null || Game.getInstance().isSpawned(r.other.parent)) {
                        onCollision.collisionEntered(r);
                    }
                } else {
                    lastCollided.remove(r);
                }
            }

            for (int i = 0; i < lastCollided.size(); i++) {
                HitResult r = lastCollided.get(i);
                if (Game.getInstance().isSpawned(r.other.parent)) {
                    onCollision.collisionExited(r);
                }
                lastCollided.remove(r);
                i--;
            }

            colliding = collided.size() > 0;

            lastCollided = collided;
        }
    }

    public record HitResult(Collider other, Vec2F hitNormal, Vec2F hit) {

    }

    public final Vec2F getPosition() {
        return parent.getPosition();
    }

    public Vec2F getSize() {
        return size;
    }

    public boolean isColliding() {
        return colliding;
    }

    @Override
    public void destruct() {
        CollisionSolver.getInstance().removeCollider(this);
    }
}

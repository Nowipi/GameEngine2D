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
        public void collisionEntered(Collider other) {

        }

        @Override
        public void collisionExited(Collider other) {

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

    private List<Collider> lastCollided = new ArrayList<>();
    public final void collision(List<Collider> collided) {
        if (hits) {
            System.out.println(parent);
            for (Collider c : collided) {
                System.out.println(c.getParent());
                if (!lastCollided.contains(c)) {
                    if (Game.getInstance().isSpawned(c.parent))
                        onCollision.collisionEntered(c);
                } else {
                    lastCollided.remove(c);
                }
            }

            for (int i = 0; i < lastCollided.size(); i++) {
                Collider c = lastCollided.get(i);
                if (Game.getInstance().isSpawned(c.parent))
                    onCollision.collisionExited(c);
                lastCollided.remove(c);
                i--;
            }

            colliding = collided.size() > 0;

            lastCollided = collided;
        }
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

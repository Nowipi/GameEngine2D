package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.CircleCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

import java.util.ArrayList;

public class CollisionSolver {


    private static CollisionSolver INSTANCE;

    public static CollisionSolver getInstance() {
        return INSTANCE;
    }

    public static void initialize(int width, int height) {
        INSTANCE = new CollisionSolver(width, height);
    }

    private QuadTree<Collider> quadTree;
    private final ArrayList<Collider> colliders = new ArrayList<>();

    private CollisionSolver(int width, int height) {
        quadTree = new ColliderQuadTree(new Rectangle(new Vec2F(0,0), new Vec2F(width, height)));
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
        quadTree.insert(collider);
        update();
    }

    public void update() {

        quadTree = new ColliderQuadTree(quadTree.boundary);

        for (Collider pc : colliders) {
            quadTree.insert(pc);
        }


        for (Collider pc : colliders) {

            float add = 50;
            Vec2F additional = new Vec2F(add);
            Rectangle range = new Rectangle(
                    VectorMath.sub(pc.getPosition(), additional),
                    new Vec2F(pc.getHalf() + additional.x, pc.getHalf() + additional.y));

            ArrayList<Collider> collided = new ArrayList<>();
            ArrayList<Collider> results = quadTree.query(range);
            for (Collider c : results) {
                if (pc != c && collides(pc, c)) {
                    collided.add(c);
                }
            }

            pc.collision(collided);

        }
    }

    private boolean collides(Collider pc, Collider c) {
        if (pc instanceof CircleCollider pcc) {
            if (c instanceof CircleCollider cc) {
                return collides(pcc, cc);
            } else {
                return collides(pcc, (BoxCollider) c);
            }
        } else {
            if (c instanceof CircleCollider cc) {
                return collides(cc, (BoxCollider) pc);
            } else {
                return collides((BoxCollider) pc, (BoxCollider) c);
            }
        }
    }

    private boolean collides(CircleCollider c, BoxCollider b) {
        // temporary variables to set edges for testing
        Vec2F test = c.getPosition();

        // which edge is closest?
        if (c.getPosition().x < b.getPosition().x)                      test.x = b.getPosition().x;                 // test left edge
        else if (c.getPosition().x > b.getPosition().x + b.getSize().x) test.x = b.getPosition().x + b.getSize().x; // right edge
        if (c.getPosition().y < b.getPosition().y)                      test.y = b.getPosition().y;                 // top edge
        else if (c.getPosition().y > b.getPosition().y + b.getSize().y) test.y = b.getPosition().y + b.getSize().y; // bottom edge

        // get distance from the closest edges
        float distance = VectorMath.distance(VectorMath.sub(c.getPosition(), test));

        return distance <= c.getRadius();
    }

    private boolean collides(CircleCollider pc, CircleCollider c) {
        Vec2F dPos = VectorMath.sub(pc.getPosition(), c.getPosition());
        return VectorMath.distance(dPos) <= pc.getRadius() + c.getRadius();
    }

    private boolean collides(BoxCollider pc, BoxCollider c) {
        float r1x = pc.getPosition().x;
        float r1y = pc.getPosition().y;
        float r1w = pc.getSize().x;
        float r1h = pc.getSize().y;
        float r2x = c.getPosition().x;
        float r2y = c.getPosition().y;
        float r2w = c.getSize().x;
        float r2h = c.getSize().y;

        return r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h;

    }


    public void removeCollider(Collider collider) {
        colliders.remove(collider);
        update();
    }
}

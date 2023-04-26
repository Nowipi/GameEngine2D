package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.CircleCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.entity.Entity;
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

    private ColliderQuadTree quadTree;
    private final ArrayList<Collider> colliders = new ArrayList<>();

    private CollisionSolver(int width, int height) {
        quadTree = new ColliderQuadTree(new Rectangle(new Vec2F(-1000,-1000), new Vec2F(width+2000, height+2000)), 10);
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
        if (collider instanceof BoxCollider b)
            quadTree.insert(b);
        else if (collider instanceof CircleCollider c)
            quadTree.insert(c);
        update();
    }

    public void update() {

        quadTree = new ColliderQuadTree(quadTree.boundary, 10);

        for (Collider pc : colliders) {
            if (pc instanceof BoxCollider b)
                quadTree.insert(b);
            else if (pc instanceof CircleCollider c)
                quadTree.insert(c);
        }

        for (int i = 0; i < colliders.size(); i++) {

            Collider pc = colliders.get(i);

            float add = quadTree.getCellSize();
            Vec2F additional = new Vec2F(add);
            Rectangle range = new Rectangle(
                    VectorMath.sub(pc.getPosition(), additional),
                    new Vec2F(pc.getSize().x/2 + additional.x, pc.getSize().y/2 + additional.y));

            ArrayList<Collider.HitResult> hits = new ArrayList<>();
            ArrayList<Collider> results = quadTree.query(range);
            for (Collider c : results) {
                Collision collision = isColliding(pc, c);
                if (pc != c && collision.collision) {
                    hits.add(new Collider.HitResult(c, collision.normal, collision.hit));
                }
            }

            int size = colliders.size();
            pc.hit(hits);
            if (size > colliders.size())
                i--;
        }
    }

    private Collision isColliding(Collider pc, Collider c) {
        if (pc instanceof CircleCollider pcc) {
            if (c instanceof CircleCollider cc) {
                return isColliding(pcc, cc);
            } else {
                return isColliding(pcc, (BoxCollider) c);
            }
        } else {
            if (c instanceof CircleCollider cc) {
                return isColliding(cc, (BoxCollider) pc);
            } else {
                return isColliding((BoxCollider) pc, (BoxCollider) c);
            }
        }
    }

    private Collision isColliding(CircleCollider c, BoxCollider b) {
        // temporary variables to set edges for testing
        Vec2F hitEdge = new Vec2F(c.getPosition());

        Vec2F normal = new Vec2F();
        // which edge is closest?
        if (c.getPosition().x < b.getPosition().x) {
            hitEdge.x = b.getPosition().x;                 // test left edge
            normal.x = -1F;
        } else if (c.getPosition().x > b.getPosition().x + b.getSize().x) {
            hitEdge.x = b.getPosition().x + b.getSize().x; // right edge
            normal.x = 1F;
        }
        if (c.getPosition().y < b.getPosition().y) {
            hitEdge.y = b.getPosition().y;                 // top edge
            if (normal.x == 0)
                normal.y = -1F;
        } else if (c.getPosition().y > b.getPosition().y + b.getSize().y) {
            hitEdge.y = b.getPosition().y + b.getSize().y; // bottom edge
            if (normal.x == 0)
                normal.y = -1F;
        }

        // get distance from the closest edges
        float distance = VectorMath.distance(VectorMath.sub(c.getPosition(), hitEdge));

        return new Collision(distance <= c.getRadius(), normal, hitEdge);
    }

    public static void main(String[] args) {

        CollisionSolver.initialize(1024, 512);
        CollisionSolver.getInstance().isColliding(new CircleCollider(3, new Entity(new Vec2F(19, 22.5F), new Vec2F(3, 3)) {
            @Override
            public void onUpdate(double delta) {

            }
        }), new BoxCollider(new Vec2F(8, 5), new Entity(new Vec2F(new Vec2F(10, 20)), new Vec2F(8, 5)) {
            @Override
            public void onUpdate(double delta) {

            }
        }));
    }

    private Collision isColliding(CircleCollider pc, CircleCollider c) {
        Vec2F dPos = VectorMath.sub(pc.getPosition(), c.getPosition());
        //Vec2F hit = VectorMath.add(VectorMath.negate(dPos), pc.getRadius() + c.getRadius()); //overlap vector
        return new Collision(VectorMath.distance(dPos) <= pc.getRadius() + c.getRadius(), null, null);
    }

    private record Collision(boolean collision, Vec2F normal, Vec2F hit) {}

    private Collision isColliding(BoxCollider pc, BoxCollider c) {
        float r1x = pc.getPosition().x;
        float r1y = pc.getPosition().y;
        float r1w = pc.getSize().x;
        float r1h = pc.getSize().y;
        float r2x = c.getPosition().x;
        float r2y = c.getPosition().y;
        float r2w = c.getSize().x;
        float r2h = c.getSize().y;

        return new Collision(r1x + r1w >= r2x &&
                r1x <= r2x + r2w &&
                r1y + r1h >= r2y &&
                r1y <= r2y + r2h, null, null);

    }


    public void removeCollider(Collider collider) {
        colliders.remove(collider);
        update();
    }
}

package noah.uyttebroeck.collision;

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

    private final QuadTree<Collider> quadTree;

    private CollisionSolver(int width, int height) {
        quadTree = new ColliderQuadTree(new Rectangle(new Vec2F(0,0), new Vec2F(width, height)));
    }

    public void addCollider(Collider collider) {
        quadTree.insert(collider);
    }

    public void update() {
        for (Collider pc : quadTree.elements) {


            float add = 50;
            Vec2F additional = new Vec2F(add);
            Rectangle range = new Rectangle(
                    VectorMath.clampMin(VectorMath.sub(pc.getPosition(), additional), new Vec2F(0,0)),
                    VectorMath.add(pc.getSize(), additional));

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


}

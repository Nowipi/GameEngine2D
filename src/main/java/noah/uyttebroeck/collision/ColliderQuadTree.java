 package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

 public class ColliderQuadTree extends QuadTree<Collider> {

    public ColliderQuadTree(Rectangle boundary) {
        super(boundary);
    }

    @Override
    protected Vec2F[] getPoints(Collider collider) {
        if (collider instanceof BoxCollider) {
            return new Vec2F[] {
                    collider.getPosition(),
                    new Vec2F(collider.getPosition().x + collider.getSize().x, collider.getPosition().y),
                    VectorMath.add(collider.getPosition(), collider.getSize()),
                    new Vec2F(collider.getPosition().x, collider.getPosition().y + collider.getSize().y)
            };
        } else {
            return new Vec2F[]{collider.getPosition()};
        }
    }

}

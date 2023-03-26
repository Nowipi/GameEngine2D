package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;

public class ColliderQuadTree extends QuadTree<Collider> {
    public ColliderQuadTree(Rectangle boundary) {
        super(boundary);
    }

    @Override
    public Vec2F getPosition(Collider element) {
        return element.getPosition();
    }

    @Override
    public Vec2F getSize(Collider element) {
        return element.getSize();
    }


}

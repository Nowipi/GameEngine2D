package noah.uyttebroeck;

import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

public class RectQuadTree extends QuadTree<Rectangle> {
    public RectQuadTree(Rectangle boundary) {
        super(boundary);
    }

    @Override
    protected Vec2F[] getPoints(Rectangle rectangle) {
        return new Vec2F[] {
                rectangle.position,
                new Vec2F(rectangle.position.x + rectangle.size.x, rectangle.position.y),
                VectorMath.add(rectangle.position, rectangle.size),
                new Vec2F(rectangle.position.x, rectangle.position.y + rectangle.size.y)
        };
    }
}

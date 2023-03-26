package noah.uyttebroeck;

import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;

public class RectQuadTree extends QuadTree<Rectangle> {
    public RectQuadTree(Rectangle boundary) {
        super(boundary);
    }

    @Override
    public Vec2F getPosition(Rectangle element) {
        return element.position;
    }

    @Override
    public Vec2F getSize(Rectangle element) {
        return element.size;
    }
}

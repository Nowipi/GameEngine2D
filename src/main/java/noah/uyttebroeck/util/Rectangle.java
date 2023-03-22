package noah.uyttebroeck.util;

public class Rectangle {

    public final Vec2F position;
    public final Vec2F size;

    public final double top;
    public final double bottom;
    public final double left;
    public final double right;

    public Rectangle(Vec2F position, Vec2F size) {
        this.position = position;
        this.size = size;
        left    = this.position.x;
        right   = this.position.x + this.size.x;
        top     = this.position.y;
        bottom  = this.position.y + this.size.y;
    }

    public boolean contains(Vec2F point) {

        return point.x >= left && point.x <= right &&
                point.y >= top && point.y <= bottom;
    }

    public boolean intersects(Rectangle range) {
        return right >= range.left && left <= range.right &&
                bottom >= range.top && top <= range.bottom;
    }

    public Rectangle subdivide(String quadrant) {
        Vec2F divided = VectorMath.scalarDivide(size, 4);
        Vec2F half = VectorMath.scalarDivide(size, 2);
        Vec2F added = VectorMath.add(position, divided);
        Vec2F subtracted = VectorMath.sub(position, divided);
        return switch (quadrant) {
            case "ne" -> new Rectangle(new Vec2F(added.x, subtracted.y), half);
            case "nw" -> new Rectangle(subtracted, half);
            case "se" -> new Rectangle(added, half);
            case "sw" -> new Rectangle(new Vec2F(subtracted.x, added.y), half);
            default -> null;
        };
    }
}

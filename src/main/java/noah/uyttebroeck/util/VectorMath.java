package noah.uyttebroeck.util;

public class VectorMath {

    public static Vec2F add(Vec2F a, Vec2F b) {
        return new Vec2F(a.x + b.x, a.y + b.y);
    }

    public static Vec2F sub(Vec2F a, Vec2F b) {
        return new Vec2F(a.x - b.x, a.y - b.y);
    }

    public static Vec2F scalarMultiply(Vec2F v, float s) {
        return new Vec2F(v.x * s, v.y * s);
    }

    public static Vec2F scalarDivide(Vec2F v, float s) {
        return new Vec2F(v.x / s, v.y / s);
    }

    public static Vec2F clamp(Vec2F vec, Vec2F min, Vec2F max) {

        Vec2F newVec = clampMin(vec, min);

        if (vec.x > max.x) {
            newVec.x = max.x;
        }
        if (vec.y > max.y) {
            newVec.y = max.y;
        }
        return newVec;
    }

    public static Vec2F clampMin(Vec2F vec, Vec2F min) {
        Vec2F newVec = new Vec2F(vec.x, vec.y);
        if (vec.x < min.x) {
            newVec.x = min.x;
        }
        if (vec.y < min.y) {
            newVec.y = min.y;
        }
        return newVec;
    }

}

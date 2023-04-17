package noah.uyttebroeck.util;

import org.joml.Math;

public class VectorMath {

    public static Vec2F add(Vec2F a, Vec2F b) {
        return new Vec2F(a.x + b.x, a.y + b.y);
    }

    public static Vec2F add(Vec2F a, float b) {
        return new Vec2F(a.x + b, a.y + b);
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

    public static float distance(Vec2F v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float dot(Vec2F v1, Vec2F v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public static Vec2F cross(Vec2F v1, Vec2F v2) {
        return new Vec2F(v1.y - v2.y, v1.x - v2.x);
    }

    public static Vec2F negate(Vec2F v) {
        return new Vec2F(-v.x, -v.y);
    }

    public static Vec2F normalize(Vec2F v) {
        float length = distance(v);
        return new Vec2F(v.x/length, v.y/length);
    }
}

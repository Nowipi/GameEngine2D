package noah.uyttebroeck.util;

public class Vec2 <T>{

    public T x;
    public T y;

    public Vec2(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

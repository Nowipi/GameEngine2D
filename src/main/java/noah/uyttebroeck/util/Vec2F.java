package noah.uyttebroeck.util;

public class Vec2F extends Vec2<Float> {
    public Vec2F(float x, float y) {
        super(x, y);
    }

    public Vec2F(float v) {
        super(v, v);
    }

    public Vec2F(Vec2F v) {
        super(v.x, v.y);
    }

    public Vec2F() {
        this(0,0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vec2F v))
            return false;
        return x.floatValue() == v.x.floatValue() && y.floatValue() == v.y.floatValue();
    }
}

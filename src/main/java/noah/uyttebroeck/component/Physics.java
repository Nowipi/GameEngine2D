package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

public class Physics extends Component {

    public Vec2F velocity;

    public Physics(Entity parent) {
        this(parent, true);
    }


    public Physics(Entity parent, boolean ticks) {
        super(new ComponentBuilder(parent).ticks(ticks));
        velocity = new Vec2F();
    }

    @Override
    public void update(double delta) {
        parent.setPosition(VectorMath.add(parent.getPosition(), VectorMath.scalarMultiply(velocity, (float) delta)));
    }

    @Override
    public void destruct() {

    }
}

package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.Graphics;

import java.awt.*;

public class Component {

    private final boolean ticks;
    protected final Entity parent;

    protected Component(ComponentBuilder builder) {
        ticks = builder.ticks;
        parent = builder.parent;
    }

    public void update(double delta){}

    public final boolean ticks() {
        return ticks;
    }

    static final class ComponentBuilder {

        private boolean ticks = false;
        private final Entity parent;
        public ComponentBuilder(Entity parent) {
            this.parent = parent;
        }

        public ComponentBuilder ticks(boolean ticks) {
            this.ticks = ticks;
            return this;
        }

        public Component build() {
            return new Component(this);
        }
    }

    public final Entity getParent() {
        return parent;
    }
}

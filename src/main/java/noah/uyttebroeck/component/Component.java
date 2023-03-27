package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;

public abstract class Component {

    private final boolean ticks;
    protected final Entity parent;

    protected Component(ComponentBuilder builder) {
        ticks = builder.ticks;
        parent = builder.parent;
    }

    public void update(double delta){}

    public abstract void destruct();

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
    }

    public final Entity getParent() {
        return parent;
    }
}

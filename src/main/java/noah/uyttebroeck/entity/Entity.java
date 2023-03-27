package noah.uyttebroeck.entity;

import noah.uyttebroeck.component.Component;
import noah.uyttebroeck.util.Vec2F;

import java.util.ArrayList;

public abstract class Entity {


    protected final ArrayList<Component> components = new ArrayList<>();
    protected Vec2F position;
    protected Vec2F size;
    protected float rotation;
    private boolean destructed = false;

    public Entity(Vec2F position, Vec2F size) {
        this.position = position;
        this.size = size;
    }

    public final void update(double delta) {

        for (Component component : components) {
            if (component.ticks()) {
                component.update(delta);
            }
        }

        onUpdate(delta);
    }

    public void destruct() {
        destructed = true;
        for (Component c : components) {
            c.destruct();
        }
    }

    public abstract void onUpdate(double delta);

    public final Vec2F getPosition() {
        return position;
    }

    public final void setPosition(Vec2F position) {
        this.position = position;
    }

    public final Vec2F getSize() {
        return size;
    }

    public final void setSize(Vec2F size) {
        this.size = size;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean isDestructed() {
        return destructed;
    }
}

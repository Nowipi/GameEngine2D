package noah.uyttebroeck.entity;

import noah.uyttebroeck.component.Component;
import noah.uyttebroeck.util.Vec2F;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {


    protected final ArrayList<Component> components = new ArrayList<>();
    protected Vec2F position;
    protected Vec2F size;

    public Entity(Vec2F position, Vec2F size) {
        this.position = position;
        this.size = size;
    }

    public final void update() {

        for (Component component : components) {
            if (component.ticks()) {
                component.update();
            }
        }

        onUpdate();
    }

    public abstract void onUpdate();


    public void draw(Graphics2D g) {
        for (Component c : components) {
                c.draw(g);
        }
    }

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
}

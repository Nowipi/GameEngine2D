package noah.uyttebroeck.entity;

import noah.uyttebroeck.component.Component;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.util.Vec2F;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {


    protected final ArrayList<Component> components = new ArrayList<>();
    protected Vec2F position;
    protected Vec2F size;
    protected float rotation;

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

    public final void render(Graphics graphics) {

        for (Component component : components) {
            component.render(graphics);
        }

        onRender(graphics);
    }

    public abstract void onUpdate(double delta);
    public abstract void onRender(Graphics graphics);

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
}

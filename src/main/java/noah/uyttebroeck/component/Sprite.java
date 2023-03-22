package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.graphics.Graphics;
import noah.uyttebroeck.graphics.Texture;
import noah.uyttebroeck.util.ResourceUtils;
import noah.uyttebroeck.util.Vec2F;
import org.joml.Vector3f;

public class Sprite extends Component {

    private final Texture texture;

    private Vector3f color = new Vector3f(1, 1, 1);
    private final int width;
    private final int height;

    public Sprite(String imageName, int width, int height, Entity parent) {
        super(new ComponentBuilder(parent).ticks(true));
        this.width = width;
        this.height = height;
        texture = ResourceUtils.getTextureFromFile(imageName);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawSprite(this);
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vec2F getSize() {
        return new Vec2F(width, height);
    }

    public Texture getTexture() {
        return texture;
    }
}

package noah.uyttebroeck.component;

import noah.uyttebroeck.entity.Entity;
import noah.uyttebroeck.Window;
import noah.uyttebroeck.util.ResourceUtils;
import noah.uyttebroeck.util.Vec2F;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends Component {

    private BufferedImage image;

    private final noah.uyttebroeck.Window window;

    private Color color = Color.WHITE;

    public Sprite(String imageName, Window window, Entity parent) {
        super(new ComponentBuilder(parent).ticks(true));
        this.window = window;
        this.image = ResourceUtils.getImage(imageName);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.PINK);
        g.drawRect(parent.getPosition().x.intValue(), parent.getPosition().y.intValue(), 4, 4);
        g.setColor(color);
        g.drawRect(parent.getPosition().x.intValue(), parent.getPosition().y.intValue(), parent.getSize().x.intValue(), parent.getSize().y.intValue());
        g.drawImage(image, parent.getPosition().x.intValue(), parent.getPosition().y.intValue(), window);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Vec2F getSize() {
        return new Vec2F(image.getWidth(), image.getHeight());
    }

    public void setSize(Vec2F size) {
        image = ResourceUtils.imageToBufferedImage(
                image.getScaledInstance(size.x.intValue(), size.y.intValue(), Image.SCALE_DEFAULT)
        );
    }
}

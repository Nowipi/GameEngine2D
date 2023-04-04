package noah.uyttebroeck.graphics;

import noah.uyttebroeck.component.Sprite;
import noah.uyttebroeck.util.Vec2;

public interface Graphics {

    void drawLine(Vec2<Integer> begin, Vec2<Integer> end, int thickness);

    void drawRect(Vec2<Float> position, Vec2<Float> size);

    void drawSprite(Sprite sprite);

    void render();

    void drawCircle(Vec2<Float> position, float radius);
}

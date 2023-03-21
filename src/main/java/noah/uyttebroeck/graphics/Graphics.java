package noah.uyttebroeck.graphics;

import noah.uyttebroeck.util.Vec2;

import java.util.ArrayList;

public interface Graphics {

    ArrayList<Mesh> meshes = new ArrayList<>();

    void drawLine(Vec2<Integer> begin, Vec2<Integer> end, int thickness);

    void drawRect(Vec2<Float> position, Vec2<Float> size);

    void drawImage();

}

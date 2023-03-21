package noah.uyttebroeck.graphics;

import noah.uyttebroeck.util.Vec2;

public class DefaultGraphics implements Graphics {


    @Override
    public void drawLine(Vec2<Integer> begin, Vec2<Integer> end, int thickness) {

    }

    @Override
    public void drawRect(Vec2<Float> position, Vec2<Float> size) {

        float[] vertices = {
                position.x + size.x, position.y + size.y, // top right
                position.x + size.x, position.y,          // bottom right
                position.x, position.y,                   // bottom left
                position.x, position.y + size.y           // top left
        };
        int[] indices = {  // note that we start from 0!
                0, 1, 3,  // first Triangle
                1, 2, 3   // second Triangle
        };
       meshes.add(new Mesh(vertices, indices));
    }

    @Override
    public void drawImage() {

    }
}

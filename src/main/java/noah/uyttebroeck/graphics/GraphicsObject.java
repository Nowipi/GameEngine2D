package noah.uyttebroeck.graphics;

import noah.uyttebroeck.Shader;

public abstract class GraphicsObject {

    private Shader shader;

    public GraphicsObject(Shader shader) {
        this.shader = shader;
    }

    protected abstract void shaderStuff(Shader shader);

    public void render() {
        shader.bind();
        shaderStuff(shader);
    }

}

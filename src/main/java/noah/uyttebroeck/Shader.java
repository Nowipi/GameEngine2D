package noah.uyttebroeck;

import noah.uyttebroeck.util.ResourceUtils;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int program;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        String vert = ResourceUtils.getFileContent(vertexShaderFile);
        String frag = ResourceUtils.getFileContent(fragmentShaderFile);

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vert);
        glCompileShader(vertexShader);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, frag);
        glCompileShader(fragmentShader);
        program = glCreateProgram();
    }

    public void bind() {
        glUseProgram(program);
    }
}

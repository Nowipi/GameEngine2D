package noah.uyttebroeck;

import noah.uyttebroeck.util.ResourceUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Shader {

    private final int program;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        String vert = ResourceUtils.getFileContent(vertexShaderFile);
        String frag = ResourceUtils.getFileContent(fragmentShaderFile);

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vert);
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("ERROR::SHADER::VERTEX::COMPILATION_FAILED" + glGetShaderInfoLog(vertexShader));
        }

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, frag);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED" + glGetShaderInfoLog(fragmentShader));
        }

        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("ERROR::SHADER::PROGRAM::LINKING_FAILED" + glGetProgramInfoLog(program));
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void bind() {
        glUseProgram(program);
    }

    public void setMatrix4f(String name, Matrix4f mat) {
        glUniformMatrix4fv(glGetUniformLocation(program, name), false, mat.get(new float[4*4]));
    }

    public void setVector3f(String name, Vector3f vec) {
        glUniform3f(glGetUniformLocation(program, name), vec.x, vec.y, vec.z);
    }

    public void setInteger(String name, int i) {
        glUniform1i(glGetUniformLocation(program, name), i);
    }
}

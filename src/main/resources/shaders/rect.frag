#version 330 core

uniform vec3 shapeColor;

void main() {
    gl_FragColor = vec4(shapeColor, 1.0);
}
#version 330 core

in vec2 TexCoords;

uniform vec3 shapeColor;
uniform float radius;

float circle(float radius, vec2 position) {
    return step(radius, distance(position, vec2(0.5)));
}

void main() {

    float circle = circle(radius, TexCoords);
    if(circle == 0.) {
        gl_FragColor = vec4(shapeColor, 1.0);
    }
}
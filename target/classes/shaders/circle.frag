#version 330 core

in vec2 TexCoords;

uniform vec3 shapeColor;

float circle(in vec2 _st, in float _radius){
    vec2 dist = _st-vec2(0.5);
    return 1.-smoothstep(_radius-(_radius*0.01),
    _radius+(_radius*0.01),
    dot(dist,dist)*4.0);
}

void main() {

    float circle = circle(TexCoords, 1.0);
    gl_FragColor = vec4(shapeColor, circle);
}
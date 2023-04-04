#version 330 core
in vec2 TexCoords;

uniform sampler2D image;
uniform vec3 spriteColor;

void main()
{
    vec4 texColor = texture(image, TexCoords);
    if(texColor.a < 0.1)
            discard;
    gl_FragColor = vec4(spriteColor, 1.0) * texColor;
}
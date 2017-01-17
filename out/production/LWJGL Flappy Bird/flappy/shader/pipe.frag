#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
    vec3 position;
} fs_in;

uniform vec2 bird;

uniform sampler2D tex;
uniform int top;

void main() {
    vec2 texCoords = fs_in.tc;
    if(top == 1) {
        texCoords = 1.0 - texCoords;

        color = texture(tex, texCoords);
        //color = mix(color, vec4(0.5, 1, 0.5, 1), 0.7);

    } else {

        color = texture(tex, fs_in.tc);

    }

    if(color.w < 1.0) discard;

    color *= 2.0 / (length(bird - fs_in.position.xy) + 1.5) + 0.5;
    color.w = 1;
    }
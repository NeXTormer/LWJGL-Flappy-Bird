#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int top;

void main() {

    if(top == 1) {
        fs_in.tc.y = 1.0 - fs_in.tc.y;
    }

    color = texture(tex, fs_in.tc);
    if(color.w < 1.0) discard;

}
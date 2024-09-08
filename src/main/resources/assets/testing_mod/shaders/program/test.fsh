#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

void main() {

    vec4 color = texture2D(DiffuseSampler, texCoord);

    gl_FragColor = vec4(0,1,0,(color.x + color.y + color.z) / 3);
}

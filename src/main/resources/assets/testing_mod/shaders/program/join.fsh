#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D MainSampler;

varying vec2 texCoord;

void main() {


    gl_FragColor = texture2D(DiffuseSampler, texCoord) + texture2D(MainSampler,texCoord);
}

#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthBuffer;
uniform sampler2D LaserDepth;

varying vec2 texCoord;

void main() {

    vec4 color = texture2D(DiffuseSampler, texCoord);

    float a = texture2D(LaserDepth, texCoord).r + texture2D(LaserDepth, texCoord).g + texture2D(LaserDepth, texCoord).b;
    float b = texture2D(DepthBuffer, texCoord).r + texture2D(DepthBuffer, texCoord).g + texture2D(DepthBuffer, texCoord).b;

    if(a > b)
    {
        color = vec4(0,0,0,1);
    }

    gl_FragColor = texture2D(DiffuseSampler, texCoord);
}

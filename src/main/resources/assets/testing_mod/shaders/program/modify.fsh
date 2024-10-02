#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

varying vec2 texCoord;

void main() {

    vec4 color = texture2D(DiffuseSampler, texCoord);

    color.w += (color.x + color.y + color.z);

    if((color.x + color.y + color.z) <= 5)
    {
        color.w = (color.x + color.y + color.z);
    }

    if(texture2D(DepthSampler, texCoord).r < 1)
    {
     //   color = vec4(0,0,0,0);
    }

    gl_FragColor = color;
}

#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthBuffer;

varying vec2 texCoord;

float near = 0.1;
float far = 1.0;

float LinearizeDepth(float depth){
    float z = depth * 2.0 - 1.0;
    return (near*far) / (far + near - z * (far-near));
}


void main() {

    float depth = LinearizeDepth(texture2D(DepthBuffer, texCoord).r);

    float x1 = texture2D(DepthBuffer, texCoord).r;
    float x2 = texture2D(DepthBuffer, texCoord).g;
    float x3 = texture2D(DepthBuffer, texCoord).b;

    float y1 = texture2D(DiffuseSampler, texCoord).r;
    float y2 = texture2D(DiffuseSampler, texCoord).g;
    float y3 = texture2D(DiffuseSampler, texCoord).b;

    vec4 color = vec4((x1-y1)*50,(x2-y2)*50,(x3-y3)*50,1);
    gl_FragColor = vec4(texture2D(DepthBuffer, texCoord).r,texture2D(DepthBuffer, texCoord).g,texture2D(DepthBuffer, texCoord).b,texture2D(DepthBuffer, texCoord).a);
   //gl_FragColor = texture2D(DepthBuffer, texCoord);
}

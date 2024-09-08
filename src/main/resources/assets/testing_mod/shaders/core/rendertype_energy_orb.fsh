#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform float GameTime;
uniform float Power;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;



out vec4 fragColor;

void main() {

    vec2 textureCoord = texCoord0;
    textureCoord.x += GameTime * 100;
    //vec4 color = texture(Sampler0, texCoord0) * vertexColor;

    vec3 color = vec3(0.7,0.6,1.0);

    float alpha = texture(Sampler0, textureCoord).x * 1.5;
    textureCoord.x -= GameTime * 100;
    textureCoord.y += 100 + GameTime * 100;
    float colorChange = texture(Sampler0, textureCoord).x * 1.5 + 0.3;
    fragColor = vec4(color.x * colorChange,color.y * colorChange ,color.z * colorChange, alpha);
}


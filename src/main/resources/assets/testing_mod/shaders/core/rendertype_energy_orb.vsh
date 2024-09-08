#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;

out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;


float rand(float n){return fract(sin(n) * 43758.5453123);}

void main() {
    vec3 pos = Position;
    float random = rand(GameTime * 500);

   // pos = pos - (random * Normal / 20.0);
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1);


    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}


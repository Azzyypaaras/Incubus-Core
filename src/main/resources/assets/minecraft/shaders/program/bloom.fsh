#version 150

in vec2 texCoord;
in vec2 oneTexel;

uniform sampler2D DiffuseSampler;
uniform sampler2D LightSourceSampler;

out vec4 fragColor;

const float pi = 3.141592654;
const float tau = pi * 2;

uniform float Radius;
uniform float Divisor;
const float SIN_45 = 0.707106781;
const float HALF_SIN_45 = SIN_45 * 0.5;

void main() {
    vec2 texel = oneTexel * Radius;
    vec4 blur = texture(LightSourceSampler, texCoord);

    blur += texture(LightSourceSampler, texCoord + vec2(0.5, 0) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(1, 0) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(HALF_SIN_45, HALF_SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(SIN_45, SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(0, 0.5) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(0, 1) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(HALF_SIN_45, -HALF_SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(SIN_45, -SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-0.5, 0) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-1, 0) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-HALF_SIN_45, -HALF_SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-SIN_45, -SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(0, -0.5) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(0, -1) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-HALF_SIN_45, HALF_SIN_45) * texel);
    blur += texture(LightSourceSampler, texCoord + vec2(-SIN_45, SIN_45) * texel);

    blur /= Divisor;
    fragColor = vec4((texture(DiffuseSampler, texCoord) + blur).rgb, 1);
}

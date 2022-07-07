#version 150

in vec2 texCoord;
in vec2 oneTexel;

uniform sampler2D DiffuseSampler;
uniform sampler2D LightSourceSampler;

out vec4 fragColor;

const float pi = 3.141592654;
const float tau = pi * 2;

const float radius = 20.0;

void main() {
    vec4 blur = texture(LightSourceSampler, texCoord);

    for (float d = 0.0; d < tau; d += tau / 16) {
        for (float i = 0.33; i <= 1.0; i += 0.33) {
            blur += texture(LightSourceSampler, texCoord + vec2(cos(d), sin(d)) * oneTexel * radius * i);
        }
    }

    blur /= 18;
    fragColor = vec4((texture(DiffuseSampler, texCoord) + blur).rgb, 1);
}

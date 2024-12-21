
attribute vec2 a_position;
attribute vec2 a_texCoord0;
attribute vec4 a_color;

uniform mat4 u_projection;
uniform mat3 u_worldTransform;

varying vec2 v_texCoord;
varying vec4 v_color;

void main() {
    v_texCoord = a_texCoord0;
    v_color = a_color;

    vec3 position = u_worldTransform * (vec3(a_position,1.0));
    gl_Position = u_projection * vec4(position.xy,0.0, 1.0);
}

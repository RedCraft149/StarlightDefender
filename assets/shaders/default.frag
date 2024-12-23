precision mediump float;

uniform sampler2D u_texture;
varying vec2 v_texCoord;
varying vec4 v_color;


void main() {
    vec4 color = texture2D(u_texture,v_texCoord);
    gl_FragColor = color * v_color;
}

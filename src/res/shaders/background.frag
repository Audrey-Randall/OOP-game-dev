// texture fragment shader
#version 330 core

in vec2 texCoord;
in vec3 vertexPos;

out vec4 FragColor;

uniform sampler2D texture1;

uniform vec2 pos = vec2(0.0);
uniform float aspectRatio = 1.0;

void main()
{
	vec2 coord = vertexPos.yx/2+.5; // convert to 0-1 space
	coord = 1-coord; // put it right side up
	coord.x *= 1.3 * aspectRatio; // scale x coordinate to map properly
	coord -= pos; // offset to make the background move

	FragColor = texture(texture1, coord);
}

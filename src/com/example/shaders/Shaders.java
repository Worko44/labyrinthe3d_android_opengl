package com.example.shaders;

import android.opengl.GLES20;

public class Shaders 
{
	public static int loadShader(int type, String shaderCode)
	{
		int shader = GLES20.glCreateShader(type);

		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}

	public static String fragmentShaderCode()
	{
		final String fragmentShaderCode =
				"precision mediump float;" +
						"uniform vec4 vColor;" +
						"void main() {" +
						"  gl_FragColor = vColor;" +
						"}";
		return (fragmentShaderCode);
	}

	public static String vertexShaderCode()
	{
		final String vertexShaderCode = "uniform mat4 uMVPMatrix;" 
				+ "attribute vec4 vPosition;" + "void main() {" 
				+ " gl_Position = uMVPMatrix * vPosition;" + "}";
		return (vertexShaderCode);
	}

	public static String vertexShaderCodeButton()
	{
		final String vertexShaderCode =
				"attribute vec4 vPosition;" 
						+ "void main() {" 
						+"  gl_Position = vPosition;" + "}";
		return (vertexShaderCode);
	}

	public static String vertexTexture()
	{
		final String vertexShaderCode =
				"uniform mat4 uMVPMatrix;" +
					    "attribute vec4 vPosition;" +
					    "attribute vec2 a_TexCoordinate;" +
					    "varying vec2 v_TexCoordinate;" +
					    "void main() {" +
					    "  gl_Position = vPosition;" +
					    "  v_TexCoordinate = a_TexCoordinate;" +
					    "}";
		return (vertexShaderCode);
	}

	public static String fragmentTexture()
	{
		final String fragmentShaderCode =
				"precision mediump float;" +
					    "uniform sampler2D u_Texture;" +
					    "varying vec2 v_TexCoordinate;" +
					    "void main() {" +
					    "  gl_FragColor = texture2D(u_Texture, v_TexCoordinate);" +
					    "}";
		return (fragmentShaderCode);
	}
	
	public static String vertexTextureDimension()
	{
		final String vertexShaderCode =
				"uniform mat4 uMVPMatrix;" +
					    "attribute vec4 vPosition;" +
					    "attribute vec2 a_TexCoordinate;" +
					    "varying vec2 v_TexCoordinate;" +
					    "void main() {" +
					    "  gl_Position = uMVPMatrix * vPosition;" +
					    "  v_TexCoordinate = a_TexCoordinate;" +
					    "}";
		return (vertexShaderCode);
	}
}
package com.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import android.content.Context;
import android.opengl.GLES20;


import com.example.shaders.Shaders;

public class Square 
{
	int vertexShader;
	int fragmentShader;
	int mProgram;
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	static final int COORDS_PER_VERTEX = 3;
	float[]  squareCoords;
	float color[];
	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

	public Square(float[] squareCoords2, Context context, float[] color2, int flag, int textureOrNot) 
	{

		squareCoords = squareCoords2;
		color = color2;
		ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoords);
		vertexBuffer.position(0);
		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);
		if (flag != 1)
			vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER, Shaders.vertexShaderCode());
		else
			vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER, Shaders.vertexShaderCodeButton());
		fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER, Shaders.fragmentShaderCode());
		mProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glBindAttribLocation(mProgram, 0, "a_TexCoordinate"); //texture
		GLES20.glLinkProgram(mProgram);
	}

	public void draw(float[] mvpMatrix, int dimOrNot)
	{
		int mPositionHandle;
		int vertexCount = 4;
		int vertexStride = COORDS_PER_VERTEX * 4;
		int mColorHandle;
		int mMVPMatrixHandle;

		GLES20.glUseProgram(mProgram);
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride , vertexBuffer);
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		if (dimOrNot != 1)
		{
			mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
			GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		}
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}
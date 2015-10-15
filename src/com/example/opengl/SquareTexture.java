package com.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.example.shaders.Shaders;

import android.content.Context;
import android.opengl.GLES20;

public class SquareTexture 
{		
	private final FloatBuffer vertexBuffer;
	private final FloatBuffer textureBuffer;
	private final ShortBuffer drawListBuffer;
	private final int mProgram;
	private int mPositionHandle;
	private int mMVPMatrixHandle;
	static final int COORDS_PER_VERTEX = 3;
	final float[] previewTextureCoordinateData =
		{
			0.0f, 1.0f,
			1.0f, 1.0f, 
			0.0f, 0.0f,
			1.0f, 0.0f
		};
	private int textureUniformHandle;
	private int texture;
	private int textureCoordinateHandle;
	private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
	private final int vertexStride = COORDS_PER_VERTEX * 4;
	float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

	public SquareTexture(Context context, float squareCoords[], int textureDataHandle, boolean isDimension) 
	{
		int vertexShader;
	
		texture = textureDataHandle;
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
		ByteBuffer texCoordinates = ByteBuffer.allocateDirect(previewTextureCoordinateData.length * 4);
		texCoordinates.order(ByteOrder.nativeOrder());
		textureBuffer = texCoordinates.asFloatBuffer();
		textureBuffer.put(previewTextureCoordinateData);
		textureBuffer.position(0);
		if (isDimension == true)
			vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,Shaders.vertexTextureDimension());
		else	
			vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,Shaders.vertexTexture());
		int fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER,Shaders.fragmentTexture());
		mProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram);
	}

	public void draw(float[] mvpMatrix) 
	{
		GLES20.glUseProgram(mProgram);
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
		textureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
		GLES20.glVertexAttribPointer(textureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glEnableVertexAttribArray(textureCoordinateHandle);
		textureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
		GLES20.glUniform1i(textureUniformHandle, 0);      
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}

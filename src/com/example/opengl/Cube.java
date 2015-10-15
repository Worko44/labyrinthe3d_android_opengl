package com.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import android.opengl.GLES20;

import com.example.shaders.Shaders;

class Cube {

	private FloatBuffer mVertexBuffer;
	private FloatBuffer mColorBuffer;
	private ShortBuffer  mIndexBuffer;
	private  int mProgram, mPositionHandle, mColorHandle, mMVPMatrixHandle ; 
	private float vertices[] = {
			-0.2f, -0.2f, 0.3f,//Bottom Left
			0.2f, -0.2f, 0.2f, //Bottom Right
			0.2f, 0.2f, 0.2f,  //Top Right
			-0.2f, 0.2f, 0.2f, //Top left

			-0.2f, -0.2f, -0.2f,//Bottom Left
			0.2f, -0.2f, -0.2f, //Bottom Right
			0.2f, 0.2f, -0.2f,  //Top Right
			-0.2f, 0.2f, -0.2f //Top left
	};
	private float colors[] = {
			0.0f,  1.0f,  0.0f,  1.0f,
			0.0f,  1.0f,  0.0f,  1.0f,
			1.0f,  0.5f,  0.0f,  1.0f,
			1.0f,  0.5f,  0.0f,  1.0f,
			1.0f,  0.0f,  0.0f,  1.0f,
			1.0f,  0.0f,  0.0f,  1.0f,
			0.0f,  0.0f,  1.0f,  1.0f,
			1.0f,  0.0f,  1.0f,  1.0f
	};
	private short indices[] = {
			0,1,2, 0,2,3,//front
			0,3,7, 0,7,4,//Left    
			0,1,5, 0,5,4,//Bottom

			6,7,4, 6,4,5,//Back
			6,7,3, 6,3,2,//top
			6,2,1, 6,1,5//right
	};

	public Cube() {

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuf.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mColorBuffer = byteBuf.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(indices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mIndexBuffer = byteBuf.asShortBuffer();
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);

		int vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER, Shaders.vertexShaderCode());
		int fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER, Shaders.fragmentShaderCode());

		mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(mProgram); 

	}

	public void draw (float[] mvpMatrix)
	{
		//GLES20.glFrontFace(GLES20.GL_CCW);
		GLES20.glUseProgram(mProgram);
		// get handle to vertex shader's vPosition member
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, 3,
				GLES20.GL_FLOAT, false,
				12, mVertexBuffer);
		// get handle to fragment shader's vColor member
		mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
		GLES20.glEnableVertexAttribArray(mColorHandle);
		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mColorHandle, 4,
				GLES20.GL_FLOAT, false,
				0, mColorBuffer);
		// Set color for drawing the triangle
		//     GLES20.glUniform2fv(mColorHandle, 1,colors, 0);
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		// Draw the triangle
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);
		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}
}
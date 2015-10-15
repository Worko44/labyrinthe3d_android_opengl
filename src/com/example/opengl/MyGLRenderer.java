package com.example.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.example.shaders.ButtonSquare;
import com.example.shaders.Map;
import com.example.shaders.Sky;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer 
{
	Map						map;
	ButtonSquare			button;
	Sky						sky;
	float[] 				scratch = new float[16];
	float[] 				mProjectionMatrix = new float[16];
	float[] 				mViewMatrix = new float[16];
	float[]					eyes = new float[3];
	float[]					eyesView = new float[3];
	public volatile float	zoomX = -3.0f;
	Context 				conText;
	
	public MyGLRenderer(Context context)
	{
		conText = context;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		//GLES20.glClearColor(0.0f,0.0f,1.0f,1.0f);
		initGame();
		Log.d("onSurfaceCreated", "onSurfaceCreated");	
	}

	private void initGame() 
	{
		eyes[0] = 0.25f;
		eyes[1] = 0.5f;
		eyes[2] = 0.0f;
		eyesView[0] = 0.25f;
		eyesView[1] = 0.5f;
		eyesView[2] = 1.0f;
		map = new Map(conText);
		button = new ButtonSquare(conText);
		sky = new Sky(conText);
	}

	public void onDrawFrame(GL10 unused)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		//checkColision(eyes, eyesView);
		Matrix.setLookAtM(mViewMatrix, 0, eyes[0], eyes[1], eyes[2], eyesView[0], eyesView[1], eyesView[2], 0.0f, 1.0f, 0.0f);
		Matrix.multiplyMM(scratch, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		map.drawMap(scratch);
		sky.drawSkyGround(scratch);
		button.drawButtonClick(scratch, unused);
		Log.d("Position", "EyesX="+eyes[0]+" EyesY="+eyes[1]+" EyesZ="+eyes[2]);
		Log.d("View", "ViewX="+eyesView[0]+" ViewY="+eyesView[1]+" ViewZ="+eyesView[2]);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		float ratio = (float) width / height;
		
		GLES20.glViewport(0, 0, width, height);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -0.5f, 0.5f, 1.0f, 50.0f);
		Log.d("onSurfaceChanged", "onSurfaceChanged");
	}
}

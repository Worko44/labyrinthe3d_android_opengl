package com.example.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView 
{
	private double 	a = 360;
	private float	eyesTmp[] = new float[3];
	private float	eyesViewTmp[] = new float[3];
	MyGLRenderer 	mRenderer;
	GestureDetector gestureDetector;
	private float 	lastSpacing = 0;

	public MyGLSurfaceView(Context context)
	{
		super(context);
		setEGLContextClientVersion(2);
		setRenderer(mRenderer = new MyGLRenderer(context));
		gestureDetector = new GestureDetector(context, new GestureListener());
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		// 2 fingers or more
		if (e.getPointerCount() == 2)
		{
			Log.d("Button", "2 fingers same time");
			float currentSpacing = spacing(e);
			if (lastSpacing != 0)
			{
				if (currentSpacing > lastSpacing && (mRenderer.zoomX < -2.0f)) 
					mRenderer.zoomX += 0.15f;
				else if (currentSpacing < lastSpacing && (mRenderer.zoomX > -15.0f)) 
					mRenderer.zoomX -= 0.15f;
				else 
					mRenderer.zoomX += 0.0f;
			}
			lastSpacing = currentSpacing;
			requestRender();
		}
		else
		{
			float x = e.getX();
			float y = e.getY();
			float xMax = (float) super.getWidth();
			float yMax = (float) super.getHeight();

			eyesTmp = mRenderer.eyes.clone();
			eyesViewTmp = mRenderer.eyesView.clone();
			if ((y > (yMax * 0.9f)) && (x < (xMax * 0.25f)) && e.getAction() == MotionEvent.ACTION_DOWN)
			{
				Log.d("Button", "UP");
				mRenderer.eyes[2] += ((mRenderer.eyesView[2] - mRenderer.eyes[2]) / 8.0f);
				mRenderer.eyes[0] += ((mRenderer.eyesView[0] - mRenderer.eyes[0]) / 8.0f);
				mRenderer.eyesView[0] = mRenderer.eyes[0] - ((float)(Math.sin(Math.toRadians(a))));
				mRenderer.eyesView[2] = mRenderer.eyes[2] + ((float)(Math.cos(Math.toRadians(a))));
				if (checkColision() == true)
					refreshTmpEyes();
			}
			if ((y > (yMax * 0.9f)) && (x > (xMax * 0.75f)) && e.getAction() == MotionEvent.ACTION_DOWN)
			{
				Log.d("Button", "DOWN");
				mRenderer.eyes[2] += ((-1.0f) * ((mRenderer.eyesView[2] - mRenderer.eyes[2]) / 8.0f));
				mRenderer.eyes[0] += ((-1.0f) * ((mRenderer.eyesView[0] - mRenderer.eyes[0]) / 8.0f));
				mRenderer.eyesView[0] = mRenderer.eyes[0] - ((float)(Math.sin(Math.toRadians(a))));
				mRenderer.eyesView[2] = mRenderer.eyes[2] + ((float)(Math.cos(Math.toRadians(a))));
				if (checkColision() == true)
					refreshTmpEyes();
			}
			if ((y > (yMax * 0.9f)) && (x > (xMax * 0.5f)) && (x < (xMax * 0.75f)) && e.getAction() == MotionEvent.ACTION_DOWN)
			{
				Log.d("Button", "RIGHT");
				if (a < 360)
					a += 10;
				else
					a = 0;
				mRenderer.eyesView[0] = mRenderer.eyes[0] - ((float)(Math.sin(Math.toRadians(a))));
				mRenderer.eyesView[2] = mRenderer.eyes[2] + ((float)(Math.cos(Math.toRadians(a))));
				if (checkColision() == true)
					refreshTmpEyes();
			}
			if ((y > (yMax * 0.9f)) && (x > (xMax * 0.25f)) && (x < (xMax * 0.5f)) && e.getAction() == MotionEvent.ACTION_DOWN)
			{
				Log.d("Button", "LEFT");
				if (a > 0)
					a -= 10;
				else
					a = 360;
				mRenderer.eyesView[0] = mRenderer.eyes[0] - ( (float)(Math.sin(Math.toRadians(a))));
				mRenderer.eyesView[2] = mRenderer.eyes[2] + ( (float)(Math.cos(Math.toRadians(a))));
				if (checkColision() == true)
					refreshTmpEyes();
			}
			requestRender();
		}
		return gestureDetector.onTouchEvent(e);
	}

	private void refreshTmpEyes() 
	{
		mRenderer.eyes = eyesTmp.clone();
		mRenderer.eyesView = eyesViewTmp.clone();
	}

	private boolean checkColision()
	{
		int[][]		map;
		float		tmpX;
		float		tmpY;
		float		oneX;
		float		oneY;
		float		space = 0.3f;

		map = mRenderer.map.getMap();
		oneY = 0.0f;
		oneX = 0.0f;
		/*if ((mRenderer.eyes[0] < 0.0f) && (a <= 180))
			oneX = 1.0f;*/
		/*if ((mRenderer.eyes[0]) < 0.0f && (a <= 180))
			oneX = -1.0f;*/
		/*if ((mRenderer.eyes[2] < 0.0f) && ((a <= 270) || (a >= 90)))
			oneY = 1.0f;*/
		/*if ((mRenderer.eyes[2]) < 0.0f && ((a <= 270) && (a >= 90)))
			oneY = 1.0f;
		if ((mRenderer.eyes[0]) > 0.0f && (a >= 180))
			oneX = 0.0f;*/
		/*if ((mRenderer.eyes[0]) > 0.0f && (a <= 180))
			oneX = -1.0f;*/
		/*if ((mRenderer.eyes[2]) > 0.0f && ((a <= 270) && (a >= 90)) && (mRenderer.eyes[0] < 0.0f)))
			oneY = -1.0f;*/
		/*else
			oneX = 0.0f;
		if (mRenderer.eyes[2] < 0.0f)
			oneY = 1.0f;
		else
			oneY = 0.0f;*/
		Log.d("Angle", "a="+a);
		tmpX = ((((float)(mRenderer.map.getSizeX()) - 1.0f) / (2.0f)) - (mRenderer.eyes[0] / (float)(mRenderer.map.getZoom())) + 0.5f) + oneX;
		tmpY = (((float)((mRenderer.map.getSizeY()) - 1.0f) / (2.0f)) - (mRenderer.eyes[2] / (float)(mRenderer.map.getZoom())) + 0.5f) + oneY;
		Log.d("Colision", "X="+ tmpX);
		Log.d("Colision", "Y="+ tmpY);
		if ((map[(int)(tmpY + space)][(int)(tmpX)] == 1) || (map[(int)(tmpY)][(int)(tmpX + space)] == 1) || 
				(map[(int)(tmpY + space)][(int)(tmpX + space)] == 1) || (map[(int)(tmpY - space)][(int)(tmpX - space)] == 1)
				|| (map[(int)(tmpY + space)][(int)(tmpX - space)] == 1) || (map[(int)(tmpY - space)][(int)(tmpX + space)] == 1)
				|| (map[(int)(tmpY - space)][(int)(tmpX)] == 1) || (map[(int)(tmpY)][(int)(tmpX - space)] == 1)
				|| (map[(int)(tmpY)][(int)(tmpX)] == 1))
			return (true);
		return (false);
	}

	private float spacing(MotionEvent event) 
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener 
	{
		@Override
		public boolean onDown(MotionEvent e) 
		{
			return true;
		}
		@Override
		public boolean onDoubleTap(MotionEvent e)
		{
			float x = e.getX();
			float y = e.getY();

			Log.d("Button", "Double Tapped at: (" + x + "," + y + ")");
			return true;
		}
		public void onLongPress(MotionEvent e)
		{
			float x = e.getX();
			float y = e.getY();

			Log.d("Button", "Double Tapped at: (" + x + "," + y + ")");
		}
	}
}
package com.example.shaders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.opengl.R;
import com.example.opengl.SquareTexture;

public class Sky
{
	public SquareTexture		sky;
	public static int			textureSky;
	public static int			sizeX = 20 * 2;
	public static int			sizeY = 20 * 2;

	public Sky(Context context)
	{
		textureSky = loadTextures(context, R.drawable.ciel);
		sky = new SquareTexture(context, getSquareSky(), textureSky, true);
	}

	public void drawSkyGround(float[] scratch)
	{
		sky.draw(scratch);
	}

	public static float[] getSquareSky()
	{
		int sizeNegX = ((sizeX + 1) / (-2));
		int sizeNegY = ((sizeY + 1) / (-2));

		float squareCoords[] = 
			{
				(float)sizeNegX, 1.5f,  ((float)sizeNegY * (-1f)),   // top left
				(float)sizeNegX, 1.5f, (float)sizeNegY,   // bottom left
				(float)(sizeNegX * (-1.0f)), 1.5f, (float)sizeNegY,  // bottom right
				(float)(sizeNegX * (-1.0f)), 1.5f, ((float)sizeNegY * (-1.0f)) }; // top right
		return (squareCoords);
	}

	public static float[] getColorSky()
	{
		float color[] = { 0.0f, 0.0f, 1.0f, 1.0f };

		return (color);
	}
	
	private int loadTextures(final Context context, final int resourceId)
	{
		final int[] textureHandle = new int[1];

		GLES20.glGenTextures(1, textureHandle, 0);
		if (textureHandle[0] != 0)
		{
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
		}
		if (textureHandle[0] == 0)
			throw new RuntimeException("Error loading texture.");
		return textureHandle[0];
	}
}

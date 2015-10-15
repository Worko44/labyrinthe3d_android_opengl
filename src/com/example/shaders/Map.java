package com.example.shaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.opengl.R;
import com.example.opengl.SquareTexture;

public class Map 
{
	int[][]				map;
	int					sizeX;
	int					sizeY;
	int					nbMur;
	int					nbSol;
	static int			textureSol;
	static int			textureMur;
	SquareTexture[][]	mur;
	SquareTexture[]		sol;
	final static float	sizeMur = 1.0f;
	int					zoom = 2;
	final static float 	colorMur[] = { 0.43671875f, 0.56953125f, 0.22265625f, 1.0f };

	public Map(Context context)
	{
		map = getMap();
		sizeX =  map[0].length;
		sizeY = map.length;
		textureSol = loadTextures(context, R.drawable.herbe);
		textureMur = loadTextures(context, R.drawable.mur);
		nbMur = getNbMur(map, sizeX, sizeY);
		nbSol = getNbSol(map, sizeX, sizeY);
		mur = getCordSquareMur(map, sizeX, sizeY, nbMur, zoom, context);
		sol = getCordSquareSol(map, sizeX, sizeY, nbSol, zoom, context);
	}

	public int[][] getMap()
	{
		int[][] map =
			{
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
				{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			};
		return (map);
	}

	public int getSizeX() 
	{
		return sizeX;
	}

	public int getSizeY() 
	{
		return sizeY;
	}

	public int getZoom() 
	{
		return zoom;
	}

	public static int	getNbMur(int[][] map, int sizeX, int sizeY)
	{
		int				nbMurTmp;
		int				x;
		int				y;

		y = 0;
		nbMurTmp = 0;
		while (y < sizeY)
		{
			x = 0;
			while (x < sizeX)
			{
				if (map[y][x] == 1)
					nbMurTmp += 1;
				x++;
			}
			y++;
		}
		return (nbMurTmp);
	}

	public static int	getNbSol(int[][] map, int sizeX, int sizeY)
	{
		int				nbSolTmp;
		int				x;
		int				y;

		y = 0;
		nbSolTmp = 0;
		while (y < sizeY)
		{
			x = 0;
			while (x < sizeX)
			{
				if (map[y][x] == 0)
					nbSolTmp += 1;
				x++;
			}
			y++;
		}
		return (nbSolTmp);
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

	public static SquareTexture[][]	getCordSquareMur(int[][] map, int sizeX, int sizeY, int nbMur, int zoom2, Context context)
	{
		SquareTexture[][] murTmp = new SquareTexture[nbMur][4];
		int					x;
		int					y;
		int					z;

		y = 0;
		z = 0;
		while (y < sizeY)
		{
			x = 0;
			while (x < sizeX)
			{
				if (map[y][x] == 1)
				{
					murTmp[z] = getFourSquare(x, y, ((sizeX - 1) / (2)), ((sizeY - 1) / (2)), zoom2, context);
					z++;
				}
				x++;
			}
			y++;
		}
		return (murTmp);
	}

	private static SquareTexture[] getFourSquare(int x, int y, int x2, int y2, int zoom2, Context context) 
	{
		SquareTexture[] tmp = new SquareTexture[4];
		int					meterX;
		int					meterY;
		float				oneX;
		float				oneY;

		/*if (x > x2)
			oneX = (float)zoom2 * (-1.0f);
		else*/
			oneX = zoom2;
		/*if (y > y2)
			oneY = (float)zoom2 * (-1.0f);
		else*/
			oneY = zoom2;
		meterX = (x2 - x) * zoom2;
		meterY = (y2 - y) * zoom2;
		float squareCoords1[] = 
			{
				(float)meterX + oneX,  sizeMur + 0.5f, (float)meterY,   // top left
				(float)meterX + oneX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY,   // bottom left
				(float)meterX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY,   // bottom right
				(float)meterX,  sizeMur + 0.5f, (float)meterY   // top right 
			};
		float squareCoords2[] = 
			{
				(float)meterX + oneX,  sizeMur + 0.5f, (float)meterY + oneY,   // top left
				(float)meterX + oneX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY + oneY,   // bottom left
				(float)meterX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY + oneY,   // bottom right
				(float)meterX,  sizeMur + 0.5f, (float)meterY + oneY   // top right 
			};

		float squareCoords3[] = 
			{
				(float)meterX + oneX,  sizeMur + 0.5f, (float)meterY,   // top left
				(float)meterX + oneX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY,   // bottom left
				(float)meterX + oneX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY + oneY,   // bottom right
				(float)meterX + oneX,  sizeMur + 0.5f, (float)meterY + oneY   // top right 
			};
		float squareCoords4[] = 
			{
				(float)meterX,  sizeMur + 0.5f, (float)meterY,   // top left
				(float)meterX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY,   // bottom left
				(float)meterX, ((-1.0f) * sizeMur) + 0.5f, (float)meterY + oneY,   // bottom right
				(float)meterX,  sizeMur + 0.5f, (float)meterY + oneY   // top right 
			};
		tmp[0] = new SquareTexture(context, squareCoords1, textureMur, true);
		tmp[1] = new SquareTexture(context, squareCoords2, textureMur, true);
		tmp[2] = new SquareTexture(context, squareCoords3, textureMur, true);
		tmp[3] = new SquareTexture(context, squareCoords4, textureMur, true);
		return (tmp);
	}

	public static SquareTexture[]	getCordSquareSol(int[][] map, int sizeX, int sizeY, int nbSol, int zoom2, Context context)
	{
		SquareTexture[] solTmp = new SquareTexture[nbSol];
		int					x;
		int					y;
		int					z;

		y = 0;
		z = 0;
		while (y < sizeY)
		{
			x = 0;
			while (x < sizeX)
			{
				if (map[y][x] == 0)
				{
					solTmp[z] = getOneSquare(x, y, ((sizeX - 1) / (2)), ((sizeY - 1) / (2)), zoom2, context);
					z++;
				}
				x++;
			}
			y++;
		}
		return (solTmp);
	}

	private static SquareTexture getOneSquare(int x, int y, int x2, int y2, int zoom2, Context context) 
	{
		SquareTexture 		tmp;
		int					meterX;
		int					meterY;
		float				oneX;
		float				oneY;

		/*if (x > x2)
			oneX = (float)zoom2 * (-1.0f);
		else*/
			oneX = zoom2;
		/*if (y > y2)
			oneY = (float)zoom2 * (-1.0f);
		else*/
			oneY = zoom2;
		meterX = (x2 - x) * zoom2;
		meterY = (y2 - y) * zoom2;
		float squareCoords[] = 
			{
				(float)meterX + oneX,  0.0f, (float)meterY,   // top left
				(float)meterX, 0.0f, (float)meterY,   // bottom left
				(float)meterX, 0.0f , (float)meterY + oneY,   // bottom right
				(float)meterX + oneX,  0.0f , (float)meterY + oneY   // top right 
			};
		tmp = new SquareTexture(context, squareCoords, textureSol, true);
		return (tmp);
	}

	public void			drawMap(float[] scratch)
	{
		int				x;
		int				y;

		y = 0;
		while (y < nbMur)
		{
			x = 0;
			while (x < 4)
			{
				mur[y][x].draw(scratch);
				x++;
			}
			y++;
		}
		y = 0;
		while (y < nbSol)
		{
			sol[y].draw(scratch);
			y++;
		}
	}
}
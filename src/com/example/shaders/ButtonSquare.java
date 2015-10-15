package com.example.shaders;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.example.opengl.Square;

public class ButtonSquare 
{
	Square[] buttonClick;
	
	public ButtonSquare(Context context)
	{
		buttonClick = new Square[4];
		buttonClick[0] = new Square(getButtonUp(), context, getColorButtonUp(), 1, 0);
		buttonClick[1] = new Square(getButtonDown(), context, getColorButtonDown(), 1, 0);
		buttonClick[2] = new Square(getButtonRight(), context, getColorButtonRight(), 1, 0);
		buttonClick[3] = new Square(getButtonLeft(), context, getColorButtonLeft(), 1, 0);
	}
	
	public void drawButtonClick(float[] scratch, GL10 gl)
	{
		buttonClick[0].draw(scratch, 1);
		buttonClick[1].draw(scratch, 1);
		buttonClick[2].draw(scratch, 1);
		buttonClick[3].draw(scratch, 1);
	}
	
	public static float[] getButtonUp()
	{
		float squareCoords[] = 
			{
				-1.0f, -0.9f, -1.0f,   // top left
				-1.0f, -1.0f, -1.0f,   // bottom left
				-0.5f, -1.0f, -1.0f,   // bottom right
				-0.5f,  -0.9f, -1.0f }; // top right

		return (squareCoords);
	}
	
	public static float[] getColorButtonUp()
	{
		float color[] = { 0.63671875f, 0.16953125f, 0.22265625f, 1.0f };
		
		return (color);
	}
	
	public static float[] getButtonLeft()
	{
		float squareCoords[] = 
			{
				-0.5f, -0.9f, -1.0f,   // top left
				-0.5f, -1.0f, -1.0f,   // bottom left
				0.0f, -1.0f, -1.0f,   // bottom right
				0.0f,  -0.9f, -1.0f }; // top right

		return (squareCoords);
	}
	
	public static float[] getColorButtonLeft()
	{
		float color[] = { 0.43671875f, 0.46953125f, 0.7525625f, 1.0f };
		
		return (color);
	}

	public static float[] getButtonRight()
	{
		float squareCoords[] = 
			{
				0.0f, -0.9f, -1.0f,   // top left
				0.0f, -1.0f, -1.0f,   // bottom left
				0.5f, -1.0f, -1.0f,   // bottom right
				0.5f,  -0.9f, -1.0f }; // top right

		return (squareCoords);
	}
	
	public static float[] getColorButtonRight()
	{
		float color[] = { 0.88671875f, 0.822953125f, 0.82265625f, 1.0f };
		
		return (color);
	}

	public static float[] getButtonDown()
	{
		float squareCoords[] = 
			{
				0.5f,  -0.9f, -1.0f,   // top left
				0.5f, -1.0f, -1.0f,   // bottom left
				1.0f, -1.0f, -1.0f,   // bottom right
				1.0f,  -0.9f, -1.0f }; // top right
		return (squareCoords);
	}

	public static float[] getColorButtonDown()
	{
		float color[] = { 0.63671875f, 0.36953125f, 0.22265625f, 1.0f };
		
		return (color);
	}

	public static float[] getSquareTest()
	{
		float squareCoords2[] = {
				0.5f,  0.5f, 0.0f,   // top left
				0.5f, -0.5f, 0.0f,   // bottom left
				0.9f, -0.5f, 0.0f,   // bottom right
				0.9f,  0.5f, 0.0f }; // top right
		return (squareCoords2);
	}
	
	public static float[] getColorButtonTest()
	{
		float color[] = { 0.63671875f, 0.26953125f, 0.22265625f, 1.0f };
		return (color);
	}

}

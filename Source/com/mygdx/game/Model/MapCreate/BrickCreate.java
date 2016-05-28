package com.mygdx.game.Model.MapCreate;

public class BrickCreate{
	public float x;
	public float y;
	public int type;
	public BrickCreate(float PosX, float PosY, int brickType){
		this.x = PosX;
		this.y = PosY;
		this.type = brickType;
	}
}

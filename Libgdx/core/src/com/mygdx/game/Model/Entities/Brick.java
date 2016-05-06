package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Michael on 2016-04-18.
 */
public class Brick implements Poolable{
	public enum BrickType{
		normal1,
		normal2,
		normal3,
		steel1,
		steel2,
		steel3,
		invisible,
		invurnerable,
		undefined
	}
	
	private boolean alive;
	private BrickType brickType;
	
	
    public static float BRICK_WIDTH = 45;
    public static float BRICK_HEIGHT = 15;
    public Body brickBody;
    public int ID;
    
    public Brick(int numberofBricksTotal) {
		ID = numberofBricksTotal;
		brickType = BrickType.undefined;
		alive = false;
	}
	public void setBody(Body body){
    	brickBody = body;
    }
	public void setType(BrickType type){
		alive = true;
		brickType = type;
	}
	public void hit(){
		switch (brickType) {
		case invisible:
			brickType = BrickType.steel3;
			break;
		case invurnerable:
			break;
		case normal1:
			alive = false;
			break;
		case normal2:
			brickType = BrickType.normal1;
			break;
		case normal3:
			brickType = BrickType.normal2;
			break;
		case steel1:
			alive = false;
			break;
		case steel2:
			brickType = BrickType.steel1;
			break;
		case steel3:
			brickType = BrickType.steel2;
			break;
		default:
			break;
		}
	}
	public boolean isAlive(){
		return alive;
	}
	
	public void init(){
		
    }
	@Override
	public void reset() {
		alive = false;
		brickType = BrickType.undefined;
		brickBody = null;
	}
}

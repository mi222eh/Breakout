package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.mygdx.game.interfaces.GameSoundListener;

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
	public BrickType brickType;
	
	public static GameSoundListener soundListener;
    public static float BRICK_WIDTH = 45;
    public static float BRICK_HEIGHT = 15;
    public Body brickBody;
    public int ID;
    
    public Brick(int numberofBricksTotal) {
		this.ID = numberofBricksTotal;
		this.brickType = BrickType.undefined;
		this.alive = false;
	}
	public void setBody(Body body){
    	this.brickBody = body;
    }
	public void setType(BrickType type){
		this.alive = true;
		this.brickType = type;
	}
	public void hit(){
		switch (this.brickType) {
		case invisible:
			this.brickType = BrickType.steel3;
			soundListener.SteelHit();
			break;
		case invurnerable:
			soundListener.InvurnerableHit();
			break;
		case normal1:
			soundListener.Normalhit();
			this.alive = false;
			break;
		case normal2:
			soundListener.Normalhit();
			this.brickType = BrickType.normal1;
			break;
		case normal3:
			soundListener.Normalhit();
			this.brickType = BrickType.normal2;
			break;
		case steel1:
			soundListener.SteelHit();
			this.alive = false;
			break;
		case steel2:
			soundListener.SteelHit();
			this.brickType = BrickType.steel1;
			break;
		case steel3:
			soundListener.SteelHit();
			this.brickType = BrickType.steel2;
			break;
		default:
			break;
		}
	}
	public boolean isAlive(){
		return this.alive;
	}
	
	public void init(){
		
    }
	@Override
	public void reset() {
		this.alive = false;
		this.brickType = BrickType.undefined;
		this.brickBody = null;
	}
}

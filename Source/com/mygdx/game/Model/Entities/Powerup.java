package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Powerup implements Poolable{
	public static int POWERUP_WIDTH = 40;
	public static int POWERUP_HEIGHT = 40;
	public enum PowerupType{
		MakePlayerWider,
		MakePlayerSmaller,
		DuplicateBalls,
		MakeBallsSlower,
		MakeBallsFaster
	}
	public Body powerBody;
	public boolean alive;
	public PowerupType type;
	public boolean activate;
	
	public Powerup(){
		this.alive = false;
		this.activate = false;
	}
	
	public void setBody(Body body){
		this.powerBody = body;
		this.alive = true;
		this.activate = false;
	}
	
	public void init(Vector2 position, Vector2 speed, PowerupType type){
		this.powerBody.setTransform(position, 0);
		this.powerBody.setLinearVelocity(speed);
		this.powerBody.setActive(true);
		this.alive = true;
		this.type = type;
	}
	public void destroy(){
		this.alive = false;
	}
	public void activate(){
		this.activate = true;
	}
	
	@Override
	public void reset() {
		this.powerBody = null;
		this.alive = false;
	}
	
	public void update(float time){
		Vector2 speed = powerBody.getLinearVelocity();
		speed.y = speed.y + time * -400;
		powerBody.setLinearVelocity(speed);
	}

}

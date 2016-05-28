package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Michael on 2016-04-11.
 */
public class Ball implements Poolable{
	public enum BallSpeed{
		Slowest(300),
		Slow(400),
		Normal(500),
		Fast(750), 
		Fastest(1100);
		private int speed;
		private BallSpeed(int speed){
			this.speed = speed;
		}
		public int getVal(){
			return this.speed;
		}
	}
	public static BallSpeed currentSpeed;
	public static float MIN_ANGLE = 60;
    public static float BALL_RADIUS = 8f;
    public static float BALL_START_Y = 95;
    public Body ballBody;
    public boolean alive;
    
    public static float getMinYSpeed(){
    	switch(currentSpeed){
		case Fast:
			return (float) (350 * Math.sqrt(2));
		case Fastest:
			return (float) (300 * Math.sqrt(2));
		case Normal:
			return (float) (250 * Math.sqrt(2));
		case Slow:
			return (float) (200 * Math.sqrt(2));
		case Slowest:
			return (float) (150 * Math.sqrt(2));
		default:
			return 2;
    	}
    }
    
    public static void increaseSpeed(){
    	switch(currentSpeed){
		case Fast:
			currentSpeed = BallSpeed.Fastest;
			break;
		case Fastest:
			currentSpeed = BallSpeed.Fastest;
			break;
		case Normal:
			currentSpeed = BallSpeed.Fast;
			break;
		case Slow:
			currentSpeed = BallSpeed.Normal;
			break;
		case Slowest:
			currentSpeed = BallSpeed.Slow;
			break;
		default:
			break;
    	}
    }
    public static void decreaseSpeed(){
    	switch(currentSpeed){
		case Fast:
			currentSpeed = BallSpeed.Normal;
			break;
		case Fastest:
			currentSpeed = BallSpeed.Fast;
			break;
		case Normal:
			currentSpeed = BallSpeed.Slow;
			break;
		case Slow:
			currentSpeed = BallSpeed.Slowest;
			break;
		case Slowest:
			currentSpeed = BallSpeed.Slowest;
			break;
		default:
			break;
    	}
    }
    
	public void setBody(Body body) {
		this.ballBody = body;
		this.ballBody.setActive(false);
		this.alive = true;
	}

	@Override
	public void reset() {
		this.alive = false;
		this.ballBody = null;
	}
	
	public void init(Vector2 position, Vector2 speed){
		this.ballBody.setTransform(position, 0);
		this.ballBody.setLinearVelocity(speed);
		this.ballBody.setActive(true);
		this.alive = true;
	}
	public void destroy(){
		this.alive = false;
	}
}

package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Michael on 2016-04-11.
 */
public class Ball implements Poolable{
	public static float MAX_ANGEL = 60;
    public static float BALL_RADIUS = 8f;
    public static float BALL_START_Y = 95;
    public Body ballBody;
    public boolean alive;
    
	public void setBody(Body body) {
		ballBody = body;
		ballBody.setActive(false);
		alive = false;
	}

	@Override
	public void reset() {
		alive = false;
		ballBody.setActive(false);
		ballBody.setLinearVelocity(0,0);
	}
	
	public void init(Vector2 position, Vector2 speed){
		ballBody.setTransform(position, 0);
		ballBody.setLinearVelocity(speed);
		ballBody.setActive(true);
		alive = true;
	}
	public void destroy(){
		alive = false;
	}
}

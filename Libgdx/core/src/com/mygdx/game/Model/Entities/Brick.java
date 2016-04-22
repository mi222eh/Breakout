package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by Michael on 2016-04-18.
 */
public class Brick implements Poolable{
    public static float BRICK_WIDTH = 30;
    public static float BRICK_HEIGHT = 15;
    public Body brickBody;
    public int ID;

    public Brick(Body body){
        brickBody = body;
    }
    public Brick() {
		// TODO Auto-generated constructor stub
	}
    
    public Brick(int numberofBricksTotal) {
		ID = numberofBricksTotal;
	}
	public void setBody(Body body){
    	brickBody = body;
    }
    
    
    
	public void init(){

    }
	@Override
	public void reset() {
		
	}
}

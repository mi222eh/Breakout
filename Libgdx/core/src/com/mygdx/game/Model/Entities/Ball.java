package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Michael on 2016-04-11.
 */
public class Ball {
    public static float BALL_RADIUS = 8f;
    public Body ballBody;
    
	public void setBody(Body body) {
		ballBody = body;
	}
}

package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Michael on 2016-04-11.
 */
public class Ball {
    public Body ballBody;
    public Ball(Body body){
        ballBody = body;
    }
}
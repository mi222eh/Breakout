package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Michael on 2016-04-18.
 */
public class Brick {
    public static float BRICK_WIDTH = 30;
    public static float BRICK_HEIGHT = 15;
    public Body brickBody;

    public Brick(Body body){
        brickBody = body;
    }
    public void init(){

    }
}

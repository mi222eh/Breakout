package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Michael on 2016-04-11.
 */
public class Player {
    public Body playerBody;
    public Player(Body body) {
        playerBody = body;
    }
}

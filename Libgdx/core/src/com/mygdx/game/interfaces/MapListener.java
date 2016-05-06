package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface MapListener {
	void createBrick(Vector2 position, int type);
	void destroyBrick(Body brickBody);
}

package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick.BrickType;

public interface MapCreateMenuListener {
	void placeBrick(Vector2 position, BrickType type);
}

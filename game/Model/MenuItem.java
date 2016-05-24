package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick.BrickType;

public class MenuItem {
	public BrickType brickType;
	public Vector2 position;
	
	public MenuItem(BrickType type, Vector2 position){
		this.brickType = type;
		this.position = position;
	}
	
	public boolean isWithinButton(Vector2 mousePosition){
		float fromX = position.x - (MapCreateModel.ItemMenuWidth / 2);
		float toX = position.x + (MapCreateModel.ItemMenuWidth / 2);
		float fromY = position.y - (MapCreateModel.ItemMenuHeight / 2);
		float toY = position.y + (MapCreateModel.ItemMenuHeight / 2);
		
		return (mousePosition.x >= fromX && mousePosition.x <= toX && mousePosition.y >= fromY && mousePosition.y <= toY);
	}
}

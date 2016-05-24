package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.MapCreateMenuListener;

public class MapCreateModel implements MapCreateMenuListener{
	public static float ItemMenuWidth = 75;
	public static float ItemMenuHeight = 30;
	public static float ItemMenuPadTop = 15;
	public static float MinHeight = 200;
	
	public MapCreate mapCreate;
	public MapCreateMenu menu;
	
	public boolean isClicking;
	public Vector2 mousePosition;
	
	public MapCreateModel(){
		mapCreate = new MapCreate();
		isClicking = false;
		mousePosition = new Vector2(0,0);
		menu = new MapCreateMenu();
		menu.setListener(this);
	}
	
	public void init(){
		mapCreate.bricks.clear();
	}
	
	public void clicks(boolean clicks){
		this.isClicking = clicks;
	}
	
	public void setMousePosition(Vector2 mousePosition){
		this.mousePosition = mousePosition;
	}
	
	public void update(){
		menu.setClicking(isClicking, mousePosition);
	}

	@Override
	public void placeBrick(Vector2 position, BrickType type) {
		if(position.y >= MinHeight + (Brick.BRICK_HEIGHT / 2) && position.y <= BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH - (Brick.BRICK_HEIGHT / 2)){
			if(position.x >= Map.WALL_WIDTH + (Brick.BRICK_WIDTH / 2) && position.x <= BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - (Brick.BRICK_WIDTH / 2)){
				
			}
		}
	}
}

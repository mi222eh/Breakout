package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.MapCreate.MapCreate;

public class MapCreateModel {
	public static float ItemMenuWidth = 75;
	public static float ItemMenuHeight = 30;
	public static float ItemMenuPadTop = 0;
	
	public MapCreate mapCreate;
	public MapCreateMenu menu;
	
	public boolean isClicking;
	public Vector2 mousePosition;
	
	public MapCreateModel(){
		mapCreate = new MapCreate();
		isClicking = false;
		mousePosition = new Vector2(0,0);
		menu = new MapCreateMenu();
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
}

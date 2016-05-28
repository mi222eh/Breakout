package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.BrickCreate;
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
	
	public boolean clicks;
	public boolean isClicking;
	public boolean hasSelected;
	public BrickCreate selectedBrick;
	public Vector2 mouseBeginPosition;
	public Vector2 mousePosition;
	
	public MapCreateModel(){
		this.mapCreate = new MapCreate();
		this.isClicking = false;
		this.clicks = false;
		this.mousePosition = new Vector2(0,0);
		this.menu = new MapCreateMenu();
		this.menu.setListener(this);
		this.hasSelected = false;
	}
	
	public void init(){
		this.mapCreate.bricks.clear();
	}
	
	public void clicks(boolean clicks){
		this.clicks = clicks;
	}
	
	public void setMousePosition(Vector2 mousePosition){
		this.mousePosition = mousePosition;
	}
	
	public void update(){
		this.menu.setClicking(this.isClicking, this.mousePosition);
		this.updateClicking();
	}
	private void placeExistingBrick(){
		if(this.isWithinWalls(new Vector2(this.selectedBrick.x, this.selectedBrick.y))){
			boolean canPlace = true;
			for (int i = 0; i < this.mapCreate.bricks.size(); i++) {
				BrickCreate brick = this.mapCreate.bricks.get(i);
				if(this.isWithin(new Vector2(brick.x, brick.y), new Vector2(this.selectedBrick.x, this.selectedBrick.y))){
					canPlace = false;
				}
			}
			if(canPlace){
				this.mapCreate.bricks.add(this.selectedBrick);
			}
		}
		this.selectedBrick = null;
		this.hasSelected = false;
	}
	private void updateClicking(){
		if(this.clicks){
			if(!this.isClicking){
				BrickCreate brick = this.getBrickAtCurrentMousePosition();
				if(brick != null){
					this.hasSelected = true;
					this.selectedBrick = brick;
					this.mouseBeginPosition = this.mousePosition;
				}
			}
			if(this.hasSelected){
				float differenseX = this.mousePosition.x - this.mouseBeginPosition.x;
				float differenseY = this.mousePosition.y - this.mouseBeginPosition.y;
				this.selectedBrick.x += differenseX;
				this.selectedBrick.y += differenseY;
				this.mouseBeginPosition = this.mousePosition;
			}
		}
		else{
			if(this.hasSelected){
				//PLACE BRICK
				this.placeExistingBrick();
			}
		}
		this.isClicking = this.clicks;
	}
	
	private BrickCreate getBrickAtCurrentMousePosition(){
		BrickCreate brick = null;
		for (int i = 0; i < this.mapCreate.bricks.size(); i++) {
			BrickCreate existingBrick = this.mapCreate.bricks.get(i);
			if(this.mousePosition.x <= existingBrick.x + Brick.BRICK_WIDTH / 2 && this.mousePosition.x >= existingBrick.x - Brick.BRICK_WIDTH / 2 &&
					this.mousePosition.y <= existingBrick.y + Brick.BRICK_HEIGHT / 2 && this.mousePosition.y >= existingBrick.y - Brick.BRICK_HEIGHT / 2){
				brick = existingBrick;
				this.mapCreate.bricks.remove(i);
				return brick;
				}
			}
		return brick;
		}
	private boolean isWithinWalls(Vector2 position){
		if(position.y >= MinHeight + (Brick.BRICK_HEIGHT / 2) && position.y <= BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH - (Brick.BRICK_HEIGHT / 2)){
			if(position.x >= Map.WALL_WIDTH + (Brick.BRICK_WIDTH / 2) && position.x <= BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - (Brick.BRICK_WIDTH / 2)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void placeBrick(Vector2 position, BrickType type) {
		boolean canCreate = true;
		if(this.isWithinWalls(position)){
				for (int i = 0; i < this.mapCreate.bricks.size(); i++) {
					BrickCreate brick = this.mapCreate.bricks.get(i);
					if(this.isWithin(new Vector2(brick.x, brick.y), position)){
						canCreate = false;
					}
				}
				if(canCreate){
					int numberType = 0;
					switch(type){
					case invisible:
						numberType = BreakoutSettings.BRICK_INVISIBLE;
						break;
					case invurnerable:
						numberType = BreakoutSettings.BRICK_INVURNERABLE;
						break;
					case normal1:
						numberType = BreakoutSettings.BRICK_NORMAL1;
						break;
					case normal2:
						numberType = BreakoutSettings.BRICK_NORMAL2;
						break;
					case normal3:
						numberType = BreakoutSettings.BRICK_NORMAL3;
						break;
					case steel1:
						numberType = BreakoutSettings.BRICK_STEEL1;
						break;
					case steel2:
						numberType = BreakoutSettings.BRICK_STEEL2;
						break;
					case steel3:
						numberType = BreakoutSettings.BRICK_STEEL3;
						break;
					case undefined:
						break;
					default:
						break;
					}
					this.mapCreate.bricks.add(new BrickCreate(position.x, position.y, numberType));
				}	
			}
		}
			
			

	private boolean isWithin(Vector2 existingPos, Vector2 newPos){
		if(newPos.x >= existingPos.x + Brick.BRICK_WIDTH || newPos.x <= existingPos.x - Brick.BRICK_WIDTH ||
				newPos.y >= existingPos.y + Brick.BRICK_HEIGHT || newPos.y <= existingPos.y - Brick.BRICK_HEIGHT){
			return false;
		}
		return true;
	}
}

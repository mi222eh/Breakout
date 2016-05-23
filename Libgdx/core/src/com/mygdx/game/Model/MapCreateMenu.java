package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Settings.BreakoutSettings;

public class MapCreateMenu {
	
	public MenuItem Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invisible, Invurnerable;
	public boolean hasSelected;
	public boolean isClicking;
	public BrickType selectedBrick;
	public Vector2 selectedPosition;
	
	public MapCreateMenu(){
		hasSelected = false;
		isClicking = false;
		float y = BreakoutSettings.SCREEN_HEIGHT - MapCreateModel.ItemMenuHeight;
		float x = 200;
		float nr = 1;
		Normal1 = new MenuItem(BrickType.normal1, new Vector2(x, y));
		nr++;
		Normal2 = new MenuItem(BrickType.normal2, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Normal3 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Steel1 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Steel2 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Steel3 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Invisible = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
		nr++;
		Invurnerable = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr, y));
	}
	
	public void setClicking(boolean click, Vector2 position){
		if(click){
			if(!isClicking){
				if(Normal1.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.normal1;
				}
				else if(Normal2.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.normal2;
				}
				else if(Normal3.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.normal3;
				}
				else if(Steel1.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.steel1;
				}
				else if(Steel2.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.steel2;
				}
				else if(Steel3.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.steel3;
				}
				else if(Invisible.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.invisible;
				}
				else if(Invurnerable.isWithinButton(position)){
					hasSelected = true;
					selectedBrick = BrickType.invurnerable;
				}
			}
		}
		else{
			if (hasSelected) {
				//Place Brick
				hasSelected = false;
			}
		}
		isClicking = click;
	}

}

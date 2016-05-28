package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.MapCreateMenuListener;

public class MapCreateMenu {
	MapCreateMenuListener listener;
	public MenuItem Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invisible, Invurnerable;
	public boolean hasSelected;
	public boolean isClicking;
	public BrickType selectedBrick;
	public Vector2 selectedPosition;
	
	public MapCreateMenu(){
		this.hasSelected = false;
		this.isClicking = false;
		float y = BreakoutSettings.SCREEN_HEIGHT - MapCreateModel.ItemMenuPadTop;
		float x = 200;
		float nr = 1;
		float pad = 10;
		this.Normal1 = new MenuItem(BrickType.normal1, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Normal2 = new MenuItem(BrickType.normal2, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Normal3 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Steel1 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Steel2 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Steel3 = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Invisible = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
		nr++;
		this.Invurnerable = new MenuItem(BrickType.normal3, new Vector2(x + MapCreateModel.ItemMenuWidth * nr + pad * nr, y));
	}
	
	public void setListener(MapCreateMenuListener list){
		this.listener = list;
	}
	
	public void setClicking(boolean click, Vector2 position){
		if(click){
			if(!this.isClicking){
				if(this.Normal1.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.normal1;
				}
				else if(this.Normal2.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.normal2;
				}
				else if(this.Normal3.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.normal3;
				}
				else if(this.Steel1.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.steel1;
				}
				else if(this.Steel2.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.steel2;
				}
				else if(this.Steel3.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.steel3;
				}
				else if(this.Invisible.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.invisible;
				}
				else if(this.Invurnerable.isWithinButton(position)){
					this.hasSelected = true;
					this.selectedBrick = BrickType.invurnerable;
				}
			}
			if(this.hasSelected){
				this.selectedPosition = position;
			}
		}
		else{
			if (this.hasSelected) {
				//Place Brick
				this.listener.placeBrick(this.selectedPosition, this.selectedBrick);
				this.selectedBrick = BrickType.undefined;
				this.hasSelected = false;
			}
		}
		this.isClicking = click;
	}

}

package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.MapCreateMenu;
import com.mygdx.game.Model.MapCreateModel;
import com.mygdx.game.Model.MenuItem;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.BrickCreate;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Settings.BreakoutSettings;

public class LevelCreatorRender {
	Vector2 mousePos;
	public MapCreateModel mapCreateModel;
	MapCreateMenu menu;
	public MapCreate mapCreate;
	
	public Sprite Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable, Invisible, Hover, Selected;
	
	public LevelCreatorRender(){
		this.mousePos = new Vector2(0,0);
		this.Normal1 = MenuTextures.MenuNormal1;
		this.Normal2 = MenuTextures.MenuNormal2;
		this.Normal3 = MenuTextures.MenuNormal3;
		this.Steel1 = MenuTextures.MenuSteel1;
		this.Steel2 = MenuTextures.MenuSteel2;
		this.Steel3 = MenuTextures.MenuSteel3;
		this.Invisible = MenuTextures.MenuInvisible;
		this.Invurnerable = MenuTextures.MenuInvurnerable;
		this.Hover = MenuTextures.Hover;
		this.Selected = MenuTextures.Selected;
	}
	
	public void setModel(MapCreateModel model){
		this.mapCreateModel = model;
		this.menu = this.mapCreateModel.menu;
		this.mapCreate = model.mapCreate;
	}
	public void setMouse(Vector2 pos){
		this.mousePos = pos;
	}
	
	public void render(SpriteBatch batch){
		this.renderWalls(batch);
		this.renderMenu(batch);
		this.renderMenuEffects(batch);
		this.renderPlacedBricks(batch);
		this.renderSelectedEffects(batch);
		this.renderSelected(batch);
		}
	private void renderSelected(SpriteBatch batch){
		if (this.mapCreateModel.hasSelected){
			BrickCreate brick = this.mapCreateModel.selectedBrick;
			Sprite currentSprite = null;
			switch (brick.type){
			case BreakoutSettings.BRICK_INVISIBLE:
				currentSprite = MapTextures.Invisible;
				break;
			case BreakoutSettings.BRICK_INVURNERABLE:
				currentSprite = MapTextures.Invurnerable;
				break;
			case BreakoutSettings.BRICK_NORMAL1:
				currentSprite = MapTextures.Normal1;
				break;
			case BreakoutSettings.BRICK_NORMAL2:
				currentSprite = MapTextures.Normal2;
				break;
			case BreakoutSettings.BRICK_NORMAL3:
				currentSprite = MapTextures.Normal3;
				break;
			case BreakoutSettings.BRICK_STEEL1:
				currentSprite = MapTextures.Steel1;
				break;
			case BreakoutSettings.BRICK_STEEL2:
				currentSprite = MapTextures.Steel2;
				break;
			case BreakoutSettings.BRICK_STEEL3:
				currentSprite = MapTextures.Steel3;
				break;
			}
			if(currentSprite != null){
				float x = brick.x - Brick.BRICK_WIDTH / 2;
				float y = brick.y - Brick.BRICK_HEIGHT / 2;
				currentSprite.setPosition(x, y);
				currentSprite.draw(batch);
			}
		}
	}
	
	private void renderPlacedBricks(SpriteBatch batch){
		for (int i = 0; i < this.mapCreateModel.mapCreate.bricks.size(); i++) {
			BrickCreate brick = this.mapCreateModel.mapCreate.bricks.get(i);
			Sprite currentSprite = null;
			switch (brick.type){
			case BreakoutSettings.BRICK_INVISIBLE:
				currentSprite = MapTextures.Invisible;
				break;
			case BreakoutSettings.BRICK_INVURNERABLE:
				currentSprite = MapTextures.Invurnerable;
				break;
			case BreakoutSettings.BRICK_NORMAL1:
				currentSprite = MapTextures.Normal1;
				break;
			case BreakoutSettings.BRICK_NORMAL2:
				currentSprite = MapTextures.Normal2;
				break;
			case BreakoutSettings.BRICK_NORMAL3:
				currentSprite = MapTextures.Normal3;
				break;
			case BreakoutSettings.BRICK_STEEL1:
				currentSprite = MapTextures.Steel1;
				break;
			case BreakoutSettings.BRICK_STEEL2:
				currentSprite = MapTextures.Steel2;
				break;
			case BreakoutSettings.BRICK_STEEL3:
				currentSprite = MapTextures.Steel3;
				break;
			}
			if(currentSprite != null){
				float x = brick.x - Brick.BRICK_WIDTH / 2;
				float y = brick.y - Brick.BRICK_HEIGHT / 2;
				currentSprite.setPosition(x, y);
				currentSprite.draw(batch);
			}
		}
	}
	private void renderSelectedEffects(SpriteBatch batch){
		if(this.mapCreateModel.menu.hasSelected){
			Sprite currentSprite = null;
			switch(this.mapCreateModel.menu.selectedBrick){
			case invisible:
				currentSprite = MapTextures.Invisible;
				break;
			case invurnerable:
				currentSprite = MapTextures.Invurnerable;
				break;
			case normal1:
				currentSprite = MapTextures.Normal1;
				break;
			case normal2:
				currentSprite = MapTextures.Normal2;
				break;
			case normal3:
				currentSprite = MapTextures.Normal3;
				break;
			case steel1:
				currentSprite = MapTextures.Steel1;
				break;
			case steel2:
				currentSprite = MapTextures.Steel2;
				break;
			case steel3:
				currentSprite = MapTextures.Steel3;
				break;
			case undefined:
				break;
			default:
				break;
			}
			if(currentSprite != null){
				Vector2 pos = this.mapCreateModel.menu.selectedPosition;
				float x = pos.x - Brick.BRICK_WIDTH / 2;
				float y = pos.y - Brick.BRICK_HEIGHT / 2;
				currentSprite.setPosition(x, y);
				currentSprite.draw(batch);
			}
		}
	}
	private void renderMenuEffects(SpriteBatch batch){
		if(this.menu.Normal1.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Normal1);
		}
		else if(this.menu.Normal2.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Normal2);
		}
		else if(this.menu.Normal3.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Normal3);
		}
		else if(this.menu.Steel1.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Steel1);
		}
		else if(this.menu.Steel2.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Steel2);
		}
		else if(this.menu.Steel3.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Steel3);
		}
		else if(this.menu.Invisible.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Invisible);
		}
		else if(this.menu.Invurnerable.isWithinButton(this.mousePos)){
			this.renderHover(batch, this.menu.Invurnerable);
		}
	}
	
	private void renderHover(SpriteBatch batch, MenuItem menu){
		Vector2 pos = this.getRenderPosition(menu.position);
		this.Hover.setPosition(pos.x, pos.y);
		this.Hover.draw(batch);
	}
	
	private Vector2 getRenderPosition(Vector2 pos){
		Vector2 newPos = new Vector2(pos);
		newPos.x = pos.x - MapCreateModel.ItemMenuWidth / 2;
		newPos.y = pos.y - MapCreateModel.ItemMenuHeight / 2;
		
		return newPos;
	}
	
	private void renderMenu(SpriteBatch batch){
		Vector2 pos = this.getRenderPosition(this.mapCreateModel.menu.Normal1.position);
		this.Normal1.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Normal2.position);
		this.Normal2.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Normal3.position);
		this.Normal3.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Steel1.position);
		this.Steel1.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Steel2.position);
		this.Steel2.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Steel3.position);
		this.Steel3.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Invurnerable.position);
		this.Invurnerable.setPosition(pos.x, pos.y);
		pos = this.getRenderPosition(this.mapCreateModel.menu.Invisible.position);
		this.Invisible.setPosition(pos.x, pos.y);
		
		this.Normal1.draw(batch);
		this.Normal2.draw(batch);
		this.Normal3.draw(batch);
		this.Steel1.draw(batch);
		this.Steel2.draw(batch);
		this.Steel3.draw(batch);
		this.Invurnerable.draw(batch);
		this.Invisible.draw(batch);
	}
	private void renderWalls(SpriteBatch batch){
		MapTextures.WallSide.setPosition(0, 0);
		MapTextures.WallSide.draw(batch);
		MapTextures.WallSide.setPosition(BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH, 0);
		MapTextures.WallSide.draw(batch);
		MapTextures.WallTop.setPosition(0, BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH);
		MapTextures.WallTop.draw(batch);
	}
}

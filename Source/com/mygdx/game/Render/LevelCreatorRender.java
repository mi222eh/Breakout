package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.MapCreateMenu;
import com.mygdx.game.Model.MapCreateModel;
import com.mygdx.game.Model.MenuItem;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Settings.BreakoutSettings;

public class LevelCreatorRender {
	Vector2 mousePos;
	public MapCreateModel mapCreateModel;
	MapCreateMenu menu;
	public MapCreate mapCreate;
	
	public Sprite Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable, Invisible, Hover, Selected;
	
	public LevelCreatorRender(){
		mousePos = new Vector2(0,0);
		Normal1 = MenuTextures.MenuNormal1;
		Normal2 = MenuTextures.MenuNormal2;
		Normal3 = MenuTextures.MenuNormal3;
		Steel1 = MenuTextures.MenuSteel1;
		Steel2 = MenuTextures.MenuSteel2;
		Steel3 = MenuTextures.MenuSteel3;
		Invisible = MenuTextures.MenuInvisible;
		Invurnerable = MenuTextures.MenuInvurnerable;
		Hover = MenuTextures.Hover;
		Selected = MenuTextures.Selected;
	}
	
	public void setModel(MapCreateModel model){
		mapCreateModel = model;
		menu = mapCreateModel.menu;
		mapCreate = model.mapCreate;
	}
	public void setMouse(Vector2 pos){
		mousePos = pos;
	}
	
	public void render(SpriteBatch batch){
		renderWalls(batch);
		renderMenu(batch);
		renderMenuEffects(batch);
		}
	private void renderMenuEffects(SpriteBatch batch){
		if(menu.Normal1.isWithinButton(mousePos)){
			renderHover(batch, menu.Normal1);
		}
		else if(menu.Normal2.isWithinButton(mousePos)){
			renderHover(batch, menu.Normal2);
		}
		else if(menu.Normal3.isWithinButton(mousePos)){
			renderHover(batch, menu.Normal3);
		}
		else if(menu.Steel1.isWithinButton(mousePos)){
			renderHover(batch, menu.Steel1);
		}
		else if(menu.Steel2.isWithinButton(mousePos)){
			renderHover(batch, menu.Steel2);
		}
		else if(menu.Steel3.isWithinButton(mousePos)){
			renderHover(batch, menu.Steel3);
		}
		else if(menu.Invisible.isWithinButton(mousePos)){
			renderHover(batch, menu.Invisible);
		}
		else if(menu.Invurnerable.isWithinButton(mousePos)){
			renderHover(batch, menu.Invurnerable);
		}
	}
	
	private void renderHover(SpriteBatch batch, MenuItem menu){
		Vector2 pos = getRenderPosition(menu.position);
		Hover.setPosition(pos.x, pos.y);
		Hover.draw(batch);
	}
	
	private Vector2 getRenderPosition(Vector2 pos){
		Vector2 newPos = new Vector2(pos);
		newPos.x = pos.x - MapCreateModel.ItemMenuWidth / 2;
		newPos.y = pos.y - MapCreateModel.ItemMenuHeight / 2;
		
		return newPos;
	}
	
	private void renderMenu(SpriteBatch batch){
		Vector2 pos = getRenderPosition(mapCreateModel.menu.Normal1.position);
		Normal1.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Normal2.position);
		Normal2.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Normal3.position);
		Normal3.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Steel1.position);
		Steel1.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Steel2.position);
		Steel2.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Steel3.position);
		Steel3.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Invurnerable.position);
		Invurnerable.setPosition(pos.x, pos.y);
		pos = getRenderPosition(mapCreateModel.menu.Invisible.position);
		Invisible.setPosition(pos.x, pos.y);
		
		Normal1.draw(batch);
		Normal2.draw(batch);
		Normal3.draw(batch);
		Steel1.draw(batch);
		Steel2.draw(batch);
		Steel3.draw(batch);
		Invurnerable.draw(batch);
		Invisible.draw(batch);
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

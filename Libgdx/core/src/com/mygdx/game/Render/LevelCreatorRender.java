package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Model.MapCreateModel;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Settings.BreakoutSettings;

public class LevelCreatorRender {
	public MapCreateModel mapCreateModel;
	public MapCreate mapCreate;
	
	public Sprite Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable, Invisible, Hover, Selected;
	
	public void LevelCreateRender(){
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
		mapCreate = model.mapCreate;
	}
	
	public void render(SpriteBatch batch){
		renderMenu(batch);
		renderWalls(batch);
	}
	
	private void renderMenu(SpriteBatch batch){
		Normal1.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Normal2.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Normal3.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Steel1.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Steel2.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Steel3.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Invurnerable.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		Invisible.setPosition(mapCreateModel.menu.Normal1.position.x, mapCreateModel.menu.Normal1.position.y);
		
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

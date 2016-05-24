package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Model.MapCreateModel;
import com.mygdx.game.Render.LevelCreatorRender;

public class CreateMapScreen implements Screen{
	
	private BreakoutGame Game;
	
	public MapCreateModel createModel;
	public LevelCreatorRender creatorRender;
	
	public CreateMapScreen(BreakoutGame game){
		Game = game;
		createModel = new MapCreateModel();
		creatorRender = new LevelCreatorRender();
		creatorRender.setModel(createModel);
	}
	
	public void init(){
		createModel.init();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Game.batch.setProjectionMatrix(Game.camera.combined);
		Vector2 mousePosition = new Vector2();
		mousePosition.x = Gdx.input.getX();
		mousePosition.y = Gdx.input.getY();
		Vector3 mouseGamePosition = Game.camera.unproject(new Vector3(mousePosition.x, mousePosition.y, 0));
		mousePosition.x = mouseGamePosition.x;
		mousePosition.y = mouseGamePosition.y;
		createModel.setMousePosition(mousePosition);
		createModel.clicks(Gdx.input.isTouched());
		creatorRender.setMouse(mousePosition);
		
		createModel.update();
		
		Game.batch.begin();
		creatorRender.render(Game.batch);
		Game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

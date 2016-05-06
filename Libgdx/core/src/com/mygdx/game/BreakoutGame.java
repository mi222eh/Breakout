package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Handlers.ScreenHandler;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Settings.BreakoutSettings;

public class BreakoutGame extends Game {
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public ScreenHandler screenHandler;
	
	@Override
	public void create () {
		screenHandler = new ScreenHandler();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        camera.update();
        setScreen(screenHandler.getScreen(ScreenType.GameScreen,this));
	}
}

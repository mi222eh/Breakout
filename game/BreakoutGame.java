package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Settings.BreakoutSettings;

public class BreakoutGame extends Game {
    public OrthographicCamera camera;
    public SpriteBatch batch;
	
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        camera.update();

        setScreen(new GameScreen(this));
	}
}

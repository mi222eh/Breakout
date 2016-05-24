package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Handlers.ScreenHandler;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Render.MapTextures;
import com.mygdx.game.Render.MenuStyle;
import com.mygdx.game.Render.MenuTextures;
import com.mygdx.game.Settings.BreakoutSettings;
import com.sun.prism.image.ViewPort;

public class BreakoutGame extends Game {
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public ScreenHandler screenHandler;
    public Viewport viewport;
	
	@Override
	public void create () {
		
		MapTextures.loadTextures();
		MenuStyle.loadStyle();
		MenuTextures.loadTextures();
		
		float aspectRatio = (float)Gdx.graphics.getHeight()/ (float)Gdx.graphics.getWidth();
		
		Gdx.gl20.glClearColor(0.5f, 0.5f, 0.5f, 0);
		screenHandler = new ScreenHandler();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        camera.update();
        setScreen(screenHandler.getScreen(ScreenType.MenuScreen,this));
	}
}

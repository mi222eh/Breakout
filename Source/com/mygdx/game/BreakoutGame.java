package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Handlers.ScreenHandler;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Render.MapTextures;
import com.mygdx.game.Render.MenuStyle;
import com.mygdx.game.Render.MenuTextures;
import com.mygdx.game.Render.SoundHandler;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.GameSoundListener;

public class BreakoutGame extends Game {
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public ScreenHandler screenHandler;
    public Viewport viewport;
    public GameSoundListener soundListener;
	
	@Override
	public void create () {
		
		soundListener = new SoundHandler();
		
		MapTextures.loadTextures();
		MenuStyle.loadStyle();
		MenuTextures.loadTextures();
		
		//float aspectRatio = (float)Gdx.graphics.getHeight()/ (float)Gdx.graphics.getWidth();
		
		Gdx.gl20.glClearColor(0.5f, 0.5f, 0.5f, 0);
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT, this.camera);
        this.viewport.apply();

        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(this.camera.combined);

        this.camera.update();
		this.screenHandler = new ScreenHandler(this);
        this.setScreen(this.screenHandler.getScreen(ScreenType.MenuScreen));
	}
}

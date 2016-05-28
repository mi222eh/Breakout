package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Model.GameStage;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Render.MapRender;
import com.mygdx.game.Render.MenuStyle;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.GameSoundListener;

/**
 * Created by Michael on 2016-04-10.
 */
public class GameScreen implements Screen{
    private BreakoutGame game;
    private float accumulator = 0f;
    GameStage gameStage;
    MapRender mapRender;
    
    String JsonMap;
    MapCreate map;
    private int level;
    private boolean loadTmx;
    private boolean loadObject;
    private boolean loadJson;
    
    private Screen returnScreen;
    private boolean customReturn;
    
    private TextButtonStyle style, inGameStyle;
    private Stage inGameStage, confirmExit, win, lose;

    public GameScreen(BreakoutGame breakoutGame) {
    	this.customReturn = false;
    	this.loadTmx = false;
    	this.loadObject = false;
    	this.loadJson = false;
        this.game = breakoutGame;
        this.gameStage = new GameStage();
        this.mapRender = new MapRender();
        this.mapRender.init();
        this.mapRender.setBalls(this.gameStage.Balls);
        this.mapRender.setBricks(this.gameStage.map.bricks);
        this.mapRender.setInvurnerableBrick(this.gameStage.map.invulnerableBricks);
        this.mapRender.setPowerups(this.gameStage.Powerups);
        this.mapRender.setPlayer(this.gameStage.player);
        this.style = MenuStyle.style;
        this.inGameStyle = MenuStyle.InGameStyle;
        this.createStage();
        this.gameStage.contactListener.soundListener = game.soundListener;
        this.gameStage.soundListener = game.soundListener;
        this.gameStage.map.soundListener = game.soundListener;
        Brick.soundListener = game.soundListener;
    }
    
    private void createStage(){
    	this.inGameStage = new Stage(this.game.viewport, this.game.batch);
    	TextButton button = new TextButton("", this.inGameStyle);
    	button.setWidth(Map.WALL_WIDTH);
    	button.setHeight(Map.WALL_WIDTH);
    	button.setPosition(BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH, BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH);
    	button.addListener(new ClickListener(){
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			GameScreen.this.dispose();
    			if(GameScreen.this.customReturn){
    				GameScreen.this.game.setScreen(GameScreen.this.returnScreen);
    				GameScreen.this.customReturn = false;
    			}
    			else{
    				MainMenuScreen screen = (MainMenuScreen) GameScreen.this.game.screenHandler.getScreen(ScreenType.MenuScreen);
    				screen.setToLevelScreen();
    				GameScreen.this.game.setScreen(screen);
    			}
    			game.soundListener.ButtonSound();
    		}
    	});
    	this.inGameStage.addActor(button);
    }
    
    public void setCustomReturnScreen(Screen screen){
    	this.returnScreen = screen;
    	this.customReturn = true;
    }
    
    public void loadJsonMap(String json){
    	this.loadJson = true;
    	this.JsonMap = json;
    }
    
    public void loadTmxMap(int level){
    	this.level = level;
    	this.loadTmx = true;
    }
    
    public void loadObject(MapCreate map){
    	this.map = map;
    	this.loadObject = true;
    }
    public void init(){
    	Gdx.input.setInputProcessor(this.inGameStage);
    }

    @Override
    public void show() {

    	this.gameStage.init();
    	this.gameStage.map.reset();
    	if(this.loadTmx){
        	this.gameStage.map.loadMapFromTmxFile(this.level);
        	this.loadTmx = false;
    	}
    	if(this.loadObject){
    		this.gameStage.map.loadObject(this.map);
    		this.loadObject = false;
    	}
    	if(this.loadJson){
    		this.loadJson = false;
    		this.gameStage.map.loadJsonMap(this.JsonMap);
    	}
    	Gdx.input.setInputProcessor(this.inGameStage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        
        this.inGameStage.act(delta);

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePos = this.game.camera.unproject(mousePos);
        this.gameStage.setPlayerPosition(mousePos.x);
        
        if(Gdx.input.isKeyJustPressed(Keys.A)){
        	this.gameStage.makePlayerBigger();
        }
        if(Gdx.input.isKeyJustPressed(Keys.D)){
        	this.gameStage.MakePlayerSmaller();
        }
        if(Gdx.input.isButtonPressed(Buttons.LEFT)){
        	this.gameStage.start();
        }
        if(Gdx.input.isKeyJustPressed(Keys.W)){
        	this.gameStage.accelerateBalls();
        }
        if(Gdx.input.isKeyJustPressed(Keys.S)){
        	this.gameStage.slowDownBalls();
        }
        if(Gdx.input.isKeyJustPressed(Keys.Q)){
        	this.gameStage.duplicateBalls();
        }


        float frameTime = Math.min(delta, BreakoutSettings.MIN_TIME);
        this.accumulator += frameTime;
        while(this.accumulator >= BreakoutSettings.TIME_STEP){
            this.gameStage.update(BreakoutSettings.TIME_STEP);
            this.accumulator -= BreakoutSettings.TIME_STEP;
        }
        
        //gameStage.debugRender(game.camera);
        this.game.batch.begin();
        this.mapRender.render(this.game.batch);
        this.game.batch.end();
        this.inGameStage.draw();

    }

    @Override
    public void resize(int width, int height) {
    	this.inGameStage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    	Gdx.input.setInputProcessor(null);
    }
}

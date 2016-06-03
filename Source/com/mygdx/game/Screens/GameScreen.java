package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    
    private boolean paused;
    private boolean hasEnded;
    private boolean hasWon;
    private boolean changed;
    
    private TextButtonStyle style, inGameStyle;
    private Stage inGameStage, endStage, pauseStage;

    public GameScreen(BreakoutGame breakoutGame) {
    	this.changed = false;
    	this.hasEnded = false;
    	this.hasWon = false;
    	this.paused = false;
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
        this.generateEndStage();
        this.generatePauseStage();
        

    }
    
    private void generatePauseStage(){
    	pauseStage = new Stage(game.viewport, game.batch);
    	Table table = new Table();
    	TextButton exitButton = new TextButton("Back to menu", MenuStyle.style);
    	
    	exitButton.addListener(new ClickListener(){
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
    	
    	TextButton resumeButton = new TextButton("Resume", MenuStyle.style);
    	resumeButton.addListener(new ClickListener(){
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			// TODO Auto-generated method stub
    			super.clicked(event, x, y);
    			setPlay();
    			game.soundListener.ButtonSound();
    		}
    	});
    	
    	table.setWidth(BreakoutSettings.SCREEN_WIDTH);
    	table.setHeight(BreakoutSettings.SCREEN_HEIGHT / 2);
    	
    	table.add(resumeButton).width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad).center();
    	table.row();
    	table.add(exitButton).width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad).center();

    	
    	
    	
    	pauseStage.addActor(table);
    }
    
    private void setPlay(){
    	this.paused = false;
    	Gdx.input.setInputProcessor(inGameStage);
    }
    
    private void setPause(){
    	this.paused = true;
    	Gdx.input.setInputProcessor(this.pauseStage);
    }
    
    private void updateEnd(){
    	if(this.hasEnded){
    		if(!this.changed){
    			mapRender.setEnd(hasWon);
    			this.changed = true;
    			Gdx.input.setInputProcessor(endStage);
    		}
    	}
    	else{
    		if(gameStage.hasLost){
    			this.hasEnded = true;
    			this.hasWon = false;
    		}
    		else if(gameStage.hasWon){
    			this.hasEnded = true;
    			this.hasWon = true;
    		}
    	}
    }
    
    private void generateEndStage(){
    	endStage = new Stage(game.viewport, game.batch);
    	Table table = new Table();
    	TextButton exitButton = new TextButton("Back to menu", MenuStyle.style);
    	
    	exitButton.addListener(new ClickListener(){
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			// TODO Auto-generated method stub
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
    	
    	TextButton restartButton = new TextButton("Restart",MenuStyle.style);
    	
    	restartButton.addListener(new ClickListener(){
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			// TODO Auto-generated method stub
    			super.clicked(event, x, y);
    			GameScreen.this.show();
    		}
    	});
    	
    	table.setWidth(BreakoutSettings.SCREEN_WIDTH);
    	table.setHeight(BreakoutSettings.SCREEN_HEIGHT / 2);
    	
    	table.add(exitButton).width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad).center();
    	table.row();
    	table.add(restartButton).width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad).center();
    	endStage.addActor(table);
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
    			game.soundListener.ButtonSound();
    			GameScreen.this.setPause();
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
    	this.loadTmx = false;
    	this.loadObject = false;
    }
    
    public void loadTmxMap(int level){
    	this.level = level;
    	this.loadTmx = true;
    	this.loadJson = false;
    	this.loadObject = false;
    }
    
    public void loadObject(MapCreate map){
    	this.map = map;
    	this.loadObject = true;
    	this.loadTmx = false;
    	this.loadJson = false;
    }
    public void init(){
    	Gdx.input.setInputProcessor(this.inGameStage);
    }

    @Override
    public void show() {
    	this.changed = false;
    	this.hasEnded = false;
    	this.hasWon = false;
    	this.paused = false;
    	this.gameStage.init();
    	this.gameStage.map.reset();
    	this.mapRender.init();
    	if(this.loadTmx){
        	this.gameStage.map.loadMapFromTmxFile(this.level);
    	}
    	if(this.loadObject){
    		this.gameStage.map.loadObject(this.map);
    	}
    	if(this.loadJson){
    		this.gameStage.map.loadJsonMap(this.JsonMap);
    	}
    	Gdx.input.setInputProcessor(this.inGameStage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        

        float frameTime = Math.min(delta, BreakoutSettings.MIN_TIME);
        this.updateEnd();
        if(this.paused){
        	this.pauseStage.act(frameTime);
        }
        else{
            this.accumulator += frameTime;
        	if(this.hasEnded){
        		this.endStage.act(frameTime);
        	}
        	else{
                //<-----------------------------------------IN GAME------------------------------------>
                this.inGameStage.act(delta);
                Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                mousePos = this.game.camera.unproject(mousePos);
                this.gameStage.setPlayerPosition(mousePos.x);
                
                /*<-------------DEBUG BUTTONS------------------------->
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
                */
        	}
            //<------------------------------------------UPDATE------------------------------------------------>

            while(this.accumulator >= BreakoutSettings.TIME_STEP){
                this.gameStage.update(BreakoutSettings.TIME_STEP);
                this.accumulator -= BreakoutSettings.TIME_STEP;
            }
        }


        

        
        //<------------------------------------------DRAW-------------------------------------------------->
        //gameStage.debugRender(game.camera);
        //<-------------------ALWAYS DRAW BEGIN---------------------->
        this.game.batch.begin();
        this.mapRender.render(this.game.batch);
        this.game.batch.end();
        //<-------------------ALWAYS DRAW END------------------------->
        if(this.paused){
        	this.pauseStage.draw();
        }
        else{
        	if(this.hasEnded){
        		this.endStage.draw();
        	}
        	else{
                this.inGameStage.draw();
        	}
        }

    }

    @Override
    public void resize(int width, int height) {
    	this.inGameStage.getViewport().setScreenSize(width, height);
    	this.endStage.getViewport().setScreenSize(width, height);
    	this.pauseStage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {
    	this.setPause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    	this.setPause();
    }

    @Override
    public void dispose() {
    	Gdx.input.setInputProcessor(null);
    }
}

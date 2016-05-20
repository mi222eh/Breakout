package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Settings.BreakoutSettings;

public class MainMenuScreen implements Screen{
	
	private final int MainButtonWidth = 500;
	private final int MainButtonHeight = 75;
	private final int MainButtonPad = 5;
	
	private final int LevelButtonWidth = 235;
	private final int LevelButtonHeight = 75;
	private final int LevelButtonPad = 5;
	
	private boolean isInMainMenu;
	private boolean isInLevelSelect;
	
	
	private Stage mainMenuStage, levelSelectStage;
	private BreakoutGame Game;
	private TextButtonStyle style;
	private Texture buttonUP, buttonDOWN, buttonHOVER;
	private BitmapFont font;
	private TextButton exitButton, soundButton, levelCreateButton, levelButton;
	
	public MainMenuScreen(BreakoutGame game) {
		isInMainMenu = true;
		isInLevelSelect = false;
		Game = game;
		
		font = new BitmapFont(Gdx.files.internal("fonts/gameFont.fnt"));
		
		buttonUP = new Texture("skin/ButtonUP.png");
		buttonDOWN = new Texture("skin/ButtonDOWN.png");
		buttonHOVER = new Texture("skin/ButtonHOVER.png");
		
		style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(buttonUP));
		style.over = new TextureRegionDrawable(new TextureRegion(buttonHOVER)); 
		style.down = new TextureRegionDrawable(new TextureRegion(buttonDOWN)); 
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.font = font;
		
		generateMainMenuStage();
		generateLevelSelectStage();
		
	}
	
	private void makeLevelListener(final int level, TextButton button){
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameScreen gameScreen = (GameScreen)Game.screenHandler.getScreen(ScreenType.GameScreen, Game);
				gameScreen.loadTmxMap(level);
				Game.setScreen(gameScreen);
				dispose();
			}
		});
	}
	
	private void generateLevelSelectStage(){
		levelSelectStage = new Stage(new ScreenViewport(Game.camera), Game.batch);
		Table table = new Table();
		table.setWidth(BreakoutSettings.SCREEN_WIDTH);
		table.setHeight(BreakoutSettings.SCREEN_HEIGHT);
		table.center();
		
		for(int i = 0; i < 10; i++){
			int level = i + 1;
			TextButton button = new TextButton("Map " + level, style);
			table.add(button).width(LevelButtonWidth).height(LevelButtonHeight).pad(LevelButtonPad);
			if(i == 4){
				table.row();
			}
			makeLevelListener(level, button);

		}
		
		TextButton backButton = new TextButton("Back", style);
		backButton.setPosition(10, 10);
		backButton.setWidth(LevelButtonWidth);
		backButton.setHeight(LevelButtonHeight);
		
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				setMainMenu();
			}
		});
		
		levelSelectStage.addActor(backButton);
		levelSelectStage.addActor(table);
	}
	
	private void setLevelSelectMenu(){
		isInMainMenu = false;
		isInLevelSelect = true;
		Gdx.input.setInputProcessor(levelSelectStage);
	}
	
	private void setMainMenu(){
		isInMainMenu = true;
		isInLevelSelect = false;
		Gdx.input.setInputProcessor(mainMenuStage);
	}
	
	private void generateMainMenuStage(){

		mainMenuStage = new Stage(new ScreenViewport(Game.camera), Game.batch);
		levelButton = new TextButton("Start", style);
		
		levelButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				setLevelSelectMenu();
			}
		});
		
		
		levelCreateButton = new TextButton("Level Creator", style);
		exitButton = new TextButton("Exit", style);
		soundButton = new TextButton("Sound: ", style);
		setSoundButtonText();
		
		soundButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				BreakoutSettings.Sound_On = !BreakoutSettings.Sound_On;
				setSoundButtonText();
			}
		});
		
		
		Table table = new Table();
		table.setWidth(BreakoutSettings.SCREEN_WIDTH);
		table.setHeight(BreakoutSettings.SCREEN_HEIGHT);
		table.add(levelButton).center().width(MainButtonWidth).height(MainButtonHeight).pad(MainButtonPad);
		table.row();
		table.add(levelCreateButton).center().width(MainButtonWidth).height(MainButtonHeight).pad(MainButtonPad);
		table.row();
		table.add(soundButton).center().width(MainButtonWidth).height(MainButtonHeight).pad(MainButtonPad);
		table.row();
		table.add(exitButton).center().width(MainButtonWidth).height(MainButtonHeight).pad(MainButtonPad);
		
		mainMenuStage.addActor(table);
	}
	
	private void setSoundButtonText(){
		if(BreakoutSettings.Sound_On){
			soundButton.setText("Sound: ON");
		}
		else{
			soundButton.setText("Sound: OFF");
		}
	}

	@Override
	public void show() {
		setMainMenu();
		setSoundButtonText();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(isInLevelSelect){
			levelSelectStage.act(delta);
			levelSelectStage.draw();
		}
		if(isInMainMenu){
			mainMenuStage.act(delta);
			mainMenuStage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		mainMenuStage.getViewport().setScreenSize(width, height);
		levelSelectStage.getViewport().setScreenSize(width, height);
		
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
		Gdx.input.setInputProcessor(null);
	}

}

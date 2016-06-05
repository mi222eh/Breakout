package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Render.MenuStyle;
import com.mygdx.game.Settings.BreakoutSettings;

public class MainMenuScreen implements Screen, HttpResponseListener{
	
	private Sprite MainMenuBackground, LevelSelectBackground, OnlineBackground;
	
	public static final int MainButtonWidth = 500;
	public static final int MainButtonHeight = 75;
	public static final int MainButtonPad = 5;
	
	private final int LevelButtonWidth = 235;
	private final int LevelButtonHeight = 75;
	private final int LevelButtonPad = 5;
	
	private boolean isInMainMenu;
	private boolean isInLevelSelect;
	private boolean beginInLevel;
	private boolean isInOnlineSelect;
	
	private HttpResponseListener httpListener;
	private GetOnlineMapListener getOnlineMapListener;

    private ScrollPaneStyle scrollStyle;
	
	
	private Stage mainMenuStage, levelSelectStage, onlineSelectStage;
	private BreakoutGame Game;
	private TextButtonStyle style;
	private TextButton exitButton, soundButton, levelCreateButton, levelButton;
	
	public MainMenuScreen(BreakoutGame game) {
		this.getOnlineMapListener = new GetOnlineMapListener();
		this.getOnlineMapListener.menuScreen = this;
		this.httpListener = this;
		this.beginInLevel = false;
		this.isInMainMenu = true;
		this.isInLevelSelect = false;
		this.Game = game;
		
		this.style = MenuStyle.style;
		
        this.scrollStyle = new ScrollPaneStyle();
        
		this.generateMainMenuStage();
		this.generateLevelSelectStage();
		this.generateOnlineSelectStage();
		
		this.MainMenuBackground = new Sprite(new Texture("Textures/MainMenuBackground.png"));
		this.LevelSelectBackground = new Sprite(new Texture("Textures/LevelSelectBackGround.png"));
		this.OnlineBackground = new Sprite(new Texture("Textures/OnlineLevelsBackground.png"));
		
		this.MainMenuBackground.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.LevelSelectBackground.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.OnlineBackground.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		
		this.MainMenuBackground.setPosition(0, 0);
		this.LevelSelectBackground.setPosition(0, 0);
		this.OnlineBackground.setPosition(0, 0);
	}
	
	private void generateOnlineSelectStage(){
		this.onlineSelectStage = new Stage(this.Game.viewport, this.Game.batch);
		TextButton backButton = new TextButton("Back", this.style);
		backButton.setPosition(10, 10);
		backButton.setWidth(this.LevelButtonWidth);
		backButton.setHeight(this.LevelButtonHeight);
		
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				MainMenuScreen.this.setLevelSelectMenu();
				Game.soundListener.ButtonSound();
			}
		});
		this.onlineSelectStage.addActor(backButton);
	}
	
	public void toGameScreenWidthJson(String mapJson){
		this.dispose();
		GameScreen gameScreen = (GameScreen)this.Game.screenHandler.getScreen(ScreenType.GameScreen);
		gameScreen.init();
		gameScreen.loadJsonMap(mapJson);;
		this.Game.setScreen(gameScreen);
	}
	
	private void generateOnlineLevelButtons(String jsonText){
		Json json = new Json();
		JsonValue texts = new JsonReader().parse(jsonText);
		Table table = new Table();
		for (int i = 0; i < texts.size; i++) {
			String name = texts.getString(i);
			TextButton button = this.generateOnlineLevelButton(name);
			table.add(button).width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(10).center();
			table.row();
		}
		ScrollPane scroll = new ScrollPane(table, this.scrollStyle);
		scroll.setWidth(BreakoutSettings.SCREEN_WIDTH - 500);
		scroll.setHeight(BreakoutSettings.SCREEN_HEIGHT);
		scroll.setPosition(250, 0);
		this.onlineSelectStage.addActor(scroll);
	}
	private TextButton generateOnlineLevelButton(String name){
		TextButton levelButton = new TextButton(name, MenuStyle.style);
		levelButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Json json = new Json();
				String nameJson = json.toJson(name);
				HttpRequestBuilder reqBuilder = new HttpRequestBuilder();
				HttpRequest req = reqBuilder.newRequest().method(HttpMethods.GET).url(BreakoutSettings.GET_MAP_URL).content("level=" + name).build();
				Gdx.net.sendHttpRequest(req, MainMenuScreen.this.getOnlineMapListener);
				Game.soundListener.ButtonSound();
			}
		});
		return levelButton;
	}
	
	private void makeLevelListener(final int level, TextButton button){
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				MainMenuScreen.this.dispose();
				GameScreen gameScreen = (GameScreen)MainMenuScreen.this.Game.screenHandler.getScreen(ScreenType.GameScreen);
				gameScreen.loadTmxMap(level);
				MainMenuScreen.this.Game.setScreen(gameScreen);
				Game.soundListener.ButtonSound();
			}
		});
	}
	
	public void setToLevelScreen(){
		this.beginInLevel = true;
	}
	
	private void generateLevelSelectStage(){
		this.levelSelectStage = new Stage(this.Game.viewport, this.Game.batch);
		Table table = new Table();
		table.setWidth(BreakoutSettings.SCREEN_WIDTH);
		table.setHeight(BreakoutSettings.SCREEN_HEIGHT);
		table.center();
		
		for(int i = 0; i < 5; i++){
			int level = i + 1;
			TextButton button = new TextButton("Map " + level, this.style);
			table.add(button).width(this.LevelButtonWidth).height(this.LevelButtonHeight).pad(this.LevelButtonPad);
			if(i == 4){
				table.row();
			}
			this.makeLevelListener(level, button);

		}
		
		TextButton backButton = new TextButton("Back", this.style);
		backButton.setPosition(10, 10);
		backButton.setWidth(this.LevelButtonWidth);
		backButton.setHeight(this.LevelButtonHeight);
		
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				MainMenuScreen.this.setMainMenu();
				Game.soundListener.ButtonSound();
			}
		});
		TextButton onlineButton = new TextButton("Online", this.style);
		onlineButton.setPosition(1000, 10);
		onlineButton.setWidth(this.LevelButtonWidth);
		onlineButton.setHeight(this.LevelButtonHeight);
		
		onlineButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				MainMenuScreen.this.setOnlineSelectMenu();
				Game.soundListener.ButtonSound();
			}
		});
		this.levelSelectStage.addActor(backButton);
		this.levelSelectStage.addActor(onlineButton);
		this.levelSelectStage.addActor(table);
	}
	
	private void setOnlineSelectMenu(){
		this.onlineSelectStage.dispose();
		this.generateOnlineSelectStage();
		this.isInOnlineSelect = true;
		this.isInMainMenu = false;
		this.isInLevelSelect = false;
		Gdx.input.setInputProcessor(this.onlineSelectStage);
		
		HttpRequestBuilder reqBuilder = new HttpRequestBuilder();
		HttpRequest req = reqBuilder.newRequest().method(HttpMethods.GET).url(BreakoutSettings.GET_LIST_URL).build();
		Gdx.net.sendHttpRequest(req, this.httpListener);
	}
	
	private void setLevelSelectMenu(){
		this.isInMainMenu = false;
		this.isInOnlineSelect = false;
		this.isInLevelSelect = true;
		Gdx.input.setInputProcessor(this.levelSelectStage);
		

		
	}
	
	private void setMainMenu(){
		this.isInOnlineSelect = false;
		this.isInMainMenu = true;
		this.isInLevelSelect = false;
		Gdx.input.setInputProcessor(this.mainMenuStage);
	}
	
	private void generateMainMenuStage(){

		this.mainMenuStage = new Stage(this.Game.viewport, this.Game.batch);
		this.levelButton = new TextButton("Start", this.style);
		
		this.levelButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				MainMenuScreen.this.setLevelSelectMenu();
				Game.soundListener.ButtonSound();
			}
		});
		this.levelCreateButton = new TextButton("Level Creator", this.style);
		
		this.levelCreateButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				CreateMapScreen screen = (CreateMapScreen)MainMenuScreen.this.Game.screenHandler.getScreen(ScreenType.CreateMapScreen);
				//screen.init();
				MainMenuScreen.this.dispose();
				MainMenuScreen.this.Game.setScreen(screen);
				Game.soundListener.ButtonSound();
			}
		});
		this.exitButton = new TextButton("Exit", this.style);
		
		this.exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Game.soundListener.ButtonSound();
				Gdx.app.exit();
			}
		});
		
		this.soundButton = new TextButton("Sound: ", this.style);
		this.setSoundButtonText();
		
		this.soundButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				BreakoutSettings.Sound_On = !BreakoutSettings.Sound_On;
				MainMenuScreen.this.setSoundButtonText();
				Game.soundListener.ButtonSound();
			}
		});
		
		
		Table table = new Table();
		table.setWidth(BreakoutSettings.SCREEN_WIDTH);
		table.setHeight(BreakoutSettings.SCREEN_HEIGHT);
		table.add(this.levelButton).center().width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad);
		table.row();
		table.add(this.levelCreateButton).center().width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad);
		table.row();
		table.add(this.soundButton).center().width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad);
		table.row();
		table.add(this.exitButton).center().width(MainMenuScreen.MainButtonWidth).height(MainMenuScreen.MainButtonHeight).pad(MainMenuScreen.MainButtonPad);
		
		this.mainMenuStage.addActor(table);
	}
	
	private void setSoundButtonText(){
		if(BreakoutSettings.Sound_On){
			this.soundButton.setText("Sound: ON");
		}
		else{
			this.soundButton.setText("Sound: OFF");
		}
	}

	@Override
	public void show() {
		if(this.beginInLevel){
			this.setLevelSelectMenu();
			this.beginInLevel = false;
		}
		else{
			this.setMainMenu();
		}
		this.setSoundButtonText();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Game.camera.update();
		this.Game.batch.setProjectionMatrix(this.Game.camera.combined);
		if(this.isInLevelSelect){
			Game.batch.begin();
			this.LevelSelectBackground.draw(Game.batch);
			Game.batch.end();
			this.levelSelectStage.act(delta);
			this.levelSelectStage.draw();
		}
		if(this.isInMainMenu){
			Game.batch.begin();
			this.MainMenuBackground.draw(Game.batch);
			Game.batch.end();
			this.mainMenuStage.act(delta);
			this.mainMenuStage.draw();
		}
		if(this.isInOnlineSelect){
			Game.batch.begin();
			this.OnlineBackground.draw(Game.batch);
			Game.batch.end();
			this.onlineSelectStage.act(delta);
			this.onlineSelectStage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		this.mainMenuStage.getViewport().setScreenSize(width, height);
		this.levelSelectStage.getViewport().setScreenSize(width, height);
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

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		
		this.generateOnlineLevelButtons(httpResponse.getResultAsString());
		
	}

	@Override
	public void failed(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		
	}

}

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Handlers.ScreenHandler.ScreenType;
import com.mygdx.game.Model.MapCreateModel;
import com.mygdx.game.Model.MapCreate.BrickCreate;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Render.LevelCreatorRender;
import com.mygdx.game.Render.MenuStyle;
import com.mygdx.game.Settings.BreakoutSettings;

public class CreateMapScreen implements Screen, HttpResponseListener{
	
	private Sprite CreateMapBackground, NotPlaceArea;
	private BreakoutGame Game;
	
	public MapCreateModel createModel;
	public LevelCreatorRender creatorRender;
	TextButtonStyle style;
	public Screen thisScreen;
	public HttpResponseListener httpListener;
	public boolean canSend;
	
	public Stage editStage;
	
	public CreateMapScreen(BreakoutGame game){
		this.CreateMapBackground = new Sprite(new Texture("Textures/LevelCreateBackground.png"));
		this.NotPlaceArea = new Sprite(new Texture("Textures/NotPlaceArea.png"));
		this.canSend = false;
		this.httpListener = this;
		this.thisScreen = this;
		this.Game = game;
		this.createModel = new MapCreateModel();
		this.creatorRender = new LevelCreatorRender();
		this.creatorRender.setModel(this.createModel);
		this.editStage = new Stage(this.Game.viewport, this.Game.batch);
		this.style = MenuStyle.style;
		
		this.createBackButton();
		this.createResetButton();
		this.createTryButton();
		this.createUploadButton();
		
		this.CreateMapBackground.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.CreateMapBackground.setPosition(0, 0);
		
		this.NotPlaceArea.setSize(BreakoutSettings.SCREEN_WIDTH, MapCreateModel.MinHeight);
		this.NotPlaceArea.setPosition(0, 0);
	}
	
	public void init(){
		this.createModel.init();
	}
	
	private void createBackButton(){
		TextButton button = new TextButton("Back", this.style);
		button.setWidth(BreakoutSettings.GAME_BUTTON_WIDTH);
		button.setHeight(BreakoutSettings.GAME_BUTTON_HEIGHT);
		
		button.setPosition(50, 25);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				CreateMapScreen.this.dispose();
				MainMenuScreen screen = (MainMenuScreen) CreateMapScreen.this.Game.screenHandler.getScreen(ScreenType.MenuScreen);
				CreateMapScreen.this.Game.setScreen(screen);
				Game.soundListener.ButtonSound();
			}
		});

		this.editStage.addActor(button);
	}
	private void createTryButton(){
		TextButton button = new TextButton("Try", this.style);
		button.setWidth(BreakoutSettings.GAME_BUTTON_WIDTH);
		button.setHeight(BreakoutSettings.GAME_BUTTON_HEIGHT);
		
		button.setPosition(700, 25);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				GameScreen screen = (GameScreen) CreateMapScreen.this.Game.screenHandler.getScreen(ScreenType.GameScreen);
				CreateMapScreen.this.dispose();
				screen.setCustomReturnScreen(CreateMapScreen.this.thisScreen);
				screen.loadObject(CreateMapScreen.this.createModel.mapCreate);
				CreateMapScreen.this.Game.setScreen(screen);
				Game.soundListener.ButtonSound();
			}
		});

		this.editStage.addActor(button);
	}
	
	private void createResetButton(){
		TextButton button = new TextButton("Reset", this.style);
		button.setWidth(BreakoutSettings.GAME_BUTTON_WIDTH);
		button.setHeight(BreakoutSettings.GAME_BUTTON_HEIGHT);
		
		button.setPosition(300, 25);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				CreateMapScreen.this.init();
				Game.soundListener.ButtonSound();
			}
		});

		this.editStage.addActor(button);
	}
	
	private void createUploadButton(){
		TextButton button = new TextButton("Upload", this.style);
		button.setWidth(BreakoutSettings.GAME_BUTTON_WIDTH);
		button.setHeight(BreakoutSettings.GAME_BUTTON_HEIGHT);
		
		button.setPosition(1000, 25);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(CreateMapScreen.this.canSend){
					Game.soundListener.ButtonSound();
					Json json = new Json();
					
					json.setSerializer(BrickCreate.class, new Json.Serializer<BrickCreate>() {
						@Override
						public BrickCreate read(Json json, JsonValue jsonData, Class type) {
							float x = jsonData.getFloat("x");
							float y = jsonData.getFloat("y");
							int typeBrick = jsonData.getInt("type");
							BrickCreate brick = new BrickCreate(x, y, typeBrick);
							return brick;
						}
						@Override
						public void write(Json json, BrickCreate brick, Class knownType) {
							json.writeObjectStart();
							json.writeValue("x", brick.x);
							json.writeValue("y", brick.y);
							json.writeValue("type", brick.type);
							json.writeObjectEnd();
						}
					});
					
					json.setElementType(MapCreate.class, "bricks", BrickCreate.class);
					
					String jsonMapString = "{\"bricks\":[";
					for (int i = 0; i < CreateMapScreen.this.createModel.mapCreate.bricks.size(); i++) {
						BrickCreate brick = CreateMapScreen.this.createModel.mapCreate.bricks.get(i);
						jsonMapString += "{\"x\":\"" + brick.x + "\",";
						jsonMapString += "\"y\":\"" + brick.y + "\",";
						jsonMapString += "\"type\":\"" + brick.type + "\"}";
						if(i + 1 != CreateMapScreen.this.createModel.mapCreate.bricks.size()){
							jsonMapString += ",";
						}
					}
					jsonMapString += "]}";
					HttpRequestBuilder reqBuilder = new HttpRequestBuilder();
					HttpRequest postRequest = new HttpRequest(HttpMethods.POST);
					postRequest.setHeader("Content-Type", "application/json");
					postRequest.setUrl(BreakoutSettings.POST_MAP_URL);
					postRequest.setContent(jsonMapString);
					//HttpRequest req = reqBuilder.newRequest().method(HttpMethods.POST).url(BreakoutSettings.POST_MAP_URL).content(jsonMapString).build();
					Gdx.net.sendHttpRequest(postRequest, CreateMapScreen.this.httpListener);
				}
			}
		});
		this.editStage.addActor(button);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.editStage);
	}

	@Override
	public void render(float delta) {
		if(this.createModel.mapCreate.bricks.size() == 0){
			this.canSend = false;
		}
		else{
			this.canSend = true;
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Game.batch.setProjectionMatrix(this.Game.camera.combined);
		Vector2 mousePosition = new Vector2();
		mousePosition.x = Gdx.input.getX();
		mousePosition.y = Gdx.input.getY();
		Vector3 mouseGamePosition = this.Game.camera.unproject(new Vector3(mousePosition.x, mousePosition.y, 0));
		mousePosition.x = mouseGamePosition.x;
		mousePosition.y = mouseGamePosition.y;

		this.editStage.act(delta);
		this.createModel.setMousePosition(mousePosition);
		this.createModel.clicks(Gdx.input.isTouched());
		this.creatorRender.setMouse(mousePosition);
		this.createModel.update();
		
		this.Game.batch.begin();
		this.CreateMapBackground.draw(Game.batch);
		this.NotPlaceArea.draw(Game.batch);
		this.creatorRender.render(this.Game.batch);
		this.Game.batch.end();

		this.editStage.draw();

	}

	@Override
	public void resize(int width, int height) {
		this.editStage.getViewport().setScreenSize(width, height);
		
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		// TODO Auto-generated method stub
		
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

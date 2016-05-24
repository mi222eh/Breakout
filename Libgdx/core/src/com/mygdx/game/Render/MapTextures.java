package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Settings.BreakoutSettings;

public class MapTextures {
	public static Sprite WallSide, WallTop, Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable, BallTexture;
	
	public static void loadTextures(){
		loadWallTextures();
		loadBallTexture();
		loadBrickTextures();
	}
	
	private static void loadWallTextures(){
		WallSide = new Sprite(new Texture("Textures/WallSide.png"));
		WallTop = new Sprite(new Texture("Textures/WallTop.png"));
		WallSide.setSize(Map.WALL_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		WallTop.setSize(BreakoutSettings.SCREEN_WIDTH, Map.WALL_WIDTH);
	}
	
	private static void loadBallTexture(){

		BallTexture = new Sprite(new Texture("Textures/Ball.png"));
		BallTexture.setSize(Ball.BALL_RADIUS * 2, Ball.BALL_RADIUS * 2);
	}
	
	private static void loadBrickTextures(){

		Normal1 = new Sprite(new Texture("Textures/Normal1.png"));
		Normal2 = new Sprite(new Texture("Textures/Normal2.png"));
		Normal3 = new Sprite(new Texture("Textures/Normal3.png"));
		Steel1 = new Sprite(new Texture("Textures/Steel1.png"));
		Steel2 = new Sprite(new Texture("Textures/Steel2.png"));
		Steel3 = new Sprite(new Texture("Textures/Steel3.png"));
		Invurnerable = new Sprite(new Texture("Textures/Invurnerable.png"));
		
		setBrickSize(Normal1);
		setBrickSize(Normal2);
		setBrickSize(Normal3);
		setBrickSize(Steel1);
		setBrickSize(Steel2);
		setBrickSize(Steel3);
		setBrickSize(Invurnerable);
	}
	
	private static void setBrickSize(Sprite sprite){
		sprite.setSize(Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
	}
}

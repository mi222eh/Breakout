package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Settings.BreakoutSettings;

public class MapRender {
	
	Sprite WallSide, WallTop;
	
	BallRender BallRender;
	BrickRender BrickRender;
	PlayerRender PlayerRender;
	
	public MapRender(){
		BallRender = new BallRender();
		BrickRender = new BrickRender();
		PlayerRender = new PlayerRender();
		
		//ALT INIT BEGIN
		WallSide = new Sprite(new Texture("Textures/WallSide.png"));
		WallTop = new Sprite(new Texture("Textures/WallTop.png"));
		WallSide.setSize(Map.WALL_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		WallTop.setSize(BreakoutSettings.SCREEN_WIDTH, Map.WALL_WIDTH);
		
		BallRender.init();
		PlayerRender.init();
		BrickRender.init();
		//ALT INIT END
		
	}
	
	public void init(){

	}
	
	public void setBalls(Array<Ball> balls){
		BallRender.setBalls(balls);
	}
	
	public void setBricks(Array<Brick> bricks){
		BrickRender.setBricks(bricks);
	}
	
	public void setPlayer(Player player){
		PlayerRender.setPlayer(player);
	}
	
	public void render(SpriteBatch batch){
		WallSide.setPosition(0, 0);
		WallSide.draw(batch);
		WallSide.setPosition(BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH, 0);
		WallSide.draw(batch);
		WallTop.setPosition(0, BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH);
		WallTop.draw(batch);
		BrickRender.render(batch);
		BallRender.render(batch);
		PlayerRender.render(batch);
	}
}

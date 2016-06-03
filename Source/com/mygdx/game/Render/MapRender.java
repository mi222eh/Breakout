package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Powerup;
import com.mygdx.game.Settings.BreakoutSettings;

public class MapRender {
	
	Sprite WallSide, WallTop, Background, WinTexture, LoseTexture;
	
	BallRender BallRender;
	BrickRender BrickRender;
	PlayerRender PlayerRender;
	PowerupRender powerupsRender;
	
	private boolean ended;
	private boolean hasWon;
	
	public MapRender(){
		this.ended = false;
		this.powerupsRender = new PowerupRender();
		this.BallRender = new BallRender();
		this.BrickRender = new BrickRender();
		this.PlayerRender = new PlayerRender();
		
		this.WallSide = MapTextures.WallSide;
		this.WallTop = MapTextures.WallTop;
		this.Background = new Sprite(new Texture("Textures/GameBackground.png"));
		this.Background.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.Background.setPosition(0, 0);
		this.WinTexture = new Sprite(new Texture("Textures/WinTexture.png"));
		this.WinTexture.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.WinTexture.setPosition(0, 0);
		this.LoseTexture = new Sprite(new Texture("Textures/LoseTexture.png"));
		this.LoseTexture.setSize(BreakoutSettings.SCREEN_WIDTH, BreakoutSettings.SCREEN_HEIGHT);
		this.LoseTexture.setPosition(0, 0);
		
		this.BallRender.init();
		this.PlayerRender.init();
		this.BrickRender.init();
		
	}
	
	public void setEnd(boolean hasWon){
		this.ended = true;
		this.hasWon = hasWon;
	}
	
	public void init(){
		this.ended = false;
	}
	
	public void setBalls(Array<Ball> balls){
		this.BallRender.setBalls(balls);
	}
	
	public void setBricks(Array<Brick> bricks){
		this.BrickRender.setBricks(bricks);
	}
	
	public void setPlayer(Player player){
		this.PlayerRender.setPlayer(player);
	}
	public void setPowerups(Array<Powerup> powerups){
		this.powerupsRender.Powerups = powerups;
	}
	
	public void render(SpriteBatch batch){
		this.Background.draw(batch);
		this.WallSide.setPosition(0, 0);
		this.WallSide.draw(batch);
		this.WallSide.setPosition(BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH, 0);
		this.WallSide.draw(batch);
		this.WallTop.setPosition(0, BreakoutSettings.SCREEN_HEIGHT - Map.WALL_WIDTH);
		this.WallTop.draw(batch);
		this.BrickRender.render(batch);
		this.BallRender.render(batch);
		this.PlayerRender.render(batch);
		this.powerupsRender.render(batch);
		if(this.ended){
			if(this.hasWon){
				this.WinTexture.draw(batch);
			}
			else{
				this.LoseTexture.draw(batch);
			}
		}
	}

	public void setInvurnerableBrick(Array<Brick> invulnerableBricks) {
		this.BrickRender.InvurnerableBricks = invulnerableBricks;
		
	}
}

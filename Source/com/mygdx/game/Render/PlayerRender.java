package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Player;

public class PlayerRender {
	Sprite PlayerTexture;
	Player Player;
	public void setPlayer(Player player){
		this.Player = player;
	}
	
	public void init(){
		this.PlayerTexture = new Sprite(new Texture("Textures/Player.png"));
	}
	
	public void render(SpriteBatch batch){
		this.PlayerTexture.setSize(this.Player.width.getVal() * 2, com.mygdx.game.Model.Entities.Player.PLAYER_HEIGHT * 2);
		Vector2 position = this.Player.getActiveBody().getPosition();
		float x = position.x - ((this.Player.width.getVal() * 2) / 2);
		float y = position.y - ((com.mygdx.game.Model.Entities.Player.PLAYER_HEIGHT * 2) / 2);
		this.PlayerTexture.setPosition(x, y);
		this.PlayerTexture.draw(batch);
	}
}

package com.mgdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entities.Player;

public class PlayerRender {
	Sprite PlayerTexture;
	Player Player;
	public void setPlayer(Player player){
		Player = player;
	}
	
	public void init(){
		PlayerTexture = new Sprite(new Texture("Textures/Player.png"));
	}
	
	public void render(SpriteBatch batch){
		PlayerTexture.setSize(Player.width.getVal() * 2, com.mygdx.game.Model.Entities.Player.PLAYER_HEIGHT * 2);
		Vector2 position = Player.getActiveBody().getPosition();
		float x = position.x - ((Player.width.getVal() * 2) / 2);
		float y = position.y - ((com.mygdx.game.Model.Entities.Player.PLAYER_HEIGHT * 2) / 2);
		PlayerTexture.setPosition(x, y);
		PlayerTexture.draw(batch);
	}
}

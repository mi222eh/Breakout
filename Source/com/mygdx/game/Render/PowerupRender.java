package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Powerup;

public class PowerupRender {
	private Sprite PowerSmaller, PowerBigger, PowerDuplicate, PowerFaster, PowerSlower;
	
	public Array<Powerup> Powerups;
	
	public PowerupRender(){
		this.Powerups = new Array<Powerup>();
		this.PowerSmaller = new Sprite(new Texture("Textures/PowerSmaller.png"));
		this.PowerBigger = new Sprite(new Texture("Textures/PowerBigger.png"));
		this.PowerDuplicate = new Sprite(new Texture("Textures/PowerDuplicate.png"));
		this.PowerFaster = new Sprite(new Texture("Textures/PowerFaster.png"));
		this.PowerSlower = new Sprite(new Texture("Textures/PowerSlower.png"));
		
		this.setSize(this.PowerSmaller);
		this.setSize(this.PowerBigger);
		this.setSize(this.PowerDuplicate);
		this.setSize(this.PowerFaster);
		this.setSize(this.PowerSlower);
	}
	private void setSize(Sprite sprite){
		sprite.setSize(Powerup.POWERUP_WIDTH, Powerup.POWERUP_WIDTH);
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < this.Powerups.size; i++) {
			Sprite currentSprite = null;
			Powerup powerup = this.Powerups.get(i);
			switch (powerup.type) {
			case DuplicateBalls:
				currentSprite = this.PowerDuplicate;
				break;
			case MakeBallsFaster:
				currentSprite = this.PowerFaster;
				break;
			case MakeBallsSlower:
				currentSprite = this.PowerSlower;
				break;
			case MakePlayerSmaller:
				currentSprite = this.PowerSmaller;
				break;
			case MakePlayerWider:
				currentSprite = this.PowerBigger;
				break;
			default:
				break;
			}
			if(currentSprite != null){
				Vector2 position = powerup.powerBody.getPosition();
				float x = position.x - (Powerup.POWERUP_WIDTH / 2);
				float y = position.y - (Powerup.POWERUP_HEIGHT / 2);
				currentSprite.setPosition(x, y);
				currentSprite.draw(batch);
			}
		}
	}
}

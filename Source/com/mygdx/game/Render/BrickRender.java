package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Brick;

public class BrickRender {
	Sprite Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable;
	Array<Brick> Bricks;
	Array<Brick> InvurnerableBricks;
	
	public void init(){
		this.Normal1 = new Sprite(new Texture("Textures/Normal1.png"));
		this.Normal2 = new Sprite(new Texture("Textures/Normal2.png"));
		this.Normal3 = new Sprite(new Texture("Textures/Normal3.png"));
		this.Steel1 = new Sprite(new Texture("Textures/Steel1.png"));
		this.Steel2 = new Sprite(new Texture("Textures/Steel2.png"));
		this.Steel3 = new Sprite(new Texture("Textures/Steel3.png"));
		this.Invurnerable = new Sprite(new Texture("Textures/Invurnerable.png"));
		
		this.setSize(this.Normal1);
		this.setSize(this.Normal2);
		this.setSize(this.Normal3);
		this.setSize(this.Steel1);
		this.setSize(this.Steel2);
		this.setSize(this.Steel3);
		this.setSize(this.Invurnerable);
	}
	
	private void setSize(Sprite sprite){
		sprite.setSize(Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
	}

	public void setBricks(Array<Brick> bricks){
		this.Bricks = bricks;
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < this.InvurnerableBricks.size; i++) {
			Brick brick = this.InvurnerableBricks.get(i);
			
			Sprite currentSprite = this.Invurnerable;
			
			Vector2 position = brick.brickBody.getPosition();
			float x = position.x - (Brick.BRICK_WIDTH / 2);
			float y = position.y - (Brick.BRICK_HEIGHT / 2);
			
			currentSprite.setPosition(x, y);
			currentSprite.draw(batch);
		}
		for (int i = 0; i < this.Bricks.size; i++) {
			Sprite currentSprite = null;
			Brick brick = this.Bricks.get(i);
			switch (brick.brickType) {
			case invisible:
				break;
			case invurnerable:
				currentSprite = this.Invurnerable;
				break;
			case normal1:
				currentSprite = this.Normal1;
				break;
			case normal2:
				currentSprite = this.Normal2;
				break;
			case normal3:
				currentSprite = this.Normal3;
				break;
			case steel1:
				currentSprite = this.Steel1;
				break;
			case steel2:
				currentSprite = this.Steel2;
				break;
			case steel3:
				currentSprite = this.Steel3;
				break;
			case undefined:
				break;
			default:
				break;
			}
			if(currentSprite != null){
				Vector2 position = brick.brickBody.getPosition();
				float x = position.x - (Brick.BRICK_WIDTH / 2);
				float y = position.y - (Brick.BRICK_HEIGHT / 2);
				
				currentSprite.setPosition(x, y);
				currentSprite.draw(batch);
			}
		}
	}
}

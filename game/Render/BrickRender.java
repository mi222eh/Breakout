package com.mgdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Brick;

public class BrickRender {
	Sprite Normal1, Normal2, Normal3, Steel1, Steel2, Steel3, Invurnerable;
	Array<Brick> Bricks;
	
	public void init(){
		Normal1 = new Sprite(new Texture("Textures/Normal1.png"));
		Normal2 = new Sprite(new Texture("Textures/Normal2.png"));
		Normal3 = new Sprite(new Texture("Textures/Normal3.png"));
		Steel1 = new Sprite(new Texture("Textures/Steel1.png"));
		Steel2 = new Sprite(new Texture("Textures/Steel2.png"));
		Steel3 = new Sprite(new Texture("Textures/Steel3.png"));
		Invurnerable = new Sprite(new Texture("Textures/Invurnerable.png"));
		
		setSize(Normal1);
		setSize(Normal2);
		setSize(Normal3);
		setSize(Steel1);
		setSize(Steel2);
		setSize(Steel3);
		setSize(Invurnerable);
	}
	
	private void setSize(Sprite sprite){
		sprite.setSize(Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
	}

	public void setBricks(Array<Brick> bricks){
		Bricks = bricks;
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < Bricks.size; i++) {
			Sprite currentSprite = null;
			Brick brick = Bricks.get(i);
			switch (brick.brickType) {
			case invisible:
				break;
			case invurnerable:
				currentSprite = Invurnerable;
				break;
			case normal1:
				currentSprite = Normal1;
				break;
			case normal2:
				currentSprite = Normal2;
				break;
			case normal3:
				currentSprite = Normal3;
				break;
			case steel1:
				currentSprite = Steel1;
				break;
			case steel2:
				currentSprite = Steel2;
				break;
			case steel3:
				currentSprite = Steel3;
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

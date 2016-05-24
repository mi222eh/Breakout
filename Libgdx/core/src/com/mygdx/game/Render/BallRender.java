package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Model.Entities.Ball;

public class BallRender {
	Sprite BallTexture;
	Array<Ball> Balls;
	
	public void setBalls(Array<Ball> balls){
		Balls = balls;
	}
	
	public void init(){
		BallTexture = new Sprite(new Texture("Textures/Ball.png"));
		BallTexture.setSize(Ball.BALL_RADIUS * 2, Ball.BALL_RADIUS * 2);
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < Balls.size; i++) {
			Ball ball = Balls.get(i);
			Vector2 position = ball.ballBody.getPosition();
			float x = position.x - (Ball.BALL_RADIUS * 2 / 2);
			float y = position.y - (Ball.BALL_RADIUS * 2 / 2);
			
			BallTexture.setPosition(x, y);
			BallTexture.draw(batch);
		}
	}
}

package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.BottomSensor;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Powerup;
import com.mygdx.game.interfaces.GameContactContactListener;
import com.mygdx.game.interfaces.GameSoundListener;

public class BreakoutContactListener implements ContactListener{
	
	public GameContactContactListener listener;
	public GameSoundListener soundListener;

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		//BALL AND BRICK COLLISION
		if(fixtureA.getUserData() instanceof Ball && fixtureB.getUserData() instanceof Brick){
			this.brickHit((Brick)fixtureB.getUserData());
		}
		if(fixtureB.getUserData() instanceof Ball && fixtureA.getUserData() instanceof Brick){
			this.brickHit((Brick)fixtureA.getUserData());
		}
		
		//PLAYER AND BALL COLLISION
		if(fixtureA.getUserData() instanceof Ball && fixtureB.getUserData() instanceof Player){
			Ball ball = (Ball) fixtureA.getUserData();
			Player player = (Player)fixtureB.getUserData();
			this.handleBallToPlayer(player, ball);
		}
		if(fixtureB.getUserData() instanceof Ball && fixtureA.getUserData() instanceof Player){
			Ball ball = (Ball) fixtureB.getUserData();
			Player player = (Player)fixtureA.getUserData();
			this.handleBallToPlayer(player, ball);
		}
		
		//BALL AND BOTTOM SENSOR
		if(fixtureA.getUserData() instanceof Ball && fixtureB.getUserData() instanceof BottomSensor){
			Ball ball = (Ball) fixtureA.getUserData();
			BottomSensor bottomSensor = (BottomSensor)fixtureB.getUserData();
			bottomSensor.handle(ball);
		}
		if(fixtureB.getUserData() instanceof Ball && fixtureA.getUserData() instanceof BottomSensor){
			Ball ball = (Ball) fixtureB.getUserData();
			BottomSensor bottomSensor = (BottomSensor)fixtureA.getUserData();
			bottomSensor.handle(ball);
		}
		
		//POWERUP AND SENSOR
		if(fixtureA.getUserData() instanceof Powerup && fixtureB.getUserData() instanceof BottomSensor){
			Powerup powerup = (Powerup) fixtureA.getUserData();
			BottomSensor bottomSensor = (BottomSensor)fixtureB.getUserData();
			bottomSensor.handle(powerup);
		}
		if(fixtureB.getUserData() instanceof Powerup && fixtureA.getUserData() instanceof BottomSensor){
			Powerup powerup = (Powerup) fixtureB.getUserData();
			BottomSensor bottomSensor = (BottomSensor)fixtureA.getUserData();
			bottomSensor.handle(powerup);
		}
		
		//POWERUP AND PLAYER
		if(fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Powerup){
			Powerup powerup = (Powerup)fixtureB.getUserData();
			powerup.activate();
		}
		if(fixtureB.getUserData() instanceof Player && fixtureA.getUserData() instanceof Powerup){
			Powerup powerup = (Powerup)fixtureA.getUserData();
			powerup.activate();
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private void brickHit(Brick brick){
		brick.hit();
	}
	
	private void handleBallToPlayer(Player player, Ball ball){

		//Get current velocity
		Vector2 velocity = ball.ballBody.getLinearVelocity();
		float velocitySpeed = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
		float angle;
		soundListener.PlayerHit();

		if(ball.ballBody.getPosition().y > Player.PLAYER_POSITION_Y + Player.PLAYER_HEIGHT){
			//Get information
			float playerX = player.getActiveBody().getPosition().x;
			float ballX = ball.ballBody.getPosition().x;
			
			//Calculate offset from the middle of the player to the middle of the ball
			float offset = ballX - playerX;
			
			//The percent calculation
			float percentToEdge = (offset / player.width.getVal());
			
			//Calculate the resulting angle
			angle = percentToEdge * Ball.MIN_ANGLE;
			
			Vector2 newVelocity = new Vector2();
			newVelocity.x = (float) (velocitySpeed * Math.sin(Math.toRadians(angle)) / Math.sin(Math.toRadians(90)));
			newVelocity.y = (float) Math.sqrt((velocitySpeed * velocitySpeed) - (newVelocity.x * newVelocity.x));
			
			//Set velocity
			ball.ballBody.setLinearVelocity(newVelocity);

		}
	}
	
	public void setListener(GameContactContactListener listener){
		this.listener = listener;
	}
}

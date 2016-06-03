package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.BottomSensor;
import com.mygdx.game.Model.Entities.Ball.BallSpeed;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Model.Entities.Player.PlayerWidth;
import com.mygdx.game.Model.Entities.Powerup;
import com.mygdx.game.Model.Entities.Wall;
import com.mygdx.game.Model.Entities.Powerup.PowerupType;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.GameContactContactListener;
import com.mygdx.game.interfaces.GameSoundListener;
import com.mygdx.game.interfaces.MapListener;

/**
 * Created by Michael on 2016-04-11.
 */
public class GameStage implements MapListener, GameContactContactListener{

	public boolean started;
    public World world;
    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public Player player;
    public Map map;
    public Array<Ball> Balls;
    private Pool<Ball> InactiveBalls;
    public Array<Powerup> Powerups;
    private Pool<Powerup>InactivePowerups;
    public GameSoundListener soundListener;
    public BreakoutContactListener contactListener;
    public boolean hasWon;
    public boolean hasLost;

    public GameStage(){
    	
    	hasWon = false;
    	hasLost = false;
    	
    	Ball.currentSpeed = BallSpeed.Normal;
    	
    	this.started = false;
    	this.map = new Map();
    	this.map.addMapListener(this);

       	//World
        this.world = new World(new Vector2(0,0), false);
        this.contactListener = new BreakoutContactListener();
        contactListener.setListener(this);
        this.world.setContactListener(contactListener);

        Box2D.init();
        
        //Powerups
        this.Powerups = new Array<Powerup>();
        this.InactivePowerups = new Pool<Powerup>() {
			@Override
			protected Powerup newObject() {
				Powerup power = new Powerup();
				return power;
			}
		};
        //Ball
        this.Balls = new Array<Ball>();
        this.InactiveBalls = new Pool<Ball>() {
			
			@Override
			protected Ball newObject() {
				Ball ball = new Ball();
				return ball;
			}
		};
        
        
        //Player
        this.player = new Player();
        
        Fixture fixtureSmallest = GameStage.CreatePlayer(PlayerWidth.Smallest.getVal(), Player.PLAYER_HEIGHT, this.world);
        fixtureSmallest.setUserData(this.player);
        Fixture fixtureSmall = GameStage.CreatePlayer(PlayerWidth.Small.getVal(), Player.PLAYER_HEIGHT, this.world);
        fixtureSmall.setUserData(this.player);
        Fixture fixtureNormal = GameStage.CreatePlayer(PlayerWidth.Normal.getVal(), Player.PLAYER_HEIGHT, this.world);
        fixtureNormal.setUserData(this.player);
        Fixture fixtureLarge = GameStage.CreatePlayer(PlayerWidth.Large.getVal(), Player.PLAYER_HEIGHT, this.world);
        fixtureLarge.setUserData(this.player);
        Fixture fixtureLargest = GameStage.CreatePlayer(PlayerWidth.Largest.getVal(), Player.PLAYER_HEIGHT, this.world);
        fixtureLargest.setUserData(this.player);
        this.player.setBodies(fixtureSmallest.getBody(), fixtureSmall.getBody(), fixtureNormal.getBody(), fixtureLarge.getBody(), fixtureLargest.getBody());
        
        this.player.init();
        
        
        GameStage.createWalls(this.world, Map.WALL_WIDTH);
        GameStage.createBottomSensor(this.world);
    }

    public void init(){
    	this.hasWon = false;
    	this.hasLost = false;
    	this.started = false;
    	Ball.currentSpeed = BallSpeed.Normal;
    	player.init();
    	
    	for (int i = 0; i < Powerups.size; i++) {
			Powerup powerup = Powerups.get(i);
			this.destroyBrick(powerup.powerBody);
		}
    	InactivePowerups.freeAll(Powerups);
    	Powerups.clear();
    	
    	for (int i = 0; i < this.Balls.size; i++) {
			Ball ball = this.Balls.get(i);
			this.destroyBrick(ball.ballBody);
		}
    	this.InactiveBalls.freeAll(this.Balls);
    	this.Balls.clear();
        Ball ball = this.InactiveBalls.obtain();
        Fixture ballfixture = GameStage.CreateCircle(Ball.BALL_RADIUS, this.world);
        ballfixture.setUserData(ball);
        ball.setBody(ballfixture.getBody());
        this.Balls.add(ball);
    }
    
    public void start(){
    	if(!this.started){
    		soundListener.Begin();
        	this.started = true;
        	for (int i = 0; i < this.Balls.size; i++) {
				Ball ball = this.Balls.get(i);
				float speed = Ball.currentSpeed.getVal();
				float speedY = Ball.getMinYSpeed();
				float speedX = (float) Math.sqrt(speed * speed - speedY * speedY);
				ball.init(ball.ballBody.getPosition(), new Vector2(speedX, speedY));
			}
    	}
    }
    
    public void makePlayerBigger(){
    	this.player.makeBigger();
    }
    
    public void MakePlayerSmaller(){
    	this.player.makeSmaller();
    }
    public void duplicateBalls(){
    	if(this.started){
    		Array<Ball>newBalls = new Array<>();
        	for (int i = 0; i < this.Balls.size; i++) {
    			Ball ball = this.Balls.get(i);
    			Vector2 speed = ball.ballBody.getLinearVelocity();
    			Ball newBall = this.InactiveBalls.obtain();
    			Fixture ballFixture = GameStage.CreateCircle(Ball.BALL_RADIUS, this.world);
    			ballFixture.setUserData(newBall);
    			newBall.setBody(ballFixture.getBody());
    			newBall.init(ball.ballBody.getPosition(), new Vector2(speed.y, speed.x * -1));
    			newBalls.add(newBall);
    		}
        	for (int i = 0; i < newBalls.size; i++) {
				Ball ball = newBalls.get(i);
				this.Balls.add(ball);
			}
        	newBalls.clear();
    	}
    }
    
    private void updateBallSpeed(){
    	for (int i = 0; i < this.Balls.size; i++) {
			Ball ball = this.Balls.get(i);
			Vector2 speed = ball.ballBody.getLinearVelocity();
			float angle = (float) Math.toDegrees(Math.atan2(speed.y, speed.x));
			float newSpeed = Ball.currentSpeed.getVal();
			float lastAngle = 180 - 90 - angle;
			float y = (float) ((newSpeed * Math.sin(Math.toRadians(angle))) / Math.sin(Math.toRadians(90)));
			float x = (float) ((newSpeed * Math.sin(Math.toRadians(lastAngle))) / Math.sin(Math.toRadians(90)));
			ball.ballBody.setLinearVelocity(new Vector2(x, y));
		}
    }
    
    public void accelerateBalls(){
    	if(this.started){
        	Ball.increaseSpeed();
        	this.updateBallSpeed();
    	}
    }
    
    public void slowDownBalls(){
    	if(this.started){
        	Ball.decreaseSpeed();
        	this.updateBallSpeed();
    	}
    }
    
    private void checkBallAngle(Ball ball){
    	float speedX = 0;
		Body Ballbody = ball.ballBody;
		Vector2 velocity = Ballbody.getLinearVelocity();
		float currentSpeed = Ball.currentSpeed.getVal();
		float minYVelocity = Ball.getMinYSpeed();
		if(velocity.y > 0 && velocity.y < minYVelocity){
			speedX = (float) Math.sqrt(currentSpeed * currentSpeed - minYVelocity * minYVelocity);
			if(velocity.x < 0){
				speedX *= -1;
			}
			Ballbody.setLinearVelocity(speedX, minYVelocity);
		}
		if(velocity.y <= 0 && velocity.y > -1 * minYVelocity){
			minYVelocity *= -1;
			speedX = (float) Math.sqrt(currentSpeed * currentSpeed - (-minYVelocity * -minYVelocity));
			if((int)velocity.x < 0){
				speedX *= -1;
			}
			Ballbody.setLinearVelocity(speedX, minYVelocity);
		}
    }
    
    private void checkBalls(){
    	for (int i = 0; i < this.Balls.size; i++) {
			Ball ball = this.Balls.get(i);
			if(!ball.alive){
				this.destroyBrick(ball.ballBody);
				this.InactiveBalls.free(ball);
				this.Balls.removeIndex(i);
			}
			else{
				this.checkBallAngle(ball);
			}
		}
    }
 
    public void update(float time){
    	//Update WON
    	hasWon = map.hasWon;
    	
    	//Check Lost
    	if(!hasWon){
    		if(Balls.size == 0){
    			hasLost = true;
    		}
    	}
    	
    	//STARTED CHECK
    	if(!this.started){
    		float playerX = this.player.getActiveBody().getPosition().x;
    		for (int i = 0; i < this.Balls.size; i++) {
				Ball ball = this.Balls.get(i);
				ball.ballBody.setTransform(playerX, Ball.BALL_START_Y, 0);
			}
    	}
    	
    	//CHECK BALLS SPEED AND ALIVE
    	else{
    		this.checkBalls();
    	}
    	
    	//CHECK POWERUPS
    	for (int i = 0; i < Powerups.size; i++) {
			Powerup powerup = Powerups.get(i);
			if(powerup.activate){
				powerup.destroy();
				PowerupType type = powerup.type;
				switch (type) {
				case DuplicateBalls:
					duplicateBalls();
					break;
				case MakeBallsFaster:
					accelerateBalls();
					break;
				case MakeBallsSlower:
					slowDownBalls();
					break;
				case MakePlayerSmaller:
					MakePlayerSmaller();
					break;
				case MakePlayerWider:
					makePlayerBigger();
					break;
				default:
					break;
				}
				if(!powerup.alive){
					this.destroyBrick(powerup.powerBody);
					InactivePowerups.free(powerup);
					Powerups.removeIndex(i);
				}
			}
		}
        
        //POWERUP GRAVITY
        for (int i = 0; i < this.Powerups.size; i++) {
			Powerup power = this.Powerups.get(i);
			power.update(time);
		}
        
        
    	//WORLD STEP
        int subSteps = 2;
        for (int i = 0; i < subSteps; i++){
            this.world.step(time/subSteps, 16, 8);
        }

        this.map.update();
    }

    public void debugRender(OrthographicCamera camera){
        this.debugRenderer.render(this.world, camera.combined);
    }

    public void setPlayerPosition (float position){
        this.player.movePlayer(position);
    }

    public static Fixture CreateCircle(float radius, World world){
	        BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        bodyDef.position.set(100, 400);
	
	        Body body = world.createBody(bodyDef);
	
	        CircleShape circle = new CircleShape();
	        circle.setRadius(radius);
	
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = circle;
	        fixtureDef.density = 0f;
	        fixtureDef.friction = 0f;
	        fixtureDef.restitution = 1f;
	
	        Fixture fixture = body.createFixture(fixtureDef);
	        
	        Filter filter = new Filter();
	        filter.categoryBits = BreakoutSettings.BALL;
	        filter.maskBits = BreakoutSettings.MASK_BALL;
	        
	        fixture.setFilterData(filter);
	
	        circle.dispose();
	        return fixture;
        }

    public static Fixture CreatePlayer(float width, float height, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH / 2, 100);

        Body body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_PLAYER;
        filter.categoryBits = BreakoutSettings.PLAYER;
        fixture.setFilterData(filter);

        box.dispose();
        return fixture;
    }

    public static void createWalls(World world, float wallWidth){
    	
    	Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_WALL;
        filter.categoryBits = BreakoutSettings.WALL;
        

        //LEFT WALL
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(wallWidth / 2, BreakoutSettings.SCREEN_HEIGHT / 2);

        Body leftWall = world.createBody(bodyDef);

        PolygonShape wall = new PolygonShape();
        wall.setAsBox(wallWidth / 2, BreakoutSettings.SCREEN_HEIGHT);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = wall;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        Fixture fixture = leftWall.createFixture(fixtureDef);
        fixture.setUserData(new Wall());
        
        fixture.setFilterData(filter);

        //UPPER WALL
        bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH / 2, BreakoutSettings.SCREEN_HEIGHT - ((wallWidth - 1) / 2));

        Body upperWall = world.createBody(bodyDef);

        wall.setAsBox(BreakoutSettings.SCREEN_WIDTH, (wallWidth - 1) / 2);

        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = wall;
        fixtureDef2.density = 1f;
        fixtureDef2.friction = 0f;
        fixtureDef2.restitution = 0;

        Fixture fixture2 = upperWall.createFixture(fixtureDef2);
        
        fixture2.setFilterData(filter);
        fixture2.setUserData(new Wall());

        //RIGHT WALL
        bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH - (wallWidth / 2), BreakoutSettings.SCREEN_HEIGHT / 2);

        Body rightWall = world.createBody(bodyDef);

        wall.setAsBox(wallWidth / 2, BreakoutSettings.SCREEN_HEIGHT);

        FixtureDef fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = wall;
        fixtureDef3.density = 1f;
        fixtureDef3.friction = 0f;
        fixtureDef3.restitution = 0f;

        Fixture fixture3 = rightWall.createFixture(fixtureDef3);
        
        fixture3.setFilterData(filter);
        fixture3.setUserData(new Wall());

        wall.dispose();
    }
    
    @Override
    public void createPowerup(Vector2 position){
    	int type =  1 + (int) (Math.random() * ((5 - 1) + 1));
    	PowerupType pType = PowerupType.MakeBallsFaster;
    	switch (type){
    	case 1:
    		pType = PowerupType.MakePlayerWider;
    		break;
    	case 2:
    		pType = PowerupType.MakePlayerSmaller;
    		break;
    	case 3:
    		pType = PowerupType.DuplicateBalls;
    		break;
    	case 4:
    		pType = PowerupType.MakeBallsFaster;
    		break;
    	case 5:
    		pType = PowerupType.MakeBallsSlower;
    		break;
    	}
    	
    	Powerup power = this.InactivePowerups.obtain();
    	Fixture powerFixture = GameStage.createPowerupBody(position, this.world);
    	powerFixture.setUserData(power);
    	
    	power.setBody(powerFixture.getBody());
    	
    	float speedY = 30;
    	float speedX = (float)(Math.random() * 300);
    	
    	float number = (float) (Math.random() * 50);
    	if(number < 25){
    		speedX *= -1;
    	}
    	
    	power.init(position, new Vector2(speedX, speedY), pType);
    	this.Powerups.add(power);
    }
    
    public static void createBottomSensor(World world){
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.StaticBody;
    	bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH / 2, Player.PLAYER_HEIGHT / 2);
    	
    	Body body = world.createBody(bodyDef);
    	
    	PolygonShape polygonShape = new PolygonShape();
    	polygonShape.setAsBox(BreakoutSettings.SCREEN_WIDTH / 2, (Player.PLAYER_HEIGHT / 2));
    	
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = polygonShape;
    	fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new BottomSensor());
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_BOTTOM_SENSOR;
        filter.categoryBits = BreakoutSettings.BOTTOM_SENSOR;
        fixture.setFilterData(filter);
        
        polygonShape.dispose();
    	
    }
    
    public static Fixture createPowerupBody(Vector2 position, World world){
    	BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(Powerup.POWERUP_HEIGHT / 2, Powerup.POWERUP_HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_POWERUP;
        filter.categoryBits = BreakoutSettings.POWERUP;
        fixture.setFilterData(filter);
        
        polygonShape.dispose();

        return fixture;
    }

    public static Fixture createBrick(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(Brick.BRICK_WIDTH / 2, Brick.BRICK_HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_BRICK;
        filter.categoryBits = BreakoutSettings.BRICK;
        fixture.setFilterData(filter);
        
        polygonShape.dispose();

        return fixture;
    }

	@Override
	public void createBrick(Vector2 position, int type) {
		Fixture brickFixture = GameStage.createBrick(this.world, position);
		this.map.addBrick(brickFixture, type);
	}

	@Override
	public void destroyBrick(Body brickBody) {
		this.world.destroyBody(brickBody);
	}
}

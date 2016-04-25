package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Map;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.MapListener;

/**
 * Created by Michael on 2016-04-11.
 */
public class GameStage implements MapListener{

    World world;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    Ball ball;
    Player player;
    Map map;

    public GameStage(){
    	
    	//World
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new BreakoutContactListener());
        
        
        //MAP
    	map = new Map();
    	map.addMapListener(this);
    	map.loadMapFromTmxFile(1);
        Box2D.init();
        
        //Ball
        ball = new Ball();
        Fixture ballfixture = GameStage.CreateCircle(Ball.BALL_RADIUS, world);
        ballfixture.setUserData(ball);
        ball.setBody(ballfixture.getBody());
        
        //Player
        player = new Player();
        Fixture playerFixture = GameStage.CreatePlayer(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT, world);
        player.setBody(playerFixture.getBody());
        
        
        //GameStage.createBrick(world, new Vector2(500, 500));
        GameStage.createWalls(world, Map.WALL_WIDTH);
        GameStage.createBottomSensor(world);
    }

    public void init(){
        ball.ballBody.applyForceToCenter(100000, 100000, false);
    }

    public void update(float time){
        int subSteps = 3;
        for (int i = 0; i < subSteps; i++){
            world.step(time/subSteps, 400, 400);
        }
    }

    public void debugRender(OrthographicCamera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void setPlayerPosition (float position){
        if (position >= BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - 30){
            player.playerBody.setTransform(new Vector2(BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - 30, Player.PLAYER_POSITION_Y), 0);
        }
        else if(position <= Map.WALL_WIDTH + 30){
            player.playerBody.setTransform(new Vector2(Map.WALL_WIDTH + 30, Player.PLAYER_POSITION_Y), 0);
        }
        else{
            player.playerBody.setTransform(new Vector2(position, Player.PLAYER_POSITION_Y), 0);
        }
    }

    public static Fixture CreateCircle(float radius, World world){
	        // First we create a body definition
	        BodyDef bodyDef = new BodyDef();
	        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        // Set our body's starting position in the world
	        bodyDef.position.set(100, 300);
	
	        // Create our body in the world using our body definition
	        Body body = world.createBody(bodyDef);
	
	        // Create a circle shape and set its radius to 6
	        CircleShape circle = new CircleShape();
	        circle.setRadius(radius);
	
	        // Create a fixture definition to apply our shape to
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = circle;
	        fixtureDef.density = 0f;
	        fixtureDef.friction = 0f;
	        fixtureDef.restitution = 1f;
	
	        // Create our fixture and attach it to the body
	        Fixture fixture = body.createFixture(fixtureDef);
	        
	        Filter filter = new Filter();
	        filter.categoryBits = BreakoutSettings.BALL;
	        filter.maskBits = BreakoutSettings.MASK_BALL;
	        
	        fixture.setFilterData(filter);
	
	        // Remember to dispose of any shapes after you're done with them!
	        // BodyDef and FixtureDef don't need disposing, but shapes do.
	        circle.dispose();
	        return fixture;
        }

    public static Fixture CreatePlayer(float width, float height, World world){
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH / 2, 100);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);


        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_PLAYER;
        filter.categoryBits = BreakoutSettings.PLAYER;
        fixture.setFilterData(filter);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
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

        wall.dispose();
    }
    
    public static void createBottomSensor(World world){
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.StaticBody;
    	bodyDef.position.set(BreakoutSettings.SCREEN_WIDTH / 2, Player.PLAYER_POSITION_Y / 2 - Player.PLAYER_HEIGHT / 2);
    	
    	Body body = world.createBody(bodyDef);
    	
    	PolygonShape polygonShape = new PolygonShape();
    	polygonShape.setAsBox(BreakoutSettings.SCREEN_WIDTH / 2, ((Player.PLAYER_POSITION_Y / 2) - Player.PLAYER_HEIGHT / 2));
    	
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = polygonShape;
    	fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        
        Filter filter = new Filter();
        filter.maskBits = BreakoutSettings.MASK_BOTTOM_SENSOR;
        filter.categoryBits = BreakoutSettings.BOTTOM_SENSOR;
        fixture.setFilterData(filter);
        
        polygonShape.dispose();
    	
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
	public void createBrick(Vector2 position) {
		Fixture brickFixture = GameStage.createBrick(world, position);
		map.addBrick(brickFixture);
	}
}

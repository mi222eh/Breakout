package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.Entities.Ball;
import com.mygdx.game.Model.Entities.Brick;
import com.mygdx.game.Model.Entities.Player;
import com.mygdx.game.Settings.BreakoutSettings;

/**
 * Created by Michael on 2016-04-11.
 */
public class GameStage {

    private float playerPosY;

    World world;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    Ball ball;
    Player player;
    float wallWidth;

    public GameStage(){
        playerPosY = 75;
        wallWidth = 16;
        Box2D.init();
        world = new World(new Vector2(0,0), false);
        ball = new Ball(GameStage.CreateCircle(Ball.BALL_RADIUS, world));
        player = new Player(GameStage.CreatePlayer(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT, world));

        GameStage.createWalls(world, wallWidth);
    }

    public void init(){
        ball.ballBody.applyForceToCenter(1000000, 1000000, false);
    }

    public void update(float time){
        int subSteps = 13;
        for (int i = 0; i < subSteps; i++){
            world.step(time/subSteps, 400, 400);
        }
    }

    public void debugRender(OrthographicCamera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void setPlayerPosition (float position){
        System.out.println(position);
        if (position >= BreakoutSettings.SCREEN_WIDTH - wallWidth - 30){
            player.playerBody.setTransform(new Vector2(BreakoutSettings.SCREEN_WIDTH - wallWidth - 30, playerPosY), 0);
        }
        else if(position <= wallWidth + 30){
            player.playerBody.setTransform(new Vector2(wallWidth + 30, playerPosY), 0);
        }
        else{
            player.playerBody.setTransform(new Vector2(position, playerPosY), 0);
        }
    }






    public static Body CreateCircle(float radius, World world){
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

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
        return body;
        }

    public static Body CreatePlayer(float width, float height, World world){
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

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        box.dispose();
        return body;
    }

    public static void createWalls(World world, float wallWidth){

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

        wall.dispose();
    }

    public static void createBrick(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(Brick.BRICK_WIDTH / 2, Brick.BRICK_HEIGHT / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        Fixture fixture = body.createFixture(fixtureDef);


    }
}

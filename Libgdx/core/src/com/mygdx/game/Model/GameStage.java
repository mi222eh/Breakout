package com.mygdx.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.Entities.Ball;
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

    public GameStage(){
        playerPosY = 100;
        Box2D.init();
        world = new World(new Vector2(0,0), false);
        ball = new Ball(GameStage.CreateCircle(15f, world));
        player = new Player(GameStage.CreatePlayer(35f, 15f, world));
    }

    public void init(){

    }

    public void update(float time){
        int subSteps = 10;
        for (int i = 0; i < 3; i++){
            world.step(time/subSteps, 400, 400);
        }

    }

    public void debugRender(OrthographicCamera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void setPlayerPosition (float position){
        player.playerBody.setTransform(new Vector2(position, 1080 - Gdx.input.getY()), 0);
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
        fixtureDef.density = 1f;
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

    public static void createWalls(World world){
        BodyDef bodyDef = new BodyDef();


    }
}

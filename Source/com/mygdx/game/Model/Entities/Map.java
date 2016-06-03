package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Model.MapCreate.BrickCreate;
import com.mygdx.game.Model.MapCreate.MapCreate;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.GameSoundListener;
import com.mygdx.game.interfaces.MapListener;

/**
 * Created by Michael on 2016-04-18.
 */

public class Map {
	
	public static int WALL_WIDTH = 32;
	
	MapListener mapListener;
	public GameSoundListener soundListener;
	
	public boolean hasWon;
	public int numberofBricksTotal = 0;
	public Array<Brick> invulnerableBricks;
	public Array<Brick> bricks;
	private Pool<Brick> inactiveBricks;
	public Map(){
		hasWon = false;
		this.invulnerableBricks = new Array<Brick>();
		this.bricks = new Array<Brick>();
		this.inactiveBricks = new Pool<Brick>() {
			@Override
			protected Brick newObject() {
				Map.this.numberofBricksTotal++;
				return new Brick(Map.this.numberofBricksTotal);
			}
		};
	}
	public void update(){
		if(this.bricks.size == 0){
			hasWon = true;
		}
		for (int i = 0; i < this.bricks.size; i++) {
			if(!this.bricks.get(i).isAlive()){
				BrickType type = this.bricks.get(i).brickType;
				if(type == BrickType.normal1){
					this.powerupHandler(this.bricks.get(i).brickBody.getPosition());
				}
				this.mapListener.destroyBrick(this.bricks.get(i).brickBody);
				this.inactiveBricks.free(this.bricks.get(i));
				this.bricks.removeIndex(i);
			}
		}
	}
	
	private void powerupHandler(Vector2 position){
		float number = (float)Math.random() * 100;
		if(number < 20){
			this.mapListener.createPowerup(position);
		}
		
	}
	
	public void addMapListener(MapListener listener){
		this.mapListener = listener;
	}
	
	public void reset(){
		hasWon = false;
		for (int i = 0; i < this.bricks.size; i++) {
			Brick brick = this.bricks.get(i);
			this.mapListener.destroyBrick(brick.brickBody);
		}
		for (int i = 0; i < this.invulnerableBricks.size; i++) {
			Brick brick = this.invulnerableBricks.get(i);
			this.mapListener.destroyBrick(brick.brickBody);
		}
		this.inactiveBricks.freeAll(this.invulnerableBricks);
		this.inactiveBricks.freeAll(this.bricks);
		this.bricks.clear();
		this.invulnerableBricks.clear();
	}
	
	public void loadMapFromTmxFile(int fileNumber){
		this.reset();
		TmxMapLoader tmx = new TmxMapLoader();
		TiledMap map = tmx.load(BreakoutSettings.TMX_MAPS_LOCATION + fileNumber + ".tmx");
		
		MapLayer layer = map.getLayers().get("bricks");
		MapObjects objects = layer.getObjects();
		int numberofobjects = objects.getCount();
		for (int i = 0; i < numberofobjects; i++) {
			MapObject object = objects.get(i);
			float x = object.getProperties().get("x", float.class);
			float y = object.getProperties().get("y", float.class);
			int type = Integer.parseInt(object.getProperties().get("type", String.class));
			this.mapListener.createBrick(new Vector2(x, y), type);
		}
	}
	
	public void addBrick(Fixture fixture, int type){
		Brick brick = this.inactiveBricks.obtain();
		fixture.setUserData(brick);
		brick.setBody(fixture.getBody());
		switch (type) {
		case BreakoutSettings.BRICK_NORMAL1:
			brick.setType(BrickType.normal1);;
			break;
		case BreakoutSettings.BRICK_NORMAL2:
			brick.setType(BrickType.normal2);;
			break;
		case BreakoutSettings.BRICK_NORMAL3:
			brick.setType(BrickType.normal3);;
			break;
		case BreakoutSettings.BRICK_STEEL1:
			brick.setType(BrickType.steel1);;
			break;
		case BreakoutSettings.BRICK_STEEL2:
			brick.setType(BrickType.steel2);;
			break;
		case BreakoutSettings.BRICK_STEEL3:
			brick.setType(BrickType.steel3);;
			break;
		case BreakoutSettings.BRICK_INVISIBLE:
			brick.setType(BrickType.invisible);;
			break;
		case BreakoutSettings.BRICK_INVURNERABLE:
			brick.setType(BrickType.invurnerable);;
			break;

		default:
			brick.setType(BrickType.undefined);
			break;
		}
		if(brick.brickType == BrickType.invurnerable){
			this.invulnerableBricks.add(brick);
		}
		else{
			this.bricks.add(brick);
		}
	}
	public void loadObject(MapCreate map) {
		for (int i = 0; i < map.bricks.size(); i++) {
			BrickCreate brick = map.bricks.get(i);
			this.mapListener.createBrick(new Vector2(brick.x, brick.y), brick.type);
		}
	}
	public void loadJsonMap(String jsonText){
		JsonValue BricksObject = new JsonReader().parse(jsonText);
		JsonValue Bricks = BricksObject.get("bricks");
		for (int i = 0; i < Bricks.size; i++) {
			JsonValue brick = Bricks.get(i);
			float x = brick.getFloat("x");
			float y = brick.getFloat("y");
			int type = brick.getInt("type");
			this.mapListener.createBrick(new Vector2(x , y), type);
		}
	}
}

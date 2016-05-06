package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Model.Entities.Brick.BrickType;
import com.mygdx.game.Settings.BreakoutSettings;
import com.mygdx.game.interfaces.MapListener;

/**
 * Created by Michael on 2016-04-18.
 */

public class Map {
	
	public static int WALL_WIDTH = 16;
	
	MapListener mapListener;
	
	public int numberofBricksTotal = 0;
	Array<Brick> bricks;
	Pool<Brick> inactiveBricks;
	public Map(){
		bricks = new Array<Brick>();
		inactiveBricks = new Pool<Brick>() {
			@Override
			protected Brick newObject() {
				numberofBricksTotal++;
				return new Brick(numberofBricksTotal);
			}
		};
	}
	public void update(){
		for (int i = 0; i < bricks.size; i++) {
			if(!bricks.get(i).isAlive()){
				mapListener.destroyBrick(bricks.get(i).brickBody);
				inactiveBricks.free(bricks.get(i));
				bricks.removeIndex(i);
			}
		}
	}
	
	public void addMapListener(MapListener listener){
		mapListener = listener;
	}
	
	public void reset(){
		inactiveBricks.freeAll(bricks);
		bricks.clear();
	}
	
	public void loadMapFromTmxFile(int fileNumber){
		reset();
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
			mapListener.createBrick(new Vector2(x, y), type);
		}
	}
	
	public void addBrick(Fixture fixture, int type){
		Brick brick = inactiveBricks.obtain();
		fixture.setUserData(brick);
		brick.setBody(fixture.getBody());
		bricks.add(brick);
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
	}
}

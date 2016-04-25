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
	
	public void addMapListener(MapListener listener){
		mapListener = listener;
	}
	
	private void reset(){
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
			mapListener.createBrick(new Vector2(x, y));
		}
	}
	
	public void addBrick(Fixture fixture){
		Brick brick = inactiveBricks.obtain();
		fixture.setUserData(brick);
		brick.setBody(fixture.getBody());
		bricks.add(brick);
		}
}

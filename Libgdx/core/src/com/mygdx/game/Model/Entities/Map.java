package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Settings.BreakoutSettings;

/**
 * Created by Michael on 2016-04-18.
 */

public class Map {
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
	
	public void loadMapFromTmxFile(int fileNumber){
		TmxMapLoader tmx = new TmxMapLoader();
		TiledMap map = tmx.load(BreakoutSettings.TMX_MAPS_LOCATION + fileNumber);
		
		MapLayer layer = map.getLayers().get("bricks");
		MapObjects objects = layer.getObjects();
		int numberofobjects = objects.getCount();
	}
}

package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Settings.BreakoutSettings;

/**
 * Created by Michael on 2016-04-11.
 */
public class Player {
	public enum PlayerWidth{
		Smallest(15),
		Small(25),
		Normal(45),
		Large(60), 
		Largest(80);
		private int val;
		private PlayerWidth(int val){
			this.val = val;
		}
		public int getVal(){
			return val;
		}
	}
	public PlayerWidth width;
    public static float PLAYER_HEIGHT = 8f;
    public static float PLAYER_POSITION_Y = 75f;
    private Body bodySmallest;
    private Body bodySmall;
    private Body bodyNormal;
    private Body bodyLarge;
    private Body bodyLargest;
    
    public Player(){
    	width = PlayerWidth.Normal;
    }
    public void setBodies(Body bodySmallest, Body bodySmall, Body bodyNormal,Body bodyLarge,Body bodyLargest){
    	this.bodySmallest = bodySmallest;
    	this.bodySmall = bodySmall;
    	this.bodyNormal = bodyNormal;
    	this.bodyLarge = bodyLarge;
    	this.bodyLargest = bodyLargest;
    }
    public void init(){
    	width = PlayerWidth.Normal;
    	updateBodiesActive();
    }
    public Body getActiveBody(){
    	switch (width) {
		case Smallest:
			return bodySmallest;
		case Small:
			return bodySmall;
		case Normal:
			return bodyNormal;
		case Large:
			return bodyLarge;
		case Largest:
			return bodyLargest;
		default:
			return null;
		}
    }
    
    public void movePlayer(float positionX){
    	float newPosition;
        if (positionX >= BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - width.getVal()){
        	newPosition = BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - width.getVal();
        }
        else if(positionX <= Map.WALL_WIDTH + width.getVal()){
        	newPosition = Map.WALL_WIDTH + width.getVal();
        }
        else{
            newPosition = positionX;
        }
    	this.bodySmallest.setTransform(newPosition, PLAYER_POSITION_Y, 0);
    	this.bodySmall.setTransform(newPosition, PLAYER_POSITION_Y, 0);
    	this.bodyNormal.setTransform(newPosition, PLAYER_POSITION_Y, 0);
    	this.bodyLarge.setTransform(newPosition, PLAYER_POSITION_Y, 0);
    	this.bodyLargest.setTransform(newPosition, PLAYER_POSITION_Y, 0);
    }
    
    public void makeBigger(){
    	switch (width){
		case Large:
			width = PlayerWidth.Largest;
			break;
		case Largest:
			width = PlayerWidth.Largest;
			break;
		case Normal:
			width = PlayerWidth.Large;
			break;
		case Small:
			width = PlayerWidth.Normal;
			break;
		case Smallest:
			width = PlayerWidth.Small;
			break;
		default:
			break;
    	}
    	updateBodiesActive();
    }
    
    private void updateBodiesActive(){
    	bodySmallest.setActive(false);
    	bodySmall.setActive(false);
    	bodyNormal.setActive(false);
    	bodyLarge.setActive(false);
    	bodyLargest.setActive(false);
    	switch (width){
		case Large:
			bodyLarge.setActive(true);
			break;
		case Largest:
			bodyLargest.setActive(true);
			break;
		case Normal:
			bodyNormal.setActive(true);
			break;
		case Small:
			bodySmall.setActive(true);
			break;
		case Smallest:
			bodySmallest.setActive(true);
			break;
		default:
			break;
    		
    	}
    }
    
    public void makeSmaller(){
    	switch (width){
		case Large:
			width = PlayerWidth.Normal;
			break;
		case Largest:
			width = PlayerWidth.Large;
			break;
		case Normal:
			width = PlayerWidth.Small;
			break;
		case Small:
			width = PlayerWidth.Smallest;
			break;
		case Smallest:
			width = PlayerWidth.Smallest;
			break;
		default:
			break;
    	}
    	updateBodiesActive();
    }
}

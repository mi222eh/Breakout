package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Settings.BreakoutSettings;

/**
 * Created by Michael on 2016-04-11.
 */
public class Player {
	public enum PlayerWidth{
		Smallest(15),
		Small(30),
		Normal(50),
		Large(70), 
		Largest(100);
		private int val;
		private PlayerWidth(int val){
			this.val = val;
		}
		public int getVal(){
			return this.val;
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
    	this.width = PlayerWidth.Normal;
    }
    public void setBodies(Body bodySmallest, Body bodySmall, Body bodyNormal,Body bodyLarge,Body bodyLargest){
    	this.bodySmallest = bodySmallest;
    	this.bodySmall = bodySmall;
    	this.bodyNormal = bodyNormal;
    	this.bodyLarge = bodyLarge;
    	this.bodyLargest = bodyLargest;
    }
    public void init(){
    	this.width = PlayerWidth.Normal;
    	this.updateBodiesActive();
    }
    public Body getActiveBody(){
    	switch (this.width) {
		case Smallest:
			return this.bodySmallest;
		case Small:
			return this.bodySmall;
		case Normal:
			return this.bodyNormal;
		case Large:
			return this.bodyLarge;
		case Largest:
			return this.bodyLargest;
		default:
			return null;
		}
    }
    
    public void movePlayer(float positionX){
    	float newPosition;
        if (positionX >= BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - this.width.getVal()){
        	newPosition = BreakoutSettings.SCREEN_WIDTH - Map.WALL_WIDTH - this.width.getVal();
        }
        else if(positionX <= Map.WALL_WIDTH + this.width.getVal()){
        	newPosition = Map.WALL_WIDTH + this.width.getVal();
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
    	switch (this.width){
		case Large:
			this.width = PlayerWidth.Largest;
			break;
		case Largest:
			this.width = PlayerWidth.Largest;
			break;
		case Normal:
			this.width = PlayerWidth.Large;
			break;
		case Small:
			this.width = PlayerWidth.Normal;
			break;
		case Smallest:
			this.width = PlayerWidth.Small;
			break;
		default:
			break;
    	}
    	this.updateBodiesActive();
    }
    
    private void updateBodiesActive(){
    	this.bodySmallest.setActive(false);
    	this.bodySmall.setActive(false);
    	this.bodyNormal.setActive(false);
    	this.bodyLarge.setActive(false);
    	this.bodyLargest.setActive(false);
    	switch (this.width){
		case Large:
			this.bodyLarge.setActive(true);
			break;
		case Largest:
			this.bodyLargest.setActive(true);
			break;
		case Normal:
			this.bodyNormal.setActive(true);
			break;
		case Small:
			this.bodySmall.setActive(true);
			break;
		case Smallest:
			this.bodySmallest.setActive(true);
			break;
		default:
			break;
    		
    	}
    }
    
    public void makeSmaller(){
    	switch (this.width){
		case Large:
			this.width = PlayerWidth.Normal;
			break;
		case Largest:
			this.width = PlayerWidth.Large;
			break;
		case Normal:
			this.width = PlayerWidth.Small;
			break;
		case Small:
			this.width = PlayerWidth.Smallest;
			break;
		case Smallest:
			this.width = PlayerWidth.Smallest;
			break;
		default:
			break;
    	}
    	this.updateBodiesActive();
    }
}

package com.mygdx.game.Settings;

/**
 * Created by Michael on 2016-04-10.
 */
public class BreakoutSettings {
    public static int SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT = 720;
    public static boolean Sound_On = true;
    public static float TIME_STEP = 1/180f;
    public static float MIN_TIME = 0.25f;
    
    public static String TMX_MAPS_LOCATION = "maps/";
    
    //CATEGORY BITS
    public static final short PLAYER = 0x0001;
    public static final short BALL = 0x0002;
    public static final short BRICK = 0x0004;
    public static final short POWERUP = 0x0008;
    public static final short WALL = 0x00016;
    public static final short BOTTOM_SENSOR = 0x0032;
    
    //MASK BITS
    public static final short MASK_PLAYER = PLAYER | BALL | POWERUP;
    public static final short MASK_BALL = PLAYER | BRICK | WALL | BOTTOM_SENSOR;
    public static final short MASK_BRICK = BRICK | BALL;
    public static final short MASK_POWERUP = PLAYER | BOTTOM_SENSOR | WALL;
    public static final short MASK_WALL = WALL | BALL | POWERUP;
    public static final short MASK_BOTTOM_SENSOR = BALL | POWERUP;
    
    //TYPE DEFINITIONS
    public static final int BRICK_NORMAL1 = 1;
    public static final int BRICK_NORMAL2 = 2;
    public static final int BRICK_NORMAL3 = 3;
    public static final int BRICK_STEEL1 = 4;
    public static final int BRICK_STEEL2 = 5;
    public static final int BRICK_STEEL3 = 6;
    public static final int BRICK_INVISIBLE = 7;
    public static final int BRICK_INVURNERABLE = 0;
}

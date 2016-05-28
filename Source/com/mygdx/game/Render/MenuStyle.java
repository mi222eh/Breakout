package com.mygdx.game.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuStyle {
	public static TextButtonStyle style, InGameStyle;
	
	public static void loadStyle(){
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/GameFont.fnt"));
		
		Texture buttonUP = new Texture("skin/ButtonUP.png");
		Texture buttonDOWN = new Texture("skin/ButtonDOWN.png");
		Texture buttonHOVER = new Texture("skin/ButtonHOVER.png");
		
		style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(buttonUP));
		style.over = new TextureRegionDrawable(new TextureRegion(buttonHOVER)); 
		style.down = new TextureRegionDrawable(new TextureRegion(buttonDOWN)); 
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.font = font;
		
		Texture backButtonUP = new Texture("skin/BackButtonUP.png");
		Texture backButtonDOWN = new Texture("skin/BackButtonDOWN.png");
		Texture backButtonHOVER = new Texture("skin/BackButtonHOVER.png");
		
		InGameStyle = new TextButtonStyle();
		InGameStyle.up = new TextureRegionDrawable(new TextureRegion(backButtonUP));
		InGameStyle.over = new TextureRegionDrawable(new TextureRegion(backButtonHOVER)); 
		InGameStyle.down = new TextureRegionDrawable(new TextureRegion(backButtonDOWN)); 
		InGameStyle.font = font;
		
		
	}
}

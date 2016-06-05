package com.mygdx.game.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Model.MapCreateModel;

public class MenuTextures {
	public static Sprite MenuNormal1, 
	MenuNormal2, 
	MenuNormal3, 
	MenuSteel1, 
	MenuSteel2, 
	MenuSteel3, 
	MenuInvurnerable, 
	MenuInvisible, 
	Hover, 
	Selected;
	
	public static void loadTextures(){

		MenuNormal1 = new Sprite(new Texture("Textures/MenuNormal1.png"));
		MenuNormal2 = new Sprite(new Texture("Textures/MenuNormal2.png"));
		MenuNormal3 = new Sprite(new Texture("Textures/MenuNormal3.png"));
		MenuSteel1 = new Sprite(new Texture("Textures/MenuSteel1.png"));
		MenuSteel2 = new Sprite(new Texture("Textures/MenuSteel2.png"));
		MenuSteel3 = new Sprite(new Texture("Textures/MenuSteel3.png"));
		MenuInvurnerable = new Sprite(new Texture("Textures/MenuInvurnerable.png"));
		MenuInvisible = new Sprite(new Texture("Textures/MenuInvisible.png"));
		Hover = new Sprite(new Texture("Textures/HoverEffekt.png"));
		Selected = new Sprite(new Texture("Textures/SelectedEffekt.png"));
		
		
		setSize(MenuNormal1);
		setSize(MenuNormal2);
		setSize(MenuNormal3);
		setSize(MenuSteel1);
		setSize(MenuSteel2);
		setSize(MenuSteel3);
		setSize(MenuInvurnerable);
		setSize(Hover);
		setSize(Selected);
	}
	
	private static void setSize(Sprite sprite){
		sprite.setSize(MapCreateModel.ItemMenuWidth, MapCreateModel.ItemMenuHeight);
	}

}

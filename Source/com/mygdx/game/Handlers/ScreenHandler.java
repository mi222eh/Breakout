package com.mygdx.game.Handlers;

import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Screens.CreateMapScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;

/**
 * Created by Michael on 2016-04-10.
 */
public class ScreenHandler {
    private GameScreen gameScreen;
    private MainMenuScreen menuScreen;
    private CreateMapScreen createScreen;
    public enum ScreenType{
        GameScreen,
        MenuScreen,
        CreateMapScreen
    }
    
    public ScreenHandler(BreakoutGame breakoutGame){
        this.gameScreen = new GameScreen(breakoutGame);
    	this.menuScreen = new MainMenuScreen(breakoutGame);
		this.createScreen = new CreateMapScreen(breakoutGame);
    }

    public Screen getScreen(ScreenType type){
        switch (type) {
            case GameScreen:
                return this.gameScreen;
		case MenuScreen:
            return this.menuScreen;
		case CreateMapScreen:
			return this.createScreen;
		default:
			break;
        }
        return null;
    }
    
}

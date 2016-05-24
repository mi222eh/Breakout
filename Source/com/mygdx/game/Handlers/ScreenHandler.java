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

    public Screen getScreen(ScreenType type, BreakoutGame breakoutGame){
        switch (type) {
            case GameScreen:
                if (gameScreen == null){
                    gameScreen = new GameScreen(breakoutGame);
                }
                return gameScreen;
		case MenuScreen:
            if (menuScreen == null){
            	menuScreen = new MainMenuScreen(breakoutGame);
            }
            return menuScreen;
		case CreateMapScreen:
			if(createScreen == null){
				createScreen = new CreateMapScreen(breakoutGame);
			}
			return createScreen;
		default:
			break;
        }
        return null;
    }
    
}

package com.mygdx.game.Handlers;

import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Screens.GameScreen;

import java.util.Enumeration;

/**
 * Created by Michael on 2016-04-10.
 */
public class ScreenHandler {
    private GameScreen gameScreen;
    public enum ScreenType{
        GameScreen
    }

    public Screen getScreen(ScreenType type, BreakoutGame breakoutGame){
        switch (type) {
            case GameScreen:
                if (gameScreen == null){
                    gameScreen = new GameScreen(breakoutGame);
                }
                return gameScreen;
        }
        return null;
    }
}

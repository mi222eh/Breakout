package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BreakoutGame;
import com.mygdx.game.Model.GameStage;
import com.mygdx.game.Settings.BreakoutSettings;

/**
 * Created by Michael on 2016-04-10.
 */
public class GameScreen implements Screen{
    private BreakoutGame game;
    private float accumulator = 0f;
    GameStage gameStage;

    public GameScreen(BreakoutGame breakoutGame) {
        this.game = breakoutGame;
    }

    @Override
    public void show() {
        gameStage = new GameStage();
        gameStage.init();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePos = game.camera.unproject(mousePos);
        gameStage.setPlayerPosition(mousePos.x);

        gameStage.debugRender(game.camera);

        float frameTime = Math.min(delta, BreakoutSettings.MIN_TIME);
        accumulator += frameTime;
        while(accumulator >= BreakoutSettings.TIME_STEP){
            gameStage.update(BreakoutSettings.TIME_STEP);
            accumulator -= BreakoutSettings.TIME_STEP;
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

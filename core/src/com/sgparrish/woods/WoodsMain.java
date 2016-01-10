package com.sgparrish.woods;

import com.badlogic.gdx.Game;
import com.sgparrish.woods.game.GameScreen;

public class WoodsMain extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}

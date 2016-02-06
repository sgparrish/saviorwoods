package com.sgparrish.woods;

import com.badlogic.gdx.Game;
import com.sgparrish.woods.game.GameScreen;
import com.sgparrish.woods.game.PhysicsScreen;

public class WoodsMain extends Game {

    @Override
    public void create() {
        //setScreen(new GameScreen());
        setScreen(new PhysicsScreen());
    }
}

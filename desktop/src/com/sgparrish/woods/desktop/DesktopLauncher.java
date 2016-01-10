package com.sgparrish.woods.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sgparrish.woods.WoodsMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Woods";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new WoodsMain(), config);
	}
}

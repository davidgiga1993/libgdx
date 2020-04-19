package com.mygdx.game;


import com.badlogic.gdx.backends.lwjgl3.GlMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
		config.setGlMode(GlMode.GLES_20, 0, 0);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}

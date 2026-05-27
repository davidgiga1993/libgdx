
package com.badlogic.gdx.backends.lwjgl3.angle;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ANGLELoaderTest {
	@Test
	public void detectMacOsAppBundle () {
		assertFalse(ANGLELoader.isAppBundlePath("/"));
		assertTrue(ANGLELoader.isAppBundlePath("/."));
		assertTrue(ANGLELoader.isAppBundlePath("/Some App.app"));
		assertTrue(ANGLELoader.isAppBundlePath("/Some App.app/."));
		assertTrue(ANGLELoader.isAppBundlePath("/Some App.app/Contents"));
		assertTrue(ANGLELoader.isAppBundlePath("/Some App.app/Contents/."));

		assertFalse(ANGLELoader.isAppBundlePath("/Some App.appother"));
		assertFalse(ANGLELoader.isAppBundlePath("/other/stuff"));
	}
}

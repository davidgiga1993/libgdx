package org.devcore.jni;

/**
 * Listens for multitouch events
 */
public interface MultitouchProcessor {

	void onTouch(int x, int y, int pointer, int mode, int button);
}


package com.badlogic.gdx.backends.lwjgl3.touch;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import org.devcore.touch.MultitouchListener;
import org.devcore.touch.linux.X11Multitouch;
import org.lwjgl.glfw.GLFWNativeX11;

public class LwjglX11Multitouch extends X11Multitouch implements LwjglMultitouch {

	@Override
	public void addWindow (Lwjgl3Window window, MultitouchListener processor) {
		long hwnd = getHwid(window);
		addWindow(hwnd, processor);
	}

	@Override
	public boolean alsoHandlesMouseInput () {
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	protected void nativeOnTouchCallback (long hwnd, int x, int y, int pointer, int mode, int button) {
		// Re-map the button constants
		super.nativeOnTouchCallback(hwnd, x, y, pointer, mode, Input.Buttons.LEFT);
	}

	private static long getHwid (Lwjgl3Window window) {
		return GLFWNativeX11.glfwGetX11Window(window.getWindowHandle());
	}
}

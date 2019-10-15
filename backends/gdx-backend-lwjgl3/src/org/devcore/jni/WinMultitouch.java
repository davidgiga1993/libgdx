package org.devcore.jni;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.utils.Disposable;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinMultitouch implements Disposable {
	public static final int POINTER_DOWN = 0;
	public static final int POINTER_UP = 1;
	public static final int POINTER_MOVE = 2;


	public static final int BUTTON_1 = 0;
	public static final int BUTTON_2 = 1;
	public static final int BUTTON_3 = 2;
	public static final int BUTTON_4 = 3;
	public static final int BUTTON_5 = 4;

	private final List<Long> activeHwnd = new ArrayList<>();
	private final Map<Long, MultitouchProcessor> callbacks = new HashMap<>();

	private native int initTouch(long hwnd);

	private native void dispose(long hwnd);

	public WinMultitouch() {
		System.load("WinMultitouch.dll");
	}

	@Override
	public void dispose() {
		for (long hwnd : activeHwnd) {
			dispose(hwnd);
		}
		activeHwnd.clear();
	}

	public void addWindow(Lwjgl3Window window, MultitouchProcessor processor) {
		if (!Thread.currentThread().getName().equals("main")) {
			throw new RuntimeException("Initialization must be done from the main thread");
		}
		long hwnd = getHwid(window);
		if (initTouch(hwnd) != 0) {
			throw new RuntimeException("Touch init failed");
		}

		activeHwnd.add(hwnd);
		callbacks.put(hwnd, processor);
	}


	@SuppressWarnings("unused")
	private void nativeOnTouchCallback(long hwnd, int x, int y, int pointer, int mode, int button) {
		switch (button)
		{
			case BUTTON_1:
			default:
				button = Input.Buttons.LEFT;
				break;
			case BUTTON_2:
				button = Input.Buttons.RIGHT;
				break;
			case BUTTON_3:
				button = Input.Buttons.MIDDLE;
				break;
			case BUTTON_4:
				button = Input.Buttons.BACK;
				break;
			case BUTTON_5:
				button = Input.Buttons.FORWARD;
				break;
		}

		MultitouchProcessor processor = callbacks.get(hwnd);
		if (processor != null)
			processor.onTouch(x, y, pointer, mode, button);
	}

	private static long getHwid(Lwjgl3Window window) {
		return GLFWNativeWin32.glfwGetWin32Window(window.getWindowHandle());
	}
}

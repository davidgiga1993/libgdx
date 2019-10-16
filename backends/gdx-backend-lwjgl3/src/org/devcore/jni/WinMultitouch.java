package org.devcore.jni;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.SharedLibraryLoader;

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
		new SharedLibraryLoader().load("WinMultitouch");
	}

	@Override
	public void dispose() {
		for (long hwnd : activeHwnd) {
			dispose(hwnd);
		}
		activeHwnd.clear();
	}

	public void addWindow(long hwnd, MultitouchProcessor processor) {
		if (!Thread.currentThread().getName().equals("main")) {
			throw new RuntimeException("Initialization must be done from the main thread");
		}
		if (initTouch(hwnd) != 0) {
			throw new RuntimeException("Touch init failed");
		}
		activeHwnd.add(hwnd);
		callbacks.put(hwnd, processor);
	}


	@SuppressWarnings("unused")
	protected void nativeOnTouchCallback(long hwnd, int x, int y, int pointer, int mode, int button) {
		MultitouchProcessor processor = callbacks.get(hwnd);
		if (processor != null) {
			processor.onTouch(x, y, pointer, mode, button);
		}
	}
}

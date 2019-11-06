package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.Input;

import org.devcore.win.Multitouch;
import org.devcore.win.MultitouchProcessor;
import org.lwjgl.glfw.GLFWNativeWin32;

public class LwjglWinMultitouch extends Multitouch
{
	public void addWindow(Lwjgl3Window window, MultitouchProcessor processor)
	{
		long hwnd = getHwid(window);
		addWindow(hwnd, processor);
	}

	@SuppressWarnings("unused")
	@Override
	protected void nativeOnTouchCallback(long hwnd, int x, int y, int pointer, int mode, int button)
	{
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

		super.nativeOnTouchCallback(hwnd, x, y, pointer, mode, button);
	}

	private static long getHwid(Lwjgl3Window window)
	{
		return GLFWNativeWin32.glfwGetWin32Window(window.getWindowHandle());
	}
}

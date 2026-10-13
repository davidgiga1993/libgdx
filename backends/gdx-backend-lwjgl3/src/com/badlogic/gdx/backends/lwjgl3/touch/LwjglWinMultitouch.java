
package com.badlogic.gdx.backends.lwjgl3.touch;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import org.devcore.touch.MultitouchListener;
import org.devcore.touch.win.WinMultitouch;
import org.lwjgl.glfw.GLFWNativeWin32;

import static org.devcore.touch.Consts.*;

public class LwjglWinMultitouch extends WinMultitouch implements LwjglMultitouch {

    @Override
    public void addWindow(Lwjgl3Window window, MultitouchListener processor) {
        long hwnd = getHwid(window);
        addWindow(hwnd, processor);
    }

    @Override
    public boolean alsoHandlesMouseInput() {
        return true;
    }

    @SuppressWarnings("unused")
    @Override
    protected void nativeOnTouchCallback(long hwnd, int x, int y, int pointer, int mode, int button) {
        // Re-map the button constants
        switch (button) {
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
            case BUTTON_1:
            default:
                button = Input.Buttons.LEFT;
                break;
        }

        super.nativeOnTouchCallback(hwnd, x, y, pointer, mode, button);
    }

    private static long getHwid(Lwjgl3Window window) {
        return GLFWNativeWin32.glfwGetWin32Window(window.getWindowHandle());
    }
}

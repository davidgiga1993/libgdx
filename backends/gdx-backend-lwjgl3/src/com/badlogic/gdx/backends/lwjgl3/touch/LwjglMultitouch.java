package com.badlogic.gdx.backends.lwjgl3.touch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import org.devcore.touch.Multitouch;
import org.devcore.touch.MultitouchListener;

public interface LwjglMultitouch extends Multitouch {
    /**
     * Adds a window to listener for multitouch events
     */
    void addWindow(Lwjgl3Window window, MultitouchListener processor);

    /**
     * Returns true if this multitouch handler also processes
     * the regular mouse inputs
     *
     * @return True if mouse inputs are also handled
     */
    boolean alsoHandlesMouseInput();
}

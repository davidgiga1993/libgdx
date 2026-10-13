
package com.badlogic.gdx.backends.lwjgl3;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.badlogic.gdx.backends.lwjgl3.touch.LwjglMultitouch;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.badlogic.gdx.utils.Array;
import org.devcore.touch.MultitouchListener;

import static org.devcore.touch.Consts.*;

/**
 * Async input processing
 */
public class MultitouchDefaultLwjgl3Input extends DefaultLwjgl3Input {

    private LwjglMultitouch multitouchInput;

    public MultitouchDefaultLwjgl3Input(Lwjgl3Window window, LwjglMultitouch multitouchInput) {
        super(window);
        this.multitouchInput = multitouchInput;
        windowHandleChanged(window.getWindowHandle());
    }

    @Override
    public void dispose() {
        if (multitouchInput != null) {
            multitouchInput.removeWindow(window.getWindowHandle());
        }
        super.dispose();
    }

    @Override
    protected void registerLwjglTouchHandler() {
        if (multitouchInput == null) {
            super.registerLwjglTouchHandler();
            return;
        }

        if (!multitouchInput.alsoHandlesMouseInput()) {
            // The multitouch handler ONLY does touch input
            // so lets also register the default handler
            super.registerLwjglTouchHandler();

        }
        registerNativeTouchHandler();
    }

    private void registerNativeTouchHandler() {
        MultitouchListener processor = new MultitouchListener() {
            private final Array<Integer> activePointers = new Array<>(10);

            @Override
            public void onTouch(int x, int y, int pointerId, int mode, int button) {
                if (window.getConfig().hdpiMode == HdpiMode.Pixels) {
                    float xScale = window.getGraphics().getBackBufferWidth() / (float) window.getGraphics().getLogicalWidth();
                    float yScale = window.getGraphics().getBackBufferHeight() / (float) window.getGraphics().getLogicalHeight();
                    x = (int) (x * xScale);
                    y = (int) (y * yScale);
                }

                long time = System.nanoTime();
                int pointerIndex = getPointerIndex(pointerId);
                switch (mode) {
                    case POINTER_DOWN:
                        eventQueue.touchDown(x, y, pointerId, button, time);
                        activePointers.add(pointerId);
                        break;
                    case POINTER_MOVE:
                        if (pointerIndex != -1) {
                            eventQueue.touchDragged(x, y, pointerId, time);
                            return;
                        }
                        eventQueue.mouseMoved(x, y, time);
                        return;
                    case POINTER_UP:
                        eventQueue.touchUp(x, y, pointerId, button, time);
                        if (pointerIndex < 0 || pointerIndex >= activePointers.size) return;
                        activePointers.removeIndex(pointerIndex);
                        break;
                }
            }

            private int getPointerIndex(int pointer) {
                for (int X = 0; X < activePointers.size; X++) {
                    if (activePointers.get(X) == pointer) {
                        return X;
                    }
                }
                return -1;
            }
        };

        try {
            multitouchInput.addWindow(window, processor);
        } catch (RuntimeException e) {
            System.err.println("Could not register multitouch: " + e.getMessage());
            // Fallback to lwjgl
            multitouchInput.dispose();
            multitouchInput = null;
            registerLwjglTouchHandler();
        }
    }
}

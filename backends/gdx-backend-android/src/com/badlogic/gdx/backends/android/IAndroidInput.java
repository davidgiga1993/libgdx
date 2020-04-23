package com.badlogic.gdx.backends.android;

import android.view.View;
import com.badlogic.gdx.Input;

public interface IAndroidInput extends Input, View.OnKeyListener, View.OnTouchListener {


	void setKeyboardAvailable(boolean keyboardAvailable);

	void addKeyListener(View.OnKeyListener androidControllers);

	void onPause();

	void onResume();
}

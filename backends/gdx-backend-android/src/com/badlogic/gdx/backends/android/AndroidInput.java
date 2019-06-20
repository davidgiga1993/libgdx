package com.badlogic.gdx.backends.android;

import android.view.View;

import com.badlogic.gdx.Input;

public interface AndroidInput extends Input, View.OnKeyListener, View.OnTouchListener
{
	void onPause();

	void onResume();
}

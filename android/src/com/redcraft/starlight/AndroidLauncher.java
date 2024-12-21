package com.redcraft.starlight;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.redcraft.starlight.launcher.HomeScreen;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HomeScreen.DEFAULT_FONT_SIZE = 40;
		ApplicationHook hook = new ApplicationHook(new HomeScreen());
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(hook, config);
	}
}

package com.redcraft.starlight.launcher;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redcraft.starlight.util.Files;

public class MultiplayerLauncher extends ImageButton {
    HomeScreen homeScreen;
    public MultiplayerLauncher(HomeScreen homeScreen) {
        super(new TextureRegionDrawable(Files.texture("button_multiplayer")),
                new TextureRegionDrawable(Files.texture("button_multiplayer")));
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClick(event,actor);
            }
        });
        this.homeScreen = homeScreen;
    }

    private void onClick(ChangeListener.ChangeEvent event, Actor actor) {
        homeScreen.stage.clear();
        homeScreen.stage.addActor(new ServerSelectionMenu(homeScreen.styles,homeScreen));
    }
}

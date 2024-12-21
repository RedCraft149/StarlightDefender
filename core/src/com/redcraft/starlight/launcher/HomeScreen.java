package com.redcraft.starlight.launcher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.redcraft.starlight.util.Files;

public class HomeScreen extends ApplicationAdapter {

    Stage stage;
    Styles styles;
    SingleplayerLauncher singleplayer;
    MultiplayerLauncher multiplayer;
    ServerLauncher server;
    ImageButton settings;

    public static int DEFAULT_FONT_SIZE = 30;

    public void create() {

        if(Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
        }
        Gdx.input.setCursorCatched(false);

        stage = new Stage();
        styles = new Styles("arial",DEFAULT_FONT_SIZE,"visui");

        singleplayer = new SingleplayerLauncher();
        multiplayer = new MultiplayerLauncher(this);
        server = new ServerLauncher(styles,stage);
        settings = new ImageButton(new TextureRegionDrawable(Files.texture("settings")));

        showAll();
        Gdx.input.setInputProcessor(stage);
    }

    public void showAll() {
        float elementWidth = Gdx.graphics.getWidth() / 3f * 0.8f;
        float spacerWidth = Gdx.graphics.getWidth() / 3f * 0.2f;

        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.add(singleplayer).width(elementWidth).center();
        table.add().width(spacerWidth);
        table.add(multiplayer).width(elementWidth).center();
        table.add().width(spacerWidth);
        table.add(server).width(elementWidth).center();
        stage.addActor(table);

        Label credits = new Label("Created by RedCraft149",styles.getLabelStyle());
        credits.setColor(Color.GRAY);
        credits.setPosition(Gdx.graphics.getWidth()-credits.getWidth()-10f,10f);
        stage.addActor(credits);
        settings.setPosition(Gdx.graphics.getWidth()-settings.getWidth()-10f, Gdx.graphics.getHeight()-settings.getHeight()-10f);
        stage.addActor(settings);
    }


    public void render() {
        ScreenUtils.clear(0,0,0,1);

        stage.act();
        stage.draw();
    }



    public void dispose() {
        stage.dispose();
    }
}

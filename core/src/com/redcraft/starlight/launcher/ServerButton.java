package com.redcraft.starlight.launcher;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redcraft.starlight.ApplicationHook;
import com.redcraft.starlight.client.CStarlightDefender;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.util.Files;

public class ServerButton extends Table {
    BitmapFont font;
    Texture background;
    TextField nameField;
    TextField addressField;
    Button join;
    Button delete;
    float scale;

    ServerSelectionMenu menu;
    public ServerButton(String namePreset, String addressPreset, Styles styles, ServerSelectionMenu menu) {
        this.menu = menu;
        pad(30f);

        int fontSize = styles.getFontSize();
        scale = fontSize * 0.05f;
        setBounds(10*scale,10*scale,580*scale,120*scale);
        font = Files.font("arial",fontSize);
        Label name = new Label("Name",new Label.LabelStyle(font, Color.GRAY));
        Label address = new Label("Address",new Label.LabelStyle(font, Color.GRAY));

        nameField = new TextField(namePreset,styles.getTextFieldStyle());
        addressField = new TextField(addressPreset,styles.getTextFieldStyle());


        join = new TextButton("Join",styles.getTextButtonStyle());
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menu.save();
                Connection connection = getConnection();
                if(connection == null) return;
                ApplicationHook.get().switchTo(new CStarlightDefender(connection));
            }
        });
        delete = new TextButton("Delete",styles.getTextButtonStyle());
        delete.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menu.removeServer(ServerButton.this);
                menu.save();
            }
        });


        Actor spacer = new Actor();
        spacer.setBounds(0,0,10*scale,10*scale);
        add(name).width(150*scale);
        add(nameField).width(390*scale);
        row();
        add(spacer);
        row();
        add(address).width(150*scale);
        add(addressField).width(390*scale);
        row();
        add(spacer);
        row();
        add(join).width(100*scale).left();
        add(delete).width(100*scale).left();

        setOrigin(100,20);

        background = Files.texture("button_background");
        setBackground(new TextureRegionDrawable(background));
    }

    private Connection getConnection() {
        String[] parts = addressField.getText().split(":");
        if(parts.length != 2) {
            addressField.setColor(Color.RED);
            return null;
        }
        int ip;
        try {
            ip = Integer.parseInt(parts[1]);
        } catch (NumberFormatException ex) {
            addressField.setColor(Color.RED);
            return null;
        }
        addressField.setColor(Color.GREEN);
        return Connection.net(parts[0],ip);
    }

    public String getName() {
        return nameField.getText();
    }
    public String getAddress() {
        return addressField.getText();
    }
}

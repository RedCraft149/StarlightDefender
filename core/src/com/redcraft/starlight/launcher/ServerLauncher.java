package com.redcraft.starlight.launcher;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redcraft.starlight.client.CStarlightDefender;
import com.redcraft.starlight.server.SStarlightDefender;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.util.Files;

import javax.swing.*;
import java.io.IOException;


public class ServerLauncher extends ImageButton {
    Dialog wrongDeviceDialog;
    Dialog noTerminalDialog;
    Stage stage;
    public ServerLauncher(Styles styles, Stage stage) {
        super(new TextureRegionDrawable(Files.texture("button_server")));
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClick(event,actor);
            }
        });

        wrongDeviceDialog = new Dialog("Error",styles.getSkin());
        wrongDeviceDialog.text("Cannot start a server on this device!");
        wrongDeviceDialog.button("Cancel",false);

        noTerminalDialog = new Dialog("Error",styles.getSkin())  {
            @Override
            public void result(Object o) {
                if(!(o instanceof Boolean)) return;
                boolean restart = (boolean) (Boolean) o;
                if(restart) {
                    restart();
                }
            }
        };
        noTerminalDialog.text("A terminal is required to start a server.");
        noTerminalDialog.button("Start server with new terminal",true);
        noTerminalDialog.button("Cancel",false);

        this.stage = stage;
    }

    private void onClick(ChangeListener.ChangeEvent event, Actor actor) {

        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            wrongDeviceDialog.show(stage);
            wrongDeviceDialog.toFront();
        } else if(System.console() == null) {
            noTerminalDialog.show(stage);
            noTerminalDialog.toFront();
        } else {
            SStarlightDefender server = new SStarlightDefender(Connection.localhost(14900));
            server.create();
        }
    }

    private void restart() {
        String filename = CStarlightDefender.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
        filename = filename.replace("%20"," ");
        System.out.println(filename);
        try {
            Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""});
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"File cannot be opened. ","Error",JOptionPane.ERROR_MESSAGE);
        }
        Gdx.app.exit();
    }
}

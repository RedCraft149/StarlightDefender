package com.redcraft.starlight.launcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.LinkedList;
import java.util.List;

public class ServerSelectionMenu extends Table {
    TextButton addButton;
    TextButton returnButton;
    ScrollPane pane;
    Table servers;
    Styles styles;

    List<ServerButton> serverList;

    public ServerSelectionMenu(Styles styles, HomeScreen homeScreen) {
        setBounds(10,-10, Gdx.graphics.getWidth()-20,Gdx.graphics.getHeight()-20);
        this.styles = styles;

        Actor spacer = new Actor();
        spacer.setBounds(0,0,10,10);

        addButton = new TextButton("Add Server",styles.getTextButtonStyle());
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addNewServer("","");
            }
        });
        returnButton = new TextButton("Return",styles.getTextButtonStyle());
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                save();
                homeScreen.stage.clear();
                homeScreen.showAll();
            }
        });
        top();
        left();
        add(addButton).left();
        add(spacer);
        add(returnButton).right();
        row();
        add(spacer);
        row();

        servers = new Table();
        servers.top().left();
        pane = new ScrollPane(servers);
        add(pane);
        //setDebug(true);

        serverList = new LinkedList<>();
        load();
    }

    public void removeServer(ServerButton button) {
        servers.removeActor(button);
        serverList.remove(button);
    }
    public void addNewServer(String name, String addr) {
        servers.row();
        ServerButton btn = new ServerButton(name,addr,styles,this);
        servers.add(btn);
        serverList.add(btn);
    }

    public void save() {
        FileHandle file = Gdx.files.external("servers.txt");
        StringBuilder builder = new StringBuilder();
        for(ServerButton button : serverList) {
            builder.append(button.getName());
            builder.append("=");
            builder.append(button.getAddress());
            builder.append("\n");
        }
        file.writeString(builder.toString(),false);
    }

    public void load() {
        FileHandle file = Gdx.files.external("servers.txt");
        if(!file.exists()) return;
        String str = file.readString();
        String[] servers = str.split("\n");
        for(String server : servers) {
            String[] parts = server.split("=");
            if(parts.length != 2) continue;
            addNewServer(parts[0],parts[1]);
        }
    }
}

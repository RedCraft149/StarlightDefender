package com.redcraft.starlight.client.elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.elements.CEntity;
import com.redcraft.starlight.client.elements.CPlayer;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;
import com.redcraft.rlib.events.QuickEvent;

import java.util.List;
import java.util.UUID;

public class MiniMap implements Drawable, Listener {
    List<CPlayer> players;
    CPlayer self;
    float size;
    CUniverse universe;

    public MiniMap(List<CPlayer> players, CPlayer self, float size, CUniverse universe) {
        this.players = players;
        this.self = self;
        this.size = size;
        this.universe = universe;
    }

    @Override
    public void draw(RenderSystem system) {
        system.begin(RenderSystem.SOLIDS | RenderSystem.ALPHA);

        system.solids().setColor(new Color(1f,1f,1f,0.2f));
        system.solids().rect(Gdx.graphics.getWidth()-size-20,Gdx.graphics.getHeight()-size-80,size,size);

        system.solids().setColor(Color.CYAN);
        Vector2 selfPosition = position(self);
        system.solids().rect(selfPosition.x-2,selfPosition.y-2,5,5);

        system.solids().setColor(Color.RED);
        for(CPlayer other : players) {
            Vector2 pos = position(other);
            system.solids().rect(pos.x-2,pos.y-2,5,5);
        }

        system.end(RenderSystem.SOLIDS | RenderSystem.ALPHA);
    }

    private Vector2 position(CPlayer player) {
        Vector2 position = player.getPosition();
        position.scl(1f/universe.width(),1f/universe.height());
        position.scl(size);
        position.add(Gdx.graphics.getWidth()-size-20,Gdx.graphics.getHeight()-size-80);
        return position;
    }

    @EventListener
    public void onPlayerJoin(QuickEvent event) {
        if(!event.matches("player_join")) return;
        Entity e = event.read("entity", CEntity.class);
        if(!(e instanceof CPlayer)) return;
        players.add((CPlayer) e);
    }

    @EventListener
    public void onPlayerLeave(QuickEvent event) {
        if(!event.matches("player_leave")) return;
        Entity e = universe.entityFinder().searchEntity(event.read("uuid", UUID.class));
        if(!(e instanceof CPlayer)) return;
        players.remove((CPlayer) e);
    }
}

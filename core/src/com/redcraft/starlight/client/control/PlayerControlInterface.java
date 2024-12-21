package com.redcraft.starlight.client.control;

import com.badlogic.gdx.math.MathUtils;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.elements.CPlayer;
import com.redcraft.starlight.client.events.PlayerShootEvent;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.starlight.shared.packets.PlayerRotationPacket;
import com.redcraft.communication.client.GClient;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.starlight.util.RMath;


public class PlayerControlInterface {

    CPlayer player;
    GClient client;

    public PlayerControlInterface(CPlayer player, GClient client) {
        this.player = player;
        this.client = client;
    }


    public void rotate(float rad) {
        player.setRotation(rad);
        client.send(new PlayerRotationPacket(rad));
    }

    public void setPosition(float x, float y) {
        player.setPosition(x,y);
        client.send(StarlightDefenderPacketList.newPacket("player_position").set("x",x).set("y",y));
    }

    public void moveInFacingDirection(float distance) {
        float dx = RMath.cos(player.getRotation() - MathUtils.HALF_PI) * distance;
        float dy = RMath.sin(player.getRotation() - MathUtils.HALF_PI) * distance;
        float x = player.getPosition().x+dx;
        float y = player.getPosition().y+dy;
        setPosition(x,y);
    }

    public void shoot() {
        if(player.getAmmo() <= 0) return;
        client.send(StarlightDefenderPacketList.newPacket("player_shoot"));
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).throwEvent(new PlayerShootEvent(player));
    }

    public void raiseShields() {
        client.send(StarlightDefenderPacketList.newPacket("player_raise_shields"));
    }
}

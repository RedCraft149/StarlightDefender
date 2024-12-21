package com.redcraft.starlight.client.frames;

import com.badlogic.gdx.utils.Array;
import com.redcraft.communication.client.GClient;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.frame.Frame;
import com.redcraft.starlight.client.frame.FrameHandler;
import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.server.StarlightDefenderClientHandler;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;


import java.util.HashMap;
import java.util.Map;

public class ConnectedFrame extends Frame {

    GClient client;

    Array<Frame> children;
    Map<String,Integer> childrenNames;

    public ConnectedFrame(FrameHandler frameHandler) {
        super(frameHandler);
        create();
    }

    public void addChild(Frame frame, String name) {
        childrenNames.put(name,this.children.size);
        this.children.add(frame);
    }
    public Frame getChild(String name) {
        return children.get(childrenNames.get(name));
    }

    @Override
    public void create() {
        children = new Array<>();
        childrenNames = new HashMap<>();
        //client = GClient.local();
        //client = GClient.net(PacketList.getAvailablePackets(),"localhost",14900);
        client = new GClient(Shared.CLIENT.get(CComponents.connection,Connection.class).createClientConnection(StarlightDefenderPacketList.getAvailablePackets()),
                             StarlightDefenderPacketList.getAvailablePackets());
        addChild(new GameFrame(frameHandler,client),"game");
        Shared.CLIENT.set(CComponents.connectedFrame,this);
        client.start();
    }

    @Override
    public void processInputs(InputCache inputs, float dt) {
        for(Frame frame : children) {
            if(frame.enabled(PROCESS_INPUT)) frame.processInputs(inputs,dt);
        }
    }

    @Override
    public void step(float deltaTime) {
        client.process(deltaTime);
        for(Frame frame : children) {
            if(frame.enabled(TICK)) frame.step(deltaTime);
        }
    }

    @Override
    public void render(RenderSystem renderSystem) {
        for(Frame frame : children) {
            if(frame.enabled(RENDER)) frame.render(renderSystem);
        }
    }

    @Override
    public void dispose() {
        System.out.println("dispose connected frame");
        client.stop();
        for(Frame frame : children) frame.dispose();
    }
}

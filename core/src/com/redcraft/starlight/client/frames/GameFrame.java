package com.redcraft.starlight.client.frames;

import com.badlogic.gdx.Gdx;
import com.redcraft.communication.packets.Packet;
import com.redcraft.communication.packets.handlers.PacketHandler;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.control.*;
import com.redcraft.starlight.client.effects.CrashVibration;
import com.redcraft.starlight.client.effects.ElementDestruction;
import com.redcraft.starlight.client.effects.ShootingSound;
import com.redcraft.starlight.client.elements.CPlayer;
import com.redcraft.starlight.client.elements.CUniverse;
import com.redcraft.starlight.client.elements.gui.GameGUI;
import com.redcraft.starlight.client.elements.gui.MiniMap;
import com.redcraft.starlight.client.frame.Frame;
import com.redcraft.starlight.client.frame.FrameHandler;
import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.packethandlers.*;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.communication.client.GClient;
import com.redcraft.communication.packets.handlers.DistributingPacketHandler;
import com.redcraft.rlib.actions.Actions;
import com.redcraft.rlib.events.EventHandler;

import java.util.ArrayList;

public class GameFrame extends Frame {

    Universe universe;
    GameGUI overlay;
    EventHandler handler;
    GClient client;

    ControllerGroup controller;

    public GameFrame(FrameHandler frameHandler, GClient client) {
        super(frameHandler);

        this.client = client;
        create();
        resume();
    }

    @Override
    public void create() {
        handler = new EventHandler();
        handler.register(new CrashVibration());
        handler.register(new ShootingSound());
        handler.register(new ElementDestruction());
        Shared.CLIENT.set(CComponents.gameEventHandler, handler);
        Shared.CLIENT.setAction(CComponents.createUniverse, Actions.create(this::setUniverse));
        DistributingPacketHandler packetHandler = new DistributingPacketHandler();
        packetHandler.addPacketHandler(new UniverseCreationHandler());
        packetHandler.addPacketHandler(new ChunkCreationHandler());
        packetHandler.addPacketHandler(new EntityRemoveHandler());
        packetHandler.addPacketHandler(new EntityPositionHandler());
        packetHandler.addPacketHandler(new EntityCreationHandler());
        packetHandler.addPacketHandler(new EntityHealthChangeHandler());
        packetHandler.addPacketHandler(new EntityRotationHandler());
        packetHandler.addPacketHandler(new PlayerPauseHandler());
        packetHandler.addPacketHandler(new PauseTimerHandler());
        packetHandler.addPacketHandler(new PlayerScoreHandler());
        packetHandler.addPacketHandler(new ShieldHitHandler());
        packetHandler.addPacketHandler(new ShieldTimeHandler());
        packetHandler.addPacketHandler(new PlayerAmmoHandler());
        packetHandler.addPacketHandler(new SpaceStationDestroyedHandler());
        packetHandler.addPacketHandler(new PacketHandler<Packet>() {
            @Override
            public void receive(Packet packet) {

            }

            @Override
            public int header() {
                return "internal:heartbeat".hashCode();
            }
        });
        client.addPacketHandler(packetHandler);
        Shared.CLIENT.setAction(CComponents.createControls, Actions.create(this::createControls));

        overlay = new GameGUI();
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
        Shared.CLIENT.set(CComponents.universe,universe);
        overlay.getHealthBar().setTarget(universe.as(CUniverse.class).getPlayer().getUUID());
        overlay.getHealthBar().setProgress(universe.as(CUniverse.class).getPlayer().getHealth());

        MiniMap miniMap = new MiniMap(new ArrayList<>(),universe.as(CUniverse.class).getPlayer(),150f,universe.as(CUniverse.class));
        overlay.addElement("minimap",miniMap);
        handler.register(miniMap);
    }

    @Override
    public void processInputs(InputCache inputs, float dt) {
        if(controller!=null) {
            inputs.forAll(input -> controller.process(input));
            controller.tick(dt);
        }
    }

    @Override
    public void step(float deltaTime) {
        //client.handleAll();
        handler.distributeCachedEvents();
        if(universe!=null) {
            universe.entityDistributor().distribute();
            universe.as(CUniverse.class).step(deltaTime);
        }
    }

    @Override
    public void render(RenderSystem renderSystem) {
        if(universe!=null) universe.as(CUniverse.class).render(renderSystem);
        overlay.draw(renderSystem);
        if(controller != null) controller.render(renderSystem);
    }

    public void createControls(CPlayer player) {
        PlayerControlInterface pci = new PlayerControlInterface(player,client);
        controller = new ControllerGroup(pci);
        switch (Gdx.app.getType()) {
            case Applet:
            case Desktop:
            case HeadlessDesktop:
                createDesktopControls(pci);
                break;
            default:
                createMobileControls(pci);
        }
    }

    private void createMobileControls(PlayerControlInterface pci) {
        controller.addController(new MobileAttackController(pci));
        controller.addController(new MobileDirectionController(pci));
        controller.addController(new MobileShieldController(pci));
    }
    private void createDesktopControls(PlayerControlInterface pci) {
        controller.addController(new DesktopControllerCatchedCursor(pci));
    }

    @Override
    public void dispose() {
        overlay.dispose();
        controller.dispose();
    }

    public GameGUI getOverlay() {
        return overlay;
    }
    public Controller getController() {
        return controller;
    }
}

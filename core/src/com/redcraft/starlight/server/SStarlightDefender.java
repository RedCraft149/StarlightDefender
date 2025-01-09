package com.redcraft.starlight.server;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.communication.server.GServer;
import com.redcraft.starlight.server.elements.SUniverse;
import com.redcraft.starlight.server.packethandlers.out.*;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;
import com.redcraft.rlib.events.EventHandler;

public class SStarlightDefender {

    EventHandler eventHandler;
    SUniverse universe;
    GServer<StarlightDefenderClientHandler> server;
    Connection connection;

    boolean running;

    public SStarlightDefender(Connection connection) {
        this.connection = connection;
    }

    public void create() {
        running = true;
        eventHandler = new EventHandler();
        Shared.initSharedServer();

        createServer(connection);

        Shared.SERVER.set(SConstants.universeDimensions, new Vector2(16,16));
        Shared.SERVER.set(SConstants.gameEventHandler, eventHandler);
        Shared.SERVER.set(SConstants.main,this);

        universe = new SUniverse(16,16,server);
        Shared.SERVER.set(SConstants.universe, universe);

        eventHandler.register(new EntityPositionHandler());
        eventHandler.register(new EntityHealthChangeHandler());
        eventHandler.register(new EntityAddHandler());
        eventHandler.register(new EntityRemoveHandler());
        eventHandler.register(new EntityRotationHandler());
        eventHandler.register(new PlayerPauseHandler());
        eventHandler.register(new PlayerPauseTimerChangeHandler());
        eventHandler.register(new PlayerScoreHandler());
        eventHandler.register(new PlayerShieldHitHandler());
        eventHandler.register(new PlayerShiedTimeHandler());
        eventHandler.register(new PlayerAmmoHandler());
        eventHandler.register(new SpaceStationDestroyedHandler());

        server.start();
        loop();
        server.stop();
    }

    private void loop() {
        long t0 = 0;
        while(running) {
            server.forAll((handler) -> handler.process(0.05f));
            eventHandler.distributeCachedEvents();
            universe.process(0.05f);
            if(System.currentTimeMillis() - t0 > 50) {
                System.err.println("Warning: Server is running behind, ms/t="+(System.currentTimeMillis() - t0));
            }

            while(System.currentTimeMillis()-t0 < 50) continue; //wait
            t0 = System.currentTimeMillis();
        }
    }

    private void createServer(Connection connection) {
        server = new GServer<>(StarlightDefenderClientHandler::new, connection.createServerConnection(StarlightDefenderPacketList.getAvailablePackets()));
        Shared.SERVER.set(SConstants.server,server);
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        new SStarlightDefender(Connection.local()).create();
    }
    public static void startServerOnNewThread(Connection connection) {
        Thread t = new Thread(()->new SStarlightDefender(connection).create());
        t.start();
    }
}

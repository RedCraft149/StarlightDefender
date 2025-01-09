package com.redcraft.starlight.server;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.communication.packets.handlers.PacketHandler;
import com.redcraft.communication.packets.handlers.SyncPacketReceiver;
import com.redcraft.communication.server.GClientHandler;
import com.redcraft.communication.server.GServer;
import com.redcraft.starlight.server.elements.*;
import com.redcraft.starlight.server.elements.ai.ArtificialController;
import com.redcraft.starlight.server.elements.ai.BasicAIController;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationGenerator;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationGenerator2;
import com.redcraft.starlight.server.elements.spacestation.SSpaceStationPart;
import com.redcraft.starlight.server.packethandlers.PlayerPositionPacketHandler;
import com.redcraft.starlight.server.packethandlers.PlayerRaiseShieldsPacketHandler;
import com.redcraft.starlight.server.packethandlers.PlayerRotationPacketHandler;
import com.redcraft.starlight.server.packethandlers.PlayerShootPacketHandler;
import com.redcraft.starlight.shared.MessagePacket;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.*;
import com.redcraft.rlib.Processable;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class StarlightDefenderClientHandler extends GClientHandler implements Processable {

    SUniverse universe;
    List<SyncPacketReceiver<?>> SSPHs;

    SEnemy enemy;
    SPlayer player;

    boolean initialized = false;

    float enemyDelay = 0f;
    float formationDelay = 10f;
    float heartbeatDelay = 1f;

    public StarlightDefenderClientHandler(GServer<StarlightDefenderClientHandler> server) {
        super(server);
    }

    @Override
    public void init() {
        SSPHs = new LinkedList<>();
        universe = Shared.SERVER.get(SConstants.universe, SUniverse.class);

        System.out.println("receive client");
        addPacketHandler(PacketHandler.of(packet -> System.out.println("Packet received: "+packet), StarlightDefenderPacketList.MESSAGE_PACKET));

        addPacketHandler(new PlayerPositionPacketHandler(uuid));
        addPacketHandler(new PlayerRotationPacketHandler(uuid));
        addPacketHandler(new PlayerShootPacketHandler(uuid));
        addPacketHandler(new PlayerRaiseShieldsPacketHandler(uuid));

        send(new MessagePacket("Hello, client!"));
        send(new UniverseCreationPacket(universe.width(),universe.height(),uuid));

        for(int i = 0; i < universe.width()*universe.height(); i++) {
            send(universe.createPacketForChunk(i));
        }

        player = new SPlayer(uuid,universe);
        universe.entityDistributor().addEntity(player);
        SEnemy enemy = new SEnemy(UUID.randomUUID(),player,universe,SEnemy.BASIC);
        enemy.setPosition(1.5f,1.5f);
        universe.entityDistributor().addEntity(enemy);

        initialized = true;

        //SSpaceStationGenerator generator = new SSpaceStationGenerator(System.currentTimeMillis(),0.4f);
        //List<SSpaceStationPart> parts = generator.position(1,1).createSpaceStation(3);
        //System.out.println("parts:"+ parts);
    }

    public void addPacketHandler(PacketHandler<?> handler) {
        super.addPacketHandler(handler);
        if(handler instanceof SyncPacketReceiver) SSPHs.add((SyncPacketReceiver<?>) handler);
    }

    public void process(float dt) {
        if(!initialized) return;
        for(SyncPacketReceiver<?> ssph : SSPHs) {
            ssph.process(dt);
        }

        enemyDelay -= dt;
        formationDelay -= dt;
        heartbeatDelay -= dt;
        if(enemyDelay <= 0f) {
            universe.addEnemyFor(player);
            enemyDelay = 3f;
        }
        if(formationDelay <= 0f && MathUtils.randomBoolean(dt*0.2f) && player.getScore() > 300) {
            spawnFormationAttack(5);
            formationDelay = 15f;
            System.out.println("FORMATION ATTACK");
        }
        if(heartbeatDelay <= 0f) {
            heartbeatDelay = 1f;
            send(new HeartbeatPacket());
        }
    }

    public void spawnFormationAttack(int num) {
        Vector2 center = player.getPosition();
        float angle = MathUtils.PI2 / num;
        for(int i = 0; i < num; i++) {
            Vector2 p = new Vector2(center);
            p.add(new Vector2(1,0).setAngleRad(angle*i).scl(1.4f));
            ArtificialController controller = universe.addEnemy(player,p,SEnemy.BASIC).getController();
            if(controller instanceof BasicAIController) {
                ((BasicAIController) controller).setState(BasicAIController.AGGRESSIVE);
            }
        }
    }

    @Override
    public void end() {
        System.out.println("END CONNECTION");
        server.end(uuid);
        server.sendToAll(new EntityRemovePacket(uuid,player.getPosition().x,player.getPosition().y));
    }
}

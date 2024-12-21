package com.redcraft.starlight.server.elements;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.collision.CircularShape;
import com.redcraft.starlight.server.elements.ai.AdvancedAIController;
import com.redcraft.starlight.server.elements.ai.ArtificialController;
import com.redcraft.starlight.server.elements.ai.BasicAIController;
import com.redcraft.starlight.shared.entity.Entity;
import com.redcraft.starlight.shared.world.Chunk;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.Processable;
import com.redcraft.starlight.util.RMath;

import java.util.ArrayList;
import java.util.UUID;

public class SEnemy extends SCollisionEntity implements Processable {

    public static final int BASIC = 1;
    public static final int ADVANCED = 2;

    SEntity target;
    SUniverse universe;
    int type;

    ArtificialController controller;

    public SEnemy(UUID uuid, SEntity target, SUniverse universe, int type) {
        super(uuid, new CircularShape(0f,0f,0.0625f));
        this.target = target;
        this.universe = universe;
        this.type = type;

        switch (type) {
            case BASIC: controller = new BasicAIController(this); break;
            case ADVANCED: controller = new AdvancedAIController(this,universe); break;
        }
    }


    public Vector2[] getObstacles(Universe universe) {
        ArrayList<Vector2> obstacles = new ArrayList<>();
        int cx = universe.chunkX(position.x);
        int cy = universe.chunkY(position.y);
        for(int x = cx-1; x <= cx+1; x++) {
            for(int y = cy-1; y <= cy+1; y++) {
                int rx = RMath.mod(x,universe.width());
                int ry = RMath.mod(y,universe.height());
                Chunk chunk = universe.getChunk(rx,ry);
                for(Entity entity : chunk.getEntities()) {
                    if(!(entity instanceof SPlayer) && !(entity instanceof SEnemy) && !(entity instanceof SBullet)) {
                        obstacles.add(entity.getPosition());
                    }
                }
            }
        }
        return obstacles.toArray(new Vector2[0]);
    }

    @Override
    public void process(float dt) {
        controller.process(dt*0.55f,target.getPosition(),getObstacles(universe));
    }

    public ArtificialController getController() {
        return controller;
    }

    public int getType() {
        return type;
    }
}

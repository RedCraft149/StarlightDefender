package com.redcraft.starlight.server.elements.spacestation;

import jdk.internal.icu.util.CodePointMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SSpaceStationGenerator {

    //0b0001 -> UP    +y
    //0b0010 -> RIGHT +x
    //0b0100 -> DOWN  -y
    //0b1000 -> LEFT  -x

    Random random;
    long seed;
    float chance;
    int x, y;

    public SSpaceStationGenerator(long seed, float chance) {
        this.seed = seed;
        this.chance = chance;
    }

    public SSpaceStationGenerator position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public List<SSpaceStationPart> createSpaceStation(int steps) {
        System.out.println(" ===== GENERATOR LOG =======");
        random = new Random((seed + 31L*y) * x + y + seed);
        List<SSpaceStationPart> newlyGenerated = new LinkedList<>();
        List<SSpaceStationPart> generated = new LinkedList<>();
        SSpaceStationConnector origin = new SSpaceStationConnector(UUID.randomUUID(),0xF);
        origin.setPosition(x,y);
        newlyGenerated.add(origin);
        for(int i = 0; i < steps; i++) {
            List<SSpaceStationPart> copy = new LinkedList<>(newlyGenerated);
            generated.addAll(newlyGenerated);
            newlyGenerated.clear();
            for(SSpaceStationPart part : copy) {
                extend(part,newlyGenerated,generated);
                System.out.println("EXTEND");
            }

        }
        return generated;
    }

    private void extend(SSpaceStationPart part, List<SSpaceStationPart> newlyGenerated, List<SSpaceStationPart> generated) {
        if(part instanceof SSpaceStationSolarPanel) return;
        if((part.direction & 0b0001) != 0) extend(part,newlyGenerated,generated,0,1);
        if((part.direction & 0b0010) != 0) extend(part,newlyGenerated,generated,1,0);
        if((part.direction & 0b0100) != 0) extend(part,newlyGenerated,generated,0,-1);
        if((part.direction & 0b1000) != 0) extend(part,newlyGenerated,generated,-1,0);
    }
    private void extend(SSpaceStationPart part, List<SSpaceStationPart> newlyGenerated, List<SSpaceStationPart> generated, int dx, int dy) {
        float targetX = part.getPosition().x + dx*0.25f;
        float targetY = part.getPosition().y + dy*0.25f;
        if(isOccupied(targetX,targetY,newlyGenerated,generated)) {
            System.out.printf("SPOT (%f | %f) is occupied!\n",targetX,targetY);
            return;
        }
        float rand = random.nextFloat();
        if(rand < 0.33f) { //Solar panel
            SSpaceStationSolarPanel panel = new SSpaceStationSolarPanel(UUID.randomUUID(),0);
            panel.direction = getSolarPanelDirection(dx,dy);
            panel.setPosition(targetX,targetY);
            newlyGenerated.add(panel);
        } else if(rand < 0.66f) { //Connector
            SSpaceStationConnector connector = new SSpaceStationConnector(UUID.randomUUID(),getRandomPartDirection());
            connector.direction |= getPartDirection(dx,dy);
            connector.setPosition(targetX,targetY);
            newlyGenerated.add(connector);
        } else { //Module
            SSpaceStationModule module = new SSpaceStationModule(UUID.randomUUID(),getRandomPartDirection());
            module.direction |= getPartDirection(dx,dy);
            module.setPosition(targetX,targetY);
            newlyGenerated.add(module);
        }
    }

    private int getSolarPanelDirection(int dx, int dy) {
        if(dx == 0 && dy == 1) return 0;
        if(dx == 1 && dy == 0) return 3;
        if(dx == 0 && dy ==-1) return 2;
        if(dx ==-1 && dy == 0) return 1;
        return 0;
    }

    private int getPartDirection(int dx, int dy) {
        if(dx == 0 && dy == 1) return 0b0100;
        if(dx == 1 && dy == 0) return 0b1000;
        if(dx == 0 && dy ==-1) return 0b0001;
        if(dx ==-1 && dy == 0) return 0b0010;
        return 0;
    }

    private int getRandomPartDirection() {
        int direction = 0;
        if(random.nextFloat() < chance) direction |= 0b0001;
        if(random.nextFloat() < chance) direction |= 0b0010;
        if(random.nextFloat() < chance) direction |= 0b0100;
        if(random.nextFloat() < chance) direction |= 0b1000;
        return direction;
    }

    private boolean isOccupied(float x, float y, List<SSpaceStationPart> newlyGenerated, List<SSpaceStationPart> generated) {
        final float epsilon = 0f;
        for(SSpaceStationPart part : generated) {
            if(Math.abs(part.getPosition().x - x) < epsilon) return true;
            if(Math.abs(part.getPosition().y - y) < epsilon) return true;
        }
        for(SSpaceStationPart part : newlyGenerated) {
            if(Math.abs(part.getPosition().x - x) < epsilon) return true;
            if(Math.abs(part.getPosition().y - y) < epsilon) return true;
        }
        return false;
    }
}

package com.redcraft.starlight.server.elements.ai;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.server.elements.SEnemy;
import com.redcraft.starlight.util.RMath;

public class BasicAIController implements ArtificialController{

    public static final int SEARCH = 1;
    public static final int AVOID = 2;
    public static final int AGGRESSIVE = 3;
    int state;

    Vector2 velocity;
    SEnemy object;

    public BasicAIController(SEnemy object) {
        this.object = object;
        velocity = new Vector2();
    }

    public void moveByState(float dst, Vector2 D, Vector2... O) {

        Vector2 position = object.getPosition();

        if(state != AGGRESSIVE) {
            boolean reset = true;
            for (Vector2 o : O) {
                if (o.dst(position) < 0.2f) state = AVOID;
                if (o.dst(position) < 0.5f) reset = false;
            }
            if (reset) {
                if (state == AVOID) state = AGGRESSIVE;
                else state = SEARCH;
            }
        }
        if(position.dst(D) > 1.5f && state == AGGRESSIVE) state = SEARCH;

        Vector2 dir = new Vector2();
        if(state == SEARCH || state == AGGRESSIVE) dir.set(D).sub(position);
        else for(Vector2 o : O) {
            dir.add(new Vector2(position).sub(o).nor().scl(1/position.dst2(o)));
        }

        dir.nor().scl(dst * (state == AGGRESSIVE ? 1.25f : 1.0f));

        updateVelocity(dir);
        object.move(velocity.x,velocity.y);
        object.setRotation((float) Math.atan2(dir.y,dir.x) + RMath.PI * 0.5f);
    }

    protected void updateVelocity(Vector2 goal) {
        Vector2 dif = new Vector2(goal).sub(velocity);
        dif.scl(0.2f);
        velocity.add(dif);
    }

    public void setState(int state) {
        this.state = state;
    }
    
    @Override
    public void process(float dst, Vector2 D, Vector2... O) {
        moveByState(dst,D,O);
    }
}

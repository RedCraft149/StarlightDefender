package com.redcraft.starlight.shared.events;

import com.redcraft.starlight.shared.entity.Entity;

public class ScoreChangeEvent extends EntityEvent{

    int score;

    public ScoreChangeEvent(Entity entity, int score) {
        super(entity);
        this.score = score;
    }

    public int score() {
        return score;
    }
}

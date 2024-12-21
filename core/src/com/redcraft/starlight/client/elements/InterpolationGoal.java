package com.redcraft.starlight.client.elements;

import com.badlogic.gdx.math.MathUtils;
import com.redcraft.rlib.actions.ReturnAction;

public class InterpolationGoal<T> {

    T goal;
    T state;
    T start;
    float completionTime;
    float currentTime;
    ReturnAction.n3<T,T,Float,T> interpolator;

    public InterpolationGoal(float completionTime, ReturnAction.n3<T, T, Float, T> interpolator) {
        this.completionTime = completionTime;
        this.interpolator = interpolator;
        this.currentTime = completionTime;
    }

    public void setGoal(T goal) {
        this.goal = goal;
    }
    public void setStart(T start) {
        this.start = start;
    }
    public void setState(T state) {
        this.state = state;
    }
    public T getState() {
        return state;
    }

    public void reset(T start, T goal) {
        setStart(start);
        setState(start);
        setGoal(goal);
        currentTime = 0f;
    }

    public void setState(float progress) {
        progress = MathUtils.clamp(progress,0f,1f);
        state = interpolator.run(start,goal,progress);
    }

    public boolean finished() {
        return currentTime >= completionTime;
    }

    public void interpolate(float dt) {
        currentTime += dt;
        setState(currentTime/completionTime);
    }
}

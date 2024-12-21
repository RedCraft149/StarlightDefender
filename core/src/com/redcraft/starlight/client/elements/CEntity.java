package com.redcraft.starlight.client.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Components;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.entity.ModularEntity;
import com.redcraft.starlight.util.RMath;

import java.util.UUID;

public class CEntity extends ModularEntity implements Drawable {

    protected final Matrix3 transform;
    protected float scale;
    protected String texture;

    protected final Vector2 relation;
    protected final Vector2 modularPosition;

    protected InterpolationGoal<Vector2> positionInterpolator;
    protected InterpolationGoal<Float> rotationInterpolator;

    public CEntity(UUID uuid) {
        super(uuid, Shared.CLIENT.get(CComponents.universeDimensions, Vector2.class), true);
        transform = new Matrix3();
        scale = 1f;
        texture = null;
        relation = new Vector2(0f,0f);
        modularPosition = new Vector2(0f,0f);

        positionInterpolator = new InterpolationGoal<>(0.05f,//Shared.CLIENT.getFloat(CComponents.interpolationTime),
                (start, goal, progress)->RMath.modLerp(start,goal,progress,dimensions,new Vector2()));
        rotationInterpolator = new InterpolationGoal<>(0.05f,
                (start, goal, progress) -> RMath.modLerp(start,goal,progress,RMath.PI * 2));

        set(Components.RESPONSIBLE_EVENT_HANDLER, Shared.CLIENT.get(CComponents.gameEventHandler));
        setFlag(Components.THROW_REMOVE_EVENTS);
        setFlag(Components.THROW_POSITION_EVENTS);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    public String getTexture() {
        return texture;
    }
    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setRelation(float x, float y) {
        relation.set(x,y);
    }

    public void recalculateTransformMatrix() {
        modularPosition.set(position);
        RMath.nearestModularVector(modularPosition,relation,dimensions);
        transform.idt().trn(modularPosition).scl(scale).rotateRad(rotation);
    }
    public void draw(RenderSystem system) {
        interpolations();
        if(texture==null || texture.isEmpty()) return;
        system.sprites().draw(texture,transform);
    }

    protected void interpolations() {
        if(!positionInterpolator.finished()) {
            positionInterpolator.interpolate(Gdx.graphics.getDeltaTime());
            setPosition(positionInterpolator.getState().x,positionInterpolator.getState().y);
            recalculateTransformMatrix();
        }
        if(!rotationInterpolator.finished()) {
            rotationInterpolator.interpolate(Gdx.graphics.getDeltaTime());
            setRotation(rotationInterpolator.getState());
            recalculateTransformMatrix();
        }
    }

    public void setTarget(Vector2 target) {
        this.positionInterpolator.reset(new Vector2(position),new Vector2(target));
        setPosition(target.x,target.y);
    }
    public void setRotationTarget(float target) {
        this.rotationInterpolator.reset(rotation,target);
        setRotation(target);
    }
}

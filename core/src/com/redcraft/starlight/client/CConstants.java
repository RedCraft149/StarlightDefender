package com.redcraft.starlight.client;

import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.SharedAssets;

public class CConstants {

    public static float PLAYER_SCALE = 0.25f;
    public static float STATIC_OBJECT_SCALE = 0.0625f;
    public static Vector2[] SPACE_STATION_PART_2x2 = new Vector2[] {
            new Vector2(0f,0f), new Vector2(1f,0f),
            new Vector2(0f,1f), new Vector2(1f,1f)
    };
    public static Vector2[] SPACE_STATION_PART_4x4 = new Vector2[] {
            new Vector2(0f,0f), new Vector2(1f,0f), new Vector2(2,0f), new Vector2(3f,0f),
            new Vector2(0f,1f), new Vector2(1f,1f), new Vector2(2,1f), new Vector2(3f,1f),
            new Vector2(0f,2f), new Vector2(1f,2f), new Vector2(2,2f), new Vector2(3f,2f),
            new Vector2(0f,3f), new Vector2(1f,3f), new Vector2(2,3f), new Vector2(3f,3f),
    };

    public static void loadAll() {
        SharedAssets assets = Shared.CLIENT;
        assets.set("player.scale", PLAYER_SCALE);
        assets.set("staticObject.scale", STATIC_OBJECT_SCALE);
        assets.set(CComponents.chunksOnScreen,3f);
    }
}

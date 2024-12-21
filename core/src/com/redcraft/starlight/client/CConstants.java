package com.redcraft.starlight.client;

import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.SharedAssets;

public class CConstants {

    public static float PLAYER_SCALE = 0.25f;
    public static float STATIC_OBJECT_SCALE = 0.0625f;

    public static void loadAll() {
        SharedAssets assets = Shared.CLIENT;
        assets.set("player.scale", PLAYER_SCALE);
        assets.set("staticObject.scale", STATIC_OBJECT_SCALE);
        assets.set(CComponents.chunksOnScreen,3f);
    }
}

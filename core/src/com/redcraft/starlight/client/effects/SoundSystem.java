package com.redcraft.starlight.client.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundSystem {
    Map<String, Sound> sounds;

    public SoundSystem() {
        this.sounds = new HashMap<>();
    }

    public void createSound(String name, String location) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+location+".mp3"));
        sounds.put(name,sound);
    }

    public void play(String name, float volume) {
        if(!sounds.containsKey(name)) return;
        sounds.get(name).play(volume);
    }

    public void disposeAndClear() {
        for(Sound sound : sounds.values()) sound.dispose();
        sounds.clear();
    }
}

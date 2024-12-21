package com.redcraft.starlight;

import com.badlogic.gdx.ApplicationListener;

public class ApplicationHook implements ApplicationListener {

    private static ApplicationHook HOOK = null;

    ApplicationListener active;
    ApplicationListener defaultApp;

    public ApplicationHook(ApplicationListener def) {
        HOOK = this;
        defaultApp = def;
    }

    @Override
    public void create() {
        HOOK = this;
        switchTo(defaultApp);
        System.out.println("SET HOOK");
    }

    @Override
    public void resize(int width, int height) {
        active.resize(width,height);
    }

    @Override
    public void render() {
        active.render();
    }

    @Override
    public void pause() {
        active.pause();
    }

    @Override
    public void resume() {
        active.resume();
    }

    @Override
    public void dispose() {
        switchTo(null);
    }

    public void switchTo(ApplicationListener application) {
        if(active != null) active.dispose();
        active = application;
        if(active != null) active.create();
    }

    public static ApplicationHook get() {
        return HOOK;
    }
}

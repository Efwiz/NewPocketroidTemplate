package com.badlogic.gdx;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Game.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class Game implements ApplicationListener {
    protected Screen screen;

    @Override // com.badlogic.gdx.ApplicationListener
    public void dispose() {
        Screen screen = this.screen;
        if (screen != null) {
            screen.hide();
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void pause() {
        Screen screen = this.screen;
        if (screen != null) {
            screen.pause();
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void resume() {
        Screen screen = this.screen;
        if (screen != null) {
            screen.resume();
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void render() {
        Screen screen = this.screen;
        if (screen != null) {
            screen.render(Gdx.graphics.getDeltaTime());
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void resize(int i, int i2) {
        Screen screen = this.screen;
        if (screen != null) {
            screen.resize(i, i2);
        }
    }

    public void setScreen(Screen screen) {
        Screen screen2 = this.screen;
        if (screen2 != null) {
            screen2.hide();
        }
        this.screen = screen;
        if (screen != null) {
            screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public Screen getScreen() {
        return this.screen;
    }
}

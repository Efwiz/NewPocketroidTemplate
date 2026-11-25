package com.badlogic.gdx;

import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Screen.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Screen extends Disposable {
    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    void hide();

    void pause();

    void render(float f);

    void resize(int i, int i2);

    void resume();

    void show();
}

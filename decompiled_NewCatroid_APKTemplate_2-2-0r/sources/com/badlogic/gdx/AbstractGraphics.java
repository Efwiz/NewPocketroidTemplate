package com.badlogic.gdx;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/AbstractGraphics.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractGraphics implements Graphics {
    @Override // com.badlogic.gdx.Graphics
    public float getRawDeltaTime() {
        return getDeltaTime();
    }

    @Override // com.badlogic.gdx.Graphics
    public float getDensity() {
        float ppiX = getPpiX();
        if (ppiX <= 0.0f || ppiX > Float.MAX_VALUE) {
            return 1.0f;
        }
        return ppiX / 160.0f;
    }

    @Override // com.badlogic.gdx.Graphics
    public float getBackBufferScale() {
        return getBackBufferWidth() / getWidth();
    }
}

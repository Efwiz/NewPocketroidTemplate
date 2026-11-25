package com.badlogic.gdx.utils.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/viewport/ScreenViewport.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ScreenViewport extends Viewport {
    private float unitsPerPixel;

    public ScreenViewport() {
        this(new OrthographicCamera());
    }

    public ScreenViewport(Camera camera) {
        this.unitsPerPixel = 1.0f;
        setCamera(camera);
    }

    @Override // com.badlogic.gdx.utils.viewport.Viewport
    public void update(int i, int i2, boolean z) {
        setScreenBounds(0, 0, i, i2);
        float f = this.unitsPerPixel;
        setWorldSize(i * f, i2 * f);
        apply(z);
    }

    public float getUnitsPerPixel() {
        return this.unitsPerPixel;
    }

    public void setUnitsPerPixel(float f) {
        this.unitsPerPixel = f;
    }
}

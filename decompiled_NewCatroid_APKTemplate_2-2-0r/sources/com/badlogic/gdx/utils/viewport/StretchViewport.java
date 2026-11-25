package com.badlogic.gdx.utils.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/viewport/StretchViewport.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class StretchViewport extends ScalingViewport {
    public StretchViewport(float f, float f2) {
        super(Scaling.stretch, f, f2);
    }

    public StretchViewport(float f, float f2, Camera camera) {
        super(Scaling.stretch, f, f2, camera);
    }
}

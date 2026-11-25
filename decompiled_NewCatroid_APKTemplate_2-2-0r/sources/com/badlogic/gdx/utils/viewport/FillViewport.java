package com.badlogic.gdx.utils.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Scaling;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/viewport/FillViewport.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FillViewport extends ScalingViewport {
    public FillViewport(float f, float f2) {
        super(Scaling.fill, f, f2);
    }

    public FillViewport(float f, float f2, Camera camera) {
        super(Scaling.fill, f, f2, camera);
    }
}

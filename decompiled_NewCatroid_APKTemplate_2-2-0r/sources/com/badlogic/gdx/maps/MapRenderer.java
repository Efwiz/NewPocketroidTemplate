package com.badlogic.gdx.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/MapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface MapRenderer {
    void render();

    void render(int[] iArr);

    void setView(OrthographicCamera orthographicCamera);

    void setView(Matrix4 matrix4, float f, float f2, float f3, float f4);
}

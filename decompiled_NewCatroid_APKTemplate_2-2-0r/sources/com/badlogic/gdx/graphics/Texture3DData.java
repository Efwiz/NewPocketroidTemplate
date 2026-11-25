package com.badlogic.gdx.graphics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/Texture3DData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Texture3DData {
    void consume3DData();

    int getDepth();

    int getGLType();

    int getHeight();

    int getInternalFormat();

    int getWidth();

    boolean isManaged();

    boolean isPrepared();

    void prepare();

    boolean useMipMaps();
}

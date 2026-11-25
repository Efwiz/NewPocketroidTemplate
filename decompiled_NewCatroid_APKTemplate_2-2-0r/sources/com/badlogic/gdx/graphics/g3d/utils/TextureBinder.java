package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.GLTexture;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/TextureBinder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TextureBinder {
    void begin();

    int bind(GLTexture gLTexture);

    int bind(TextureDescriptor textureDescriptor);

    void end();

    int getBindCount();

    int getReuseCount();

    void resetCounts();
}

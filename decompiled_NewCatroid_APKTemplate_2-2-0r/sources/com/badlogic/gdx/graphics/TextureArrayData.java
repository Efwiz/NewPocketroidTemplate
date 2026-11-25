package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.FileTextureArrayData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/TextureArrayData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TextureArrayData {
    void consumeTextureArrayData();

    int getDepth();

    int getGLType();

    int getHeight();

    int getInternalFormat();

    int getWidth();

    boolean isManaged();

    boolean isPrepared();

    void prepare();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/TextureArrayData$Factory.class
      classes8.dex
     */
    public static class Factory {
        public static TextureArrayData loadFromFiles(Pixmap.Format format, boolean z, FileHandle... fileHandleArr) {
            return new FileTextureArrayData(format, z, fileHandleArr);
        }
    }
}

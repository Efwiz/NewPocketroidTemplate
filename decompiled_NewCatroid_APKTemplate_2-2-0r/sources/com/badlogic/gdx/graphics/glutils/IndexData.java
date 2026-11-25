package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.utils.Disposable;
import java.nio.ShortBuffer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/IndexData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface IndexData extends Disposable {
    void bind();

    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    @Deprecated
    ShortBuffer getBuffer();

    ShortBuffer getBuffer(boolean z);

    int getNumIndices();

    int getNumMaxIndices();

    void invalidate();

    void setIndices(ShortBuffer shortBuffer);

    void setIndices(short[] sArr, int i, int i2);

    void unbind();

    void updateIndices(int i, short[] sArr, int i2, int i3);
}

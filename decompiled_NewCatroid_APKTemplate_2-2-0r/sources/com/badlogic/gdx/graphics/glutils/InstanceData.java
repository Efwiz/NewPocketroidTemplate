package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.Disposable;
import java.nio.FloatBuffer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/InstanceData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface InstanceData extends Disposable {
    void bind(ShaderProgram shaderProgram);

    void bind(ShaderProgram shaderProgram, int[] iArr);

    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    VertexAttributes getAttributes();

    @Deprecated
    FloatBuffer getBuffer();

    FloatBuffer getBuffer(boolean z);

    int getNumInstances();

    int getNumMaxInstances();

    void invalidate();

    void setInstanceData(FloatBuffer floatBuffer, int i);

    void setInstanceData(float[] fArr, int i, int i2);

    void unbind(ShaderProgram shaderProgram);

    void unbind(ShaderProgram shaderProgram, int[] iArr);

    void updateInstanceData(int i, FloatBuffer floatBuffer, int i2, int i3);

    void updateInstanceData(int i, float[] fArr, int i2, int i3);
}

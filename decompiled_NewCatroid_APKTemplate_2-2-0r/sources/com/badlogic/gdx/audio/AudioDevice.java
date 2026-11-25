package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/audio/AudioDevice.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface AudioDevice extends Disposable {
    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    int getLatency();

    boolean isMono();

    void pause();

    void resume();

    void setVolume(float f);

    void writeSamples(float[] fArr, int i, int i2);

    void writeSamples(short[] sArr, int i, int i2);
}

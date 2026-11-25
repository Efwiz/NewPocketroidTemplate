package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/audio/AudioRecorder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface AudioRecorder extends Disposable {
    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    void read(short[] sArr, int i, int i2);
}

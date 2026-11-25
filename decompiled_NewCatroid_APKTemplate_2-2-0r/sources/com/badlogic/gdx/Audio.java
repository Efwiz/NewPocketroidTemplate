package com.badlogic.gdx;

import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Audio.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Audio {
    String[] getAvailableOutputDevices();

    AudioDevice newAudioDevice(int i, boolean z);

    AudioRecorder newAudioRecorder(int i, boolean z);

    Music newMusic(FileHandle fileHandle);

    Sound newSound(FileHandle fileHandle);

    boolean switchOutputDevice(String str);
}

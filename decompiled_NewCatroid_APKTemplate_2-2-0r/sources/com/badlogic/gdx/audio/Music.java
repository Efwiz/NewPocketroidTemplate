package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/audio/Music.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Music extends Disposable {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/audio/Music$OnCompletionListener.class
      classes8.dex
     */
    public interface OnCompletionListener {
        void onCompletion(Music music);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    void dispose();

    float getPosition();

    float getVolume();

    boolean isLooping();

    boolean isPlaying();

    void pause();

    void play();

    void setLooping(boolean z);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setPan(float f, float f2);

    void setPosition(float f);

    void setVolume(float f);

    void stop();
}

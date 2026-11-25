package org.catrobat.catroid.raptor;

import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/PlayableAudio.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface PlayableAudio {
    void dispose();

    String getAttachedObjectId();

    float getBaseVolume();

    String getInstanceName();

    float getMaxDistance();

    Vector3 getPosition();

    boolean isPlaying();

    void play();

    void setAttachedObjectId(String id2);

    void setBaseVolume(float volume);

    void setInstanceName(String name2);

    void setMaxDistance(float maxDistance);

    void setPitch(float pitch);

    void setPosition(Vector3 position);

    void setVolume(float volume);

    void stop();

    void update3D(float volume, float pan);
}

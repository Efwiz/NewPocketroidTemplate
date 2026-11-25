package org.catrobat.catroid.raptor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SoundWrapper.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundWrapper implements PlayableAudio {
    private String attachedToObjectId;
    private float baseVolume;
    private String instanceName;
    private final boolean loop;
    private final float pitch;
    private final Sound sound;
    private long instanceId = -1;
    private Vector3 position = new Vector3();
    private float maxDistance = 250.0f;

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void dispose() {
    }

    public SoundWrapper(Sound sound, float volume, float pitch, boolean loop) {
        this.sound = sound;
        this.baseVolume = volume;
        this.pitch = pitch;
        this.loop = loop;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void play() {
        if (this.loop) {
            this.instanceId = this.sound.loop(this.baseVolume, this.pitch, 0.0f);
        } else {
            this.instanceId = this.sound.play(this.baseVolume, this.pitch, 0.0f);
        }
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void stop() {
        long j = this.instanceId;
        if (j != -1) {
            this.sound.stop(j);
        }
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public boolean isPlaying() {
        return this.instanceId != -1;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setInstanceName(String name2) {
        this.instanceName = name2;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public String getInstanceName() {
        return this.instanceName;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setPosition(Vector3 pos) {
        this.position.set(pos);
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public Vector3 getPosition() {
        return this.position;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setAttachedObjectId(String id2) {
        this.attachedToObjectId = id2;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public String getAttachedObjectId() {
        return this.attachedToObjectId;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void update3D(float volume, float pan) {
        long j = this.instanceId;
        if (j != -1) {
            this.sound.setVolume(j, this.baseVolume * volume);
            this.sound.setPan(this.instanceId, pan, volume * this.baseVolume);
        }
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setVolume(float volume) {
        this.baseVolume = volume;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setPitch(float pitch) {
        this.sound.setPitch(this.instanceId, pitch);
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setBaseVolume(float volume) {
        this.baseVolume = volume;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public float getBaseVolume() {
        return this.baseVolume;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public float getMaxDistance() {
        return this.maxDistance;
    }
}

package org.catrobat.catroid.raptor;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/MusicWrapper.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MusicWrapper implements PlayableAudio {
    private String attachedToObjectId;
    private float baseVolume;
    private String instanceName;
    private final boolean loop;
    private final Music music;
    private Vector3 position = new Vector3();
    private float maxDistance = 250.0f;

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setPitch(float pitch) {
    }

    public MusicWrapper(Music music, float volume, float pitch, boolean loop) {
        this.music = music;
        this.loop = loop;
        this.baseVolume = volume;
        music.setVolume(volume);
        music.setLooping(loop);
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void play() {
        this.music.play();
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void stop() {
        this.music.stop();
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void dispose() {
        this.music.dispose();
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public boolean isPlaying() {
        return this.music.isPlaying();
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
        this.music.setVolume(this.baseVolume * volume);
        this.music.setPan(pan, volume);
    }

    @Override // org.catrobat.catroid.raptor.PlayableAudio
    public void setVolume(float volume) {
        this.music.setVolume(volume);
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

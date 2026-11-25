package org.catrobat.catroid.content;

import android.media.MediaPlayer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/MediaPlayerWithSoundDetails.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MediaPlayerWithSoundDetails extends MediaPlayer {
    private Sprite startedBySprite = null;
    private String pathToSoundFile = null;

    public Sprite getStartedBySprite() {
        return this.startedBySprite;
    }

    public void setStartedBySprite(Sprite startedBySprite) {
        this.startedBySprite = startedBySprite;
    }

    public String getPathToSoundFile() {
        return this.pathToSoundFile;
    }

    public void setPathToSoundFile(String pathToSoundFile) {
        this.pathToSoundFile = pathToSoundFile;
    }

    @Override // android.media.MediaPlayer
    public void release() {
        super.release();
        clearData();
    }

    @Override // android.media.MediaPlayer
    public void reset() {
        super.reset();
        clearData();
    }

    private void clearData() {
        this.startedBySprite = null;
        this.pathToSoundFile = null;
    }
}

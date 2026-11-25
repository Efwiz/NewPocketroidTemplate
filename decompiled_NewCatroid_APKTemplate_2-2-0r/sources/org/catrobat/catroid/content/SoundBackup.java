package org.catrobat.catroid.content;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SoundBackup.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundBackup {
    private final int currentPosition;
    private final String pathToSoundFile;
    private final Sprite startedBySprite;

    public SoundBackup(String pathToSoundFile, Sprite startedBySprite, int currentPosition) {
        this.pathToSoundFile = pathToSoundFile;
        this.startedBySprite = startedBySprite;
        this.currentPosition = currentPosition;
    }

    public String getPathToSoundFile() {
        return this.pathToSoundFile;
    }

    public Sprite getStartedBySprite() {
        return this.startedBySprite;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }
}

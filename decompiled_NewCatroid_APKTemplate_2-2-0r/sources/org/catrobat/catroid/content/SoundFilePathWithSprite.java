package org.catrobat.catroid.content;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SoundFilePathWithSprite.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundFilePathWithSprite {
    private final String soundFilePath;
    private final Sprite sprite;

    public SoundFilePathWithSprite(String soundFilePath, Sprite sprite) {
        this.soundFilePath = soundFilePath;
        this.sprite = sprite;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SoundFilePathWithSprite)) {
            return false;
        }
        SoundFilePathWithSprite soundFilePathWithSprite = (SoundFilePathWithSprite) obj;
        return soundFilePathWithSprite.soundFilePath.equals(this.soundFilePath) && soundFilePathWithSprite.sprite == this.sprite;
    }

    public int hashCode() {
        return (this.sprite.hashCode() * 31) + this.soundFilePath.hashCode();
    }
}

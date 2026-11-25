package org.catrobat.catroid.pocketmusic.note;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/MusicalKey.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum MusicalKey {
    BASS(NoteName.D3),
    VIOLIN(NoteName.B4);

    private NoteName noteNameOnMiddleLine;

    MusicalKey(NoteName noteNameOnMiddleLine) {
        this.noteNameOnMiddleLine = noteNameOnMiddleLine;
    }

    public NoteName getNoteNameOnMiddleLine() {
        return this.noteNameOnMiddleLine;
    }
}

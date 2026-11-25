package org.catrobat.catroid.pocketmusic.note;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/MusicalBeat.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum MusicalBeat {
    BEAT_3_4(3, 4, NoteLength.QUARTER),
    BEAT_4_4(4, 4, NoteLength.QUARTER),
    BEAT_16_16(16, 16, NoteLength.SIXTEENTH);

    private final int bottomNumber;
    private final NoteLength noteLength;
    private final int topNumber;

    MusicalBeat(int topNumnber, int bottomNumber, NoteLength noteLength) {
        this.topNumber = topNumnber;
        this.bottomNumber = bottomNumber;
        this.noteLength = noteLength;
    }

    public int getTopNumber() {
        return this.topNumber;
    }

    public int getBottomNumber() {
        return this.bottomNumber;
    }

    public NoteLength getNoteLength() {
        return this.noteLength;
    }

    public static MusicalBeat convertToMusicalBeat(int topNumber, int bottomNumber) {
        for (MusicalBeat musicalBeat : values()) {
            if (musicalBeat.getTopNumber() == topNumber && musicalBeat.getBottomNumber() == bottomNumber) {
                return musicalBeat;
            }
        }
        return BEAT_4_4;
    }
}

package org.catrobat.catroid.pocketmusic.note;

import androidx.collection.SparseArrayCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/NoteLength.class
  classes14.dex
 */
/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r8v0 org.catrobat.catroid.pocketmusic.note.NoteLength, still in use, count: 1, list:
  (r8v0 org.catrobat.catroid.pocketmusic.note.NoteLength) from 0x0074: FILLED_NEW_ARRAY 
  (r9v2 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r1v7 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r2v6 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r3v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r4v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r5v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r6v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r7v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
  (r8v0 org.catrobat.catroid.pocketmusic.note.NoteLength)
 A[WRAPPED] (LINE:31) elemType: org.catrobat.catroid.pocketmusic.note.NoteLength
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: classes14.dex */
public final class NoteLength {
    WHOLE_DOT(6.0f),
    WHOLE(4.0f),
    HALF_DOT(3.0f),
    HALF(2.0f),
    QUARTER_DOT(1.5f),
    QUARTER(1.0f),
    EIGHT_DOT(0.75f),
    EIGHT(0.5f),
    SIXTEENTH(0.25f);

    private static final long DEFAULT_TICK_DURATION_MODIFIER = 8;
    private static final int MINUTE_IN_SECONDS = 60;
    private static final int SECOND_IN_MILLISECONDS = 1000;
    private static final NoteLength SMALLEST_NOTE_LENGTH;
    private static final NoteLength[] SORTED_NOTE_LENGTHS;
    private static SparseArrayCompat<long[]> millisecondsCalculationMap;
    private float length;

    public static NoteLength valueOf(String name2) {
        return (NoteLength) Enum.valueOf(NoteLength.class, name2);
    }

    public static NoteLength[] values() {
        return (NoteLength[]) $VALUES.clone();
    }

    static {
        NoteLength noteLength = SIXTEENTH;
        SORTED_NOTE_LENGTHS = new NoteLength[]{noteLength, noteLength, noteLength, noteLength, noteLength, noteLength, noteLength, noteLength, noteLength};
        SMALLEST_NOTE_LENGTH = noteLength;
        millisecondsCalculationMap = new SparseArrayCompat<>();
    }

    private NoteLength(float length) {
        super($enum$name, $enum$ordinal);
        this.length = length;
    }

    public static NoteLength getNoteLengthFromTickDuration(long duration, int beatsPerMinute) {
        NoteLength noteLength = SMALLEST_NOTE_LENGTH;
        NoteLength[] noteLengthArrValues = values();
        for (int length = noteLengthArrValues.length - 1; length >= 0 && duration - noteLengthArrValues[length].toTicks(beatsPerMinute) >= 0; length--) {
            noteLength = noteLengthArrValues[length];
        }
        return noteLength;
    }

    public static NoteLength getNoteLengthFromMilliseconds(long millis, int beatsPerMinute) {
        long[] milliseconds = getMilliseconds(beatsPerMinute);
        int i = 0;
        long j = milliseconds[0];
        long j2 = milliseconds[milliseconds.length - 1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < milliseconds.length) {
            long j3 = milliseconds[i2];
            if (millis <= j3) {
                i = i2;
                j2 = j3;
                break;
            }
            i3 = i2;
            i2++;
            j = j3;
        }
        if (Math.abs(j - millis) > Math.abs(j2 - millis)) {
            return SORTED_NOTE_LENGTHS[i];
        }
        return SORTED_NOTE_LENGTHS[i3];
    }

    public long toTicks(int beatsPerMinute) {
        return Math.round(((long) beatsPerMinute) * 8 * this.length);
    }

    public long toMilliseconds(int beatsPerMinute) {
        return Math.round(((beatsPerMinute * this.length) * 1000.0f) / 60.0f);
    }

    public static long tickToMilliseconds(long tick) {
        return ((tick * 1000) / 60) / 8;
    }

    public boolean hasStem() {
        return (this == WHOLE || this == WHOLE_DOT) ? false : true;
    }

    public NoteFlag getFlag() {
        if (this == SIXTEENTH) {
            return NoteFlag.DOUBLE_FLAG;
        }
        if (this == EIGHT || this == EIGHT_DOT) {
            return NoteFlag.SINGLE_FLAG;
        }
        return NoteFlag.NO_FLAG;
    }

    public boolean hasDot() {
        return WHOLE_DOT == this || HALF_DOT == this || QUARTER_DOT == this || EIGHT_DOT == this;
    }

    public boolean isHalfOrHigher() {
        return WHOLE_DOT == this || WHOLE == this || HALF_DOT == this || HALF == this;
    }

    private static long[] getMilliseconds(int beatsPerMinute) {
        if (millisecondsCalculationMap.get(beatsPerMinute) == null) {
            millisecondsCalculationMap.put(beatsPerMinute, calculateMilliseconds(beatsPerMinute));
        }
        return millisecondsCalculationMap.get(beatsPerMinute);
    }

    private static long[] calculateMilliseconds(int beatsPerMinute) {
        int length = SORTED_NOTE_LENGTHS.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = SORTED_NOTE_LENGTHS[i].toMilliseconds(beatsPerMinute);
        }
        return jArr;
    }
}

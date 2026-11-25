package org.catrobat.catroid.pocketmusic.note;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/Octave.class
  classes14.dex
 */
/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v3 org.catrobat.catroid.pocketmusic.note.Octave, still in use, count: 1, list:
  (r0v3 org.catrobat.catroid.pocketmusic.note.Octave) from 0x01b9: SPUT (r0v3 org.catrobat.catroid.pocketmusic.note.Octave) (LINE:42) org.catrobat.catroid.pocketmusic.note.Octave.DEFAULT_OCTAVE org.catrobat.catroid.pocketmusic.note.Octave
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
public final class Octave {
    CONTRA_OCTAVE(new NoteName[]{NoteName.C1, NoteName.C1S, NoteName.D1, NoteName.D1S, NoteName.E1, NoteName.F1, NoteName.F1S, NoteName.G1, NoteName.G1S, NoteName.A1, NoteName.A1S, NoteName.B1}),
    GREAT_OCTAVE(new NoteName[]{NoteName.C2, NoteName.C2S, NoteName.D2, NoteName.D2S, NoteName.E2, NoteName.F2, NoteName.F2S, NoteName.G2, NoteName.G2S, NoteName.A2, NoteName.A2S, NoteName.B2}),
    SMALL_OCTAVE(new NoteName[]{NoteName.C3, NoteName.C3S, NoteName.D3, NoteName.D3S, NoteName.E3, NoteName.F3, NoteName.F3S, NoteName.G3, NoteName.G3S, NoteName.A3, NoteName.A3S, NoteName.B3}),
    ONE_LINE_OCTAVE(new NoteName[]{NoteName.C4, NoteName.C4S, NoteName.D4, NoteName.D4S, NoteName.E4, NoteName.F4, NoteName.F4S, NoteName.G4, NoteName.G4S, NoteName.A4, NoteName.A4S, NoteName.B4}),
    TWO_LINE_OCTAVE(new NoteName[]{NoteName.C5, NoteName.C5S, NoteName.D5, NoteName.D5S, NoteName.E5, NoteName.F5, NoteName.F5S, NoteName.G5, NoteName.G5S, NoteName.A5, NoteName.A5S, NoteName.B5}),
    THREE_LINE_OCTAVE(new NoteName[]{NoteName.C6, NoteName.C6S, NoteName.D6, NoteName.D6S, NoteName.E6, NoteName.F6, NoteName.F6S, NoteName.G6, NoteName.G6S, NoteName.A6, NoteName.A6S, NoteName.B6}),
    FOUR_LINE_OCTAVE(new NoteName[]{NoteName.C7, NoteName.C7S, NoteName.D7, NoteName.D7S, NoteName.E7, NoteName.F7, NoteName.F7S, NoteName.G7, NoteName.G7S, NoteName.A7, NoteName.A7S, NoteName.B7});

    public static final Octave DEFAULT_OCTAVE = new Octave(new NoteName[]{NoteName.C4, NoteName.C4S, NoteName.D4, NoteName.D4S, NoteName.E4, NoteName.F4, NoteName.F4S, NoteName.G4, NoteName.G4S, NoteName.A4, NoteName.A4S, NoteName.B4});
    public static final int NUMBER_OF_UNSIGNED_HALF_TONE_STEPS_PER_OCTAVE = 7;
    private NoteName[] noteNames;

    public static Octave valueOf(String name2) {
        return (Octave) Enum.valueOf(Octave.class, name2);
    }

    public static Octave[] values() {
        return (Octave[]) $VALUES.clone();
    }

    static {
    }

    private Octave(NoteName[] noteNames) {
        super($enum$name, $enum$ordinal);
        this.noteNames = (NoteName[]) noteNames.clone();
    }

    public NoteName[] getNoteNames() {
        return (NoteName[]) this.noteNames.clone();
    }

    public Octave next() {
        int iOrdinal = ordinal();
        int i = iOrdinal + 1;
        if (i < values().length) {
            iOrdinal = i;
        }
        return values()[iOrdinal];
    }

    public Octave previous() {
        int iOrdinal = ordinal();
        int i = iOrdinal - 1;
        if (i >= 0) {
            iOrdinal = i;
        }
        return values()[iOrdinal];
    }
}

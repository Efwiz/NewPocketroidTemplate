package org.catrobat.catroid.pocketmusic.note;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/NoteName.class
  classes14.dex
 */
/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v24 org.catrobat.catroid.pocketmusic.note.NoteName, still in use, count: 1, list:
  (r0v24 org.catrobat.catroid.pocketmusic.note.NoteName) from 0x0675: SPUT (r0v24 org.catrobat.catroid.pocketmusic.note.NoteName) (LINE:49) org.catrobat.catroid.pocketmusic.note.NoteName.DEFAULT_NOTE_NAME org.catrobat.catroid.pocketmusic.note.NoteName
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
public final class NoteName {
    EXT0(0, false),
    EXT1(1, false),
    EXT2(2, false),
    EXT3(3, false),
    EXT4(4, false),
    EXT5(5, false),
    EXT6(6, false),
    EXT7(7, false),
    EXT8(8, false),
    EXT9(9, false),
    EXT10(10, false),
    EXT11(11, false),
    EXT12(12, false),
    EXT13(13, false),
    EXT14(14, false),
    EXT15(15, false),
    EXT16(16, false),
    EXT17(17, false),
    EXT18(17, false),
    EXT19(17, false),
    EXT20(20, false),
    A0(21, false),
    A0S(22, true),
    B0(23, false),
    C1(24, false),
    C1S(25, true),
    D1(26, false),
    D1S(27, true),
    E1(28, false),
    F1(29, false),
    F1S(30, true),
    G1(31, false),
    G1S(32, true),
    A1(33, false),
    A1S(34, true),
    B1(35, false),
    C2(36, false),
    C2S(37, true),
    D2(38, false),
    D2S(39, true),
    E2(40, false),
    F2(41, false),
    F2S(42, true),
    G2(43, false),
    G2S(44, true),
    A2(45, false),
    A2S(46, true),
    B2(47, false),
    C3(48, false),
    C3S(49, true),
    D3(50, false),
    D3S(51, true),
    E3(52, false),
    F3(53, false),
    F3S(54, true),
    G3(55, false),
    G3S(56, true),
    A3(57, false),
    A3S(58, true),
    B3(59, false),
    C4(60, false),
    C4S(61, true),
    D4(62, false),
    D4S(63, true),
    E4(64, false),
    F4(65, false),
    F4S(66, true),
    G4(67, false),
    G4S(68, true),
    A4(69, false),
    A4S(70, true),
    B4(71, false),
    C5(72, false),
    C5S(73, true),
    D5(74, false),
    D5S(75, true),
    E5(76, false),
    F5(77, false),
    F5S(78, true),
    G5(79, false),
    G5S(80, true),
    A5(81, false),
    A5S(82, true),
    B5(83, false),
    C6(84, false),
    C6S(85, true),
    D6(86, false),
    D6S(87, true),
    E6(88, false),
    F6(89, false),
    F6S(90, true),
    G6(91, false),
    G6S(92, true),
    A6(93, false),
    A6S(94, true),
    B6(95, false),
    C7(96, false),
    C7S(97, true),
    D7(98, false),
    D7S(99, true),
    E7(100, false),
    F7(101, false),
    F7S(102, true),
    G7(103, false),
    G7S(104, true),
    A7(105, false),
    A7S(106, true),
    B7(107, false),
    C8(108, false),
    EXT109(109, false),
    EXT110(110, false),
    EXT111(111, false),
    EXT112(112, false),
    EXT113(113, false),
    EXT114(114, false),
    EXT115(115, false),
    EXT116(116, false),
    EXT117(117, false),
    EXT118(118, false),
    EXT119(119, false),
    EXT120(120, false),
    EXT121(121, false),
    EXT122(122, false),
    EXT123(123, false),
    EXT124(124, false),
    EXT125(125, false),
    EXT126(126, false),
    EXT127(127, false),
    EXT128(128, false),
    EXT129(129, false),
    EXT130(130, false);

    public static final NoteName DEFAULT_NOTE_NAME = new NoteName(24, false);
    private int midi;
    private boolean signed;

    public static NoteName valueOf(String name2) {
        return (NoteName) Enum.valueOf(NoteName.class, name2);
    }

    public static NoteName[] values() {
        return (NoteName[]) $VALUES.clone();
    }

    static {
    }

    private NoteName(int midi, boolean signed) {
        super($enum$name, $enum$ordinal);
        this.midi = midi;
        this.signed = signed;
    }

    public static NoteName getNoteNameFromMidiValue(int midiValue) {
        NoteName[] noteNameArrValues = values();
        for (int i = 0; i < noteNameArrValues.length; i++) {
            if (noteNameArrValues[i].getMidi() == midiValue) {
                return noteNameArrValues[i];
            }
        }
        return DEFAULT_NOTE_NAME;
    }

    public static int calculateDistanceCountingNoneSignedNotesOnly(NoteName referenceNoteName, NoteName noteName) {
        int i = 1;
        boolean z = noteName.midi - referenceNoteName.midi > 0;
        NoteName next = z ? referenceNoteName : noteName;
        if (z) {
            referenceNoteName = noteName;
        }
        if (!next.isSigned()) {
            i = referenceNoteName.isSigned() ? -1 : 0;
        }
        while (next.getMidi() != referenceNoteName.getMidi()) {
            if (!next.isSigned()) {
                i++;
            }
            next = next.next();
        }
        return z ? i * (-1) : i;
    }

    public static int calculateDistanceToMiddleLineCountingSignedNotesOnly(MusicalKey key, NoteName noteName) {
        return calculateDistanceCountingNoneSignedNotesOnly(key.getNoteNameOnMiddleLine(), noteName);
    }

    public int getMidi() {
        return this.midi;
    }

    public NoteName next() {
        int iOrdinal = ordinal();
        int i = iOrdinal + 1;
        if (i < values().length) {
            iOrdinal = i;
        }
        return values()[iOrdinal];
    }

    public NoteName previous() {
        int iOrdinal = ordinal();
        int i = iOrdinal - 1;
        if (i >= 0) {
            iOrdinal = i;
        }
        return values()[iOrdinal];
    }

    public boolean isSigned() {
        return this.signed;
    }
}

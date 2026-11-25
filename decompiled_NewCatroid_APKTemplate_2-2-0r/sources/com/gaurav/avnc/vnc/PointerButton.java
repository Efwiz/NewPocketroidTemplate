package com.gaurav.avnc.vnc;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: PointerButton.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lcom/gaurav/avnc/vnc/PointerButton;", "", "bitMask", "", "(Ljava/lang/String;II)V", "getBitMask", "()I", "None", "Left", "Middle", "Right", "WheelUp", "WheelDown", "WheelLeft", "WheelRight", "vncclient_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum PointerButton {
    None(0),
    Left(1),
    Middle(2),
    Right(4),
    WheelUp(8),
    WheelDown(16),
    WheelLeft(32),
    WheelRight(64);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final int bitMask;

    public static EnumEntries<PointerButton> getEntries() {
        return $ENTRIES;
    }

    PointerButton(int i) {
        this.bitMask = i;
    }

    public final int getBitMask() {
        return this.bitMask;
    }
}

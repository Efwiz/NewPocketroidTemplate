package org.jetbrains.kotlin.cfg.variable;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0000J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/InitState;", "", "s", "", "(Ljava/lang/String;ILjava/lang/String;)V", "merge", "other", "toString", "INITIALIZED", "INITIALIZED_EXHAUSTIVELY", GrsBaseInfo.CountryCodeSource.UNKNOWN, "NOT_INITIALIZED", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum InitState {
    INITIALIZED("I"),
    INITIALIZED_EXHAUSTIVELY("IE"),
    UNKNOWN("I?"),
    NOT_INITIALIZED("");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String s;

    public static EnumEntries<InitState> getEntries() {
        return $ENTRIES;
    }

    InitState(String str) {
        this.s = str;
    }

    public final InitState merge(InitState other) {
        InitState initState;
        Intrinsics.checkNotNullParameter(other, "other");
        if (this == other || other == (initState = INITIALIZED_EXHAUSTIVELY)) {
            return this;
        }
        return this == initState ? other : UNKNOWN;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.s;
    }
}

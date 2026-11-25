package org.jetbrains.kotlin.fir.analysis.cfa.util;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: CfgTraverser.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/TraverseDirection;", "", "(Ljava/lang/String;I)V", "Forward", "Backward", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TraverseDirection {
    Forward,
    Backward;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<TraverseDirection> getEntries() {
        return $ENTRIES;
    }
}

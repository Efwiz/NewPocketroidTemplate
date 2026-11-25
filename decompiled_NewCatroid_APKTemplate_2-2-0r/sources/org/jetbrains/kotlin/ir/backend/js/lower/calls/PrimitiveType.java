package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: CallsLoweringUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/PrimitiveType;", "", "(Ljava/lang/String;I)V", "FLOATING_POINT_NUMBER", "INTEGER_NUMBER", "STRING", "BOOLEAN", "OTHER", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum PrimitiveType {
    FLOATING_POINT_NUMBER,
    INTEGER_NUMBER,
    STRING,
    BOOLEAN,
    OTHER;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<PrimitiveType> getEntries() {
        return $ENTRIES;
    }
}

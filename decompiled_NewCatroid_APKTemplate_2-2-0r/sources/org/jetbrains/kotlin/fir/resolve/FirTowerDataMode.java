package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: FirRegularTowerDataContexts.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "", "(Ljava/lang/String;I)V", "REGULAR", "CLASS_HEADER_ANNOTATIONS", "NESTED_CLASS", "COMPANION_OBJECT", "CONSTRUCTOR_HEADER", "ENUM_ENTRY", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum FirTowerDataMode {
    REGULAR,
    CLASS_HEADER_ANNOTATIONS,
    NESTED_CLASS,
    COMPANION_OBJECT,
    CONSTRUCTOR_HEADER,
    ENUM_ENTRY;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<FirTowerDataMode> getEntries() {
        return $ENTRIES;
    }
}

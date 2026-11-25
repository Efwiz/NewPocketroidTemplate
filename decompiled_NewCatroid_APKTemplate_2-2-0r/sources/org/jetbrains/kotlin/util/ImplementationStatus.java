package org.jetbrains.kotlin.util;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ImplementationStatus.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/util/ImplementationStatus;", "", "(Ljava/lang/String;I)V", "isOverridable", "", "()Z", "shouldBeImplemented", "getShouldBeImplemented", "NOT_IMPLEMENTED", "VAR_IMPLEMENTED_BY_VAL", "AMBIGUOUSLY_INHERITED", "INHERITED_OR_SYNTHESIZED", "ALREADY_IMPLEMENTED", "CANNOT_BE_IMPLEMENTED", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ImplementationStatus {
    NOT_IMPLEMENTED,
    VAR_IMPLEMENTED_BY_VAL,
    AMBIGUOUSLY_INHERITED,
    INHERITED_OR_SYNTHESIZED,
    ALREADY_IMPLEMENTED,
    CANNOT_BE_IMPLEMENTED;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ImplementationStatus> getEntries() {
        return $ENTRIES;
    }

    public final boolean getShouldBeImplemented() {
        return this == NOT_IMPLEMENTED || this == AMBIGUOUSLY_INHERITED;
    }

    public final boolean isOverridable() {
        return (this == ALREADY_IMPLEMENTED || this == CANNOT_BE_IMPLEMENTED) ? false : true;
    }
}

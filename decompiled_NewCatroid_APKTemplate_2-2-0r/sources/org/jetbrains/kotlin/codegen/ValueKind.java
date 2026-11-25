package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: CallGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ValueKind;", "", "(Ljava/lang/String;I)V", "GENERAL", "GENERAL_VARARG", "DEFAULT_PARAMETER", "DEFAULT_INLINE_PARAMETER", "DEFAULT_MASK", "METHOD_HANDLE_IN_DEFAULT", "READ_OF_INLINE_LAMBDA_FOR_INLINE_SUSPEND_PARAMETER", "READ_OF_OBJECT_FOR_INLINE_SUSPEND_PARAMETER", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ValueKind {
    GENERAL,
    GENERAL_VARARG,
    DEFAULT_PARAMETER,
    DEFAULT_INLINE_PARAMETER,
    DEFAULT_MASK,
    METHOD_HANDLE_IN_DEFAULT,
    READ_OF_INLINE_LAMBDA_FOR_INLINE_SUSPEND_PARAMETER,
    READ_OF_OBJECT_FOR_INLINE_SUSPEND_PARAMETER;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ValueKind> getEntries() {
        return $ENTRIES;
    }
}

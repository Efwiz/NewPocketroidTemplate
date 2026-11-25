package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: IrTypeOperator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "", "(Ljava/lang/String;I)V", "CAST", "IMPLICIT_CAST", "IMPLICIT_NOTNULL", "IMPLICIT_COERCION_TO_UNIT", "IMPLICIT_INTEGER_COERCION", "SAFE_CAST", "INSTANCEOF", "NOT_INSTANCEOF", "SAM_CONVERSION", "IMPLICIT_DYNAMIC_CAST", "REINTERPRET_CAST", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum IrTypeOperator {
    CAST,
    IMPLICIT_CAST,
    IMPLICIT_NOTNULL,
    IMPLICIT_COERCION_TO_UNIT,
    IMPLICIT_INTEGER_COERCION,
    SAFE_CAST,
    INSTANCEOF,
    NOT_INSTANCEOF,
    SAM_CONVERSION,
    IMPLICIT_DYNAMIC_CAST,
    REINTERPRET_CAST;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<IrTypeOperator> getEntries() {
        return $ENTRIES;
    }
}

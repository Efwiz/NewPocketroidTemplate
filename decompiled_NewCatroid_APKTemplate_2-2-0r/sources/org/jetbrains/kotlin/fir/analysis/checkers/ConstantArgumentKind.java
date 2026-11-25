package org.jetbrains.kotlin.fir.analysis.checkers;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: FirConstChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0080\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConstantArgumentKind;", "", "(Ljava/lang/String;I)V", "NOT_CONST", "ENUM_NOT_CONST", "NOT_KCLASS_LITERAL", "NOT_CONST_VAL_IN_CONST_EXPRESSION", "KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ConstantArgumentKind {
    NOT_CONST,
    ENUM_NOT_CONST,
    NOT_KCLASS_LITERAL,
    NOT_CONST_VAL_IN_CONST_EXPRESSION,
    KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ConstantArgumentKind> getEntries() {
        return $ENTRIES;
    }
}

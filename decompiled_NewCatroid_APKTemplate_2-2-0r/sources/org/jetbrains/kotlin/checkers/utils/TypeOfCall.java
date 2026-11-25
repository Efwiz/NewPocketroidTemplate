package org.jetbrains.kotlin.checkers.utils;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: CheckerTestUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/checkers/utils/TypeOfCall;", "", "nameToRender", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getNameToRender", "()Ljava/lang/String;", "VARIABLE_THROUGH_INVOKE", "PROPERTY_GETTER", "FUNCTION", "UNRESOLVED", "OTHER", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TypeOfCall {
    VARIABLE_THROUGH_INVOKE("variable&invoke"),
    PROPERTY_GETTER("variable"),
    FUNCTION("function"),
    UNRESOLVED("unresolved"),
    OTHER("other");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String nameToRender;

    public static EnumEntries<TypeOfCall> getEntries() {
        return $ENTRIES;
    }

    TypeOfCall(String str) {
        this.nameToRender = str;
    }

    public final String getNameToRender() {
        return this.nameToRender;
    }
}

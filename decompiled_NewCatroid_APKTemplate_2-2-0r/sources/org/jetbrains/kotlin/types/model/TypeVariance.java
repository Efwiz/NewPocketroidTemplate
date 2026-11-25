package org.jetbrains.kotlin.types.model;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/model/TypeVariance;", "", "presentation", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getPresentation", "()Ljava/lang/String;", "toString", "IN", "OUT", "INV", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TypeVariance {
    IN("in"),
    OUT("out"),
    INV("");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String presentation;

    public static EnumEntries<TypeVariance> getEntries() {
        return $ENTRIES;
    }

    TypeVariance(String str) {
        this.presentation = str;
    }

    public final String getPresentation() {
        return this.presentation;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.presentation;
    }
}

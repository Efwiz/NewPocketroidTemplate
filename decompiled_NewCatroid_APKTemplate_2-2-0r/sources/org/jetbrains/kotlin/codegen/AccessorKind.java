package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: syntheticAccessorUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorKind;", "", "suffix", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSuffix", "()Ljava/lang/String;", "NORMAL", "IN_CLASS_COMPANION", "FIELD_FROM_LOCAL", "LATEINIT_INTRINSIC", "JVM_DEFAULT_COMPATIBILITY", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum AccessorKind {
    NORMAL("p"),
    IN_CLASS_COMPANION("cp"),
    FIELD_FROM_LOCAL("lp"),
    LATEINIT_INTRINSIC("li"),
    JVM_DEFAULT_COMPATIBILITY("jd");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String suffix;

    public static EnumEntries<AccessorKind> getEntries() {
        return $ENTRIES;
    }

    AccessorKind(String str) {
        this.suffix = str;
    }

    public final String getSuffix() {
        return this.suffix;
    }
}

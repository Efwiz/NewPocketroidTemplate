package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: nullabilityValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/Nullability;", "", "(Ljava/lang/String;I)V", "isNotNull", "", "isNull", "NULL", "NOT_NULL", "NULLABLE", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum Nullability {
    NULL,
    NOT_NULL,
    NULLABLE;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<Nullability> getEntries() {
        return $ENTRIES;
    }

    public final boolean isNull() {
        return this == NULL;
    }

    public final boolean isNotNull() {
        return this == NOT_NULL;
    }
}

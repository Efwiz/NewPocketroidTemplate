package org.jetbrains.kotlin.resolve;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ForbiddenNamedArgumentsTarget.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "toString", "NON_KOTLIN_FUNCTION", "INVOKE_ON_FUNCTION_TYPE", "EXPECTED_CLASS_MEMBER", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ForbiddenNamedArgumentsTarget {
    NON_KOTLIN_FUNCTION("non-Kotlin functions"),
    INVOKE_ON_FUNCTION_TYPE("function types"),
    EXPECTED_CLASS_MEMBER("members of expected classes");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String description;

    public static EnumEntries<ForbiddenNamedArgumentsTarget> getEntries() {
        return $ENTRIES;
    }

    ForbiddenNamedArgumentsTarget(String str) {
        this.description = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.description;
    }
}

package org.jetbrains.kotlin.types;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: EmptyIntersectionTypeKind.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "", MediaTrack.ROLE_DESCRIPTION, "", "isDefinitelyEmpty", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getDescription", "()Ljava/lang/String;", "()Z", "MULTIPLE_CLASSES", "FINAL_CLASS_AND_INTERFACE", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum EmptyIntersectionTypeKind {
    MULTIPLE_CLASSES("multiple incompatible classes", true),
    FINAL_CLASS_AND_INTERFACE("final class and interface", false);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String description;
    private final boolean isDefinitelyEmpty;

    public static EnumEntries<EmptyIntersectionTypeKind> getEntries() {
        return $ENTRIES;
    }

    EmptyIntersectionTypeKind(String str, boolean z) {
        this.description = str;
        this.isDefinitelyEmpty = z;
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: renamed from: isDefinitelyEmpty, reason: from getter */
    public final boolean getIsDefinitelyEmpty() {
        return this.isDefinitelyEmpty;
    }
}

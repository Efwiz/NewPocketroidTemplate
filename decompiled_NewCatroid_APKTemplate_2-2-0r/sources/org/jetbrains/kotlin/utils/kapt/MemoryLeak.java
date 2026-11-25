package org.jetbrains.kotlin.utils.kapt;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: MemoryLeakDetector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/kapt/MemoryLeak;", "", "className", "", "fieldName", MediaTrack.ROLE_DESCRIPTION, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getClassName", "()Ljava/lang/String;", "getDescription", "getFieldName", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemoryLeak {
    private final String className;
    private final String description;
    private final String fieldName;

    public MemoryLeak(String className, String fieldName, String description) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(description, "description");
        this.className = className;
        this.fieldName = fieldName;
        this.description = description;
    }

    public final String getClassName() {
        return this.className;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getFieldName() {
        return this.fieldName;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.utils.kapt.MemoryLeak");
        MemoryLeak memoryLeak = (MemoryLeak) other;
        return Intrinsics.areEqual(this.className, memoryLeak.className) && Intrinsics.areEqual(this.fieldName, memoryLeak.fieldName);
    }

    public int hashCode() {
        return (this.className.hashCode() * 31) + this.fieldName.hashCode();
    }
}

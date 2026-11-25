package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSnapshot;", "()V", "classAbiHash", "", "getClassAbiHash", "()J", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "toString", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AccessibleClassSnapshot extends ClassSnapshot {
    public /* synthetic */ AccessibleClassSnapshot(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract long getClassAbiHash();

    public abstract ClassId getClassId();

    private AccessibleClassSnapshot() {
        super(null);
    }

    public String toString() {
        String string = getClassId().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}

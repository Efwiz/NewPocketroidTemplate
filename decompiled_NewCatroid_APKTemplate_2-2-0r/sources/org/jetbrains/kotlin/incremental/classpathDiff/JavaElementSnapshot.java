package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;", "", "name", "", "abiHash", "", "(Ljava/lang/String;J)V", "getAbiHash", "()J", "getName", "()Ljava/lang/String;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaElementSnapshot {
    private final long abiHash;
    private final String name;

    public JavaElementSnapshot(String name2, long j) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.abiHash = j;
    }

    public final String getName() {
        return this.name;
    }

    public final long getAbiHash() {
        return this.abiHash;
    }
}

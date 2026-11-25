package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/InaccessibleClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSnapshot;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class InaccessibleClassSnapshot extends ClassSnapshot {
    public static final InaccessibleClassSnapshot INSTANCE = new InaccessibleClassSnapshot();

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InaccessibleClassSnapshot)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return -1477474346;
    }

    public String toString() {
        return "InaccessibleClassSnapshot";
    }

    private InaccessibleClassSnapshot() {
        super(null);
    }
}

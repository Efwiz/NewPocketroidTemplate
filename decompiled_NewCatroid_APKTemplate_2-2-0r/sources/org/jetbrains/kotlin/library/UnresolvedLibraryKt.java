package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UnresolvedLibrary.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u001a \u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"UnresolvedLibrary", "Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;", "path", "", "libraryVersion", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "lenient", "", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class UnresolvedLibraryKt {
    public static final RequiredUnresolvedLibrary UnresolvedLibrary(String path, String str) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new RequiredUnresolvedLibrary(path, str);
    }

    public static final UnresolvedLibrary UnresolvedLibrary(String path, String str, boolean z) {
        Intrinsics.checkNotNullParameter(path, "path");
        return z ? new LenientUnresolvedLibrary(path, str) : new RequiredUnresolvedLibrary(path, str);
    }
}

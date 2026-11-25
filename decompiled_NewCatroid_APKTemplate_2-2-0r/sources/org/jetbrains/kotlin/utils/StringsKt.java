package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: strings.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0001¨\u0006\u0006"}, d2 = {"join", "", "collection", "", "", "separator", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StringsKt {
    public static final String join(Iterable<? extends Object> collection, String separator) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Intrinsics.checkNotNullParameter(separator, "separator");
        return kotlin.collections.CollectionsKt.joinToString$default(collection, separator, null, null, 0, null, null, 62, null);
    }
}

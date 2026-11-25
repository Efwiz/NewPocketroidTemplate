package org.jetbrains.kotlin.serialization.js;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinFileRegistry.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/KotlinFileRegistry;", "", "()V", "fileIds", "", "Lorg/jetbrains/kotlin/serialization/js/KotlinFileMetadata;", "", "getFileIds", "()Ljava/util/Map;", "fileIdsImpl", "", "lookup", "file", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFileRegistry {
    private final Map<KotlinFileMetadata, Integer> fileIdsImpl = new LinkedHashMap();

    public final int lookup(KotlinFileMetadata file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Map<KotlinFileMetadata, Integer> map = this.fileIdsImpl;
        Integer numValueOf = map.get(file);
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(this.fileIdsImpl.size());
            map.put(file, numValueOf);
        }
        return numValueOf.intValue();
    }

    public final Map<KotlinFileMetadata, Integer> getFileIds() {
        return this.fileIdsImpl;
    }
}

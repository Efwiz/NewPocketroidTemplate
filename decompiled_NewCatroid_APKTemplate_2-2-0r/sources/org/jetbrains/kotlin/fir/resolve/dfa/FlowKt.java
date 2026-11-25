package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0002¨\u0006\u0004"}, d2 = {"emptyPersistentHashMapBuilder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap$Builder;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlowKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final <K, V> PersistentMap.Builder<K, V> emptyPersistentHashMapBuilder() {
        return ExtensionsKt.persistentHashMapOf(new Pair[0]).builder();
    }
}

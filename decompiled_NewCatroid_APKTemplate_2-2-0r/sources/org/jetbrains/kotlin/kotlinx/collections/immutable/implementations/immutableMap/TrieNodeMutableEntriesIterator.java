package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022 \u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0003B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0096\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNodeMutableEntriesIterator;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNodeBaseIterator;", "", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "(Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)V", ES6Iterator.NEXT_METHOD, "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class TrieNodeMutableEntriesIterator<K, V> extends TrieNodeBaseIterator<K, V, Map.Entry<K, V>> {
    private final PersistentHashMapBuilder<K, V> builder;

    public TrieNodeMutableEntriesIterator(PersistentHashMapBuilder<K, V> builder) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        this.builder = builder;
    }

    @Override // java.util.Iterator
    public Map.Entry<K, V> next() {
        CommonFunctionsKt.m7306assert(hasNextKey());
        setIndex(getIndex() + 2);
        return new MutableMapEntry(this.builder, getBuffer()[getIndex() - 2], getBuffer()[getIndex() - 1]);
    }
}

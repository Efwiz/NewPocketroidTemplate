package org.jetbrains.kotlin.util.slicedMap;

import java.util.Collection;
import java.util.Collections;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: classes6.dex */
public interface SlicedMap {
    public static final SlicedMap DO_NOTHING = new SlicedMap() { // from class: org.jetbrains.kotlin.util.slicedMap.SlicedMap.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "f", "org/jetbrains/kotlin/util/slicedMap/SlicedMap$1", "forEach"));
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
        public void forEach(Function3<WritableSlice, Object, Object, Void> function3) {
            if (function3 == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
        public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
            return readOnlySlice.computeValue(this, k, null, true);
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
        public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
            return Collections.emptySet();
        }
    };

    void forEach(Function3<WritableSlice, Object, Object, Void> function3);

    <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k);

    <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice);
}

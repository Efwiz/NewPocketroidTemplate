package org.jetbrains.kotlin.util.slicedMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.jvm.functions.Function3;
import org.jetbrains.kotlin.com.google.common.collect.ArrayListMultimap;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap;

/* JADX INFO: loaded from: classes6.dex */
public class SlicedMapImpl implements MutableSlicedMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean alwaysAllowRewrite;
    private Map<Object, KeyFMap> map = null;
    private Multimap<WritableSlice<?, ?>, Object> collectiveSliceKeys = null;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "slice";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/util/slicedMap/SlicedMapImpl";
        } else {
            objArr[0] = "f";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getSliceContents";
        } else {
            objArr[1] = "org/jetbrains/kotlin/util/slicedMap/SlicedMapImpl";
        }
        if (i == 1) {
            objArr[2] = "getSliceContents";
        } else if (i != 2 && i != 3) {
            objArr[2] = "forEach";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public SlicedMapImpl(boolean z) {
        this.alwaysAllowRewrite = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap
    public <K, V> void put(WritableSlice<K, V> writableSlice, K k, V v) {
        Object obj;
        if (writableSlice.check(k, v)) {
            if (this.map == null) {
                this.map = new OpenAddressLinearProbingHashTable();
            }
            KeyFMap keyFMap = this.map.get(k);
            if (keyFMap == null) {
                keyFMap = KeyFMap.EMPTY_MAP;
            }
            KeyWithSlice<K, V, WritableSlice<K, V>> key = writableSlice.getKey();
            RewritePolicy rewritePolicy = writableSlice.getRewritePolicy();
            if (this.alwaysAllowRewrite || !rewritePolicy.rewriteProcessingNeeded(k) || (obj = keyFMap.get(key)) == null || rewritePolicy.processRewrite(writableSlice, k, obj, v)) {
                if (writableSlice.isCollective()) {
                    if (this.collectiveSliceKeys == null) {
                        this.collectiveSliceKeys = ArrayListMultimap.create();
                    }
                    this.collectiveSliceKeys.put(writableSlice, k);
                }
                this.map.put(k, keyFMap.plus(key, v));
                writableSlice.afterPut(this, k, v);
            }
        }
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap
    public void clear() {
        this.map = null;
        this.collectiveSliceKeys = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
        Map<Object, KeyFMap> map = this.map;
        KeyFMap keyFMap = map != null ? map.get(k) : null;
        Object obj = keyFMap != null ? keyFMap.get(readOnlySlice.getKey()) : null;
        return (V) readOnlySlice.computeValue(this, k, obj, obj == null);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
        Multimap<WritableSlice<?, ?>, Object> multimap = this.collectiveSliceKeys;
        return multimap == null ? Collections.emptyList() : (Collection<K>) multimap.get(writableSlice);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public void forEach(final Function3<WritableSlice, Object, Object, Void> function3) {
        if (function3 == null) {
            $$$reportNull$$$0(0);
        }
        Map<Object, KeyFMap> map = this.map;
        if (map == null) {
            return;
        }
        map.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SlicedMapImpl.lambda$forEach$0(function3, obj, (KeyFMap) obj2);
            }
        });
    }

    static /* synthetic */ void lambda$forEach$0(Function3 function3, Object obj, KeyFMap keyFMap) {
        if (keyFMap == null) {
            return;
        }
        for (Key key : keyFMap.getKeys()) {
            function3.invoke(((AbstractWritableSlice) key).getSlice(), obj, keyFMap.get(key));
        }
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap
    public <K, V> ImmutableMap<K, V> getSliceContents(final ReadOnlySlice<K, V> readOnlySlice) {
        if (readOnlySlice == null) {
            $$$reportNull$$$0(1);
        }
        if (this.map == null) {
            ImmutableMap<K, V> immutableMapOf = ImmutableMap.of();
            if (immutableMapOf == null) {
                $$$reportNull$$$0(2);
            }
            return immutableMapOf;
        }
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        this.map.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SlicedMapImpl.lambda$getSliceContents$1(readOnlySlice, builder, obj, (KeyFMap) obj2);
            }
        });
        ImmutableMap<K, V> immutableMapBuild = builder.build();
        if (immutableMapBuild == null) {
            $$$reportNull$$$0(3);
        }
        return immutableMapBuild;
    }

    static /* synthetic */ void lambda$getSliceContents$1(ReadOnlySlice readOnlySlice, ImmutableMap.Builder builder, Object obj, KeyFMap keyFMap) {
        Object obj2 = keyFMap.get(readOnlySlice.getKey());
        if (obj2 != null) {
            builder.put(obj, obj2);
        }
    }
}

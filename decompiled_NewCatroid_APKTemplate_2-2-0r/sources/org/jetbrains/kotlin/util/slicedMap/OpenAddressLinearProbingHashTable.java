package org.jetbrains.kotlin.util.slicedMap;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: OpenAddressLinearProbingHashTable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u0000  *\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004:\u0002 !B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00152\u0016\u0010\u0017\u001a\u0012\u0012\u0006\b\u0000\u0012\u00028\u0000\u0012\u0006\b\u0000\u0012\u00028\u00010\u0018H\u0016J\u0018\u0010\u0019\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001a\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\u001f\u0010\u001c\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001d\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u0015H\u0002R\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR&\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/util/slicedMap/OpenAddressLinearProbingHashTable;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/AbstractMutableMap;", "()V", "array", "", "[Ljava/lang/Object;", TypeBlock.NAME_entries, "", "", "getEntries", "()Ljava/util/Set;", "shift", "", "size", "getSize", "()I", "size_", LineReader.CLEAR, "", "forEach", "action", "Ljava/util/function/BiConsumer;", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/lang/Object;", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "rehash", "Companion", "Entry", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OpenAddressLinearProbingHashTable<K, V> extends AbstractMutableMap<K, V> {
    private Object[] array = OpenAddressLinearProbingHashTableKt.EMPTY_ARRAY;
    private int shift;
    private int size_;

    public OpenAddressLinearProbingHashTable() {
        clear();
    }

    @Override // kotlin.collections.AbstractMutableMap
    /* JADX INFO: renamed from: getSize, reason: from getter */
    public int getSize_() {
        return this.size_;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        int iHashCode = ((key.hashCode() * (-1640531527)) >>> this.shift) << 1;
        Object obj = this.array[iHashCode];
        while (obj != null) {
            if (Intrinsics.areEqual(obj, key)) {
                V v = (V) this.array[iHashCode + 1];
                Intrinsics.checkNotNull(v, "null cannot be cast to non-null type V of org.jetbrains.kotlin.util.slicedMap.OpenAddressLinearProbingHashTable");
                return v;
            }
            if (iHashCode == 0) {
                iHashCode = this.array.length;
            }
            iHashCode -= 2;
            obj = this.array[iHashCode];
        }
        return null;
    }

    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        if (!OpenAddressLinearProbingHashTableKt.put(this.array, this.shift, key, value)) {
            return null;
        }
        int i = this.size_ + 1;
        this.size_ = i;
        if (i < (Integer.MAX_VALUE >>> this.shift)) {
            return null;
        }
        rehash();
        return null;
    }

    private final void rehash() {
        int iMax = Math.max(this.shift - 3, 0);
        Object[] objArr = new Object[1 << (33 - iMax)];
        int length = this.array.length;
        for (int i = 0; i < length; i += 2) {
            Object[] objArr2 = this.array;
            Object obj = objArr2[i];
            if (obj != null) {
                OpenAddressLinearProbingHashTableKt.put(objArr, iMax, obj, objArr2[i + 1]);
            }
        }
        this.shift = iMax;
        this.array = objArr;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.shift = 27;
        this.array = new Object[1 << (33 - 27)];
        this.size_ = 0;
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        int length = this.array.length;
        for (int i = 0; i < length; i += 2) {
            Object[] objArr = this.array;
            Object obj = objArr[i];
            if (obj != null) {
                Object obj2 = objArr[i + 1];
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of org.jetbrains.kotlin.util.slicedMap.OpenAddressLinearProbingHashTable");
                action.accept(obj, obj2);
            }
        }
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<Map.Entry<K, V>> getEntries() {
        throw new IllegalStateException("OpenAddressLinearProbingHashTable::entries is not supported and hardly will be");
    }

    /* JADX INFO: compiled from: OpenAddressLinearProbingHashTable.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0002\b\u000b\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00032\u0006\u0010\f\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0003X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/util/slicedMap/OpenAddressLinearProbingHashTable$Entry;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Entry<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final K key;
        private final V value;

        public Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V newValue) {
            throw new UnsupportedOperationException("This Entry is not mutable.");
        }
    }
}

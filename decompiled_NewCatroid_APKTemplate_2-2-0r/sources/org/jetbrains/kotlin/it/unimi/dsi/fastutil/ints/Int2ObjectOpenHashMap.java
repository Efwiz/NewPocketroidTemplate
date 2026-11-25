package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import android.Manifest;
import com.google.android.gms.cast.MediaError;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public class Int2ObjectOpenHashMap<V> extends AbstractInt2ObjectMap<V> implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Int2ObjectMap.FastEntrySet<V> entries;
    protected final float f;
    protected transient int[] key;
    protected transient IntSet keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient V[] value;
    protected transient ObjectCollection<V> values;

    public Int2ObjectOpenHashMap(int i, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        int iArraySize = HashCommon.arraySize(i, f);
        this.n = iArraySize;
        this.minN = iArraySize;
        this.mask = iArraySize - 1;
        this.maxFill = HashCommon.maxFill(iArraySize, f);
        int i2 = this.n;
        this.key = new int[i2 + 1];
        this.value = (V[]) new Object[i2 + 1];
    }

    public Int2ObjectOpenHashMap(int i) {
        this(i, 0.75f);
    }

    public Int2ObjectOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2ObjectOpenHashMap(Map<? extends Integer, ? extends V> map, float f) {
        this(map.size(), f);
        putAll(map);
    }

    public Int2ObjectOpenHashMap(Map<? extends Integer, ? extends V> map) {
        this(map, 0.75f);
    }

    public Int2ObjectOpenHashMap(Int2ObjectMap<V> int2ObjectMap, float f) {
        this(int2ObjectMap.size(), f);
        putAll(int2ObjectMap);
    }

    public Int2ObjectOpenHashMap(Int2ObjectMap<V> int2ObjectMap) {
        this((Int2ObjectMap) int2ObjectMap, 0.75f);
    }

    public Int2ObjectOpenHashMap(int[] iArr, V[] vArr, float f) {
        this(iArr.length, f);
        if (iArr.length != vArr.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + iArr.length + " and " + vArr.length + Constant.AFTER_QUTO);
        }
        for (int i = 0; i < iArr.length; i++) {
            put(iArr[i], vArr[i]);
        }
    }

    public Int2ObjectOpenHashMap(int[] iArr, V[] vArr) {
        this(iArr, vArr, 0.75f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int i) {
        int iArraySize = HashCommon.arraySize(i, this.f);
        if (iArraySize > this.n) {
            rehash(iArraySize);
        }
    }

    private void tryCapacity(long j) {
        int iMin = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(j / this.f))));
        if (iMin > this.n) {
            rehash(iMin);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public V removeEntry(int i) {
        V[] vArr = this.value;
        V v = vArr[i];
        vArr[i] = null;
        this.size--;
        shiftKeys(i);
        int i2 = this.n;
        if (i2 > this.minN && this.size < this.maxFill / 4 && i2 > 16) {
            rehash(i2 / 2);
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public V removeNullEntry() {
        this.containsNullKey = false;
        V[] vArr = this.value;
        int i = this.n;
        V v = vArr[i];
        vArr[i] = null;
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return v;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (this.f <= 0.5d) {
            ensureCapacity(map.size());
        } else {
            tryCapacity(size() + map.size());
        }
        super.putAll(map);
    }

    private int find(int i) {
        int i2;
        if (i == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 != 0) {
            if (i == i3) {
                return iMix;
            }
            do {
                iMix = (iMix + 1) & this.mask;
                i2 = iArr[iMix];
                if (i2 == 0) {
                }
            } while (i != i2);
            return iMix;
        }
        return -(iMix + 1);
    }

    private void insert(int i, int i2, V v) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = i2;
        this.value[i] = v;
        int i3 = this.size;
        this.size = i3 + 1;
        if (i3 >= this.maxFill) {
            rehash(HashCommon.arraySize(i3 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public V put(int i, V v) {
        int iFind = find(i);
        if (iFind < 0) {
            insert((-iFind) - 1, i, v);
            return this.defRetValue;
        }
        V[] vArr = this.value;
        V v2 = vArr[iFind];
        vArr[iFind] = v;
        return v2;
    }

    protected final void shiftKeys(int i) {
        int i2;
        int[] iArr = this.key;
        while (true) {
            int i3 = (i + 1) & this.mask;
            while (true) {
                i2 = iArr[i3];
                if (i2 == 0) {
                    iArr[i] = 0;
                    this.value[i] = null;
                    return;
                }
                int iMix = HashCommon.mix(i2);
                int i4 = this.mask;
                int i5 = iMix & i4;
                if (i > i3) {
                    if (i >= i5 && i5 > i3) {
                        break;
                    } else {
                        i3 = (i3 + 1) & i4;
                    }
                } else if (i >= i5 || i5 > i3) {
                    break;
                } else {
                    i3 = (i3 + 1) & i4;
                }
            }
            iArr[i] = i2;
            V[] vArr = this.value;
            vArr[i] = vArr[i3];
            i = i3;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public V remove(int i) {
        int i2;
        if (i == 0) {
            return this.containsNullKey ? removeNullEntry() : this.defRetValue;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return this.defRetValue;
        }
        if (i == i3) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (i != i2);
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public V get(int i) {
        int i2;
        if (i == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return this.defRetValue;
        }
        if (i == i3) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (i != i2);
        return this.value[iMix];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public boolean containsKey(int i) {
        int i2;
        if (i == 0) {
            return this.containsNullKey;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean containsValue(Object obj) {
        V[] vArr = this.value;
        int[] iArr = this.key;
        if (this.containsNullKey && Objects.equals(vArr[this.n], obj)) {
            return true;
        }
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (iArr[i2] != 0 && Objects.equals(vArr[i2], obj)) {
                return true;
            }
            i = i2;
        }
    }

    public V getOrDefault(int i, V v) {
        int i2;
        if (i == 0) {
            return this.containsNullKey ? this.value[this.n] : v;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i3 = iArr[iMix];
        if (i3 == 0) {
            return v;
        }
        if (i == i3) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            i2 = iArr[iMix];
            if (i2 == 0) {
                return v;
            }
        } while (i != i2);
        return this.value[iMix];
    }

    public V putIfAbsent(int i, V v) {
        int iFind = find(i);
        if (iFind >= 0) {
            return this.value[iFind];
        }
        insert((-iFind) - 1, i, v);
        return this.defRetValue;
    }

    public boolean remove(int i, Object obj) {
        if (i == 0) {
            if (!this.containsNullKey || !Objects.equals(obj, this.value[this.n])) {
                return false;
            }
            removeNullEntry();
            return true;
        }
        int[] iArr = this.key;
        int iMix = HashCommon.mix(i) & this.mask;
        int i2 = iArr[iMix];
        if (i2 == 0) {
            return false;
        }
        if (i == i2 && Objects.equals(obj, this.value[iMix])) {
            removeEntry(iMix);
            return true;
        }
        while (true) {
            iMix = (iMix + 1) & this.mask;
            int i3 = iArr[iMix];
            if (i3 == 0) {
                return false;
            }
            if (i == i3 && Objects.equals(obj, this.value[iMix])) {
                removeEntry(iMix);
                return true;
            }
        }
    }

    public boolean replace(int i, V v, V v2) {
        int iFind = find(i);
        if (iFind < 0 || !Objects.equals(v, this.value[iFind])) {
            return false;
        }
        this.value[iFind] = v2;
        return true;
    }

    public V replace(int i, V v) {
        int iFind = find(i);
        if (iFind < 0) {
            return this.defRetValue;
        }
        V[] vArr = this.value;
        V v2 = vArr[iFind];
        vArr[iFind] = v;
        return v2;
    }

    public V computeIfAbsent(int i, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        int iFind = find(i);
        if (iFind >= 0) {
            return this.value[iFind];
        }
        V vApply = intFunction.apply(i);
        insert((-iFind) - 1, i, vApply);
        return vApply;
    }

    public V computeIfAbsent(int i, Int2ObjectFunction<? extends V> int2ObjectFunction) {
        Objects.requireNonNull(int2ObjectFunction);
        int iFind = find(i);
        if (iFind >= 0) {
            return this.value[iFind];
        }
        if (!int2ObjectFunction.containsKey(i)) {
            return this.defRetValue;
        }
        V v = int2ObjectFunction.get(i);
        insert((-iFind) - 1, i, v);
        return v;
    }

    public V computeIfPresent(int i, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int iFind = find(i);
        if (iFind < 0) {
            return this.defRetValue;
        }
        V vApply = biFunction.apply(Integer.valueOf(i), this.value[iFind]);
        if (vApply == null) {
            if (i == 0) {
                removeNullEntry();
            } else {
                removeEntry(iFind);
            }
            return this.defRetValue;
        }
        this.value[iFind] = vApply;
        return vApply;
    }

    public V compute(int i, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int iFind = find(i);
        V vApply = biFunction.apply(Integer.valueOf(i), iFind >= 0 ? this.value[iFind] : null);
        if (vApply == null) {
            if (iFind >= 0) {
                if (i == 0) {
                    removeNullEntry();
                } else {
                    removeEntry(iFind);
                }
            }
            return this.defRetValue;
        }
        if (iFind < 0) {
            insert((-iFind) - 1, i, vApply);
            return vApply;
        }
        this.value[iFind] = vApply;
        return vApply;
    }

    public V merge(int i, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Manifest manifest;
        Objects.requireNonNull(biFunction);
        int iFind = find(i);
        if (iFind < 0 || (manifest = this.value[iFind]) == null) {
            if (v == null) {
                return this.defRetValue;
            }
            insert((-iFind) - 1, i, v);
            return v;
        }
        V vApply = biFunction.apply(manifest, v);
        if (vApply == null) {
            if (i == 0) {
                removeNullEntry();
            } else {
                removeEntry(iFind);
            }
            return this.defRetValue;
        }
        this.value[iFind] = vApply;
        return vApply;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
        Arrays.fill(this.value, (Object) null);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Int2ObjectMap.Entry<V>, Map.Entry<Integer, V> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
            return Int2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Int2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = Int2ObjectOpenHashMap.this.value[this.index];
            Int2ObjectOpenHashMap.this.value[this.index] = v;
            return v2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2ObjectOpenHashMap.this.key[this.index]);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Int2ObjectOpenHashMap.this.key[this.index] == ((Integer) entry.getKey()).intValue() && Objects.equals(Int2ObjectOpenHashMap.this.value[this.index], entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2ObjectOpenHashMap.this.key[this.index] ^ (Int2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Int2ObjectOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Int2ObjectOpenHashMap.this.key[this.index] + "=>" + Int2ObjectOpenHashMap.this.value[this.index];
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int c;
        int last;
        boolean mustReturnNullKey;
        int pos;
        IntArrayList wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            this.pos = Int2ObjectOpenHashMap.this.n;
            this.last = -1;
            this.c = Int2ObjectOpenHashMap.this.size;
            this.mustReturnNullKey = Int2ObjectOpenHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.c != 0;
        }

        public int nextEntry() {
            int i;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i2 = Int2ObjectOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    int i3 = this.wrapped.getInt((-i) - 1);
                    int iMix = HashCommon.mix(i3);
                    int i4 = Int2ObjectOpenHashMap.this.mask;
                    while (true) {
                        int i5 = iMix & i4;
                        if (i3 == iArr[i5]) {
                            return i5;
                        }
                        iMix = i5 + 1;
                        i4 = Int2ObjectOpenHashMap.this.mask;
                    }
                }
            } while (iArr[i] == 0);
            this.last = i;
            return i;
        }

        public void forEachRemaining(ConsumerType consumertype) {
            int i;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i2 = Int2ObjectOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int i4 = this.wrapped.getInt((-i3) - 1);
                    int iMix = HashCommon.mix(i4);
                    int i5 = Int2ObjectOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i5;
                        if (i4 == iArr[i]) {
                            break;
                        }
                        iMix = i + 1;
                        i5 = Int2ObjectOpenHashMap.this.mask;
                    }
                    acceptOnIndex(consumertype, i);
                    this.c--;
                } else if (iArr[i3] != 0) {
                    this.last = i3;
                    acceptOnIndex(consumertype, i3);
                    this.c--;
                }
            }
        }

        private void shiftKeys(int i) {
            int i2;
            int i3;
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            while (true) {
                int i4 = i + 1;
                int i5 = Int2ObjectOpenHashMap.this.mask;
                while (true) {
                    i2 = i4 & i5;
                    i3 = iArr[i2];
                    if (i3 == 0) {
                        iArr[i] = 0;
                        Int2ObjectOpenHashMap.this.value[i] = null;
                        return;
                    }
                    int iMix = HashCommon.mix(i3) & Int2ObjectOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = Int2ObjectOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = Int2ObjectOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(iArr[i2]);
                }
                iArr[i] = i3;
                Int2ObjectOpenHashMap.this.value[i] = Int2ObjectOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Int2ObjectOpenHashMap.this.n) {
                Int2ObjectOpenHashMap.this.containsNullKey = false;
                Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Int2ObjectOpenHashMap.this.remove(this.wrapped.getInt((-i2) - 1));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Int2ObjectOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private Int2ObjectOpenHashMap<V>.MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Int2ObjectOpenHashMap<V>.MapEntry next() {
            Int2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> consumer, int i) {
            Int2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Int2ObjectMap.Entry<V>>> implements ObjectIterator<Int2ObjectMap.Entry<V>> {
        private final Int2ObjectOpenHashMap<V>.MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Int2ObjectOpenHashMap<V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends Int2ObjectOpenHashMap<V>.MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Int2ObjectOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Int2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Int2ObjectOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Int2ObjectOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Int2ObjectOpenHashMap.this.n);
                return true;
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                if (iArr[i] != 0) {
                    this.c++;
                    this.pos = i + 1;
                    acceptOnIndex(consumertype, i);
                    return true;
                }
                this.pos = i + 1;
            }
        }

        public void forEachRemaining(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Int2ObjectOpenHashMap.this.n);
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                if (iArr[i] != 0) {
                    acceptOnIndex(consumertype, i);
                    this.c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (this.hasSplit) {
                return Math.min(Int2ObjectOpenHashMap.this.size - this.c, ((long) ((((double) Int2ObjectOpenHashMap.this.realSize()) / ((double) Int2ObjectOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Int2ObjectOpenHashMap.this.size - this.c;
        }

        public SplitType trySplit() {
            int i;
            int i2 = this.pos;
            int i3 = this.max;
            if (i2 >= i3 - 1 || (i = (i3 - i2) >> 1) <= 1) {
                return null;
            }
            int i4 = i + i2;
            SplitType splittype = (SplitType) makeForSplit(i2, i4, this.mustReturnNull);
            this.pos = i4;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return splittype;
        }
    }

    private final class EntrySpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super Int2ObjectMap.Entry<V>>, Int2ObjectOpenHashMap<V>.EntrySpliterator> implements ObjectSpliterator<Int2ObjectMap.Entry<V>> {
        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator trySplit() {
            return (Spliterator) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public /* bridge */ /* synthetic */ ObjectSpliterator trySplit() {
            return (ObjectSpliterator) super.trySplit();
        }

        EntrySpliterator() {
            super();
        }

        EntrySpliterator(int i, int i2, boolean z, boolean z2) {
            super(i, i2, z, z2);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Int2ObjectMap.Entry<V>> consumer, int i) {
            consumer.accept(new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.FastEntrySet
        public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            int i;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return false;
            }
            int iIntValue = ((Integer) entry.getKey()).intValue();
            Object value = entry.getValue();
            if (iIntValue == 0) {
                return Int2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n], value);
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            int iMix = HashCommon.mix(iIntValue) & Int2ObjectOpenHashMap.this.mask;
            int i2 = iArr[iMix];
            if (i2 == 0) {
                return false;
            }
            if (iIntValue == i2) {
                return Objects.equals(Int2ObjectOpenHashMap.this.value[iMix], value);
            }
            do {
                iMix = (iMix + 1) & Int2ObjectOpenHashMap.this.mask;
                i = iArr[iMix];
                if (i == 0) {
                    return false;
                }
            } while (iIntValue != i);
            return Objects.equals(Int2ObjectOpenHashMap.this.value[iMix], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return false;
            }
            int iIntValue = ((Integer) entry.getKey()).intValue();
            Object value = entry.getValue();
            if (iIntValue == 0) {
                if (!Int2ObjectOpenHashMap.this.containsNullKey || !Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n], value)) {
                    return false;
                }
                Int2ObjectOpenHashMap.this.removeNullEntry();
                return true;
            }
            int[] iArr = Int2ObjectOpenHashMap.this.key;
            int iMix = HashCommon.mix(iIntValue) & Int2ObjectOpenHashMap.this.mask;
            int i = iArr[iMix];
            if (i == 0) {
                return false;
            }
            if (i == iIntValue) {
                if (!Objects.equals(Int2ObjectOpenHashMap.this.value[iMix], value)) {
                    return false;
                }
                Int2ObjectOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Int2ObjectOpenHashMap.this.mask;
                int i2 = iArr[iMix];
                if (i2 == 0) {
                    return false;
                }
                if (i2 == iIntValue && Objects.equals(Int2ObjectOpenHashMap.this.value[iMix], value)) {
                    Int2ObjectOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.n], Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n]));
            }
            int i = Int2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Int2ObjectOpenHashMap.this.key[i2] != 0) {
                    consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[i2], Int2ObjectOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
            AbstractInt2ObjectMap.BasicEntry basicEntry = new AbstractInt2ObjectMap.BasicEntry();
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                basicEntry.key = Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.n];
                basicEntry.value = Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Int2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Int2ObjectOpenHashMap.this.key[i2] != 0) {
                    basicEntry.key = Int2ObjectOpenHashMap.this.key[i2];
                    basicEntry.value = Int2ObjectOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap
    public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends Int2ObjectOpenHashMap<V>.MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2ObjectOpenHashMap.this.key[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2ObjectOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<java.util.function.IntConsumer, Int2ObjectOpenHashMap<V>.KeySpliterator> implements IntSpliterator {
        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator trySplit() {
            return (Spliterator) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ IntSpliterator trySplit() {
            return (IntSpliterator) super.trySplit();
        }

        KeySpliterator() {
            super();
        }

        KeySpliterator(int i, int i2, boolean z, boolean z2) {
            super(i, i2, z, z2);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            if (this.hasSplit) {
                return 257;
            }
            return MediaError.DetailedErrorCode.DASH_NETWORK;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2ObjectOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.KeySpliterator makeForSplit(int i, int i2, boolean z) {
            return new KeySpliterator(i, i2, z, true);
        }
    }

    private final class KeySet extends AbstractIntSet {
        private KeySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntIterator iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return new KeySpliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer intConsumer) {
            if (Int2ObjectOpenHashMap.this.containsNullKey) {
                intConsumer.accept(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.n]);
            }
            int i = Int2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                int i3 = Int2ObjectOpenHashMap.this.key[i2];
                if (i3 != 0) {
                    intConsumer.accept(i3);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectOpenHashMap.this.size;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean contains(int i) {
            return Int2ObjectOpenHashMap.this.containsKey(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
        public boolean remove(int i) {
            int i2 = Int2ObjectOpenHashMap.this.size;
            Int2ObjectOpenHashMap.this.remove(i);
            return Int2ObjectOpenHashMap.this.size != i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    public Set<Integer> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends Int2ObjectOpenHashMap<V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Int2ObjectOpenHashMap.this.value[i]);
        }

        @Override // java.util.Iterator
        public V next() {
            return Int2ObjectOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends Int2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super V>, Int2ObjectOpenHashMap<V>.ValueSpliterator> implements ObjectSpliterator<V> {
        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Spliterator
        public /* bridge */ /* synthetic */ boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator trySplit() {
            return (Spliterator) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public /* bridge */ /* synthetic */ ObjectSpliterator trySplit() {
            return (ObjectSpliterator) super.trySplit();
        }

        ValueSpliterator() {
            super();
        }

        ValueSpliterator(int i, int i2, boolean z, boolean z2) {
            super(i, i2, z, z2);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 0 : 64;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Int2ObjectOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.MapSpliterator
        public final Int2ObjectOpenHashMap<V>.ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap.1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
                public ObjectSpliterator<V> spliterator() {
                    return new ValueSpliterator();
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super V> consumer) {
                    if (Int2ObjectOpenHashMap.this.containsNullKey) {
                        consumer.accept(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.n]);
                    }
                    int i = Int2ObjectOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Int2ObjectOpenHashMap.this.key[i2] != 0) {
                            consumer.accept(Int2ObjectOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2ObjectOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return Int2ObjectOpenHashMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2ObjectOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int i) {
        int iNextPowerOfTwo = HashCommon.nextPowerOfTwo((int) Math.ceil(i / this.f));
        if (iNextPowerOfTwo >= this.n || this.size > HashCommon.maxFill(iNextPowerOfTwo, this.f)) {
            return true;
        }
        try {
            rehash(iNextPowerOfTwo);
            return true;
        } catch (OutOfMemoryError unused) {
            return false;
        }
    }

    protected void rehash(int i) {
        int i2;
        int[] iArr = this.key;
        V[] vArr = this.value;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr2 = new int[i4];
        V[] vArr2 = (V[]) new Object[i4];
        int i5 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i6 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i5--;
                    i2 = iArr[i5];
                } while (i2 == 0);
                int iMix = HashCommon.mix(i2) & i3;
                if (iArr2[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & i3;
                    } while (iArr2[iMix] != 0);
                }
                iArr2[iMix] = iArr[i5];
                vArr2[iMix] = vArr[i5];
                iRealSize = i6;
            } else {
                vArr2[i] = vArr[this.n];
                this.n = i;
                this.mask = i3;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = iArr2;
                this.value = vArr2;
                return;
            }
        }
    }

    public Int2ObjectOpenHashMap<V> clone() {
        try {
            Int2ObjectOpenHashMap<V> int2ObjectOpenHashMap = (Int2ObjectOpenHashMap) super.clone();
            int2ObjectOpenHashMap.keys = null;
            int2ObjectOpenHashMap.values = null;
            int2ObjectOpenHashMap.entries = null;
            int2ObjectOpenHashMap.containsNullKey = this.containsNullKey;
            int2ObjectOpenHashMap.key = (int[]) this.key.clone();
            int2ObjectOpenHashMap.value = (V[]) ((Object[]) this.value.clone());
            return int2ObjectOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public int hashCode() {
        int iHashCode;
        int iRealSize = realSize();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = iRealSize - 1;
            if (iRealSize == 0) {
                break;
            }
            while (true) {
                iHashCode = this.key[i];
                if (iHashCode != 0) {
                    break;
                }
                i++;
            }
            V v = this.value[i];
            if (this != v) {
                iHashCode ^= v == null ? 0 : v.hashCode();
            }
            i2 += iHashCode;
            i++;
            iRealSize = i3;
        }
        if (!this.containsNullKey) {
            return i2;
        }
        V v2 = this.value[this.n];
        return i2 + (v2 != null ? v2.hashCode() : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int[] iArr = this.key;
        V[] vArr = this.value;
        EntryIterator entryIterator = new EntryIterator();
        objectOutputStream.defaultWriteObject();
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            int iNextEntry = entryIterator.nextEntry();
            objectOutputStream.writeInt(iArr[iNextEntry]);
            objectOutputStream.writeObject(vArr[iNextEntry]);
            i = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        int i;
        objectInputStream.defaultReadObject();
        int iArraySize = HashCommon.arraySize(this.size, this.f);
        this.n = iArraySize;
        this.maxFill = HashCommon.maxFill(iArraySize, this.f);
        int i2 = this.n;
        this.mask = i2 - 1;
        int[] iArr = new int[i2 + 1];
        this.key = iArr;
        V[] vArr = (V[]) new Object[i2 + 1];
        this.value = vArr;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            int i5 = objectInputStream.readInt();
            Object object = objectInputStream.readObject();
            if (i5 == 0) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = HashCommon.mix(i5);
                int i6 = this.mask;
                while (true) {
                    i = iMix & i6;
                    if (iArr[i] != 0) {
                        iMix = i + 1;
                        i6 = this.mask;
                    }
                }
            }
            iArr[i] = i5;
            vArr[i] = object;
            i3 = i4;
        }
    }
}

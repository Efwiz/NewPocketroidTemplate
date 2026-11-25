package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import android.Manifest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public class Object2LongOpenHashMap<K> extends AbstractObject2LongMap<K> implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Object2LongMap.FastEntrySet<K> entries;
    protected final float f;
    protected transient K[] key;
    protected transient ObjectSet<K> keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient long[] value;
    protected transient LongCollection values;

    public Object2LongOpenHashMap(int i, float f) {
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
        this.key = (K[]) new Object[i2 + 1];
        this.value = new long[i2 + 1];
    }

    public Object2LongOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2LongOpenHashMap(Object2LongMap<K> object2LongMap, float f) {
        this(object2LongMap.size(), f);
        putAll(object2LongMap);
    }

    public Object2LongOpenHashMap(Object2LongMap<K> object2LongMap) {
        this(object2LongMap, 0.75f);
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
    public long removeEntry(int i) {
        long j = this.value[i];
        this.size--;
        shiftKeys(i);
        int i2 = this.n;
        if (i2 > this.minN && this.size < this.maxFill / 4 && i2 > 16) {
            rehash(i2 / 2);
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long removeNullEntry() {
        this.containsNullKey = false;
        K[] kArr = this.key;
        int i = this.n;
        kArr[i] = null;
        long j = this.value[i];
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return j;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, java.util.Map
    public void putAll(Map<? extends K, ? extends Long> map) {
        if (this.f <= 0.5d) {
            ensureCapacity(map.size());
        } else {
            tryCapacity(size() + map.size());
        }
        super.putAll(map);
    }

    private int find(K k) {
        K k2;
        if (k == null) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(k.hashCode()) & this.mask;
        K k3 = kArr[iMix];
        if (k3 != null) {
            if (k.equals(k3)) {
                return iMix;
            }
            do {
                iMix = (iMix + 1) & this.mask;
                k2 = kArr[iMix];
                if (k2 == null) {
                }
            } while (!k.equals(k2));
            return iMix;
        }
        return -(iMix + 1);
    }

    private void insert(int i, K k, long j) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = k;
        this.value[i] = j;
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(i2 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction
    public long put(K k, long j) {
        int iFind = find(k);
        if (iFind < 0) {
            insert((-iFind) - 1, k, j);
            return this.defRetValue;
        }
        long[] jArr = this.value;
        long j2 = jArr[iFind];
        jArr[iFind] = j;
        return j2;
    }

    protected final void shiftKeys(int i) {
        K k;
        K[] kArr = this.key;
        while (true) {
            int i2 = (i + 1) & this.mask;
            while (true) {
                k = kArr[i2];
                if (k == null) {
                    kArr[i] = null;
                    return;
                }
                int iMix = HashCommon.mix(k.hashCode());
                int i3 = this.mask;
                int i4 = iMix & i3;
                if (i > i2) {
                    if (i >= i4 && i4 > i2) {
                        break;
                    } else {
                        i2 = (i2 + 1) & i3;
                    }
                } else if (i >= i4 || i4 > i2) {
                    break;
                } else {
                    i2 = (i2 + 1) & i3;
                }
            }
            kArr[i] = k;
            long[] jArr = this.value;
            jArr[i] = jArr[i2];
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction
    public long removeLong(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey ? removeNullEntry() : this.defRetValue;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(obj.hashCode()) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (obj.equals(k2)) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return this.defRetValue;
            }
        } while (!obj.equals(k));
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction
    public long getLong(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(obj.hashCode()) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (obj.equals(k2)) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return this.defRetValue;
            }
        } while (!obj.equals(k));
        return this.value[iMix];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(obj.hashCode()) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return false;
        }
        if (obj.equals(k2)) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return false;
            }
        } while (!obj.equals(k));
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
    public boolean containsValue(long j) {
        long[] jArr = this.value;
        K[] kArr = this.key;
        if (this.containsNullKey && jArr[this.n] == j) {
            return true;
        }
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (kArr[i2] != null && jArr[i2] == j) {
                return true;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
    public long getOrDefault(Object obj, long j) {
        K k;
        if (obj == null) {
            return this.containsNullKey ? this.value[this.n] : j;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(obj.hashCode()) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return j;
        }
        if (obj.equals(k2)) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return j;
            }
        } while (!obj.equals(k));
        return this.value[iMix];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
    public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int iFind = find(k);
        Long lApply = biFunction.apply(k, iFind >= 0 ? Long.valueOf(this.value[iFind]) : null);
        if (lApply == null) {
            if (iFind >= 0) {
                if (k == null) {
                    removeNullEntry();
                } else {
                    removeEntry(iFind);
                }
            }
            return this.defRetValue;
        }
        long jLongValue = lApply.longValue();
        if (iFind < 0) {
            insert((-iFind) - 1, k, jLongValue);
            return jLongValue;
        }
        this.value[iFind] = jLongValue;
        return jLongValue;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, (Object) null);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Object2LongMap.Entry<K>, Map.Entry<K, Long> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Object2LongOpenHashMap.this.key[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry
        public long getLongValue() {
            return Object2LongOpenHashMap.this.value[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry
        public long setValue(long j) {
            long j2 = Object2LongOpenHashMap.this.value[this.index];
            Object2LongOpenHashMap.this.value[this.index] = j;
            return j2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry, java.util.Map.Entry
        @Deprecated
        public Long getValue() {
            return Long.valueOf(Object2LongOpenHashMap.this.value[this.index]);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.Entry, java.util.Map.Entry
        @Deprecated
        public Long setValue(Long l) {
            return Long.valueOf(setValue(l.longValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Objects.equals(Object2LongOpenHashMap.this.key[this.index], entry.getKey()) && Object2LongOpenHashMap.this.value[this.index] == ((Long) entry.getValue()).longValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (Object2LongOpenHashMap.this.key[this.index] == null ? 0 : Object2LongOpenHashMap.this.key[this.index].hashCode()) ^ HashCommon.long2int(Object2LongOpenHashMap.this.value[this.index]);
        }

        public String toString() {
            return Object2LongOpenHashMap.this.key[this.index] + "=>" + Object2LongOpenHashMap.this.value[this.index];
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int c;
        int last;
        boolean mustReturnNullKey;
        int pos;
        ObjectArrayList<K> wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            this.pos = Object2LongOpenHashMap.this.n;
            this.last = -1;
            this.c = Object2LongOpenHashMap.this.size;
            this.mustReturnNullKey = Object2LongOpenHashMap.this.containsNullKey;
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
                int i2 = Object2LongOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i) - 1);
                    int iMix = HashCommon.mix(k.hashCode());
                    int i3 = Object2LongOpenHashMap.this.mask;
                    while (true) {
                        int i4 = iMix & i3;
                        if (k.equals(kArr[i4])) {
                            return i4;
                        }
                        iMix = i4 + 1;
                        i3 = Object2LongOpenHashMap.this.mask;
                    }
                }
            } while (kArr[i] == null);
            this.last = i;
            return i;
        }

        public void forEachRemaining(ConsumerType consumertype) {
            int i;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i2 = Object2LongOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i3) - 1);
                    int iMix = HashCommon.mix(k.hashCode());
                    int i4 = Object2LongOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i4;
                        if (k.equals(kArr[i])) {
                            break;
                        }
                        iMix = i + 1;
                        i4 = Object2LongOpenHashMap.this.mask;
                    }
                    acceptOnIndex(consumertype, i);
                    this.c--;
                } else if (kArr[i3] != null) {
                    this.last = i3;
                    acceptOnIndex(consumertype, i3);
                    this.c--;
                }
            }
        }

        private void shiftKeys(int i) {
            int i2;
            K k;
            K[] kArr = Object2LongOpenHashMap.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = Object2LongOpenHashMap.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    k = kArr[i2];
                    if (k == null) {
                        kArr[i] = null;
                        return;
                    }
                    int iMix = HashCommon.mix(k.hashCode()) & Object2LongOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Object2LongOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Object2LongOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                    }
                    this.wrapped.add(kArr[i2]);
                }
                kArr[i] = k;
                Object2LongOpenHashMap.this.value[i] = Object2LongOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Object2LongOpenHashMap.this.n) {
                Object2LongOpenHashMap.this.containsNullKey = false;
                Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Object2LongOpenHashMap.this.removeLong(this.wrapped.set((-i2) - 1, null));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Object2LongOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends Object2LongOpenHashMap<K>.MapIterator<Consumer<? super Object2LongMap.Entry<K>>> implements ObjectIterator<Object2LongMap.Entry<K>> {
        private Object2LongOpenHashMap<K>.MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Object2LongOpenHashMap<K>.MapEntry next() {
            Object2LongOpenHashMap<K>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Object2LongMap.Entry<K>> consumer, int i) {
            Object2LongOpenHashMap<K>.MapEntry mapEntry = new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends Object2LongOpenHashMap<K>.MapIterator<Consumer<? super Object2LongMap.Entry<K>>> implements ObjectIterator<Object2LongMap.Entry<K>> {
        private final Object2LongOpenHashMap<K>.MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Object2LongOpenHashMap<K>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Object2LongMap.Entry<K>> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends Object2LongOpenHashMap<K>.MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Object2LongOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Object2LongOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Object2LongOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Object2LongOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Object2LongOpenHashMap.this.n);
                return true;
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                if (kArr[i] != null) {
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
                acceptOnIndex(consumertype, Object2LongOpenHashMap.this.n);
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                if (kArr[i] != null) {
                    acceptOnIndex(consumertype, i);
                    this.c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (this.hasSplit) {
                return Math.min(Object2LongOpenHashMap.this.size - this.c, ((long) ((((double) Object2LongOpenHashMap.this.realSize()) / ((double) Object2LongOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Object2LongOpenHashMap.this.size - this.c;
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

    private final class EntrySpliterator extends Object2LongOpenHashMap<K>.MapSpliterator<Consumer<? super Object2LongMap.Entry<K>>, Object2LongOpenHashMap<K>.EntrySpliterator> implements ObjectSpliterator<Object2LongMap.Entry<K>> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Object2LongMap.Entry<K>> consumer, int i) {
            consumer.accept(new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final Object2LongOpenHashMap<K>.EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Object2LongMap.Entry<K>> implements Object2LongMap.FastEntrySet<K> {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.FastEntrySet
        public ObjectIterator<Object2LongMap.Entry<K>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Object2LongMap.Entry<K>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            K k;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return false;
            }
            Object key = entry.getKey();
            long jLongValue = ((Long) entry.getValue()).longValue();
            if (key == null) {
                return Object2LongOpenHashMap.this.containsNullKey && Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n] == jLongValue;
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            int iMix = HashCommon.mix(key.hashCode()) & Object2LongOpenHashMap.this.mask;
            K k2 = kArr[iMix];
            if (k2 == null) {
                return false;
            }
            if (key.equals(k2)) {
                return Object2LongOpenHashMap.this.value[iMix] == jLongValue;
            }
            do {
                iMix = (iMix + 1) & Object2LongOpenHashMap.this.mask;
                k = kArr[iMix];
                if (k == null) {
                    return false;
                }
            } while (!key.equals(k));
            return Object2LongOpenHashMap.this.value[iMix] == jLongValue;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return false;
            }
            Object key = entry.getKey();
            long jLongValue = ((Long) entry.getValue()).longValue();
            if (key == null) {
                if (!Object2LongOpenHashMap.this.containsNullKey || Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n] != jLongValue) {
                    return false;
                }
                Object2LongOpenHashMap.this.removeNullEntry();
                return true;
            }
            K[] kArr = Object2LongOpenHashMap.this.key;
            int iMix = HashCommon.mix(key.hashCode()) & Object2LongOpenHashMap.this.mask;
            K k = kArr[iMix];
            if (k == null) {
                return false;
            }
            if (k.equals(key)) {
                if (Object2LongOpenHashMap.this.value[iMix] != jLongValue) {
                    return false;
                }
                Object2LongOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Object2LongOpenHashMap.this.mask;
                K k2 = kArr[iMix];
                if (k2 == null) {
                    return false;
                }
                if (k2.equals(key) && Object2LongOpenHashMap.this.value[iMix] == jLongValue) {
                    Object2LongOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2LongOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2LongOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
            if (Object2LongOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractObject2LongMap.BasicEntry(Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n], Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n]));
            }
            int i = Object2LongOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Object2LongOpenHashMap.this.key[i2] != null) {
                    consumer.accept(new AbstractObject2LongMap.BasicEntry(Object2LongOpenHashMap.this.key[i2], Object2LongOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
            AbstractObject2LongMap.BasicEntry basicEntry = new AbstractObject2LongMap.BasicEntry();
            if (Object2LongOpenHashMap.this.containsNullKey) {
                basicEntry.key = Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n];
                basicEntry.value = Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Object2LongOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Object2LongOpenHashMap.this.key[i2] != null) {
                    basicEntry.key = Object2LongOpenHashMap.this.key[i2];
                    basicEntry.value = Object2LongOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap
    public Object2LongMap.FastEntrySet<K> object2LongEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends Object2LongOpenHashMap<K>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Object2LongOpenHashMap.this.key[i]);
        }

        @Override // java.util.Iterator
        public K next() {
            return Object2LongOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends Object2LongOpenHashMap<K>.MapSpliterator<Consumer<? super K>, Object2LongOpenHashMap<K>.KeySpliterator> implements ObjectSpliterator<K> {
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

        KeySpliterator() {
            super();
        }

        KeySpliterator(int i, int i2, boolean z, boolean z2) {
            super(i, i2, z, z2);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Object2LongOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final Object2LongOpenHashMap<K>.KeySpliterator makeForSplit(int i, int i2, boolean z) {
            return new KeySpliterator(i, i2, z, true);
        }
    }

    private final class KeySet extends AbstractObjectSet<K> {
        private KeySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<K> spliterator() {
            return new KeySpliterator();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            if (Object2LongOpenHashMap.this.containsNullKey) {
                consumer.accept(Object2LongOpenHashMap.this.key[Object2LongOpenHashMap.this.n]);
            }
            int i = Object2LongOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                Manifest manifest = Object2LongOpenHashMap.this.key[i2];
                if (manifest != null) {
                    consumer.accept(manifest);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2LongOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Object2LongOpenHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            int i = Object2LongOpenHashMap.this.size;
            Object2LongOpenHashMap.this.removeLong(obj);
            return Object2LongOpenHashMap.this.size != i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2LongOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends Object2LongOpenHashMap<K>.MapIterator<LongConsumer> implements LongIterator {
        @Override // java.util.PrimitiveIterator.OfLong
        public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            super.forEachRemaining(longConsumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapIterator
        public final void acceptOnIndex(LongConsumer longConsumer, int i) {
            longConsumer.accept(Object2LongOpenHashMap.this.value[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
        public long nextLong() {
            return Object2LongOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends Object2LongOpenHashMap<K>.MapSpliterator<LongConsumer, Object2LongOpenHashMap<K>.ValueSpliterator> implements LongSpliterator {
        @Override // java.util.Spliterator.OfLong
        public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
            super.forEachRemaining(longConsumer);
        }

        @Override // java.util.Spliterator.OfLong
        public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
            return super.tryAdvance(longConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator trySplit() {
            return (Spliterator) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ LongSpliterator trySplit() {
            return (LongSpliterator) super.trySplit();
        }

        ValueSpliterator() {
            super();
        }

        ValueSpliterator(int i, int i2, boolean z, boolean z2) {
            super(i, i2, z, z2);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final void acceptOnIndex(LongConsumer longConsumer, int i) {
            longConsumer.accept(Object2LongOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.MapSpliterator
        public final Object2LongOpenHashMap<K>.ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap, java.util.Map
    public Collection<Long> values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap.1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
                public LongIterator iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
                public LongSpliterator spliterator() {
                    return new ValueSpliterator();
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
                public void forEach(LongConsumer longConsumer) {
                    if (Object2LongOpenHashMap.this.containsNullKey) {
                        longConsumer.accept(Object2LongOpenHashMap.this.value[Object2LongOpenHashMap.this.n]);
                    }
                    int i = Object2LongOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Object2LongOpenHashMap.this.key[i2] != null) {
                            longConsumer.accept(Object2LongOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Object2LongOpenHashMap.this.size;
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
                public boolean contains(long j) {
                    return Object2LongOpenHashMap.this.containsValue(j);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Object2LongOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    protected void rehash(int i) {
        K k;
        K[] kArr = this.key;
        long[] jArr = this.value;
        int i2 = i - 1;
        int i3 = i + 1;
        K[] kArr2 = (K[]) new Object[i3];
        long[] jArr2 = new long[i3];
        int i4 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i5 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i4--;
                    k = kArr[i4];
                } while (k == null);
                int iMix = HashCommon.mix(k.hashCode()) & i2;
                if (kArr2[iMix] != null) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (kArr2[iMix] != null);
                }
                kArr2[iMix] = kArr[i4];
                jArr2[iMix] = jArr[i4];
                iRealSize = i5;
            } else {
                jArr2[i] = jArr[this.n];
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = kArr2;
                this.value = jArr2;
                return;
            }
        }
    }

    public Object2LongOpenHashMap<K> clone() {
        try {
            Object2LongOpenHashMap<K> object2LongOpenHashMap = (Object2LongOpenHashMap) super.clone();
            object2LongOpenHashMap.keys = null;
            object2LongOpenHashMap.values = null;
            object2LongOpenHashMap.entries = null;
            object2LongOpenHashMap.containsNullKey = this.containsNullKey;
            object2LongOpenHashMap.key = (K[]) ((Object[]) this.key.clone());
            object2LongOpenHashMap.value = (long[]) this.value.clone();
            return object2LongOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2LongMap, java.util.Map
    public int hashCode() {
        K k;
        int iRealSize = realSize();
        int i = 0;
        int i2 = 0;
        int iLong2int = 0;
        while (true) {
            int i3 = iRealSize - 1;
            if (iRealSize == 0) {
                break;
            }
            while (true) {
                k = this.key[i];
                if (k != null) {
                    break;
                }
                i++;
            }
            if (this != k) {
                iLong2int = k.hashCode();
            }
            iLong2int ^= HashCommon.long2int(this.value[i]);
            i2 += iLong2int;
            i++;
            iRealSize = i3;
        }
        return this.containsNullKey ? i2 + HashCommon.long2int(this.value[this.n]) : i2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] kArr = this.key;
        long[] jArr = this.value;
        EntryIterator entryIterator = new EntryIterator();
        objectOutputStream.defaultWriteObject();
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            int iNextEntry = entryIterator.nextEntry();
            objectOutputStream.writeObject(kArr[iNextEntry]);
            objectOutputStream.writeLong(jArr[iNextEntry]);
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
        K[] kArr = (K[]) new Object[i2 + 1];
        this.key = kArr;
        long[] jArr = new long[i2 + 1];
        this.value = jArr;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            Object object = objectInputStream.readObject();
            long j = objectInputStream.readLong();
            if (object == null) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = HashCommon.mix(object.hashCode());
                int i5 = this.mask;
                while (true) {
                    i = iMix & i5;
                    if (kArr[i] != 0) {
                        iMix = i + 1;
                        i5 = this.mask;
                    }
                }
            }
            kArr[i] = object;
            jArr[i] = j;
            i3 = i4;
        }
    }
}

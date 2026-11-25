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
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public class Reference2IntOpenHashMap<K> extends AbstractReference2IntMap<K> implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Reference2IntMap.FastEntrySet<K> entries;
    protected final float f;
    protected transient K[] key;
    protected transient ReferenceSet<K> keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient int[] value;
    protected transient IntCollection values;

    public Reference2IntOpenHashMap(int i, float f) {
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
        this.value = new int[i2 + 1];
    }

    public Reference2IntOpenHashMap(int i) {
        this(i, 0.75f);
    }

    public Reference2IntOpenHashMap() {
        this(16, 0.75f);
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
    public int removeEntry(int i) {
        int i2 = this.value[i];
        this.size--;
        shiftKeys(i);
        int i3 = this.n;
        if (i3 > this.minN && this.size < this.maxFill / 4 && i3 > 16) {
            rehash(i3 / 2);
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int removeNullEntry() {
        this.containsNullKey = false;
        K[] kArr = this.key;
        int i = this.n;
        kArr[i] = null;
        int i2 = this.value[i];
        int i3 = this.size - 1;
        this.size = i3;
        if (i > this.minN && i3 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return i2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, java.util.Map
    public void putAll(Map<? extends K, ? extends Integer> map) {
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
        int iMix = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K k3 = kArr[iMix];
        if (k3 != null) {
            if (k == k3) {
                return iMix;
            }
            do {
                iMix = (iMix + 1) & this.mask;
                k2 = kArr[iMix];
                if (k2 == null) {
                }
            } while (k != k2);
            return iMix;
        }
        return -(iMix + 1);
    }

    private void insert(int i, K k, int i2) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = k;
        this.value[i] = i2;
        int i3 = this.size;
        this.size = i3 + 1;
        if (i3 >= this.maxFill) {
            rehash(HashCommon.arraySize(i3 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunction
    public int put(K k, int i) {
        int iFind = find(k);
        if (iFind < 0) {
            insert((-iFind) - 1, k, i);
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int i2 = iArr[iFind];
        iArr[iFind] = i;
        return i2;
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
                int iMix = HashCommon.mix(System.identityHashCode(k));
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
            int[] iArr = this.value;
            iArr[i] = iArr[i2];
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunction
    public int removeInt(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey ? removeNullEntry() : this.defRetValue;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(System.identityHashCode(obj)) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (obj == k2) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return this.defRetValue;
            }
        } while (obj != k);
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunction
    public int getInt(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(System.identityHashCode(obj)) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (obj == k2) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return this.defRetValue;
            }
        } while (obj != k);
        return this.value[iMix];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public boolean containsKey(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNullKey;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(System.identityHashCode(obj)) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return false;
        }
        if (obj == k2) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return false;
            }
        } while (obj != k);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap
    public boolean containsValue(int i) {
        int[] iArr = this.value;
        K[] kArr = this.key;
        if (this.containsNullKey && iArr[this.n] == i) {
            return true;
        }
        int i2 = this.n;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (kArr[i3] != null && iArr[i3] == i) {
                return true;
            }
            i2 = i3;
        }
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Reference2IntMap.Entry<K>, Map.Entry<K, Integer> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Reference2IntOpenHashMap.this.key[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry
        public int getIntValue() {
            return Reference2IntOpenHashMap.this.value[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry
        public int setValue(int i) {
            int i2 = Reference2IntOpenHashMap.this.value[this.index];
            Reference2IntOpenHashMap.this.value[this.index] = i;
            return i2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Reference2IntOpenHashMap.this.value[this.index]);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer setValue(Integer num) {
            return Integer.valueOf(setValue(num.intValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Reference2IntOpenHashMap.this.key[this.index] == entry.getKey() && Reference2IntOpenHashMap.this.value[this.index] == ((Integer) entry.getValue()).intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return System.identityHashCode(Reference2IntOpenHashMap.this.key[this.index]) ^ Reference2IntOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Reference2IntOpenHashMap.this.key[this.index] + "=>" + Reference2IntOpenHashMap.this.value[this.index];
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int c;
        int last;
        boolean mustReturnNullKey;
        int pos;
        ReferenceArrayList<K> wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            this.pos = Reference2IntOpenHashMap.this.n;
            this.last = -1;
            this.c = Reference2IntOpenHashMap.this.size;
            this.mustReturnNullKey = Reference2IntOpenHashMap.this.containsNullKey;
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
                int i2 = Reference2IntOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i) - 1);
                    int iMix = HashCommon.mix(System.identityHashCode(k));
                    int i3 = Reference2IntOpenHashMap.this.mask;
                    while (true) {
                        int i4 = iMix & i3;
                        if (k == kArr[i4]) {
                            return i4;
                        }
                        iMix = i4 + 1;
                        i3 = Reference2IntOpenHashMap.this.mask;
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
                int i2 = Reference2IntOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i3) - 1);
                    int iMix = HashCommon.mix(System.identityHashCode(k));
                    int i4 = Reference2IntOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i4;
                        if (k == kArr[i]) {
                            break;
                        }
                        iMix = i + 1;
                        i4 = Reference2IntOpenHashMap.this.mask;
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
            K[] kArr = Reference2IntOpenHashMap.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = Reference2IntOpenHashMap.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    k = kArr[i2];
                    if (k == null) {
                        kArr[i] = null;
                        return;
                    }
                    int iMix = HashCommon.mix(System.identityHashCode(k)) & Reference2IntOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Reference2IntOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Reference2IntOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new ReferenceArrayList<>(2);
                    }
                    this.wrapped.add(kArr[i2]);
                }
                kArr[i] = k;
                Reference2IntOpenHashMap.this.value[i] = Reference2IntOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Reference2IntOpenHashMap.this.n) {
                Reference2IntOpenHashMap.this.containsNullKey = false;
                Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Reference2IntOpenHashMap.this.removeInt(this.wrapped.set((-i2) - 1, null));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Reference2IntOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends Reference2IntOpenHashMap<K>.MapIterator<Consumer<? super Reference2IntMap.Entry<K>>> implements ObjectIterator<Reference2IntMap.Entry<K>> {
        private Reference2IntOpenHashMap<K>.MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Reference2IntOpenHashMap<K>.MapEntry next() {
            Reference2IntOpenHashMap<K>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Reference2IntMap.Entry<K>> consumer, int i) {
            Reference2IntOpenHashMap<K>.MapEntry mapEntry = new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends Reference2IntOpenHashMap<K>.MapIterator<Consumer<? super Reference2IntMap.Entry<K>>> implements ObjectIterator<Reference2IntMap.Entry<K>> {
        private final Reference2IntOpenHashMap<K>.MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Reference2IntOpenHashMap<K>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Reference2IntMap.Entry<K>> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends Reference2IntOpenHashMap<K>.MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Reference2IntOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Reference2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Reference2IntOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Reference2IntOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Reference2IntOpenHashMap.this.n);
                return true;
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
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
                acceptOnIndex(consumertype, Reference2IntOpenHashMap.this.n);
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
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
                return Math.min(Reference2IntOpenHashMap.this.size - this.c, ((long) ((((double) Reference2IntOpenHashMap.this.realSize()) / ((double) Reference2IntOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Reference2IntOpenHashMap.this.size - this.c;
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

    private final class EntrySpliterator extends Reference2IntOpenHashMap<K>.MapSpliterator<Consumer<? super Reference2IntMap.Entry<K>>, Reference2IntOpenHashMap<K>.EntrySpliterator> implements ObjectSpliterator<Reference2IntMap.Entry<K>> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Reference2IntMap.Entry<K>> consumer, int i) {
            consumer.accept(new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final Reference2IntOpenHashMap<K>.EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Reference2IntMap.Entry<K>> implements Reference2IntMap.FastEntrySet<K> {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.FastEntrySet
        public ObjectIterator<Reference2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Reference2IntMap.Entry<K>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            K k;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            Object key = entry.getKey();
            int iIntValue = ((Integer) entry.getValue()).intValue();
            if (key == null) {
                return Reference2IntOpenHashMap.this.containsNullKey && Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n] == iIntValue;
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
            int iMix = HashCommon.mix(System.identityHashCode(key)) & Reference2IntOpenHashMap.this.mask;
            K k2 = kArr[iMix];
            if (k2 == null) {
                return false;
            }
            if (key == k2) {
                return Reference2IntOpenHashMap.this.value[iMix] == iIntValue;
            }
            do {
                iMix = (iMix + 1) & Reference2IntOpenHashMap.this.mask;
                k = kArr[iMix];
                if (k == null) {
                    return false;
                }
            } while (key != k);
            return Reference2IntOpenHashMap.this.value[iMix] == iIntValue;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            Object key = entry.getKey();
            int iIntValue = ((Integer) entry.getValue()).intValue();
            if (key == null) {
                if (!Reference2IntOpenHashMap.this.containsNullKey || Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n] != iIntValue) {
                    return false;
                }
                Reference2IntOpenHashMap.this.removeNullEntry();
                return true;
            }
            K[] kArr = Reference2IntOpenHashMap.this.key;
            int iMix = HashCommon.mix(System.identityHashCode(key)) & Reference2IntOpenHashMap.this.mask;
            K k = kArr[iMix];
            if (k == null) {
                return false;
            }
            if (k == key) {
                if (Reference2IntOpenHashMap.this.value[iMix] != iIntValue) {
                    return false;
                }
                Reference2IntOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Reference2IntOpenHashMap.this.mask;
                K k2 = kArr[iMix];
                if (k2 == null) {
                    return false;
                }
                if (k2 == key && Reference2IntOpenHashMap.this.value[iMix] == iIntValue) {
                    Reference2IntOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Reference2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Reference2IntOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
            if (Reference2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractReference2IntMap.BasicEntry(Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n], Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n]));
            }
            int i = Reference2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Reference2IntOpenHashMap.this.key[i2] != null) {
                    consumer.accept(new AbstractReference2IntMap.BasicEntry(Reference2IntOpenHashMap.this.key[i2], Reference2IntOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
            AbstractReference2IntMap.BasicEntry basicEntry = new AbstractReference2IntMap.BasicEntry();
            if (Reference2IntOpenHashMap.this.containsNullKey) {
                basicEntry.key = Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n];
                basicEntry.value = Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Reference2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Reference2IntOpenHashMap.this.key[i2] != null) {
                    basicEntry.key = Reference2IntOpenHashMap.this.key[i2];
                    basicEntry.value = Reference2IntOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap
    public Reference2IntMap.FastEntrySet<K> reference2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends Reference2IntOpenHashMap<K>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Reference2IntOpenHashMap.this.key[i]);
        }

        @Override // java.util.Iterator
        public K next() {
            return Reference2IntOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends Reference2IntOpenHashMap<K>.MapSpliterator<Consumer<? super K>, Reference2IntOpenHashMap<K>.KeySpliterator> implements ObjectSpliterator<K> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Reference2IntOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final Reference2IntOpenHashMap<K>.KeySpliterator makeForSplit(int i, int i2, boolean z) {
            return new KeySpliterator(i, i2, z, true);
        }
    }

    private final class KeySet extends AbstractReferenceSet<K> {
        private KeySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
        public ObjectIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
        public ObjectSpliterator<K> spliterator() {
            return new KeySpliterator();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            if (Reference2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(Reference2IntOpenHashMap.this.key[Reference2IntOpenHashMap.this.n]);
            }
            int i = Reference2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                Manifest manifest = Reference2IntOpenHashMap.this.key[i2];
                if (manifest != null) {
                    consumer.accept(manifest);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Reference2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Reference2IntOpenHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            int i = Reference2IntOpenHashMap.this.size;
            Reference2IntOpenHashMap.this.removeInt(obj);
            return Reference2IntOpenHashMap.this.size != i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Reference2IntOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
    public ReferenceSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends Reference2IntOpenHashMap<K>.MapIterator<IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(IntConsumer intConsumer, int i) {
            intConsumer.accept(Reference2IntOpenHashMap.this.value[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Reference2IntOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends Reference2IntOpenHashMap<K>.MapSpliterator<IntConsumer, Reference2IntOpenHashMap<K>.ValueSpliterator> implements IntSpliterator {
        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        @Override // java.util.Spliterator.OfInt
        public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(IntConsumer intConsumer, int i) {
            intConsumer.accept(Reference2IntOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.MapSpliterator
        public final Reference2IntOpenHashMap<K>.ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntMap, java.util.Map
    public Collection<Integer> values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap.1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
                public IntIterator iterator() {
                    return new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
                public IntSpliterator spliterator() {
                    return new ValueSpliterator();
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
                public void forEach(IntConsumer intConsumer) {
                    if (Reference2IntOpenHashMap.this.containsNullKey) {
                        intConsumer.accept(Reference2IntOpenHashMap.this.value[Reference2IntOpenHashMap.this.n]);
                    }
                    int i = Reference2IntOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Reference2IntOpenHashMap.this.key[i2] != null) {
                            intConsumer.accept(Reference2IntOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Reference2IntOpenHashMap.this.size;
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
                public boolean contains(int i) {
                    return Reference2IntOpenHashMap.this.containsValue(i);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Reference2IntOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    protected void rehash(int i) {
        K k;
        K[] kArr = this.key;
        int[] iArr = this.value;
        int i2 = i - 1;
        int i3 = i + 1;
        K[] kArr2 = (K[]) new Object[i3];
        int[] iArr2 = new int[i3];
        int i4 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i5 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i4--;
                    k = kArr[i4];
                } while (k == null);
                int iMix = HashCommon.mix(System.identityHashCode(k)) & i2;
                if (kArr2[iMix] != null) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (kArr2[iMix] != null);
                }
                kArr2[iMix] = kArr[i4];
                iArr2[iMix] = iArr[i4];
                iRealSize = i5;
            } else {
                iArr2[i] = iArr[this.n];
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = kArr2;
                this.value = iArr2;
                return;
            }
        }
    }

    public Reference2IntOpenHashMap<K> clone() {
        try {
            Reference2IntOpenHashMap<K> reference2IntOpenHashMap = (Reference2IntOpenHashMap) super.clone();
            reference2IntOpenHashMap.keys = null;
            reference2IntOpenHashMap.values = null;
            reference2IntOpenHashMap.entries = null;
            reference2IntOpenHashMap.containsNullKey = this.containsNullKey;
            reference2IntOpenHashMap.key = (K[]) ((Object[]) this.key.clone());
            reference2IntOpenHashMap.value = (int[]) this.value.clone();
            return reference2IntOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2IntMap, java.util.Map
    public int hashCode() {
        K k;
        int iRealSize = realSize();
        int i = 0;
        int i2 = 0;
        int iIdentityHashCode = 0;
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
                iIdentityHashCode = System.identityHashCode(k);
            }
            iIdentityHashCode ^= this.value[i];
            i2 += iIdentityHashCode;
            i++;
            iRealSize = i3;
        }
        return this.containsNullKey ? i2 + this.value[this.n] : i2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] kArr = this.key;
        int[] iArr = this.value;
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
            objectOutputStream.writeInt(iArr[iNextEntry]);
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
        int[] iArr = new int[i2 + 1];
        this.value = iArr;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            Object object = objectInputStream.readObject();
            int i5 = objectInputStream.readInt();
            if (object == null) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = HashCommon.mix(System.identityHashCode(object));
                int i6 = this.mask;
                while (true) {
                    i = iMix & i6;
                    if (kArr[i] != 0) {
                        iMix = i + 1;
                        i6 = this.mask;
                    }
                }
            }
            kArr[i] = object;
            iArr[i] = i5;
            i3 = i4;
        }
    }
}

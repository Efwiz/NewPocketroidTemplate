package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import android.Manifest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public class Reference2ObjectOpenHashMap<K, V> extends AbstractReference2ObjectMap<K, V> implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Reference2ObjectMap.FastEntrySet<K, V> entries;
    protected final float f;
    protected transient K[] key;
    protected transient ReferenceSet<K> keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient V[] value;
    protected transient ObjectCollection<V> values;

    public Reference2ObjectOpenHashMap(int i, float f) {
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
        this.value = (V[]) new Object[i2 + 1];
    }

    public Reference2ObjectOpenHashMap() {
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
        K[] kArr = this.key;
        int i = this.n;
        kArr[i] = null;
        V[] vArr = this.value;
        V v = vArr[i];
        vArr[i] = null;
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return v;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
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

    private void insert(int i, K k, V v) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = k;
        this.value[i] = v;
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(i2 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunction, java.util.Map
    public V put(K k, V v) {
        int iFind = find(k);
        if (iFind < 0) {
            insert((-iFind) - 1, k, v);
            return this.defRetValue;
        }
        V[] vArr = this.value;
        V v2 = vArr[iFind];
        vArr[iFind] = v;
        return v2;
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
                    this.value[i] = null;
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
            V[] vArr = this.value;
            vArr[i] = vArr[i2];
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectFunction, java.util.Map
    public V remove(Object obj) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public V get(Object obj) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, java.util.Map
    public boolean containsValue(Object obj) {
        V[] vArr = this.value;
        K[] kArr = this.key;
        if (this.containsNullKey && Objects.equals(vArr[this.n], obj)) {
            return true;
        }
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (kArr[i2] != null && Objects.equals(vArr[i2], obj)) {
                return true;
            }
            i = i2;
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
        Arrays.fill(this.value, (Object) null);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Reference2ObjectMap.Entry<K, V>, Map.Entry<K, V> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Reference2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Reference2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = Reference2ObjectOpenHashMap.this.value[this.index];
            Reference2ObjectOpenHashMap.this.value[this.index] = v;
            return v2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Reference2ObjectOpenHashMap.this.key[this.index] == entry.getKey() && Objects.equals(Reference2ObjectOpenHashMap.this.value[this.index], entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return System.identityHashCode(Reference2ObjectOpenHashMap.this.key[this.index]) ^ (Reference2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Reference2ObjectOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Reference2ObjectOpenHashMap.this.key[this.index] + "=>" + Reference2ObjectOpenHashMap.this.value[this.index];
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
            this.pos = Reference2ObjectOpenHashMap.this.n;
            this.last = -1;
            this.c = Reference2ObjectOpenHashMap.this.size;
            this.mustReturnNullKey = Reference2ObjectOpenHashMap.this.containsNullKey;
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
                int i2 = Reference2ObjectOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i) - 1);
                    int iMix = HashCommon.mix(System.identityHashCode(k));
                    int i3 = Reference2ObjectOpenHashMap.this.mask;
                    while (true) {
                        int i4 = iMix & i3;
                        if (k == kArr[i4]) {
                            return i4;
                        }
                        iMix = i4 + 1;
                        i3 = Reference2ObjectOpenHashMap.this.mask;
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
                int i2 = Reference2ObjectOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    K k = this.wrapped.get((-i3) - 1);
                    int iMix = HashCommon.mix(System.identityHashCode(k));
                    int i4 = Reference2ObjectOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i4;
                        if (k == kArr[i]) {
                            break;
                        }
                        iMix = i + 1;
                        i4 = Reference2ObjectOpenHashMap.this.mask;
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
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = Reference2ObjectOpenHashMap.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    k = kArr[i2];
                    if (k == null) {
                        kArr[i] = null;
                        Reference2ObjectOpenHashMap.this.value[i] = null;
                        return;
                    }
                    int iMix = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Reference2ObjectOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Reference2ObjectOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new ReferenceArrayList<>(2);
                    }
                    this.wrapped.add(kArr[i2]);
                }
                kArr[i] = k;
                Reference2ObjectOpenHashMap.this.value[i] = Reference2ObjectOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Reference2ObjectOpenHashMap.this.n) {
                Reference2ObjectOpenHashMap.this.containsNullKey = false;
                Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n] = null;
                Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Reference2ObjectOpenHashMap.this.remove(this.wrapped.set((-i2) - 1, null));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Reference2ObjectOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>> implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> {
        private Reference2ObjectOpenHashMap<K, V>.MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
            Reference2ObjectOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer, int i) {
            Reference2ObjectOpenHashMap<K, V>.MapEntry mapEntry = new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>> implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> {
        private final Reference2ObjectOpenHashMap<K, V>.MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Reference2ObjectOpenHashMap<K, V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends Reference2ObjectOpenHashMap<K, V>.MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Reference2ObjectOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Reference2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Reference2ObjectOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Reference2ObjectOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Reference2ObjectOpenHashMap.this.n);
                return true;
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
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
                acceptOnIndex(consumertype, Reference2ObjectOpenHashMap.this.n);
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
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
                return Math.min(Reference2ObjectOpenHashMap.this.size - this.c, ((long) ((((double) Reference2ObjectOpenHashMap.this.realSize()) / ((double) Reference2ObjectOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Reference2ObjectOpenHashMap.this.size - this.c;
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

    private final class EntrySpliterator extends Reference2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>, Reference2ObjectOpenHashMap<K, V>.EntrySpliterator> implements ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer, int i) {
            consumer.accept(new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final Reference2ObjectOpenHashMap<K, V>.EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> implements Reference2ObjectMap.FastEntrySet<K, V> {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet
        public ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            K k;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key == null) {
                return Reference2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], value);
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
            int iMix = HashCommon.mix(System.identityHashCode(key)) & Reference2ObjectOpenHashMap.this.mask;
            K k2 = kArr[iMix];
            if (k2 == null) {
                return false;
            }
            if (key == k2) {
                return Objects.equals(Reference2ObjectOpenHashMap.this.value[iMix], value);
            }
            do {
                iMix = (iMix + 1) & Reference2ObjectOpenHashMap.this.mask;
                k = kArr[iMix];
                if (k == null) {
                    return false;
                }
            } while (key != k);
            return Objects.equals(Reference2ObjectOpenHashMap.this.value[iMix], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key == null) {
                if (!Reference2ObjectOpenHashMap.this.containsNullKey || !Objects.equals(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n], value)) {
                    return false;
                }
                Reference2ObjectOpenHashMap.this.removeNullEntry();
                return true;
            }
            K[] kArr = Reference2ObjectOpenHashMap.this.key;
            int iMix = HashCommon.mix(System.identityHashCode(key)) & Reference2ObjectOpenHashMap.this.mask;
            K k = kArr[iMix];
            if (k == null) {
                return false;
            }
            if (k == key) {
                if (!Objects.equals(Reference2ObjectOpenHashMap.this.value[iMix], value)) {
                    return false;
                }
                Reference2ObjectOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Reference2ObjectOpenHashMap.this.mask;
                K k2 = kArr[iMix];
                if (k2 == null) {
                    return false;
                }
                if (k2 == key && Objects.equals(Reference2ObjectOpenHashMap.this.value[iMix], value)) {
                    Reference2ObjectOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Reference2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Reference2ObjectOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
            if (Reference2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractReference2ObjectMap.BasicEntry(Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n], Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n]));
            }
            int i = Reference2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Reference2ObjectOpenHashMap.this.key[i2] != null) {
                    consumer.accept(new AbstractReference2ObjectMap.BasicEntry(Reference2ObjectOpenHashMap.this.key[i2], Reference2ObjectOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
            AbstractReference2ObjectMap.BasicEntry basicEntry = new AbstractReference2ObjectMap.BasicEntry();
            if (Reference2ObjectOpenHashMap.this.containsNullKey) {
                basicEntry.key = Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n];
                basicEntry.value = Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Reference2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Reference2ObjectOpenHashMap.this.key[i2] != null) {
                    basicEntry.key = Reference2ObjectOpenHashMap.this.key[i2];
                    basicEntry.value = Reference2ObjectOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap
    public Reference2ObjectMap.FastEntrySet<K, V> reference2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Reference2ObjectOpenHashMap.this.key[i]);
        }

        @Override // java.util.Iterator
        public K next() {
            return Reference2ObjectOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends Reference2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super K>, Reference2ObjectOpenHashMap<K, V>.KeySpliterator> implements ObjectSpliterator<K> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super K> consumer, int i) {
            consumer.accept(Reference2ObjectOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final Reference2ObjectOpenHashMap<K, V>.KeySpliterator makeForSplit(int i, int i2, boolean z) {
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
            if (Reference2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(Reference2ObjectOpenHashMap.this.key[Reference2ObjectOpenHashMap.this.n]);
            }
            int i = Reference2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                Manifest manifest = Reference2ObjectOpenHashMap.this.key[i2];
                if (manifest != null) {
                    consumer.accept(manifest);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Reference2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Reference2ObjectOpenHashMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            int i = Reference2ObjectOpenHashMap.this.size;
            Reference2ObjectOpenHashMap.this.remove(obj);
            return Reference2ObjectOpenHashMap.this.size != i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Reference2ObjectOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    public ReferenceSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends Reference2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Reference2ObjectOpenHashMap.this.value[i]);
        }

        @Override // java.util.Iterator
        public V next() {
            return Reference2ObjectOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends Reference2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super V>, Reference2ObjectOpenHashMap<K, V>.ValueSpliterator> implements ObjectSpliterator<V> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Reference2ObjectOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.MapSpliterator
        public final Reference2ObjectOpenHashMap<K, V>.ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap.1
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
                    if (Reference2ObjectOpenHashMap.this.containsNullKey) {
                        consumer.accept(Reference2ObjectOpenHashMap.this.value[Reference2ObjectOpenHashMap.this.n]);
                    }
                    int i = Reference2ObjectOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Reference2ObjectOpenHashMap.this.key[i2] != null) {
                            consumer.accept(Reference2ObjectOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Reference2ObjectOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return Reference2ObjectOpenHashMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Reference2ObjectOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    protected void rehash(int i) {
        K k;
        K[] kArr = this.key;
        V[] vArr = this.value;
        int i2 = i - 1;
        int i3 = i + 1;
        K[] kArr2 = (K[]) new Object[i3];
        V[] vArr2 = (V[]) new Object[i3];
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
                vArr2[iMix] = vArr[i4];
                iRealSize = i5;
            } else {
                vArr2[i] = vArr[this.n];
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = kArr2;
                this.value = vArr2;
                return;
            }
        }
    }

    public Reference2ObjectOpenHashMap<K, V> clone() {
        try {
            Reference2ObjectOpenHashMap<K, V> reference2ObjectOpenHashMap = (Reference2ObjectOpenHashMap) super.clone();
            reference2ObjectOpenHashMap.keys = null;
            reference2ObjectOpenHashMap.values = null;
            reference2ObjectOpenHashMap.entries = null;
            reference2ObjectOpenHashMap.containsNullKey = this.containsNullKey;
            reference2ObjectOpenHashMap.key = (K[]) ((Object[]) this.key.clone());
            reference2ObjectOpenHashMap.value = (V[]) ((Object[]) this.value.clone());
            return reference2ObjectOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap, java.util.Map
    public int hashCode() {
        K k;
        int iRealSize = realSize();
        int i = 0;
        int i2 = 0;
        int iHashCode = 0;
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
                iHashCode = System.identityHashCode(k);
            }
            V v = this.value[i];
            if (this != v) {
                iHashCode = (v == null ? 0 : v.hashCode()) ^ iHashCode;
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
        K[] kArr = this.key;
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
            objectOutputStream.writeObject(kArr[iNextEntry]);
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
        K[] kArr = (K[]) new Object[i2 + 1];
        this.key = kArr;
        V[] vArr = (V[]) new Object[i2 + 1];
        this.value = vArr;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            if (object == null) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = HashCommon.mix(System.identityHashCode(object));
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
            vArr[i] = object2;
            i3 = i4;
        }
    }
}

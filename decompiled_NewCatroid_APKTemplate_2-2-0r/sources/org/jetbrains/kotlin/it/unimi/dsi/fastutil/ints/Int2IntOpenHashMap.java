package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.google.android.gms.cast.MediaError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public class Int2IntOpenHashMap extends AbstractInt2IntMap implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Int2IntMap.FastEntrySet entries;
    protected final float f;
    protected transient int[] key;
    protected transient IntSet keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient int[] value;
    protected transient IntCollection values;

    public Int2IntOpenHashMap(int i, float f) {
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
        this.value = new int[i2 + 1];
    }

    public Int2IntOpenHashMap(int i) {
        this(i, 0.75f);
    }

    public Int2IntOpenHashMap() {
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
        int[] iArr = this.value;
        int i = this.n;
        int i2 = iArr[i];
        int i3 = this.size - 1;
        this.size = i3;
        if (i > this.minN && i3 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return i2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends Integer> map) {
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

    private void insert(int i, int i2, int i3) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = i2;
        this.value[i] = i3;
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(i4 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
    public int put(int i, int i2) {
        int iFind = find(i);
        if (iFind < 0) {
            insert((-iFind) - 1, i, i2);
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int i3 = iArr[iFind];
        iArr[iFind] = i2;
        return i3;
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
            int[] iArr2 = this.value;
            iArr2[i] = iArr2[i3];
            i = i3;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
    public int remove(int i) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
    public int get(int i) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
    public boolean containsValue(int i) {
        int[] iArr = this.value;
        int[] iArr2 = this.key;
        if (this.containsNullKey && iArr[this.n] == i) {
            return true;
        }
        int i2 = this.n;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (iArr2[i3] != 0 && iArr[i3] == i) {
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
        Arrays.fill(this.key, 0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Int2IntMap.Entry, Map.Entry<Integer, Integer> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int getIntKey() {
            return Int2IntOpenHashMap.this.key[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int getIntValue() {
            return Int2IntOpenHashMap.this.value[this.index];
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int setValue(int i) {
            int i2 = Int2IntOpenHashMap.this.value[this.index];
            Int2IntOpenHashMap.this.value[this.index] = i;
            return i2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2IntOpenHashMap.this.key[this.index]);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Int2IntOpenHashMap.this.value[this.index]);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
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
            return Int2IntOpenHashMap.this.key[this.index] == ((Integer) entry.getKey()).intValue() && Int2IntOpenHashMap.this.value[this.index] == ((Integer) entry.getValue()).intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2IntOpenHashMap.this.key[this.index] ^ Int2IntOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Int2IntOpenHashMap.this.key[this.index] + "=>" + Int2IntOpenHashMap.this.value[this.index];
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
            this.pos = Int2IntOpenHashMap.this.n;
            this.last = -1;
            this.c = Int2IntOpenHashMap.this.size;
            this.mustReturnNullKey = Int2IntOpenHashMap.this.containsNullKey;
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
                int i2 = Int2IntOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    int i3 = this.wrapped.getInt((-i) - 1);
                    int iMix = HashCommon.mix(i3);
                    int i4 = Int2IntOpenHashMap.this.mask;
                    while (true) {
                        int i5 = iMix & i4;
                        if (i3 == iArr[i5]) {
                            return i5;
                        }
                        iMix = i5 + 1;
                        i4 = Int2IntOpenHashMap.this.mask;
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
                int i2 = Int2IntOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    int i4 = this.wrapped.getInt((-i3) - 1);
                    int iMix = HashCommon.mix(i4);
                    int i5 = Int2IntOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i5;
                        if (i4 == iArr[i]) {
                            break;
                        }
                        iMix = i + 1;
                        i5 = Int2IntOpenHashMap.this.mask;
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
            int[] iArr = Int2IntOpenHashMap.this.key;
            while (true) {
                int i4 = i + 1;
                int i5 = Int2IntOpenHashMap.this.mask;
                while (true) {
                    i2 = i4 & i5;
                    i3 = iArr[i2];
                    if (i3 == 0) {
                        iArr[i] = 0;
                        return;
                    }
                    int iMix = HashCommon.mix(i3) & Int2IntOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = Int2IntOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i4 = i2 + 1;
                        i5 = Int2IntOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(iArr[i2]);
                }
                iArr[i] = i3;
                Int2IntOpenHashMap.this.value[i] = Int2IntOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Int2IntOpenHashMap.this.n) {
                Int2IntOpenHashMap.this.containsNullKey = false;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Int2IntOpenHashMap.this.remove(this.wrapped.getInt((-i2) - 1));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Int2IntOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectIterator<Int2IntMap.Entry> {
        private MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public MapEntry next() {
            MapEntry mapEntry = Int2IntOpenHashMap.this.new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int i) {
            MapEntry mapEntry = Int2IntOpenHashMap.this.new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectIterator<Int2IntMap.Entry> {
        private final MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = Int2IntOpenHashMap.this.new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Int2IntOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Int2IntOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Int2IntOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Int2IntOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Int2IntOpenHashMap.this.n);
                return true;
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
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
                acceptOnIndex(consumertype, Int2IntOpenHashMap.this.n);
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
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
                return Math.min(Int2IntOpenHashMap.this.size - this.c, ((long) ((((double) Int2IntOpenHashMap.this.realSize()) / ((double) Int2IntOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Int2IntOpenHashMap.this.size - this.c;
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

    private final class EntrySpliterator extends MapSpliterator<Consumer<? super Int2IntMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Int2IntMap.Entry> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int i) {
            consumer.accept(Int2IntOpenHashMap.this.new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return Int2IntOpenHashMap.this.new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.FastEntrySet
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return Int2IntOpenHashMap.this.new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            int i;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            if (iIntValue == 0) {
                return Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n] == iIntValue2;
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
            int iMix = HashCommon.mix(iIntValue) & Int2IntOpenHashMap.this.mask;
            int i2 = iArr[iMix];
            if (i2 == 0) {
                return false;
            }
            if (iIntValue == i2) {
                return Int2IntOpenHashMap.this.value[iMix] == iIntValue2;
            }
            do {
                iMix = (iMix + 1) & Int2IntOpenHashMap.this.mask;
                i = iArr[iMix];
                if (i == 0) {
                    return false;
                }
            } while (iIntValue != i);
            return Int2IntOpenHashMap.this.value[iMix] == iIntValue2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            if (iIntValue == 0) {
                if (!Int2IntOpenHashMap.this.containsNullKey || Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n] != iIntValue2) {
                    return false;
                }
                Int2IntOpenHashMap.this.removeNullEntry();
                return true;
            }
            int[] iArr = Int2IntOpenHashMap.this.key;
            int iMix = HashCommon.mix(iIntValue) & Int2IntOpenHashMap.this.mask;
            int i = iArr[iMix];
            if (i == 0) {
                return false;
            }
            if (i == iIntValue) {
                if (Int2IntOpenHashMap.this.value[iMix] != iIntValue2) {
                    return false;
                }
                Int2IntOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Int2IntOpenHashMap.this.mask;
                int i2 = iArr[iMix];
                if (i2 == 0) {
                    return false;
                }
                if (i2 == iIntValue && Int2IntOpenHashMap.this.value[iMix] == iIntValue2) {
                    Int2IntOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
            if (Int2IntOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n], Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n]));
            }
            int i = Int2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Int2IntOpenHashMap.this.key[i2] != 0) {
                    consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[i2], Int2IntOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
            AbstractInt2IntMap.BasicEntry basicEntry = new AbstractInt2IntMap.BasicEntry();
            if (Int2IntOpenHashMap.this.containsNullKey) {
                basicEntry.key = Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n];
                basicEntry.value = Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Int2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Int2IntOpenHashMap.this.key[i2] != 0) {
                    basicEntry.key = Int2IntOpenHashMap.this.key[i2];
                    basicEntry.value = Int2IntOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2IntOpenHashMap.this.key[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends MapSpliterator<java.util.function.IntConsumer, KeySpliterator> implements IntSpliterator {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2IntOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final KeySpliterator makeForSplit(int i, int i2, boolean z) {
            return Int2IntOpenHashMap.this.new KeySpliterator(i, i2, z, true);
        }
    }

    private final class KeySet extends AbstractIntSet {
        private KeySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntIterator iterator() {
            return Int2IntOpenHashMap.this.new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return Int2IntOpenHashMap.this.new KeySpliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer intConsumer) {
            if (Int2IntOpenHashMap.this.containsNullKey) {
                intConsumer.accept(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n]);
            }
            int i = Int2IntOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                int i3 = Int2IntOpenHashMap.this.key[i2];
                if (i3 != 0) {
                    intConsumer.accept(i3);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntOpenHashMap.this.size;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean contains(int i) {
            return Int2IntOpenHashMap.this.containsKey(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
        public boolean remove(int i) {
            int i2 = Int2IntOpenHashMap.this.size;
            Int2IntOpenHashMap.this.remove(i);
            return Int2IntOpenHashMap.this.size != i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    public Set<Integer> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends MapIterator<java.util.function.IntConsumer> implements IntIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapIterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2IntOpenHashMap.this.value[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends MapSpliterator<java.util.function.IntConsumer, ValueSpliterator> implements IntSpliterator {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final void acceptOnIndex(java.util.function.IntConsumer intConsumer, int i) {
            intConsumer.accept(Int2IntOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.MapSpliterator
        public final ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return Int2IntOpenHashMap.this.new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    public Collection<Integer> values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap.1
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
                public IntIterator iterator() {
                    return Int2IntOpenHashMap.this.new ValueIterator();
                }

                @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
                public IntSpliterator spliterator() {
                    return Int2IntOpenHashMap.this.new ValueSpliterator();
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
                public void forEach(java.util.function.IntConsumer intConsumer) {
                    if (Int2IntOpenHashMap.this.containsNullKey) {
                        intConsumer.accept(Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n]);
                    }
                    int i = Int2IntOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Int2IntOpenHashMap.this.key[i2] != 0) {
                            intConsumer.accept(Int2IntOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2IntOpenHashMap.this.size;
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
                public boolean contains(int i) {
                    return Int2IntOpenHashMap.this.containsValue(i);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2IntOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    protected void rehash(int i) {
        int i2;
        int[] iArr = this.key;
        int[] iArr2 = this.value;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
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
                if (iArr3[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & i3;
                    } while (iArr3[iMix] != 0);
                }
                iArr3[iMix] = iArr[i5];
                iArr4[iMix] = iArr2[i5];
                iRealSize = i6;
            } else {
                iArr4[i] = iArr2[this.n];
                this.n = i;
                this.mask = i3;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = iArr3;
                this.value = iArr4;
                return;
            }
        }
    }

    public Int2IntOpenHashMap clone() {
        try {
            Int2IntOpenHashMap int2IntOpenHashMap = (Int2IntOpenHashMap) super.clone();
            int2IntOpenHashMap.keys = null;
            int2IntOpenHashMap.values = null;
            int2IntOpenHashMap.entries = null;
            int2IntOpenHashMap.containsNullKey = this.containsNullKey;
            int2IntOpenHashMap.key = (int[]) this.key.clone();
            int2IntOpenHashMap.value = (int[]) this.value.clone();
            return int2IntOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public int hashCode() {
        int i;
        int iRealSize = realSize();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = iRealSize - 1;
            if (iRealSize == 0) {
                break;
            }
            while (true) {
                i = this.key[i3];
                if (i == 0) {
                    i3++;
                }
            }
            i2 += i ^ this.value[i3];
            i3++;
            iRealSize = i4;
        }
        return this.containsNullKey ? i2 + this.value[this.n] : i2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int[] iArr = this.key;
        int[] iArr2 = this.value;
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
            objectOutputStream.writeInt(iArr2[iNextEntry]);
            i = i2;
        }
    }

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
        int[] iArr2 = new int[i2 + 1];
        this.value = iArr2;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            int i5 = objectInputStream.readInt();
            int i6 = objectInputStream.readInt();
            if (i5 == 0) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = HashCommon.mix(i5);
                int i7 = this.mask;
                while (true) {
                    i = iMix & i7;
                    if (iArr[i] != 0) {
                        iMix = i + 1;
                        i7 = this.mask;
                    }
                }
            }
            iArr[i] = i5;
            iArr2[i] = i6;
            i3 = i4;
        }
    }
}

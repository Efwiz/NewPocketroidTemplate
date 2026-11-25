package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import com.google.android.gms.cast.MediaError;
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
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator;

/* JADX INFO: loaded from: classes5.dex */
public class Double2ObjectOpenHashMap<V> extends AbstractDouble2ObjectMap<V> implements Hash, Serializable, Cloneable {
    private static final long serialVersionUID = 0;
    protected transient boolean containsNullKey;
    protected transient Double2ObjectMap.FastEntrySet<V> entries;
    protected final float f;
    protected transient double[] key;
    protected transient DoubleSet keys;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;
    protected transient V[] value;
    protected transient ObjectCollection<V> values;

    public Double2ObjectOpenHashMap(int i, float f) {
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
        this.key = new double[i2 + 1];
        this.value = (V[]) new Object[i2 + 1];
    }

    public Double2ObjectOpenHashMap() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, java.util.Map
    public void putAll(Map<? extends Double, ? extends V> map) {
        if (this.f <= 0.5d) {
            ensureCapacity(map.size());
        } else {
            tryCapacity(size() + map.size());
        }
        super.putAll(map);
    }

    private int find(double d) {
        double d2;
        if (Double.doubleToLongBits(d) == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        double[] dArr = this.key;
        int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(d))) & this.mask;
        double d3 = dArr[iMix];
        if (Double.doubleToLongBits(d3) != 0) {
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
                return iMix;
            }
            do {
                iMix = (iMix + 1) & this.mask;
                d2 = dArr[iMix];
                if (Double.doubleToLongBits(d2) == 0) {
                }
            } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
            return iMix;
        }
        return -(iMix + 1);
    }

    private void insert(int i, double d, V v) {
        if (i == this.n) {
            this.containsNullKey = true;
        }
        this.key[i] = d;
        this.value[i] = v;
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(i2 + 2, this.f));
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public V put(double d, V v) {
        int iFind = find(d);
        if (iFind < 0) {
            insert((-iFind) - 1, d, v);
            return this.defRetValue;
        }
        V[] vArr = this.value;
        V v2 = vArr[iFind];
        vArr[iFind] = v;
        return v2;
    }

    protected final void shiftKeys(int i) {
        double d;
        double[] dArr = this.key;
        while (true) {
            int i2 = (i + 1) & this.mask;
            while (true) {
                d = dArr[i2];
                if (Double.doubleToLongBits(d) == 0) {
                    dArr[i] = 0.0d;
                    this.value[i] = null;
                    return;
                }
                int iMix = (int) HashCommon.mix(Double.doubleToRawLongBits(d));
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
            dArr[i] = d;
            V[] vArr = this.value;
            vArr[i] = vArr[i2];
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public V remove(double d) {
        double d2;
        if (Double.doubleToLongBits(d) == 0) {
            return this.containsNullKey ? removeNullEntry() : this.defRetValue;
        }
        double[] dArr = this.key;
        int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(d))) & this.mask;
        double d3 = dArr[iMix];
        if (Double.doubleToLongBits(d3) == 0) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            d2 = dArr[iMix];
            if (Double.doubleToLongBits(d2) == 0) {
                return this.defRetValue;
            }
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return removeEntry(iMix);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public V get(double d) {
        double d2;
        if (Double.doubleToLongBits(d) == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        double[] dArr = this.key;
        int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(d))) & this.mask;
        double d3 = dArr[iMix];
        if (Double.doubleToLongBits(d3) == 0) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
            return this.value[iMix];
        }
        do {
            iMix = (iMix + 1) & this.mask;
            d2 = dArr[iMix];
            if (Double.doubleToLongBits(d2) == 0) {
                return this.defRetValue;
            }
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.value[iMix];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public boolean containsKey(double d) {
        double d2;
        if (Double.doubleToLongBits(d) == 0) {
            return this.containsNullKey;
        }
        double[] dArr = this.key;
        int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(d))) & this.mask;
        double d3 = dArr[iMix];
        if (Double.doubleToLongBits(d3) == 0) {
            return false;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
            return true;
        }
        do {
            iMix = (iMix + 1) & this.mask;
            d2 = dArr[iMix];
            if (Double.doubleToLongBits(d2) == 0) {
                return false;
            }
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, java.util.Map
    public boolean containsValue(Object obj) {
        V[] vArr = this.value;
        double[] dArr = this.key;
        if (this.containsNullKey && Objects.equals(vArr[this.n], obj)) {
            return true;
        }
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (Double.doubleToLongBits(dArr[i2]) != 0 && Objects.equals(vArr[i2], obj)) {
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
        Arrays.fill(this.key, 0.0d);
        Arrays.fill(this.value, (Object) null);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    public int size() {
        return this.size;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    final class MapEntry implements Double2ObjectMap.Entry<V>, Map.Entry<Double, V> {
        int index;

        MapEntry(int i) {
            this.index = i;
        }

        MapEntry() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry
        public double getDoubleKey() {
            return Double2ObjectOpenHashMap.this.key[this.index];
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return Double2ObjectOpenHashMap.this.value[this.index];
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = Double2ObjectOpenHashMap.this.value[this.index];
            Double2ObjectOpenHashMap.this.value[this.index] = v;
            return v2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry, java.util.Map.Entry
        @Deprecated
        public Double getKey() {
            return Double.valueOf(Double2ObjectOpenHashMap.this.key[this.index]);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double) entry.getKey()).doubleValue()) && Objects.equals(Double2ObjectOpenHashMap.this.value[this.index], entry.getValue());
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return HashCommon.double2int(Double2ObjectOpenHashMap.this.key[this.index]) ^ (Double2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Double2ObjectOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Double2ObjectOpenHashMap.this.key[this.index] + "=>" + Double2ObjectOpenHashMap.this.value[this.index];
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int c;
        int last;
        boolean mustReturnNullKey;
        int pos;
        DoubleArrayList wrapped;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        private MapIterator() {
            this.pos = Double2ObjectOpenHashMap.this.n;
            this.last = -1;
            this.c = Double2ObjectOpenHashMap.this.size;
            this.mustReturnNullKey = Double2ObjectOpenHashMap.this.containsNullKey;
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
                int i2 = Double2ObjectOpenHashMap.this.n;
                this.last = i2;
                return i2;
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    double d = this.wrapped.getDouble((-i) - 1);
                    int iMix = (int) HashCommon.mix(Double.doubleToRawLongBits(d));
                    int i3 = Double2ObjectOpenHashMap.this.mask;
                    while (true) {
                        int i4 = iMix & i3;
                        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(dArr[i4])) {
                            return i4;
                        }
                        iMix = i4 + 1;
                        i3 = Double2ObjectOpenHashMap.this.mask;
                    }
                }
            } while (Double.doubleToLongBits(dArr[i]) == 0);
            int i5 = this.pos;
            this.last = i5;
            return i5;
        }

        public void forEachRemaining(ConsumerType consumertype) {
            int i;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                int i2 = Double2ObjectOpenHashMap.this.n;
                this.last = i2;
                acceptOnIndex(consumertype, i2);
                this.c--;
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            while (this.c != 0) {
                int i3 = this.pos - 1;
                this.pos = i3;
                if (i3 < 0) {
                    this.last = Integer.MIN_VALUE;
                    double d = this.wrapped.getDouble((-i3) - 1);
                    int iMix = (int) HashCommon.mix(Double.doubleToRawLongBits(d));
                    int i4 = Double2ObjectOpenHashMap.this.mask;
                    while (true) {
                        i = iMix & i4;
                        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(dArr[i])) {
                            break;
                        }
                        iMix = i + 1;
                        i4 = Double2ObjectOpenHashMap.this.mask;
                    }
                    acceptOnIndex(consumertype, i);
                    this.c--;
                } else if (Double.doubleToLongBits(dArr[i3]) != 0) {
                    int i5 = this.pos;
                    this.last = i5;
                    acceptOnIndex(consumertype, i5);
                    this.c--;
                }
            }
        }

        private void shiftKeys(int i) {
            int i2;
            double d;
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = Double2ObjectOpenHashMap.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    d = dArr[i2];
                    if (Double.doubleToLongBits(d) == 0) {
                        dArr[i] = 0.0d;
                        Double2ObjectOpenHashMap.this.value[i] = null;
                        return;
                    }
                    int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(d))) & Double2ObjectOpenHashMap.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Double2ObjectOpenHashMap.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = Double2ObjectOpenHashMap.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new DoubleArrayList(2);
                    }
                    this.wrapped.add(dArr[i2]);
                }
                dArr[i] = d;
                Double2ObjectOpenHashMap.this.value[i] = Double2ObjectOpenHashMap.this.value[i2];
                i = i2;
            }
        }

        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == Double2ObjectOpenHashMap.this.n) {
                Double2ObjectOpenHashMap.this.containsNullKey = false;
                Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    Double2ObjectOpenHashMap.this.remove(this.wrapped.getDouble((-i2) - 1));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            Double2ObjectOpenHashMap.this.size--;
            this.last = -1;
        }
    }

    private final class EntryIterator extends Double2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Double2ObjectMap.Entry<V>>> implements ObjectIterator<Double2ObjectMap.Entry<V>> {
        private Double2ObjectOpenHashMap<V>.MapEntry entry;

        private EntryIterator() {
            super();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Double2ObjectOpenHashMap<V>.MapEntry next() {
            Double2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Double2ObjectMap.Entry<V>> consumer, int i) {
            Double2ObjectOpenHashMap<V>.MapEntry mapEntry = new MapEntry(i);
            this.entry = mapEntry;
            consumer.accept(mapEntry);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapIterator, java.util.Iterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private final class FastEntryIterator extends Double2ObjectOpenHashMap<V>.MapIterator<Consumer<? super Double2ObjectMap.Entry<V>>> implements ObjectIterator<Double2ObjectMap.Entry<V>> {
        private final Double2ObjectOpenHashMap<V>.MapEntry entry;

        private FastEntryIterator() {
            super();
            this.entry = new MapEntry();
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override // java.util.Iterator
        public Double2ObjectOpenHashMap<V>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super Double2ObjectMap.Entry<V>> consumer, int i) {
            this.entry.index = i;
            consumer.accept(this.entry);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends Double2ObjectOpenHashMap<V>.MapSpliterator<ConsumerType, SplitType>> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        abstract SplitType makeForSplit(int i, int i2, boolean z);

        MapSpliterator() {
            this.pos = 0;
            this.max = Double2ObjectOpenHashMap.this.n;
            this.c = 0;
            this.mustReturnNull = Double2ObjectOpenHashMap.this.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = Double2ObjectOpenHashMap.this.n;
            this.c = 0;
            boolean z3 = Double2ObjectOpenHashMap.this.containsNullKey;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        public boolean tryAdvance(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Double2ObjectOpenHashMap.this.n);
                return true;
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                if (Double.doubleToLongBits(dArr[i]) != 0) {
                    this.c++;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    acceptOnIndex(consumertype, i2);
                    return true;
                }
                this.pos++;
            }
        }

        public void forEachRemaining(ConsumerType consumertype) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                acceptOnIndex(consumertype, Double2ObjectOpenHashMap.this.n);
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                if (Double.doubleToLongBits(dArr[i]) != 0) {
                    acceptOnIndex(consumertype, this.pos);
                    this.c++;
                }
                this.pos++;
            }
        }

        public long estimateSize() {
            if (this.hasSplit) {
                return Math.min(Double2ObjectOpenHashMap.this.size - this.c, ((long) ((((double) Double2ObjectOpenHashMap.this.realSize()) / ((double) Double2ObjectOpenHashMap.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return Double2ObjectOpenHashMap.this.size - this.c;
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

    private final class EntrySpliterator extends Double2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super Double2ObjectMap.Entry<V>>, Double2ObjectOpenHashMap<V>.EntrySpliterator> implements ObjectSpliterator<Double2ObjectMap.Entry<V>> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super Double2ObjectMap.Entry<V>> consumer, int i) {
            consumer.accept(new MapEntry(i));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final Double2ObjectOpenHashMap<V>.EntrySpliterator makeForSplit(int i, int i2, boolean z) {
            return new EntrySpliterator(i, i2, z, true);
        }
    }

    private final class MapEntrySet extends AbstractObjectSet<Double2ObjectMap.Entry<V>> implements Double2ObjectMap.FastEntrySet<V> {
        private MapEntrySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap.FastEntrySet
        public ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<Double2ObjectMap.Entry<V>> spliterator() {
            return new EntrySpliterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            double d;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return false;
            }
            double dDoubleValue = ((Double) entry.getKey()).doubleValue();
            Object value = entry.getValue();
            if (Double.doubleToLongBits(dDoubleValue) == 0) {
                return Double2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n], value);
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(dDoubleValue))) & Double2ObjectOpenHashMap.this.mask;
            double d2 = dArr[iMix];
            if (Double.doubleToLongBits(d2) == 0) {
                return false;
            }
            if (Double.doubleToLongBits(dDoubleValue) == Double.doubleToLongBits(d2)) {
                return Objects.equals(Double2ObjectOpenHashMap.this.value[iMix], value);
            }
            do {
                iMix = (iMix + 1) & Double2ObjectOpenHashMap.this.mask;
                d = dArr[iMix];
                if (Double.doubleToLongBits(d) == 0) {
                    return false;
                }
            } while (Double.doubleToLongBits(dDoubleValue) != Double.doubleToLongBits(d));
            return Objects.equals(Double2ObjectOpenHashMap.this.value[iMix], value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return false;
            }
            double dDoubleValue = ((Double) entry.getKey()).doubleValue();
            Object value = entry.getValue();
            if (Double.doubleToLongBits(dDoubleValue) == 0) {
                if (!Double2ObjectOpenHashMap.this.containsNullKey || !Objects.equals(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n], value)) {
                    return false;
                }
                Double2ObjectOpenHashMap.this.removeNullEntry();
                return true;
            }
            double[] dArr = Double2ObjectOpenHashMap.this.key;
            int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(dDoubleValue))) & Double2ObjectOpenHashMap.this.mask;
            double d = dArr[iMix];
            if (Double.doubleToLongBits(d) == 0) {
                return false;
            }
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(dDoubleValue)) {
                if (!Objects.equals(Double2ObjectOpenHashMap.this.value[iMix], value)) {
                    return false;
                }
                Double2ObjectOpenHashMap.this.removeEntry(iMix);
                return true;
            }
            while (true) {
                iMix = (iMix + 1) & Double2ObjectOpenHashMap.this.mask;
                double d2 = dArr[iMix];
                if (Double.doubleToLongBits(d2) == 0) {
                    return false;
                }
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(dDoubleValue) && Objects.equals(Double2ObjectOpenHashMap.this.value[iMix], value)) {
                    Double2ObjectOpenHashMap.this.removeEntry(iMix);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Double2ObjectOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Double2ObjectOpenHashMap.this.clear();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
            if (Double2ObjectOpenHashMap.this.containsNullKey) {
                consumer.accept(new AbstractDouble2ObjectMap.BasicEntry(Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n], Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n]));
            }
            int i = Double2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[i2]) != 0) {
                    consumer.accept(new AbstractDouble2ObjectMap.BasicEntry(Double2ObjectOpenHashMap.this.key[i2], Double2ObjectOpenHashMap.this.value[i2]));
                }
                i = i2;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
            AbstractDouble2ObjectMap.BasicEntry basicEntry = new AbstractDouble2ObjectMap.BasicEntry();
            if (Double2ObjectOpenHashMap.this.containsNullKey) {
                basicEntry.key = Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n];
                basicEntry.value = Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n];
                consumer.accept(basicEntry);
            }
            int i = Double2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[i2]) != 0) {
                    basicEntry.key = Double2ObjectOpenHashMap.this.key[i2];
                    basicEntry.value = Double2ObjectOpenHashMap.this.value[i2];
                    consumer.accept(basicEntry);
                }
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap
    public Double2ObjectMap.FastEntrySet<V> double2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    private final class KeyIterator extends Double2ObjectOpenHashMap<V>.MapIterator<java.util.function.DoubleConsumer> implements DoubleIterator {
        @Override // java.util.PrimitiveIterator.OfDouble
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            super.forEachRemaining(doubleConsumer);
        }

        public KeyIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(java.util.function.DoubleConsumer doubleConsumer, int i) {
            doubleConsumer.accept(Double2ObjectOpenHashMap.this.key[i]);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
        public double nextDouble() {
            return Double2ObjectOpenHashMap.this.key[nextEntry()];
        }
    }

    private final class KeySpliterator extends Double2ObjectOpenHashMap<V>.MapSpliterator<java.util.function.DoubleConsumer, Double2ObjectOpenHashMap<V>.KeySpliterator> implements DoubleSpliterator {
        @Override // java.util.Spliterator.OfDouble
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            super.forEachRemaining(doubleConsumer);
        }

        @Override // java.util.Spliterator.OfDouble
        public /* bridge */ /* synthetic */ boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            return super.tryAdvance(doubleConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ Spliterator trySplit() {
            return (Spliterator) super.trySplit();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public /* bridge */ /* synthetic */ DoubleSpliterator trySplit() {
            return (DoubleSpliterator) super.trySplit();
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(java.util.function.DoubleConsumer doubleConsumer, int i) {
            doubleConsumer.accept(Double2ObjectOpenHashMap.this.key[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final Double2ObjectOpenHashMap<V>.KeySpliterator makeForSplit(int i, int i2, boolean z) {
            return new KeySpliterator(i, i2, z, true);
        }
    }

    private final class KeySet extends AbstractDoubleSet {
        private KeySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleIterator iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleSpliterator spliterator() {
            return new KeySpliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
        public void forEach(java.util.function.DoubleConsumer doubleConsumer) {
            if (Double2ObjectOpenHashMap.this.containsNullKey) {
                doubleConsumer.accept(Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n]);
            }
            int i = Double2ObjectOpenHashMap.this.n;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return;
                }
                double d = Double2ObjectOpenHashMap.this.key[i2];
                if (Double.doubleToLongBits(d) != 0) {
                    doubleConsumer.accept(d);
                }
                i = i2;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Double2ObjectOpenHashMap.this.size;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean contains(double d) {
            return Double2ObjectOpenHashMap.this.containsKey(d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet
        public boolean remove(double d) {
            int i = Double2ObjectOpenHashMap.this.size;
            Double2ObjectOpenHashMap.this.remove(d);
            return Double2ObjectOpenHashMap.this.size != i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Double2ObjectOpenHashMap.this.clear();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
    public Set<Double> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    private final class ValueIterator extends Double2ObjectOpenHashMap<V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        public ValueIterator() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapIterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Double2ObjectOpenHashMap.this.value[i]);
        }

        @Override // java.util.Iterator
        public V next() {
            return Double2ObjectOpenHashMap.this.value[nextEntry()];
        }
    }

    private final class ValueSpliterator extends Double2ObjectOpenHashMap<V>.MapSpliterator<Consumer<? super V>, Double2ObjectOpenHashMap<V>.ValueSpliterator> implements ObjectSpliterator<V> {
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
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final void acceptOnIndex(Consumer<? super V> consumer, int i) {
            consumer.accept(Double2ObjectOpenHashMap.this.value[i]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.MapSpliterator
        public final Double2ObjectOpenHashMap<V>.ValueSpliterator makeForSplit(int i, int i2, boolean z) {
            return new ValueSpliterator(i, i2, z, true);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap.1
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
                    if (Double2ObjectOpenHashMap.this.containsNullKey) {
                        consumer.accept(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n]);
                    }
                    int i = Double2ObjectOpenHashMap.this.n;
                    while (true) {
                        int i2 = i - 1;
                        if (i == 0) {
                            return;
                        }
                        if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[i2]) != 0) {
                            consumer.accept(Double2ObjectOpenHashMap.this.value[i2]);
                        }
                        i = i2;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Double2ObjectOpenHashMap.this.size;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return Double2ObjectOpenHashMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Double2ObjectOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    protected void rehash(int i) {
        double[] dArr = this.key;
        V[] vArr = this.value;
        int i2 = i - 1;
        int i3 = i + 1;
        double[] dArr2 = new double[i3];
        V[] vArr2 = (V[]) new Object[i3];
        int i4 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i5 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i4--;
                } while (Double.doubleToLongBits(dArr[i4]) == 0);
                int iMix = ((int) HashCommon.mix(Double.doubleToRawLongBits(dArr[i4]))) & i2;
                if (Double.doubleToLongBits(dArr2[iMix]) != 0) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (Double.doubleToLongBits(dArr2[iMix]) != 0);
                }
                dArr2[iMix] = dArr[i4];
                vArr2[iMix] = vArr[i4];
                iRealSize = i5;
            } else {
                vArr2[i] = vArr[this.n];
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = dArr2;
                this.value = vArr2;
                return;
            }
        }
    }

    public Double2ObjectOpenHashMap<V> clone() {
        try {
            Double2ObjectOpenHashMap<V> double2ObjectOpenHashMap = (Double2ObjectOpenHashMap) super.clone();
            double2ObjectOpenHashMap.keys = null;
            double2ObjectOpenHashMap.values = null;
            double2ObjectOpenHashMap.entries = null;
            double2ObjectOpenHashMap.containsNullKey = this.containsNullKey;
            double2ObjectOpenHashMap.key = (double[]) this.key.clone();
            double2ObjectOpenHashMap.value = (V[]) ((Object[]) this.value.clone());
            return double2ObjectOpenHashMap;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap, java.util.Map
    public int hashCode() {
        int iRealSize = realSize();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = iRealSize - 1;
            if (iRealSize == 0) {
                break;
            }
            while (Double.doubleToLongBits(this.key[i]) == 0) {
                i++;
            }
            int iDouble2int = HashCommon.double2int(this.key[i]);
            V v = this.value[i];
            if (this != v) {
                iDouble2int ^= v == null ? 0 : v.hashCode();
            }
            i2 += iDouble2int;
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
        double[] dArr = this.key;
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
            objectOutputStream.writeDouble(dArr[iNextEntry]);
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
        double[] dArr = new double[i2 + 1];
        this.key = dArr;
        V[] vArr = (V[]) new Object[i2 + 1];
        this.value = vArr;
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            double d = objectInputStream.readDouble();
            Object object = objectInputStream.readObject();
            if (Double.doubleToLongBits(d) == 0) {
                i = this.n;
                this.containsNullKey = true;
            } else {
                int iMix = (int) HashCommon.mix(Double.doubleToRawLongBits(d));
                int i5 = this.mask;
                while (true) {
                    i = iMix & i5;
                    if (Double.doubleToLongBits(dArr[i]) != 0) {
                        iMix = i + 1;
                        i5 = this.mask;
                    }
                }
            }
            dArr[i] = d;
            vArr[i] = object;
            i3 = i4;
        }
    }
}

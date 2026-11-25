package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import android.Manifest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public class ReferenceOpenHashSet<K> extends AbstractReferenceSet<K> implements Hash, Serializable, Cloneable {
    private static final Collector<Object, ?, ReferenceOpenHashSet<Object>> TO_SET_COLLECTOR = Collector.of(new Supplier() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return new ReferenceOpenHashSet();
        }
    }, new BiConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet$$ExternalSyntheticLambda1
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ReferenceOpenHashSet) obj).add(obj2);
        }
    }, new BinaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet$$ExternalSyntheticLambda2
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((ReferenceOpenHashSet) obj).combine((ReferenceOpenHashSet) obj2);
        }
    }, new Collector.Characteristics[0]);
    private static final long serialVersionUID = 0;
    protected transient boolean containsNull;
    protected final float f;
    protected transient K[] key;
    protected transient int mask;
    protected transient int maxFill;
    protected final transient int minN;
    protected transient int n;
    protected int size;

    public ReferenceOpenHashSet(int i, float f) {
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
        this.key = (K[]) new Object[this.n + 1];
    }

    public ReferenceOpenHashSet(int i) {
        this(i, 0.75f);
    }

    public ReferenceOpenHashSet() {
        this(16, 0.75f);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ReferenceOpenHashSet(K[] kArr, int i, int i2, float f) {
        this(i2 < 0 ? 0 : i2, f);
        ObjectArrays.ensureOffsetLength(kArr, i, i2);
        for (int i3 = 0; i3 < i2; i3++) {
            add(kArr[i + i3]);
        }
    }

    public ReferenceOpenHashSet(K[] kArr, float f) {
        this(kArr, 0, kArr.length, f);
    }

    public ReferenceOpenHashSet(K[] kArr) {
        this(kArr, 0.75f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ReferenceOpenHashSet<K> combine(ReferenceOpenHashSet<? extends K> referenceOpenHashSet) {
        addAll(referenceOpenHashSet);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends K> collection) {
        if (this.f <= 0.5d) {
            ensureCapacity(collection.size());
        } else {
            tryCapacity(size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(K k) {
        K k2;
        if (k == null) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            K[] kArr = this.key;
            int iMix = HashCommon.mix(System.identityHashCode(k)) & this.mask;
            K k3 = kArr[iMix];
            if (k3 != null) {
                if (k3 == k) {
                    return false;
                }
                do {
                    iMix = (iMix + 1) & this.mask;
                    k2 = kArr[iMix];
                    if (k2 != null) {
                    }
                } while (k2 != k);
                return false;
            }
            kArr[iMix] = k;
        }
        int i = this.size;
        this.size = i + 1;
        if (i >= this.maxFill) {
            rehash(HashCommon.arraySize(i + 2, this.f));
        }
        return true;
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
            i = i2;
        }
    }

    private boolean removeEntry(int i) {
        this.size--;
        shiftKeys(i);
        int i2 = this.n;
        if (i2 > this.minN && this.size < this.maxFill / 4 && i2 > 16) {
            rehash(i2 / 2);
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        K[] kArr = this.key;
        int i = this.n;
        kArr[i] = null;
        int i2 = this.size - 1;
        this.size = i2;
        if (i > this.minN && i2 < this.maxFill / 4 && i > 16) {
            rehash(i / 2);
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        K k;
        if (obj == null) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        K[] kArr = this.key;
        int iMix = HashCommon.mix(System.identityHashCode(obj)) & this.mask;
        K k2 = kArr[iMix];
        if (k2 == null) {
            return false;
        }
        if (obj == k2) {
            return removeEntry(iMix);
        }
        do {
            iMix = (iMix + 1) & this.mask;
            k = kArr[iMix];
            if (k == null) {
                return false;
            }
        } while (obj != k);
        return removeEntry(iMix);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        K k;
        if (obj == null) {
            return this.containsNull;
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, (Object) null);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    private final class SetIterator implements ObjectIterator<K> {
        int c;
        int last;
        boolean mustReturnNull;
        int pos;
        ReferenceArrayList<K> wrapped;

        private SetIterator() {
            this.pos = ReferenceOpenHashSet.this.n;
            this.last = -1;
            this.c = ReferenceOpenHashSet.this.size;
            this.mustReturnNull = ReferenceOpenHashSet.this.containsNull;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c != 0;
        }

        @Override // java.util.Iterator
        public K next() {
            int i;
            K k;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.c--;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ReferenceOpenHashSet.this.n;
                return ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n];
            }
            K[] kArr = ReferenceOpenHashSet.this.key;
            do {
                i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    return this.wrapped.get((-i) - 1);
                }
                k = kArr[i];
            } while (k == null);
            this.last = i;
            return k;
        }

        private final void shiftKeys(int i) {
            int i2;
            K k;
            K[] kArr = ReferenceOpenHashSet.this.key;
            while (true) {
                int i3 = i + 1;
                int i4 = ReferenceOpenHashSet.this.mask;
                while (true) {
                    i2 = i3 & i4;
                    k = kArr[i2];
                    if (k == null) {
                        kArr[i] = null;
                        return;
                    }
                    int iMix = HashCommon.mix(System.identityHashCode(k)) & ReferenceOpenHashSet.this.mask;
                    if (i > i2) {
                        if (i >= iMix && iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = ReferenceOpenHashSet.this.mask;
                    } else {
                        if (i >= iMix || iMix > i2) {
                            break;
                        }
                        i3 = i2 + 1;
                        i4 = ReferenceOpenHashSet.this.mask;
                    }
                }
                if (i2 < i) {
                    if (this.wrapped == null) {
                        this.wrapped = new ReferenceArrayList<>(2);
                    }
                    this.wrapped.add(kArr[i2]);
                }
                kArr[i] = k;
                i = i2;
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.last;
            if (i == -1) {
                throw new IllegalStateException();
            }
            if (i == ReferenceOpenHashSet.this.n) {
                ReferenceOpenHashSet.this.containsNull = false;
                ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n] = null;
            } else {
                int i2 = this.pos;
                if (i2 < 0) {
                    ReferenceOpenHashSet.this.remove(this.wrapped.set((-i2) - 1, null));
                    this.last = -1;
                    return;
                }
                shiftKeys(this.last);
            }
            ReferenceOpenHashSet.this.size--;
            this.last = -1;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            K[] kArr = ReferenceOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ReferenceOpenHashSet.this.n;
                consumer.accept(kArr[ReferenceOpenHashSet.this.n]);
                this.c--;
            }
            while (this.c != 0) {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    consumer.accept(this.wrapped.get((-i) - 1));
                    this.c--;
                } else {
                    Manifest manifest = kArr[i];
                    if (manifest != null) {
                        this.last = i;
                        consumer.accept(manifest);
                        this.c--;
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    public ObjectIterator<K> iterator() {
        return new SetIterator();
    }

    private final class SetSpliterator implements ObjectSpliterator<K> {
        int c;
        boolean hasSplit;
        int max;
        boolean mustReturnNull;
        int pos;

        SetSpliterator() {
            this.pos = 0;
            this.max = ReferenceOpenHashSet.this.n;
            this.c = 0;
            this.mustReturnNull = ReferenceOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int i, int i2, boolean z, boolean z2) {
            this.pos = 0;
            this.max = ReferenceOpenHashSet.this.n;
            this.c = 0;
            boolean z3 = ReferenceOpenHashSet.this.containsNull;
            this.pos = i;
            this.max = i2;
            this.mustReturnNull = z;
            this.hasSplit = z2;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.c++;
                consumer.accept(ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n]);
                return true;
            }
            K[] kArr = ReferenceOpenHashSet.this.key;
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return false;
                }
                Manifest manifest = kArr[i];
                if (manifest != null) {
                    this.c++;
                    this.pos = i + 1;
                    consumer.accept(manifest);
                    return true;
                }
                this.pos = i + 1;
            }
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            K[] kArr = ReferenceOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                consumer.accept(kArr[ReferenceOpenHashSet.this.n]);
                this.c++;
            }
            while (true) {
                int i = this.pos;
                if (i >= this.max) {
                    return;
                }
                Manifest manifest = kArr[i];
                if (manifest != null) {
                    consumer.accept(manifest);
                    this.c++;
                }
                this.pos++;
            }
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (this.hasSplit) {
                return Math.min(ReferenceOpenHashSet.this.size - this.c, ((long) ((((double) ReferenceOpenHashSet.this.realSize()) / ((double) ReferenceOpenHashSet.this.n)) * ((double) (this.max - this.pos)))) + (this.mustReturnNull ? 1L : 0L));
            }
            return ReferenceOpenHashSet.this.size - this.c;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ReferenceOpenHashSet<K>.SetSpliterator trySplit() {
            int i;
            int i2 = this.pos;
            int i3 = this.max;
            if (i2 >= i3 - 1 || (i = (i3 - i2) >> 1) <= 1) {
                return null;
            }
            int i4 = i2 + i;
            ReferenceOpenHashSet<K>.SetSpliterator setSpliterator = new SetSpliterator(i2, i4, this.mustReturnNull, true);
            this.pos = i4;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    public ObjectSpliterator<K> spliterator() {
        return new SetSpliterator();
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super K> consumer) {
        if (this.containsNull) {
            consumer.accept(this.key[this.n]);
        }
        K[] kArr = this.key;
        int i = this.n;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            Manifest manifest = kArr[i2];
            if (manifest != null) {
                consumer.accept(manifest);
            }
            i = i2;
        }
    }

    protected void rehash(int i) {
        K k;
        K[] kArr = this.key;
        int i2 = i - 1;
        K[] kArr2 = (K[]) new Object[i + 1];
        int i3 = this.n;
        int iRealSize = realSize();
        while (true) {
            int i4 = iRealSize - 1;
            if (iRealSize != 0) {
                do {
                    i3--;
                    k = kArr[i3];
                } while (k == null);
                int iMix = HashCommon.mix(System.identityHashCode(k)) & i2;
                if (kArr2[iMix] != null) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (kArr2[iMix] != null);
                }
                kArr2[iMix] = kArr[i3];
                iRealSize = i4;
            } else {
                this.n = i;
                this.mask = i2;
                this.maxFill = HashCommon.maxFill(i, this.f);
                this.key = kArr2;
                return;
            }
        }
    }

    public ReferenceOpenHashSet<K> clone() {
        try {
            ReferenceOpenHashSet<K> referenceOpenHashSet = (ReferenceOpenHashSet) super.clone();
            referenceOpenHashSet.key = (K[]) ((Object[]) this.key.clone());
            referenceOpenHashSet.containsNull = this.containsNull;
            return referenceOpenHashSet;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceSet, java.util.Collection, java.util.Set
    public int hashCode() {
        K k;
        int iRealSize = realSize();
        int iIdentityHashCode = 0;
        int i = 0;
        while (true) {
            int i2 = iRealSize - 1;
            if (iRealSize == 0) {
                return iIdentityHashCode;
            }
            while (true) {
                k = this.key[i];
                if (k != null) {
                    break;
                }
                i++;
            }
            if (this != k) {
                iIdentityHashCode += System.identityHashCode(k);
            }
            i++;
            iRealSize = i2;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectIterator<K> it = iterator();
        objectOutputStream.defaultWriteObject();
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return;
            }
            objectOutputStream.writeObject(it.next());
            i = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        int iMix;
        objectInputStream.defaultReadObject();
        int iArraySize = HashCommon.arraySize(this.size, this.f);
        this.n = iArraySize;
        this.maxFill = HashCommon.maxFill(iArraySize, this.f);
        int i = this.n;
        this.mask = i - 1;
        K[] kArr = (K[]) new Object[i + 1];
        this.key = kArr;
        int i2 = this.size;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return;
            }
            Object object = objectInputStream.readObject();
            if (object == null) {
                iMix = this.n;
                this.containsNull = true;
            } else {
                iMix = HashCommon.mix(System.identityHashCode(object)) & this.mask;
                if (kArr[iMix] != 0) {
                    do {
                        iMix = (iMix + 1) & this.mask;
                    } while (kArr[iMix] != 0);
                }
            }
            kArr[iMix] = object;
            i2 = i3;
        }
    }
}

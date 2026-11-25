package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes5.dex */
public class ByteArrayList extends AbstractByteList implements Serializable, Cloneable, RandomAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -7046029254386353130L;
    protected transient byte[] a;
    protected int size;

    private static final byte[] copyArraySafe(byte[] bArr, int i) {
        if (i == 0) {
            return ByteArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(bArr, i);
    }

    protected ByteArrayList(byte[] bArr, boolean z) {
        this.a = bArr;
    }

    public ByteArrayList() {
        this.a = ByteArrays.DEFAULT_EMPTY_ARRAY;
    }

    private void grow(int i) {
        byte[] bArr = this.a;
        if (i <= bArr.length) {
            return;
        }
        if (bArr != ByteArrays.DEFAULT_EMPTY_ARRAY) {
            byte[] bArr2 = this.a;
            i = (int) Math.max(Math.min(((long) bArr2.length) + ((long) (bArr2.length >> 1)), 2147483639L), i);
        } else if (i < 10) {
            i = 10;
        }
        this.a = ByteArrays.forceCapacity(this.a, i, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void add(int i, byte b) {
        ensureIndex(i);
        grow(this.size + 1);
        int i2 = this.size;
        if (i != i2) {
            byte[] bArr = this.a;
            System.arraycopy(bArr, i, bArr, i + 1, i2 - i);
        }
        this.a[i] = b;
        this.size++;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean add(byte b) {
        grow(this.size + 1);
        byte[] bArr = this.a;
        int i = this.size;
        this.size = i + 1;
        bArr[i] = b;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public byte getByte(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        return this.a[i];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public int indexOf(byte b) {
        for (int i = 0; i < this.size; i++) {
            if (b == this.a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public int lastIndexOf(byte b) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (b == this.a[i2]) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public byte removeByte(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        byte[] bArr = this.a;
        byte b = bArr[i];
        int i3 = i2 - 1;
        this.size = i3;
        if (i != i3) {
            System.arraycopy(bArr, i + 1, bArr, i, i3 - i);
        }
        return b;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean rem(byte b) {
        int iIndexOf = indexOf(b);
        if (iIndexOf == -1) {
            return false;
        }
        removeByte(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public byte set(int i, byte b) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        byte[] bArr = this.a;
        byte b2 = bArr[i];
        bArr[i] = b;
        return b2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.size == 0;
    }

    private class SubList extends AbstractByteList.ByteRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int i, int i2) {
            super(ByteArrayList.this, i, i2);
        }

        private byte[] getParentArray() {
            return ByteArrayList.this.a;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.ByteSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public byte getByte(int i) {
            ensureRestrictedIndex(i);
            return ByteArrayList.this.a[i + this.from];
        }

        private final class SubListIterator extends ByteIterators.AbstractIndexBasedListIterator {
            SubListIterator(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final byte get(int i) {
                return ByteArrayList.this.a[SubList.this.from + i];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void add(int i, byte b) {
                SubList.this.add(i, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void set(int i, byte b) {
                SubList.this.set(i, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.removeByte(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator
            public byte nextByte() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                byte[] bArr = ByteArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return bArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator
            public byte previousByte() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                byte[] bArr = ByteArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return bArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.PrimitiveIterator
            public void forEachRemaining(ByteConsumer byteConsumer) {
                int i = SubList.this.to - SubList.this.from;
                while (this.pos < i) {
                    byte[] bArr = ByteArrayList.this.a;
                    int i2 = SubList.this.from;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    this.lastReturned = i3;
                    byteConsumer.accept(bArr[i2 + i3]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.ByteSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteListIterator listIterator(int i) {
            return new SubListIterator(i);
        }

        private final class SubListSpliterator extends ByteSpliterators.LateBindingSizeIndexBasedSpliterator {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int i, int i2) {
                super(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator
            protected final byte get(int i) {
                return ByteArrayList.this.a[i];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int i, int i2) {
                return SubList.this.new SubListSpliterator(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public boolean tryAdvance(ByteConsumer byteConsumer) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                byte[] bArr = ByteArrayList.this.a;
                int i = this.pos;
                this.pos = i + 1;
                byteConsumer.accept(bArr[i]);
                return true;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public void forEachRemaining(ByteConsumer byteConsumer) {
                int maxPos = getMaxPos();
                while (this.pos < maxPos) {
                    byte[] bArr = ByteArrayList.this.a;
                    int i = this.pos;
                    this.pos = i + 1;
                    byteConsumer.accept(bArr[i]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.ByteSubList, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteSpliterator spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(byte[] bArr, int i, int i2) {
            if (ByteArrayList.this.a == bArr && this.from == i && this.to == i2) {
                return true;
            }
            if (i2 - i != size()) {
                return false;
            }
            int i3 = this.from;
            while (i3 < this.to) {
                int i4 = i3 + 1;
                int i5 = i + 1;
                if (ByteArrayList.this.a[i3] != bArr[i]) {
                    return false;
                }
                i = i5;
                i3 = i4;
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof List)) {
                return false;
            }
            if (obj instanceof ByteArrayList) {
                ByteArrayList byteArrayList = (ByteArrayList) obj;
                return contentsEquals(byteArrayList.a, 0, byteArrayList.size());
            }
            if (obj instanceof SubList) {
                SubList subList = (SubList) obj;
                return contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(obj);
        }

        int contentsCompareTo(byte[] bArr, int i, int i2) {
            if (ByteArrayList.this.a == bArr && this.from == i && this.to == i2) {
                return 0;
            }
            int i3 = this.from;
            while (i3 < this.to && i3 < i2) {
                int iCompare = Byte.compare(ByteArrayList.this.a[i3], bArr[i]);
                if (iCompare != 0) {
                    return iCompare;
                }
                i3++;
                i++;
            }
            if (i3 < i2) {
                return -1;
            }
            return i3 < this.to ? 1 : 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.ByteSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.lang.Comparable
        public int compareTo(List<? extends Byte> list) {
            if (list instanceof ByteArrayList) {
                ByteArrayList byteArrayList = (ByteArrayList) list;
                return contentsCompareTo(byteArrayList.a, 0, byteArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList) list;
                return contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteList subList(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        ensureIndex(i);
        ensureIndex(i2);
        if (i > i2) {
            throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        return new SubList(i, i2);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void getElements(int i, byte[] bArr, int i2, int i3) {
        ByteArrays.ensureOffsetLength(bArr, i2, i3);
        System.arraycopy(this.a, i, bArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void removeElements(int i, int i2) {
        org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, i, i2);
        byte[] bArr = this.a;
        System.arraycopy(bArr, i2, bArr, i, this.size - i2);
        this.size -= i2 - i;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void setElements(int i, byte[] bArr, int i2, int i3) {
        ensureIndex(i);
        ByteArrays.ensureOffsetLength(bArr, i2, i3);
        int i4 = i + i3;
        if (i4 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + this.size + Constant.AFTER_QUTO);
        }
        System.arraycopy(bArr, i2, this.a, i, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    public void forEach(ByteConsumer byteConsumer) {
        for (int i = 0; i < this.size; i++) {
            byteConsumer.accept(this.a[i]);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList
    public boolean addAll(int i, ByteCollection byteCollection) {
        if (byteCollection instanceof ByteList) {
            return addAll(i, (ByteList) byteCollection);
        }
        ensureIndex(i);
        int size = byteCollection.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        byte[] bArr = this.a;
        System.arraycopy(bArr, i, bArr, i + size, this.size - i);
        ByteIterator it = byteCollection.iterator();
        this.size += size;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return true;
            }
            this.a[i] = it.nextByte();
            size = i2;
            i++;
        }
    }

    public boolean addAll(int i, ByteList byteList) {
        ensureIndex(i);
        int size = byteList.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        byte[] bArr = this.a;
        System.arraycopy(bArr, i, bArr, i + size, this.size - i);
        byteList.getElements(0, this.a, i, size);
        this.size += size;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection
    public boolean removeAll(ByteCollection byteCollection) {
        int i;
        byte[] bArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.size;
            if (i2 >= i) {
                break;
            }
            if (!byteCollection.contains(bArr[i2])) {
                bArr[i3] = bArr[i2];
                i3++;
            }
            i2++;
        }
        boolean z = i != i3;
        this.size = i3;
        return z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection
    public byte[] toArray(byte[] bArr) {
        if (bArr == null || bArr.length < this.size) {
            bArr = Arrays.copyOf(bArr, this.size);
        }
        System.arraycopy(this.a, 0, bArr, 0, this.size);
        return bArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteListIterator listIterator(int i) {
        ensureIndex(i);
        return new ByteListIterator(i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteArrayList.1
            int last = -1;
            int pos;
            final /* synthetic */ int val$index;

            {
                this.val$index = i;
                this.pos = i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < ByteArrayList.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator
            public byte nextByte() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                byte[] bArr = ByteArrayList.this.a;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.last = i2;
                return bArr[i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator
            public byte previousByte() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                byte[] bArr = ByteArrayList.this.a;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.last = i2;
                return bArr[i2];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
            public void add(byte b) {
                ByteArrayList byteArrayList = ByteArrayList.this;
                int i2 = this.pos;
                this.pos = i2 + 1;
                byteArrayList.add(i2, b);
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
            public void set(byte b) {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                ByteArrayList.this.set(i2, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                ByteArrayList.this.removeByte(i2);
                int i3 = this.last;
                int i4 = this.pos;
                if (i3 < i4) {
                    this.pos = i4 - 1;
                }
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.PrimitiveIterator
            public void forEachRemaining(ByteConsumer byteConsumer) {
                while (this.pos < ByteArrayList.this.size) {
                    byte[] bArr = ByteArrayList.this.a;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.last = i2;
                    byteConsumer.accept(bArr[i2]);
                }
            }
        };
    }

    private final class Spliterator implements ByteSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        public Spliterator(ByteArrayList byteArrayList) {
            this(0, byteArrayList.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : ByteArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(ByteConsumer byteConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            byte[] bArr = ByteArrayList.this.a;
            int i = this.pos;
            this.pos = i + 1;
            byteConsumer.accept(bArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(ByteConsumer byteConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                byteConsumer.accept(ByteArrayList.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterator, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public ByteSpliterator trySplit() {
            int workingMax = getWorkingMax();
            int i = this.pos;
            int i2 = (workingMax - i) >> 1;
            if (i2 <= 1) {
                return null;
            }
            this.max = workingMax;
            int i3 = i2 + i;
            this.pos = i3;
            this.hasSplit = true;
            return ByteArrayList.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void sort(ByteComparator byteComparator) {
        if (byteComparator == null) {
            ByteArrays.stableSort(this.a, 0, this.size);
        } else {
            ByteArrays.stableSort(this.a, 0, this.size, byteComparator);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void unstableSort(ByteComparator byteComparator) {
        if (byteComparator == null) {
            ByteArrays.unstableSort(this.a, 0, this.size);
        } else {
            ByteArrays.unstableSort(this.a, 0, this.size, byteComparator);
        }
    }

    public ByteArrayList clone() {
        if (getClass() == ByteArrayList.class) {
            ByteArrayList byteArrayList = new ByteArrayList(copyArraySafe(this.a, this.size), false);
            byteArrayList.size = this.size;
            return byteArrayList;
        }
        try {
            ByteArrayList byteArrayList2 = (ByteArrayList) super.clone();
            byteArrayList2.a = copyArraySafe(this.a, this.size);
            return byteArrayList2;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean equals(ByteArrayList byteArrayList) {
        if (byteArrayList == this) {
            return true;
        }
        int size = size();
        if (size != byteArrayList.size()) {
            return false;
        }
        byte[] bArr = this.a;
        byte[] bArr2 = byteArrayList.a;
        if (bArr == bArr2 && size == byteArrayList.size()) {
            return true;
        }
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof List)) {
            return false;
        }
        if (obj instanceof ByteArrayList) {
            return equals((ByteArrayList) obj);
        }
        if (obj instanceof SubList) {
            return ((SubList) obj).equals(this);
        }
        return super.equals(obj);
    }

    public int compareTo(ByteArrayList byteArrayList) {
        int size = size();
        int size2 = byteArrayList.size();
        byte[] bArr = this.a;
        byte[] bArr2 = byteArrayList.a;
        if (bArr == bArr2 && size == size2) {
            return 0;
        }
        int i = 0;
        while (i < size && i < size2) {
            int iCompare = Byte.compare(bArr[i], bArr2[i]);
            if (iCompare != 0) {
                return iCompare;
            }
            i++;
        }
        if (i < size2) {
            return -1;
        }
        return i < size ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.lang.Comparable
    public int compareTo(List<? extends Byte> list) {
        if (list instanceof ByteArrayList) {
            return compareTo((ByteArrayList) list);
        }
        if (list instanceof SubList) {
            return -((SubList) list).compareTo((List<? extends Byte>) this);
        }
        return super.compareTo(list);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeByte(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new byte[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readByte();
        }
    }
}

package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractByteList extends AbstractByteCollection implements ByteList {
    protected AbstractByteList() {
    }

    protected void ensureIndex(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
    }

    protected void ensureRestrictedIndex(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i >= size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + size() + Constant.AFTER_QUTO);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void add(int i, byte b) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean add(byte b) {
        add(size(), b);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public byte removeByte(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public byte set(int i, byte b) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Byte> collection) {
        if (collection instanceof ByteCollection) {
            return addAll(i, (ByteCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Byte> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().byteValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Byte> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    public ByteListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteListIterator listIterator(int i) {
        ensureIndex(i);
        return new ByteIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final byte get(int i2) {
                return AbstractByteList.this.getByte(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, byte b) {
                AbstractByteList.this.add(i2, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, byte b) {
                AbstractByteList.this.set(i2, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractByteList.this.removeByte(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractByteList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends ByteSpliterators.LateBindingSizeIndexBasedSpliterator {
        final ByteList l;

        IndexBasedSpliterator(ByteList byteList, int i) {
            super(i);
            this.l = byteList;
        }

        IndexBasedSpliterator(ByteList byteList, int i, int i2) {
            super(i, i2);
            this.l = byteList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator
        protected final byte get(int i) {
            return this.l.getByte(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean contains(byte b) {
        return indexOf(b) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public int indexOf(byte b) {
        ByteListIterator byteListIteratorListIterator = listIterator();
        while (byteListIteratorListIterator.hasNext()) {
            if (b == byteListIteratorListIterator.nextByte()) {
                return byteListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public int lastIndexOf(byte b) {
        ByteListIterator byteListIteratorListIterator = listIterator(size());
        while (byteListIteratorListIterator.hasPrevious()) {
            if (b == byteListIteratorListIterator.previousByte()) {
                return byteListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    public ByteList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new ByteRandomAccessSubList(this, i, i2) : new ByteSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    public void forEach(ByteConsumer byteConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                byteConsumer.accept(getByte(i));
            }
            return;
        }
        super.forEach(byteConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        ByteListIterator byteListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            byteListIteratorListIterator.nextByte();
            byteListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void getElements(int i, byte[] bArr, int i2, int i3) {
        ensureIndex(i);
        ByteArrays.ensureOffsetLength(bArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        if (this instanceof RandomAccess) {
            while (true) {
                int i5 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                bArr[i2] = getByte(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            ByteListIterator byteListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                bArr[i2] = byteListIteratorListIterator.nextByte();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
    public void setElements(int i, byte[] bArr, int i2, int i3) {
        ensureIndex(i);
        ByteArrays.ensureOffsetLength(bArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, bArr[i5 + i2]);
                i5++;
            }
        } else {
            ByteListIterator byteListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                byteListIteratorListIterator.nextByte();
                byteListIteratorListIterator.set(bArr[i5 + i2]);
                i5++;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeElements(0, size());
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        ByteListIterator it = iterator();
        int size = size();
        int iNextByte = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iNextByte;
            }
            iNextByte = (iNextByte * 31) + it.nextByte();
            size = i;
        }
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        if (list instanceof ByteList) {
            ByteListIterator byteListIteratorListIterator = listIterator();
            ByteListIterator byteListIteratorListIterator2 = ((ByteList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (byteListIteratorListIterator.nextByte() != byteListIteratorListIterator2.nextByte()) {
                    return false;
                }
                size = i;
            }
        } else {
            ByteListIterator byteListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(byteListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Byte> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof ByteList) {
            ByteListIterator byteListIteratorListIterator = listIterator();
            ByteListIterator byteListIteratorListIterator2 = ((ByteList) list).listIterator();
            while (byteListIteratorListIterator.hasNext() && byteListIteratorListIterator2.hasNext()) {
                int iCompare = Byte.compare(byteListIteratorListIterator.nextByte(), byteListIteratorListIterator2.nextByte());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (byteListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return byteListIteratorListIterator.hasNext() ? 1 : 0;
        }
        ByteListIterator byteListIteratorListIterator3 = listIterator();
        ListIterator<? extends Byte> listIterator = list.listIterator();
        while (byteListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = byteListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return byteListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean rem(byte b) {
        int iIndexOf = indexOf(b);
        if (iIndexOf == -1) {
            return false;
        }
        removeByte(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public byte[] toByteArray() {
        int size = size();
        byte[] bArr = new byte[size];
        getElements(0, bArr, 0, size);
        return bArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection
    public byte[] toArray(byte[] bArr) {
        int size = size();
        if (bArr.length < size) {
            bArr = Arrays.copyOf(bArr, size);
        }
        getElements(0, bArr, 0, size);
        return bArr;
    }

    public boolean addAll(int i, ByteCollection byteCollection) {
        ensureIndex(i);
        ByteIterator it = byteCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextByte());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection
    public boolean addAll(ByteCollection byteCollection) {
        return addAll(size(), byteCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ByteListIterator it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.valueOf((int) it.nextByte()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class ByteSubList extends AbstractByteList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final ByteList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Byte> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
        public /* bridge */ /* synthetic */ ByteIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public ByteSubList(ByteList byteList, int i, int i2) {
            this.l = byteList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
        public boolean add(byte b) {
            this.l.add(this.to, b);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public void add(int i, byte b) {
            ensureIndex(i);
            this.l.add(this.from + i, b);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, java.util.List
        public boolean addAll(int i, Collection<? extends Byte> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public byte getByte(int i) {
            ensureRestrictedIndex(i);
            return this.l.getByte(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public byte removeByte(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeByte(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public byte set(int i, byte b) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, b);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public void getElements(int i, byte[] bArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, bArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            ByteList byteList = this.l;
            int i3 = this.from;
            byteList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList
        public void setElements(int i, byte[] bArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, bArr, i2, i3);
        }

        private final class RandomAccessIter extends ByteIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final byte get(int i) {
                return ByteSubList.this.l.getByte(ByteSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void add(int i, byte b) {
                ByteSubList.this.add(i, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator
            protected final void set(int i, byte b) {
                ByteSubList.this.set(i, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                ByteSubList.this.removeByte(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return ByteSubList.this.to - ByteSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
            public void add(byte b) {
                super.add(b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements ByteListIterator {
            private ByteListIterator parent;

            ParentWrappingIter(ByteListIterator byteListIterator) {
                this.parent = byteListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - ByteSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - ByteSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < ByteSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= ByteSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator
            public byte nextByte() {
                if (hasNext()) {
                    return this.parent.nextByte();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator
            public byte previousByte() {
                if (hasPrevious()) {
                    return this.parent.previousByte();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
            public void add(byte b) {
                this.parent.add(b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator
            public void set(byte b) {
                this.parent.set(b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ByteSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
        public boolean rem(byte b) {
            int iIndexOf = indexOf(b);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeByte(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList
        public boolean addAll(int i, ByteCollection byteCollection) {
            ensureIndex(i);
            return super.addAll(i, byteCollection);
        }
    }

    public static class ByteRandomAccessSubList extends ByteSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public ByteRandomAccessSubList(ByteList byteList, int i, int i2) {
            super(byteList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList.ByteSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.AbstractByteList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
        public ByteList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ByteRandomAccessSubList(this, i, i2);
        }
    }
}

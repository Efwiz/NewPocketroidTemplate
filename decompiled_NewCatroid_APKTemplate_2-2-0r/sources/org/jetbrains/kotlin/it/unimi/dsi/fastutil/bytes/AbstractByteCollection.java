package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractByteCollection extends AbstractCollection<Byte> implements ByteCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    public abstract ByteIterator iterator();

    protected AbstractByteCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean add(byte b) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean contains(byte b) {
        ByteIterator it = iterator();
        while (it.hasNext()) {
            if (b == it.nextByte()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public boolean rem(byte b) {
        ByteIterator it = iterator();
        while (it.hasNext()) {
            if (b == it.nextByte()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    @Deprecated
    public boolean add(Byte b) {
        return super.add(b);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public byte[] toArray(byte[] bArr) {
        int size = size();
        if (bArr == null) {
            bArr = new byte[size];
        } else if (bArr.length < size) {
            bArr = Arrays.copyOf(bArr, size);
        }
        ByteIterators.unwrap(iterator(), bArr);
        return bArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    public byte[] toByteArray() {
        return toArray((byte[]) null);
    }

    public boolean addAll(ByteCollection byteCollection) {
        ByteIterator it = byteCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextByte())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Byte> collection) {
        if (collection instanceof ByteCollection) {
            return addAll((ByteCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(ByteCollection byteCollection) {
        ByteIterator it = byteCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextByte())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ByteCollection) {
            return containsAll((ByteCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(ByteCollection byteCollection) {
        ByteIterator it = byteCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextByte())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof ByteCollection) {
            return removeAll((ByteCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(ByteCollection byteCollection) {
        ByteIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!byteCollection.contains(it.nextByte())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof ByteCollection) {
            return retainAll((ByteCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ByteIterator it = iterator();
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
                sb.append("}");
                return sb.toString();
            }
        }
    }
}

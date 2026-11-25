package org.jf.dexlib2.dexbacked.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.util.collection.ArrayIterator;
import org.jf.util.collection.ArrayListIterator;
import org.jf.util.collection.ComputingList;

/* JADX INFO: loaded from: classes6.dex */
public class DexStringSection implements List<DexString>, Function<DexString, String> {
    private final DexString[] array;
    private final List<String> stringList = new ComputingList(this, this);

    @Override // java.util.List, java.util.Collection
    public boolean add(DexString dexString) {
        return false;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends DexString> collection) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends DexString> collection) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.List
    public DexString remove(int i) {
        return null;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.List
    public DexString set(int i, DexString dexString) {
        return null;
    }

    @Override // java.util.List
    public List<DexString> subList(int i, int i2) {
        return null;
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return tArr;
    }

    public DexStringSection(int i) {
        this.array = new DexString[i];
    }

    public void load(DexBuffer dexBuffer, int i, int i2) {
        DexString[] dexStringArr = this.array;
        int length = dexStringArr.length;
        DexReader<? extends DexBuffer> dexReader = dexBuffer.readerAt(i);
        for (int i3 = 0; i3 < length; i3++) {
            dexReader.setOffset(dexBuffer.readSmallUint((i3 * i2) + i));
            dexStringArr[i3] = new DexString(this, i3, dexReader.readString(dexReader.readSmallUleb128()));
        }
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.array.length;
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.array.length == 0;
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        if (obj instanceof DexString) {
            return contains((DexString) obj);
        }
        return false;
    }

    public boolean contains(DexString dexString) {
        int index;
        return dexString != null && dexString.getStringSection() == this && (index = dexString.getIndex()) >= 0 && index < size() && get(index) == dexString;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<DexString> iterator() {
        return new ArrayIterator(this.array);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return (Object[]) this.array.clone();
    }

    @Override // java.util.List
    public DexString get(int i) {
        if (i < 0) {
            return null;
        }
        return this.array[i];
    }

    @Override // java.util.List
    public void add(int i, DexString dexString) {
        this.array[i] = dexString;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        if (obj instanceof DexString) {
            return ((DexString) obj).getIndex();
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        if (obj instanceof DexString) {
            return ((DexString) obj).getIndex();
        }
        return -1;
    }

    @Override // java.util.List
    public ListIterator<DexString> listIterator() {
        return new ArrayListIterator(this.array);
    }

    @Override // java.util.List
    public ListIterator<DexString> listIterator(int i) {
        return new ArrayListIterator(this.array, i);
    }

    @Override // java.util.function.Function
    public String apply(DexString dexString) {
        if (dexString != null) {
            return dexString.getValue();
        }
        return null;
    }
}

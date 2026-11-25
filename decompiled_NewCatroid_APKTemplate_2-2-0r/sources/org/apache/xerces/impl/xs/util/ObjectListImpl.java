package org.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import org.apache.xerces.xs.datatypes.ObjectList;

/* JADX INFO: loaded from: classes13.dex */
public final class ObjectListImpl extends AbstractList implements ObjectList {
    public static final ObjectListImpl EMPTY_LIST = new ObjectListImpl(new Object[0], 0);
    private final Object[] fArray;
    private final int fLength;

    public ObjectListImpl(Object[] objArr, int i) {
        this.fArray = objArr;
        this.fLength = i;
    }

    private void toArray0(Object[] objArr) {
        int i = this.fLength;
        if (i > 0) {
            System.arraycopy(this.fArray, 0, objArr, 0, i);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, org.apache.xerces.xs.datatypes.ObjectList
    public boolean contains(Object obj) {
        if (obj == null) {
            for (int i = 0; i < this.fLength; i++) {
                if (this.fArray[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i2 = 0; i2 < this.fLength; i2++) {
                if (obj.equals(this.fArray[i2])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        if (i < 0 || i >= this.fLength) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return this.fArray[i];
    }

    @Override // org.apache.xerces.xs.datatypes.ObjectList
    public int getLength() {
        return this.fLength;
    }

    @Override // org.apache.xerces.xs.datatypes.ObjectList
    public Object item(int i) {
        if (i < 0 || i >= this.fLength) {
            return null;
        }
        return this.fArray[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return getLength();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] objArr = new Object[this.fLength];
        toArray0(objArr);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray(Object[] objArr) {
        if (objArr.length < this.fLength) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.fLength);
        }
        toArray0(objArr);
        int length = objArr.length;
        int i = this.fLength;
        if (length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}

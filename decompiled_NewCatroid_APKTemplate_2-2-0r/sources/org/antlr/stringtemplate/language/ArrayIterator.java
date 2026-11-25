package org.antlr.stringtemplate.language;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes12.dex */
public class ArrayIterator implements Iterator {
    protected Object array;
    protected int i = -1;
    protected int n;

    public ArrayIterator(Object obj) {
        this.array = obj;
        this.n = Array.getLength(obj);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        int i = this.i + 1;
        int i2 = this.n;
        return i < i2 && i2 > 0;
    }

    @Override // java.util.Iterator
    public Object next() {
        int i = this.i + 1;
        this.i = i;
        if (i >= this.n) {
            throw new NoSuchElementException();
        }
        return Array.get(this.array, i);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

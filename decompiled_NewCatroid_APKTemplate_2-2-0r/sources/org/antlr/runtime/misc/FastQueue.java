package org.antlr.runtime.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes12.dex */
public class FastQueue<T> {
    protected List<T> data = new ArrayList();
    protected int p = 0;
    protected int range = -1;

    public void reset() {
        clear();
    }

    public void clear() {
        this.p = 0;
        this.data.clear();
    }

    public T remove() {
        T tElementAt = elementAt(0);
        int i = this.p + 1;
        this.p = i;
        if (i == this.data.size()) {
            clear();
        }
        return tElementAt;
    }

    public void add(T t) {
        this.data.add(t);
    }

    public int size() {
        return this.data.size() - this.p;
    }

    public int range() {
        return this.range;
    }

    public T head() {
        return elementAt(0);
    }

    public T elementAt(int i) {
        int i2 = this.p + i;
        if (i2 >= this.data.size()) {
            throw new NoSuchElementException("queue index " + i2 + " > last index " + (this.data.size() - 1));
        }
        if (i2 < 0) {
            throw new NoSuchElementException("queue index " + i2 + " < 0");
        }
        if (i2 > this.range) {
            this.range = i2;
        }
        return this.data.get(i2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        int i = 0;
        while (i < size) {
            sb.append(elementAt(i));
            i++;
            if (i < size) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

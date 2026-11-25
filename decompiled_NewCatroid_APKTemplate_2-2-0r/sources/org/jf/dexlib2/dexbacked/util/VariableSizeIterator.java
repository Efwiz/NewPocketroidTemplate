package org.jf.dexlib2.dexbacked.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;

/* JADX INFO: loaded from: classes6.dex */
public abstract class VariableSizeIterator<T> implements Iterator<T> {
    private int index;
    private final DexReader<?> reader;
    protected final int size;

    protected abstract T readNextItem(DexReader<?> dexReader, int i);

    protected VariableSizeIterator(DexBuffer dexBuffer, int i, int i2) {
        this.reader = dexBuffer.readerAt(i);
        this.size = i2;
    }

    protected VariableSizeIterator(DexReader<?> dexReader, int i) {
        this.reader = dexReader;
        this.size = i;
    }

    public int getReaderOffset() {
        return this.reader.getOffset();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < this.size;
    }

    @Override // java.util.Iterator
    public T next() {
        int i = this.index;
        if (i >= this.size) {
            throw new NoSuchElementException();
        }
        DexReader<?> dexReader = this.reader;
        this.index = i + 1;
        return readNextItem(dexReader, i);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

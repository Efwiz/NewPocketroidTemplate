package org.jf.dexlib2.dexbacked.util;

import java.util.Iterator;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.util.collection.AbstractIterator;

/* JADX INFO: loaded from: classes6.dex */
public abstract class VariableSizeLookaheadIterator<T> extends AbstractIterator<T> implements Iterator<T> {
    private final DexReader<?> reader;

    protected abstract T readNextItem(DexReader<?> dexReader);

    protected VariableSizeLookaheadIterator(DexBuffer dexBuffer, int i) {
        this.reader = dexBuffer.readerAt(i);
    }

    @Override // org.jf.util.collection.AbstractIterator
    public T computeNext() {
        return readNextItem(this.reader);
    }

    public final int getReaderOffset() {
        return this.reader.getOffset();
    }
}

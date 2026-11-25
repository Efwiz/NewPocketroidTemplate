package org.antlr.runtime.misc;

/* JADX INFO: loaded from: classes12.dex */
public abstract class LookaheadStream<T> extends FastQueue<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int UNINITIALIZED_EOF_ELEMENT_INDEX = Integer.MAX_VALUE;
    protected int lastMarker;
    protected T prevElement;
    protected int currentElementIndex = 0;
    public T eof = null;
    protected int markDepth = 0;

    public abstract boolean isEOF(T t);

    public abstract T nextElement();

    public void release(int i) {
    }

    @Override // org.antlr.runtime.misc.FastQueue
    public void reset() {
        super.reset();
        this.currentElementIndex = 0;
        this.p = 0;
        this.prevElement = null;
    }

    @Override // org.antlr.runtime.misc.FastQueue
    public T remove() {
        T tElementAt = elementAt(0);
        this.p++;
        if (this.p == this.data.size() && this.markDepth == 0) {
            this.prevElement = tElementAt;
            clear();
        }
        return tElementAt;
    }

    public void consume() {
        syncAhead(1);
        remove();
        this.currentElementIndex++;
    }

    protected void syncAhead(int i) {
        int size = (((this.p + i) - 1) - this.data.size()) + 1;
        if (size > 0) {
            fill(size);
        }
    }

    public void fill(int i) {
        for (int i2 = 1; i2 <= i; i2++) {
            T tNextElement = nextElement();
            if (isEOF(tNextElement)) {
                this.eof = tNextElement;
            }
            this.data.add(tNextElement);
        }
    }

    @Override // org.antlr.runtime.misc.FastQueue
    public int size() {
        throw new UnsupportedOperationException("streams are of unknown size");
    }

    public T LT(int i) {
        if (i == 0) {
            return null;
        }
        if (i < 0) {
            return LB(-i);
        }
        syncAhead(i);
        return (this.p + i) + (-1) > this.data.size() ? this.eof : elementAt(i - 1);
    }

    public int index() {
        return this.currentElementIndex;
    }

    public int mark() {
        this.markDepth++;
        int i = this.p;
        this.lastMarker = i;
        return i;
    }

    public void rewind(int i) {
        this.markDepth--;
        this.currentElementIndex -= this.p - i;
        this.p = i;
    }

    public void rewind() {
        int i = this.p;
        int i2 = this.lastMarker;
        this.currentElementIndex -= i - i2;
        this.p = i2;
    }

    public void seek(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("can't seek before the beginning of the input");
        }
        int i2 = this.currentElementIndex - i;
        if (this.p - i2 < 0) {
            throw new UnsupportedOperationException("can't seek before the beginning of this stream's buffer");
        }
        this.p -= i2;
        this.currentElementIndex = i;
    }

    protected T LB(int i) {
        int i2 = this.p - i;
        if (i2 == -1) {
            return this.prevElement;
        }
        if (i2 >= 0) {
            return this.data.get(i2);
        }
        if (i2 < -1) {
            throw new UnsupportedOperationException("can't look more than one token before the beginning of this stream's buffer");
        }
        throw new UnsupportedOperationException("can't look past the end of this stream's buffer using LB(int)");
    }
}

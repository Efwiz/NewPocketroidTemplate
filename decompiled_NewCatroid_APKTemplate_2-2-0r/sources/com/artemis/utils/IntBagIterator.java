package com.artemis.utils;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes8.dex */
public class IntBagIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected int index;
    protected int indexEnd;
    protected final IntBag intBag;
    protected State state;

    protected enum State {
        INIT,
        NEXT_CALLED,
        REMOVE_CALLED
    }

    public IntBagIterator(IntBag intBag) {
        this.intBag = intBag;
        this.index = -1;
        this.indexEnd = intBag != null ? intBag.size() : 0;
        this.state = State.INIT;
    }

    public IntBagIterator(IntBag intBag, int i) {
        this.intBag = intBag;
        this.index = i - 1;
        this.indexEnd = intBag != null ? intBag.size() : 0;
        this.state = State.INIT;
    }

    public IntBagIterator(IntBag intBag, int i, int i2) {
        this.intBag = intBag;
        this.index = i - 1;
        this.indexEnd = i + i2;
        int size = intBag.size();
        if (intBag != null) {
            int i3 = this.indexEnd;
            if (i3 < size) {
                size = i3;
            }
        } else {
            size = 0;
        }
        this.indexEnd = size;
        this.state = State.INIT;
    }

    public int getCurrentIndex() {
        if (this.state == State.NEXT_CALLED) {
            int i = this.index;
            int i2 = this.indexEnd;
            if (i < i2 && i2 <= this.intBag.size()) {
                return this.index;
            }
        }
        throw new IllegalStateException(getErrorMessage(this.index));
    }

    public boolean hasNext() {
        int i = this.index + 1;
        int i2 = this.indexEnd;
        return i < i2 && i2 <= this.intBag.size();
    }

    public int next() {
        int i = this.index + 1;
        int i2 = this.indexEnd;
        if (i < i2 && i2 <= this.intBag.size()) {
            this.index = i;
            this.state = State.NEXT_CALLED;
            return this.intBag.getData()[this.index];
        }
        throw new NoSuchElementException(getErrorMessage(i));
    }

    public void remove() {
        if (this.state == State.NEXT_CALLED) {
            int i = this.index;
            int i2 = this.indexEnd;
            if (i < i2 && i2 <= this.intBag.size()) {
                this.state = State.REMOVE_CALLED;
                this.intBag.removeIndex(this.index);
                this.index--;
                this.indexEnd--;
                return;
            }
        }
        throw new IllegalStateException(getErrorMessage(this.index));
    }

    private String getErrorMessage(int i) {
        return "Tried accessing element: " + i + "/" + this.indexEnd + "/" + this.intBag.size() + "/" + this.state.name();
    }
}

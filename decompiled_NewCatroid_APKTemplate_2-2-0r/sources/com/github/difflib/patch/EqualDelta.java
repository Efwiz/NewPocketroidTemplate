package com.github.difflib.patch;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EqualDelta<T> extends AbstractDelta<T> {
    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyFuzzyToAt(List<T> list, int i, int i2) {
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyTo(List<T> list) throws PatchFailedException {
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void restore(List<T> list) {
    }

    public EqualDelta(Chunk<T> chunk, Chunk<T> chunk2) {
        super(DeltaType.EQUAL, chunk, chunk2);
    }

    public String toString() {
        return "[EqualDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + "]";
    }

    @Override // com.github.difflib.patch.AbstractDelta
    public AbstractDelta<T> withChunks(Chunk<T> chunk, Chunk<T> chunk2) {
        return new EqualDelta(chunk, chunk2);
    }
}

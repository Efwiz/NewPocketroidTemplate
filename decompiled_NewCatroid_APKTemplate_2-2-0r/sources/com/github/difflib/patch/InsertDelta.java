package com.github.difflib.patch;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class InsertDelta<T> extends AbstractDelta<T> {
    public InsertDelta(Chunk<T> chunk, Chunk<T> chunk2) {
        super(DeltaType.INSERT, chunk, chunk2);
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyTo(List<T> list) throws PatchFailedException {
        int position = getSource().getPosition();
        List<T> lines = getTarget().getLines();
        for (int i = 0; i < lines.size(); i++) {
            list.add(position + i, lines.get(i));
        }
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void restore(List<T> list) {
        int position = getTarget().getPosition();
        int size = getTarget().size();
        for (int i = 0; i < size; i++) {
            list.remove(position);
        }
    }

    public String toString() {
        return "[InsertDelta, position: " + getSource().getPosition() + ", lines: " + getTarget().getLines() + "]";
    }

    @Override // com.github.difflib.patch.AbstractDelta
    public AbstractDelta<T> withChunks(Chunk<T> chunk, Chunk<T> chunk2) {
        return new InsertDelta(chunk, chunk2);
    }
}

package com.github.difflib.patch;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class DeleteDelta<T> extends AbstractDelta<T> {
    public DeleteDelta(Chunk<T> chunk, Chunk<T> chunk2) {
        super(DeltaType.DELETE, chunk, chunk2);
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyTo(List<T> list) throws PatchFailedException {
        int position = getSource().getPosition();
        int size = getSource().size();
        for (int i = 0; i < size; i++) {
            list.remove(position);
        }
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void restore(List<T> list) {
        int position = getTarget().getPosition();
        List<T> lines = getSource().getLines();
        for (int i = 0; i < lines.size(); i++) {
            list.add(position + i, lines.get(i));
        }
    }

    public String toString() {
        return "[DeleteDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + "]";
    }

    @Override // com.github.difflib.patch.AbstractDelta
    public AbstractDelta<T> withChunks(Chunk<T> chunk, Chunk<T> chunk2) {
        return new DeleteDelta(chunk, chunk2);
    }
}

package com.github.difflib.patch;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ChangeDelta<T> extends AbstractDelta<T> {
    public ChangeDelta(Chunk<T> chunk, Chunk<T> chunk2) {
        super(DeltaType.CHANGE, chunk, chunk2);
        Objects.requireNonNull(chunk, "source must not be null");
        Objects.requireNonNull(chunk2, "target must not be null");
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyTo(List<T> list) throws PatchFailedException {
        int position = getSource().getPosition();
        int size = getSource().size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            list.remove(position);
        }
        Iterator<T> it = getTarget().getLines().iterator();
        while (it.hasNext()) {
            list.add(position + i, it.next());
            i++;
        }
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void restore(List<T> list) {
        int position = getTarget().getPosition();
        int size = getTarget().size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            list.remove(position);
        }
        Iterator<T> it = getSource().getLines().iterator();
        while (it.hasNext()) {
            list.add(position + i, it.next());
            i++;
        }
    }

    @Override // com.github.difflib.patch.AbstractDelta
    protected void applyFuzzyToAt(List<T> list, int i, int i2) throws PatchFailedException {
        int size = getSource().size();
        for (int i3 = i; i3 < size - i; i3++) {
            list.remove(i2 + i);
        }
        Iterator<T> it = getTarget().getLines().subList(i, getTarget().size() - i).iterator();
        while (it.hasNext()) {
            list.add(i2 + i, it.next());
            i++;
        }
    }

    public String toString() {
        return "[ChangeDelta, position: " + getSource().getPosition() + ", lines: " + getSource().getLines() + " to " + getTarget().getLines() + "]";
    }

    @Override // com.github.difflib.patch.AbstractDelta
    public AbstractDelta<T> withChunks(Chunk<T> chunk, Chunk<T> chunk2) {
        return new ChangeDelta(chunk, chunk2);
    }
}

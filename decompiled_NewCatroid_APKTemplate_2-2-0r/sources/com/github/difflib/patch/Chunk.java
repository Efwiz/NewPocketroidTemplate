package com.github.difflib.patch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class Chunk<T> implements Serializable {
    private final List<Integer> changePosition;
    private List<T> lines;
    private final int position;

    public Chunk(int i, List<T> list, List<Integer> list2) {
        this.position = i;
        this.lines = new ArrayList(list);
        this.changePosition = list2 != null ? new ArrayList(list2) : null;
    }

    public Chunk(int i, List<T> list) {
        this(i, list, (List<Integer>) null);
    }

    public Chunk(int i, T[] tArr, List<Integer> list) {
        this.position = i;
        this.lines = Arrays.asList(tArr);
        this.changePosition = list != null ? new ArrayList(list) : null;
    }

    public Chunk(int i, T[] tArr) {
        this(i, tArr, (List<Integer>) null);
    }

    public VerifyChunk verifyChunk(List<T> list) throws PatchFailedException {
        return verifyChunk(list, 0, getPosition());
    }

    public VerifyChunk verifyChunk(List<T> list, int i, int i2) throws PatchFailedException {
        int size = size() - i;
        int size2 = (size() + i2) - 1;
        if (i2 + i > list.size() || size2 - i > list.size()) {
            return VerifyChunk.POSITION_OUT_OF_TARGET;
        }
        while (i < size) {
            if (!list.get(i2 + i).equals(this.lines.get(i))) {
                return VerifyChunk.CONTENT_DOES_NOT_MATCH_TARGET;
            }
            i++;
        }
        return VerifyChunk.OK;
    }

    public int getPosition() {
        return this.position;
    }

    public void setLines(List<T> list) {
        this.lines = list;
    }

    public List<T> getLines() {
        return this.lines;
    }

    public List<Integer> getChangePosition() {
        return this.changePosition;
    }

    public int size() {
        return this.lines.size();
    }

    public int last() {
        return (getPosition() + size()) - 1;
    }

    public int hashCode() {
        return Objects.hash(this.lines, Integer.valueOf(this.position), Integer.valueOf(size()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Chunk chunk = (Chunk) obj;
        List<T> list = this.lines;
        if (list == null) {
            if (chunk.lines != null) {
                return false;
            }
        } else if (!list.equals(chunk.lines)) {
            return false;
        }
        return this.position == chunk.position;
    }

    public String toString() {
        return "[position: " + this.position + ", size: " + size() + ", lines: " + this.lines + "]";
    }
}

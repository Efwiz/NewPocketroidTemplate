package io.github.rosemoe.sora.lang.folding;

import io.github.rosemoe.sora.util.IntPair;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class FoldingRegion {
    private List<FoldingRegion> children;
    private boolean collapsed;
    private long end;
    private long start;

    public FoldingRegion(int i, int i2, int i3, int i4) {
        this(IntPair.pack(i, i2), IntPair.pack(i3, i4));
        if (i > i3 || (i == i3 && i2 > i4)) {
            throw new IllegalArgumentException("start > end");
        }
    }

    FoldingRegion(long j, long j2) {
        this.start = j;
        this.end = j2;
    }

    public void setCollapsed(boolean z) {
        this.collapsed = z;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public int getStartLine() {
        return IntPair.getFirst(this.start);
    }

    public int getStartColumn() {
        return IntPair.getSecond(this.start);
    }

    public int getEndLine() {
        return IntPair.getFirst(this.end);
    }

    public int getEndColumn() {
        return IntPair.getSecond(this.end);
    }

    public FoldingRegion createChild(int i, int i2, int i3, int i4) {
        if (i < getStartLine() || (i == getStartLine() && i2 < getStartColumn())) {
            throw new IllegalArgumentException("child start is before parent start");
        }
        if (i3 > getEndLine() || (i3 == getEndLine() && i4 > getEndColumn())) {
            throw new IllegalArgumentException("child end is beyond parent end");
        }
        FoldingRegion foldingRegion = new FoldingRegion(i, i2, i3, i4);
        this.children.add(foldingRegion);
        return foldingRegion;
    }
}

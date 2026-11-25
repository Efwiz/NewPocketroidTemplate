package io.github.rosemoe.sora.text.bidi;

import io.github.rosemoe.sora.util.IntPair;

/* JADX INFO: loaded from: classes11.dex */
public class Directions {
    private int length;
    private long[] runs;

    public Directions(long[] jArr, int i) {
        this.runs = jArr;
        this.length = i;
    }

    public void setData(long[] jArr, int i) {
        this.runs = jArr;
        this.length = i;
    }

    public void setLength(int i) {
        this.length = i;
    }

    public int getLength() {
        return this.length;
    }

    public int getRunCount() {
        return this.runs.length;
    }

    public int getRunStart(int i) {
        return IntPair.getFirst(this.runs[i]);
    }

    public int getRunEnd(int i) {
        return i == this.runs.length + (-1) ? this.length : getRunStart(i + 1);
    }

    public boolean isRunRtl(int i) {
        return IntPair.getSecond(this.runs[i]) == 1;
    }
}

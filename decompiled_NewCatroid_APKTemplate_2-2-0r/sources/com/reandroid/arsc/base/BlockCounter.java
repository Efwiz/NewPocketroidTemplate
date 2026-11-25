package com.reandroid.arsc.base;

/* JADX INFO: loaded from: classes10.dex */
public class BlockCounter {
    private int COUNT;
    public final Block END;
    public boolean FOUND;

    void onCountAdded(int i) {
    }

    public void setCurrent(Block block) {
    }

    public BlockCounter(Block block) {
        this.END = block;
    }

    public void addCount(int i) {
        if (this.FOUND || i == 0) {
            return;
        }
        int i2 = this.COUNT + i;
        this.COUNT = i2;
        onCountAdded(i2);
    }

    public int getCountValue() {
        return this.COUNT;
    }

    public String toString() {
        if (this.FOUND) {
            return "FOUND=" + this.COUNT;
        }
        return String.valueOf(this.COUNT);
    }
}

package io.github.rosemoe.sora.lang.completion.snippet;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SnippetItem implements Cloneable {
    private int end;
    private int start;

    @Override // 
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract SnippetItem mo2801clone();

    public SnippetItem() {
        this(0);
    }

    public SnippetItem(int i) {
        this(i, i);
    }

    public SnippetItem(int i, int i2) {
        setIndex(i, i2);
    }

    public int getStartIndex() {
        return this.start;
    }

    public int getEndIndex() {
        return this.end;
    }

    public void setIndex(int i) {
        setIndex(i, i);
    }

    public void setIndex(int i, int i2) {
        this.start = i;
        this.end = i2;
    }

    public void shiftIndex(int i) {
        this.start += i;
        this.end += i;
    }
}

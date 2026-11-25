package io.github.rosemoe.sora.text;

import io.github.rosemoe.sora.util.IntPair;

/* JADX INFO: loaded from: classes11.dex */
public final class Cursor {
    public static final int DIRECTION_LTR = 1;
    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_RTL = 2;
    private CharPosition cache0;
    private CharPosition cache1;
    private CharPosition cache2;
    private final Content content;
    private final CachedIndexer indexer;
    private int selDirection = 0;
    private CharPosition leftSel = new CharPosition().toBOF();
    private CharPosition rightSel = new CharPosition().toBOF();

    private static boolean isWhitespace(char c) {
        return c == '\t' || c == ' ';
    }

    public Cursor(Content content) {
        this.content = content;
        this.indexer = new CachedIndexer(content);
    }

    public void set(int i, int i2) {
        setLeft(i, i2);
        setRight(i, i2);
    }

    public void setLeft(int i, int i2) {
        this.leftSel = this.indexer.getCharPosition(i, i2).fromThis();
    }

    public void setRight(int i, int i2) {
        this.rightSel = this.indexer.getCharPosition(i, i2).fromThis();
    }

    public int getLeftLine() {
        return this.leftSel.getLine();
    }

    public int getLeftColumn() {
        return this.leftSel.getColumn();
    }

    public int getRightLine() {
        return this.rightSel.getLine();
    }

    public int getRightColumn() {
        return this.rightSel.getColumn();
    }

    public boolean isInSelectedRegion(int i, int i2) {
        boolean z = false;
        if (i < getLeftLine() || i > getRightLine()) {
            return false;
        }
        boolean z2 = i != getLeftLine() || i2 >= getLeftColumn();
        if (i != getRightLine()) {
            return z2;
        }
        if (z2 && i2 < getRightColumn()) {
            z = true;
        }
        return z;
    }

    public int getLeft() {
        return this.leftSel.index;
    }

    public int getRight() {
        return this.rightSel.index;
    }

    public void updateCache(int i) {
        this.indexer.getCharIndex(i, 0);
    }

    public CachedIndexer getIndexer() {
        return this.indexer;
    }

    public boolean isSelected() {
        return this.leftSel.index != this.rightSel.index;
    }

    public void setSelectionDirection(int i) {
        this.selDirection = i;
    }

    public int getSelectionDirection() {
        return this.selDirection;
    }

    public long getLeftOf(long j) {
        int first = IntPair.getFirst(j);
        int second = IntPair.getSecond(j);
        int curPosLeft = TextLayoutHelper.get().getCurPosLeft(second, this.content.getLine(first));
        if (curPosLeft != second || second != 0) {
            return IntPair.pack(first, curPosLeft);
        }
        if (first == 0) {
            return 0L;
        }
        int i = first - 1;
        return IntPair.pack(i, this.content.getColumnCount(i));
    }

    public long getRightOf(long j) {
        int first = IntPair.getFirst(j);
        int second = IntPair.getSecond(j);
        int columnCount = this.content.getColumnCount(first);
        int curPosRight = TextLayoutHelper.get().getCurPosRight(second, this.content.getLine(first));
        if (curPosRight == columnCount && second == curPosRight) {
            int i = first + 1;
            if (i == this.content.getLineCount()) {
                return IntPair.pack(first, columnCount);
            }
            return IntPair.pack(i, 0);
        }
        return IntPair.pack(first, curPosRight);
    }

    public CharPosition left() {
        return this.leftSel.fromThis();
    }

    public CharPosition right() {
        return this.rightSel.fromThis();
    }

    public TextRange getRange() {
        return new TextRange(left(), right());
    }

    void beforeInsert(int i, int i2) {
        this.cache0 = this.indexer.getCharPosition(i, i2).fromThis();
    }

    void beforeDelete(int i, int i2, int i3, int i4) {
        this.cache1 = this.indexer.getCharPosition(i, i2).fromThis();
        this.cache2 = this.indexer.getCharPosition(i3, i4).fromThis();
    }

    void beforeReplace() {
        this.indexer.beforeReplace(this.content);
    }

    void afterInsert(int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.indexer.afterInsert(this.content, i, i2, i3, i4, charSequence);
        int index = this.cache0.getIndex();
        if (getLeft() >= index) {
            this.leftSel = this.indexer.getCharPosition(getLeft() + charSequence.length()).fromThis();
        }
        if (getRight() >= index) {
            this.rightSel = this.indexer.getCharPosition(getRight() + charSequence.length()).fromThis();
        }
    }

    void afterDelete(int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.indexer.afterDelete(this.content, i, i2, i3, i4, charSequence);
        int index = this.cache1.getIndex();
        int index2 = this.cache2.getIndex();
        int left = getLeft();
        int right = getRight();
        if (index > right) {
            return;
        }
        int i5 = index2 - index;
        int iMax = left - Math.max(0, Math.min(left - index, i5));
        int iMax2 = right - Math.max(0, Math.min(right - index, i5));
        this.leftSel = this.indexer.getCharPosition(iMax).fromThis();
        this.rightSel = this.indexer.getCharPosition(iMax2).fromThis();
    }
}

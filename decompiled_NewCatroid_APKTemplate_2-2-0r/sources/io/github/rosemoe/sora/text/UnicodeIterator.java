package io.github.rosemoe.sora.text;

import io.github.rosemoe.sora.util.ObjectPool;

/* JADX INFO: loaded from: classes11.dex */
public class UnicodeIterator {
    private static final ObjectPool<UnicodeIterator> sPool = new ObjectPool<UnicodeIterator>() { // from class: io.github.rosemoe.sora.text.UnicodeIterator.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.github.rosemoe.sora.util.ObjectPool
        public UnicodeIterator allocateNew() {
            return new UnicodeIterator();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.github.rosemoe.sora.util.ObjectPool
        public void onRecycleObject(UnicodeIterator unicodeIterator) {
            unicodeIterator.text = null;
        }
    };
    private int codePoint;
    private int end;
    private int limit;
    private int start;
    private CharSequence text;

    private UnicodeIterator() {
    }

    public static UnicodeIterator obtain(CharSequence charSequence, int i, int i2) {
        UnicodeIterator unicodeIteratorObtain = sPool.obtain();
        unicodeIteratorObtain.set(charSequence, i, i2);
        return unicodeIteratorObtain;
    }

    public void recycle() {
        sPool.recycle(this);
    }

    public void set(CharSequence charSequence, int i, int i2) {
        if ((i | i2 | (i2 - i) | (charSequence.length() - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.text = charSequence;
        this.end = i;
        this.start = i;
        this.limit = i2;
    }

    public boolean hasNext() {
        return this.end < this.limit;
    }

    public int nextCodePoint() {
        int i;
        int i2 = this.end;
        this.start = i2;
        if (i2 >= this.limit) {
            this.codePoint = 0;
        } else {
            this.end = i2 + 1;
            char cCharAt = this.text.charAt(i2);
            if (Character.isHighSurrogate(cCharAt) && (i = this.end) < this.limit) {
                this.codePoint = Character.toCodePoint(cCharAt, this.text.charAt(i));
                this.end++;
            } else {
                this.codePoint = cCharAt;
            }
        }
        return this.codePoint;
    }

    public int getCodePoint() {
        return this.codePoint;
    }

    public int getStartIndex() {
        return this.start;
    }

    public int getEndIndex() {
        return this.end;
    }
}

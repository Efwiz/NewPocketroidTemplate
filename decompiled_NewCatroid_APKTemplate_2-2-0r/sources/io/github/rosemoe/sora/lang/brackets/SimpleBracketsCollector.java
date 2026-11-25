package io.github.rosemoe.sora.lang.brackets;

import android.util.SparseIntArray;
import io.github.rosemoe.sora.text.Content;

/* JADX INFO: loaded from: classes11.dex */
public class SimpleBracketsCollector implements BracketsProvider {
    private final SparseIntArray mapping = new SparseIntArray();

    public void add(int i, int i2) {
        int i3 = i + 1;
        int i4 = i2 + 1;
        this.mapping.put(i3, i4);
        this.mapping.put(i4, i3);
    }

    public void clear() {
        this.mapping.clear();
    }

    private PairedBracket getForIndex(int i) {
        int i2 = this.mapping.get(i + 1) - 1;
        if (i2 <= i) {
            i2 = i;
            i = i2;
        }
        if (i != -1) {
            return new PairedBracket(i2, i);
        }
        return null;
    }

    @Override // io.github.rosemoe.sora.lang.brackets.BracketsProvider
    public PairedBracket getPairedBracketAt(Content content, int i) {
        int i2 = i - 1;
        PairedBracket forIndex = i2 >= 0 ? getForIndex(i2) : null;
        return forIndex == null ? getForIndex(i) : forIndex;
    }
}

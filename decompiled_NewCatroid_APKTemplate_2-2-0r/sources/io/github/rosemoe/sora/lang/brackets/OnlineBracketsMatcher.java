package io.github.rosemoe.sora.lang.brackets;

import io.github.rosemoe.sora.text.Content;

/* JADX INFO: loaded from: classes11.dex */
public class OnlineBracketsMatcher implements BracketsProvider {
    private final int limit;
    private final char[] pairs;

    public OnlineBracketsMatcher(char[] cArr, int i) {
        if ((cArr.length & 1) != 0) {
            throw new IllegalArgumentException("pairs must have even length");
        }
        this.pairs = cArr;
        this.limit = i;
    }

    private int findIndex(char c) {
        int i = 0;
        while (true) {
            char[] cArr = this.pairs;
            if (i >= cArr.length) {
                return -1;
            }
            if (c == cArr[i]) {
                return i;
            }
            i++;
        }
    }

    private PairedBracket tryComputePaired(Content content, int i) {
        char cCharAt = content.charAt(i);
        int iFindIndex = findIndex(cCharAt);
        if (iFindIndex == -1) {
            return null;
        }
        char c = this.pairs[iFindIndex ^ 1];
        int i2 = 0;
        if ((iFindIndex & 1) == 0) {
            for (int i3 = i + 1; i3 < content.length() && i3 - i < this.limit; i3++) {
                char cCharAt2 = content.charAt(i3);
                if (cCharAt2 == c) {
                    if (i2 <= 0) {
                        return new PairedBracket(i, i3);
                    }
                    i2--;
                } else if (cCharAt2 == cCharAt) {
                    i2++;
                }
            }
            return null;
        }
        for (int i4 = i - 1; i4 >= 0 && i - i4 < this.limit; i4--) {
            char cCharAt3 = content.charAt(i4);
            if (cCharAt3 == c) {
                if (i2 <= 0) {
                    return new PairedBracket(i4, i);
                }
                i2--;
            } else if (cCharAt3 == cCharAt) {
                i2++;
            }
        }
        return null;
    }

    @Override // io.github.rosemoe.sora.lang.brackets.BracketsProvider
    public PairedBracket getPairedBracketAt(Content content, int i) {
        PairedBracket pairedBracketTryComputePaired = i > 0 ? tryComputePaired(content, i - 1) : null;
        return pairedBracketTryComputePaired == null ? tryComputePaired(content, i) : pairedBracketTryComputePaired;
    }
}

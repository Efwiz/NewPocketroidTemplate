package io.github.rosemoe.sora.text;

import android.icu.text.BreakIterator;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.MyCharacter;

/* JADX INFO: loaded from: classes11.dex */
public class ICUUtils {
    public static long getWordRange(CharSequence charSequence, int i, boolean z) {
        if (z) {
            BreakIterator wordInstance = BreakIterator.getWordInstance();
            wordInstance.setText(new CharSequenceIterator(charSequence));
            int iFollowing = wordInstance.following(i);
            int iPrevious = wordInstance.previous();
            if (i >= iPrevious && i <= iFollowing) {
                return IntPair.pack(iPrevious, iFollowing);
            }
            return getWordRangeFallback(charSequence, i);
        }
        return getWordRangeFallback(charSequence, i);
    }

    public static long getWordRangeFallback(CharSequence charSequence, int i) {
        int i2 = i;
        while (i2 < charSequence.length() && MyCharacter.isJavaIdentifierPart(charSequence.charAt(i2))) {
            i2++;
        }
        if (i2 > i) {
            while (i > 0 && MyCharacter.isJavaIdentifierPart(charSequence.charAt(i - 1))) {
                i--;
            }
        }
        return IntPair.pack(i, i2);
    }
}

package io.github.rosemoe.sora.text.bidi;

import android.text.TextUtils;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.TemporaryCharBuffer;
import java.text.Bidi;

/* JADX INFO: loaded from: classes11.dex */
public class TextBidi {
    public static boolean couldAffectRtl(char c) {
        return (1424 <= c && c <= 2303) || c == 8206 || c == 8207 || (8234 <= c && c <= 8238) || ((8294 <= c && c <= 8297) || ((55296 <= c && c <= 57343) || ((64285 <= c && c <= 65023) || (65136 <= c && c <= 65278))));
    }

    public static Directions getDirections(CharSequence charSequence) {
        int length = charSequence.length();
        if (doesNotNeedBidi(charSequence)) {
            return new Directions(new long[]{IntPair.pack(0, 0)}, length);
        }
        char[] cArrObtain = TemporaryCharBuffer.obtain(length);
        TextUtils.getChars(charSequence, 0, length, cArrObtain, 0);
        Bidi bidi = new Bidi(cArrObtain, 0, null, 0, charSequence.length(), 0);
        int runCount = bidi.getRunCount();
        long[] jArr = new long[runCount];
        for (int i = 0; i < runCount; i++) {
            jArr[i] = IntPair.pack(bidi.getRunStart(i), bidi.getRunLevel(i));
        }
        TemporaryCharBuffer.recycle(cArrObtain);
        return new Directions(jArr, length);
    }

    public static boolean doesNotNeedBidi(CharSequence charSequence) {
        if (charSequence instanceof BidiRequirementChecker) {
            return !((BidiRequirementChecker) charSequence).mayNeedBidi();
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (couldAffectRtl(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

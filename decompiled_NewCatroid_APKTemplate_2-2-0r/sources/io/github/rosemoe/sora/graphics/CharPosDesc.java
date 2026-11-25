package io.github.rosemoe.sora.graphics;

import io.github.rosemoe.sora.util.IntPair;

/* JADX INFO: loaded from: classes11.dex */
public class CharPosDesc {
    private CharPosDesc() {
    }

    public static long make(int i, float f) {
        return IntPair.pack(i, Float.floatToRawIntBits(f));
    }

    public static int getTextOffset(long j) {
        return IntPair.getFirst(j);
    }

    public static float getPixelWidthOrOffset(long j) {
        return Float.intBitsToFloat(IntPair.getSecond(j));
    }
}

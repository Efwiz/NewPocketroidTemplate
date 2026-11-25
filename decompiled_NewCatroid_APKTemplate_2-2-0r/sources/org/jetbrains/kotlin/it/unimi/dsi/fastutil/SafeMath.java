package org.jetbrains.kotlin.it.unimi.dsi.fastutil;

/* JADX INFO: loaded from: classes5.dex */
public final class SafeMath {
    public static char safeIntToChar(int i) {
        if (i < 0 || 65535 < i) {
            throw new IllegalArgumentException(i + " can't be represented as char");
        }
        return (char) i;
    }

    public static byte safeIntToByte(int i) {
        if (i < -128 || 127 < i) {
            throw new IllegalArgumentException(i + " can't be represented as byte (out of range)");
        }
        return (byte) i;
    }

    public static short safeIntToShort(int i) {
        if (i < -32768 || 32767 < i) {
            throw new IllegalArgumentException(i + " can't be represented as short (out of range)");
        }
        return (short) i;
    }

    public static float safeDoubleToFloat(double d) {
        if (Double.isNaN(d)) {
            return Float.NaN;
        }
        if (Double.isInfinite(d)) {
            return d < 0.0d ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        }
        if (d < -3.4028234663852886E38d || 3.4028234663852886E38d < d) {
            throw new IllegalArgumentException(d + " can't be represented as float (out of range)");
        }
        float f = (float) d;
        if (f == d) {
            return f;
        }
        throw new IllegalArgumentException(d + " can't be represented as float (imprecise)");
    }
}

package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

/* JADX INFO: loaded from: classes3.dex */
public class ScriptRuntime {
    public static double NaN = Double.NaN;

    static double stringToNumber(String str, int i, int i2) {
        char c;
        char c2;
        int nextBinaryDigit;
        int i3;
        int length = str.length();
        char c3 = i2 < 10 ? (char) (i2 + 47) : '9';
        if (i2 > 10) {
            c = (char) (i2 + 87);
            c2 = (char) (i2 + 55);
        } else {
            c = 'a';
            c2 = 'A';
        }
        int i4 = i;
        double d = 0.0d;
        while (i4 < length) {
            char cCharAt = str.charAt(i4);
            if ('0' <= cCharAt && cCharAt <= c3) {
                i3 = cCharAt - '0';
            } else if ('a' <= cCharAt && cCharAt < c) {
                i3 = cCharAt - 'W';
            } else {
                if ('A' > cCharAt || cCharAt >= c2) {
                    break;
                }
                i3 = cCharAt - '7';
            }
            d = (d * ((double) i2)) + ((double) i3);
            i4++;
        }
        if (i == i4) {
            return NaN;
        }
        if (d >= 9.007199254740992E15d) {
            if (i2 == 10) {
                try {
                    return Double.valueOf(str.substring(i, i4)).doubleValue();
                } catch (NumberFormatException unused) {
                    return NaN;
                }
            }
            if (i2 == 2 || i2 == 4 || i2 == 8 || i2 == 16 || i2 == 32) {
                BinaryDigitReader binaryDigitReader = new BinaryDigitReader(i2, str, i, i4);
                do {
                    nextBinaryDigit = binaryDigitReader.getNextBinaryDigit();
                } while (nextBinaryDigit == 0);
                if (nextBinaryDigit != 1) {
                    return 0.0d;
                }
                double d2 = 1.0d;
                for (int i5 = 52; i5 != 0; i5--) {
                    nextBinaryDigit = binaryDigitReader.getNextBinaryDigit();
                    if (nextBinaryDigit < 0) {
                        return d2;
                    }
                    d2 = (d2 * 2.0d) + ((double) nextBinaryDigit);
                }
                int nextBinaryDigit2 = binaryDigitReader.getNextBinaryDigit();
                if (nextBinaryDigit2 < 0) {
                    return d2;
                }
                int i6 = 0;
                double d3 = 2.0d;
                while (true) {
                    int nextBinaryDigit3 = binaryDigitReader.getNextBinaryDigit();
                    if (nextBinaryDigit3 < 0) {
                        return (d2 + ((double) ((nextBinaryDigit | i6) & nextBinaryDigit2))) * d3;
                    }
                    i6 |= nextBinaryDigit3;
                    d3 *= 2.0d;
                }
            }
        }
        return d;
    }
}

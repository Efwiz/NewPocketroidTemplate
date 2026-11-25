package io.github.rosemoe.sora.util;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes11.dex */
public class Numbers {
    static final char[] DigitTens = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, ExternalAnnotationProvider.NONNULL, '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
    static final char[] DigitOnes = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9'};

    public static int clearBit(int i, int i2) {
        return (i & i2) != 0 ? i ^ i2 : i;
    }

    public static int stringSize(int i) {
        int i2;
        if (i >= 0) {
            i = -i;
            i2 = 0;
        } else {
            i2 = 1;
        }
        int i3 = -10;
        for (int i4 = 1; i4 < 10; i4++) {
            if (i > i3) {
                return i4 + i2;
            }
            i3 *= 10;
        }
        return i2 + 10;
    }

    public static void getChars(int i, int i2, char[] cArr) {
        boolean z = i < 0;
        if (!z) {
            i = -i;
        }
        while (i <= -100) {
            int i3 = i / 100;
            int i4 = (i3 * 100) - i;
            cArr[i2 - 1] = DigitOnes[i4];
            i2 -= 2;
            cArr[i2] = DigitTens[i4];
            i = i3;
        }
        int i5 = i2 - 1;
        int i6 = -i;
        cArr[i5] = DigitOnes[i6];
        if (i < -9) {
            i5 = i2 - 2;
            cArr[i5] = DigitTens[i6];
        }
        if (z) {
            cArr[i5 - 1] = '-';
        }
    }

    public static int coerceIn(int i, int i2, int i3) {
        return Math.max(i2, Math.min(i3, i));
    }
}

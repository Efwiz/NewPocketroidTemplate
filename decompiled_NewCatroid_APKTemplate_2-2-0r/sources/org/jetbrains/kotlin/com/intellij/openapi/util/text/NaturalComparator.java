package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import java.util.Comparator;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes3.dex */
public final class NaturalComparator implements Comparator<String> {
    public static final Comparator<String> INSTANCE = new NaturalComparator();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3) {
            objArr[0] = "s2";
        } else {
            objArr[0] = "s1";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/NaturalComparator";
        if (i == 2 || i == 3) {
            objArr[2] = "compareCharRange";
        } else {
            objArr[2] = "naturalCompare";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private NaturalComparator() {
    }

    @Override // java.util.Comparator
    public int compare(String str, String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return naturalCompare(str, str2, str.length(), str2.length(), true);
    }

    private static int naturalCompare(String str, String str2, int i, int i2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < i && i4 < i2) {
            char cCharAt = str.charAt(i3);
            char cCharAt2 = str2.charAt(i4);
            if ((StringUtil.isDecimalDigit(cCharAt) || cCharAt == ' ') && (StringUtil.isDecimalDigit(cCharAt2) || cCharAt2 == ' ')) {
                int iSkipChar = skipChar(str, skipChar(str, i3, i, io.netty.util.internal.StringUtil.SPACE), i, ExternalAnnotationProvider.NULLABLE);
                int iSkipChar2 = skipChar(str2, skipChar(str2, i4, i2, io.netty.util.internal.StringUtil.SPACE), i2, ExternalAnnotationProvider.NULLABLE);
                int iSkipDigits = skipDigits(str, iSkipChar, i);
                int iSkipDigits2 = skipDigits(str2, iSkipChar2, i2);
                int i5 = (iSkipDigits - iSkipChar) - (iSkipDigits2 - iSkipChar2);
                if (i5 != 0) {
                    return i5;
                }
                int iCompareCharRange = compareCharRange(str, str2, iSkipChar, iSkipChar2, iSkipDigits);
                if (iCompareCharRange != 0) {
                    return iCompareCharRange;
                }
                int i6 = (iSkipDigits - i3) - (iSkipDigits2 - i4);
                if (i6 != 0) {
                    return i6;
                }
                int iCompareCharRange2 = compareCharRange(str, str2, i3, i4, iSkipChar);
                if (iCompareCharRange2 != 0) {
                    return iCompareCharRange2;
                }
                i3 = iSkipDigits - 1;
                i4 = iSkipDigits2 - 1;
            } else {
                int iCompareChars = compareChars(cCharAt, cCharAt2, z);
                if (iCompareChars != 0) {
                    return iCompareChars;
                }
            }
            i3++;
            i4++;
        }
        if (i3 < i) {
            return 1;
        }
        if (i4 < i2) {
            return -1;
        }
        if (i != i2) {
            return i - i2;
        }
        if (z) {
            return naturalCompare(str, str2, i, i2, false);
        }
        return 0;
    }

    private static int compareCharRange(String str, String str2, int i, int i2, int i3) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(3);
        }
        while (i < i3) {
            int iCharAt = str.charAt(i) - str2.charAt(i2);
            if (iCharAt != 0) {
                return iCharAt;
            }
            i++;
            i2++;
        }
        return 0;
    }

    private static int compareChars(char c, char c2, boolean z) {
        if (c == ' ' && c2 > ' ' && c2 < '0') {
            return 1;
        }
        if (c2 != ' ' || c <= ' ' || c >= '0') {
            return StringUtil.compare(c, c2, z);
        }
        return -1;
    }

    private static int skipDigits(String str, int i, int i2) {
        while (i < i2 && StringUtil.isDecimalDigit(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private static int skipChar(String str, int i, int i2, char c) {
        while (i < i2 && str.charAt(i) == c) {
            i++;
        }
        return i;
    }
}

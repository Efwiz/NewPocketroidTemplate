package com.reandroid.common;

import com.reandroid.utils.collection.CollectionUtil;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes10.dex */
public class SymbolGenerationUtils {
    private static final char[] CHARACTERS;
    private static final int LOWERCASE_AND_SUFFIX;
    private static final Set<String> RESERVED_NAMES;
    private static final int SUFFIX_LENGTH;
    private static final int TOTAL_CHARACTERS;

    public static String generateLowercase(int i) {
        return generate(i, false);
    }

    public static String generateMixedCase(int i) {
        return generate(i, true);
    }

    public static String generate(int i, boolean z) {
        int i2 = i + 1;
        int i3 = z ? TOTAL_CHARACTERS : LOWERCASE_AND_SUFFIX;
        int i4 = i3 - SUFFIX_LENGTH;
        int i5 = i2;
        int i6 = 1;
        for (int i7 = i4; i5 > i7; i7 = i3) {
            i5 = (i5 - 1) / i7;
            i6++;
        }
        char[] cArr = new char[i6];
        int i8 = SUFFIX_LENGTH;
        int i9 = i2;
        int i10 = 0;
        while (i9 > i4) {
            int i11 = i9 - 1;
            cArr[i10] = CHARACTERS[(i11 % i4) + i8];
            i9 = i11 / i4;
            i4 = i3;
            i8 = 0;
            i10++;
        }
        cArr[i10] = CHARACTERS[(i9 - 1) + i8];
        String str = new String(cArr);
        return isReserved(str) ? generate(i2, z) : str;
    }

    private static boolean isReserved(String str) {
        return str.length() > 1 && RESERVED_NAMES.contains(str);
    }

    static {
        char[] charArray = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        CHARACTERS = charArray;
        int length = charArray.length;
        TOTAL_CHARACTERS = length;
        LOWERCASE_AND_SUFFIX = length - 26;
        SUFFIX_LENGTH = 10;
        RESERVED_NAMES = CollectionUtil.newHashSet("boolean", "byte", PsiKeyword.CHAR, "double", "float", "int", "long", "short", PsiKeyword.VOID, "it", "by", "class");
    }
}

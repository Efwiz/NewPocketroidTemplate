package com.android.tools.r8.utils;

import com.android.tools.r8.internal.OU;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.utils.a3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3869a3 {
    public static HashSet a = null;
    private static final char[] b;
    private static final int c;
    private static final int d;
    static final /* synthetic */ boolean e = true;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.android.tools.r8.utils.a3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final a a = new a("USE_MIXED_CASE", 0);
        public static final a b = new a("DONT_USE_MIXED_CASE", 1);

        private a(String str, int i) {
            super(str, i);
        }
    }

    public static String a(int i, a aVar) {
        char[] cArr = Y2.a;
        int i2 = aVar == a.a ? c : d;
        int i3 = i2 - 10;
        int i4 = i;
        int i5 = 1;
        for (int i6 = i3; i4 > i6; i6 = i2) {
            i4 = (i4 - 1) / i6;
            i5++;
        }
        char[] cArrCopyOfRange = Arrays.copyOfRange(cArr, 0, i5);
        int i7 = 10;
        int i8 = 0;
        while (i > i3) {
            int i9 = i - 1;
            cArrCopyOfRange[i8] = b[(i9 % i3) + i7];
            i = i9 / i3;
            i3 = i2;
            i7 = 0;
            i8++;
        }
        int i10 = i8 + 1;
        cArrCopyOfRange[i8] = b[(i - 1) + i7];
        boolean z = e;
        if (!z && i10 != cArrCopyOfRange.length) {
            throw new AssertionError();
        }
        if (z || !Character.isDigit(cArrCopyOfRange[0])) {
            return new String(cArrCopyOfRange);
        }
        throw new AssertionError();
    }

    static {
        String[] strArr = {"boolean", "byte", PsiKeyword.CHAR, "double", "float", "int", "long", "short", PsiKeyword.VOID, "it", "by"};
        HashSet hashSetA = OU.a(11);
        Collections.addAll(hashSetA, strArr);
        a = hashSetA;
        char[] charArray = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        b = charArray;
        int length = charArray.length;
        c = length;
        d = length - 26;
    }
}

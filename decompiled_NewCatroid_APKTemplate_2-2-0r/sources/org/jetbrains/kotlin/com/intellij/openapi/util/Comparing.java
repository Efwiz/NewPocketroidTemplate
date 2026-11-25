package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public final class Comparing {
    public static int compare(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> boolean equal(T t, T t2) {
        if (t == t2) {
            return true;
        }
        if (t == 0 || t2 == 0) {
            return false;
        }
        if ((t instanceof Object[]) && (t2 instanceof Object[])) {
            return Arrays.equals((Object[]) t, (Object[]) t2);
        }
        if ((t instanceof CharSequence) && (t2 instanceof CharSequence)) {
            return equal((CharSequence) t, (CharSequence) t2, true);
        }
        return t.equals(t2);
    }

    public static boolean equal(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtilRt.equal(charSequence, charSequence2, true);
    }

    public static boolean equal(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return StringUtilRt.equal(charSequence, charSequence2, z);
    }

    public static boolean equal(String str, String str2, boolean z) {
        if (str == null) {
            return str2 == null;
        }
        return z ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public static boolean strEqual(String str, String str2) {
        return strEqual(str, str2, true);
    }

    public static boolean strEqual(String str, String str2, boolean z) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        return equal(str, str2, z);
    }

    public static int hashcode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public static <T extends Comparable<? super T>> int compare(T t, T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return -1;
        }
        if (t2 == null) {
            return 1;
        }
        return t.compareTo(t2);
    }
}

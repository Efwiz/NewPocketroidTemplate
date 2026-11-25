package org.jetbrains.kotlin.com.intellij.util;

import java.io.File;
import java.util.Collection;

/* JADX INFO: loaded from: classes3.dex */
public final class ArrayUtilRt {
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Collection[] EMPTY_COLLECTION_ARRAY = new Collection[0];
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "src";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ArrayUtilRt";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ArrayUtilRt";
        } else {
            objArr[1] = "toStringArray";
        }
        if (i == 1) {
            objArr[2] = "find";
        } else if (i == 2) {
            objArr[2] = "indexOf";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static String[] toStringArray(Collection<String> collection) {
        String[] strArr = (collection == null || collection.isEmpty()) ? EMPTY_STRING_ARRAY : (String[]) collection.toArray(EMPTY_STRING_ARRAY);
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        return strArr;
    }

    public static <T> int find(T[] tArr, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(1);
        }
        return indexOf(tArr, t, 0, tArr.length);
    }

    public static <T> int indexOf(T[] tArr, T t, int i, int i2) {
        if (tArr == null) {
            $$$reportNull$$$0(2);
        }
        if (t == null) {
            while (i < i2) {
                if (tArr[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        while (i < i2) {
            if (t.equals(tArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }
}

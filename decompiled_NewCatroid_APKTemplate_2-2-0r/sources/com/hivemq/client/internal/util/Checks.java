package com.hivemq.client.internal.util;

import com.hivemq.client.internal.util.collections.ImmutableList;

/* JADX INFO: loaded from: classes10.dex */
public final class Checks {
    public static <T> T notNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str + " must not be null.");
    }

    public static String notEmpty(String str, String str2) {
        notNull(str, str2);
        if (str.isEmpty()) {
            throw new IllegalArgumentException(str2 + " must be at least one character long.");
        }
        return str;
    }

    public static <S, T extends S> T notImplemented(S s, Class<T> cls, String str) {
        return (T) notImplementedInternal(notNull(s, str), cls, str);
    }

    public static <S, T extends S> T notImplementedOrNull(S s, Class<T> cls, String str) {
        if (s == null) {
            return null;
        }
        return (T) notImplementedInternal(s, cls, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <S, T extends S> T notImplementedInternal(S s, Class<T> cls, String str) {
        if (cls.isInstance(s)) {
            return s;
        }
        throw new IllegalArgumentException(str + " must not be implemented by the user, but was implemented by " + s.getClass().getTypeName() + ".");
    }

    public static <T> T[] elementsNotNull(T[] tArr, String str) {
        notNull(tArr, str);
        for (int i = 0; i < tArr.length; i++) {
            elementNotNull(tArr[i], str, i);
        }
        return tArr;
    }

    public static <E> E elementNotNull(E e, String str, int i) {
        if (e != null) {
            return e;
        }
        throw new NullPointerException(str + " must not contain a null element, found at index " + i + ".");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <S, T extends S> ImmutableList<T> elementsNotImplemented(ImmutableList<S> immutableList, Class<T> cls, String str) {
        for (int i = 0; i < immutableList.size(); i++) {
            notImplementedInternal(immutableList.get(i), cls, str);
        }
        return immutableList;
    }

    public static long range(long j, long j2, long j3, String str) {
        if (j < j2) {
            throw new IllegalArgumentException(str + " must not be smaller than " + j2);
        }
        if (j <= j3) {
            return j;
        }
        throw new IllegalArgumentException(str + " must not be greater than " + j3);
    }

    public static int unsignedShort(int i, String str) {
        if (UnsignedDataTypes.isUnsignedShort(i)) {
            return i;
        }
        throw new IllegalArgumentException(str + " must not exceed the value range of unsigned short [0, 65535], but was " + i + ".");
    }

    public static int unsignedShortNotZero(int i, String str) {
        if (i == 0) {
            throw new IllegalArgumentException(str + " must not be zero.");
        }
        unsignedShort(i, str);
        return i;
    }

    public static long unsignedInt(long j, String str) {
        if (UnsignedDataTypes.isUnsignedInt(j)) {
            return j;
        }
        throw new IllegalArgumentException(str + " must not exceed the value range of unsigned int [0, 4294967295], but was " + j + ".");
    }

    public static int index(int i, int i2) {
        if (i >= 0 && i < i2) {
            return i;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index must not be smaller than 0, but was " + i + ".");
        }
        throw new IndexOutOfBoundsException("Index must not be greater than or equal to the size (" + i2 + "), but was " + i + ".");
    }

    public static int cursorIndex(int i, int i2) {
        if (i >= 0 && i <= i2) {
            return i;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("Cursor index must not be smaller than 0, but was " + i + ".");
        }
        throw new IndexOutOfBoundsException("Cursor index must not be greater than the size (" + i2 + "), but was " + i + ".");
    }

    public static void indexRange(int i, int i2, int i3) {
        if (i < 0 || i > i2 || i2 > i3) {
            if (i < 0) {
                throw new IndexOutOfBoundsException("Start index must not be smaller than 0, but was " + i + ".");
            }
            if (i > i2) {
                throw new IndexOutOfBoundsException("Start index must not be greater than the end index, but " + i + " > " + i2 + ".");
            }
            throw new IndexOutOfBoundsException("End index must not be greater than or equal to the size (" + i3 + "), but was " + i2 + ".");
        }
    }

    public static void state(boolean z, String str) {
        if (!z) {
            throw new IllegalStateException(str);
        }
    }

    public static <T> T stateNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new IllegalStateException(str + " must not be null. This must not happen and is a bug.");
    }

    private Checks() {
    }
}

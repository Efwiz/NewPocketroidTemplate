package com.reandroid.utils;

/* JADX INFO: loaded from: classes11.dex */
public class ObjectsUtil {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj) throws ClassCastException {
        return obj;
    }

    public static <T> T getNull() throws ClassCastException {
        return null;
    }

    public static byte of(byte b) {
        return b;
    }

    public static double of(double d) {
        return d;
    }

    public static float of(float f) {
        return f;
    }

    public static int of(int i) {
        return i;
    }

    public static long of(long j) {
        return j;
    }

    public static <T> T of(T t) {
        return t;
    }

    public static String of(String str) {
        return str;
    }

    public static short of(short s) {
        return s;
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static int hash(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public static int hash(Object obj, Object obj2) {
        return ((hash(obj) + 31) * 31) + hash(obj2);
    }

    public static int hash(Object obj, Object obj2, Object obj3) {
        return ((((hash(obj) + 31) * 31) + hash(obj2)) * 31) + hash(obj3);
    }

    public static int hash(Object obj, Object obj2, Object obj3, Object obj4) {
        return ((((((hash(obj) + 31) * 31) + hash(obj2)) * 31) + hash(obj3)) * 31) + hash(obj4);
    }

    public static int hash(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return ((((((((hash(obj) + 31) * 31) + hash(obj2)) * 31) + hash(obj3)) * 31) + hash(obj4)) * 31) + hash(obj5);
    }
}

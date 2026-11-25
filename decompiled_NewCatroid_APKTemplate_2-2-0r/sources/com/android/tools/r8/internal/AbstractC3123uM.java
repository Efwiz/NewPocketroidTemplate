package com.android.tools.r8.internal;

import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3123uM {
    public static final /* synthetic */ int a = 0;

    static {
        try {
            AbstractC0909Ct.b();
        } catch (Throwable th) {
            Logger.getLogger(AbstractC3123uM.class.getName()).log(Level.WARNING, "Java 7 compatibility warning: See https://github.com/google/guava/issues/5269", (Throwable) new Exception("Guava will drop support for Java 7 in 2021. Please let us know if this will cause you problems: https://github.com/google/guava/issues/5269", th));
        }
    }

    public static void a(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void b(String str, boolean z) {
        if (!z) {
            throw new IllegalStateException(str);
        }
    }

    public static void a(String str, boolean z) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void a(boolean z, String str, char c) {
        if (!z) {
            throw new IllegalArgumentException(AbstractC2619nY.a(str, Character.valueOf(c)));
        }
    }

    public static void b(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException(a("index", i, i2));
        }
    }

    public static void a(boolean z, String str, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(AbstractC2619nY.a(str, obj));
        }
    }

    public static Object a(Object obj) {
        obj.getClass();
        return obj;
    }

    public static void a(int i, int i2) {
        String strA;
        if (i < 0 || i >= i2) {
            if (i < 0) {
                strA = AbstractC2619nY.a("%s (%s) must not be negative", "index", Integer.valueOf(i));
            } else if (i2 >= 0) {
                strA = AbstractC2619nY.a("%s (%s) must be less than size (%s)", "index", Integer.valueOf(i), Integer.valueOf(i2));
            } else {
                throw new IllegalArgumentException(new StringBuilder(26).append("negative size: ").append(i2).toString());
            }
            throw new IndexOutOfBoundsException(strA);
        }
    }

    private static String a(String str, int i, int i2) {
        if (i < 0) {
            return AbstractC2619nY.a("%s (%s) must not be negative", str, Integer.valueOf(i));
        }
        if (i2 >= 0) {
            return AbstractC2619nY.a("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IllegalArgumentException(new StringBuilder(26).append("negative size: ").append(i2).toString());
    }

    public static void a(int i, int i2, int i3) {
        String strA;
        if (i < 0 || i2 < i || i2 > i3) {
            if (i < 0 || i > i3) {
                strA = a("start index", i, i3);
            } else if (i2 >= 0 && i2 <= i3) {
                strA = AbstractC2619nY.a("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i));
            } else {
                strA = a("end index", i2, i3);
            }
            throw new IndexOutOfBoundsException(strA);
        }
    }
}

package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ds, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0936Ds {
    public static void a(Object obj) {
        if (obj != null) {
            return;
        }
        throw ((NullPointerException) a(AbstractC0936Ds.class.getName(), (RuntimeException) new NullPointerException()));
    }

    public static void b(Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw ((NullPointerException) a(AbstractC0936Ds.class.getName(), (RuntimeException) new NullPointerException(str.concat(" must not be null"))));
    }

    public static void c(Object obj, String str) {
        if (obj == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String name2 = AbstractC0936Ds.class.getName();
            int i = 0;
            while (!stackTrace[i].getClassName().equals(name2)) {
                i++;
            }
            while (stackTrace[i].getClassName().equals(name2)) {
                i++;
            }
            StackTraceElement stackTraceElement = stackTrace[i];
            throw ((NullPointerException) a(AbstractC0936Ds.class.getName(), (RuntimeException) new NullPointerException("Parameter specified as non-null is null: method " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ", parameter " + str)));
        }
    }

    public static void a(Object obj, String str) {
        if (obj != null) {
            return;
        }
        throw ((NullPointerException) a(AbstractC0936Ds.class.getName(), (RuntimeException) new NullPointerException(str)));
    }

    public static void a(String str) {
        throw ((C1810d10) a(AbstractC0936Ds.class.getName(), (RuntimeException) new C1810d10("lateinit property " + str + " has not been initialized")));
    }

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    static Throwable a(String str, RuntimeException runtimeException) {
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(stackTrace[i2].getClassName())) {
                i = i2;
            }
        }
        runtimeException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i + 1, length));
        return runtimeException;
    }
}

package com.android.tools.r8.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1701bZ {
    private static final Object a;
    public static final /* synthetic */ int b = 0;

    static {
        Object objInvoke;
        Method method;
        Object objInvoke2 = null;
        try {
            objInvoke = Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            objInvoke = null;
        }
        a = objInvoke;
        if (objInvoke != null) {
            try {
                Class.forName("sun.misc.JavaLangAccess", false, null).getMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
            } catch (ThreadDeath e2) {
                throw e2;
            } catch (Throwable unused2) {
            }
        }
        if (objInvoke == null) {
            return;
        }
        try {
            try {
                method = Class.forName("sun.misc.JavaLangAccess", false, null).getMethod("getStackTraceDepth", Throwable.class);
            } catch (ThreadDeath e3) {
                throw e3;
            } catch (Throwable unused3) {
                method = null;
            }
            if (method == null) {
                return;
            }
            try {
                objInvoke2 = Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
            } catch (ThreadDeath e4) {
                throw e4;
            } catch (Throwable unused4) {
            }
            method.invoke(objInvoke2, new Throwable());
        } catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException unused5) {
        }
    }
}

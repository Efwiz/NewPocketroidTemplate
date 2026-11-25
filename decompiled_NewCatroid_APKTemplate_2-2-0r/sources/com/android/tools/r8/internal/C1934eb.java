package com.android.tools.r8.internal;

import java.io.Closeable;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1934eb implements InterfaceC2012fb {
    static final C1934eb a = new C1934eb();
    static final Method b;

    static {
        Method method;
        try {
            method = Throwable.class.getMethod("addSuppressed", Throwable.class);
        } catch (Throwable unused) {
            method = null;
        }
        b = method;
    }

    C1934eb() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2012fb
    public final void a(Closeable closeable, Throwable th, Throwable th2) {
        if (th == th2) {
            return;
        }
        try {
            b.invoke(th, th2);
        } catch (Throwable unused) {
            Logger logger = AbstractC1776cb.a;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(closeable);
            logger.log(level, new StringBuilder(strValueOf.length() + 42).append("Suppressing exception thrown when closing ").append(strValueOf).toString(), th2);
        }
    }
}

package com.android.tools.r8.internal;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.db, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1852db implements InterfaceC2012fb {
    static final C1852db a = new C1852db();

    C1852db() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2012fb
    public final void a(Closeable closeable, Throwable th, Throwable th2) {
        Logger logger = AbstractC1776cb.a;
        Level level = Level.WARNING;
        String strValueOf = String.valueOf(closeable);
        logger.log(level, new StringBuilder(strValueOf.length() + 42).append("Suppressing exception thrown when closing ").append(strValueOf).toString(), th2);
    }
}

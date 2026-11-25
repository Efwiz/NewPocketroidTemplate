package com.android.tools.r8.naming;

import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface P {
    default Q.c a(Function function) {
        return c() ? d() : b().a(function, a());
    }

    String a();

    Q.c b();

    boolean c();

    Q.c d();
}

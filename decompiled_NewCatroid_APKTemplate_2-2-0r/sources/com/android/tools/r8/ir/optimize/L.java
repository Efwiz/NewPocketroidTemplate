package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.S10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class L {
    private static final L a = new I();
    private static final L b = new J();

    public static L a() {
        return a;
    }

    public static L e() {
        return b;
    }

    public Iterable b() {
        throw new C3405y10();
    }

    public boolean c() {
        return this instanceof K;
    }

    public boolean d() {
        return this instanceof J;
    }

    public static L a(S10 s10) {
        return new K(s10);
    }
}

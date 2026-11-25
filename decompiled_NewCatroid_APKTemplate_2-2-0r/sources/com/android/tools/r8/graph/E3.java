package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class E3 {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C0706m3.d dVar, C0658g0 c0658g0) {
        if (!a && dVar == null) {
            throw new AssertionError();
        }
        if (!dVar.a() && c0658g0 != null) {
            for (C0633d0 c0633d0 : c0658g0.c) {
                if (!a && c0633d0.t0().e.toString().equals("Ldalvik/annotation/Signature;")) {
                    throw new AssertionError();
                }
            }
        }
        return true;
    }
}

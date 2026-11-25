package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0720p {
    public static AbstractC0720p a(com.android.tools.r8.utils.A1 a1) {
        C0708n c0708n = new C0708n();
        return (!a1.C1() || a1.n1.c0) ? c0708n : new C0714o(a1.j0());
    }

    public abstract C0767v2 a(C0767v2 c0767v2);

    public abstract String a(String str);

    public abstract C0767v2 b(C0767v2 c0767v2);
}

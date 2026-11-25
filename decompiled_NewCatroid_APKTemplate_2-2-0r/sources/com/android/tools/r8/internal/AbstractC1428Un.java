package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Un, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1428Un {
    public static AbstractC1428Un a(AbstractC1380Tn abstractC1380Tn) {
        return (abstractC1380Tn == null || !(abstractC1380Tn instanceof GH)) ? C1628ag.d() : new C2320jd(abstractC1380Tn.a());
    }

    public abstract AbstractC1428Un a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar);

    public abstract AbstractC1428Un a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.u5 u5Var);

    public abstract AbstractC1380Tn b();

    public abstract AbstractC1380Tn c();

    public static C1628ag a() {
        return C1628ag.d();
    }
}

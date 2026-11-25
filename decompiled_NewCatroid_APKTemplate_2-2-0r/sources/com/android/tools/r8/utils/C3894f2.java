package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.AbstractC2251ih;

/* JADX INFO: renamed from: com.android.tools.r8.utils.f2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3894f2 extends AbstractC2251ih {
    private static final C3894f2 b = new C3894f2();
    private final boolean a = false;

    private C3894f2() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        C0684j2 c0684j2 = (C0684j2) obj;
        C0684j2 c0684j3 = (C0684j2) obj2;
        if (this.a) {
            return c0684j2.h.equals(c0684j3.h);
        }
        return c0684j2.f.equals(c0684j3.f) && c0684j2.h.equals(c0684j3.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        C0684j2 c0684j2 = (C0684j2) obj;
        if (this.a) {
            return c0684j2.h.hashCode();
        }
        return c0684j2.h.hashCode() + (c0684j2.f.hashCode() * 31);
    }

    public static C3894f2 a() {
        return b;
    }
}

package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ew, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0972Ew extends AbstractC0998Fw {
    static final /* synthetic */ boolean f = true;

    @Override // com.android.tools.r8.internal.AbstractC0998Fw
    public final AbstractC0998Fw e() {
        return this;
    }

    public final C1027Gw f() {
        Set setA = a();
        AbstractC2450lF abstractC2450lF = ((AbstractC0998Fw) this).c;
        AbstractC2450lF abstractC2450lF2 = ((AbstractC0998Fw) this).d;
        AbstractC2450lF abstractC2450lF3 = ((AbstractC0998Fw) this).e;
        G1 g1 = G1.PUBLIC;
        if (setA.size() == G1.values().length) {
            abstractC2450lF.getClass();
            if (abstractC2450lF instanceof C2219iF) {
                abstractC2450lF2.getClass();
                if (abstractC2450lF2 instanceof C2219iF) {
                    abstractC2450lF3.getClass();
                    if (abstractC2450lF3 instanceof C2219iF) {
                        return C1027Gw.e;
                    }
                }
            }
        }
        C1027Gw c1027Gw = new C1027Gw(setA, abstractC2450lF, abstractC2450lF2, abstractC2450lF3);
        if (f || !c1027Gw.f()) {
            return c1027Gw;
        }
        throw new AssertionError();
    }
}

package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1225Nw extends C1027Gw {
    private static final C1225Nw l = new C1225Nw(G1.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a());
    private final AbstractC2450lF g;
    private final AbstractC2450lF h;
    private final AbstractC2450lF i;
    private final AbstractC2450lF j;
    private final AbstractC2450lF k;

    public C1225Nw(Set set, AbstractC2450lF abstractC2450lF, AbstractC2450lF abstractC2450lF2, AbstractC2450lF abstractC2450lF3, AbstractC2450lF abstractC2450lF4, AbstractC2450lF abstractC2450lF5, AbstractC2450lF abstractC2450lF6, AbstractC2450lF abstractC2450lF7, AbstractC2450lF abstractC2450lF8) {
        super(set, abstractC2450lF, abstractC2450lF2, abstractC2450lF7);
        this.g = abstractC2450lF3;
        this.h = abstractC2450lF4;
        this.i = abstractC2450lF5;
        this.j = abstractC2450lF6;
        this.k = abstractC2450lF8;
    }

    public static C1225Nw h() {
        return l;
    }

    public static C1198Mw i() {
        return new C1198Mw();
    }

    @Override // com.android.tools.r8.internal.C1027Gw
    public final boolean f() {
        if (super.f()) {
            AbstractC2450lF abstractC2450lF = this.g;
            abstractC2450lF.getClass();
            if (abstractC2450lF instanceof C2219iF) {
                AbstractC2450lF abstractC2450lF2 = this.h;
                abstractC2450lF2.getClass();
                if (abstractC2450lF2 instanceof C2219iF) {
                    AbstractC2450lF abstractC2450lF3 = this.i;
                    abstractC2450lF3.getClass();
                    if (abstractC2450lF3 instanceof C2219iF) {
                        AbstractC2450lF abstractC2450lF4 = this.j;
                        abstractC2450lF4.getClass();
                        if (abstractC2450lF4 instanceof C2219iF) {
                            AbstractC2450lF abstractC2450lF5 = this.k;
                            abstractC2450lF5.getClass();
                            if (abstractC2450lF5 instanceof C2219iF) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public final AbstractC2450lF j() {
        return this.j;
    }

    public final AbstractC2450lF k() {
        return this.h;
    }

    public final AbstractC2450lF l() {
        return this.i;
    }

    public final AbstractC2450lF m() {
        return this.k;
    }

    public final AbstractC2450lF n() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.C1027Gw
    public final String toString() {
        if (f()) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        C2781pU c2781pUA = C2781pU.a(sb);
        AbstractC2925rU.a(c2781pUA, this);
        AbstractC2925rU.a(c2781pUA, n(), "synchronized");
        AbstractC2925rU.a(c2781pUA, k(), "bridge");
        AbstractC2925rU.a(c2781pUA, l(), "native");
        AbstractC2925rU.a(c2781pUA, j(), "abstract");
        AbstractC2925rU.a(c2781pUA, m(), "strictfp");
        return sb.toString();
    }
}

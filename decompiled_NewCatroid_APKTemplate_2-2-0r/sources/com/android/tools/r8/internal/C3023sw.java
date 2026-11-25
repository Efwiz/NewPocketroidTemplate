package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3023sw extends C1027Gw {
    private static final C3023sw i = new C3023sw(G1.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a());
    private final AbstractC2450lF g;
    private final AbstractC2450lF h;

    public C3023sw(Set set, AbstractC2450lF abstractC2450lF, AbstractC2450lF abstractC2450lF2, AbstractC2450lF abstractC2450lF3, AbstractC2450lF abstractC2450lF4, AbstractC2450lF abstractC2450lF5) {
        super(set, abstractC2450lF, abstractC2450lF2, abstractC2450lF5);
        this.g = abstractC2450lF3;
        this.h = abstractC2450lF4;
    }

    public static C3023sw h() {
        return i;
    }

    public static C2954rw i() {
        return new C2954rw();
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
                    return true;
                }
            }
        }
        return false;
    }

    public final AbstractC2450lF j() {
        return this.h;
    }

    public final AbstractC2450lF k() {
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
        AbstractC2925rU.a(c2781pUA, k(), "volatile");
        AbstractC2925rU.a(c2781pUA, j(), "transient");
        return sb.toString();
    }
}

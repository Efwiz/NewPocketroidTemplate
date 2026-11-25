package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1289Qd extends C3492zF {
    static final /* synthetic */ boolean i = true;

    public C1289Qd(S10 s10, S10 s11) {
        super(s10, s11);
        if (!i && !s10.A()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1289Qd G() {
        return this;
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof C1289Qd;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean l1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return q00.a(K2());
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        if (!i && g()) {
            throw new AssertionError();
        }
        if (h().D()) {
            c3340xC.a(interfaceC2641no);
        }
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new R8(H2(), l6.a(b())), this);
    }

    @Override // com.android.tools.r8.internal.C3492zF, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        super.a(c0702m, c2433l20);
        if (!i && !c2433l20.a(K2().w(), a())) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.b(K2());
    }
}

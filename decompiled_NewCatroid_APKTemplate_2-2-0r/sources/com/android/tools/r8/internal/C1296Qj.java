package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1296Qj extends AbstractC1954eu {
    static final /* synthetic */ boolean i = true;

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (i) {
            return 0;
        }
        throw new AssertionError("Goto has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (i) {
            return 0;
        }
        throw new AssertionError("Goto defines no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 24;
    }

    public final C3332x4 K2() {
        boolean z = i;
        if (!z && h().m() != this) {
            throw new AssertionError();
        }
        List<C3332x4> listZ = h().z();
        if (z || listZ.size() >= 1) {
            return listZ.get(listZ.size() - 1);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean M1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1296Qj P() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.M1() && abstractC2412ko.P().K2() == K2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean l1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        if (h() == null || h().z().isEmpty()) {
            return super.toString() + "block <unknown>";
        }
        return super.toString() + "block " + K2().w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C2899r7(l6.a(K2())), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(K2());
    }
}

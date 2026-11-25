package com.android.tools.r8.internal;

import java.util.LinkedHashSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class IX {
    static final /* synthetic */ boolean d = true;
    private final LinkedHashSet a;
    private final LinkedHashSet b;
    private boolean c;

    private IX() {
        this.a = OU.d();
        this.b = OU.d();
        this.c = false;
    }

    final void a(IX ix) {
        this.b.add(ix);
    }

    final void b(IX ix) {
        this.a.add(ix);
        ix.b.add(this);
    }

    public C3217vX g() {
        return null;
    }

    C3363xX h() {
        return null;
    }

    public C3441yX i() {
        return null;
    }

    InterfaceC3511zX j() {
        return null;
    }

    DX k() {
        return null;
    }

    GX l() {
        return null;
    }

    HX m() {
        return null;
    }

    final LinkedHashSet n() {
        return this.b;
    }

    public final IX o() {
        if (d || r()) {
            return (IX) this.b.iterator().next();
        }
        throw new AssertionError();
    }

    public final IX p() {
        if (d || s()) {
            return (IX) this.a.iterator().next();
        }
        throw new AssertionError();
    }

    final LinkedHashSet q() {
        return this.a;
    }

    public final boolean r() {
        return this.b.size() == 1;
    }

    public final boolean s() {
        return this.a.size() == 1;
    }

    final boolean t() {
        return this.c;
    }

    public boolean u() {
        return this instanceof C3441yX;
    }

    boolean v() {
        return this instanceof C3217vX;
    }

    boolean w() {
        return this instanceof C3217vX;
    }

    final void x() {
        for (IX ix : this.a) {
            ix.b.remove(this);
            ix.b.addAll(this.b);
        }
        for (IX ix2 : this.b) {
            ix2.a.remove(this);
            ix2.a.addAll(this.a);
        }
        this.c = true;
    }

    static DX a(DG dg) {
        return new DX(dg);
    }

    static C3441yX a(C1166Ls c1166Ls) {
        return new C3441yX(c1166Ls);
    }

    static EX b(AbstractC2412ko abstractC2412ko) {
        return new EX(abstractC2412ko);
    }

    static C3217vX a(C2115gt c2115gt) {
        return new C3217vX(c2115gt);
    }

    static HX a(AbstractC1305Qs abstractC1305Qs) {
        return new HX(abstractC1305Qs);
    }

    static AX a(AbstractC2412ko abstractC2412ko) {
        return new AX(abstractC2412ko);
    }
}

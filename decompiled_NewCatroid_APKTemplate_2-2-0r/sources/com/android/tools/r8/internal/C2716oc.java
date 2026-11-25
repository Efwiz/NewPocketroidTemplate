package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2716oc extends AbstractC2862qc {
    static final /* synthetic */ boolean d = true;
    private AbstractC1154Lf c;

    public C2716oc(AbstractC1154Lf abstractC1154Lf, Set set) {
        super(set);
        this.c = abstractC1154Lf;
        boolean z = d;
        if (!z && s()) {
            throw new AssertionError("Must use BottomReceiverParameterState instead");
        }
        if (!z && t()) {
            throw new AssertionError("Must use UnknownParameterState instead");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final B1 a(C0702m c0702m) {
        int i = B1.b;
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL f() {
        return new C2716oc(this.c, l());
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final AbstractC3122uL k() {
        if (n()) {
            if (this.c.h()) {
                return F5.g();
            }
            o();
        }
        if (d || !s()) {
            return this;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final AbstractC1154Lf q() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final WH r() {
        return this.c.f();
    }

    public final boolean s() {
        return this.c.h() && !n();
    }

    public final boolean t() {
        return this.c.m();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final AbstractC3425yH a(C0702m c0702m, AbstractC2862qc abstractC2862qc, C0767v2 c0767v2, InterfaceC3880d interfaceC3880d) {
        if (!d && c0767v2 != null && !c0767v2.O0()) {
            throw new AssertionError();
        }
        AbstractC1154Lf abstractC1154Lf = this.c;
        AbstractC1154Lf abstractC1154LfA = abstractC1154Lf.a(c0702m, abstractC2862qc.q());
        this.c = abstractC1154LfA;
        if (abstractC1154LfA.m()) {
            return C2582n10.g();
        }
        boolean zA = a(abstractC2862qc);
        if (b(c0702m)) {
            return C2582n10.g();
        }
        if (!this.c.equals(abstractC1154Lf) || zA) {
            interfaceC3880d.a();
        }
        return this;
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1497Xb extends AbstractC2862qc {
    static final /* synthetic */ boolean d = true;
    private WH c;

    public C1497Xb(WH wh, Set set) {
        super(set);
        this.c = wh;
        boolean z = d;
        if (!z && s()) {
            throw new AssertionError("Must use BottomArrayTypeParameterState instead");
        }
        if (!z && t()) {
            throw new AssertionError("Must use UnknownParameterState instead");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final B1 a(C0702m c0702m) {
        if (this.c.f()) {
            return c0702m.b().a();
        }
        int i = B1.b;
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL f() {
        return new C1497Xb(this.c, l());
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final C1497Xb g() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final AbstractC3122uL k() {
        if (n()) {
            if (this.c.d()) {
                return C3183v5.g();
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
        return AbstractC1154Lf.n();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final WH r() {
        return this.c;
    }

    public final boolean s() {
        return this.c.d() && !n();
    }

    public final boolean t() {
        return this.c.g();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final AbstractC3425yH a(C0702m c0702m, AbstractC2862qc abstractC2862qc, C0767v2 c0767v2, InterfaceC3880d interfaceC3880d) {
        boolean z = d;
        if (!z && !c0767v2.M0()) {
            throw new AssertionError();
        }
        if (!z && this.c.g()) {
            throw new AssertionError();
        }
        WH whA = this.c.a(abstractC2862qc.r());
        this.c = whA;
        if (whA.g()) {
            return C2582n10.g();
        }
        boolean zA = a(abstractC2862qc);
        if (b(c0702m)) {
            return C2582n10.g();
        }
        if (zA) {
            interfaceC3880d.a();
        }
        return this;
    }
}

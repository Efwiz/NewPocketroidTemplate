package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ac, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1624ac extends AbstractC2862qc {
    static final /* synthetic */ boolean e = true;
    private B1 c;
    private AbstractC1154Lf d;

    public C1624ac(B1 b1, AbstractC1154Lf abstractC1154Lf, Set set) {
        super(set);
        this.c = b1;
        this.d = abstractC1154Lf;
        boolean z = e;
        if (!z && s()) {
            throw new AssertionError("Must use BottomClassTypeParameterState instead");
        }
        if (!z && t()) {
            throw new AssertionError("Must use UnknownParameterState instead");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final B1 a(C0702m c0702m) {
        B1 b1;
        if (!this.d.f().f()) {
            return this.c;
        }
        if (e || (b1 = this.c) == null || b1.x()) {
            return c0702m.b().a();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL f() {
        return new C1624ac(this.c, this.d, l());
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final C1624ac h() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final AbstractC3122uL k() {
        if (n()) {
            B1 b1 = this.c;
            b1.getClass();
            if (b1 instanceof H5) {
                if (e || this.d.h()) {
                    return C3333x5.g();
                }
                throw new AssertionError();
            }
            o();
        }
        if (e || !s()) {
            return this;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final AbstractC1154Lf q() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final WH r() {
        return this.d.f();
    }

    public final boolean s() {
        B1 b1 = this.c;
        b1.getClass();
        return (b1 instanceof H5) && this.d.h() && !n();
    }

    public final boolean t() {
        return this.c.isUnknown() && this.d.m();
    }

    @Override // com.android.tools.r8.internal.AbstractC2862qc
    public final AbstractC3425yH a(C0702m c0702m, AbstractC2862qc abstractC2862qc, C0767v2 c0767v2, InterfaceC3880d interfaceC3880d) {
        if (!e && !c0767v2.O0()) {
            throw new AssertionError();
        }
        B1 b1 = this.c;
        this.c = b1.a(abstractC2862qc.a(c0702m), c0702m.b(), true, false);
        AbstractC1154Lf abstractC1154Lf = this.d;
        this.d = F20.a(c0702m, abstractC1154Lf.a(c0702m, abstractC2862qc.q()), c0767v2);
        if (this.c.isUnknown() && this.d.m()) {
            return C2582n10.g();
        }
        boolean zA = a(abstractC2862qc);
        if (b(c0702m)) {
            return C2582n10.g();
        }
        if (this.c != b1 || !this.d.equals(abstractC1154Lf) || zA) {
            interfaceC3880d.a();
        }
        return this;
    }
}

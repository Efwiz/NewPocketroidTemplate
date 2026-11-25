package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2548mc extends AbstractC2168hc {
    static final /* synthetic */ boolean d = true;
    private B1 c;

    public C2548mc(B1 b1, Set set) {
        super(set);
        this.c = b1;
        boolean z = d;
        if (!z && q()) {
            throw new AssertionError("Must use BottomPrimitiveTypeParameterState instead");
        }
        if (!z && r()) {
            throw new AssertionError("Must use UnknownParameterState instead");
        }
    }

    public final AbstractC3425yH a(C0702m c0702m, C2548mc c2548mc, C0767v2 c0767v2, InterfaceC3880d interfaceC3880d) {
        if (!d && !c0767v2.W0()) {
            throw new AssertionError();
        }
        B1 b1 = this.c;
        B1 b1A = b1.a(c2548mc.c, c0702m.b(), false, false);
        this.c = b1A;
        if (b1A.isUnknown()) {
            return C2582n10.g();
        }
        boolean zA = a(c2548mc);
        if (b(c0702m)) {
            return C2582n10.g();
        }
        if (this.c != b1 || zA) {
            interfaceC3880d.a();
        }
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL f() {
        return new C2548mc(this.c, l());
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final C2548mc i() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2168hc
    public final AbstractC3122uL k() {
        if (n()) {
            B1 b1 = this.c;
            b1.getClass();
            if (b1 instanceof H5) {
                return E5.g();
            }
            o();
        }
        if (d || !q()) {
            return this;
        }
        throw new AssertionError();
    }

    public final boolean q() {
        B1 b1 = this.c;
        b1.getClass();
        return (b1 instanceof H5) && !n();
    }

    public final boolean r() {
        return this.c.isUnknown();
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final B1 a(C0702m c0702m) {
        return this.c;
    }
}

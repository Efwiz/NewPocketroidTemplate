package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3109u8 extends L7 {
    static final /* synthetic */ boolean c = true;
    private final EnumC1812d20 b;

    public C3109u8(EnumC1812d20 enumC1812d20) {
        this.b = enumC1812d20;
    }

    private int U() {
        int i = AbstractC3040t8.a[this.b.ordinal()];
        if (i == 1) {
            return 172;
        }
        if (i == 2) {
            return 174;
        }
        if (i == 3) {
            return 173;
        }
        if (i == 4) {
            return 175;
        }
        if (i == 5) {
            return 176;
        }
        throw new C3405y10("Unexpected return type: " + this.b);
    }

    public EnumC1812d20 V() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c2) {
        com.android.tools.r8.graph.C.a(this, b7);
        return 0;
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.utils.D3 a(BiFunction biFunction, B7 b7, AbstractC2359k7 abstractC2359k7) {
        return com.android.tools.r8.utils.D3.b(abstractC2359k7);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(U());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.e(n8.e().a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        if (c || !interfaceC3107u6.e().I0().Y0()) {
            return abstractC2359k7.a(c0702m, interfaceC3107u6, interfaceC3107u6.e().I0()).h();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}

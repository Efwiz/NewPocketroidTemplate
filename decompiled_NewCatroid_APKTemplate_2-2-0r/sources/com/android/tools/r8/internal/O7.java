package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class O7 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final int b;
    private final EnumC1812d20 c;

    public O7(EnumC1812d20 enumC1812d20, int i) {
        this.b = i;
        this.c = enumC1812d20;
    }

    private int T() {
        int i = N7.a[this.c.ordinal()];
        if (i == 1) {
            return 25;
        }
        if (i == 2) {
            return 21;
        }
        if (i == 3) {
            return 23;
        }
        if (i == 4) {
            return 22;
        }
        if (i == 5) {
            return 24;
        }
        throw new C3405y10("Unexpected type " + this.c);
    }

    public final int U() {
        return this.b;
    }

    public final EnumC1812d20 V() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this.b, b7.r().b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final O7 r() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean y() {
        return false;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return T();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.d(T(), this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, final InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(c0702m, interfaceC3107u6, this.b, this.c, new BiFunction() { // from class: com.android.tools.r8.internal.O7$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC1348Si) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        H8 h8A = n8.a(this.b);
        H8 h8A2 = n8.a(h8A);
        EnumC1812d20 enumC1812d20 = h8A.b;
        int i = h8A2.a;
        int i2 = h8A.a;
        c2102gl.getClass();
        c2102gl.a(EnumC1968f20.a(enumC1812d20), i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC1348Si interfaceC1348Si) {
        if (interfaceC1348Si.J()) {
            return abstractC2359k7.a(interfaceC3107u6, interfaceC1348Si.F());
        }
        if (!d && !interfaceC1348Si.q() && !interfaceC1348Si.k()) {
            throw new AssertionError();
        }
        StringBuilder sbAppend = new StringBuilder("Unexpected attempt to read local of type top at index ").append(this.b);
        if (this.c.b()) {
            sbAppend.append(" and ").append(this.b + 1);
        }
        return AbstractC2359k7.a(sbAppend.toString());
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class R8 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final int b;
    private final EnumC1812d20 c;

    public R8(EnumC1812d20 enumC1812d20, int i) {
        this.b = i;
        this.c = enumC1812d20;
    }

    private int U() {
        int i = Q8.a[this.c.ordinal()];
        if (i == 1) {
            return 58;
        }
        if (i == 2) {
            return 54;
        }
        if (i == 3) {
            return 56;
        }
        if (i == 4) {
            return 55;
        }
        if (i == 5) {
            return 57;
        }
        throw new C3405y10("Unexpected type " + this.c);
    }

    public final int T() {
        return this.b;
    }

    public final EnumC1812d20 V() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this.b, b7.v().b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final R8 v() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean y() {
        return false;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.d(U(), this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(this.b, interfaceC3054tM, interfaceC3107u6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(this.b, this.c.d(), c0702m, interfaceC3107u6);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        H8 h8E = n8.e();
        EnumC1812d20 enumC1812d20 = this.c;
        int i = n8.a(this.b, h8E).a;
        int i2 = h8E.a;
        c2102gl.getClass();
        c2102gl.a(EnumC1968f20.a(enumC1812d20), i, i2);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, final C0702m c0702m, final InterfaceC3107u6 interfaceC3107u6) {
        if (this.c.a()) {
            return abstractC2359k7.b(new BiFunction() { // from class: com.android.tools.r8.internal.R8$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.a(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                }
            });
        }
        if (!d && this.c.a()) {
            throw new AssertionError();
        }
        EnumC1812d20 enumC1812d20 = this.c;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.R8$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(c0702m, interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        };
        abstractC2359k7.getClass();
        return abstractC2359k7.a(c0702m, enumC1812d20.a(c0702m.a()), interfaceC3107u6, biFunction);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class B6 extends C6 {
    static final /* synthetic */ boolean d = true;

    public B6(EnumC2523mE enumC2523mE) {
        super(enumC2523mE);
    }

    private int U() {
        switch (T()) {
            case OBJECT:
                return 50;
            case BOOLEAN_OR_BYTE:
                return 51;
            case CHAR:
                return 52;
            case SHORT:
                return 53;
            case INT:
                return 46;
            case FLOAT:
                return 48;
            case LONG:
                return 47;
            case DOUBLE:
                return 49;
            default:
                throw new C3405y10("Unexpected type " + T());
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(U());
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        H8 h8A;
        H8 h8E = n8.e();
        H8 h8E2 = n8.e();
        boolean z = d;
        if (!z && !h8E2.b.a()) {
            throw new AssertionError();
        }
        EnumC1812d20 enumC1812d20A = EnumC1812d20.a(T());
        C0767v2 c0767v2 = h8E2.c;
        if (c0767v2 != null) {
            h8A = n8.a(c0767v2.a(c2102gl.o.a()));
            if (!z && n8.c().b != enumC1812d20A) {
                throw new AssertionError();
            }
        } else {
            h8A = n8.a(enumC1812d20A);
        }
        c2102gl.a(T(), h8A.a, h8E2.a, h8E.a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, final C0702m c0702m, final InterfaceC3107u6 interfaceC3107u6) {
        final C0722p1 c0722p1A = c0702m.a();
        return abstractC2359k7.a(c0702m, interfaceC3107u6, c0722p1A.A1).a(c0702m, a(c0722p1A), interfaceC3107u6, new BiFunction() { // from class: com.android.tools.r8.internal.B6$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(interfaceC3107u6, c0702m, c0722p1A, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C0702m c0702m, C0722p1 c0722p1, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        if (interfaceC3054tM.l()) {
            if (T() == EnumC2523mE.OBJECT) {
                return abstractC2359k7.a(interfaceC3107u6, InterfaceC1348Si.c());
            }
            EnumC2523mE enumC2523mET = T();
            abstractC2359k7.getClass();
            return abstractC2359k7.a(interfaceC3107u6, InterfaceC1348Si.a(enumC2523mET, c0702m.a()));
        }
        if (interfaceC3054tM.G()) {
            return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM.f().T().a().S());
        }
        if (d || interfaceC3054tM.N()) {
            return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM.j().T().a(c0722p1));
        }
        throw new AssertionError();
    }
}

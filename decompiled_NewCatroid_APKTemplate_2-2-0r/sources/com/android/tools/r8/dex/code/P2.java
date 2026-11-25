package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class P2 extends K0<C0767v2> {
    P2(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.g());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 b(K0 k0) {
        return (C0767v2) k0.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "new-instance";
    }

    public C0767v2 N() {
        return (C0767v2) this.f;
    }

    @Override // com.android.tools.r8.dex.code.K0
    final void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.dex.code.P2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((K0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "NewInstance";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 34;
    }

    public P2(int i, C0767v2 c0767v2) {
        super(i, c0767v2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        C0767v2 c0767v2N = N();
        abstractC1476WjE.getClass();
        C0767v2 c0767v2A = abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2N);
        c0767v2A.getClass();
        if (l.a(c0767v2A)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2A);
            c0759u2C.getClass();
            l.a(c0759u2C);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, N());
        B1.a(this.e, shortBuffer, v());
        B1.a(c0767v2A, shortBuffer, c0645e5);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.d(N());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.d(this.e, N());
    }
}

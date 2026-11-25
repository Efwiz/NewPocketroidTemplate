package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.I5;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3089ts;
import java.nio.ShortBuffer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.c0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0420c0 extends K0 {
    C0420c0(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0699l2 b(K0 k0) {
        return (C0699l2) k0.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const-method-handle";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        c2363kB.a((C0699l2) this.f, I5.b, o5Var).a(c0702m, l);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "ConstMethodHandle";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 254;
    }

    public C0420c0(int i, C0699l2 c0699l2) {
        super(i, c0699l2);
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((C0699l2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.a((C0699l2) this.f, I5.b);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0699l2 c0699l2A = c2363kB.a((C0699l2) this.f, I5.b, o5Var);
        int iA = c0645e5.a(c0699l2A);
        if (iA == (65535 & iA)) {
            B1.a(this.e, shortBuffer, 254);
            B1.a(c0699l2A, shortBuffer, c0645e5);
            return;
        }
        throw new C3089ts("MethodHandle-index overflow.");
    }

    @Override // com.android.tools.r8.dex.code.K0
    final void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.dex.code.c0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0420c0.b((K0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((C0699l2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(this.e, (C0699l2) this.f);
    }
}

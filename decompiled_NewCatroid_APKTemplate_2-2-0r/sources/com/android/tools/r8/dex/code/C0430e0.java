package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0656f5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3089ts;
import com.android.tools.r8.internal.WH;
import java.nio.ShortBuffer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.e0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0430e0 extends K0<C0759u2> {
    C0430e0(int i, InterfaceC0409a interfaceC0409a, C0656f5 c0656f5) {
        super(i, interfaceC0409a, c0656f5.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0759u2 b(K0 k0) {
        return (C0759u2) k0.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const-string";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean F() {
        return true;
    }

    public C0759u2 N() {
        return (C0759u2) this.f;
    }

    @Override // com.android.tools.r8.dex.code.K0
    final void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.dex.code.e0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0430e0.b((K0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0430e0 d() {
        return this;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "ConstString";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 26;
    }

    public C0430e0(int i, C0759u2 c0759u2) {
        super(i, c0759u2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0759u2 c0759u2N = N();
        c0759u2N.getClass();
        l.a(c0759u2N);
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((C0759u2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((C0759u2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0759u2 c0759u2 = (C0759u2) this.f;
        c0759u2.getClass();
        int iA = c0645e5.a(c0759u2);
        if (iA == (65535 & iA)) {
            B1.a(this.e, shortBuffer, v());
            B1.a(this.f, shortBuffer, c0645e5);
            return;
        }
        throw new C3089ts("String-index overflow.");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        short s = this.e;
        c2102gl.a(new C1033Hc(c2102gl.a(s, AbstractC3472z00.b((C0702m<?>) c2102gl.o, WH.b()), 2), (C0759u2) this.f));
    }
}

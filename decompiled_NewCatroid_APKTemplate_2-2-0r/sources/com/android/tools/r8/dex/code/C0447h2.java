package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2525mG;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.WH;
import java.nio.ShortBuffer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.h2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0447h2 extends K0 {
    static final /* synthetic */ boolean i = true;
    private final AbstractC2525mG h;

    public C0447h2(int i2, AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        super(i2, abstractC0744s2);
        this.h = abstractC2525mG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0744s2 b(K0 k0) {
        return (AbstractC0744s2) k0.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "const-string*";
    }

    public final AbstractC0744s2 N() {
        return (AbstractC0744s2) this.f;
    }

    public final AbstractC2525mG O() {
        return this.h;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        ((AbstractC0744s2) this.f).a(c0702m, l);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0447h2 h() {
        return this;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int r() {
        return 257;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "DexItemBasedConstString";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        throw new C3405y10("DexItemBasedConstString instructions should always be rewritten into ConstString");
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((AbstractC0744s2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.K0
    final void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new Function() { // from class: com.android.tools.r8.dex.code.h2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0447h2.b((K0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.K0, com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", \"" + ((AbstractC0744s2) this.f).toString() + "\"");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        throw new C3405y10("DexItemBasedConstString instructions should always be rewritten into ConstString");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        if (this.h.f()) {
            if (!i) {
                AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) this.f;
                abstractC0744s2.getClass();
                if (!(abstractC0744s2 instanceof C0767v2)) {
                    throw new AssertionError();
                }
            }
            j5.f(((AbstractC0744s2) this.f).w0());
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        short s = this.e;
        c2102gl.a(new C2477lf(c2102gl.a(s, AbstractC3472z00.b((C0702m<?>) c2102gl.o, WH.b()), 2), (AbstractC0744s2) this.f, this.h));
    }
}

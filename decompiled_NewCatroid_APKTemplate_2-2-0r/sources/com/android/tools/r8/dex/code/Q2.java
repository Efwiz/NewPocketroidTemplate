package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3405y10;
import java.nio.ShortBuffer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q2 extends K0 {
    private final int h;

    public Q2(int i, int i2, C0767v2 c0767v2) {
        super(i, c0767v2);
        this.h = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 b(K0 k0) {
        return (C0767v2) k0.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "new-unboxed-enum-instance";
    }

    @Override // com.android.tools.r8.dex.code.K0
    final void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.dex.code.Q2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Q2.b((K0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "NewUnboxedEnumInstance";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.f((C0767v2) this.f);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.d(this.e, this.h, (C0767v2) this.f);
    }
}

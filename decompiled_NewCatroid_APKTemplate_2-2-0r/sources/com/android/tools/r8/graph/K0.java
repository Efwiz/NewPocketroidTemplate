package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.dex.C0583u;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K0 extends J0 implements M0 {
    static final /* synthetic */ boolean g = true;
    private final int e;
    private final int f;

    public K0(int i, int i2) {
        this.e = i;
        this.f = i2;
    }

    @Override // com.android.tools.r8.graph.J0
    /* JADX INFO: renamed from: A0 */
    public final J0 U() {
        return this;
    }

    public final int B0() {
        return this.f;
    }

    @Override // com.android.tools.r8.graph.J0, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.M0
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
    }

    @Override // com.android.tools.r8.graph.M0
    public final int p0() {
        int i = this.e;
        return (com.android.tools.r8.utils.Q1.d(0) * i) + com.android.tools.r8.utils.Q1.d(i) + com.android.tools.r8.utils.Q1.d(1) + 1 + this.f + 1;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        return Objects.hash(Integer.valueOf(this.e), Integer.valueOf(this.f));
    }

    public final String toString() {
        return "PcBasedDebugInfo (params: " + this.e + ", max-pc: " + com.android.tools.r8.utils.Y2.a(this.f, 2) + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.graph.J0
    public final K0 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.J0
    final int v0() {
        return 2;
    }

    @Override // com.android.tools.r8.graph.J0
    public final int w0() {
        return this.e;
    }

    @Override // com.android.tools.r8.graph.J0
    public final int x0() {
        return 1;
    }

    @Override // com.android.tools.r8.graph.J0, com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        J0 j0 = (J0) xVar;
        int iA = aVar.a(AbstractC0403b.b(2), AbstractC0403b.b(j0.v0()));
        return iA != 0 ? iA : a(j0, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.K0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((K0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.K0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((K0) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new K0$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.graph.J0
    final int a(J0 j0, com.android.tools.r8.utils.structural.a aVar) {
        if (g || j0.z0()) {
            return aVar.a(this, j0.u0(), new K0$$ExternalSyntheticLambda0());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        v.a(this);
    }

    @Override // com.android.tools.r8.graph.M0
    public final void a(C0583u c0583u, C0645e5 c0645e5, AbstractC1476Wj abstractC1476Wj) {
        c0583u.c(1);
        c0583u.c(this.e);
        for (int i = 0; i < this.e; i++) {
            c0583u.a((C0759u2) null);
        }
        B0.b bVar = c0645e5.a().r;
        if (!B0.b) {
            bVar.getClass();
        }
        bVar.a(c0583u, abstractC1476Wj);
        for (int i2 = 0; i2 < this.f; i2++) {
            B0.b bVar2 = c0645e5.a().s;
            if (!B0.b) {
                bVar2.getClass();
            }
            bVar2.a(c0583u, abstractC1476Wj);
        }
        c0583u.a(0);
    }
}

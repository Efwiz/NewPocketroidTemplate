package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1237Oh;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.JE;
import com.android.tools.r8.internal.KE;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class u4 extends DH {
    static final /* synthetic */ boolean i = true;
    private final C0767v2 f;
    private final C0729q2 g;
    final /* synthetic */ x4 h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public u4(x4 x4Var, C0767v2 c0767v2, C0729q2 c0729q2) {
        super(x4Var.c.a(), AbstractC1476Wj.e());
        this.h = x4Var;
        this.f = c0767v2;
        this.g = c0729q2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 a(C0767v2 c0767v2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final Iterable b(C0767v2 c0767v2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.Y0 c(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return (com.android.tools.r8.graph.Y0) this.h.p.b.getOrDefault(y0, y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.DH
    public final C0767v2 e(C0767v2 c0767v2) {
        return c0767v2 == this.f ? this.g.d : (C0767v2) this.h.m.getOrDefault(c0767v2, c0767v2);
    }

    @Override // com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public final com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.DH, com.android.tools.r8.internal.AbstractC1476Wj
    public final KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj) {
        AbstractC0727q0 abstractC0727q0C;
        KE keA = this.h.c.E().a(c0684j2, c0684j3, enumC1953et, abstractC1476Wj);
        C0684j2 c0684j4 = (C0684j2) this.h.p.c.get(keA.b());
        if (c0684j4 == null) {
            return keA;
        }
        JE jeA = ((JE) KE.d().b(c0684j4)).a(keA.e()).a(keA.f());
        if (keA.f() == EnumC1953et.d && (abstractC0727q0C = this.h.b.c(c0684j4.e)) != null && !abstractC0727q0C.e.W()) {
            if (!i && !this.h.b.c(c0684j2.e).e.W()) {
                throw new AssertionError();
            }
            jeA.a(EnumC1953et.g);
        }
        return jeA.a();
    }

    @Override // com.android.tools.r8.internal.DH
    protected final KE a(KE ke, C0684j2 c0684j2) {
        throw new C3405y10();
    }
}

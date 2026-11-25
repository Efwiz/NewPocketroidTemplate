package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ob, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1230Ob extends AbstractC1287Qb {
    static final /* synthetic */ boolean d = true;
    private final com.android.tools.r8.graph.o5 c;

    private C1230Ob(C1257Pb c1257Pb, com.android.tools.r8.graph.o5 o5Var) {
        super(c1257Pb);
        this.c = o5Var;
    }

    public final C1314Rb a() {
        C0729q2 c0729q2A = this.c.a();
        int i = ((AbstractC1287Qb) this).b;
        ((AbstractC1287Qb) this).b = i + 1;
        C1314Rb c1314Rb = new C1314Rb(this, c0729q2A, i);
        if (!d) {
            ((AbstractC1287Qb) this).a.a(c1314Rb);
        }
        return c1314Rb;
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder b(StringBuilder sb) {
        C1257Pb c1257Pb = ((AbstractC1287Qb) this).a;
        c1257Pb.getClass();
        StringBuilder sbAppend = sb.append('$').append(c1257Pb.b);
        com.android.tools.r8.utils.structural.n nVarB = com.android.tools.r8.utils.structural.n.b();
        this.c.getReference().a(nVarB);
        return sbAppend.append('$').append(nVarB.a());
    }

    public final com.android.tools.r8.graph.o5 b() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder a(StringBuilder sb) {
        sb.append(this.c.a().getType().b1());
        C1257Pb c1257Pb = ((AbstractC1287Qb) this).a;
        c1257Pb.getClass();
        StringBuilder sbAppend = sb.append('$').append(c1257Pb.b);
        com.android.tools.r8.utils.structural.n nVarB = com.android.tools.r8.utils.structural.n.b();
        this.c.getReference().a(nVarB);
        return sbAppend.append('$').append(nVarB.a());
    }
}

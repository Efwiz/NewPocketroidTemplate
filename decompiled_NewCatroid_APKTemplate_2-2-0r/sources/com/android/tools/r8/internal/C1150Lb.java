package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1150Lb extends AbstractC1287Qb {
    static final /* synthetic */ boolean d = true;
    private final N6 c;

    private C1150Lb(C1257Pb c1257Pb, N6 n6) {
        super(c1257Pb);
        this.c = n6;
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder a(StringBuilder sb) {
        C1257Pb c1257Pb = ((AbstractC1287Qb) this).a;
        c1257Pb.getClass();
        return sb.append('$').append(c1257Pb.b).append(this.c.a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder b(StringBuilder sb) {
        C1257Pb c1257Pb = ((AbstractC1287Qb) this).a;
        c1257Pb.getClass();
        return sb.append('$').append(c1257Pb.b).append(this.c.a());
    }

    public final C1314Rb a(C0729q2 c0729q2) {
        int i = ((AbstractC1287Qb) this).b;
        ((AbstractC1287Qb) this).b = i + 1;
        C1314Rb c1314Rb = new C1314Rb(this, c0729q2, i);
        if (!d) {
            ((AbstractC1287Qb) this).a.a(c1314Rb);
        }
        return c1314Rb;
    }
}

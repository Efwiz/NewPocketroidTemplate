package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JP {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final Q4 b;
    private final IdentityHashMap c;

    private JP(C0702m c0702m) {
        this.b = new Q4();
        this.c = new IdentityHashMap();
        this.a = c0702m;
    }

    public final com.android.tools.r8.graph.proto.k a(C0684j2 c0684j2, com.android.tools.r8.graph.W0 w0) {
        if (!d && w0.getReference() == c0684j2) {
            throw new AssertionError();
        }
        this.b.put(w0.getReference(), c0684j2);
        if (w0.l1().equals(c0684j2.G0())) {
            return com.android.tools.r8.graph.proto.k.h();
        }
        int iD1 = w0.d1();
        int iH1 = w0.h1();
        com.android.tools.r8.graph.proto.d dVarA = com.android.tools.r8.graph.proto.e.a(iH1);
        boolean[] zArr = new boolean[iH1];
        for (int i = iD1; i < iH1; i++) {
            C0767v2 c0767v2J = w0.j(i);
            for (int i2 = iD1; i2 < iH1; i2++) {
                if (c0767v2J == c0684j2.a(i2, w0.getAccessFlags().o()) && !zArr[i2]) {
                    dVarA.a(i, i2);
                    zArr[i2] = true;
                    break;
                }
            }
        }
        com.android.tools.r8.graph.proto.e eVarA = dVarA.a();
        if (!d && (eVarA instanceof com.android.tools.r8.graph.proto.g)) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.proto.k kVarA = com.android.tools.r8.graph.proto.k.a(AbstractC1639am.i(), (com.android.tools.r8.graph.proto.l) null, com.android.tools.r8.graph.proto.c.a().a(iH1).a(eVarA).a());
        this.c.put(c0684j2, kVarA);
        return kVarA;
    }

    public final boolean b() {
        return this.b.isEmpty();
    }

    public final KP a() {
        return new KP(this.a, this.b, this.c);
    }
}

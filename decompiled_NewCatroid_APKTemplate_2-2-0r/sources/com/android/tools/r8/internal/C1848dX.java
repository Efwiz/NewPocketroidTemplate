package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1848dX implements InterfaceC2390kX {
    static final /* synthetic */ boolean b = true;
    private final String a;

    C1848dX(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final void a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, QX qx) {
        C0684j2 c0684j2;
        C0684j2 c0684j3;
        AbstractC1332Rs abstractC1332RsC0 = c2561ml.previous().c0();
        boolean z = b;
        if (!z && abstractC1332RsC0 == null) {
            throw new AssertionError();
        }
        S10 s10A = InterfaceC2390kX.a(c0702m, c2409kl, c2561ml, abstractC1332RsC0, this.a);
        c2561ml.next();
        C0684j2 c0684j2S2 = abstractC1332RsC0.S2();
        if (abstractC1332RsC0.a(c0702m.a())) {
            int i = C1166Ls.n;
            C1141Ks c1141Ks = (C1141Ks) new C1141Ks().a(AbstractC1639am.a(abstractC1332RsC0.U2(), s10A));
            C0722p1 c0722p1A = c0702m.a();
            if (c0684j2S2.B0() == c0722p1A.w2) {
                c0684j3 = c0722p1A.X3.q;
            } else {
                if (!AbstractC1772cX.a && c0684j2S2.B0() != c0722p1A.v2) {
                    throw new AssertionError();
                }
                c0684j3 = c0722p1A.W3.q;
            }
            c2561ml.a(((C1141Ks) ((C1141Ks) c1141Ks.a(c0684j3)).a(abstractC1332RsC0.b())).b(), (Set) null);
            return;
        }
        C0722p1 c0722p1A2 = c0702m.a();
        if (c0684j2S2 == c0722p1A2.X3.l || c0684j2S2 == c0722p1A2.W3.l) {
            abstractC1332RsC0.a(1, s10A);
            return;
        }
        int i2 = C2115gt.m;
        C2030ft c2030ft = (C2030ft) new C2030ft().a(AbstractC1639am.a(abstractC1332RsC0.U2(), s10A));
        C0722p1 c0722p1A3 = c0702m.a();
        if (c0684j2S2.B0() == c0722p1A3.w2) {
            c0684j2 = c0722p1A3.X3.l;
        } else {
            if (!z && c0684j2S2.B0() != c0722p1A3.v2) {
                throw new AssertionError();
            }
            c0684j2 = c0722p1A3.W3.l;
        }
        c2561ml.a(((C2030ft) ((C2030ft) c2030ft.a(c0684j2)).a(abstractC1332RsC0.b())).b(), (Set) null);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final boolean b() {
        return true;
    }
}

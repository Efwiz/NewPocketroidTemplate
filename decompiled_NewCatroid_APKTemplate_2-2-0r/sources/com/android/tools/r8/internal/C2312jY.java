package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2312jY extends AbstractC2470lY {
    static final /* synthetic */ boolean j = true;
    private final C3332x4 f;
    private final C3332x4 g;
    private final LinkedHashMap h;
    final /* synthetic */ C2544mY i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C2312jY(C2544mY c2544mY, C2409kl c2409kl, B4 b4, C3332x4 c3332x4, C1620aY c1620aY, Set set) {
        super(c2409kl, b4, c1620aY, set);
        this.i = c2544mY;
        this.f = c3332x4;
        this.g = c1620aY.J2();
        this.h = a(c1620aY);
    }

    private static LinkedHashMap a(C1620aY c1620aY) throws Throwable {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        c1620aY.a(new InterfaceC3890e3() { // from class: com.android.tools.r8.internal.jY$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                linkedHashMap.put((C0759u2) obj, (C3332x4) obj2);
            }
        });
        return linkedHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC2470lY
    final void a() {
        Iterator it = this.f.u().iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).a(this.f, (Set) null);
        }
        this.f.X();
        Set setC = OU.c();
        Set setB = com.android.tools.r8.utils.T2.b(this.h.size());
        for (C3332x4 c3332x4 : this.h.values()) {
            if (!setB.add(c3332x4)) {
                setC.add(c3332x4);
            }
        }
        C3332x4 c3332x5 = null;
        for (Map.Entry entry : this.h.entrySet()) {
            C1033Hc c1033Hc = new C1033Hc(this.a.a((AbstractC3472z00) this.i.c), (C0759u2) entry.getKey());
            c1033Hc.b(this.d);
            C2115gt c2115gt = new C2115gt(this.i.a.a().k4.i, this.a.a((AbstractC3472z00) AbstractC3472z00.k()), AbstractC1639am.a(this.e, c1033Hc.b()));
            c2115gt.b(this.d);
            C1015Gl c1015Gl = new C1015Gl(EnumC1076Il.NE, c2115gt.b());
            c1015Gl.b(AbstractC1915eM.u());
            C3332x4 c3332x6 = (C3332x4) entry.getValue();
            if (setC.contains(c3332x6)) {
                C3332x4 c3332x4A = C3332x4.a(this.a.f.a(), AbstractC1915eM.u(), this.a.t());
                c3332x4A.h(c3332x6);
                this.b.add(c3332x4A);
                this.c.add(c3332x4A);
                c3332x6 = c3332x4A;
            }
            int iA = this.a.f.a();
            C3155ul c3155ulT = this.a.t();
            AbstractC2412ko[] abstractC2412koArr = {c1033Hc, c2115gt};
            C3332x4 c3332x7 = new C3332x4();
            for (int i = 0; i < 2; i++) {
                c3332x7.a(abstractC2412koArr[i], c3155ulT);
            }
            c3332x7.a(c1015Gl, c3155ulT);
            c3332x7.a((C2102gl) null);
            c3332x7.g(iA);
            c3332x7.h(c3332x6);
            this.b.add(c3332x7);
            this.c.add(c3332x7);
            if (c3332x5 == null) {
                AbstractC1954eu abstractC1954euM = this.f.m();
                C1296Qj c1296Qj = new C1296Qj();
                c1296Qj.a(c3332x7);
                abstractC1954euM.h().a(this.a, (AbstractC2412ko) abstractC1954euM).a(c1296Qj, (Set) null);
                this.f.h(c3332x7);
            } else {
                c3332x5.h(c3332x7);
            }
            c3332x5 = c3332x7;
        }
        if (!j && c3332x5 == null) {
            throw new AssertionError();
        }
        c3332x5.h(this.g);
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.io.UTFDataFormatException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2391kY extends AbstractC2470lY {
    static final /* synthetic */ boolean m = true;
    private final C3332x4 f;
    private final C3332x4 g;
    private final C3332x4 h;
    private final C3332x4 i;
    C2261ir j;
    private int k;
    final /* synthetic */ C2544mY l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C2391kY(C2544mY c2544mY, C2409kl c2409kl, B4 b4, C3332x4 c3332x4, C1620aY c1620aY, Set set) {
        super(c2409kl, b4, c1620aY, set);
        this.l = c2544mY;
        this.f = c3332x4;
        this.g = c1620aY.J2();
        C3332x4 c3332x4A = c1620aY.J2().A();
        this.h = c3332x4A;
        this.i = c3332x4A.A();
        this.j = a(c1620aY);
    }

    private static C2261ir a(C1620aY c1620aY) throws Throwable {
        final C2261ir c2261ir = new C2261ir();
        c1620aY.a(new InterfaceC3890e3() { // from class: com.android.tools.r8.internal.kY$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) throws UTFDataFormatException {
                C2391kY.a(c2261ir, (C0759u2) obj, (C3332x4) obj2);
            }
        });
        return c2261ir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC0992Fq interfaceC0992Fq, C0759u2 c0759u2, C3332x4 c3332x4) throws UTFDataFormatException {
        int iU0 = c0759u2.u0();
        if (interfaceC0992Fq.a(iU0)) {
            ((Map) interfaceC0992Fq.get(iU0)).put(c0759u2, c3332x4);
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(c0759u2, c3332x4);
        interfaceC0992Fq.a(iU0, linkedHashMap);
    }

    @Override // com.android.tools.r8.internal.AbstractC2470lY
    final void a() {
        Iterator it = this.f.u().iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).a(this.f, (Set) null);
        }
        this.f.X();
        C3332x4 c3332x4 = this.f;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(this.a, c3332x4.a0());
        interfaceC2641noA.previous();
        C2409kl c2409kl = this.a;
        C3332x4 c3332x5 = this.h;
        C1484Wr c1484WrK = AbstractC3472z00.k();
        c2409kl.getClass();
        ML ml = new ML(c2409kl.e.a(), c3332x5, c1484WrK, null, ML.a.a);
        S10 s10A = interfaceC2641noA.a(this.a, this.l.a.Y(), -1);
        ml.h(s10A);
        C2115gt c2115gt = new C2115gt(this.l.a.a().k4.r, this.a.a((AbstractC3472z00) AbstractC3472z00.k()), AbstractC1639am.c(this.e));
        c2115gt.b(this.d);
        interfaceC2641noA.add(c2115gt);
        InterfaceC1912eK it2 = this.j.values().iterator();
        while (it2.hasNext()) {
            Map map = (Map) it2.next();
            C3332x4 c3332x4A = C3332x4.a(this.a.f.a(), this.d, this.a.t());
            this.b.add(c3332x4A);
            this.f.h(c3332x4A);
            C3332x4 c3332x6 = (C3332x4) this.b.previous();
            if (!m && c3332x6 != c3332x4A) {
                throw new AssertionError();
            }
            this.b.next();
            for (Map.Entry entry : map.entrySet()) {
                c3332x4A.s().clear();
                InterfaceC2641no interfaceC2641noA2 = c3332x4A.a(this.a);
                S10 s10A2 = interfaceC2641noA2.a(this.l.a, this.a, (C0759u2) entry.getKey());
                this.c.add(c3332x4A);
                C2115gt c2115gt2 = new C2115gt(this.l.a.a().k4.i, this.a.a((AbstractC3472z00) AbstractC3472z00.k()), AbstractC1639am.a(this.e, s10A2));
                c2115gt2.b(this.d);
                interfaceC2641noA2.add(c2115gt2);
                C3332x4 c3332x4A2 = C3332x4.a(this.a.f.a(), this.d, this.a.t(), this.h);
                this.h.r().add(c3332x4A2);
                this.b.add(c3332x4A2);
                c3332x4A.h(c3332x4A2);
                InterfaceC2641no interfaceC2641noA3 = c3332x4A2.a(this.a);
                C2409kl c2409kl2 = this.a;
                com.android.tools.r8.utils.A1 a1Y = this.l.a.Y();
                int i = this.k;
                this.k = i + 1;
                ml.h(interfaceC2641noA3.a(c2409kl2, a1Y, i));
                C3332x4 c3332x4A3 = C3332x4.a(this.a.f.a(), this.d, this.a.t(), this.h);
                this.b.add(c3332x4A3);
                c3332x4A.h(c3332x4A3);
                interfaceC2641noA2.next();
                interfaceC2641noA2.d(new C1015Gl(EnumC1076Il.NE, c2115gt2.b()));
                c3332x4A = c3332x4A3;
            }
            ml.h(s10A);
            this.h.r().add(c3332x4A);
        }
        this.f.h(this.g);
        S10 s10B = c2115gt.b();
        int i2 = 0;
        int[] iArrA = ((J) this.j.keySet()).a(new int[0]);
        int length = iArrA.length;
        int[] iArr = new int[length];
        int iV = this.f.V();
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = i3 + iV;
        }
        C1458Vr c1458Vr = new C1458Vr(this.f.z().size() - 1, s10B, iArrA, iArr);
        interfaceC2641noA.next();
        interfaceC2641noA.d(c1458Vr);
        MR mr = new MR();
        mr.e(-1);
        this.h.s().clear();
        InterfaceC1912eK it3 = this.j.values().iterator();
        while (it3.hasNext()) {
            for (C3332x4 c3332x7 : ((Map) it3.next()).values()) {
                if (mr.a(c3332x7) == -1) {
                    mr.a(this.h.z().size(), c3332x7);
                    this.h.h(c3332x7);
                }
            }
        }
        this.h.s().add(this.i);
        int i4 = this.k;
        int i5 = com.android.tools.r8.utils.B.b;
        int[] iArr2 = new int[i4];
        for (int i6 = 0; i6 < i4; i6++) {
            iArr2[i6] = i6;
        }
        int[] iArr3 = new int[i4];
        InterfaceC1912eK it4 = this.j.values().iterator();
        while (it4.hasNext()) {
            Iterator it5 = ((Map) it4.next()).entrySet().iterator();
            while (it5.hasNext()) {
                iArr3[i2] = mr.a(((Map.Entry) it5.next()).getValue());
                i2++;
            }
        }
        C1458Vr c1458Vr2 = new C1458Vr(mr.size(), ml, iArr2, iArr3);
        InterfaceC2641no interfaceC2641noA4 = this.h.a(this.a);
        interfaceC2641noA4.next();
        interfaceC2641noA4.d(c1458Vr2);
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3340xC {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C2409kl b;
    private final Q00 c;
    private IdentityHashMap d = null;
    private B4 e = null;

    public C3340xC(C0702m c0702m, C2409kl c2409kl, Q00 q00) {
        this.a = c0702m;
        this.b = c2409kl;
        this.c = q00;
    }

    public final void a() {
        this.d = new IdentityHashMap();
        this.e = this.b.s();
        while (this.e.hasNext()) {
            InterfaceC2641no interfaceC2641noA = this.e.next().a(this.b);
            while (interfaceC2641noA.hasNext()) {
                interfaceC2641noA.next().a(interfaceC2641noA, this);
            }
            this.d.clear();
        }
        this.d = null;
        this.e = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0090, code lost:
    
        if (r3 != 1.0d) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a7, code lost:
    
        if (r3 != 1) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void b(com.android.tools.r8.internal.AbstractC2412ko r8, com.android.tools.r8.internal.InterfaceC2641no r9) {
        /*
            Method dump skipped, instruction units count: 441
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C3340xC.b(com.android.tools.r8.internal.ko, com.android.tools.r8.internal.no):void");
    }

    public final void a(C2971s8 c2971s8) {
        for (C3332x4 c3332x4 : this.b.d) {
            if (!c3332x4.x().isEmpty()) {
                if (!f) {
                    AbstractC2412ko abstractC2412koK = c3332x4.k();
                    abstractC2412koK.getClass();
                    if (abstractC2412koK instanceof AF) {
                        throw new AssertionError();
                    }
                }
                for (int i = 0; i < c3332x4.y().size(); i++) {
                    C3332x4 c3332x5 = c3332x4.y().get(i);
                    List<ML> listX = c3332x4.x();
                    ArrayList<C3265wC> arrayList = new ArrayList(listX.size());
                    for (ML ml : listX) {
                        if (ml.Y()) {
                            S10 s10C = ml.c(i);
                            if (c2971s8.a(ml) != c2971s8.a(s10C)) {
                                arrayList.add(new C3265wC(ml, s10C));
                            }
                        }
                    }
                    InterfaceC2641no interfaceC2641noA = c3332x5.a(this.b, c3332x5.p().size());
                    AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
                    if (!f && c3332x5.m() != abstractC2412koPrevious) {
                        throw new AssertionError();
                    }
                    AbstractC1915eM abstractC1915eMY0 = abstractC2412koPrevious.Y0();
                    ArrayList arrayList2 = new ArrayList(arrayList.size());
                    int i2 = 0;
                    for (C3265wC c3265wC : arrayList) {
                        int i3 = i2 + 1;
                        C2617nW c2617nWA = C2617nW.a(this.c.b(c3265wC.a), i2, this.a);
                        AbstractC2412ko abstractC2412koA = a(c2617nWA, c3265wC.b);
                        abstractC2412koA.a(c3265wC.a.h());
                        abstractC2412koA.b(abstractC1915eMY0);
                        interfaceC2641noA.add(abstractC2412koA);
                        arrayList2.add(c2617nWA);
                        c3265wC.b.c(c3265wC.a);
                        i2 = i3;
                    }
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        C3265wC c3265wC2 = (C3265wC) arrayList.get(size);
                        C2617nW c2617nW = (C2617nW) arrayList2.get(size);
                        C2723oi c2723oi = new C2723oi(c3265wC2.a);
                        SW sw = new SW(c2617nW, c2723oi);
                        sw.a(c3265wC2.a.h());
                        sw.b(abstractC1915eMY0);
                        interfaceC2641noA.add(sw);
                        c3265wC2.a.g(c2723oi);
                    }
                }
                c2971s8.a(c3332x4, c3332x4.x());
            }
        }
        this.b.d.forEach(new xC$$ExternalSyntheticLambda0());
    }

    public final void a(C0767v2 c0767v2, AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no) {
        C2617nW c2617nWA = C2617nW.a(this.c.a(c0767v2), 0, this.a);
        C3332x4 c3332x4H = abstractC2412ko.h();
        if (c3332x4H.D() && abstractC2412ko.g()) {
            c3332x4H = interfaceC2641no.a(this.b, this.e);
            interfaceC2641no = c3332x4H.a(this.b);
        }
        abstractC2412ko.g(c2617nWA);
        ZL zl = new ZL(c2617nWA);
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
        zl.a(c3332x4H);
        zl.b(abstractC1915eMY0);
        interfaceC2641no.add(zl);
    }

    public final void a(AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no) {
        AbstractC2412ko abstractC2412koA;
        interfaceC2641no.previous();
        int i = 0;
        int i2 = 0;
        while (i < ((ArrayList) abstractC2412ko.h1()).size()) {
            S10 s10 = (S10) ((ArrayList) abstractC2412ko.h1()).get(i);
            int i3 = i2 + 1;
            C2617nW c2617nWA = C2617nW.a(this.c.b(s10), i2, this.a);
            if (!f && this.d == null) {
                throw new AssertionError();
            }
            AbstractC0824Ac abstractC0824Ac = (AbstractC0824Ac) this.d.get(s10);
            if (abstractC0824Ac != null) {
                int i4 = AbstractC0824Ac.h;
                if (abstractC0824Ac instanceof C3446yc) {
                    abstractC2412koA = C3446yc.a(c2617nWA, abstractC0824Ac.A());
                } else if (abstractC0824Ac instanceof C0889Cc) {
                    abstractC2412koA = C0889Cc.a(c2617nWA, abstractC0824Ac.C());
                } else if (abstractC0824Ac instanceof C0918Dc) {
                    abstractC2412koA = C0918Dc.a(c2617nWA, abstractC0824Ac.D());
                } else if (abstractC0824Ac.B1()) {
                    abstractC2412koA = C0978Fc.a(c2617nWA, abstractC0824Ac.E());
                } else if (abstractC0824Ac instanceof C1033Hc) {
                    abstractC2412koA = C1033Hc.a(c2617nWA, abstractC0824Ac.F());
                } else if (abstractC0824Ac instanceof C2477lf) {
                    abstractC2412koA = C2477lf.a(c2617nWA, abstractC0824Ac.J());
                } else {
                    throw new C3405y10();
                }
                C3332x4 c3332x4H = abstractC2412ko.h();
                AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
                abstractC2412koA.a(c3332x4H);
                abstractC2412koA.b(abstractC1915eMY0);
                interfaceC2641no.add(abstractC2412koA);
            } else {
                AbstractC2412ko abstractC2412koA2 = a(c2617nWA, s10);
                C3332x4 c3332x4H2 = abstractC2412ko.h();
                AbstractC1915eM abstractC1915eMY1 = abstractC2412ko.Y0();
                abstractC2412koA2.a(c3332x4H2);
                abstractC2412koA2.b(abstractC1915eMY1);
                interfaceC2641no.add(abstractC2412koA2);
            }
            abstractC2412ko.a(i, c2617nWA);
            i++;
            i2 = i3;
        }
        interfaceC2641no.next();
    }

    public final void a(InterfaceC2641no interfaceC2641no) {
        interfaceC2641no.a(this.b, this.e);
        this.e.previous();
    }

    private static AbstractC2412ko a(C2617nW c2617nW, S10 s10) {
        if (s10.L()) {
            AbstractC0824Ac abstractC0824AcN = s10.n();
            if (abstractC0824AcN.B1()) {
                return new C0978Fc(c2617nW, abstractC0824AcN.E().P2());
            }
            if (abstractC0824AcN.C1()) {
                return new C1033Hc(c2617nW, abstractC0824AcN.F().J2());
            }
            if (abstractC0824AcN.I1()) {
                C2477lf c2477lfJ = abstractC0824AcN.J();
                return new C2477lf(c2617nW, c2477lfJ.J2(), c2477lfJ.K2());
            }
            if (abstractC0824AcN.x1()) {
                return new C3446yc(abstractC0824AcN.A().K2(), c2617nW);
            }
            throw new C3405y10("Unexpected constant value: " + s10);
        }
        return new C3044tC(c2617nW, s10);
    }
}

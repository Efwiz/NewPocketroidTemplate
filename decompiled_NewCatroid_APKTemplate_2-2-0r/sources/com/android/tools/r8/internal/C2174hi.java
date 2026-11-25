package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2174hi {
    static final /* synthetic */ boolean h = true;
    private final com.android.tools.r8.graph.Y0 a;
    private final C2409kl b;
    private final AbstractC2412ko c;
    private final C0702m d;
    private S10 e = null;
    private final IdentityHashMap f = new IdentityHashMap();
    private final IdentityHashMap g = new IdentityHashMap();

    C2174hi(com.android.tools.r8.graph.Y0 y0, C2409kl c2409kl, AbstractC2412ko abstractC2412ko, C0702m c0702m) {
        this.a = y0;
        this.b = c2409kl;
        this.c = abstractC2412ko;
        this.d = c0702m;
        boolean z = h;
        if (!z && !abstractC2412ko.d1()) {
            throw new AssertionError();
        }
        if (!z && abstractC2412ko.b() != abstractC2412ko.b().m()) {
            throw new AssertionError();
        }
    }

    final void a(S10 s10, S10 s11) {
        for (Map.Entry entry : this.f.entrySet()) {
            if (entry.getValue() == s10) {
                entry.setValue(s11);
            }
        }
        for (Map.Entry entry2 : this.g.entrySet()) {
            if (entry2.getValue() == s10) {
                entry2.setValue(s11);
            }
        }
    }

    final S10 b(C3332x4 c3332x4, AbstractC2412ko abstractC2412ko) {
        if (!h && abstractC2412ko == null) {
            throw new AssertionError();
        }
        S10 s10A = a(c3332x4, abstractC2412ko);
        return s10A != null ? s10A : a(c3332x4);
    }

    private S10 a(C3332x4 c3332x4) {
        S10 s10;
        S10 s11;
        S10 s10A;
        S10 s12 = (S10) this.f.get(c3332x4);
        if (s12 != null) {
            return s12;
        }
        List<C3332x4> listY = c3332x4.y();
        if (listY.size() == 1) {
            C3332x4 c3332x5 = listY.get(0);
            S10 s13 = (S10) this.g.get(c3332x5);
            if (s13 == null) {
                S10 s10A2 = a(c3332x5, (AbstractC2412ko) null);
                if (s10A2 == null) {
                    s11 = s13;
                    s10A = s10A2;
                    s10A = a(c3332x5);
                }
                s11 = s13;
                s10A = s10A2;
                if (!h && s10A == null) {
                    throw new AssertionError();
                }
                this.g.put(c3332x5, s10A);
                s11 = s10A;
            }
            s11 = s13;
            this.f.put(c3332x4, s11);
            s10 = s11;
        } else {
            ML ml = new ML(this.b.e.a(), c3332x4, AbstractC3472z00.a(this.a.h, WH.i(), (C0702m<?>) this.d), null, ML.a.a);
            this.f.put(c3332x4, ml);
            ArrayList arrayList = new ArrayList();
            for (C3332x4 c3332x6 : c3332x4.y()) {
                S10 s10A3 = (S10) this.g.get(c3332x6);
                if (s10A3 == null) {
                    s10A3 = a(c3332x6, (AbstractC2412ko) null);
                    if (s10A3 == null) {
                        s10A3 = a(c3332x6);
                    }
                    if (!h && s10A3 == null) {
                        throw new AssertionError();
                    }
                    this.g.put(c3332x6, s10A3);
                }
                arrayList.add(s10A3);
            }
            ml.a((List) arrayList, false);
            AbstractC3472z00 abstractC3472z00C = ml.c(this.d);
            if (!h && !abstractC3472z00C.a(ml.w(), this.d)) {
                throw new AssertionError();
            }
            ml.a(abstractC3472z00C);
            s10 = ml;
        }
        if (h || s10 != null) {
            return s10;
        }
        throw new AssertionError();
    }

    private S10 a(C3332x4 c3332x4, AbstractC2412ko abstractC2412ko) {
        AbstractC2412ko abstractC2412koPrevious;
        C3479z4 c3479z4D = abstractC2412ko == null ? c3332x4.d(c3332x4.p().size()) : c3332x4.d(abstractC2412ko);
        while (true) {
            if (!c3479z4D.hasPrevious()) {
                abstractC2412koPrevious = null;
                break;
            }
            abstractC2412koPrevious = c3479z4D.previous();
            if (!h && abstractC2412koPrevious == null) {
                throw new AssertionError();
            }
            if (abstractC2412koPrevious == this.c || (abstractC2412koPrevious.S1() && abstractC2412koPrevious.V().getField() == this.a && abstractC2412koPrevious.V().e().m() == this.c.b())) {
                break;
            }
        }
        if (abstractC2412koPrevious == null) {
            return null;
        }
        if (abstractC2412koPrevious.S1()) {
            return abstractC2412koPrevious.V().value();
        }
        if (!h && this.c != abstractC2412koPrevious) {
            throw new AssertionError();
        }
        if (this.e == null) {
            A4 a4A = c3332x4.a(this.b, this.c);
            if (this.a.h.W0()) {
                this.e = this.b.a(AbstractC3472z00.a(this.a.h, WH.b(), (C0702m<?>) this.d));
                C0978Fc c0978Fc = new C0978Fc(this.e, 0L);
                c0978Fc.b(this.c.Y0());
                a4A.add(c0978Fc);
            } else {
                this.e = a4A.a(this.b, this.d.Y());
            }
        }
        return this.e;
    }
}

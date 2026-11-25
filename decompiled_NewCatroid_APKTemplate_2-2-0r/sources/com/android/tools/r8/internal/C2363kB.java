package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0691k2;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2363kB {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.graph.Q0 a;
    private final AbstractC1476Wj b;
    private final AbstractC1476Wj c;
    private final ConcurrentHashMap d;
    private final ConcurrentHashMap e;

    public C2363kB(C0702m<?> c0702m) {
        this(c0702m, c0702m.E(), c0702m.n());
    }

    private C0721p0 b(C0721p0 c0721p0, com.android.tools.r8.graph.o5 o5Var) {
        C0759u2 c0759u2C0;
        C0722p1 c0722p1A = this.a.a();
        C0737r2 c0737r2A = a(c0721p0.e);
        C0699l2 c0699l2 = c0721p0.f;
        com.android.tools.r8.graph.I5 i5 = com.android.tools.r8.graph.I5.b;
        C0699l2 c0699l2A = a(c0699l2, i5, o5Var);
        C0684j2 c0684j2U0 = c0721p0.f.u0();
        boolean z = c0684j2U0 == c0722p1A.I5 || c0684j2U0 == c0722p1A.J5;
        if (z) {
            i5 = com.android.tools.r8.graph.I5.a;
        }
        List listA = a(c0721p0.g, i5, o5Var);
        if (z) {
            boolean z2 = f;
            if (!z2 && c0721p0.g.size() <= 0) {
                throw new AssertionError();
            }
            if (!z2) {
                com.android.tools.r8.graph.B2 b2 = (com.android.tools.r8.graph.B2) c0721p0.g.get(0);
                b2.getClass();
                if (!(b2 instanceof com.android.tools.r8.graph.H2)) {
                    throw new AssertionError();
                }
            }
            C0722p1 c0722p1A2 = this.a.a();
            int i = JA.n;
            c0759u2C0 = ((C0684j2) this.b.a(c0722p1A2.a(c0721p0.e.e, (C0737r2) ((com.android.tools.r8.graph.B2) c0721p0.g.get(0)).H0().b, c0721p0.d), o5Var.getReference(), EnumC1953et.d, this.c).b()).C0();
        } else {
            c0759u2C0 = c0721p0.d;
        }
        return (c0737r2A.equals(c0721p0.e) && c0759u2C0 == c0721p0.d && c0699l2A == c0721p0.f && listA.equals(c0721p0.g)) ? c0721p0 : C0722p1.a(c0759u2C0, c0737r2A, c0699l2A, listA);
    }

    public final C0722p1 a() {
        return this.a.a();
    }

    public C2363kB(C0702m c0702m, int i) {
        this.d = new ConcurrentHashMap();
        this.a = c0702m;
        this.b = c0702m.E();
        this.c = c0702m.n();
        this.e = new ConcurrentHashMap();
    }

    public final C0721p0 a(final C0721p0 c0721p0, final com.android.tools.r8.graph.o5 o5Var) {
        ConcurrentHashMap concurrentHashMap = this.e;
        return concurrentHashMap == null ? b(c0721p0, o5Var) : (C0721p0) concurrentHashMap.computeIfAbsent(c0721p0, new Function() { // from class: com.android.tools.r8.internal.kB$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0721p0, o5Var, (C0721p0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0721p0 a(C0721p0 c0721p0, com.android.tools.r8.graph.o5 o5Var, C0721p0 c0721p1) {
        return b(c0721p0, o5Var);
    }

    public final List a(List list, com.android.tools.r8.graph.I5 i5, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.H2 h2H0;
        com.android.tools.r8.graph.B2 h2;
        com.android.tools.r8.graph.G2 g2G0;
        C0767v2 c0767v2;
        C0767v2 c0767v2A;
        com.android.tools.r8.graph.B2.k kVar;
        ArrayList arrayList = null;
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            com.android.tools.r8.graph.B2 b2 = (com.android.tools.r8.graph.B2) list.get(i);
            int iOrdinal = b2.N0().ordinal();
            if (iOrdinal == 7) {
                h2H0 = b2.H0();
                C0737r2 c0737r2 = (C0737r2) h2H0.b;
                C0737r2 c0737r2A = a(c0737r2);
                if (c0737r2A != c0737r2) {
                    h2 = h2H0;
                    h2 = new com.android.tools.r8.graph.H2(c0737r2A);
                }
            } else if (iOrdinal == 8) {
                g2G0 = b2.G0();
                C0699l2 c0699l2 = (C0699l2) g2G0.b;
                C0699l2 c0699l2A = a(c0699l2, i5, o5Var);
                if (c0699l2A != c0699l2) {
                    h2 = g2G0;
                    h2 = new com.android.tools.r8.graph.G2(c0699l2A);
                }
            } else if (iOrdinal == 10 && (c0767v2A = this.b.a(this.c, (c0767v2 = (C0767v2) b2.L0().b))) != c0767v2) {
                kVar = new com.android.tools.r8.graph.B2.k(c0767v2A);
            } else {
                h2 = b2;
            }
            if (h2 != b2) {
                if (arrayList == null) {
                    arrayList = new ArrayList(list.subList(0, i));
                }
                arrayList.add(h2);
                z = true;
            } else if (arrayList != null) {
                arrayList.add(h2);
            }
        }
        return z ? arrayList : list;
    }

    public C2363kB(com.android.tools.r8.graph.Q0 q0, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        this.d = new ConcurrentHashMap();
        this.a = q0;
        this.b = abstractC1476Wj;
        this.c = abstractC1476Wj2;
        this.e = null;
    }

    public final C0737r2 a(C0737r2 c0737r2) {
        return this.a.a().a(c0737r2, new Function() { // from class: com.android.tools.r8.internal.kB$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 a(C0767v2 c0767v2) {
        return this.b.a(this.c, c0767v2);
    }

    public final C0699l2 a(C0699l2 c0699l2, com.android.tools.r8.graph.I5 i5, com.android.tools.r8.graph.o5 o5Var) {
        C0684j2 c0684j2;
        EnumC0691k2 enumC0691k2A;
        if (c0699l2.d.h()) {
            C0684j2 c0684j2U0 = c0699l2.u0();
            EnumC0691k2 enumC0691k2 = c0699l2.d;
            KE keA = this.b.a(c0684j2U0, o5Var.getReference(), enumC0691k2.i(), this.c);
            C0684j2 c0684j3 = (C0684j2) keA.b();
            if (i5 == com.android.tools.r8.graph.I5.a) {
                enumC0691k2A = keA.f().a(c0684j3);
                c0684j2 = c0684j3;
            } else {
                boolean z = f;
                if (!z && i5 != com.android.tools.r8.graph.I5.b) {
                    throw new AssertionError();
                }
                C0684j2 c0684j2A = this.a.a().a(this.b.a(this.c, c0684j2U0.e), c0684j3.h, c0684j3.f);
                if (!enumC0691k2.d()) {
                    c0684j2 = c0684j2A;
                    enumC0691k2A = enumC0691k2;
                } else {
                    if (!z && c0684j3.e != c0684j2A.e) {
                        throw new AssertionError();
                    }
                    EnumC0691k2 enumC0691k2A2 = keA.f().a(c0684j2A);
                    if (!z && enumC0691k2A2 != EnumC0691k2.i && enumC0691k2A2 != EnumC0691k2.g) {
                        throw new AssertionError();
                    }
                    c0684j2 = c0684j2A;
                    enumC0691k2A = enumC0691k2A2;
                }
            }
            if (enumC0691k2A == enumC0691k2 && c0684j2 == c0684j2U0 && c0684j3 == c0684j2) {
                return c0699l2;
            }
            AbstractC0727q0 abstractC0727q0A = this.a.a(c0684j2.e, o5Var);
            return this.a.a().a(enumC0691k2A, c0684j2, abstractC0727q0A != null ? abstractC0727q0A.isInterface() : c0699l2.f, c0684j3 != c0684j2 ? c0684j3 : null);
        }
        com.android.tools.r8.graph.Y0 y0T0 = c0699l2.t0();
        com.android.tools.r8.graph.Y0 y0C = this.b.c(this.c, y0T0);
        return y0C != y0T0 ? this.a.a().a(c0699l2.d, y0C, c0699l2.f, (C0684j2) null) : c0699l2;
    }
}

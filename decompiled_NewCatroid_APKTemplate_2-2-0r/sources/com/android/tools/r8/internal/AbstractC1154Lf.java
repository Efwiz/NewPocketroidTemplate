package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1154Lf {
    static final /* synthetic */ boolean a = true;
    public static final /* synthetic */ int b = 0;

    public static C1207Nf b() {
        return C1207Nf.d;
    }

    public static C1207Nf c() {
        return C1207Nf.e;
    }

    public static C1207Nf n() {
        return C1207Nf.f;
    }

    public abstract AbstractC1154Lf a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set);

    public C1207Nf a() {
        return null;
    }

    public abstract AbstractC3472z00 a(AbstractC3472z00 abstractC3472z00);

    public C1229Oa d() {
        return null;
    }

    public abstract C1229Oa e();

    public abstract boolean equals(Object obj);

    public abstract WH f();

    public boolean g() {
        return false;
    }

    public boolean h() {
        return false;
    }

    public boolean i() {
        return false;
    }

    public boolean j() {
        return e() != null;
    }

    public boolean k() {
        return false;
    }

    public boolean l() {
        return false;
    }

    public boolean m() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x001b  */
    public static C1207Nf a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        C1229Oa c1229OaB;
        if (abstractC3472z00.w()) {
            c1229OaB = abstractC3472z00.b();
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c1229OaB.R());
            if (abstractC0727q0C == null || !abstractC0727q0C.c(c0702m)) {
                c1229OaB = null;
            }
        } else {
            c1229OaB = null;
        }
        return a(c0702m, abstractC3472z00, c1229OaB);
    }

    public static C1207Nf a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, C1229Oa c1229Oa) {
        abstractC3472z00.getClass();
        if (abstractC3472z00 instanceof G5) {
            return C1207Nf.d;
        }
        if (abstractC3472z00 instanceof AS) {
            return C1207Nf.e;
        }
        if (abstractC3472z00 instanceof C2163hZ) {
            return C1207Nf.f;
        }
        if (c1229Oa != null) {
            boolean z = a;
            if (!z && !abstractC3472z00.w()) {
                throw new AssertionError();
            }
            if (!z && abstractC3472z00.N() != c1229Oa.N()) {
                throw new AssertionError();
            }
            if (abstractC3472z00.equals(c1229Oa)) {
                return new C2557mh(c1229Oa);
            }
            return C1180Mf.a(c0702m, abstractC3472z00.b(), c1229Oa);
        }
        boolean z2 = a;
        if (!z2 && abstractC3472z00.w()) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(abstractC3472z00.b().R());
            if (!z2 && abstractC0727q0C != null && abstractC0727q0C.c(c0702m)) {
                throw new AssertionError();
            }
        }
        return new C1207Nf(abstractC3472z00);
    }

    public static C2557mh a(C1229Oa c1229Oa) {
        return new C2557mh(c1229Oa);
    }

    public static C1207Nf a(C0702m c0702m, S10 s10) {
        if (!a && !s10.w().I()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00A = s10.a(c0702m);
        return a(c0702m, abstractC3472z00A, s10.a(c0702m, abstractC3472z00A, abstractC3472z00A.N()));
    }

    public static AbstractC1154Lf a(ArrayList arrayList, C0702m c0702m) {
        AbstractC1154Lf abstractC1154LfA = C1207Nf.d;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            abstractC1154LfA = abstractC1154LfA.a(c0702m, (AbstractC1154Lf) it.next());
        }
        return abstractC1154LfA;
    }

    public final AbstractC1154Lf a(C0702m c0702m, AbstractC1154Lf abstractC1154Lf) {
        if (h()) {
            return abstractC1154Lf;
        }
        if (abstractC1154Lf.h() || equals(abstractC1154Lf)) {
            return this;
        }
        if (!m() && !abstractC1154Lf.m()) {
            if (!k() && !abstractC1154Lf.k()) {
                boolean z = a;
                if (!z && !i()) {
                    throw new AssertionError();
                }
                if (z || abstractC1154Lf.i()) {
                    return a().a(c0702m, abstractC1154Lf.a());
                }
                throw new AssertionError();
            }
            if (!f().h() && !abstractC1154Lf.f().h()) {
                return OH.o();
            }
            return C1207Nf.f;
        }
        return C1207Nf.f;
    }
}

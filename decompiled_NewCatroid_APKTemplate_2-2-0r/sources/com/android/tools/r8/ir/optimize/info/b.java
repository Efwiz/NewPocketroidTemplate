package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1052Hq;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC2168hc;
import com.android.tools.r8.internal.AbstractC3122uL;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C1207Nf;
import com.android.tools.r8.internal.C1853dc;
import com.android.tools.r8.internal.C2582n10;
import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.internal.wH$$ExternalSyntheticLambda0;
import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class b extends a {
    static final /* synthetic */ boolean d = true;
    private final int a;
    private final InterfaceC0992Fq b;
    private final InterfaceC0992Fq c;

    private b(int i, InterfaceC0992Fq interfaceC0992Fq, InterfaceC0992Fq interfaceC0992Fq2) {
        boolean z = d;
        if (!z && i <= 0) {
            throw new AssertionError();
        }
        if (!z && !interfaceC0992Fq2.values().stream().noneMatch(new wH$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        if (!z && !interfaceC0992Fq.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.b$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC1154Lf) obj).m();
            }
        })) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = (InterfaceC0992Fq) Objects.requireNonNull(interfaceC0992Fq);
        this.c = (InterfaceC0992Fq) Objects.requireNonNull(interfaceC0992Fq2);
    }

    @Override // com.android.tools.r8.ir.optimize.info.a
    public final b a() {
        return this;
    }

    @Override // com.android.tools.r8.ir.optimize.info.a
    public final AbstractC1154Lf b(int i) {
        if (d || (i >= 0 && i < this.a)) {
            return (AbstractC1154Lf) this.b.getOrDefault(Integer.valueOf(i), AbstractC1154Lf.n());
        }
        throw new AssertionError();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.b.equals(bVar.b) && this.c.equals(bVar.c);
    }

    public final int hashCode() {
        return System.identityHashCode(this.c) + (System.identityHashCode(this.b) * 7);
    }

    public final String toString() {
        String str;
        String string = this.b.toString();
        if (this.c == null) {
            str = "";
        } else {
            str = System.lineSeparator() + this.c;
        }
        return string + str;
    }

    public final a a(com.android.tools.r8.graph.proto.k kVar) {
        if (kVar.g()) {
            return this;
        }
        com.android.tools.r8.graph.proto.c cVarA = kVar.a();
        if (cVarA.g()) {
            if (!kVar.e()) {
                return this;
            }
            return new b(kVar.i() + this.a, this.b, this.c);
        }
        if (!d && !cVarA.c().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.b$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((Integer) obj);
            }
        })) {
            throw new AssertionError();
        }
        int iH = this.a - cVarA.h();
        if (iH == 0) {
            return s.d();
        }
        InterfaceC0992Fq c3017sq = new C3017sq(iH);
        InterfaceC0992Fq c3017sq2 = new C3017sq(iH);
        int i = 0;
        for (int i2 = 0; i2 < this.a; i2++) {
            if (!cVarA.a(i2).c()) {
                InterfaceC0992Fq interfaceC0992Fq = this.c;
                Integer numValueOf = Integer.valueOf(i2);
                int i3 = B1.b;
                B1 b1 = (B1) interfaceC0992Fq.getOrDefault(numValueOf, C2892r10.F());
                if (!b1.isUnknown()) {
                    c3017sq.a(i, b1);
                }
                AbstractC1154Lf abstractC1154Lf = (AbstractC1154Lf) this.b.get(i2);
                if (abstractC1154Lf != null) {
                    c3017sq2.a(i, abstractC1154Lf);
                }
                i++;
            }
        }
        int i4 = kVar.i() + iH;
        if (c3017sq.isEmpty() && c3017sq2.isEmpty()) {
            return s.d();
        }
        if (c3017sq2.isEmpty()) {
            c3017sq2 = AbstractC1052Hq.a;
        }
        if (c3017sq.isEmpty()) {
            c3017sq = AbstractC1052Hq.a;
        }
        return new b(i4, c3017sq2, c3017sq);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Integer num) {
        return num.intValue() < this.a;
    }

    @Override // com.android.tools.r8.ir.optimize.info.a
    public final B1 a(int i) {
        if (d || (i >= 0 && i < this.a)) {
            return (B1) this.c.getOrDefault(Integer.valueOf(i), C2892r10.F());
        }
        throw new AssertionError();
    }

    public static a a(C0702m c0702m, o5 o5Var, C1853dc c1853dc) {
        int iO = c1853dc.o();
        b bVar = new b(iO, new C3017sq(iO), new C3017sq(iO));
        boolean z = true;
        for (int i = 0; i < c1853dc.o(); i++) {
            AbstractC3122uL abstractC3122uLA = c1853dc.a(i);
            abstractC3122uLA.getClass();
            if (!(abstractC3122uLA instanceof C2582n10)) {
                AbstractC2168hc abstractC2168hcA = abstractC3122uLA.a();
                B1 b1A = abstractC2168hcA.a(c0702m);
                if (b1A.y()) {
                    bVar.c.a(i, b1A);
                    z = false;
                }
                C0767v2 c0767v2J = o5Var.e().j(i);
                if (c0767v2J.X0()) {
                    C1207Nf c1207NfA = AbstractC1154Lf.a(c0702m, AbstractC3472z00.a(c0767v2J, WH.i(), (C0702m<?>) c0702m));
                    if (c0767v2J.M0()) {
                        WH whR = abstractC2168hcA.g().r();
                        if (whR.f()) {
                            bVar.c.a(i, c0702m.b().a());
                        } else if (whR.e()) {
                            bVar.b.a(i, c1207NfA.a(WH.b()));
                        } else if (!d) {
                            throw new AssertionError();
                        }
                        z = false;
                    } else if (c0767v2J.O0()) {
                        AbstractC1154Lf abstractC1154LfQ = abstractC2168hcA.j().q();
                        if (!abstractC1154LfQ.m()) {
                            bVar.b.a(i, abstractC1154LfQ);
                            z = false;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return z ? s.d() : bVar;
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1969f3 extends BS {
    static final /* synthetic */ boolean h = true;
    private final AbstractC3472z00 f;
    private final XH g;

    private C1969f3(AbstractC3472z00 abstractC3472z00, WH wh, XH xh) {
        super(wh);
        if (!h && !abstractC3472z00.H() && !abstractC3472z00.N().g()) {
            throw new AssertionError();
        }
        this.f = abstractC3472z00;
        this.g = xh;
    }

    public final AbstractC3472z00 R() {
        AbstractC3472z00 abstractC3472z00S = S();
        while (abstractC3472z00S.r()) {
            abstractC3472z00S = abstractC3472z00S.a().S();
        }
        return abstractC3472z00S;
    }

    public AbstractC3472z00 S() {
        return this.f;
    }

    public final AbstractC3472z00 T() {
        return this.f.A() ? AbstractC3472z00.k() : this.f;
    }

    public int U() {
        int i = 1;
        for (AbstractC3472z00 abstractC3472z00S = S(); abstractC3472z00S.r(); abstractC3472z00S = abstractC3472z00S.a().S()) {
            i++;
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final C1969f3 a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.BS
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C1969f3 a(WH wh) {
        return wh.equals(N()) ? this : (C1969f3) this.g.a(wh, new BiFunction() { // from class: com.android.tools.r8.internal.f3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((WH) obj, (XH) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1969f3)) {
            return false;
        }
        C1969f3 c1969f3 = (C1969f3) obj;
        if (N() != c1969f3.N()) {
            return false;
        }
        return this.f.equals(c1969f3.f);
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return Objects.hash(this.c, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean r() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        return this.c.toString() + " (" + this.f.toString() + "[])";
    }

    public static C1969f3 a(final AbstractC3472z00 abstractC3472z00, final WH wh) {
        return (C1969f3) XH.a(wh, new Function() { // from class: com.android.tools.r8.internal.f3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1969f3.a(abstractC3472z00, wh, (XH) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1969f3 a(AbstractC3472z00 abstractC3472z00, WH wh, XH xh) {
        return new C1969f3(abstractC3472z00, wh, xh);
    }

    @Override // com.android.tools.r8.internal.BS
    public final C0767v2 a(C0722p1 c0722p1) {
        C0767v2 c0767v2R;
        AbstractC3472z00 abstractC3472z00R = R();
        if (abstractC3472z00R.H()) {
            c0767v2R = abstractC3472z00R.c().a(c0722p1);
        } else {
            if (!h && !abstractC3472z00R.w()) {
                throw new AssertionError();
            }
            c0767v2R = abstractC3472z00R.b().R();
        }
        return c0722p1.a(U(), c0767v2R);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C1969f3 a(WH wh, XH xh) {
        if (h || this.c != wh) {
            return new C1969f3(this.f, wh, xh);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean a(C0702m c0702m) {
        return this.f.a(c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final AbstractC3472z00 a(C0702m c0702m, Function function, Set set) {
        AbstractC3472z00 abstractC3472z00A;
        return (!this.f.I() || (abstractC3472z00A = this.f.a(c0702m, function, set)) == this.f) ? this : a(abstractC3472z00A, this.c);
    }

    @Override // com.android.tools.r8.internal.BS
    public final BS a(BS bs, C0702m c0702m) {
        if (bs.r()) {
            C1969f3 c1969f3A = bs.a();
            WH whA = N().a(c1969f3A.N());
            BS bsA = a(S(), c1969f3A.S(), c0702m, whA);
            if (bsA == null) {
                return c1969f3A.N() == whA ? c1969f3A : a(whA);
            }
            if (h || bsA.N() == whA) {
                return bsA;
            }
            throw new AssertionError();
        }
        if (bs.w()) {
            return bs.b().a(this, c0702m);
        }
        if (!h && !(bs instanceof AS)) {
            throw new AssertionError();
        }
        return b(bs.N());
    }

    private static BS a(AbstractC3472z00 abstractC3472z00, AbstractC3472z00 abstractC3472z01, C0702m c0702m, WH wh) {
        if (abstractC3472z00.equals(abstractC3472z01)) {
            return null;
        }
        if (abstractC3472z00.I() && abstractC3472z01.I()) {
            if (abstractC3472z00.r() && abstractC3472z01.r()) {
                BS bsA = a(abstractC3472z00.a().S(), abstractC3472z01.a().S(), c0702m, abstractC3472z00.N().a(abstractC3472z01.N()));
                if (bsA == null) {
                    return null;
                }
                return a(bsA, wh);
            }
            return a(abstractC3472z00.d().a(abstractC3472z01.d(), c0702m), wh);
        }
        boolean z = h;
        if (!z && !abstractC3472z00.H() && !abstractC3472z01.H()) {
            throw new AssertionError();
        }
        if (c0702m.q()) {
            if (!z && !c0702m.f().h()) {
                throw new AssertionError();
            }
            C0722p1 c0722p1A = c0702m.a();
            return C1229Oa.a(c0722p1A.Z1, wh, (C0702m<? extends C0680j>) c0702m.h0(), C1513Xr.a().a(c0722p1A.w5).a(c0722p1A.u5).a());
        }
        return AbstractC3472z00.a(c0702m.a().Z1, wh, (C0702m<?>) c0702m).b();
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3472z00 {
    static final /* synthetic */ boolean a = true;
    public static final /* synthetic */ int b = 0;

    public static C3037t5 e() {
        return C3037t5.S();
    }

    public static G5 f() {
        return G5.P();
    }

    public static Z5 g() {
        return Z5.S();
    }

    public static C2361k9 h() {
        return C2361k9.S();
    }

    public static C0981Ff i() {
        return C0981Ff.S();
    }

    public static C0858Bi j() {
        return C0858Bi.S();
    }

    public static C1484Wr k() {
        return C1484Wr.S();
    }

    public static C1984fD l() {
        return C1984fD.S();
    }

    public static BS m() {
        return BS.Q();
    }

    public static VU n() {
        return VU.S();
    }

    public static C2994sV o() {
        return C2994sV.R();
    }

    public static C2163hZ p() {
        return C2163hZ.P();
    }

    public static E20 q() {
        return E20.R();
    }

    public final boolean A() {
        return (this instanceof C3037t5) || (this instanceof Z5) || (this instanceof VU) || (this instanceof C2361k9);
    }

    public boolean B() {
        return this instanceof C0858Bi;
    }

    public boolean C() {
        return this instanceof C1484Wr;
    }

    public boolean D() {
        return this instanceof C1984fD;
    }

    public boolean E() {
        return this instanceof AS;
    }

    public boolean F() {
        return N().h();
    }

    public final boolean G() {
        return r() || w() || (this instanceof AS) || (this instanceof C1484Wr) || (this instanceof C0858Bi) || (this instanceof C1984fD) || (this instanceof C0981Ff) || (this instanceof G5);
    }

    public boolean H() {
        return false;
    }

    public boolean I() {
        return false;
    }

    boolean J() {
        return this instanceof VU;
    }

    public boolean K() {
        return false;
    }

    public boolean L() {
        return this instanceof C2163hZ;
    }

    public boolean M() {
        return false;
    }

    public abstract WH N();

    public int O() {
        if (a) {
            return 1;
        }
        if ((this instanceof G5) || (this instanceof C2163hZ)) {
            throw new AssertionError();
        }
        return 1;
    }

    public C1969f3 a() {
        return null;
    }

    public AbstractC3472z00 a(C0702m c0702m, Function function, Set set) {
        return this;
    }

    public boolean a(C0702m c0702m) {
        return false;
    }

    public C1229Oa b() {
        return null;
    }

    public XM c() {
        return null;
    }

    public BS d() {
        return null;
    }

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public boolean r() {
        return false;
    }

    boolean s() {
        return this instanceof C3037t5;
    }

    public boolean t() {
        return this instanceof G5;
    }

    public abstract String toString();

    boolean u() {
        return this instanceof Z5;
    }

    boolean v() {
        return this instanceof C2361k9;
    }

    public boolean w() {
        return false;
    }

    public boolean x() {
        return N().e();
    }

    public final boolean y() {
        return N().f();
    }

    public boolean z() {
        return this instanceof C0981Ff;
    }

    public final boolean a(AbstractC3472z00 abstractC3472z00) {
        if (this == abstractC3472z00) {
            return true;
        }
        boolean z = this instanceof G5;
        abstractC3472z00.getClass();
        if (z != (abstractC3472z00 instanceof G5) || H() || abstractC3472z00.H()) {
            return false;
        }
        if (a || (I() && abstractC3472z00.I())) {
            return d().a(WH.i()).equals(abstractC3472z00.d().a(WH.i()));
        }
        throw new AssertionError();
    }

    public boolean b(AbstractC3472z00 abstractC3472z00, C0702m<?> c0702m) {
        if (this == abstractC3472z00) {
            return true;
        }
        if (this instanceof C2163hZ) {
            abstractC3472z00.getClass();
            return abstractC3472z00 instanceof C2163hZ;
        }
        abstractC3472z00.getClass();
        if ((abstractC3472z00 instanceof C2163hZ) || (this instanceof G5)) {
            return true;
        }
        if (abstractC3472z00 instanceof G5) {
            return false;
        }
        if (H()) {
            return a(abstractC3472z00, c0702m);
        }
        if (a || (I() && abstractC3472z00.I())) {
            return a(abstractC3472z00.F() ? abstractC3472z00.d() : abstractC3472z00.d().a(WH.i()), c0702m);
        }
        throw new AssertionError();
    }

    public boolean c(AbstractC3472z00 abstractC3472z00, C0702m<?> c0702m) {
        if (!equals(abstractC3472z00)) {
            AbstractC3472z00 abstractC3472z00A = a(c0702m, abstractC3472z00);
            if (!equals(abstractC3472z00A) && abstractC3472z00.equals(abstractC3472z00A)) {
                return true;
            }
        }
        return false;
    }

    public final AbstractC3472z00 a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return a(c0702m, abstractC1476Wj, abstractC1476Wj2, Collections.emptySet());
    }

    public final AbstractC3472z00 a(C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final AbstractC1476Wj abstractC1476Wj2, Set set) {
        return a(c0702m, new Function() { // from class: com.android.tools.r8.internal.z00$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC1476Wj.a(abstractC1476Wj2, (C0767v2) obj);
            }
        }, set);
    }

    public static C1229Oa b(C0702m<?> c0702m, WH wh) {
        return a(c0702m.a().X1, wh, c0702m).b();
    }

    public final AbstractC3472z00 a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        if (this != abstractC3472z00) {
            abstractC3472z00.getClass();
            if (!(abstractC3472z00 instanceof G5)) {
                if (this instanceof G5) {
                    return abstractC3472z00;
                }
                if (!(this instanceof C2163hZ) && !(abstractC3472z00 instanceof C2163hZ) && H() == abstractC3472z00.H()) {
                    if (H()) {
                        XM xmC = c();
                        XM xmC2 = abstractC3472z00.c();
                        if (xmC == xmC2) {
                            xmC.getClass();
                            return xmC;
                        }
                        if (xmC.K()) {
                            if (xmC2.K()) {
                                return o();
                            }
                            if (XM.c || xmC2.M()) {
                                return p();
                            }
                            throw new AssertionError();
                        }
                        boolean z = XM.c;
                        if (!z && !xmC.M()) {
                            throw new AssertionError();
                        }
                        if (xmC2.M()) {
                            return q();
                        }
                        if (z || xmC2.K()) {
                            return p();
                        }
                        throw new AssertionError();
                    }
                    boolean z2 = a;
                    if (!z2 && !I()) {
                        throw new AssertionError();
                    }
                    if (!z2 && !G()) {
                        throw new AssertionError();
                    }
                    if (!z2 && !abstractC3472z00.I()) {
                        throw new AssertionError();
                    }
                    if (z2 || abstractC3472z00.G()) {
                        return d().a(abstractC3472z00.d(), c0702m);
                    }
                    throw new AssertionError();
                }
                return p();
            }
        }
        return this;
    }

    public static AbstractC3472z00 a(Iterable<AbstractC3472z00> iterable, C0702m<?> c0702m) {
        AbstractC3472z00 abstractC3472z00F = f();
        Iterator<AbstractC3472z00> it = iterable.iterator();
        while (it.hasNext()) {
            abstractC3472z00F = abstractC3472z00F.a(c0702m, it.next());
        }
        return abstractC3472z00F;
    }

    public boolean a(AbstractC3472z00 abstractC3472z00, C0702m<?> c0702m) {
        if (!equals(abstractC3472z00)) {
            AbstractC3472z00 abstractC3472z00A = a(c0702m, abstractC3472z00);
            if (equals(abstractC3472z00A) || !abstractC3472z00.equals(abstractC3472z00A)) {
                return false;
            }
        }
        return true;
    }

    public final boolean a(C0767v2 c0767v2) {
        if (a || c0767v2.O0()) {
            return w() && b().R() == c0767v2;
        }
        throw new AssertionError();
    }

    public static C1229Oa a(C0702m c0702m, WH wh) {
        return a(c0702m.a().Z1, wh, (C0702m<?>) c0702m).b();
    }

    public static AbstractC3472z00 a(C0767v2 c0767v2, WH wh, C0702m<?> c0702m) {
        return a(c0767v2, wh, (C0702m) c0702m, false);
    }

    public static AbstractC3472z00 a(C0767v2 c0767v2, WH wh, C0702m c0702m, boolean z) {
        if (c0767v2 == C0722p1.S5) {
            if (a || !wh.e()) {
                return m();
            }
            throw new AssertionError();
        }
        if (c0767v2.W0()) {
            return XM.a(c0767v2, z);
        }
        return c0702m.a().a(c0767v2, wh, c0702m);
    }
}

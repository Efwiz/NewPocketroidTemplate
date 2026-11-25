package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B1 {
    static final /* synthetic */ boolean a = true;
    public static final /* synthetic */ int b = 0;

    public static C2892r10 E() {
        return C2892r10.F();
    }

    public boolean A() {
        return false;
    }

    public boolean B() {
        return false;
    }

    public boolean C() {
        return false;
    }

    public boolean D() {
        return false;
    }

    public final B1 a(B1 b1, C1 c1, boolean z, boolean z2) {
        C1991fI c1991fI;
        if ((this instanceof H5) || b1.isUnknown()) {
            return b1;
        }
        if (isUnknown() || (b1 instanceof H5) || equals(b1)) {
            return this;
        }
        if (z) {
            if (x()) {
                return UH.a(b1);
            }
            if (b1.x()) {
                return UH.a(this);
            }
            if ((this instanceof UH) && l().F().equals(b1)) {
                return this;
            }
            return ((b1 instanceof UH) && b1.l().F().equals(this)) ? b1 : C2892r10.F();
        }
        boolean z3 = a;
        if (!z3 && (this instanceof UH)) {
            throw new AssertionError();
        }
        if (!z3 && (b1 instanceof UH)) {
            throw new AssertionError();
        }
        if (!z2 || !w() || !b1.w()) {
            return C2892r10.F();
        }
        if (this instanceof C2853qV) {
            c1991fI = new C1991fI().a(e().G());
        } else {
            if (!z3 && !(this instanceof C2063gI)) {
                throw new AssertionError();
            }
            C2063gI c2063gII = i();
            c2063gII.getClass();
            c1991fI = new C1991fI(c2063gII);
        }
        if (b1 instanceof C2853qV) {
            c1991fI.a(b1.e().G());
        } else {
            if (!z3 && !(b1 instanceof C2063gI)) {
                throw new AssertionError();
            }
            c1991fI.a(b1.i());
        }
        return c1991fI.a(c1);
    }

    public abstract B1 b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2);

    public AbstractC2980sH b() {
        return null;
    }

    public boolean c() {
        return false;
    }

    public C1910eI d() {
        return null;
    }

    public C2853qV e() {
        return null;
    }

    public abstract boolean equals(Object obj);

    public boolean g() {
        return false;
    }

    public abstract int hashCode();

    public C2063gI i() {
        return null;
    }

    public boolean isUnknown() {
        return false;
    }

    public AbstractC3292wV j() {
        return null;
    }

    public InterfaceC1204Nc k() {
        return null;
    }

    public UH l() {
        return null;
    }

    public C2388kV m() {
        return null;
    }

    public AbstractC2467lV n() {
        return null;
    }

    public C2616nV o() {
        return null;
    }

    public AbstractC2706oV p() {
        return null;
    }

    public C3214vV q() {
        return null;
    }

    public GW r() {
        return null;
    }

    public int s() {
        throw new UnsupportedOperationException("Abstract value " + this + " does not have a known array length.");
    }

    public AbstractC3121uK t() {
        throw new UnsupportedOperationException("Abstract value " + this + " does not have any object state.");
    }

    public abstract String toString();

    public boolean u() {
        return false;
    }

    public boolean v() {
        return this instanceof C3064tV;
    }

    public boolean w() {
        return this instanceof C2853qV;
    }

    public boolean x() {
        return false;
    }

    public abstract boolean y();

    public boolean z() {
        return false;
    }
}

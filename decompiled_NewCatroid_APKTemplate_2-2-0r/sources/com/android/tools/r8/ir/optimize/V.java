package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.X4;
import com.android.tools.r8.graph.o5;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class V {
    public final U a;
    final C0767v2 b;
    static final /* synthetic */ boolean e = true;
    public static final V c = new V(U.NEVER);
    public static final V d = new V(U.ALWAYS);

    private V(U u) {
        if (!e && u != U.NEVER && u != U.ALWAYS) {
            throw new AssertionError();
        }
        this.a = u;
        this.b = null;
    }

    public static V a(o5 o5Var, C0767v2 c0767v2, AbstractC0657g abstractC0657g, C0702m c0702m) {
        if (abstractC0657g.m()) {
            return d;
        }
        if (abstractC0657g.j()) {
            if (o5Var.a().D1()) {
                return AbstractC3578p0.a(o5Var.t(), c0767v2, c0702m) ? new V(U.SAMENEST, c0767v2) : c;
            }
            return c0767v2 == o5Var.t() ? new V(U.SAMECLASS, c0767v2) : c;
        }
        if (!abstractC0657g.l()) {
            return c0767v2.a(o5Var.t()) ? new V(U.PACKAGE, c0767v2) : c;
        }
        if (c0767v2.a(o5Var.t())) {
            return new V(U.PACKAGE, c0767v2);
        }
        return c0702m.a(o5Var.t(), c0767v2).d() ? new V(U.SUBCLASS, c0767v2) : c;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof V)) {
            return false;
        }
        V v = (V) obj;
        return this.a.ordinal() == v.a.ordinal() && this.b == v.b;
    }

    public final int hashCode() {
        if (this.b == null) {
            return this.a.ordinal();
        }
        return this.b.e.hashCode() * this.a.ordinal();
    }

    V(U u, C0767v2 c0767v2) {
        boolean z = e;
        if (!z && (u == U.NEVER || u == U.ALWAYS)) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        this.a = u;
        this.b = c0767v2;
    }

    public static V a(C0702m c0702m, C0767v2 c0767v2, o5 o5Var) {
        if (c0767v2.M0()) {
            return a(c0702m, c0767v2.a(c0702m.a()), o5Var);
        }
        if (c0767v2.W0()) {
            return d;
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (abstractC0727q0C == null) {
            return c;
        }
        return a(o5Var, c0767v2, abstractC0727q0C.e, c0702m);
    }

    /* JADX WARN: Code duplicated, block: B:150:0x01c9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:151:0x01ca  */
    public static V a(V v, V v2, C0702m<?> c0702m) {
        boolean zD;
        if (v.equals(v2)) {
            return v;
        }
        if (v2.a.ordinal() < v.a.ordinal()) {
            return a(v2, v, c0702m);
        }
        V v3 = c;
        if (v == v3) {
            return v3;
        }
        if (v2 == d) {
            return v;
        }
        int i = v.a.a | v2.a.a;
        boolean z = e;
        if (!z && U.NEVER.c(i)) {
            throw new AssertionError();
        }
        if (!z && U.ALWAYS.c(i)) {
            throw new AssertionError();
        }
        U u = U.SAMECLASS;
        if (u.c(i)) {
            if (!z && v.a != u) {
                throw new AssertionError();
            }
            U u2 = v2.a;
            if (u2 == u) {
                if (z || v.b != v2.b) {
                    return v3;
                }
                throw new AssertionError();
            }
            if (u2 == U.SAMENEST) {
                return AbstractC3578p0.a(v.b, v2.b, c0702m) ? v : v3;
            }
            if (u2 == U.PACKAGE) {
                return v.b.a(v2.b) ? v : v3;
            }
            if (z || u2 == U.SUBCLASS) {
                return c0702m.a(v.b, v2.b).d() ? v : v3;
            }
            throw new AssertionError();
        }
        U u3 = U.SAMENEST;
        if (u3.c(i)) {
            if (!z && v.a != u3) {
                throw new AssertionError();
            }
            if (v2.a == u3) {
                return AbstractC3578p0.a(v.b, v2.b, c0702m) ? v : v3;
            }
            if (!z) {
                C0767v2 c0767v2 = v.b;
                String strH0 = c0767v2.H0();
                AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
                if (!z && abstractC0727q0C == null) {
                    throw new AssertionError();
                }
                if (abstractC0727q0C.D1()) {
                    if (!abstractC0727q0C.G1()) {
                        abstractC0727q0C = c0702m.c(abstractC0727q0C.g1());
                    }
                    if (!z && abstractC0727q0C == null) {
                        throw new AssertionError();
                    }
                    for (X4 x4 : abstractC0727q0C.i1()) {
                        if (!e && !x4.a().H0().equals(strH0)) {
                            throw new AssertionError();
                        }
                    }
                }
            }
            U u4 = v2.a;
            if (u4 == U.PACKAGE) {
                return v.b.a(v2.b) ? v : c;
            }
            boolean z2 = e;
            if (!z2 && u4 != U.SUBCLASS) {
                throw new AssertionError();
            }
            C0767v2 c0767v3 = v.b;
            C0767v2 c0767v4 = v2.b;
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v3);
            if (abstractC0727q0C2 != null) {
                if (!abstractC0727q0C2.D1()) {
                    zD = c0702m.a(abstractC0727q0C2.d, c0767v4).d();
                } else {
                    if (!abstractC0727q0C2.G1()) {
                        abstractC0727q0C2 = c0702m.c(abstractC0727q0C2.g1());
                    }
                    if (abstractC0727q0C2 != null) {
                        Iterator<X4> it = abstractC0727q0C2.i1().iterator();
                        do {
                            if (!it.hasNext()) {
                                zD = true;
                            }
                        } while (c0702m.a(it.next().a(), c0767v4).d());
                    } else if (!z2) {
                        throw new AssertionError();
                    }
                }
                if (zD) {
                    return v;
                }
                return c;
            }
            if (!z2) {
                throw new AssertionError();
            }
            zD = false;
            if (zD) {
                return v;
            }
            return c;
        }
        U u5 = U.PACKAGE;
        if (u5.c(i)) {
            if (!z && v.a != u5) {
                throw new AssertionError();
            }
            U u6 = v2.a;
            if (u6 == u5) {
                if (z || v.b != v2.b) {
                    return v.b.a(v2.b) ? v : v3;
                }
                throw new AssertionError();
            }
            if (!z && u6 != U.SUBCLASS) {
                throw new AssertionError();
            }
            if (v2.b.a(v.b)) {
                return v;
            }
            return c0702m.a(v.b, v2.b).d() ? new V(u, v.b) : v3;
        }
        if (!z && !U.SUBCLASS.c(i)) {
            throw new AssertionError();
        }
        if (!z && v.a != v2.a) {
            throw new AssertionError();
        }
        if (!z && v.b == v2.b) {
            throw new AssertionError();
        }
        if (c0702m.a(v.b, v2.b).d()) {
            return v;
        }
        return c0702m.a(v2.b, v.b).d() ? v2 : v3;
    }
}

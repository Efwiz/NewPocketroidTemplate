package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JA {
    static final JA l = new JA();
    static final /* synthetic */ boolean m = true;
    public static final /* synthetic */ int n = 0;
    final String a;
    final C0684j2 b;
    public final C0759u2 c;
    final C0737r2 d;
    final C0737r2 e;
    public final C0699l2 f;
    public final ArrayList g;
    public final Set h;
    public final C0800x2 i;
    private final C0725p4 j;
    private final C0767v2 k;

    private JA() {
        this.g = new ArrayList();
        this.h = OU.c();
        this.a = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.b = null;
    }

    final C0767v2 a() {
        C0767v2[] c0767v2Arr = this.e.f.a;
        C0767v2[] c0767v2Arr2 = this.i.a;
        if (m || c0767v2Arr2.length > 0 || c0767v2Arr.length > 0) {
            return c0767v2Arr2.length > 0 ? c0767v2Arr2[0] : c0767v2Arr[0];
        }
        throw new AssertionError();
    }

    public final C0684j2 b() {
        return this.b;
    }

    public static JA b(C0721p0 c0721p0, C0702m c0702m, C0680j c0680j, com.android.tools.r8.graph.o5 o5Var) {
        JA jaA = a(c0721p0, c0702m, c0680j, o5Var);
        if (jaA == l) {
            return null;
        }
        return jaA;
    }

    private com.android.tools.r8.graph.W0 a(C0702m c0702m, C0680j c0680j, com.android.tools.r8.graph.o5 o5Var) {
        boolean z = m;
        if (!z && o5Var == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2U0 = this.f.u0();
        switch (this.f.d.ordinal()) {
            case 4:
                com.android.tools.r8.graph.W0 w0B = c0680j.b(c0684j2U0, o5Var, c0702m, c0680j);
                if (z || w0B == null || w0B.f.o()) {
                    return w0B;
                }
                throw new AssertionError();
            case 5:
            case 7:
                com.android.tools.r8.graph.W0 w0T = c0680j.b(a(), c0684j2U0, this.f.f).t();
                if (w0T == null) {
                    w0T = c0680j.a(c0684j2U0, o5Var, c0702m, c0680j);
                }
                if (z || w0T == null || ((this.f.d.e() && a(w0T)) || ((this.f.d.d() && w0T.L1() && a(w0T)) || (this.f.d.d() && w0T.P1() && a(w0T))))) {
                    return w0T;
                }
                throw new AssertionError();
            case 6:
                com.android.tools.r8.graph.W0 w0A = c0680j.a(c0684j2U0, o5Var, c0702m, c0680j);
                if (z || w0A == null || w0A.f.Y()) {
                    return w0A;
                }
                throw new AssertionError();
            case 8:
                com.android.tools.r8.graph.W0 w0T2 = c0680j.b(a(), c0684j2U0).t();
                if (z || w0T2 == null || a(w0T2)) {
                    return w0T2;
                }
                throw new AssertionError();
            default:
                throw new C3405y10("Unexpected method handle kind in " + this.f);
        }
    }

    private JA(C0702m c0702m, C0680j c0680j, com.android.tools.r8.graph.o5 o5Var, C0721p0 c0721p0, C0759u2 c0759u2, C0737r2 c0737r2, C0737r2 c0737r3, C0699l2 c0699l2, C0767v2 c0767v2, C0800x2 c0800x2) {
        ArrayList arrayList = new ArrayList();
        this.g = arrayList;
        this.h = OU.c();
        boolean z = m;
        if (!z && c0680j == null) {
            throw new AssertionError();
        }
        if (!z && c0721p0 == null) {
            throw new AssertionError();
        }
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        if (!z && c0737r2 == null) {
            throw new AssertionError();
        }
        if (!z && c0737r3 == null) {
            throw new AssertionError();
        }
        if (!z && c0699l2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && c0800x2 == null) {
            throw new AssertionError();
        }
        this.b = c0680j.a().a(c0767v2, c0737r2, c0759u2);
        this.a = c0721p0.u0();
        this.c = c0759u2;
        this.d = c0737r2;
        this.e = c0737r3;
        this.f = c0699l2;
        this.i = c0800x2;
        arrayList.add(c0767v2);
        com.android.tools.r8.graph.W0 w0A = o5Var == null ? null : a(c0702m, c0680j, o5Var);
        if (w0A != null) {
            this.j = w0A.f.P();
            this.k = w0A.J0();
        } else {
            this.j = null;
            this.k = null;
        }
    }

    private static boolean a(com.android.tools.r8.graph.W0 w0) {
        return (w0.f.Y() || w0.E0()) ? false : true;
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.k == c0767v2;
    }

    public final void a(BiConsumer biConsumer) {
        biConsumer.accept(this.d.e, this.e.e);
        for (int i = 0; i < this.e.t0(); i++) {
            biConsumer.accept(this.d.k(i), this.e.k(i));
        }
    }

    final boolean a(com.android.tools.r8.graph.o5 o5Var) {
        C0725p4 c0725p4;
        if (this.f.d.f() && ((c0725p4 = this.j) == null || c0725p4.m())) {
            return false;
        }
        boolean zG = this.f.d.g();
        boolean z = this.f.d.e() || this.f.d.d();
        boolean zC = this.f.d.c();
        boolean z2 = m;
        if (!z2 && !z && !zG && !zC && !this.f.d.f()) {
            throw new AssertionError();
        }
        if (!z2 && this.f.d.d() && (!this.j.j() || this.j.Y() || this.j.o())) {
            throw new AssertionError();
        }
        C0725p4 c0725p5 = this.j;
        if (c0725p5 == null) {
            if (zG || zC) {
                return !o5Var.t().H0().equals(this.f.u0().e.H0());
            }
            return true;
        }
        if (c0725p5.j()) {
            return true;
        }
        if (c0725p5.m()) {
            return false;
        }
        boolean zEquals = o5Var.t().H0().equals(this.k.H0());
        if (z2 || c0725p5.l() || zEquals) {
            return c0725p5.l() && !zEquals;
        }
        throw new AssertionError();
    }

    static JA a(C0721p0 c0721p0, C0702m c0702m, C0680j c0680j, com.android.tools.r8.graph.o5 o5Var) {
        int i;
        if (c0721p0.f.d.g()) {
            C0722p1 c0722p1A = c0680j.a();
            C0684j2 c0684j2U0 = c0721p0.f.u0();
            if (c0684j2U0 == c0722p1A.I5 || c0684j2U0 == c0722p1A.J5) {
                C0722p1 c0722p1A2 = c0680j.a();
                C0684j2 c0684j2U1 = c0721p0.f.u0();
                C0759u2 c0759u2 = c0721p0.d;
                int i2 = 0;
                com.android.tools.r8.graph.H2 h2 = (com.android.tools.r8.graph.H2) a(c0721p0.g, 0, com.android.tools.r8.graph.H2.class);
                C0699l2 c0699l2 = (C0699l2) ((com.android.tools.r8.graph.G2) a(c0721p0.g, 1, com.android.tools.r8.graph.G2.class)).b;
                com.android.tools.r8.graph.H2 h3 = (com.android.tools.r8.graph.H2) a(c0721p0.g, 2, com.android.tools.r8.graph.H2.class);
                C0737r2 c0737r2 = (C0737r2) h3.b;
                C0737r2 c0737r3 = (C0737r2) h2.b;
                if (a(c0722p1A2, c0737r2.e, c0737r3.e)) {
                    C0767v2[] c0767v2Arr = c0737r2.f.a;
                    C0767v2[] c0767v2Arr2 = c0737r3.f.a;
                    int length = c0767v2Arr.length;
                    if (length == c0767v2Arr2.length) {
                        for (int i3 = 0; i3 < length; i3++) {
                            if (a(c0722p1A2, c0767v2Arr[i3], c0767v2Arr2[i3])) {
                            }
                        }
                        C0737r2 c0737r4 = c0721p0.e;
                        final JA ja = new JA(c0702m, c0680j, o5Var, c0721p0, c0759u2, (C0737r2) h2.b, (C0737r2) h3.b, c0699l2, c0737r4.e, c0737r4.f);
                        if (c0684j2U1 == c0722p1A2.I5) {
                            if (c0721p0.g.size() != 3) {
                                throw new C3405y10("Unexpected number of metafactory method arguments in " + c0721p0.toString());
                            }
                        } else {
                            List list = c0721p0.g;
                            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.JA$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    JA.a(this.f$0, (C0767v2) obj);
                                }
                            };
                            final Set set = ja.h;
                            Objects.requireNonNull(set);
                            Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.internal.JA$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    set.add((C0737r2) obj);
                                }
                            };
                            int i4 = ((com.android.tools.r8.graph.B2.g) a(list, 3, com.android.tools.r8.graph.B2.g.class)).b;
                            if (!m && (i4 & (-8)) != 0) {
                                throw new AssertionError();
                            }
                            if ((i4 & 2) != 0) {
                                int i5 = ((com.android.tools.r8.graph.B2.g) a(list, 4, com.android.tools.r8.graph.B2.g.class)).b;
                                i = 5;
                                int i6 = 0;
                                while (i6 < i5) {
                                    consumer.accept((C0767v2) ((com.android.tools.r8.graph.B2.k) a(list, i, com.android.tools.r8.graph.B2.k.class)).b);
                                    i6++;
                                    i++;
                                }
                            } else {
                                i = 4;
                            }
                            if ((i4 & 1) != 0) {
                                consumer.accept(c0722p1A2.u5);
                            }
                            if ((i4 & 4) != 0) {
                                int i7 = i + 1;
                                int i8 = ((com.android.tools.r8.graph.B2.g) a(list, i, com.android.tools.r8.graph.B2.g.class)).b;
                                while (true) {
                                    i = i7;
                                    if (i2 >= i8) {
                                        break;
                                    }
                                    i7 = i + 1;
                                    consumer2.accept((C0737r2) ((com.android.tools.r8.graph.H2) a(list, i, com.android.tools.r8.graph.H2.class)).b);
                                    i2++;
                                }
                            }
                            if (list.size() != i) {
                                throw new C3405y10("Unexpected number of metafactory method arguments in DexCallSite");
                            }
                        }
                        return ja;
                    }
                }
                throw new C3405y10("Enforced and erased signatures are inconsistent in " + c0721p0.toString());
            }
        }
        return l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(JA ja, C0767v2 c0767v2) {
        if (ja.g.contains(c0767v2)) {
            return;
        }
        ja.g.add(c0767v2);
    }

    public static ArrayList a(C0721p0 c0721p0, C0702m c0702m) {
        JA jaA = a(c0721p0, c0702m, (C0680j) c0702m.f(), null);
        if (jaA == l) {
            return null;
        }
        return jaA.g;
    }

    private static com.android.tools.r8.graph.B2 a(List list, int i, Class cls) {
        if (list.size() >= i) {
            com.android.tools.r8.graph.B2 b2 = (com.android.tools.r8.graph.B2) list.get(i);
            if (cls.isAssignableFrom(b2.getClass())) {
                return b2;
            }
            throw new C3405y10("Unexpected type of bootstrap arguments #" + i + " in DexCallSite");
        }
        throw new C3405y10("Expected to find at least " + i + " bootstrap arguments in DexCallSite");
    }

    static boolean a(C0722p1 c0722p1, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3 || (c0767v2.O0() && c0767v3.O0())) {
            return true;
        }
        if (!c0767v2.M0()) {
            return false;
        }
        if (c0767v3.M0()) {
            return a(c0722p1, c0767v2.a(c0722p1), c0767v3.a(c0722p1));
        }
        return c0767v3 == c0722p1.Z1;
    }
}

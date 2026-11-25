package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ko, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2412ko implements InterfaceC3174v, InterfaceC2729oo, InterfaceC3176v00 {
    static final /* synthetic */ boolean g = true;
    protected S10 a = null;
    protected final ArrayList b = new ArrayList();
    private C3332x4 c = null;
    private int d = -1;
    private Set e = null;
    private AbstractC1915eM f = null;

    protected AbstractC2412ko(S10 s10) {
        f(s10);
    }

    private boolean R0() {
        boolean z;
        if (d1()) {
            S10 s10B = b();
            if (s10B.a0() <= 0) {
                Iterator<AbstractC2412ko> it = s10B.h0().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                    } else if (it.next().h() != h()) {
                    }
                }
            }
            z = true;
        } else {
            z = false;
        }
        return !z;
    }

    public C3446yc A() {
        return null;
    }

    public US A0() {
        return null;
    }

    public boolean A1() {
        return this instanceof C0918Dc;
    }

    public boolean A2() {
        return false;
    }

    public AbstractC0824Ac B() {
        return null;
    }

    public C1616aU B0() {
        return null;
    }

    public boolean B1() {
        return false;
    }

    public boolean B2() {
        return false;
    }

    public C0889Cc C() {
        return null;
    }

    public C3133uU C0() {
        return null;
    }

    public boolean C1() {
        return this instanceof C1033Hc;
    }

    public boolean C2() {
        return false;
    }

    public C0918Dc D() {
        return null;
    }

    public TU D0() {
        return null;
    }

    public boolean D1() {
        return (this instanceof AG) || o2() || b2() || (this instanceof C1358Ss) || (this instanceof LQ);
    }

    public boolean D2() {
        return this instanceof M10;
    }

    public C0978Fc E() {
        return null;
    }

    public WU E0() {
        return null;
    }

    public final boolean E1() {
        return p2() || D1();
    }

    public abstract int E2();

    public C1033Hc F() {
        return null;
    }

    public QW F0() {
        return null;
    }

    public final boolean F1() {
        return (this instanceof C1343Sd) || (this instanceof C1316Rd) || (this instanceof C1232Od) || (this instanceof C1289Qd) || (this instanceof C1259Pd);
    }

    public abstract int F2();

    public C1289Qd G() {
        return null;
    }

    public RW G0() {
        return null;
    }

    public boolean G1() {
        return this instanceof C1289Qd;
    }

    public abstract int G2();

    public C1316Rd H() {
        return null;
    }

    public SW H0() {
        return null;
    }

    public boolean H1() {
        return this instanceof C1343Sd;
    }

    public final EnumC1812d20 H2() {
        return this.a.d0();
    }

    public C1343Sd I() {
        return null;
    }

    public C1620aY I0() {
        return null;
    }

    public boolean I1() {
        return this instanceof C2477lf;
    }

    public boolean I2() {
        return false;
    }

    public C2477lf J() {
        return null;
    }

    public C3218vY J0() {
        return null;
    }

    public boolean J1() {
        return this instanceof C1189Mn;
    }

    public C3379xf K() {
        return null;
    }

    public IY K0() {
        return null;
    }

    public boolean K1() {
        return false;
    }

    public C1099Jf L() {
        return null;
    }

    public C1621aZ L0() {
        return null;
    }

    public boolean L1() {
        return this instanceof C1793co;
    }

    public InterfaceC1102Jh M() {
        return null;
    }

    public N10 M0() {
        return null;
    }

    public boolean M1() {
        return false;
    }

    public AbstractC1182Mh N() {
        return null;
    }

    public P30 N0() {
        return null;
    }

    public boolean N1() {
        return false;
    }

    public InterfaceC1474Wh O() {
        return null;
    }

    public final void O0() {
        if (!g && this.c == null) {
            throw new AssertionError();
        }
        this.c = null;
    }

    public boolean O1() {
        return this instanceof C2179hn;
    }

    public C1296Qj P() {
        return null;
    }

    public final void P0() {
        Set set = this.e;
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                ((S10) it.next()).d(this);
            }
            this.e.clear();
        }
    }

    public boolean P1() {
        return this instanceof C1189Mn;
    }

    public C1015Gl Q() {
        return null;
    }

    public final void Q0() {
        f(null);
    }

    public boolean Q1() {
        return this instanceof C1189Mn;
    }

    public C2179hn R() {
        return null;
    }

    public boolean R1() {
        return this instanceof C1641ao;
    }

    public InterfaceC1108Jn S() {
        return null;
    }

    public final Set S0() {
        Set set = this.e;
        return set != null ? set : AbstractC1048Hm.k();
    }

    public boolean S1() {
        return false;
    }

    public C1189Mn T() {
        return null;
    }

    public S10 T0() {
        return a(0);
    }

    public boolean T1() {
        return false;
    }

    public C1641ao U() {
        return null;
    }

    public String U0() {
        return getClass().getSimpleName();
    }

    public boolean U1() {
        return false;
    }

    public C1793co V() {
        return null;
    }

    public S10 V0() {
        throw new C3405y10("Should conform to throwsOnNullInput.");
    }

    public boolean V1() {
        return this instanceof C1113Js;
    }

    public C1458Vr W() {
        return null;
    }

    public final int W0() {
        return this.d;
    }

    public boolean W1() {
        return false;
    }

    public AbstractC1084Is X() {
        return null;
    }

    public AbstractC0824Ac X0() {
        return null;
    }

    public boolean X1() {
        return this instanceof C1248Os;
    }

    public C1113Js Y() {
        return null;
    }

    public final AbstractC1915eM Y0() {
        if (g || this.f != null) {
            return this.f;
        }
        throw new AssertionError();
    }

    public boolean Y1() {
        return false;
    }

    public C1166Ls Z() {
        return null;
    }

    public final String Z0() {
        AbstractC1915eM abstractC1915eM = this.f;
        return abstractC1915eM == null ? "???" : abstractC1915eM.toString();
    }

    public boolean Z1() {
        return false;
    }

    public abstract com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var);

    public abstract Object a(AbstractC1855de abstractC1855de);

    public abstract void a(L6 l6);

    public abstract void a(C2322jf c2322jf);

    public abstract void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC);

    void a(C3804u c3804u) {
    }

    public boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return false;
    }

    public boolean a(C0702m c0702m, S10 s10) {
        return false;
    }

    public boolean a(C0722p1 c0722p1) {
        return false;
    }

    public boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return false;
    }

    public boolean a(com.android.tools.r8.ir.regalloc.f fVar) {
        return false;
    }

    public C1248Os a0() {
        return null;
    }

    public final boolean a1() {
        return this.c != null;
    }

    public boolean a2() {
        return this instanceof C1358Ss;
    }

    public void b(AbstractC1915eM abstractC1915eM) {
        if (!g && this.f != null) {
            throw new AssertionError();
        }
        this.f = abstractC1915eM;
    }

    public abstract boolean b(AbstractC2412ko abstractC2412ko);

    public AbstractC1305Qs b0() {
        return null;
    }

    public final boolean b1() {
        return a(new kl$$ExternalSyntheticLambda13());
    }

    public boolean b2() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public AbstractC1332Rs c0() {
        return null;
    }

    public abstract boolean c1();

    public boolean c2() {
        return this instanceof C1433Us;
    }

    public abstract boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var);

    public boolean d(S10 s10) {
        return true;
    }

    public C1358Ss d0() {
        return null;
    }

    public final boolean d1() {
        return this.a != null;
    }

    public boolean d2() {
        return this instanceof C1721bt;
    }

    public final void e(S10 s10) {
        boolean z = g;
        if (!z && !s10.A()) {
            throw new AssertionError();
        }
        Set set = this.e;
        if (set == null) {
            if (!z) {
                throw new AssertionError();
            }
        } else {
            if (!z && !set.contains(s10)) {
                throw new AssertionError();
            }
            if (this.e.remove(s10)) {
                s10.d(this);
            }
        }
    }

    public C1385Ts e0() {
        return null;
    }

    final boolean e1() {
        return this.f != null;
    }

    public boolean e2() {
        return this instanceof C1798ct;
    }

    public S10 f(S10 s10) {
        S10 s10B = b();
        this.a = s10;
        if (s10 != null) {
            s10.b = this;
        }
        return s10B;
    }

    public C1433Us f0() {
        return null;
    }

    public final boolean f1() {
        return !g1();
    }

    public boolean f2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3174v
    public boolean g() {
        return false;
    }

    public C1721bt g0() {
        return null;
    }

    public final boolean g1() {
        return d1() && b().y();
    }

    public boolean g2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2729oo
    public C3332x4 h() {
        if (g || this.c != null) {
            return this.c;
        }
        throw new AssertionError();
    }

    public C1798ct h0() {
        return null;
    }

    public final List h1() {
        return this.b;
    }

    public boolean h2() {
        return this instanceof C3044tC;
    }

    public C2115gt i0() {
        return null;
    }

    public boolean i1() {
        return g();
    }

    public boolean i2() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3176v00
    public final com.android.tools.r8.graph.X j() {
        S10 s10 = this.a;
        if (s10 == null) {
            return null;
        }
        return s10.s();
    }

    public AbstractC1954eu j0() {
        return null;
    }

    public boolean j1() {
        return this instanceof C3446yc;
    }

    public boolean j2() {
        return this instanceof C3344xF;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2729oo
    public final AbstractC2412ko k() {
        return this;
    }

    public C3044tC k0() {
        return null;
    }

    public boolean k1() {
        return false;
    }

    public boolean k2() {
        return false;
    }

    public FC l0() {
        return null;
    }

    public boolean l1() {
        return this instanceof C1232Od;
    }

    public boolean l2() {
        return false;
    }

    public C3344xF m0() {
        return null;
    }

    public boolean m1() {
        return false;
    }

    public boolean m2() {
        return this instanceof AF;
    }

    public H1 n() {
        return null;
    }

    public C3492zF n0() {
        return null;
    }

    public boolean n1() {
        return false;
    }

    public boolean n2() {
        return this instanceof AG;
    }

    public AF o0() {
        return null;
    }

    public boolean o1() {
        return false;
    }

    public boolean o2() {
        return false;
    }

    public X1 p() {
        return null;
    }

    public CF p0() {
        return null;
    }

    public boolean p1() {
        return false;
    }

    public boolean p2() {
        return false;
    }

    public C3406y2 q() {
        return null;
    }

    public C2979sG q0() {
        return null;
    }

    public boolean q1() {
        return this instanceof Z2;
    }

    public boolean q2() {
        return false;
    }

    public U2 r() {
        return null;
    }

    public AG r0() {
        return null;
    }

    public boolean r1() {
        return false;
    }

    public boolean r2() {
        return this instanceof C3446yc;
    }

    public V2 s() {
        return null;
    }

    public BG s0() {
        return null;
    }

    public boolean s1() {
        return this instanceof N3;
    }

    public boolean s2() {
        return this instanceof ZL;
    }

    public Y2 t() {
        return null;
    }

    public DG t0() {
        return null;
    }

    public final boolean t1() {
        return (this instanceof N3) && w().K2();
    }

    public boolean t2() {
        return this instanceof LQ;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(U0());
        for (int length = sb.length(); length < 20; length++) {
            sb.append(" ");
        }
        sb.append(" ");
        S10 s10 = this.a;
        if (s10 != null) {
            sb.append(s10);
            sb.append(" <- ");
        }
        if (!this.b.isEmpty()) {
            com.android.tools.r8.utils.Y2.a(sb, this.b, ", ", com.android.tools.r8.utils.Y2.a.NONE);
        }
        return sb.toString();
    }

    public Z2 u() {
        return null;
    }

    public FG u0() {
        return null;
    }

    public final boolean u1() {
        return (this instanceof N3) && w().L2();
    }

    public boolean u2() {
        return false;
    }

    public C1736c3 v() {
        return null;
    }

    public LH v0() {
        return null;
    }

    public boolean v1() {
        return false;
    }

    public boolean v2() {
        return this instanceof QW;
    }

    public N3 w() {
        return null;
    }

    public C1831dI w0() {
        return null;
    }

    public boolean w1() {
        return false;
    }

    public boolean w2() {
        return this instanceof QW;
    }

    public T4 x() {
        return null;
    }

    public NK x0() {
        return null;
    }

    public boolean x1() {
        return this instanceof C3446yc;
    }

    public boolean x2() {
        return this instanceof SW;
    }

    public C2972s9 y() {
        return null;
    }

    public ZL y0() {
        return null;
    }

    public boolean y1() {
        return false;
    }

    public boolean y2() {
        return false;
    }

    public C2318jb z() {
        return null;
    }

    public LQ z0() {
        return null;
    }

    public boolean z1() {
        return this instanceof C0889Cc;
    }

    public boolean z2() {
        return this instanceof C3218vY;
    }

    protected void c(S10 s10) {
        if (s10 != null) {
            this.b.add(s10);
            if (!g && !s10.F()) {
                throw new AssertionError();
            }
            if (s10.F()) {
                s10.b(this);
            }
        }
    }

    public final S10 g(S10 s10) {
        S10 s11 = this.a;
        this.a = null;
        f(s10);
        if (s11 != null) {
            s11.b = null;
        }
        return s11;
    }

    public S10 b() {
        return this.a;
    }

    public final void b(S10 s10) {
        if (!g && !s10.A()) {
            throw new AssertionError();
        }
        if (this.e == null) {
            this.e = OU.c();
        }
        this.e.add(s10);
    }

    public AbstractC2744p f(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (d(c0702m, o5Var) && c(c0702m, o5Var)) {
            return C2198i10.e();
        }
        return C1501Xf.e();
    }

    public boolean a(Set set) {
        return this instanceof C1641ao;
    }

    public final void c(AbstractC2412ko abstractC2412ko) {
        Set set = this.e;
        if (set == null) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((S10) it.next()).b(this, abstractC2412ko);
        }
        this.e.clear();
    }

    public final void a(AbstractC1915eM abstractC1915eM) {
        boolean z = g;
        if (!z && abstractC1915eM == null) {
            throw new AssertionError();
        }
        if (!z && this.f == null) {
            throw new AssertionError();
        }
        this.f = abstractC1915eM;
    }

    public final void b(S10 s10, S10 s11) {
        for (int i = 0; i < this.b.size(); i++) {
            if (s10 == this.b.get(i)) {
                this.b.set(i, s11);
                s11.b(this);
            }
        }
        s10.e(this);
    }

    public final boolean e(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return R0() && !c(c0702m, o5Var);
    }

    protected AbstractC2412ko(S10 s10, S10 s11) {
        c(s11);
        f(s10);
    }

    public final S10 a(int i) {
        return (S10) this.b.get(i);
    }

    public final boolean a(Predicate predicate) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            if (predicate.test((S10) it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean c(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return a(c0702m, o5Var, C2332jo.a);
    }

    public B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (g || d1()) {
            return C2892r10.F();
        }
        throw new AssertionError();
    }

    public final void b(int i) {
        if (!g && i == -1) {
            throw new AssertionError();
        }
        this.d = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3176v00
    public AbstractC3472z00 a() {
        if (d1()) {
            return b().w();
        }
        return null;
    }

    public boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return i1();
    }

    public static void a(AbstractC2412ko abstractC2412ko) {
        S10 s10 = abstractC2412ko.a;
        if (s10 != null) {
            s10.e();
        }
        abstractC2412ko.b.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ko$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((S10) obj).e();
            }
        });
        Set set = abstractC2412ko.e;
        if (set != null) {
            set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ko$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((S10) obj).e();
                }
            });
            abstractC2412ko.e = null;
        }
    }

    protected AbstractC2412ko(S10 s10, List list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                c((S10) it.next());
            }
        }
        f(s10);
    }

    public void a(int i, S10 s10) {
        S10 s11 = (S10) this.b.get(i);
        this.b.set(i, s10);
        s10.b(this);
        s11.e(this);
    }

    public final void a(S10 s10, S10 s11) {
        boolean z = g;
        if (!z && !s10.A()) {
            throw new AssertionError();
        }
        if (!z && !s11.A()) {
            throw new AssertionError();
        }
        if (!z && s11.s() != s10.s()) {
            throw new AssertionError("Replacing debug values with inconsistent locals " + s10.s() + " and " + s11.s() + ". This is likely a code transformation bug that has not taken local information into account");
        }
        boolean zRemove = this.e.remove(s10);
        if (!z && !zRemove) {
            throw new AssertionError();
        }
        if (zRemove && s11.A()) {
            s11.a(this);
        }
    }

    public final S10 a(com.android.tools.r8.graph.X x) {
        Set set = this.e;
        if (set == null) {
            return null;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            S10 s10 = (S10) it.next();
            if (s10.A() && s10.s() == x) {
                it.remove();
                s10.d(this);
                return s10;
            }
        }
        return null;
    }

    public void a(C3332x4 c3332x4) {
        if (!g && c3332x4 == null) {
            throw new AssertionError();
        }
        this.c = c3332x4;
    }

    /* JADX WARN: Code duplicated, block: B:129:0x020b  */
    /* JADX WARN: Code duplicated, block: B:141:0x0219 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00af, code lost:
    
        if (com.android.tools.r8.internal.EnumC1812d20.a(r0.n) == com.android.tools.r8.internal.EnumC1812d20.a(r1.n)) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0124, code lost:
    
        if (r0.d0() != r1.d0()) goto L75;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean a(com.android.tools.r8.internal.AbstractC2412ko r10, com.android.tools.r8.ir.regalloc.f r11, com.android.tools.r8.internal.CE r12) {
        /*
            Method dump skipped, instruction units count: 557
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.AbstractC2412ko.a(com.android.tools.r8.internal.ko, com.android.tools.r8.ir.regalloc.f, com.android.tools.r8.internal.CE):boolean");
    }

    public boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return i1();
    }

    public com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (c(c0702m, c2409kl.h())) {
            return com.android.tools.r8.ir.optimize.L.e();
        }
        return com.android.tools.r8.ir.optimize.L.a();
    }

    public C0767v2 a(C0702m c0702m, Q00 q00) {
        S10 s10;
        if (g || (s10 = this.a) == null || !s10.w().I()) {
            throw new C3405y10("Instruction without object outValue cannot compute verification type");
        }
        throw new AssertionError();
    }

    public SA a(C2409kl c2409kl, Function function) {
        if (this.a.G()) {
            return new C1004Gc(this.a);
        }
        return C3106u5.c();
    }

    public AbstractC3472z00 a(C0702m c0702m) {
        if (!g && this.a != null) {
            throw new AssertionError();
        }
        throw new Z00("Implement type lattice evaluation for: " + U0());
    }

    public void a(C0702m c0702m, C2433l20 c2433l20) {
        S10 s10 = this.a;
        if (s10 != null) {
            AbstractC3472z00 abstractC3472z00W = s10.w();
            if (abstractC3472z00W.r()) {
                C0767v2 c0767v2B = abstractC3472z00W.a().a(c0702m.a()).b(c0702m.a());
                if (!g && AbstractC2964s2.a(c0702m.E(), c0767v2B) != c0767v2B) {
                    throw new AssertionError();
                }
                return;
            }
            if (abstractC3472z00W.w()) {
                C0767v2 c0767v2R = abstractC3472z00W.b().R();
                if (!g && AbstractC2964s2.a(c0702m.E(), c0767v2R) != c0767v2R) {
                    throw new AssertionError();
                }
            }
        }
    }

    public final void a(boolean z) {
        boolean z2 = g;
        if (!z2 && this.f == null) {
            throw new AssertionError();
        }
        if (!z2 && z && !Y0().s()) {
            throw new AssertionError();
        }
        if (!z2 && g() && !(this instanceof C1033Hc) && !(this instanceof C2477lf) && !Y0().s() && !Y0().t()) {
            throw new AssertionError();
        }
    }

    public void a(NB nb) {
        throw new Z00("Missing impl for " + getClass().getSimpleName());
    }
}

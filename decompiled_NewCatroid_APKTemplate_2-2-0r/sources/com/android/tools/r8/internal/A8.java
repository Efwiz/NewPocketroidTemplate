package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0771w;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A8 implements KV {
    static final /* synthetic */ boolean w = true;
    private C1713bl a;
    private final boolean c;
    private N8 f;
    private final List g;
    private final C0753u h;
    private final com.android.tools.r8.graph.o5 i;
    private final Origin j;
    private final C0702m k;
    private C3483z8 m;
    private C3414y8 n;
    private int o;
    private int p;
    private boolean q;
    private InterfaceC0992Fq r;
    private InterfaceC0992Fq s;
    private final C2828q6 u;
    private final com.android.tools.r8.utils.H1 v;
    private boolean b = false;
    private boolean d = false;
    private C3344xF e = null;
    private final MR l = new MR();
    private C1303Qq t = new C1303Qq();

    public A8(C0753u c0753u, List list, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, Origin origin, C0702m c0702m) {
        boolean z = false;
        this.h = c0753u;
        this.g = list;
        this.i = o5Var;
        this.j = origin;
        this.k = c0702m;
        int i = 0;
        for (int i2 = 0; i2 < c0753u.I0().size(); i2++) {
            B7 b7 = c0753u.I0().get(i2);
            if (b7 instanceof M7) {
                this.l.a(e(i2), (M7) b7);
            }
            if (b7 instanceof C2051g8) {
                i++;
            }
        }
        this.f = new N8();
        this.u = new C2828q6(abstractC1915eM, i, c0684j2, o5Var.e().N0(), c0753u.N0());
        com.android.tools.r8.utils.H1 h1Y0 = c0702m.Y().y0();
        this.v = h1Y0;
        if (!h().M1() && h1Y0.b() && h().Q1()) {
            z = true;
        }
        this.c = z;
    }

    private com.android.tools.r8.graph.W0 h() {
        return this.i.e();
    }

    private int[] i(int i) {
        B7 b7 = this.h.I0().get(i);
        boolean z = w;
        if (!z && !a(b7)) {
            throw new AssertionError();
        }
        M7 m7A = b7.A();
        if (b7.Q() || (b7 instanceof V8)) {
            if (z || m7A == null) {
                return new int[0];
            }
            throw new AssertionError();
        }
        if (!z && !(b7 instanceof U8) && m7A == null) {
            throw new AssertionError("getTargets(): Non-control flow instruction " + b7.getClass());
        }
        if (!(b7 instanceof U8)) {
            int iA = this.l.a(m7A);
            if (b7 instanceof C2899r7) {
                return new int[]{iA};
            }
            if (z || b7.C()) {
                return new int[]{iA, i + 1};
            }
            throw new AssertionError();
        }
        U8 u8 = (U8) b7;
        List listX = u8.X();
        int[] iArr = new int[listX.size() + 1];
        for (int i2 = 0; i2 < listX.size(); i2++) {
            iArr[i2] = this.l.a(listX.get(i2));
        }
        iArr[listX.size()] = this.l.a(u8.U());
        return iArr;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, int i3, C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void b() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void c(C2102gl c2102gl) {
        int i;
        if (!w && this.q) {
            throw new AssertionError();
        }
        this.q = true;
        this.f.a(this.u.a());
        InterfaceC0992Fq interfaceC0992Fq = h(this.o).c;
        this.r = interfaceC0992Fq;
        if (this.q) {
            this.s = interfaceC0992Fq;
        } else {
            this.s = !a(this.h.I0().get(this.o)) ? h(this.o + 1).c : AbstractC2568mr.a;
        }
        com.android.tools.r8.graph.W0 w0H = h();
        final N8 n8 = this.f;
        Objects.requireNonNull(n8);
        c2102gl.a(0, w0H, new BiConsumer() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                n8.a(((Integer) obj).intValue(), (C0767v2) obj2);
            }
        });
        InterfaceC0992Fq interfaceC0992Fq2 = h(0).c;
        if (!interfaceC0992Fq2.isEmpty()) {
            int i2 = !h().E0() ? 1 : 0;
            for (C0767v2 c0767v2 : h().m1().f.a) {
                i2 = (c0767v2.T0() || c0767v2.P0()) ? i2 + 2 : i2 + 1;
            }
            InterfaceC1912eK it = interfaceC0992Fq2.c().iterator();
            while (it.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                if (i2 <= interfaceC0934Dq.a()) {
                    c2102gl.b(interfaceC0934Dq.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue());
                }
            }
        }
        boolean z = this.c;
        if (z) {
            if (!w && !z) {
                throw new AssertionError();
            }
            this.d = true;
            C0767v2 c0767v2T = this.i.t();
            if (h().E0()) {
                i = this.f.a(c0767v2T).a;
                this.f.e();
                c2102gl.b(i, c0767v2T);
            } else {
                i = this.f.a(0).a;
            }
            C3344xF c3344xF = new C3344xF(EnumC3423yF.ENTER, c2102gl.b(i, EnumC1968f20.OBJECT));
            c2102gl.a(c3344xF);
            this.e = c3344xF;
            this.d = false;
        }
        AbstractC1915eM abstractC1915eMB = b(0);
        if (!this.f.a().equals(abstractC1915eMB)) {
            this.f.b(abstractC1915eMB);
            c2102gl.a(this.f.a());
        }
        L8 l8B = this.f.b();
        L8 l8 = (L8) this.t.get(0);
        L8 l8A = N8.a(l8, l8B, this.j);
        if (l8A != l8) {
            this.t.a(0, l8A);
        }
        this.q = false;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void clear() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final int d(int i) {
        return BZip2Constants.BASEBLOCKSIZE;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean d() {
        return false;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e(int i) {
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean f(int i) {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int g(int i) {
        return i;
    }

    private C3414y8 h(int i) {
        C3414y8 c3414y8 = this.n;
        if (c3414y8 == null || c3414y8.a > i || i >= c3414y8.b) {
            List<C0753u.a> list = this.g;
            MR mr = this.l;
            int iMax = Integer.MIN_VALUE;
            int iMin = Integer.MAX_VALUE;
            InterfaceC0992Fq c1303Qq = null;
            for (C0753u.a aVar : list) {
                int iA = mr.a(aVar.d());
                int iA2 = mr.a(aVar.a());
                if (iA > i) {
                    iMin = Math.min(iMin, iA);
                } else if (i >= iA2) {
                    iMax = Math.max(iMax, iA2);
                } else {
                    if (c1303Qq == null) {
                        c1303Qq = new C1303Qq(16);
                    }
                    iMax = Math.max(iMax, iA);
                    iMin = Math.min(iMin, iA2);
                    c1303Qq.a(aVar.b(), aVar.c());
                }
            }
            if (c1303Qq == null) {
                c1303Qq = AbstractC2568mr.a;
            }
            this.n = new C3414y8(iMax, iMin, c1303Qq);
        }
        return this.n;
    }

    @Override // com.android.tools.r8.internal.KV
    public final C3038t6 b(C2102gl c2102gl) {
        if (this.q || this.d) {
            return null;
        }
        C3483z8 c3483z8A = a(this.o, c2102gl.o.a());
        if (!C3483z8.e && c3483z8A.c.isEmpty() != c3483z8A.d.isEmpty()) {
            throw new AssertionError();
        }
        if (c3483z8A.c.isEmpty()) {
            return null;
        }
        return new C3038t6(c3483z8A.c, c3483z8A.d);
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e() {
        return this.h.I0().size();
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM f() {
        return this.f.a();
    }

    public final int g() {
        return this.o;
    }

    private void d(C2102gl c2102gl) {
        String str;
        if (!w && this.r.equals(this.s)) {
            throw new AssertionError();
        }
        InterfaceC1912eK it = this.s.c().iterator();
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            if (!((com.android.tools.r8.graph.X) interfaceC0934Dq.getValue()).equals(this.r.get(interfaceC0934Dq.a()))) {
                H8 h8A = this.f.a(interfaceC0934Dq.a());
                if (h8A != null && h8A.b != EnumC1812d20.a(((com.android.tools.r8.graph.X) interfaceC0934Dq.getValue()).b)) {
                    int i = AbstractC3336x8.a[h8A.b.ordinal()];
                    if (i == 1) {
                        str = "reference";
                    } else if (i == 2) {
                        str = "int";
                    } else if (i == 3) {
                        str = "float";
                    } else if (i == 4) {
                        str = "long";
                    } else {
                        if (i != 5) {
                            throw new C3405y10();
                        }
                        str = "double";
                    }
                    throw new C0994Fs("Attempt to define local of type " + str + " as " + ((com.android.tools.r8.graph.X) interfaceC0934Dq.getValue()).c(3));
                }
                c2102gl.b(interfaceC0934Dq.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.KV
    public final int a(int i, C2102gl c2102gl) {
        B7 b7 = this.h.I0().get(i);
        C0702m c0702m = c2102gl.o;
        if (!w && (c0702m.Y().f instanceof ClassFileConsumer) != this.v.a()) {
            throw new AssertionError();
        }
        if (b7.x()) {
            C3483z8 c3483z8A = a(i, c0702m.a());
            if (!C3483z8.e && c3483z8A.c.isEmpty() != c3483z8A.d.isEmpty()) {
                throw new AssertionError();
            }
            if (!c3483z8A.c.isEmpty()) {
                c2102gl.h(c3483z8A.a);
                Lr lr = new Lr(16);
                InterfaceC0993Fr it = c3483z8A.d.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Integer) it.next()).intValue();
                    if (lr.a(iIntValue)) {
                        c2102gl.c(i, iIntValue);
                    }
                }
                if (!(b7 instanceof V8)) {
                    c2102gl.d(i, i + 1);
                }
                return i;
            }
            boolean z = b7 instanceof V8;
            this.b |= z;
            if (z) {
                return i;
            }
            return -1;
        }
        if (!a(b7)) {
            return -1;
        }
        for (int i2 : i(i)) {
            c2102gl.d(i, i2);
        }
        this.b |= b7.Q();
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM b(int i) {
        if (i == -2) {
            return this.u.a(this.k.Y().W0, new Supplier() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.i();
                }
            }, this.i.getReference());
        }
        while (true) {
            int i2 = i + 1;
            if (i2 >= this.h.I0().size()) {
                break;
            }
            B7 b7 = this.h.I0().get(i);
            if (!(b7 instanceof M7) && !(b7 instanceof C2284j7)) {
                break;
            }
            i = i2;
        }
        while (i >= 0 && !(this.h.I0().get(i) instanceof C2051g8)) {
            i--;
        }
        if (i < 0) {
            return this.u.a();
        }
        return a(((C2051g8) this.h.I0().get(i)).U());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterable i() {
        return (Iterable) this.h.I0().stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return A8.b((B7) obj);
            }
        }).map(new Function() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return A8.c((B7) obj);
            }
        }).collect(Collectors.toList());
    }

    private C3483z8 a(int i, C0722p1 c0722p1) {
        C3483z8 c3483z8 = this.m;
        if (c3483z8 == null || c3483z8.a > i || i >= c3483z8.b) {
            List<W8> listQ0 = this.h.Q0();
            MR mr = this.l;
            boolean z = this.c;
            ArrayList arrayList = new ArrayList();
            C2732or c2732or = new C2732or(16);
            C3129uS c3129uS = new C3129uS(0);
            int iMin = Integer.MAX_VALUE;
            int iMax = 0;
            boolean z2 = false;
            for (W8 w8 : listQ0) {
                int iA = mr.a(w8.a);
                int iA2 = mr.a(w8.b);
                if (iA <= i) {
                    if (i >= iA2) {
                        iMax = Math.max(iMax, iA2);
                    } else {
                        iMax = Math.max(iMax, iA);
                        iMin = Math.min(iMin, iA2);
                        for (int i2 = 0; i2 < w8.c.size() && !z2; i2++) {
                            C0767v2 c0767v2 = w8.c.get(i2);
                            if (c3129uS.add(c0767v2)) {
                                arrayList.add(c0767v2);
                                c2732or.a(mr.a(w8.d.get(i2)));
                                z2 = c0767v2 == c0722p1.m3;
                            }
                        }
                        if (z2) {
                            break;
                        }
                    }
                } else {
                    iMin = Math.min(iMin, iA);
                }
            }
            if (z && !z2) {
                arrayList.add(c0722p1.m3);
                c2732or.a(-2);
            }
            this.m = new C3483z8(iMax, iMin, arrayList, c2732or);
        }
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(B7 b7) {
        return b7 instanceof C2051g8;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X c(int i) {
        if (this.d) {
            return null;
        }
        return (com.android.tools.r8.graph.X) this.r.get(i);
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean c() {
        return this.d || this.q || this.h.I0().get(this.o).x();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC1915eM c(B7 b7) {
        return ((C2051g8) b7).U();
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i) {
        if (this.d) {
            return null;
        }
        if (this.q) {
            return c(i);
        }
        if (w || !a(this.h.I0().get(this.o))) {
            return (com.android.tools.r8.graph.X) this.s.get(i);
        }
        throw new AssertionError("Outgoing local is undefined for control-flow instructions");
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(final C2102gl c2102gl, int i, boolean z) {
        InterfaceC0992Fq interfaceC0992Fq;
        if (i == -2) {
            if (!w && !this.c) {
                throw new AssertionError();
            }
            this.d = true;
            this.f.b(b(-2));
            c2102gl.a(new C3344xF(EnumC3423yF.EXIT, (S10) ((ArrayList) this.e.h1()).get(0)));
            c2102gl.f(BZip2Constants.BASEBLOCKSIZE);
            this.d = false;
            return;
        }
        B7 b7 = this.h.I0().get(i);
        this.o = i;
        if (z) {
            C1713bl c1713bl = (C1713bl) c2102gl.f().get(i);
            this.a = c1713bl;
            if (i == 0 && c1713bl == null) {
                this.a = (C1713bl) c2102gl.f().get(-1);
            }
            this.f.a((L8) this.t.get(i), i == 0, b(i));
            this.p = this.o;
        }
        if (!w && this.a == null) {
            throw new AssertionError();
        }
        InterfaceC0992Fq interfaceC0992Fq2 = h(this.o).c;
        this.r = interfaceC0992Fq2;
        if (this.q) {
            this.s = interfaceC0992Fq2;
        } else {
            if (!a(this.h.I0().get(this.o))) {
                interfaceC0992Fq = h(this.o + 1).c;
            } else {
                interfaceC0992Fq = AbstractC2568mr.a;
            }
            this.s = interfaceC0992Fq;
        }
        if (b7.x()) {
            L8 l8B = this.f.b();
            C0767v2 c0767v2 = c2102gl.o.a().m3;
            E8 e8 = new E8(l8B.b() + 1, 1);
            l8B.a(e8);
            e8.d[0] = new J8(c0767v2);
            InterfaceC0841Ar it = this.a.e.iterator();
            while (it.hasNext()) {
                int iIntValue = Integer.valueOf(((K) it).t()).intValue();
                L8 l8 = (L8) this.t.get(iIntValue);
                L8 l8A = N8.a(l8, e8, this.j);
                if (l8A != l8) {
                    this.t.a(iIntValue, l8A);
                }
            }
        }
        boolean zEquals = this.r.equals(this.s);
        int i2 = i + 1;
        boolean z2 = (i2 == e() || c2102gl.f().a(i2)) ? false : true;
        if (b7.Q() || (b7 instanceof V8)) {
            if (!w && !this.a.c.isEmpty()) {
                throw new AssertionError();
            }
            if (this.a.e.isEmpty()) {
                InterfaceC0992Fq interfaceC0992Fq3 = this.r;
                Objects.requireNonNull(c2102gl);
                interfaceC0992Fq3.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        c2102gl.a(((Integer) obj).intValue(), (com.android.tools.r8.graph.X) obj2);
                    }
                });
            } else if (!this.r.isEmpty()) {
                C1303Qq c1303Qq = new C1303Qq(16);
                InterfaceC0841Ar it2 = this.a.e.iterator();
                while (it2.hasNext()) {
                    c1303Qq.putAll(h(Integer.valueOf(((K) it2).t()).intValue()).c);
                }
                InterfaceC1912eK it3 = this.r.c().iterator();
                while (it3.hasNext()) {
                    InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it3.next();
                    if (c1303Qq.get(interfaceC0934Dq.a()) != interfaceC0934Dq.getValue()) {
                        c2102gl.a(interfaceC0934Dq.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue());
                    }
                }
            }
        } else if (!zEquals && z2) {
            if (!w && this.r.equals(this.s)) {
                throw new AssertionError();
            }
            InterfaceC1912eK it4 = this.r.c().iterator();
            while (it4.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it4.next();
                if (!((com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue()).equals(this.s.get(interfaceC0934Dq2.a()))) {
                    c2102gl.a(interfaceC0934Dq2.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue());
                }
            }
        }
        b7.a(c2102gl, this.f, this);
        if (z2) {
            if (zEquals) {
                return;
            }
            d(c2102gl);
            return;
        }
        L8 l8B2 = this.f.b();
        if (a(b7)) {
            for (int i3 : i(i)) {
                L8 l9 = (L8) this.t.get(i3);
                L8 l8A2 = N8.a(l9, l8B2, this.j);
                if (l8A2 != l9) {
                    this.t.a(i3, l8A2);
                }
            }
            return;
        }
        L8 l10 = (L8) this.t.get(i2);
        L8 l8A3 = N8.a(l10, l8B2, this.j);
        if (l8A3 != l10) {
            this.t.a(i2, l8A3);
        }
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl) {
        if (this.c) {
            this.d = true;
            c2102gl.a(new C3344xF(EnumC3423yF.EXIT, (S10) ((ArrayList) this.e.h1()).get(0)));
            this.d = false;
        }
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, int i2, boolean z) {
        if (i == -1 || i2 == -2) {
            return;
        }
        this.f.b(b(z ? i2 : i));
        InterfaceC0992Fq interfaceC0992Fq = h(i).c;
        InterfaceC0992Fq interfaceC0992Fq2 = h(i2).c;
        if (!z) {
            InterfaceC1912eK it = interfaceC0992Fq.c().iterator();
            while (it.hasNext()) {
                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                if (interfaceC0992Fq2.get(interfaceC0934Dq.a()) != interfaceC0934Dq.getValue()) {
                    c2102gl.a(interfaceC0934Dq.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue());
                }
            }
        }
        InterfaceC1912eK it2 = interfaceC0992Fq2.c().iterator();
        while (it2.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it2.next();
            if (interfaceC0992Fq.get(interfaceC0934Dq2.a()) != interfaceC0934Dq2.getValue()) {
                c2102gl.b(interfaceC0934Dq2.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue());
            }
        }
        if (this.b || !(this.h.I0().get(i) instanceof C2899r7)) {
            return;
        }
        if (!w && z) {
            throw new AssertionError();
        }
        InterfaceC1912eK it3 = interfaceC0992Fq.c().iterator();
        while (it3.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq3 = (InterfaceC0934Dq) it3.next();
            if (interfaceC0992Fq2.get(interfaceC0934Dq3.a()) == interfaceC0934Dq3.getValue()) {
                c2102gl.a(interfaceC0934Dq3.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq3.getValue());
            }
        }
    }

    public final int a(M7 m7) {
        if (w || this.l.containsKey(m7)) {
            return this.l.a(m7);
        }
        throw new AssertionError();
    }

    public final void a(C2284j7 c2284j7) {
        InterfaceC1456Vp interfaceC1456VpW = c2284j7.W();
        int i = 0;
        final C0767v2[] c0767v2Arr = new C0767v2[interfaceC1456VpW.isEmpty() ? 0 : interfaceC1456VpW.d() + 1];
        C0767v2[] c0767v2Arr2 = new C0767v2[c2284j7.Z().size()];
        c2284j7.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.A8$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i2, Object obj) {
                this.f$0.a(c0767v2Arr, i2, (InterfaceC1348Si) obj);
            }
        });
        Iterator it = c2284j7.Z().iterator();
        while (it.hasNext()) {
            c0767v2Arr2[i] = a((InterfaceC3054tM) it.next());
            i++;
        }
        E8 e8A = this.f.a(c0767v2Arr, c0767v2Arr2, b(this.o));
        if (!w && this.t.get(this.p) == null) {
            throw new AssertionError();
        }
        for (int i2 = this.p; i2 < this.o; i2++) {
            B7 b7 = this.h.I0().get(i2);
            b7.getClass();
            if (!(b7 instanceof C2051g8) && !(b7 instanceof M7)) {
                return;
            }
        }
        this.t.a(this.p, e8A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2[] c0767v2Arr, int i, InterfaceC1348Si interfaceC1348Si) {
        c0767v2Arr[i] = a(interfaceC1348Si);
    }

    private C0767v2 a(InterfaceC1348Si interfaceC1348Si) {
        if (interfaceC1348Si.M()) {
            if (interfaceC1348Si.l()) {
                interfaceC1348Si.O().getClass();
                return C0722p1.S5;
            }
            if (w || interfaceC1348Si.N()) {
                return interfaceC1348Si.j().T();
            }
            throw new AssertionError();
        }
        if (interfaceC1348Si.isPrimitive()) {
            if (interfaceC1348Si.E()) {
                return interfaceC1348Si.H().a(this.k.a());
            }
            if (w || interfaceC1348Si.I()) {
                return interfaceC1348Si.p().U().a(this.k.a());
            }
            throw new AssertionError();
        }
        if (interfaceC1348Si.y()) {
            int iA = a(interfaceC1348Si.R());
            while (true) {
                iA++;
                if (iA >= this.h.I0().size()) {
                    break;
                }
                B7 b7 = this.h.I0().get(iA);
                if (!(b7 instanceof M7) && !(b7 instanceof C2284j7) && !(b7 instanceof C2051g8)) {
                    if (!w && !(b7 instanceof V7)) {
                        throw new AssertionError();
                    }
                    break;
                    break;
                }
            }
            B7 b8 = this.h.I0().get(iA);
            if (w || (b8 instanceof V7)) {
                return ((V7) b8).getType();
            }
            throw new AssertionError();
        }
        if (interfaceC1348Si.A()) {
            return this.i.t();
        }
        if (w || interfaceC1348Si.q()) {
            return null;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i, int i2) {
        return (com.android.tools.r8.graph.X) h(i2).c.get(i);
    }

    @Override // com.android.tools.r8.internal.KV
    public final C0767v2 a(int i, int i2, ML.a aVar) {
        H8 h8A;
        if (!w && this.h.O0() == 1) {
            throw new AssertionError();
        }
        if (AbstractC2590n7.a(this.h.O0())) {
            return null;
        }
        L8 l8 = (L8) this.t.get(i2);
        if (l8 == null) {
            this.k.Y().e.warning(new C0771w(this.j, this.i.getReference(), "Could not find stack map for block at offset " + i2 + ". This is most likely due to invalid stack maps in input."));
            return null;
        }
        if (H8.a(i)) {
            h8A = l8.b(H8.b(i));
        } else {
            h8A = l8.a(i);
        }
        if (h8A == null) {
            if (aVar == ML.a.b) {
                com.android.tools.r8.graph.X xA = a(i, i2);
                if (xA != null) {
                    return xA.b;
                }
                ArrayList arrayList = new ArrayList();
                for (C0753u.a aVar2 : this.g) {
                    if (aVar2.b() == i) {
                        arrayList.add(aVar2);
                    }
                }
                if (arrayList.size() == 1) {
                    return ((C0753u.a) arrayList.get(0)).c().b;
                }
            }
            return null;
        }
        if (h8A.a()) {
            return h8A.c;
        }
        if (h8A.b.a()) {
            return this.k.a().Z1;
        }
        return h8A.b.d().a(this.k.a());
    }

    private static boolean a(B7 b7) {
        return b7.Q() || b7.A() != null || (b7 instanceof U8) || (b7 instanceof V8);
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean a() {
        return this.h.O0() == 4;
    }

    public final AbstractC1915eM a(AbstractC1915eM abstractC1915eM) {
        return this.u.b(abstractC1915eM.a().a(this.u.a(abstractC1915eM.b())).a());
    }
}

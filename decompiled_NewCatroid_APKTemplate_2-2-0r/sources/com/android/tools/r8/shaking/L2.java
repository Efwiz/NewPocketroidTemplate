package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C0855Bf;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.naming.C3664u;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L2 {
    private final AbstractC1639am A;
    private final AbstractC1639am B;
    private final AbstractC1639am C;
    private final boolean D;
    private final C3826x2 E;
    private final C3840z3 F;
    private final C3840z3 G;
    private final C3840z3 H;
    private final boolean I;
    private final boolean J;
    private final boolean K;
    private final int L;
    private final String a;
    private final C0722p1 b;
    private final AbstractC1639am c;
    private final com.android.tools.r8.utils.A1.n d;
    private final String e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final boolean k;
    private final Path l;
    private final boolean m;
    private final Path n;
    private final boolean o;
    private final Path p;
    private final Path q;
    private final boolean r;
    private final String s;
    private final C3718f3 t;
    private final C3826x2 u;
    private final C3826x2 v;
    private final C3826x2 w;
    protected final AbstractC1639am x;
    private final boolean y;
    private final Path z;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean R = true;
        private boolean A;
        private Path B;
        private Path C;
        private Path D;
        private Path E;
        private boolean F;
        private Origin G;
        private Position H;
        private final C3826x2.a I;
        private final C3834y3 J;
        private final C3834y3 K;
        private final C3834y3 L;
        private boolean M;
        private boolean N;
        private boolean O;
        private boolean P;
        private int Q;
        private final ArrayList a;
        private final ArrayList b;
        private final ArrayList c;
        private final com.android.tools.r8.utils.O2 d;
        private com.android.tools.r8.utils.A1.n e;
        private String f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private boolean l;
        private Path m;
        private boolean n;
        private Path o;
        private boolean p;
        private Path q;
        private Path r;
        private boolean s;
        private String t;
        private final ArrayList u;
        private final C3826x2.a v;
        private final C3826x2.a w;
        private final C3826x2.a x;
        protected final LinkedHashSet y;
        private final C0722p1 z;

        private a(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = new ArrayList();
            this.e = com.android.tools.r8.utils.A1.n.a;
            this.f = "";
            this.i = true;
            this.j = true;
            this.k = true;
            this.u = new ArrayList();
            this.v = C3826x2.a();
            this.w = C3826x2.a();
            this.x = C3826x2.a();
            this.y = OU.d();
            this.I = C3826x2.a();
            this.J = C3840z3.a().a(E3.a().a().b());
            this.K = C3840z3.a().a(E3.a().a().b());
            this.L = C3840z3.a().b();
            this.M = false;
            this.N = false;
            this.O = false;
            this.P = false;
            this.Q = -1;
            this.z = c0722p1;
            this.d = o2;
        }

        public final void a(String str) {
            this.a.add(str);
        }

        public final void b(ArrayList arrayList) {
            this.c.addAll(arrayList);
        }

        public final void c(String str) {
            this.e = com.android.tools.r8.utils.A1.n.c;
            this.f = str;
        }

        public a d() {
            this.i = false;
            return this;
        }

        public a e() {
            this.k = false;
            return this;
        }

        public void f(Path path) {
            if (!R && !this.p) {
                throw new AssertionError();
            }
            this.q = path;
        }

        public final void g(Path path) {
            this.o = path;
        }

        public final ArrayList h() {
            return this.b;
        }

        final Origin i() {
            return this.G;
        }

        final Position j() {
            return this.H;
        }

        public final com.android.tools.r8.utils.A1.n k() {
            return this.e;
        }

        public final boolean l() {
            return this.r != null;
        }

        final boolean m() {
            return this.N;
        }

        final boolean n() {
            return this.F;
        }

        final boolean o() {
            return this.j;
        }

        public final void p() {
            this.g = true;
        }

        public final void q() {
            this.N = true;
        }

        public final void r() {
            this.O = true;
        }

        public final void s() {
            this.l = true;
        }

        public final void t() {
            this.n = true;
        }

        public final void u() {
            this.s = true;
        }

        public final void a(ArrayList arrayList) {
            this.b.addAll(arrayList);
        }

        public final void b(String str) {
            this.e = com.android.tools.r8.utils.A1.n.d;
            this.f = str;
        }

        public void d(String str) {
            this.t = str;
        }

        public final void e(Path path) {
            if (!R && !this.l) {
                throw new AssertionError();
            }
            this.m = path;
        }

        public final void g() {
            this.P = true;
        }

        public final void h(Path path) {
            this.B = path;
        }

        public final void a(Path path) {
            this.r = path;
        }

        public a c() {
            this.j = false;
            return this;
        }

        public final void d(AbstractC3833y2 abstractC3833y2) {
            this.v.a(abstractC3833y2);
        }

        public final void f() {
            this.L.c();
        }

        public a a(List<String> list) {
            this.u.addAll(list);
            return this;
        }

        public void b(boolean z) {
            this.h = z;
        }

        public void c(boolean z) {
            this.p = z;
        }

        public void d(boolean z) {
            this.A = z;
        }

        public void a(S2 s2) {
            this.y.add(s2);
        }

        public final void b(AbstractC3833y2 abstractC3833y2) {
            this.x.a(abstractC3833y2);
        }

        public final void c(AbstractC3833y2 abstractC3833y2) {
            this.w.a(abstractC3833y2);
        }

        public final void d(Path path) {
            this.E = path;
        }

        public final void a(Origin origin, Position position) {
            if (!R && origin == null) {
                throw new AssertionError();
            }
            this.F = true;
            this.G = origin;
            this.H = position;
        }

        public final void b(Path path) {
            this.D = path;
        }

        public final void c(Path path) {
            this.C = path;
        }

        public final a b(E3 e3) {
            this.J.a(e3);
            return this;
        }

        public final void c(E3 e3) {
            this.L.a(e3);
        }

        /* JADX WARN: Code duplicated, block: B:15:0x00c0  */
        /* JADX WARN: Code duplicated, block: B:16:0x00c3  */
        public final L2 b() {
            boolean z;
            int i;
            int i2;
            int i3;
            String strJoin = String.join(System.lineSeparator(), this.a);
            C0722p1 c0722p1 = this.z;
            ArrayList arrayList = this.b;
            ArrayList arrayList2 = this.c;
            com.android.tools.r8.utils.A1.n nVar = this.e;
            String str = this.f;
            boolean z2 = this.g;
            boolean z3 = this.h;
            boolean z4 = this.i;
            boolean z5 = this.j;
            boolean z6 = this.k;
            boolean z7 = this.l;
            Path path = this.m;
            boolean z8 = this.n;
            Path path2 = this.o;
            boolean z9 = this.p;
            Path path3 = this.q;
            Path path4 = this.r;
            boolean z10 = this.s;
            String str2 = this.t;
            C3718f3 c3718f3A = C3718f3.a(this.u);
            C3826x2 c3826x2A = this.v.a();
            C3826x2 c3826x2A2 = this.w.a();
            C3826x2 c3826x2A3 = this.x.a();
            LinkedHashSet linkedHashSet = this.y;
            boolean z11 = this.A;
            Path path5 = this.B;
            AbstractC1639am abstractC1639amA = C3664u.a(this.d, this.C);
            AbstractC1639am abstractC1639amA2 = C3664u.a(this.d, this.D);
            AbstractC1639am abstractC1639amA3 = C3664u.a(this.d, this.E);
            boolean z12 = this.F;
            C3826x2 c3826x2A4 = this.I.a();
            C3840z3 c3840z3A = this.J.a();
            C3840z3 c3840z3A2 = this.K.a();
            C3840z3 c3840z3A3 = this.L.a();
            boolean z13 = this.N;
            boolean z14 = this.O;
            boolean z15 = this.P;
            if (!R) {
                int i4 = this.Q;
                z = z15;
                if (i4 != -1) {
                    i = 1;
                    if (i4 < 1) {
                        throw new AssertionError();
                    }
                }
                i2 = this.Q;
                if (i2 >= i) {
                    i3 = i2;
                } else {
                    i3 = i;
                }
                L2 l2 = new L2(strJoin, c0722p1, arrayList, arrayList2, nVar, str, z2, z3, z4, z5, z6, z7, path, z8, path2, z9, path3, path4, z10, str2, c3718f3A, c3826x2A, c3826x2A2, c3826x2A3, linkedHashSet, z11, path5, abstractC1639amA, abstractC1639amA2, abstractC1639amA3, z12, c3826x2A4, c3840z3A, c3840z3A2, c3840z3A3, z13, z14, z, i3);
                this.d.a();
                return l2;
            }
            z = z15;
            i = 1;
            i2 = this.Q;
            if (i2 >= i) {
                i3 = i2;
            } else {
                i3 = i;
            }
            L2 l3 = new L2(strJoin, c0722p1, arrayList, arrayList2, nVar, str, z2, z3, z4, z5, z6, z7, path, z8, path2, z9, path3, path4, z10, str2, c3718f3A, c3826x2A, c3826x2A2, c3826x2A3, linkedHashSet, z11, path5, abstractC1639amA, abstractC1639amA2, abstractC1639amA3, z12, c3826x2A4, c3840z3A, c3840z3A2, c3840z3A3, z13, z14, z, i3);
            this.d.a();
            return l3;
        }

        public final void a(AbstractC3833y2 abstractC3833y2) {
            this.I.a(abstractC3833y2);
        }

        public final void a(E3 e3) {
            this.K.a(e3);
        }

        public final void a(boolean z) {
            this.M = z;
        }

        public final void a(int i) {
            if (!R && i < 1) {
                throw new AssertionError();
            }
            int i2 = this.Q;
            if (i2 == -1) {
                this.Q = i;
            } else {
                this.Q = Math.min(i2, i);
            }
        }

        public L2 a() {
            if (this.M && !this.j) {
                this.u.addAll(C3718f3.t);
            }
            if (this.e == com.android.tools.r8.utils.A1.n.a && this.j) {
                this.e = com.android.tools.r8.utils.A1.n.b;
            }
            return b();
        }
    }

    private L2(String str, C0722p1 c0722p1, ArrayList arrayList, ArrayList arrayList2, com.android.tools.r8.utils.A1.n nVar, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Path path, boolean z7, Path path2, boolean z8, Path path3, Path path4, boolean z9, String str3, C3718f3 c3718f3, C3826x2 c3826x2, C3826x2 c3826x3, C3826x2 c3826x4, LinkedHashSet linkedHashSet, boolean z10, Path path5, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, AbstractC1639am abstractC1639am3, boolean z11, C3826x2 c3826x5, C3840z3 c3840z3, C3840z3 c3840z4, C3840z3 c3840z5, boolean z12, boolean z13, boolean z14, int i) {
        this.a = str;
        this.b = c0722p1;
        AbstractC1639am.a(arrayList);
        this.c = AbstractC1639am.a(arrayList2);
        this.d = nVar;
        this.e = str2;
        this.f = z;
        this.g = z2;
        this.h = z3;
        this.i = z4;
        this.j = z5;
        this.k = z6;
        this.l = path;
        this.m = z7;
        this.n = path2;
        this.o = z8;
        this.p = path3;
        this.q = path4;
        this.r = z9;
        this.s = str3;
        this.t = c3718f3;
        this.u = c3826x2;
        this.v = c3826x3;
        this.w = c3826x4;
        this.x = AbstractC1639am.a(linkedHashSet);
        this.y = z10;
        this.z = path5;
        this.A = abstractC1639am;
        this.B = abstractC1639am2;
        this.C = abstractC1639am3;
        this.D = z11;
        this.E = c3826x5;
        this.F = c3840z3;
        this.G = c3840z4;
        this.H = c3840z5;
        this.I = z12;
        this.J = z13;
        this.K = z14;
        this.L = i;
    }

    public static a a(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        return new a(c0722p1, o2);
    }

    public final boolean A() {
        return this.f;
    }

    public boolean B() {
        return this.I;
    }

    public final boolean C() {
        return this.g;
    }

    public boolean D() {
        return this.D;
    }

    public boolean E() {
        return this.i;
    }

    public boolean F() {
        return this.h;
    }

    public boolean G() {
        return this.k;
    }

    public final boolean H() {
        return this.o;
    }

    public boolean I() {
        return this.y;
    }

    public boolean J() {
        return this.m;
    }

    public final boolean K() {
        return this.K;
    }

    public boolean L() {
        return this.j;
    }

    public boolean M() {
        return this.r;
    }

    public C3840z3 b() {
        return this.G;
    }

    public C3840z3 c() {
        return this.F;
    }

    public Path d() {
        return this.q;
    }

    public final AbstractC1639am e() {
        return this.B;
    }

    public final C0722p1 f() {
        return this.b;
    }

    public C3826x2 g() {
        return this.w;
    }

    public C3718f3 h() {
        return this.t;
    }

    public C3840z3 i() {
        return this.H;
    }

    public final C3826x2 j() {
        return this.u;
    }

    public List<J0> k() {
        return this.c;
    }

    public final int l() {
        return this.L;
    }

    public final AbstractC1639am m() {
        return this.A;
    }

    public final AbstractC1639am n() {
        return this.C;
    }

    public com.android.tools.r8.utils.A1.n o() {
        return this.d;
    }

    public String p() {
        return this.e;
    }

    public String q() {
        return this.a;
    }

    public Path r() {
        return this.l;
    }

    public Path s() {
        return this.p;
    }

    public Path t() {
        return this.n;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        C3718f3 c3718f3 = this.t;
        if (c3718f3.a || c3718f3.b || c3718f3.c || c3718f3.d || c3718f3.e || c3718f3.f || c3718f3.j || c3718f3.k || c3718f3.l || c3718f3.m || c3718f3.n || c3718f3.o || c3718f3.p || c3718f3.q || c3718f3.r || c3718f3.s) {
            c3718f3.a(sb);
            sb.append(com.android.tools.r8.utils.Y2.c);
        }
        AbstractC2963s10 it = this.x.iterator();
        while (it.hasNext()) {
            ((S2) it.next()).a(sb);
            sb.append(com.android.tools.r8.utils.Y2.c);
        }
        return sb.toString();
    }

    public String u() {
        return this.s;
    }

    public List<S2> v() {
        return this.x;
    }

    public Path w() {
        return this.z;
    }

    public boolean x() {
        return this.q != null;
    }

    public final boolean y() {
        return this.J;
    }

    public final boolean z() {
        return !this.v.c();
    }

    public final C3826x2 a(C0855Bf c0855Bf) {
        return this.v;
    }

    public C3826x2 a() {
        return this.E;
    }
}

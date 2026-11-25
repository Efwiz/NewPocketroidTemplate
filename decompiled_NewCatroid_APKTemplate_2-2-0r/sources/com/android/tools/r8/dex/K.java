package com.android.tools.r8.dex;

import com.android.tools.r8.errors.UnsupportedDefaultInterfaceMethodDiagnostic;
import com.android.tools.r8.errors.UnsupportedInvokeCustomDiagnostic;
import com.android.tools.r8.errors.UnsupportedPrivateInterfaceMethodDiagnostic;
import com.android.tools.r8.errors.UnsupportedStaticInterfaceMethodDiagnostic;
import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0640e0;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.graph.I3;
import com.android.tools.r8.graph.M0;
import com.android.tools.r8.graph.M2;
import com.android.tools.r8.graph.R0;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C3505zR;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.H0;
import com.android.tools.r8.utils.I1;
import com.android.tools.r8.utils.Q1;
import java.security.MessageDigest;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.zip.Adler32;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final AbstractC1476Wj b;
    private final C0645e5 c;
    private final A1 d;
    private final C0586x e;
    private final I f;
    private final r g;
    private final r0 h;
    private final boolean i;

    public K(C0702m c0702m, C0586x c0586x, C0645e5 c0645e5, r rVar, r0 r0Var, boolean z) {
        this.a = c0702m;
        this.b = c0702m.E();
        this.c = c0645e5;
        A1 a1Y = c0702m.Y();
        this.d = a1Y;
        this.e = c0586x;
        this.f = new I(a1Y);
        this.g = rVar;
        this.h = r0Var;
        this.i = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        S0 s0A = c0729q2.a(this.a.z());
        if (s0A != null) {
            I i = this.f;
            S0 s0 = (S0) i.l.put(c0729q2, s0A);
            if (!I.n && s0 != null) {
                throw new AssertionError();
            }
            I.a(i.j, s0A);
        }
    }

    public final E b() {
        F fA = a(0, 1);
        return new E(fA.b.d(), fA.c.w);
    }

    public final I c() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(C0729q2 c0729q2) {
        int iA;
        this.g.a(c0729q2);
        this.e.f(this.c.b(c0729q2.d));
        this.e.f(c0729q2.e.Q());
        C0586x c0586x = this.e;
        C0767v2 c0767v2 = c0729q2.f;
        c0586x.f(c0767v2 == null ? -1 : this.c.b(c0767v2));
        C0586x c0586x2 = this.e;
        I i = this.f;
        C0800x2 c0800x2 = c0729q2.g;
        i.getClass();
        int iA2 = 0;
        c0586x2.f(c0800x2.isEmpty() ? 0 : I.b(i.c, c0800x2));
        C0586x c0586x3 = this.e;
        C0759u2 c0759u2 = c0729q2.h;
        c0586x3.f(c0759u2 == null ? -1 : this.c.a(c0759u2));
        C0586x c0586x4 = this.e;
        I i2 = this.f;
        i2.getClass();
        if (c0729q2.a2()) {
            iA = i2.h.a(i2.b(c0729q2));
            if (!I.n && iA == -2) {
                throw new AssertionError();
            }
        } else {
            iA = 0;
        }
        c0586x4.f(iA);
        C0586x c0586x5 = this.e;
        if (c0729q2.c2() || c0729q2.b2()) {
            iA2 = this.f.i.a(c0729q2);
            if (!I.n && (iA2 == -1 || iA2 == -2)) {
                throw new AssertionError();
            }
        }
        c0586x5.f(iA2);
        C0586x c0586x6 = this.e;
        I i3 = this.f;
        c0586x6.f(I.b(i3.j, i3.c(c0729q2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0759u2 c0759u2) {
        int iA;
        C0586x c0586x = this.e;
        C3505zR c3505zR = this.f.d;
        if (c0759u2 == null) {
            iA = 0;
        } else {
            iA = c3505zR.a(c0759u2);
            if (!I.n && (iA == -1 || iA == -2)) {
                throw new AssertionError();
            }
        }
        c0586x.f(iA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        this.e.f(this.c.a(this.a.z().c(c0767v2)));
    }

    public static void a(R0 r0, C0586x c0586x, final C0645e5 c0645e5) {
        ArrayList<C0649f0> arrayList = new ArrayList(Arrays.asList(r0.b));
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda25
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return K.a(c0645e5, (C0649f0) obj, (C0649f0) obj2);
            }
        });
        int iB = c0645e5.b(r0.a);
        c0586x.getClass();
        Q1.b(c0586x, iB);
        Q1.b(c0586x, arrayList.size());
        for (C0649f0 c0649f0 : arrayList) {
            Q1.b(c0586x, c0645e5.a(c0649f0.a));
            c0649f0.b.a(c0586x, c0645e5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b(W0 w0) {
        I i = this.f;
        C0663g5 c0663g5 = w0.h;
        i.getClass();
        if (c0663g5.isEmpty()) {
            return 0;
        }
        return I.b(i.g, c0663g5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        if (!j && !c0729q2.c2() && !c0729q2.b2()) {
            throw new AssertionError();
        }
        I i = this.f;
        int iA = i.i.a(this.e.c(), c0729q2);
        if (!I.n && iA > -1) {
            throw new AssertionError();
        }
        C0586x c0586x = this.e;
        int size = c0729q2.N1().size();
        c0586x.getClass();
        Q1.b(c0586x, size);
        C0586x c0586x2 = this.e;
        int size2 = c0729q2.w1().size();
        c0586x2.getClass();
        Q1.b(c0586x2, size2);
        C0586x c0586x3 = this.e;
        int iJ = c0729q2.Y().j();
        c0586x3.getClass();
        Q1.b(c0586x3, iJ);
        C0586x c0586x4 = this.e;
        int iK = c0729q2.Y().k();
        c0586x4.getClass();
        Q1.b(c0586x4, iK);
        a((List) c0729q2.N1());
        a((List) c0729q2.w1());
        a(c0729q2.V0());
        a(c0729q2.P1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(C0645e5 c0645e5, C0649f0 c0649f0, C0649f0 c0649f1) {
        C0759u2 c0759u2 = c0649f0.a;
        C0759u2 c0759u3 = c0649f1.a;
        com.android.tools.r8.utils.structural.a aVarD = c0645e5.d();
        c0759u2.getClass();
        return aVarD.a(c0759u2, c0759u3);
    }

    public final K a() {
        new J(this, this.a, this.c.c()).a(this.c.c());
        this.f.i.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
        if (!j && this.f.d.size() != 0) {
            throw new AssertionError();
        }
        for (C0759u2 c0759u2 : this.c.m()) {
            C3505zR c3505zR = this.f.d;
            if (!c3505zR.containsKey(c0759u2)) {
                c3505zR.a(-1, c0759u2);
            }
        }
        Iterator it = this.c.l().iterator();
        while (it.hasNext()) {
            this.f.a(((C0737r2) it.next()).u0());
        }
        AbstractC0594a1.a(this.f, this.c.b());
        AbstractC0594a1.a(this.f, this.c.c());
        return this;
    }

    public static void b(G g, C0586x c0586x) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA1);
            byte[] bArrA = c0586x.a();
            int i = g.a;
            messageDigest.update(bArrA, i + 32, (g.w - i) - 32);
            messageDigest.digest(c0586x.a(), g.a + 12, 20);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final F a(int i, int i2) {
        byte b;
        byte[] bArrA;
        int iA;
        for (C0729q2 c0729q2 : this.c.c()) {
            if (c0729q2.isInterface()) {
                Iterator<W0> it = c0729q2.V0().iterator();
                while (it.hasNext()) {
                    a(c0729q2, it.next());
                }
                Iterator<W0> it2 = c0729q2.P1().iterator();
                while (it2.hasNext()) {
                    a(c0729q2, it2.next());
                }
            }
        }
        if (!j && !this.a.a().i()) {
            EnumC3891f enumC3891fB0 = this.d.B0();
            for (Y0 y0 : this.c.e()) {
                if (!j && !y0.f.c(enumC3891fB0)) {
                    throw new AssertionError();
                }
            }
            for (C0684j2 c0684j2 : this.c.k()) {
                if (!j && !c0684j2.f.c(enumC3891fB0)) {
                    throw new AssertionError();
                }
            }
            for (C0767v2 c0767v2 : this.c.n()) {
                if (c0767v2.O0()) {
                    boolean z = j;
                    if (!z && !C0759u2.a(enumC3891fB0, c0767v2.F0())) {
                        throw new AssertionError();
                    }
                    if (!z) {
                        int i3 = com.android.tools.r8.synthesis.L.W;
                        com.android.tools.r8.synthesis.L.a(c0767v2.b1());
                    }
                }
            }
        }
        C0645e5 c0645e5 = this.c;
        boolean z2 = this.i;
        boolean z3 = G.x;
        int i4 = 2;
        if (!z3 && i != 0 && i2 != 2) {
            throw new AssertionError();
        }
        int i5 = (i2 == 2 ? 116 : 112) + i;
        int size = i5 + (z2 ? c0645e5.m().size() * 4 : 0);
        int size2 = (c0645e5.n().size() * 4) + size;
        int size3 = (c0645e5.l().size() * 12) + size2;
        int size4 = (c0645e5.e().size() * 8) + size3;
        int size5 = (c0645e5.k().size() * 8) + size4;
        int length = (c0645e5.c().length * 32) + size5;
        int size6 = (c0645e5.b().size() * 4) + length;
        int size7 = (c0645e5.j().size() * 8) + size6;
        final G g = new G(i, i5, size, size2, size3, size4, size5, length, size6, size7, i2);
        int i6 = -1;
        if (!z3 && g.l != -1) {
            throw new AssertionError();
        }
        g.l = size7;
        W wA = W.a(this.a, this.f, this.h);
        Collection<o5> collectionF = wA.f();
        C0586x c0586x = this.e;
        if (!z3 && !G.a(g.l, true)) {
            throw new AssertionError();
        }
        int i7 = g.l;
        Iterator it3 = collectionF.iterator();
        int i8 = 0;
        while (it3.hasNext()) {
            int i9 = (i8 + 3) & (-4);
            M2 m2U0 = ((o5) it3.next()).e().a1().u0();
            int length2 = (m2U0.s().length * 8) + (m2U0.V() * i4) + 16;
            if (m2U0.z().length > 0) {
                length2 = Q1.d(m2U0.z().length) + ((length2 + 3) & (-4));
                C0772w0.b[] bVarArrZ = m2U0.z();
                int length3 = bVarArrZ.length;
                int i10 = 0;
                while (i10 < length3) {
                    C0772w0.b bVar = bVarArrZ[i10];
                    boolean z4 = bVar.b != i6;
                    int iC = Q1.c(z4 ? -bVar.a.length : bVar.a.length) + length2;
                    C0772w0.b.a[] aVarArr = bVar.a;
                    int length4 = aVarArr.length;
                    Iterator it4 = it3;
                    int i11 = 0;
                    while (i11 < length4) {
                        int i12 = length4;
                        C0772w0.b.a aVar = aVarArr[i11];
                        iC = Q1.d(aVar.b) + Q1.d(this.c.b(aVar.a(this.b))) + iC;
                        i11++;
                        length4 = i12;
                        bVarArrZ = bVarArrZ;
                        length3 = length3;
                    }
                    C0772w0.b[] bVarArr = bVarArrZ;
                    int i13 = length3;
                    length2 = z4 ? Q1.d(bVar.b) + iC : iC;
                    i10++;
                    it3 = it4;
                    bVarArrZ = bVarArr;
                    length3 = i13;
                    i6 = -1;
                }
            }
            i8 = i9 + length2;
            it3 = it3;
            i6 = -1;
            i4 = 2;
        }
        c0586x.e(i7 + i8);
        if (((AbstractCollection) this.f.b.keySet()).isEmpty()) {
            if (!G.x && g.m != -1) {
                throw new AssertionError();
            }
            g.m = 0;
        } else {
            int iA2 = this.e.a(1);
            if (!G.x && g.m != -1) {
                throw new AssertionError();
            }
            g.m = iA2;
            HashSet hashSet = new HashSet(this.f.b.keySet().size());
            Iterator<o5> it5 = collectionF.iterator();
            while (it5.hasNext()) {
                M0 m0F = it5.next().e().a1().u0().F();
                if (m0F != null && hashSet.add(m0F)) {
                    I.a(m0F, this.e.c(), this.f.b);
                    this.e.a(new C0583u(m0F, this.c, this.b).a());
                }
            }
        }
        int iA3 = this.e.a(4);
        boolean z5 = G.x;
        if (!z5 && g.n != -1) {
            throw new AssertionError();
        }
        g.n = iA3;
        C0586x c0586x2 = this.e;
        if (!z5 && !G.a(g.l, true)) {
            throw new AssertionError();
        }
        c0586x2.e(g.l);
        boolean z6 = j;
        if (!z6 && this.e.c() % 4 != 0) {
            throw new AssertionError();
        }
        a(collectionF, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.a(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((o5) obj);
            }
        }, 4);
        if (!z6) {
            if (!z5 && !G.a(g.m, false)) {
                throw new AssertionError();
            }
            if (g.m != 0) {
                int iC2 = this.e.c();
                if (!z5 && !G.a(g.m, false)) {
                    throw new AssertionError();
                }
                if (iC2 != g.m) {
                    throw new AssertionError();
                }
            }
        }
        C0586x c0586x3 = this.e;
        if (!z5 && !G.a(g.n, true)) {
            throw new AssertionError();
        }
        c0586x3.e(g.n);
        a(wA.i(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.a(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0800x2) obj);
            }
        }, 1);
        if (this.i) {
            a(wA.h(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    g.h(((Integer) obj).intValue());
                }
            }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0759u2) obj);
                }
            }, 1);
            b = 0;
        } else {
            b = 0;
            g.o = 0;
        }
        a(wA.b(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.e(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0633d0) obj);
            }
        }, 1);
        a(wA.e(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.f(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        }, 1);
        a(wA.g(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.g(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((S0) obj);
            }
        }, 1);
        a(wA.c(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.d(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0658g0) obj);
            }
        }, 4);
        a(wA.d(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.c(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0663g5) obj);
            }
        }, 4);
        a(wA.a(), new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                g.b(((Integer) obj).intValue());
            }
        }, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0640e0) obj);
            }
        }, 4);
        int iA4 = this.e.a(4);
        g.v = iA4;
        this.e.d(4);
        byte b2 = b;
        int i14 = b2;
        for (H h : g.a(this, 0, this.f.d.keySet().size(), g.b, g.c())) {
            if (this.i) {
                iA = h.a(this.e);
            } else {
                iA = (h.c != 0 || h.a == 8194) ? 1 : b2;
            }
            i14 += iA;
        }
        this.e.e(iA4);
        this.e.f(i14);
        this.e.d(i14 * 12);
        g.w = this.e.c();
        this.e.e((g.k == 2 ? 116 : 112) + g.a);
        if (this.i) {
            a(this.c.m(), g.b, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0759u2) obj);
                }
            });
        } else if (!j && g.b != g.c) {
            throw new AssertionError();
        }
        a(this.c.n(), g.c, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        });
        a(this.c.l(), g.d, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0737r2) obj);
            }
        });
        a(this.c.e(), g.e, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Y0) obj);
            }
        });
        a(this.c.k(), g.f, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0684j2) obj);
            }
        });
        C0729q2[] c0729q2ArrC = this.c.c();
        int i15 = g.g;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0729q2) obj);
            }
        };
        if (!j && this.e.c() != i15) {
            throw new AssertionError();
        }
        int length5 = c0729q2ArrC.length;
        for (int i16 = b2; i16 < length5; i16++) {
            consumer.accept(c0729q2ArrC[i16]);
        }
        a(this.c.b(), g.h, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0721p0) obj);
            }
        });
        a(this.c.j(), g.i, new Consumer() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0699l2) obj);
            }
        });
        this.e.e(g.a);
        this.e.a(C0582t.a);
        C0586x c0586x4 = this.e;
        A1 a1 = this.d;
        A1.p pVar = a1.n1;
        if (pVar.j) {
            bArrA = H0.V41.a();
        } else if (pVar.i && a1.B0().a(EnumC3891f.R)) {
            bArrA = H0.V40.a();
        } else {
            A1 a2 = this.d;
            byte[] bArr = a2.n1.m;
            bArrA = bArr != null ? bArr : H0.c(a2.B0()).a();
        }
        c0586x4.a(bArrA);
        this.e.a(b2);
        this.e.e(g.a + 32);
        this.e.f(g.w - g.a);
        this.e.f(g.k == 2 ? 116 : 112);
        this.e.f(305419896);
        this.e.f(b2);
        this.e.f(b2);
        this.e.f(g.v);
        int size8 = this.c.m().size();
        this.e.f(size8);
        this.e.f(size8 == 0 ? b2 : g.b);
        int size9 = this.c.n().size();
        this.e.f(size9);
        this.e.f(size9 == 0 ? b2 : g.c);
        int size10 = this.c.l().size();
        this.e.f(size10);
        this.e.f(size10 == 0 ? b2 : g.d);
        int size11 = this.c.e().size();
        this.e.f(size11);
        this.e.f(size11 == 0 ? b2 : g.e);
        int size12 = this.c.k().size();
        this.e.f(size12);
        this.e.f(size12 == 0 ? b2 : g.f);
        int length6 = this.c.c().length;
        this.e.f(length6);
        this.e.f(length6 == 0 ? b2 : g.g);
        if (g.k == 2) {
            this.e.f(b2);
            this.e.f(b2);
            this.e.f(g.a);
        } else {
            C0586x c0586x5 = this.e;
            int i17 = g.w - g.j;
            if (!G.x && i17 % 4 != 0) {
                throw new AssertionError();
            }
            c0586x5.f(i17);
            this.e.f(g.j);
        }
        if (!j && this.e.c() != g.b) {
            throw new AssertionError();
        }
        if (this.i) {
            b(g, this.e);
            a(g, this.e);
        }
        return new F(this, this.e, g);
    }

    private void a(C0729q2 c0729q2, W0 w0) {
        if (this.a.a().a(w0.getReference())) {
            return;
        }
        if (w0.f.o()) {
            if (!this.d.M()) {
                A1 a1 = this.d;
                if (!a1.n1.h0) {
                    a1.e.a(new UnsupportedStaticInterfaceMethodDiagnostic(c0729q2.getOrigin(), MethodPosition.create(w0)));
                    throw null;
                }
            }
        } else if (!w0.C1()) {
            if (!w0.f.W() && !w0.f.j() && !this.d.M()) {
                this.d.e.a(new UnsupportedDefaultInterfaceMethodDiagnostic(c0729q2.getOrigin(), MethodPosition.create(w0)));
                throw null;
            }
        } else {
            throw new C1367Tb("Interface must not have constructors: " + w0.getReference().r0());
        }
        if (w0.f.j()) {
            if (this.d.X()) {
                return;
            }
            this.d.e.a(new UnsupportedPrivateInterfaceMethodDiagnostic(c0729q2.getOrigin(), MethodPosition.create(w0)));
            throw null;
        }
        if (w0.f.m()) {
            return;
        }
        throw new C1367Tb("Interface methods must not be protected or package private: " + w0.getReference().r0());
    }

    private void a(Collection collection, int i, Consumer consumer) {
        if (!j && this.e.c() != i) {
            throw new AssertionError();
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            consumer.accept((I3) it.next());
        }
    }

    private void a(Collection collection, Consumer consumer, Consumer consumer2, int i) {
        if (collection.isEmpty()) {
            consumer.accept(0);
        } else {
            consumer.accept(Integer.valueOf(this.e.a(i)));
            collection.forEach(consumer2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0737r2 c0737r2) {
        this.e.f(this.c.a(c0737r2.d));
        this.e.f(this.c.b(c0737r2.e));
        C0586x c0586x = this.e;
        I i = this.f;
        C0800x2 c0800x2 = c0737r2.f;
        i.getClass();
        c0586x.f(c0800x2.isEmpty() ? 0 : I.b(i.c, c0800x2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Y0 y0) {
        int iB = this.c.b(y0.e);
        boolean z = j;
        if (!z && (iB & 65535) != iB) {
            throw new AssertionError();
        }
        this.e.a((short) iB);
        int iB2 = this.c.b(y0.h);
        if (!z && (iB2 & 65535) != iB2) {
            throw new AssertionError();
        }
        this.e.a((short) iB2);
        this.e.f(this.c.a(this.a.z().a(y0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2) {
        int iB = this.c.b(c0684j2.e);
        boolean z = j;
        if (!z && (iB & 65535) != iB) {
            throw new AssertionError();
        }
        this.e.a((short) iB);
        int iA = this.c.a(c0684j2.h);
        if (!z && (iA & 65535) != iA) {
            throw new AssertionError();
        }
        this.e.a((short) iA);
        this.e.f(this.c.a(this.a.z().a(c0684j2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(o5 o5Var) {
        M2 m2U0 = o5Var.e().a1().u0();
        int iA = this.f.a.a(this.e.a(4), o5Var.e());
        int i = -1;
        if (!I.n && iA > -1) {
            throw new AssertionError();
        }
        this.e.a((short) m2U0.a(o5Var));
        this.e.a((short) m2U0.b(o5Var));
        this.e.a((short) m2U0.D());
        this.e.a((short) m2U0.s().length);
        this.e.f(I.b(this.f.b, m2U0.F()));
        int iC = this.e.c();
        this.e.d(4);
        this.e.a(this.a, m2U0, o5Var, this.c, this.g);
        int iC2 = this.e.c() - iC;
        int i2 = iC2 - 4;
        this.e.d(-iC2);
        this.e.f(i2 / 2);
        this.e.d(i2);
        if (m2U0.s().length > 0) {
            int iA2 = this.e.a(4);
            this.e.d(m2U0.s().length * 8);
            int iC3 = this.e.c();
            C0586x c0586x = this.e;
            int length = m2U0.z().length;
            c0586x.getClass();
            Q1.b(c0586x, length);
            short[] sArr = new short[m2U0.z().length];
            C0772w0.b[] bVarArrZ = m2U0.z();
            int length2 = bVarArrZ.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length2) {
                C0772w0.b bVar = bVarArrZ[i3];
                int i5 = i4 + 1;
                sArr[i4] = (short) (this.e.c() - iC3);
                boolean z = bVar.b != i;
                C0586x c0586x2 = this.e;
                C0772w0.b.a[] aVarArr = bVar.a;
                int length3 = z ? -aVarArr.length : aVarArr.length;
                c0586x2.getClass();
                Q1.a(c0586x2, length3);
                C0772w0.b.a[] aVarArr2 = bVar.a;
                int length4 = aVarArr2.length;
                int i6 = 0;
                while (i6 < length4) {
                    C0772w0.b.a aVar = aVarArr2[i6];
                    C0586x c0586x3 = this.e;
                    int i7 = iC3;
                    C0772w0.b[] bVarArr = bVarArrZ;
                    int iB = this.c.b(aVar.a(this.b));
                    c0586x3.getClass();
                    Q1.b(c0586x3, iB);
                    C0586x c0586x4 = this.e;
                    int i8 = aVar.b;
                    c0586x4.getClass();
                    Q1.b(c0586x4, i8);
                    this.g.a(aVar.a(this.b));
                    i6++;
                    iC3 = i7;
                    bVarArrZ = bVarArr;
                }
                int i9 = iC3;
                C0772w0.b[] bVarArr2 = bVarArrZ;
                if (z) {
                    C0586x c0586x5 = this.e;
                    int i10 = bVar.b;
                    c0586x5.getClass();
                    Q1.b(c0586x5, i10);
                }
                i3++;
                i4 = i5;
                iC3 = i9;
                bVarArrZ = bVarArr2;
                i = -1;
            }
            int iC4 = this.e.c();
            this.e.e(iA2);
            for (C0772w0.a aVar2 : m2U0.s()) {
                this.e.f(aVar2.b);
                this.e.a((short) aVar2.c);
                this.e.a(sArr[aVar2.d]);
            }
            this.e.e(iC4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0028, code lost:
    
        if (r0.c.isEmpty() == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(com.android.tools.r8.graph.C0800x2 r6) {
        /*
            r5 = this;
            boolean r0 = com.android.tools.r8.dex.K.j
            if (r0 != 0) goto L11
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto Lb
            goto L11
        Lb:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            r6.<init>()
            throw r6
        L11:
            com.android.tools.r8.dex.I r0 = r5.f
            com.android.tools.r8.dex.x r1 = r5.e
            r2 = 4
            int r1 = r1.a(r2)
            boolean r2 = com.android.tools.r8.dex.I.n
            if (r2 != 0) goto L34
            if (r1 == 0) goto L2b
            com.android.tools.r8.internal.DI r2 = com.android.tools.r8.dex.I.m334$$Nest$fgetc(r0)
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L2e
            goto L34
        L2b:
            r0.getClass()
        L2e:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            r6.<init>()
            throw r6
        L34:
            com.android.tools.r8.internal.DI r0 = com.android.tools.r8.dex.I.m334$$Nest$fgetc(r0)
            com.android.tools.r8.dex.I.m344$$Nest$sma(r6, r1, r0)
            com.android.tools.r8.graph.v2[] r6 = r6.a
            com.android.tools.r8.dex.x r0 = r5.e
            int r1 = r6.length
            r0.f(r1)
            int r0 = r6.length
            r1 = 0
        L45:
            if (r1 >= r0) goto L58
            r2 = r6[r1]
            com.android.tools.r8.dex.x r3 = r5.e
            com.android.tools.r8.graph.e5 r4 = r5.c
            int r2 = r4.b(r2)
            short r2 = (short) r2
            r3.a(r2)
            int r1 = r1 + 1
            goto L45
        L58:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.dex.K.a(com.android.tools.r8.graph.x2):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0759u2 c0759u2) {
        I i = this.f;
        int iA = i.d.a(this.e.c(), c0759u2);
        if (!I.n && iA > -1) {
            throw new AssertionError();
        }
        C0586x c0586x = this.e;
        int i2 = c0759u2.d;
        c0586x.getClass();
        Q1.b(c0586x, i2);
        this.e.a(c0759u2.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0633d0 c0633d0) {
        I.a(c0633d0, this.e.c(), this.f.e);
        this.e.a((byte) c0633d0.a);
        a(c0633d0.b, this.e, this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0658g0 c0658g0) {
        I i = this.f;
        int iA = this.e.a(4);
        if (!I.n && !i.m.l() && c0658g0.isEmpty()) {
            throw new AssertionError();
        }
        I.a(c0658g0, iA, i.f);
        ArrayList arrayList = new ArrayList(Arrays.asList(c0658g0.c));
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda26
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.a((C0633d0) obj, (C0633d0) obj2);
            }
        });
        this.e.f(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.e.f(I.b(this.f.e, (C0633d0) it.next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(C0633d0 c0633d0, C0633d0 c0633d1) {
        C0767v2 c0767v2 = c0633d0.b.a;
        C0767v2 c0767v3 = c0633d1.b.a;
        com.android.tools.r8.utils.structural.a aVarD = this.c.d();
        c0767v2.getClass();
        return aVarD.a(c0767v2, c0767v3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0663g5 c0663g5) {
        if (!j && c0663g5.isEmpty()) {
            throw new AssertionError();
        }
        I i = this.f;
        int iA = this.e.a(4);
        if (!I.n) {
            i.getClass();
            if (iA == 0 || c0663g5.isEmpty()) {
                throw new AssertionError();
            }
        }
        I.a(c0663g5, iA, i.g);
        this.e.f(c0663g5.s0());
        for (int i2 = 0; i2 < c0663g5.size(); i2++) {
            if (!c0663g5.k(i2)) {
                C0586x c0586x = this.e;
                I i3 = this.f;
                C0658g0 c0658g0J = c0663g5.j(i2);
                c0586x.f((i3.m.l() || !c0658g0J.isEmpty()) ? I.b(i3.f, c0658g0J) : 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0640e0 c0640e0) {
        I.a(c0640e0, this.e.a(4), this.f.h);
        C0586x c0586x = this.e;
        I i = this.f;
        C0658g0 c0658g0S0 = c0640e0.s0();
        c0586x.f((i.m.l() || !c0658g0S0.isEmpty()) ? I.b(i.f, c0658g0S0) : 0);
        ArrayList<U0> arrayListB = c0640e0.b(this.c.d());
        ArrayList<U0> arrayListC = c0640e0.c(this.c.d());
        ArrayList<U0> arrayListA = c0640e0.a(this.c.d());
        this.e.f(arrayListA.size());
        this.e.f(arrayListB.size());
        this.e.f(arrayListC.size());
        ToIntFunction toIntFunction = new ToIntFunction() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return this.f$0.a((T0) obj);
            }
        };
        for (U0 u0 : arrayListA) {
            this.e.f(u0.getReference().a(this.c));
            this.e.f(toIntFunction.applyAsInt(u0));
        }
        ToIntFunction toIntFunction2 = new ToIntFunction() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda11
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return this.f$0.a((W0) obj);
            }
        };
        for (U0 u1 : arrayListB) {
            this.e.f(u1.getReference().a(this.c));
            this.e.f(toIntFunction2.applyAsInt(u1));
        }
        ToIntFunction toIntFunction3 = new ToIntFunction() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda22
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return this.f$0.b((W0) obj);
            }
        };
        for (U0 u2 : arrayListC) {
            this.e.f(u2.getReference().a(this.c));
            this.e.f(toIntFunction3.applyAsInt(u2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(T0 t0) {
        I i = this.f;
        C0658g0 c0658g0S0 = t0.s0();
        if (i.m.l() || !c0658g0S0.isEmpty()) {
            return I.b(i.f, c0658g0S0);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(W0 w0) {
        I i = this.f;
        C0658g0 c0658g0S0 = w0.s0();
        if (i.m.l() || !c0658g0S0.isEmpty()) {
            return I.b(i.f, c0658g0S0);
        }
        return 0;
    }

    private void a(List list) {
        ArrayList<T0> arrayList = new ArrayList(list);
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda23
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.a((T0) obj, (T0) obj2);
            }
        });
        int i = 0;
        for (T0 t0 : arrayList) {
            boolean z = j;
            if (!z && !t0.b(this.a.a())) {
                throw new AssertionError();
            }
            int iA = this.c.a(t0.getReference());
            if (!z && iA - i < 0) {
                throw new AssertionError();
            }
            C0586x c0586x = this.e;
            c0586x.getClass();
            Q1.b(c0586x, iA - i);
            C0586x c0586x2 = this.e;
            int iQ = t0.f.Q();
            c0586x2.getClass();
            Q1.b(c0586x2, iQ);
            this.g.a(t0.getReference());
            i = iA;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(T0 t0, T0 t1) {
        Y0 reference = t0.getReference();
        Y0 reference2 = t1.getReference();
        com.android.tools.r8.utils.structural.a aVarD = this.c.d();
        reference.getClass();
        return aVarD.a(reference, reference2);
    }

    private void a(Iterable iterable) {
        ArrayList<W0> arrayListC = I1.c(iterable);
        arrayListC.sort(new Comparator() { // from class: com.android.tools.r8.dex.K$$ExternalSyntheticLambda27
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.a((W0) obj, (W0) obj2);
            }
        });
        int i = 0;
        for (W0 w0 : arrayListC) {
            int iA = this.c.a(w0.getReference());
            boolean z = j;
            if (!z && iA - i < 0) {
                throw new AssertionError();
            }
            C0586x c0586x = this.e;
            c0586x.getClass();
            Q1.b(c0586x, iA - i);
            C0586x c0586x2 = this.e;
            int iV = w0.f.V();
            c0586x2.getClass();
            Q1.b(c0586x2, iV);
            M2 m2C1 = w0.c1();
            this.g.a(w0.getReference());
            if (m2C1 == null) {
                if (!z && !w0.X1()) {
                    throw new AssertionError();
                }
                C0586x c0586x3 = this.e;
                c0586x3.getClass();
                Q1.b(c0586x3, 0);
            } else {
                C0586x c0586x4 = this.e;
                int iA2 = this.f.a.a(w0);
                if (!I.n && (iA2 == -1 || iA2 == -2)) {
                    throw new AssertionError();
                }
                c0586x4.getClass();
                Q1.b(c0586x4, iA2);
                w0.Z1();
            }
            i = iA;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(W0 w0, W0 w1) {
        C0684j2 reference = w0.getReference();
        C0684j2 reference2 = w1.getReference();
        com.android.tools.r8.utils.structural.a aVarD = this.c.d();
        reference.getClass();
        return aVarD.a(reference, reference2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(S0 s0) {
        I.a(s0, this.e.c(), this.f.j);
        C0586x c0586x = this.e;
        int length = s0.a.length;
        c0586x.getClass();
        Q1.b(c0586x, length);
        for (B2 b2 : s0.a) {
            b2.a(this.e, this.c);
        }
    }

    public static void a(G g, C0586x c0586x) {
        Adler32 adler32 = new Adler32();
        byte[] bArrA = c0586x.a();
        int i = g.a;
        adler32.update(bArrA, i + 12, (g.w - i) - 12);
        c0586x.e(g.a + 8);
        c0586x.f((int) adler32.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0721p0 c0721p0) {
        if (this.d.O()) {
            if (!j && this.e.c() % 4 != 0) {
                throw new AssertionError();
            }
            C0586x c0586x = this.e;
            I i = this.f;
            c0586x.f(I.b(i.j, c0721p0.t0()));
            return;
        }
        this.d.e.a(new UnsupportedInvokeCustomDiagnostic(Origin.unknown(), Position.UNKNOWN));
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0699l2 c0699l2) {
        EnumC0691k2 enumC0691k2;
        int iA;
        if (this.d.O()) {
            if (c0699l2.d.ordinal() != 9) {
                enumC0691k2 = c0699l2.d;
            } else {
                enumC0691k2 = EnumC0691k2.i;
            }
            boolean z = j;
            if (!z && this.e.c() % 4 != 0) {
                throw new AssertionError();
            }
            this.e.a(enumC0691k2.a());
            this.e.a((short) 0);
            if (c0699l2.d.h()) {
                iA = this.c.a(c0699l2.u0());
            } else {
                if (!z && !c0699l2.d.b()) {
                    throw new AssertionError();
                }
                iA = this.c.a(c0699l2.t0());
            }
            if (!z && (65535 & iA) != iA) {
                throw new AssertionError();
            }
            this.e.a((short) iA);
            this.e.a((short) 0);
            return;
        }
        this.d.e.a(new UnsupportedInvokeCustomDiagnostic(Origin.unknown(), Position.UNKNOWN));
        throw null;
    }
}

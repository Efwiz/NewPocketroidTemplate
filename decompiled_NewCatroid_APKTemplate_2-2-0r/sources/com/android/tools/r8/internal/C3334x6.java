package com.android.tools.r8.internal;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda1;
import com.android.tools.r8.SourceFileEnvironment;
import com.android.tools.r8.SourceFileProvider;
import com.android.tools.r8.dex.C0548k;
import com.android.tools.r8.dex.C0562k$$ExternalSyntheticLambda20;
import com.android.tools.r8.errors.CodeSizeOverflowDiagnostic;
import com.android.tools.r8.errors.ConstantPoolOverflowDiagnostic;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0670h5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0787w2;
import com.android.tools.r8.graph.j2$$ExternalSyntheticLambda2;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.AbstractC3897g0;
import com.android.tools.r8.utils.AbstractC3972u2;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.C3963s1;
import com.android.tools.r8.utils.C3988x2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3334x6 {
    private static final Z8 f = Z8.f;
    static final /* synthetic */ boolean g = true;
    private final AbstractC0681j0 a;
    private final C0702m b;
    private final com.android.tools.r8.utils.A1 c;
    private final Optional d;
    private final Predicate e;

    public C3334x6(C0702m c0702m, com.android.tools.r8.dex.U u) {
        this.a = c0702m.f().b();
        this.b = c0702m;
        this.c = c0702m.Y();
        this.d = Optional.ofNullable(u);
        C0664h c0664hF = c0702m.f();
        Objects.requireNonNull(c0664hF);
        this.e = C3988x2.a(new C0562k$$ExternalSyntheticLambda20(c0664hF));
    }

    private void a(final AbstractC1899eF abstractC1899eF, C0663g5 c0663g5) {
        abstractC1899eF.a(c0663g5.size(), true);
        abstractC1899eF.a(c0663g5.size(), false);
        for (final int i = 0; i < c0663g5.size(); i++) {
            a(new InterfaceC3184v6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC3184v6
                public final AbstractC2509m2 a(String str, boolean z) {
                    return abstractC1899eF.a(i, str, z);
                }
            }, new InterfaceC3259w6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda10
                @Override // com.android.tools.r8.internal.InterfaceC3259w6
                public final AbstractC2509m2 a(int i2, C00 c00, String str, boolean z) {
                    return C3334x6.a(i2, c00, str, z);
                }
            }, c0663g5.j(i).c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0759u2 b(C0684j2 c0684j2) {
        return this.b.z().c(c0684j2.I0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void b(Z8 z8, C2363kB c2363kB, C1549Za c1549Za, AbstractC2178hm abstractC2178hm, com.android.tools.r8.graph.o5 o5Var) {
        String string;
        String[] strArr;
        AbstractC2509m2 abstractC2509m2A;
        AbstractC3649o0 abstractC3649o0A = this.b.b(o5Var.a()) ? AbstractC3649o0.a() : this.b.z();
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        int iT = w0E.getAccessFlags().T();
        if (w0E.g) {
            iT |= 131072;
        }
        int i = iT;
        String string2 = abstractC3649o0A.a(o5Var.getReference()).toString();
        String strA = w0E.a(abstractC3649o0A);
        C0706m3.g gVarE1 = o5Var.e().e1();
        Predicate predicate = this.e;
        if (gVarE1.a()) {
            string = null;
        } else {
            com.android.tools.r8.graph.A3 a3 = new com.android.tools.r8.graph.A3(abstractC3649o0A, predicate);
            a3.a(gVarE1);
            string = a3.toString();
        }
        com.android.tools.r8.graph.B2 b2A = a(w0E.s0(), this.a.e.h5);
        if (b2A == null) {
            strArr = null;
        } else {
            com.android.tools.r8.graph.B2[] b2ArrJ1 = b2A.v0().j1();
            String[] strArr2 = new String[b2ArrJ1.length];
            for (int i2 = 0; i2 < b2ArrJ1.length; i2++) {
                strArr2[i2] = this.b.z().d((C0767v2) b2ArrJ1[i2].L0().b);
            }
            strArr = strArr2;
        }
        final AbstractC1899eF abstractC1899eFA = c1549Za.a(i, string2, strA, string, strArr);
        if (abstractC2178hm.containsKey(w0E.K0()) && (abstractC2509m2A = abstractC1899eFA.a()) != null) {
            a(abstractC2509m2A, (String) null, (com.android.tools.r8.graph.B2) abstractC2178hm.get(w0E.K0()));
            abstractC2509m2A.a();
        }
        for (C0633d0 c0633d0 : w0E.s0().c) {
            if (c0633d0.b.a == this.b.a().V4) {
                boolean z = g;
                if (!z && c0633d0.a != 2) {
                    throw new AssertionError();
                }
                if (!z && c0633d0.b.b.length != 2) {
                    throw new AssertionError();
                }
                if (!z && !c0633d0.b.b[0].a.toString().equals("names")) {
                    throw new AssertionError();
                }
                if (!z && !c0633d0.b.b[1].a.toString().equals("accessFlags")) {
                    throw new AssertionError();
                }
                com.android.tools.r8.graph.B2.a aVarV0 = c0633d0.b.b[0].b.v0();
                com.android.tools.r8.graph.B2.a aVarV1 = c0633d0.b.b[1].b.v0();
                if (!z && (aVarV0 == null || aVarV1 == null)) {
                    throw new AssertionError();
                }
                if (!z && aVarV0.j1().length != aVarV1.j1().length) {
                    throw new AssertionError();
                }
                for (int i3 = 0; i3 < aVarV0.j1().length; i3++) {
                    com.android.tools.r8.graph.B2.j jVarK0 = aVarV0.j1()[i3].K0();
                    abstractC1899eFA.b(aVarV1.j1()[i3].D0().b, jVarK0 != null ? ((C0759u2) jVarK0.b).toString() : null);
                }
            }
        }
        a(new InterfaceC3184v6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC3184v6
            public final AbstractC2509m2 a(String str, boolean z2) {
                return abstractC1899eFA.a(str, z2);
            }
        }, new InterfaceC3259w6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC3259w6
            public final AbstractC2509m2 a(int i4, C00 c00, String str, boolean z2) {
                return abstractC1899eFA.c(i4, c00, str, z2);
            }
        }, w0E.s0().c);
        a(abstractC1899eFA, w0E.h);
        if (w0E.X1()) {
            return;
        }
        a(o5Var, z8, abstractC3649o0A, c2363kB, abstractC1899eFA);
    }

    private void b(ClassFileConsumer classFileConsumer, C3945p c3945p) {
        com.android.tools.r8.naming.J0 j0A;
        if (this.c.x1 == null) {
            j0A = null;
        } else {
            if (!g && !this.d.isPresent()) {
                throw new AssertionError();
            }
            j0A = AbstractC3417yB.a(c3945p, this.b, this.a.c, AbstractC3972u2.a(), C1525Yd.a(this.c));
            ((com.android.tools.r8.dex.U) this.d.get()).a(j0A.b());
        }
        Optional map = this.d.filter(new Predicate() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.dex.U) obj);
            }
        }).map(new ExtractMarker$$ExternalSyntheticLambda1());
        SourceFileEnvironment sourceFileEnvironmentA = this.c.K1 != null ? C0548k.a(j0A) : null;
        C2363kB c2363kB = new C2363kB(this.b);
        Collection<C0729q2> collectionD = this.a.d();
        ArrayList arrayList = new ArrayList();
        com.android.tools.r8.utils.A1 a1 = this.c;
        if (a1.t0 && a1.K0()) {
            ArrayList arrayList2 = new ArrayList(collectionD.size());
            for (C0729q2 c0729q2 : collectionD) {
                com.android.tools.r8.synthesis.D D = this.b.D();
                D.getClass();
                if (D.g(c0729q2.getType())) {
                    arrayList.add(c0729q2);
                } else {
                    arrayList2.add(c0729q2);
                }
            }
            collectionD = arrayList2;
        }
        Iterator<C0729q2> it = collectionD.iterator();
        while (it.hasNext()) {
            b(it.next(), classFileConsumer, c2363kB, map, sourceFileEnvironmentA);
        }
        if (!arrayList.isEmpty()) {
            C3963s1 c3963s1 = new C3963s1(this.c.x0(), this.b);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                b((C0729q2) it2.next(), c3963s1, c2363kB, map, sourceFileEnvironmentA);
            }
            c3963s1.a(this.b);
        }
        C0548k.a(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return this.b.z().c(c0767v2).a(this.b.z().c(c0767v3));
    }

    public final void a(ClassFileConsumer classFileConsumer) {
        if (!g && this.c.x1 != null) {
            throw new AssertionError();
        }
        a(classFileConsumer, (C3945p) null);
    }

    public final void a(ClassFileConsumer classFileConsumer, C3945p c3945p) {
        this.a.c.a("CfApplicationWriter.write");
        try {
            b(classFileConsumer, c3945p);
        } finally {
            this.a.c.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.dex.U u) {
        if (u.q()) {
            return false;
        }
        if (!g && !u.a().equals("CF".toLowerCase()) && !u.a().equals(com.android.tools.r8.dex.U.a.DEX.name().toLowerCase())) {
            throw new AssertionError();
        }
        if (this.c.i0().c) {
            return (u.a().equals("CF".toLowerCase()) && u.n()) ? false : true;
        }
        return true;
    }

    private void a(C0729q2 c0729q2, final ClassFileConsumer classFileConsumer, final C2363kB c2363kB, Optional optional, SourceFileEnvironment sourceFileEnvironment) {
        String string;
        Z8 z8U1;
        int iS;
        String string2;
        AbstractC2178hm abstractC2178hmA;
        final C1549Za c1549Za = new C1549Za(0);
        if (optional.isPresent()) {
            int iA = c1549Za.a(optional.get());
            if (!g && iA != 2) {
                throw new AssertionError();
            }
        }
        SourceFileProvider sourceFileProvider = this.c.K1;
        if (sourceFileProvider == null) {
            C0759u2 c0759u2 = c0729q2.h;
            string = c0759u2 != null ? c0759u2.toString() : null;
        } else {
            string = sourceFileProvider.get(sourceFileEnvironment);
        }
        com.android.tools.r8.graph.B2 b2A = a(c0729q2.s0(), this.a.e.g5);
        c1549Za.a(string, b2A == null ? null : ((C0759u2) b2A.K0().j1()).toString());
        if (c0729q2.Z1()) {
            z8U1 = c0729q2.U1();
        } else {
            z8U1 = f;
        }
        Iterator<com.android.tools.r8.graph.W0> it = c0729q2.V0().iterator();
        while (it.hasNext()) {
            z8U1 = (Z8) com.android.tools.r8.utils.structural.s.a(z8U1, a(it.next()));
        }
        Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.P1().iterator();
        while (it2.hasNext()) {
            z8U1 = (Z8) com.android.tools.r8.utils.structural.s.a(z8U1, a(it2.next()));
        }
        if (z8U1.a(Z8.h)) {
            c0729q2.e.g0();
        } else if (!c0729q2.e.W()) {
            c0729q2.e.c0();
        }
        if (!c0729q2.getType().D0().b(this.b.a().c("/package-info;")) && !this.c.n1.l0) {
            iS = c0729q2.e.P();
        } else {
            iS = c0729q2.e.s();
        }
        if (c0729q2.e2()) {
            iS |= 131072;
        }
        int i = iS;
        final String string3 = this.b.z().c(c0729q2.d).toString();
        String strD = this.b.z().d(c0729q2.d);
        C0706m3.b bVarA1 = c0729q2.a1();
        AbstractC3649o0 abstractC3649o0Z = this.b.z();
        Predicate predicate = this.e;
        if (bVarA1.a()) {
            string2 = null;
        } else {
            com.android.tools.r8.graph.A3 a3 = new com.android.tools.r8.graph.A3(abstractC3649o0Z, predicate);
            a3.a(bVarA1);
            string2 = a3.toString();
        }
        String strD2 = c0729q2.f != null ? this.b.z().d(c0729q2.f) : null;
        String[] strArr = new String[c0729q2.g.a.length];
        for (int i2 = 0; i2 < c0729q2.g.a.length; i2++) {
            strArr[i2] = this.b.z().d(c0729q2.g.a[i2]);
        }
        if (!g) {
            com.android.tools.r8.synthesis.L.a(strD);
        }
        c1549Za.a(z8U1.d(), i, strD, string2, strD2, strArr);
        this.b.D().a(c1549Za, c0729q2, this.b);
        a(new InterfaceC3184v6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.internal.InterfaceC3184v6
            public final AbstractC2509m2 a(String str, boolean z) {
                return c1549Za.a(str, z);
            }
        }, new InterfaceC3259w6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.internal.InterfaceC3259w6
            public final AbstractC2509m2 a(int i3, C00 c00, String str, boolean z) {
                return c1549Za.a(i3, c00, str, z);
            }
        }, c0729q2.s0().c);
        com.android.tools.r8.graph.B2 b2A2 = a(c0729q2.s0(), this.a.e.Q4);
        if (b2A2 == null) {
            abstractC2178hmA = AbstractC2178hm.s();
        } else {
            com.android.tools.r8.graph.R0 r0 = b2A2.u0().b;
            C1946em c1946emE = AbstractC2178hm.e();
            for (C0649f0 c0649f0 : r0.b) {
                c1946emE.a(c0649f0.a, c0649f0.b);
            }
            abstractC2178hmA = c1946emE.a();
        }
        final AbstractC2178hm abstractC2178hm = abstractC2178hmA;
        if (c0729q2.b1() != null) {
            c0729q2.b1().a(c1549Za, this.b.z());
        }
        if (c0729q2.h1() != null) {
            c0729q2.h1().a(c1549Za, this.b.z());
        }
        Iterator<com.android.tools.r8.graph.X4> it3 = c0729q2.i1().iterator();
        while (it3.hasNext()) {
            it3.next().a(c1549Za, this.b.z());
            if (!g && c0729q2.h1() != null) {
                throw new AssertionError("A nest host cannot also be a nest member.");
            }
        }
        Iterator<C0670h5> it4 = c0729q2.j1().iterator();
        while (it4.hasNext()) {
            it4.next().a(c1549Za, this.b.z());
        }
        if (c0729q2.K1()) {
            Iterator<com.android.tools.r8.graph.v5> it5 = c0729q2.k1().iterator();
            while (it5.hasNext()) {
                it5.next().a(c1549Za, this.b.z(), this.e, new BiConsumer() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda15
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        this.f$0.a((AbstractC2509m2) obj, (com.android.tools.r8.graph.R0) obj2);
                    }
                });
            }
        }
        Iterator<com.android.tools.r8.graph.J3> it6 = c0729q2.e1().iterator();
        while (it6.hasNext()) {
            it6.next().a(c1549Za, this.b.z(), this.c);
        }
        Iterator<com.android.tools.r8.graph.T0> it7 = c0729q2.N1().iterator();
        while (it7.hasNext()) {
            a(it7.next(), c1549Za);
        }
        Iterator<com.android.tools.r8.graph.T0> it8 = c0729q2.w1().iterator();
        while (it8.hasNext()) {
            a(it8.next(), c1549Za);
        }
        if (this.c.i0().a) {
            ArrayList arrayList = new ArrayList();
            c0729q2.l(new E7$$ExternalSyntheticLambda0(arrayList));
            arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda16
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.a((com.android.tools.r8.graph.o5) obj, (com.android.tools.r8.graph.o5) obj2);
                }
            });
            final Z8 z8 = z8U1;
            arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(z8, c2363kB, c1549Za, abstractC2178hm, (com.android.tools.r8.graph.o5) obj);
                }
            });
        } else {
            final Z8 z9 = z8U1;
            c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(z9, c2363kB, c1549Za, abstractC2178hm, (com.android.tools.r8.graph.o5) obj);
                }
            });
        }
        final byte[] bArrB = c1549Za.b();
        com.android.tools.r8.utils.O2 o2 = this.c.e;
        new Consumer() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3334x6.a(classFileConsumer, bArrB, string3, (DiagnosticsHandler) obj);
            }
        }.accept(o2);
        o2.a();
    }

    private void b(C0729q2 c0729q2, ClassFileConsumer classFileConsumer, C2363kB c2363kB, Optional optional, SourceFileEnvironment sourceFileEnvironment) {
        if (!g) {
            C0767v2 type = c0729q2.getType();
            int i = com.android.tools.r8.synthesis.L.W;
            com.android.tools.r8.synthesis.L.a(type.b1());
        }
        try {
            a(c0729q2, classFileConsumer, c2363kB, optional, sourceFileEnvironment);
        } catch (C1149La e) {
            this.b.Y().e.a(new ConstantPoolOverflowDiagnostic(c0729q2.c, Reference.classFromBinaryName(e.a()), e.b()));
            throw null;
        } catch (C1828dF e2) {
            this.b.Y().e.a(new CodeSizeOverflowDiagnostic(c0729q2.c, Reference.methodFromDescriptor(Reference.classFromBinaryName(e2.a()).getDescriptor(), e2.d(), e2.c()), e2.b()));
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ClassFileConsumer classFileConsumer, byte[] bArr, String str, DiagnosticsHandler diagnosticsHandler) {
        classFileConsumer.accept(ByteDataView.of(bArr), str, diagnosticsHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        if (g || o5Var.a().equals(o5Var2.a())) {
            return Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((C0684j2) obj);
                }
            }).thenComparing(new j2$$ExternalSyntheticLambda2()).thenComparing(new Function() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3334x6.a((C0684j2) obj);
                }
            }, AbstractC3897g0.a(new Comparator() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda11
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.a((C0767v2) obj, (C0767v2) obj2);
                }
            })).compare(o5Var.getReference(), o5Var2.getReference());
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2[] a(C0684j2 c0684j2) {
        return c0684j2.H0().f.a;
    }

    private Z8 a(com.android.tools.r8.graph.W0 w0) {
        if (!w0.p1()) {
            boolean z = g;
            if (!z && !this.c.U0() && !this.c.V0()) {
                throw new AssertionError("Expected class file version for " + w0.getReference().r0());
            }
            if (!z && !f.e(this.c.b(com.android.tools.r8.utils.A1.P1))) {
                throw new AssertionError();
            }
            if (this.c.V0()) {
                return this.c.b(com.android.tools.r8.utils.A1.P1);
            }
            return f;
        }
        return w0.Z0();
    }

    private static com.android.tools.r8.graph.B2 a(C0658g0 c0658g0, C0767v2 c0767v2) {
        C0633d0 c0633d0A = c0658g0.a(c0767v2);
        if (c0633d0A == null) {
            return null;
        }
        boolean z = g;
        if (!z && c0633d0A.a != 2) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.R0 r0 = c0633d0A.b;
        if (z || r0.b.length == 1) {
            return r0.b[0].b;
        }
        throw new AssertionError();
    }

    private void a(com.android.tools.r8.graph.T0 t0, C1549Za c1549Za) {
        String string;
        int iP = t0.f.P();
        if (t0.a1()) {
            iP |= 131072;
        }
        int i = iP;
        String string2 = this.b.z().a(t0.getReference()).toString();
        String string3 = this.b.z().c(t0.getReference().h).toString();
        C0706m3.e eVarT0 = t0.T0();
        AbstractC3649o0 abstractC3649o0Z = this.b.z();
        Predicate predicate = this.e;
        if (eVarT0.a()) {
            string = null;
        } else {
            com.android.tools.r8.graph.A3 a3 = new com.android.tools.r8.graph.A3(abstractC3649o0Z, predicate);
            a3.a((C0706m3.i) eVarT0);
            string = a3.toString();
        }
        final AbstractC2252ii abstractC2252iiA = c1549Za.a(i, string2, string3, string, (t0.f.o() && t0.Z0()) ? t0.Y0().s0() : null);
        a(new InterfaceC3184v6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC3184v6
            public final AbstractC2509m2 a(String str, boolean z) {
                return abstractC2252iiA.a(str, z);
            }
        }, new InterfaceC3259w6() { // from class: com.android.tools.r8.internal.x6$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC3259w6
            public final AbstractC2509m2 a(int i2, C00 c00, String str, boolean z) {
                return abstractC2252iiA.a(i2, c00, str, z);
            }
        }, t0.s0().c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        throw new C3405y10("Type annotations are not placed on parameters");
    }

    private void a(InterfaceC3184v6 interfaceC3184v6, InterfaceC3259w6 interfaceC3259w6, C0633d0[] c0633d0Arr) {
        AbstractC2509m2 abstractC2509m2A;
        for (C0633d0 c0633d0 : c0633d0Arr) {
            if (c0633d0.a != 2) {
                String string = this.b.z().c(c0633d0.b.a).toString();
                boolean z = c0633d0.a == 1;
                C0787w2 c0787w2S0 = c0633d0.s0();
                if (c0787w2S0 == null) {
                    abstractC2509m2A = interfaceC3184v6.a(string, z);
                } else {
                    abstractC2509m2A = interfaceC3259w6.a(c0787w2S0.x0(), c0787w2S0.w0(), string, z);
                }
                if (abstractC2509m2A != null) {
                    a(abstractC2509m2A, c0633d0.b);
                    abstractC2509m2A.a();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC2509m2 abstractC2509m2, com.android.tools.r8.graph.R0 r0) {
        for (C0649f0 c0649f0 : r0.b) {
            a(abstractC2509m2, c0649f0.a.toString(), c0649f0.b);
        }
    }

    private void a(AbstractC2509m2 abstractC2509m2, String str, com.android.tools.r8.graph.B2 b2) {
        switch (b2.N0().ordinal()) {
            case 7:
                throw new C3405y10("writeAnnotationElement of DexValueMethodType");
            case 8:
                throw new C3405y10("writeAnnotationElement of DexValueMethodHandle");
            case 9:
                abstractC2509m2.a(str, (Object) ((C0759u2) b2.K0().j1()).toString());
                return;
            case 10:
                abstractC2509m2.a(str, CZ.f(this.b.z().c((C0767v2) b2.L0().b).toString()));
                return;
            case 11:
                throw new C3405y10("writeAnnotationElement of DexValueField");
            case 12:
                throw new C3405y10("writeAnnotationElement of DexValueMethod");
            case 13:
                com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) b2.A0().j1();
                if (!g && this.b.z().a(y0) != y0.f) {
                    throw new AssertionError();
                }
                abstractC2509m2.a(str, this.b.z().c(y0.getType()).toString(), y0.f.toString());
                return;
            case 14:
                com.android.tools.r8.graph.B2[] b2ArrJ1 = b2.v0().j1();
                AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a(str);
                if (abstractC2509m2A != null) {
                    for (com.android.tools.r8.graph.B2 b3 : b2ArrJ1) {
                        a(abstractC2509m2A, (String) null, b3);
                    }
                    abstractC2509m2A.a();
                    return;
                }
                return;
            case 15:
                com.android.tools.r8.graph.A2 a2U0 = b2.u0();
                AbstractC2509m2 abstractC2509m2A2 = abstractC2509m2.a(str, this.b.z().c(a2U0.b.a).toString());
                if (abstractC2509m2A2 != null) {
                    a(abstractC2509m2A2, a2U0.b);
                    abstractC2509m2A2.a();
                    return;
                }
                return;
            default:
                abstractC2509m2.a(str, b2.M0());
                return;
        }
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, Z8 z8, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        boolean z = g;
        if (!z && !wA1.A0()) {
            throw new AssertionError();
        }
        if (!z && wA1.w0() <= 0) {
            throw new AssertionError();
        }
        if (wA1.A0()) {
            wA1.t0().a(o5Var, z8, this.b, abstractC3649o0, c2363kB, abstractC1899eF);
        } else {
            throw new C3405y10("The CfApplicationWriter cannot write non cf writable code " + wA1.getClass().getCanonicalName() + " for method " + o5Var.getReference().r0());
        }
    }
}

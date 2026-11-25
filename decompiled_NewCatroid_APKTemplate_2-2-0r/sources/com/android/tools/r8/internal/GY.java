package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class GY {
    static final /* synthetic */ boolean d = true;
    private final com.android.tools.r8.utils.A1 a;
    private final com.android.tools.r8.graph.N2 b;
    private final CY c = EY.a();

    public GY(com.android.tools.r8.utils.A1 a1, Path path) throws IOException {
        this.a = a1;
        this.b = b(path);
    }

    private void a() {
        this.c.a(new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.a((AbstractC0727q0) obj, (Collection) obj2, (Collection) obj3);
            }
        });
    }

    private Set c() {
        Set setC = OU.c();
        C0722p1 c0722p1J0 = this.a.j0();
        setC.add(c0722p1J0.a(c0722p1J0.P2, c0722p1J0.b(c0722p1J0.c(c0722p1J0.c("Ljava/util/stream/Stream;")), new C0767v2[0]), c0722p1J0.c("parallelStream")));
        C0767v2 c0767v2C = c0722p1J0.c(c0722p1J0.c("Ljava/util/stream/BaseStream;"));
        String[] strArr = {"Base", "Double", "Int", "Long"};
        for (int i = 0; i < 4; i++) {
            C0767v2 c0767v2C2 = c0722p1J0.c(c0722p1J0.c("Ljava/util/stream/" + strArr[i] + "Stream;"));
            setC.add(c0722p1J0.a(c0767v2C2, c0722p1J0.b(c0767v2C2, new C0767v2[0]), c0722p1J0.c("parallel")));
            setC.add(c0722p1J0.a(c0767v2C2, c0722p1J0.b(c0767v2C, new C0767v2[0]), c0722p1J0.c("parallel")));
        }
        return setC;
    }

    public EY b(Collection<Path> collection, Path path) throws IOException {
        a(collection, path);
        b();
        for (C0684j2 c0684j2 : c()) {
            CY cy = this.c;
            EY.c cVarF = EY.c.f();
            if (((FY) cy.a.get(c0684j2.B0())) != null) {
                cy.a(c0684j2, cVarF);
            }
        }
        a(path);
        a();
        return this.c.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, Collection collection, Collection collection2) {
        DY dyA = this.c.a(abstractC0727q0.d);
        if (dyA == null || !dyA.d()) {
            AbstractC0727q0 abstractC0727q0C = this.b.c(abstractC0727q0.d);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            boolean zA = a(abstractC0727q0C.W0(), collection, arrayList) & a(abstractC0727q0C.M1(), collection2, arrayList2) & this.c.a(abstractC0727q0).isEmpty();
            Iterator it = this.c.b(abstractC0727q0).values().iterator();
            while (it.hasNext()) {
                zA &= ((EY.c) it.next()).g;
            }
            this.c.a(abstractC0727q0.d, new DY(arrayList, arrayList2, zA));
        }
    }

    private void b() {
        this.c.b(new BiConsumer() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((AbstractC0727q0) obj, (com.android.tools.r8.graph.W0) obj2);
            }
        });
    }

    private static boolean a(Iterable iterable, Collection collection, ArrayList arrayList) {
        Iterator it = iterable.iterator();
        boolean z = true;
        while (it.hasNext()) {
            final com.android.tools.r8.graph.U0 u0 = (com.android.tools.r8.graph.U0) it.next();
            if (u0.getAccessFlags().m() || u0.getAccessFlags().l()) {
                if (collection.stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return GY.a(u0, (com.android.tools.r8.graph.U0) obj);
                    }
                })) {
                    arrayList.add(u0.getReference());
                    z = false;
                }
            }
        }
        return z;
    }

    private com.android.tools.r8.graph.N2 b(Path path) throws IOException {
        C0408c c0408c = new C0408c(C3945p.b().a(path).a(), this.a, com.android.tools.r8.utils.y3.b());
        ExecutorService executorServiceA = C3879c3.a(this.a);
        if (!d && this.a.d0) {
            throw new AssertionError();
        }
        this.a.d0 = true;
        C0612b4 c0612b4A = c0408c.a(executorServiceA);
        this.a.d0 = false;
        return c0612b4A.i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.U0 u0, com.android.tools.r8.graph.U0 u1) {
        return u1.getReference() == u0.getReference();
    }

    private void a(Path path) throws Throwable {
        for (int iD = EnumC3891f.J.d(); iD <= AbstractC2817q.i.d(); iD++) {
            EnumC3891f enumC3891fC = EnumC3891f.c(iD);
            com.android.tools.r8.d0.a aVarA = com.android.tools.r8.d0.a(path);
            com.android.tools.r8.utils.A1 a1 = this.a;
            final C2215iD c2215iDA = C1233Oe.a(aVarA, a1.a, a1.e, false, enumC3891fC.d()).a(this.b, com.android.tools.r8.utils.y3.b());
            this.a.c(enumC3891fC);
            this.a.z1();
            this.a.a(c2215iDA);
            final C0702m c0702mA = C0702m.a(C0664h.a(this.b, com.android.tools.r8.synthesis.y.d()), this.a.J0(), com.android.tools.r8.utils.y3.b());
            final C0680j c0680jG = c0702mA.g();
            final List<C0684j2> listA = C2281j4.a(this.b, this.a);
            final int i = iD;
            this.c.b(new BiConsumer() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(c2215iDA, listA, c0680jG, c0702mA, i, (AbstractC0727q0) obj, (com.android.tools.r8.graph.W0) obj2);
                }
            });
            final int i2 = iD;
            this.c.a(new BiConsumer() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(c2215iDA, c0680jG, c0702mA, i2, (AbstractC0727q0) obj, (com.android.tools.r8.graph.T0) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2215iD c2215iD, List list, C0680j c0680j, C0702m c0702m, int i, AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.W0 w0) {
        C0684j2 reference = w0.getReference();
        if (!c2215iD.b(reference) && !list.contains(reference) && !c2215iD.k().containsKey(reference)) {
            if (c2215iD.m().containsKey(reference.B0()) && w0.E0()) {
                return;
            }
            com.android.tools.r8.graph.I4 i4A = c0680j.a(reference, c0680j.a(reference.B0()).isInterface());
            if (i4A.x()) {
                com.android.tools.r8.androidapi.h hVarA = c0702m.d().a((AbstractC0642e2) i4A.r().getReference(), com.android.tools.r8.androidapi.h.f());
                if (hVarA.y()) {
                    if (i < hVarA.b0().a().d()) {
                        this.c.a(reference, EY.c.a(i));
                        return;
                    }
                    return;
                }
                throw new RuntimeException("API database does not recognize the method " + w0.getReference().r0());
            }
            if (!d && !i4A.h()) {
                throw new AssertionError();
            }
            this.c.a(reference, EY.c.a(i));
            return;
        }
        if (c2215iD.k().containsKey(reference)) {
            this.c.a(reference, EY.c.d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2215iD c2215iD, C0680j c0680j, C0702m c0702m, int i, AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.T0 t0) {
        if (c2215iD.a((AbstractC0744s2) t0.J0()) || c2215iD.x().containsKey(t0.getReference())) {
            return;
        }
        AbstractC0677i3 abstractC0677i3C = c0680j.c(t0.getReference());
        if (abstractC0677i3C.B()) {
            com.android.tools.r8.androidapi.h hVarA = c0702m.d().a((AbstractC0642e2) abstractC0677i3C.s().getReference(), com.android.tools.r8.androidapi.h.f());
            if (hVarA.y()) {
                if (i < hVarA.b0().a().d()) {
                    this.c.a(t0.getReference(), EY.a.a(i));
                    return;
                }
                return;
            }
            throw new RuntimeException("API database does not recognize the field " + t0.getReference().r0());
        }
        if (!d && !abstractC0677i3C.h()) {
            throw new AssertionError();
        }
        this.c.a(t0.getReference(), EY.a.a(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.W0 w0) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(abstractC0727q0.d);
        if (!d && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        if (abstractC0727q0C.c(w0.getReference()) == null) {
            this.c.a(w0.getReference(), EY.c.e());
        }
    }

    private void a(Collection collection, Path path) throws Throwable {
        EnumC3891f enumC3891f = EnumC3891f.B;
        com.android.tools.r8.d0.a aVarA = com.android.tools.r8.d0.a(path);
        com.android.tools.r8.utils.A1 a1 = this.a;
        C2215iD c2215iDA = C1233Oe.a(aVarA, a1.a, a1.e, false, enumC3891f.d()).a(this.b, com.android.tools.r8.utils.y3.b());
        this.a.c(enumC3891f);
        this.a.z1();
        this.a.a(c2215iDA);
        final com.android.tools.r8.graph.N2 n2I = new C0408c(C3945p.b().i(collection).a(), this.a, com.android.tools.r8.utils.y3.b()).a().i();
        List<C0684j2> listA = C2281j4.a(this.b, this.a);
        for (C0729q2 c0729q2 : n2I.d()) {
            if (c2215iDA.m().containsKey(c0729q2.d)) {
                if (!d && !c0729q2.isInterface()) {
                    throw new AssertionError();
                }
                for (com.android.tools.r8.graph.W0 w0 : c0729q2.M1()) {
                    if (w0.w1() || w0.E0()) {
                        if (!w0.K0().b("lambda$") && !w0.K0().toString().contains("$deserializeLambda$") && !w0.getReference().r0().equals("void java.util.Collection.forEach(java.util.function.Consumer)")) {
                            this.c.a(c0729q2, w0);
                        }
                    }
                }
                a(c0729q2, listA);
                this.c.a(c0729q2.d, DY.a());
            } else if (c0729q2.e.m() || c0729q2.e.l()) {
                if (c2215iDA.a((AbstractC0744s2) c0729q2.d) && this.b.c(c0729q2.d) != null) {
                    for (com.android.tools.r8.graph.W0 w1 : c0729q2.M1()) {
                        if (w1.getAccessFlags().m() || w1.N1()) {
                            this.c.a(c0729q2, w1);
                        }
                    }
                    a(c0729q2, listA);
                    for (com.android.tools.r8.graph.T0 t0 : c0729q2.W0()) {
                        if (t0.getAccessFlags().m() || t0.f.l()) {
                            this.c.a(c0729q2, t0);
                        }
                    }
                }
            }
        }
        c2215iDA.a(new Consumer() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(n2I, (C0684j2) obj);
            }
        });
        c2215iDA.x().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.GY$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(n2I, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.N2 n2, C0684j2 c0684j2) {
        com.android.tools.r8.graph.W0 w0C;
        AbstractC0727q0 abstractC0727q0C = n2.c(c0684j2.B0());
        if (abstractC0727q0C != null && (w0C = abstractC0727q0C.c(c0684j2)) != null) {
            this.c.a(abstractC0727q0C, w0C);
            this.c.a(abstractC0727q0C.d, DY.a());
            return;
        }
        AbstractC0727q0 abstractC0727q0C2 = this.b.c(c0684j2.B0());
        com.android.tools.r8.graph.W0 w0C2 = abstractC0727q0C2.c(c0684j2);
        if (!d && w0C2 == null) {
            throw new AssertionError();
        }
        this.c.a(abstractC0727q0C2, w0C2);
        this.c.a(abstractC0727q0C2.d, DY.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.N2 n2, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        com.android.tools.r8.graph.T0 t0A;
        AbstractC0727q0 abstractC0727q0C = n2.c(y0.B0());
        if (abstractC0727q0C != null && (t0A = abstractC0727q0C.a(y0)) != null) {
            this.c.a(abstractC0727q0C, t0A);
            this.c.a(abstractC0727q0C.d, DY.a());
            return;
        }
        AbstractC0727q0 abstractC0727q0C2 = this.b.c(y0.B0());
        com.android.tools.r8.graph.T0 t0A2 = abstractC0727q0C2.a(y0);
        if (!d && t0A2 == null) {
            throw new AssertionError();
        }
        this.c.a(abstractC0727q0C2, t0A2);
        this.c.a(abstractC0727q0C2.d, DY.a());
    }

    private void a(C0729q2 c0729q2, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0684j2 c0684j2 = (C0684j2) it.next();
            if (c0729q2.d == c0684j2.B0()) {
                com.android.tools.r8.graph.W0 w0C = this.b.c(c0729q2.d).c(c0684j2);
                if (w0C == null) {
                    AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.a(this.a.j0().j2, this.a.j0().k2, this.a.j0().g2);
                    if (!c0684j2.toString().equals("java.util.stream.Stream java.util.stream.Stream.ofNullable(java.lang.Object)") && !abstractC1048HmA.contains(c0684j2.B0())) {
                        throw new Error("Unexpected backport missing from Android " + AbstractC2817q.i + ": " + c0684j2);
                    }
                    w0C = com.android.tools.r8.graph.W0.S0().a(c0684j2).a(C0725p4.b(9, false)).a();
                }
                this.c.a(c0729q2, w0C);
            }
        }
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3625g;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.naming.C3651p0;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.AbstractC3972u2;
import com.android.tools.r8.utils.C3917j2;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3191vD {
    static final /* synthetic */ boolean f = true;
    private final C0729q2 a;
    private final C0767v2 b;
    private final String c;
    private C3634k.a d;
    final /* synthetic */ C3341xD e;

    private C3191vD(C3341xD c3341xD, C0729q2 c0729q2, C0767v2 c0767v2, String str) {
        this.e = c3341xD;
        this.a = c0729q2;
        this.b = c0767v2;
        this.c = str;
        if (c0767v2.r0().equals(str)) {
            return;
        }
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void g(Object obj) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3634k.b bVar, com.android.tools.r8.naming.mappinginformation.e eVar) {
        bVar.a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3191vD.d((com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        }, eVar);
    }

    public final C3191vD a(AbstractC3972u2 abstractC3972u2) {
        C0759u2 c0759u2A = abstractC3972u2.a(this.a);
        if (c0759u2A != null) {
            b().a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.e((com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            }, com.android.tools.r8.naming.mappinginformation.a.a(c0759u2A.r0()));
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C3266wD b(C0684j2 c0684j2) {
        C0702m c0702m = this.e.b;
        return new C3266wD(c0702m.z().a(c0702m.a(), c0702m.E().c((AbstractC1476Wj) null, c0684j2)).E0());
    }

    public final C3191vD a(com.android.tools.r8.synthesis.D d) {
        C0729q2 c0729q2 = this.a;
        d.getClass();
        if (d.i(c0729q2.d)) {
            b().a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.f((com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            }, C1442Vb.q());
        }
        return this;
    }

    private AbstractC3625g b() {
        if (this.d == null) {
            this.d = this.e.c.a(this.c, this.b.r0(), Position.UNKNOWN);
        }
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C3191vD a() {
        final MapVersion mapVersionZ0 = this.e.b.Y().z0();
        C0729q2 c0729q2 = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(mapVersionZ0, (com.android.tools.r8.graph.T0) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.c(consumer, CM.b());
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MapVersion mapVersion, com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.Y0 reference = t0.getReference();
        com.android.tools.r8.graph.Y0 y0A = this.e.b.E().a(reference);
        AbstractC3649o0 abstractC3649o0Z = this.e.b.z();
        C0722p1 c0722p1A = this.e.b.a();
        abstractC3649o0Z.getClass();
        com.android.tools.r8.graph.Y0 y0A2 = c0722p1A.a(abstractC3649o0Z.a(c0722p1A, reference.e), abstractC3649o0Z.a(c0722p1A, reference.h), abstractC3649o0Z.a(reference));
        if (y0A2.f == y0A.f && y0A.e == this.b) {
            return;
        }
        com.android.tools.r8.naming.Q.a aVar = new com.android.tools.r8.naming.Q.a(y0A.e != this.b ? y0A.F0() : y0A.f.r0(), y0A.h.r0());
        com.android.tools.r8.naming.Q.a aVarA = com.android.tools.r8.naming.Q.a.a(y0A2);
        com.android.tools.r8.naming.Q q = new com.android.tools.r8.naming.Q(aVar, aVarA, Position.UNKNOWN);
        if (AbstractC2465lT.a(mapVersion) && !aVar.c.equals(aVarA.c)) {
            q.a(C2307jT.a(y0A2), new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.a((com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
        }
        if (t0.N0()) {
            q.a(C1442Vb.q(), new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.b((com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
        }
        b().a(q);
    }

    /* JADX WARN: Code duplicated, block: B:93:0x021d  */
    public final void a(com.android.tools.r8.graph.o5 o5Var, List list, final C2067gM c2067gM, final boolean z) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0684j2 c0684j2B = this.e.b.E().b(o5Var.getReference());
        int i = 0;
        com.android.tools.r8.naming.Q.b bVarA = com.android.tools.r8.naming.Q.b.a(c0684j2B, c0684j2B.e != this.b);
        C3917j2 c3917j2A = C3917j2.a(new ArrayList());
        if (o5Var.e().N0() || (!list.isEmpty() && ((C3114uD) list.get(0)).b().m())) {
            com.android.tools.r8.synthesis.D D = this.e.b.D();
            C0729q2 c0729q2A = o5Var.a();
            D.getClass();
            if (!D.g(c0729q2A.getType())) {
                c3917j2A.a(C1442Vb.q());
            }
        }
        C0684j2 c0684j2A = this.e.b.z().a(this.e.b.a(), o5Var.getReference());
        MapVersion mapVersionZ0 = this.e.b.Y().z0();
        C0767v2 c0767v2 = this.b;
        if (!AbstractC2465lT.a(mapVersionZ0) ? !(!list.isEmpty() || !c3917j2A.a() || c0684j2A.C0() != c0684j2B.f || c0684j2B.e != c0767v2) : !(!list.isEmpty() || !c3917j2A.a() || c0684j2B != c0684j2A)) {
            if (f || this.e.b.Y().r1 == com.android.tools.r8.utils.A1.j.a || !AbstractC2773pM.a(w0E, this.e.b.Y())) {
                return;
            }
            com.android.tools.r8.graph.W wA1 = w0E.a1();
            if ((!wA1.B0() || wA1.P().i.length != 1) && !this.e.b.a(w0E)) {
                throw new AssertionError();
            }
            return;
        }
        final com.android.tools.r8.naming.Q.b bVarA2 = com.android.tools.r8.naming.Q.b.a(c0684j2A);
        if (AbstractC2465lT.a(mapVersionZ0) && (!bVarA.c.equals(bVarA2.c) || !Arrays.equals(bVarA.d, bVarA2.d))) {
            c3917j2A.a(C2385kT.a(c0684j2A));
        }
        b().a(new com.android.tools.r8.naming.Q(bVarA, bVarA2, Position.UNKNOWN));
        C0684j2 c0684j2G = null;
        if (list.isEmpty()) {
            final C3634k.b bVarA3 = b().a(null, bVarA, null, bVarA2.a);
            c3917j2A.a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.b(bVarA3, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
            return;
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        identityHashMap.put(c0684j2B, bVarA);
        final Function function = new Function() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(identityHashMap, (C0684j2) obj);
            }
        };
        AbstractC1915eM abstractC1915eMB = ((C3114uD) list.get(0)).b();
        if (abstractC1915eMB.q()) {
            c0684j2G = abstractC1915eMB.g();
        } else {
            AbstractC1915eM abstractC1915eMB2 = abstractC1915eMB.b();
            if (abstractC1915eMB2 != null) {
                AbstractC1915eM abstractC1915eMH = abstractC1915eMB2.h();
                if (abstractC1915eMH.q()) {
                    c0684j2G = abstractC1915eMH.g();
                }
            }
        }
        if (c0684j2G != null) {
            ((C3266wD) this.e.d.computeIfAbsent(c0684j2G, new Function() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((C0684j2) obj);
                }
            })).b = list;
            c3917j2A.a(new RK());
        }
        while (i < list.size()) {
            C3114uD c3114uD = (C3114uD) list.get(i);
            C3114uD c3114uD2 = c3114uD;
            int i2 = i + 1;
            char c = 1;
            while (i2 < list.size()) {
                C3114uD c3114uD3 = (C3114uD) list.get(i2);
                if (c != 4) {
                    int iE = c3114uD3.b().e();
                    int iE2 = c3114uD.b().e();
                    if (iE2 == iE) {
                        if (c == 3) {
                            c = 4;
                        } else {
                            c = (c3114uD.a() == c3114uD3.a() && c == 1) ? (char) 1 : (char) 2;
                        }
                    } else if (c != 2 && iE - iE2 == c3114uD3.a() - c3114uD.a()) {
                        c = 3;
                    } else {
                        c = 4;
                    }
                }
                AbstractC1915eM abstractC1915eMB3 = c3114uD3.b();
                AbstractC1915eM abstractC1915eMB4 = c3114uD.b();
                if (abstractC1915eMB3.g() != abstractC1915eMB4.g() || c == 4 || !Objects.equals(abstractC1915eMB3.b(), abstractC1915eMB4.b()) || abstractC1915eMB3.i() != null || c3114uD2.b().i() != null) {
                    break;
                }
                if (c3114uD2.a() > c3114uD3.a()) {
                    c3114uD2 = c3114uD3;
                }
                if (c3114uD.a() < c3114uD3.a()) {
                    c3114uD = c3114uD3;
                }
                i2++;
            }
            com.android.tools.r8.naming.N0 n0A = this.e.g.a(c3114uD2.a(), c3114uD.a());
            AbstractC1915eM abstractC1915eMB5 = c3114uD2.b();
            com.android.tools.r8.naming.N0 n0A2 = this.e.g.a(abstractC1915eMB5.e(), c3114uD.b().e());
            C0702m c0702m = this.e.b;
            AbstractC3625g abstractC3625gB = b();
            C3341xD c3341xD = this.e;
            final C3634k.b bVarA4 = a(c0702m, function, abstractC3625gB, abstractC1915eMB5, bVarA2, n0A, n0A2, c3341xD.e, c3341xD.f);
            c3917j2A.a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3191vD.a(bVarA4, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
            if (abstractC1915eMB5.i() != null) {
                final C3085to c3085to = new C3085to();
                final int iA = ((C3114uD) com.android.tools.r8.utils.V1.b(list)).a();
                abstractC1915eMB5.j().a(new BiConsumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda11
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        this.f$0.a(z, iA, c2067gM, c3085to, function, bVarA2, (Integer) obj, (AbstractC1915eM) obj2);
                    }
                });
                ((C3266wD) this.e.d.computeIfAbsent(abstractC1915eMB5.i(), new Function() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda12
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((C0684j2) obj);
                    }
                })).c.add(C3979v2.a(bVarA4, c3085to));
            }
            i = i2;
            function = function;
            bVarA2 = bVarA2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.naming.Q.b a(Map map, final C0684j2 c0684j2) {
        return (com.android.tools.r8.naming.Q.b) map.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0684j2, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.naming.Q.b a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return com.android.tools.r8.naming.Q.b.a(c0684j2, c0684j2.e != this.a.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3634k.b bVar, com.android.tools.r8.naming.mappinginformation.e eVar) {
        bVar.a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3191vD.c((com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        }, eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, int i, InterfaceC2229iM interfaceC2229iM, InterfaceC1050Ho interfaceC1050Ho, Function function, com.android.tools.r8.naming.Q.b bVar, Integer num, AbstractC1915eM abstractC1915eM) {
        int iE;
        if (z) {
            iE = num.intValue() + i + 1;
        } else {
            iE = ((AbstractC1915eM) interfaceC2229iM.a(abstractC1915eM).b()).e();
        }
        interfaceC1050Ho.b(num.intValue(), iE);
        C0702m c0702m = this.e.b;
        AbstractC3625g abstractC3625gB = b();
        com.android.tools.r8.naming.N0 n0A = this.e.g.a(iE, iE);
        com.android.tools.r8.naming.N0 n0A2 = this.e.g.a(abstractC1915eM.e(), abstractC1915eM.e());
        C3341xD c3341xD = this.e;
        a(c0702m, function, abstractC3625gB, abstractC1915eM, bVar, n0A, n0A2, c3341xD.e, c3341xD.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C3266wD a(C0684j2 c0684j2) {
        C0702m c0702m = this.e.b;
        return new C3266wD(c0702m.z().a(c0702m.a(), c0702m.E().c((AbstractC1476Wj) null, c0684j2)).E0());
    }

    private static C3634k.b a(C0702m c0702m, Function function, AbstractC3625g abstractC3625g, AbstractC1915eM abstractC1915eM, com.android.tools.r8.naming.Q.b bVar, com.android.tools.r8.naming.N0 n0, com.android.tools.r8.naming.N0 n1, IdentityHashMap identityHashMap, C3651p0 c3651p0) {
        C3634k.b bVarA = null;
        int i = -1;
        do {
            if (abstractC1915eM.m() && abstractC1915eM.k()) {
                abstractC1915eM = abstractC1915eM.b();
            } else {
                i++;
                C0767v2 c0767v2B0 = abstractC1915eM.g().B0();
                String strG = c0702m.g(c0767v2B0);
                if (strG != null) {
                    String str = (String) identityHashMap.put(c0767v2B0, strG);
                    if (!f && str != null && !str.equals(strG)) {
                        throw new AssertionError();
                    }
                }
                bVarA = abstractC3625g.a(n0, (com.android.tools.r8.naming.Q.b) function.apply(abstractC1915eM.g()), i == 0 ? n1 : c3651p0.a(Math.max(abstractC1915eM.e(), 0)), bVar.a);
                if (abstractC1915eM.r()) {
                    MapVersion mapVersion = C2308jU.c;
                    bVarA.a(new Consumer() { // from class: com.android.tools.r8.internal.vD$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            C3191vD.g((com.android.tools.r8.naming.mappinginformation.e) obj);
                        }
                    }, new C1927eU().a(C2238iU.a(Reference.classFromDescriptor(c0702m.a().T0.toString()))).a(new C2082gU(i)).a());
                }
                abstractC1915eM = abstractC1915eM.b();
            }
        } while (abstractC1915eM != null);
        if (f || bVarA != null) {
            return bVarA;
        }
        throw new AssertionError();
    }
}

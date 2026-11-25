package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ms, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2569ms {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C1566Zr b;
    private final ConcurrentHashMap c = new ConcurrentHashMap();
    private final int d;

    public C2569ms(C0702m c0702m, int i) {
        this.a = c0702m;
        this.b = new C1566Zr(c0702m);
        this.d = i;
    }

    public static C2569ms a(C0702m c0702m) {
        int i;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        Z8 z8 = C1566Zr.c;
        if (a1Y.Z0()) {
            i = 1;
        } else {
            i = a1Y.C1.m().isEmpty() ? 3 : 2;
        }
        if (i == 3) {
            return null;
        }
        return new C2569ms(c0702m, i);
    }

    private C2416ks c() {
        int i = C2416ks.o;
        final C2336js c2336js = new C2336js();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2336js, (C0729q2) obj, (C2492ls) obj2);
            }
        });
        return c2336js.a(this.a);
    }

    public final C1566Zr b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2492ls b(C0729q2 c0729q2) {
        return new C2492ls();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1720bs interfaceC1720bs) {
        boolean z = e;
        if (!z && this.a.q()) {
            throw new AssertionError();
        }
        if (o5Var.a().isInterface()) {
            if (this.d == 2) {
                if (this.a.Y().C1.d() && !o5Var.e().f.N() && this.b.e(o5Var.t()) && this.b.b(o5Var.a(), o5Var) != null) {
                    C1566Zr c1566Zr = this.b;
                    com.android.tools.r8.graph.W0 w0E = o5Var.e();
                    c1566Zr.getClass();
                    if (C1566Zr.a(w0E)) {
                        a(o5Var, this.b.a(o5Var, interfaceC1720bs));
                    }
                    if (a(o5Var)) {
                        return;
                    }
                    a(o5Var.a()).d = true;
                    return;
                }
                return;
            }
            if (o5Var.e().f.N()) {
                if (o5Var.e().u1()) {
                    return;
                }
                a(o5Var.a()).c = true;
                a(o5Var, this.b.b(o5Var, interfaceC1720bs));
                return;
            }
            if (!z && !o5Var.e().R0()) {
                throw new AssertionError();
            }
            C1566Zr c1566Zr2 = this.b;
            com.android.tools.r8.graph.W0 w0E2 = o5Var.e();
            c1566Zr2.getClass();
            if (C1566Zr.a(w0E2)) {
                a(o5Var, this.b.a(o5Var, interfaceC1720bs));
            }
            if (a(o5Var)) {
                return;
            }
            a(o5Var.a()).d = true;
        }
    }

    private void c(C0729q2 c0729q2) {
        if (!e && this.a.q()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        for (com.android.tools.r8.graph.o5 o5Var : c0729q2.k2()) {
            if (a(o5Var)) {
                arrayList.add(o5Var.e());
            }
        }
        if (arrayList.size() < c0729q2.Y().k()) {
            c0729q2.b((com.android.tools.r8.graph.W0[]) arrayList.toArray(com.android.tools.r8.graph.W0.s));
        } else if (!e) {
            throw new AssertionError("Interface " + c0729q2 + " was analysed as having bridges to remove, but no bridges were found.");
        }
    }

    static com.android.tools.r8.graph.o5 a(final C0729q2 c0729q2, C0759u2 c0759u2, C0737r2 c0737r2, C0702m c0702m, Consumer consumer, Consumer consumer2) {
        return c0702m.D().a(c0759u2, c0737r2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.k;
            }
        }, c0729q2, c0702m, new Consumer() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2569ms.a(c0729q2, (com.android.tools.r8.synthesis.P) obj);
            }
        }, consumer, consumer2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, com.android.tools.r8.synthesis.P p) {
        p.a(c0729q2.h);
    }

    /* JADX WARN: Code duplicated, block: B:69:0x0136  */
    /* JADX WARN: Code duplicated, block: B:72:0x0156  */
    /* JADX WARN: Code duplicated, block: B:75:0x0183  */
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        C0729q2 c0729q2A;
        com.android.tools.r8.graph.W0 w0E;
        boolean z;
        com.android.tools.r8.graph.W wA;
        boolean z2 = e;
        if (!z2 && !com.android.tools.r8.graph.L3.a(o5Var2.e().a1())) {
            throw new AssertionError();
        }
        if (o5Var.e().a1() != null) {
            com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
            if (!z2 && wA1 == null) {
                throw new AssertionError();
            }
            if (wA1.B0()) {
                for (com.android.tools.r8.dex.code.B1 b1 : wA1.P().i) {
                    if (b1 instanceof com.android.tools.r8.dex.code.W1) {
                        throw new C1367Tb("One or more instruction is preventing default interface method from being desugared: " + o5Var.w(), o5Var.getOrigin());
                    }
                }
                c0729q2A = o5Var.a();
                w0E = o5Var.e();
                z = e;
                if (z && w0E.A1()) {
                    throw new AssertionError();
                }
                if (z && w0E.E0() && !w0E.O0() && !w0E.P0()) {
                    throw new AssertionError("Static interface method " + o5Var.w() + " is expected to either be public or private in " + o5Var.getOrigin());
                }
                if (w0E.E0() && !w0E.O0()) {
                    if (!z) {
                        this.b.getClass();
                        if (!C1566Zr.a(w0E)) {
                            throw new AssertionError();
                        }
                    }
                    a(c0729q2A).a(o5Var.e(), o5Var2.e());
                } else {
                    a(c0729q2A).a();
                    a(c0729q2A).a(o5Var.getReference(), o5Var2.getReference());
                }
                if (w0E.p1()) {
                    o5Var2.e().a(w0E.Z0());
                }
                wA = w0E.a1().a(o5Var2.getReference(), o5Var.e(), this.a.a());
                if (!w0E.E0()) {
                    com.android.tools.r8.graph.W0.a(wA, o5Var2.getReference().F0(), this.a);
                }
                o5Var2.a(wA, this.a);
                o5Var.a(com.android.tools.r8.graph.L3.H0(), this.a);
                return;
            }
            if (!z2 && !wA1.z0()) {
                throw new AssertionError();
            }
            for (B7 b7 : wA1.K().I0()) {
                if ((b7 instanceof I7) && ((I7) b7).b(o5Var.t())) {
                    throw new C1367Tb("One or more instruction is preventing default interface method from being desugared: " + o5Var.w(), o5Var.getOrigin());
                }
            }
            c0729q2A = o5Var.a();
            w0E = o5Var.e();
            z = e;
            if (z) {
            }
            if (z) {
            }
            if (w0E.E0()) {
                a(c0729q2A).a();
                a(c0729q2A).a(o5Var.getReference(), o5Var2.getReference());
            } else {
                a(c0729q2A).a();
                a(c0729q2A).a(o5Var.getReference(), o5Var2.getReference());
            }
            if (w0E.p1()) {
                o5Var2.e().a(w0E.Z0());
            }
            wA = w0E.a1().a(o5Var2.getReference(), o5Var.e(), this.a.a());
            if (!w0E.E0()) {
                com.android.tools.r8.graph.W0.a(wA, o5Var2.getReference().F0(), this.a);
            }
            o5Var2.a(wA, this.a);
            o5Var.a(com.android.tools.r8.graph.L3.H0(), this.a);
            return;
        }
        throw new C1367Tb("Code is missing for private instance interface method: " + o5Var.getReference().r0(), o5Var.getOrigin());
    }

    private boolean a(com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0727q0 abstractC0727q0C;
        if (!e && this.a.q()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A = o5Var.a();
        if (!o5Var.getAccessFlags().X()) {
            return true;
        }
        if (this.a.Y().T0()) {
            return false;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        HashSet hashSet = new HashSet();
        C0767v2 c0767v2 = c0729q2A.f;
        if (c0767v2 != null) {
            arrayDeque.add(new C3979v2(c0729q2A, c0767v2));
        }
        for (C0767v2 c0767v3 : c0729q2A.g.a) {
            arrayDeque.add(new C3979v2(c0729q2A, c0767v3));
        }
        while (!arrayDeque.isEmpty()) {
            C3979v2 c3979v2 = (C3979v2) arrayDeque.pop();
            C0767v2 c0767v4 = (C0767v2) c3979v2.b();
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) c3979v2.a();
            if (abstractC0727q0.e0()) {
                C0664h c0664hF = this.a.f();
                abstractC0727q0C = abstractC0727q0.a0();
                c0664hF.getClass();
                if (abstractC0727q0C.d != c0767v4) {
                    AbstractC0727q0 abstractC0727q0C2 = c0664hF.c(c0767v4);
                    if (abstractC0727q0C2 != null && !abstractC0727q0C2.f0() && !abstractC0727q0C.f0()) {
                        c0664hF.a(abstractC0727q0C, abstractC0727q0C2);
                    }
                    abstractC0727q0C = abstractC0727q0C2;
                }
            } else {
                abstractC0727q0C = this.a.c(c0767v4);
            }
            if (abstractC0727q0C != null && hashSet.add(abstractC0727q0C.d)) {
                if (abstractC0727q0C.d(o5Var.getReference()) != null) {
                    return false;
                }
                C0767v2 c0767v5 = abstractC0727q0C.f;
                if (c0767v5 != null) {
                    arrayDeque.add(new C3979v2(abstractC0727q0C, c0767v5));
                }
                for (C0767v2 c0767v6 : abstractC0727q0C.g.a) {
                    arrayDeque.add(new C3979v2(abstractC0727q0C, c0767v6));
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2336js c2336js, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        boolean z = e;
        if (!z && !com.android.tools.r8.graph.L3.a(w0.a1())) {
            throw new AssertionError();
        }
        if (!z && com.android.tools.r8.graph.L3.a(w1.a1())) {
            throw new AssertionError();
        }
        w0.f.e0();
        w0.Z1();
        c2336js.d.put(w0.getReference(), w1.getReference());
    }

    public final void a() {
        C2416ks c2416ksC = c();
        if (c2416ksC != null) {
            new C2184hs(this.a, c2416ksC).a();
        }
    }

    private C2492ls a(C0729q2 c0729q2) {
        return (C2492ls) this.c.computeIfAbsent(c0729q2, new Function() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2569ms.b((C0729q2) obj);
            }
        });
    }

    public final void a(final BiConsumer biConsumer) {
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2569ms.a(biConsumer, (C0729q2) obj, (C2492ls) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C0729q2 c0729q2, C2492ls c2492ls) {
        IdentityHashMap identityHashMap = c2492ls.b;
        if (identityHashMap != null) {
            identityHashMap.forEach(biConsumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C2336js c2336js, C0729q2 c0729q2, C2492ls c2492ls) {
        if (c2492ls.c || this.a.q()) {
            com.android.tools.r8.graph.W0 w0Y0 = c0729q2.Y0();
            C0792w4 c0792w4Y = c0729q2.Y();
            if (w0Y0 != null) {
                c0792w4Y.getClass();
                c0792w4Y.a(new com.android.tools.r8.graph.W0[]{w0Y0});
            } else {
                c0792w4Y.b();
            }
        }
        IdentityHashMap identityHashMap = c2492ls.a;
        if (identityHashMap != null) {
            identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C2569ms.a(c2336js, (com.android.tools.r8.graph.W0) obj, (com.android.tools.r8.graph.W0) obj2);
                }
            });
        }
        IdentityHashMap identityHashMap2 = c2492ls.b;
        if (identityHashMap2 != null) {
            Objects.requireNonNull(c2336js);
            identityHashMap2.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ms$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    c2336js.a((C0684j2) obj, (C0684j2) obj2);
                }
            });
        }
        if (c2492ls.d) {
            if (!e && this.a.q()) {
                throw new AssertionError();
            }
            c(c0729q2);
        }
    }
}

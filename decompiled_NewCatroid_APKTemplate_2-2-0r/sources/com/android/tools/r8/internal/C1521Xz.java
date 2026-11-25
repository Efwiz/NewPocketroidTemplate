package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.C3988x2;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1521Xz {
    private static final int[] d = {1, 4, 0};
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C2037fz c;

    public C1521Xz(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A;
        this.c = c0722p1A.z4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0633d0 a(C0633d0 c0633d0, C0633d0 c0633d1, C0633d0 c0633d2) {
        return c0633d2 == c0633d0 ? c0633d1 : c0633d2;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.Wz.<init>(boolean, boolean, boolean, boolean, boolean, boolean, boolean, com.android.tools.r8.internal.Wz-IA):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:309)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:224)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:93)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    public final void b(java.util.concurrent.ExecutorService r13) {
        /*
            r12 = this;
            com.android.tools.r8.graph.m r0 = r12.a
            com.android.tools.r8.internal.Wj r0 = r0.E()
            com.android.tools.r8.graph.m r1 = r12.a
            com.android.tools.r8.internal.Wj r1 = r1.w()
            com.android.tools.r8.graph.p1 r2 = r12.b
            com.android.tools.r8.graph.v2 r2 = r2.t3
            com.android.tools.r8.graph.v2 r0 = r0.b(r1, r2)
            com.android.tools.r8.graph.m r1 = r12.a
            com.android.tools.r8.graph.q0 r1 = r1.c(r0)
            com.android.tools.r8.internal.Wz r11 = new com.android.tools.r8.internal.Wz
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r3 = r12.c
            com.android.tools.r8.internal.dz r3 = r3.d
            com.android.tools.r8.graph.u2 r3 = r3.a
            boolean r3 = a(r1, r2, r3)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r4 = r12.c
            com.android.tools.r8.internal.dz r4 = r4.d
            com.android.tools.r8.graph.u2 r4 = r4.b
            boolean r4 = a(r1, r2, r4)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r5 = r12.c
            com.android.tools.r8.internal.dz r5 = r5.d
            com.android.tools.r8.graph.u2 r5 = r5.c
            a(r1, r2, r5)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r5 = r12.c
            com.android.tools.r8.internal.dz r5 = r5.d
            com.android.tools.r8.graph.u2 r5 = r5.d
            boolean r5 = a(r1, r2, r5)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r6 = r12.c
            com.android.tools.r8.internal.dz r6 = r6.d
            com.android.tools.r8.graph.u2 r6 = r6.e
            boolean r6 = a(r1, r2, r6)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r7 = r12.c
            com.android.tools.r8.internal.dz r7 = r7.d
            com.android.tools.r8.graph.u2 r7 = r7.f
            boolean r7 = a(r1, r2, r7)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r8 = r12.c
            com.android.tools.r8.internal.dz r8 = r8.d
            com.android.tools.r8.graph.u2 r8 = r8.g
            boolean r8 = a(r1, r2, r8)
            com.android.tools.r8.graph.m r2 = r12.a
            com.android.tools.r8.internal.fz r9 = r12.c
            com.android.tools.r8.internal.dz r9 = r9.d
            com.android.tools.r8.graph.u2 r9 = r9.h
            boolean r9 = a(r1, r2, r9)
            r10 = 0
            r2 = r11
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            com.android.tools.r8.graph.m r1 = r12.a
            com.android.tools.r8.graph.h r1 = r1.f()
            java.util.Collection r1 = r1.d()
            com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda5 r2 = new com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda5
            r2.<init>()
            com.android.tools.r8.utils.C3879c3.a(r1, r2, r13)
            com.android.tools.r8.graph.m r13 = r12.a
            com.android.tools.r8.internal.Wj r0 = r13.E()
            r13.c(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1521Xz.b(java.util.concurrent.ExecutorService):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0767v2 c0767v2, C0633d0 c0633d0) {
        return c0633d0.b.a != c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0767v2 c0767v2, C1495Wz c1495Wz, C0729q2 c0729q2) {
        InterfaceC2743oz interfaceC2743ozV1 = c0729q2.V1();
        if (interfaceC2743ozV1 == AbstractC1745cA.b()) {
            return;
        }
        C0633d0 c0633d0A = c0729q2.s0().a(c0767v2);
        if (c0633d0A != null && interfaceC2743ozV1 != AbstractC1745cA.c() && (!this.a.f().i() || ((C3733i) this.a.i0().f()).g(c0729q2))) {
            a(c0729q2, interfaceC2743ozV1, c0633d0A, c1495Wz);
        } else if (c0633d0A != null) {
            C0658g0 c0658g0S0 = c0729q2.s0();
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C1521Xz.a(c0767v2, (C0633d0) obj);
                }
            };
            c0658g0S0.getClass();
            c0729q2.a(c0658g0S0.a(C3988x2.a(predicate)));
        }
    }

    public final void a(ExecutorService executorService) {
        if (this.a.z().c()) {
            return;
        }
        final C1495Wz c1495Wz = new C1495Wz(true, true, true, true, true, true, true);
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K();
        C3879c3.a(this.a.f().d(), new Consumer() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(k, c1495Wz, (C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final com.android.tools.r8.utils.K k, C1495Wz c1495Wz, C0729q2 c0729q2) {
        C0633d0 c0633d0A = c0729q2.s0().a(this.b.t3);
        if (c0633d0A == null) {
            return;
        }
        C0702m c0702m = this.a;
        Consumer consumerB = C3912j0.b();
        Objects.requireNonNull(k);
        InterfaceC2743oz interfaceC2743ozA = C2888qz.a(c0702m, c0729q2, c0633d0A, consumerB, new Supplier() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return k.b();
            }
        });
        if (interfaceC2743ozA == AbstractC1745cA.c()) {
            return;
        }
        a(c0729q2, interfaceC2743ozA, c0633d0A, c1495Wz);
    }

    private void a(final AbstractC0727q0 abstractC0727q0, final InterfaceC2743oz interfaceC2743oz, final C0633d0 c0633d0, final C1495Wz c1495Wz) {
        try {
            final C3979v2 c3979v2A = interfaceC2743oz.a(this.a, abstractC0727q0);
            if (!((Boolean) c3979v2A.b()).booleanValue() && this.a.Y().n1.t0) {
                if (!e && !this.a.a(new Supplier() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.a(abstractC0727q0, c3979v2A, interfaceC2743oz, c1495Wz);
                    }
                })) {
                    throw new AssertionError();
                }
            } else {
                final C0633d0 c0633d0A = a((InterfaceC3115uE) c3979v2A.a(), interfaceC2743oz.m(), a(d, interfaceC2743oz.t()), c1495Wz);
                abstractC0727q0.a(abstractC0727q0.s0().a(new Function() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C1521Xz.a(c0633d0, c0633d0A, (C0633d0) obj);
                    }
                }));
            }
        } catch (Throwable th) {
            if (!e && !this.a.a(new Supplier() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.a(abstractC0727q0, th);
                }
            })) {
                throw new AssertionError();
            }
            this.a.Y().e.warning(C1339Rz.a(abstractC0727q0.d, th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean a(AbstractC0727q0 abstractC0727q0, C3979v2 c3979v2, InterfaceC2743oz interfaceC2743oz, C1495Wz c1495Wz) {
        C0633d0 c0633d0A = abstractC0727q0.s0().a(this.b.t3);
        C0633d0 c0633d0A2 = a((InterfaceC3115uE) c3979v2.a(), interfaceC2743oz.m(), a(d, interfaceC2743oz.t()), c1495Wz);
        try {
            String strA = C1821dA.a("", C2888qz.a(this.c, c0633d0A.b));
            String strA2 = C1821dA.a("", C2888qz.a(this.c, c0633d0A2.b));
            if (!e && !strA.equals(strA2)) {
                throw new AssertionError("The metadata should be equivalent");
            }
        } catch (C1440Uz unused) {
        }
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(AbstractC0727q0 abstractC0727q0, Throwable th) {
        this.a.Y().e.a(C1339Rz.a(abstractC0727q0.d, th));
        throw null;
    }

    private static boolean a(AbstractC0727q0 abstractC0727q0, C0702m c0702m, final C0759u2 c0759u2) {
        if (!c0702m.f().i() || abstractC0727q0 == null || abstractC0727q0.I1()) {
            return true;
        }
        return abstractC0727q0.h(new Predicate() { // from class: com.android.tools.r8.internal.Xz$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1521Xz.a(c0759u2, (com.android.tools.r8.graph.W0) obj);
            }
        }).iterator().hasNext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0759u2 c0759u2, com.android.tools.r8.graph.W0 w0) {
        return w0.getReference().f == c0759u2;
    }

    private C0633d0 a(InterfaceC3115uE interfaceC3115uE, String str, int[] iArr, C1495Wz c1495Wz) {
        ArrayList arrayList = new ArrayList();
        if (c1495Wz.b) {
            C0759u2 c0759u2 = this.c.d.b;
            com.android.tools.r8.graph.B2[] b2Arr = new com.android.tools.r8.graph.B2[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                b2Arr[i] = com.android.tools.r8.graph.B2.g.j(iArr[i]);
            }
            arrayList.add(new C0649f0(c0759u2, new com.android.tools.r8.graph.B2.a(b2Arr)));
        }
        if (c1495Wz.a) {
            arrayList.add(new C0649f0(this.c.d.a, com.android.tools.r8.graph.B2.g.j(interfaceC3115uE.k())));
        }
        if (c1495Wz.c) {
            C0759u2 c0759u3 = this.c.d.d;
            String[] strArrD1 = interfaceC3115uE.d1();
            com.android.tools.r8.graph.B2[] b2Arr2 = new com.android.tools.r8.graph.B2[strArrD1.length];
            for (int i2 = 0; i2 < strArrD1.length; i2++) {
                b2Arr2[i2] = new com.android.tools.r8.graph.B2.j(this.b.c(strArrD1[i2]));
            }
            arrayList.add(new C0649f0(c0759u3, new com.android.tools.r8.graph.B2.a(b2Arr2)));
        }
        if (c1495Wz.d) {
            C0759u2 c0759u4 = this.c.d.e;
            String[] strArrD2 = interfaceC3115uE.d2();
            com.android.tools.r8.graph.B2[] b2Arr3 = new com.android.tools.r8.graph.B2[strArrD2.length];
            for (int i3 = 0; i3 < strArrD2.length; i3++) {
                b2Arr3[i3] = new com.android.tools.r8.graph.B2.j(this.b.c(strArrD2[i3]));
            }
            arrayList.add(new C0649f0(c0759u4, new com.android.tools.r8.graph.B2.a(b2Arr3)));
        }
        if (c1495Wz.f && str != null && !str.isEmpty()) {
            arrayList.add(new C0649f0(this.c.d.g, new com.android.tools.r8.graph.B2.j(this.b.c(str))));
        }
        if (c1495Wz.e && !interfaceC3115uE.xs().isEmpty()) {
            arrayList.add(new C0649f0(this.c.d.f, new com.android.tools.r8.graph.B2.j(this.b.c(interfaceC3115uE.xs()))));
        }
        if (c1495Wz.g && interfaceC3115uE.xi() != 0) {
            arrayList.add(new C0649f0(this.c.d.h, com.android.tools.r8.graph.B2.g.j(interfaceC3115uE.xi())));
        }
        return new C0633d0(1, new com.android.tools.r8.graph.R0(this.b.t3, (C0649f0[]) arrayList.toArray(C0649f0.c)));
    }

    private static int[] a(int[] iArr, int[] iArr2) {
        boolean z = e;
        if (!z && iArr.length != 2 && iArr.length != 3) {
            throw new AssertionError();
        }
        if (!z && iArr2.length != 2 && iArr2.length != 3) {
            throw new AssertionError();
        }
        int i = iArr[0];
        int i2 = iArr2[0];
        if (i != i2) {
            return i > i2 ? iArr : iArr2;
        }
        int i3 = iArr[1];
        int i4 = iArr2[1];
        if (i3 != i4) {
            return i3 > i4 ? iArr : iArr2;
        }
        int i5 = iArr.length >= 3 ? iArr[2] : 0;
        int i6 = iArr2.length >= 3 ? iArr2[2] : 0;
        return (i5 == i6 || i5 > i6) ? iArr : iArr2;
    }
}

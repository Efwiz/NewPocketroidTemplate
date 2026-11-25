package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.graph.W0$$ExternalSyntheticLambda12;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda28;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3894f2;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ga, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1002Ga {
    static final /* synthetic */ boolean m = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C1566Zr c;
    private final boolean e;
    private final Predicate f;
    private final int l;
    private final C3894f2 d = C3894f2.a();
    private final ConcurrentHashMap g = new ConcurrentHashMap();
    private final ConcurrentHashMap h = new ConcurrentHashMap();
    private final ConcurrentHashMap i = new ConcurrentHashMap();
    private final ConcurrentHashMap j = new ConcurrentHashMap();
    private final ConcurrentHashMap k = new ConcurrentHashMap();

    C1002Ga(C0702m c0702m, Predicate predicate, int i) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = new C1566Zr(c0702m);
        if (!m && i == 3) {
            throw new AssertionError();
        }
        this.e = !c0702m.Y().C1.isEmpty();
        this.f = predicate;
        this.l = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0684j2 c0684j2) {
        return true;
    }

    public final void a(final InterfaceC2262is interfaceC2262is, C0729q2 c0729q2) {
        if (c0729q2.isInterface()) {
            return;
        }
        b(new C0850Ba(c0729q2, c0729q2, new BiConsumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(interfaceC2262is, (C0729q2) obj, (C0767v2) obj2);
            }
        }), interfaceC2262is, c0729q2);
    }

    private C0887Ca b(AbstractC0727q0 abstractC0727q0, C0887Ca c0887Ca) {
        boolean z = m;
        if (!z && !abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.f != this.b.Z1) {
            throw new AssertionError();
        }
        if (!z && this.c.e(abstractC0727q0.d)) {
            throw new AssertionError();
        }
        if (this.l == 2) {
            return C0887Ca.c;
        }
        if (abstractC0727q0.f0() && (!this.e || !this.c.a(abstractC0727q0))) {
            return c0887Ca;
        }
        C0822Aa c0822AaA = a(abstractC0727q0, new Predicate() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1002Ga.b((C0684j2) obj);
            }
        });
        c0887Ca.getClass();
        return c0822AaA.a.isEmpty() ? c0887Ca : new C0887Ca(c0887Ca.a.a(c0822AaA), c0887Ca.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC2262is interfaceC2262is, C0729q2 c0729q2, C0767v2 c0767v2) {
        interfaceC2262is.a(c0729q2, c0767v2, this.c);
    }

    public final void a(final InterfaceC2262is interfaceC2262is, ExecutorService executorService) {
        C3879c3.a(this.j, new InterfaceC3890e3() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                C1002Ga.a(interfaceC2262is, (C0729q2) obj, (Map) obj2);
            }
        }, executorService);
        this.k.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(interfaceC2262is, (C0729q2) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Set set, Map map, C0767v2 c0767v2) {
        if (set.contains(c0767v2)) {
            map.put(c0767v2, new C0706m3.c(this.c.b(c0767v2)));
        }
        a((List) null, map, c0767v2, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map b(C0729q2 c0729q2) {
        return new ConcurrentHashMap();
    }

    private C3297wa b(final C0850Ba c0850Ba, final InterfaceC2262is interfaceC2262is, AbstractC0727q0 abstractC0727q0) {
        if (!m && abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        if (abstractC0727q0.f0()) {
            return C3297wa.d;
        }
        ConcurrentHashMap concurrentHashMap = this.g;
        Function function = new Function() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0850Ba, interfaceC2262is, (AbstractC0727q0) obj);
            }
        };
        Object objApply = concurrentHashMap.get(abstractC0727q0);
        if (objApply == null) {
            synchronized (abstractC0727q0) {
                objApply = concurrentHashMap.get(abstractC0727q0);
                if (objApply == null) {
                    objApply = function.apply(abstractC0727q0);
                    concurrentHashMap.put(abstractC0727q0, objApply);
                }
            }
        }
        return (C3297wa) objApply;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(InterfaceC2262is interfaceC2262is, C0729q2 c0729q2, Map map) {
        ArrayList arrayList = new ArrayList(map.size());
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            arrayList.add(((AbstractC0944Ea) it.next()).a.e());
        }
        arrayList.sort(Comparator.comparing(new W0$$ExternalSyntheticLambda12()));
        c0729q2.b((Collection) arrayList);
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            AbstractC0944Ea abstractC0944Ea = (AbstractC0944Ea) map.get(((com.android.tools.r8.graph.W0) it2.next()).getReference());
            abstractC0944Ea.getClass();
            if (abstractC0944Ea instanceof C0916Da) {
                interfaceC2262is.a(abstractC0944Ea.a().b, abstractC0944Ea.a);
            } else {
                C0976Fa c0976FaB = abstractC0944Ea.b();
                interfaceC2262is.a(abstractC0944Ea.a, c0976FaB.b, c0976FaB.c);
            }
        }
    }

    private C0887Ca b(final C0850Ba c0850Ba, AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0.f0() && !this.e) {
            return C0887Ca.c;
        }
        ConcurrentHashMap concurrentHashMap = this.i;
        Function function = new Function() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0850Ba, (AbstractC0727q0) obj);
            }
        };
        Object objApply = concurrentHashMap.get(abstractC0727q0);
        if (objApply == null) {
            synchronized (abstractC0727q0) {
                objApply = concurrentHashMap.get(abstractC0727q0);
                if (objApply == null) {
                    objApply = function.apply(abstractC0727q0);
                    concurrentHashMap.put(abstractC0727q0, objApply);
                }
            }
        }
        return (C0887Ca) objApply;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC2262is interfaceC2262is, C0729q2 c0729q2, List list) {
        if (list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            interfaceC2262is.a(c0729q2, this.c.a(((C0706m3.c) it.next()).q()));
        }
        c0729q2.c(list);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.Ca.<init>(com.android.tools.r8.internal.Aa, com.android.tools.r8.internal.xa, com.android.tools.r8.internal.Ca-IA):void, class status: GENERATED_AND_UNLOADED
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
    private com.android.tools.r8.internal.C0887Ca a(com.android.tools.r8.graph.AbstractC0727q0 r7, com.android.tools.r8.internal.C0887Ca r8) {
        /*
            r6 = this;
            boolean r0 = com.android.tools.r8.internal.C1002Ga.m
            if (r0 != 0) goto L11
            boolean r1 = r7.isInterface()
            if (r1 == 0) goto Lb
            goto L11
        Lb:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L11:
            if (r0 != 0) goto L22
            com.android.tools.r8.graph.v2 r1 = r7.f
            com.android.tools.r8.graph.p1 r2 = r6.b
            com.android.tools.r8.graph.v2 r2 = r2.Z1
            if (r1 != r2) goto L1c
            goto L22
        L1c:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L22:
            if (r0 != 0) goto L35
            com.android.tools.r8.internal.Zr r1 = r6.c
            com.android.tools.r8.graph.v2 r2 = r7.d
            boolean r1 = r1.e(r2)
            if (r1 == 0) goto L2f
            goto L35
        L2f:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L35:
            if (r0 != 0) goto L42
            boolean r0 = r6.e
            if (r0 == 0) goto L3c
            goto L42
        L3c:
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L42:
            com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda5 r0 = new com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda5
            r0.<init>()
            com.android.tools.r8.internal.Aa r0 = r6.a(r7, r0)
            com.android.tools.r8.internal.xa r1 = new com.android.tools.r8.internal.xa
            com.android.tools.r8.internal.ya r2 = new com.android.tools.r8.internal.ya
            com.android.tools.r8.graph.v2 r7 = r7.d
            r2.<init>(r7)
            r7 = 0
            r1.<init>(r0, r2)
            r8.getClass()
            boolean r3 = r1.a()
            if (r3 == 0) goto L62
            goto La2
        L62:
            com.android.tools.r8.internal.Ca r3 = new com.android.tools.r8.internal.Ca
            com.android.tools.r8.internal.Aa r4 = r8.a
            com.android.tools.r8.internal.xa r8 = r8.b
            boolean r5 = r8.a()
            if (r5 == 0) goto L6f
            goto L9e
        L6f:
            boolean r1 = r1.a()
            if (r1 == 0) goto L77
            r1 = r8
            goto L9e
        L77:
            com.android.tools.r8.internal.xa r1 = new com.android.tools.r8.internal.xa
            com.android.tools.r8.internal.Aa r5 = r8.a
            com.android.tools.r8.internal.Aa r0 = r5.a(r0)
            com.android.tools.r8.internal.ya r8 = r8.b
            r8.getClass()
            com.android.tools.r8.internal.Bm r5 = com.android.tools.r8.internal.AbstractC1048Hm.g()
            java.util.Set r8 = r8.a
            r5.a(r8)
            java.util.Set r8 = r2.a
            r5.a(r8)
            com.android.tools.r8.internal.ya r8 = new com.android.tools.r8.internal.ya
            com.android.tools.r8.internal.Hm r2 = r5.a()
            r8.<init>(r2)
            r1.<init>(r0, r8)
        L9e:
            r3.<init>(r4, r1)
            r8 = r3
        La2:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1002Ga.a(com.android.tools.r8.graph.q0, com.android.tools.r8.internal.Ca):com.android.tools.r8.internal.Ca");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0684j2 c0684j2) {
        return this.a.Y().C1.a(c0684j2) != null;
    }

    private C0822Aa a(AbstractC0727q0 abstractC0727q0, Predicate predicate) {
        if (!m && !abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        HashSet hashSet = new HashSet(abstractC0727q0.Y().k());
        for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.m(new q2$$ExternalSyntheticLambda28())) {
            if (predicate.test(w0.getReference())) {
                hashSet.add(this.d.c(w0.getReference()));
            }
        }
        C0822Aa c0822Aa = C0822Aa.b;
        return hashSet.isEmpty() ? C0822Aa.b : new C0822Aa(hashSet);
    }

    private void a(final AbstractC0727q0 abstractC0727q0, C3444ya c3444ya) {
        if (abstractC0727q0.I1()) {
            return;
        }
        HashSet hashSet = new HashSet(c3444ya.a);
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        Iterator it = c3444ya.a.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) it.next());
            if (abstractC0727q0C != null) {
                if (!m && !abstractC0727q0C.f0() && !this.a.Y().C1.d()) {
                    throw new AssertionError();
                }
                o3E.a((Iterable) abstractC0727q0C.f1());
            }
        }
        while (o3E.c()) {
            C0767v2 c0767v2 = (C0767v2) o3E.f();
            hashSet.remove(c0767v2);
            AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0767v2);
            if (abstractC0727q0C2 != null) {
                o3E.a((Iterable) abstractC0727q0C2.f1());
            }
        }
        Iterator it2 = c3444ya.a.iterator();
        while (it2.hasNext()) {
            AbstractC0727q0 abstractC0727q0C3 = this.a.c((C0767v2) it2.next());
            if (abstractC0727q0C3 != null) {
                abstractC0727q0C3 = this.a.c(abstractC0727q0C3.f);
            }
            while (abstractC0727q0C3 != null && abstractC0727q0C3.getType() != this.a.a().Z1) {
                hashSet.remove(abstractC0727q0C3.getType());
                abstractC0727q0C3 = this.a.c(abstractC0727q0C3.n1());
            }
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        a(abstractC0727q0, hashSet, identityHashMap);
        ArrayList arrayList = new ArrayList(hashSet);
        Collections.sort(arrayList);
        final ArrayList arrayList2 = new ArrayList();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            C0706m3.c cVar = (C0706m3.c) identityHashMap.get((C0767v2) it3.next());
            if (!m && cVar == null) {
                throw new AssertionError();
            }
            arrayList2.add(cVar);
        }
        abstractC0727q0.f1().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList2, abstractC0727q0, (C0767v2) obj);
            }
        });
        this.k.put(abstractC0727q0.a0(), arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        for (int i = 0; i < list.size(); i++) {
            if (((C0706m3.c) list.get(i)).q() == c0767v2) {
                if (this.a.Y().i0().b) {
                    list.remove(i);
                    return;
                }
                throw new C1367Tb("Code has already been library desugared. Interface " + c0767v2.D0() + " is already implemented by " + abstractC0727q0.getType().D0());
            }
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, final HashSet hashSet, final IdentityHashMap identityHashMap) {
        if (this.a.Y().T0() && abstractC0727q0.O1()) {
            abstractC0727q0.c(new BiConsumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda12
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(hashSet, identityHashMap, (C0767v2) obj, (C0706m3.c) obj2);
                }
            });
        } else {
            abstractC0727q0.d(new Consumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(hashSet, identityHashMap, (C0767v2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Map map, C0767v2 c0767v2, C0706m3.c cVar) {
        if (set.contains(c0767v2)) {
            map.put(c0767v2, new C0706m3.c(this.c.b(c0767v2), cVar.r()));
        }
        a(cVar.r(), map, c0767v2, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Map map, C0767v2 c0767v2) {
        if (set.contains(c0767v2)) {
            map.put(c0767v2, new C0706m3.c(this.c.b(c0767v2)));
        }
        a((List) null, map, c0767v2, set);
    }

    private void a(List list, final Map map, C0767v2 c0767v2, final Set set) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null) {
            return;
        }
        if (this.a.Y().T0() && abstractC0727q0C.O1()) {
            if (!m && list == null) {
                throw new AssertionError();
            }
            abstractC0727q0C.b(list, new BiConsumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda15
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(set, map, (C0767v2) obj, (List) obj2);
                }
            });
            return;
        }
        if (!m && list != null) {
            throw new AssertionError();
        }
        abstractC0727q0C.d(new Consumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(set, map, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Map map, C0767v2 c0767v2, List list) {
        if (set.contains(c0767v2)) {
            map.put(c0767v2, new C0706m3.c(this.c.b(c0767v2), list));
        }
        a(list, map, c0767v2, set);
    }

    private void a(AbstractC0727q0 abstractC0727q0, C3297wa c3297wa, C1507Xl c1507Xl, C0684j2 c0684j2) {
        C0684j2 c0684j2B;
        C0680j c0680jG = this.a.g();
        com.android.tools.r8.graph.I4 i4G = c0680jG.g(abstractC0727q0, c0684j2);
        if (i4G.h() || ((com.android.tools.r8.graph.W0) i4G.g().b()).E0()) {
            final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(true);
            if (i4G.h()) {
                i4G.k().a(C3912j0.b(), new Consumer() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C1002Ga.a(k, (com.android.tools.r8.graph.W0) obj);
                    }
                });
            } else if (i4G.j()) {
                k.c(((com.android.tools.r8.graph.W0) i4G.g().b()).E0());
            }
            if (k.c() && k.e()) {
                i4G = c0680jG.b(c0684j2.e, c0684j2);
            }
            if (i4G.h()) {
                if (i4G.v()) {
                    a(c0684j2, abstractC0727q0, this.b.p3, i4G.k());
                    return;
                }
                if (i4G.b(abstractC0727q0, this.a, c0680jG)) {
                    a(c0684j2, abstractC0727q0, this.b.r3, i4G.k());
                    return;
                } else {
                    if (!m && !i4G.a(abstractC0727q0, this.a, c0680jG)) {
                        throw new AssertionError();
                    }
                    a(c0684j2, abstractC0727q0, this.b.n3, i4G.k());
                    return;
                }
            }
        }
        boolean z = m;
        if (!z && !i4G.j()) {
            throw new AssertionError();
        }
        InterfaceC0669h4 interfaceC0669h4A = i4G.a(abstractC0727q0, c0680jG);
        if (interfaceC0669h4A == null) {
            if (z) {
                this.a.Y().e.c("The class processor was not able to look-up the default method " + c0684j2 + " in the class " + abstractC0727q0 + " (Single resolution: " + i4G.x() + "; resolution pair: " + i4G.c() + "). Please report this issue in the D8/R8 bug tracker at https://issuetracker.google.com/issues/237507594.");
                a(c0684j2, abstractC0727q0, this.b.r3, com.android.tools.r8.graph.I4.b.A());
                return;
            }
            throw new AssertionError();
        }
        AbstractC0748t0 abstractC0748t0P = interfaceC0669h4A.p();
        if (!z && abstractC0748t0P == null) {
            throw new AssertionError();
        }
        abstractC0748t0P.getClass();
        boolean z2 = abstractC0748t0P instanceof com.android.tools.r8.graph.o5;
        if (!(z2 ? this.f.test(abstractC0748t0P.g0()) : true) || c3297wa.a(abstractC0748t0P)) {
            return;
        }
        if (abstractC0748t0P.H()) {
            c0684j2B = this.c.a(abstractC0748t0P).getReference();
        } else {
            C2630ne c2630neA = this.c.a(abstractC0748t0P.a(), abstractC0748t0P);
            c0684j2B = c2630neA != null ? this.c.b(c2630neA) : null;
        }
        if (c0684j2B != null) {
            c1507Xl.a(abstractC0748t0P);
            if (abstractC0727q0.e0()) {
                com.android.tools.r8.graph.W0 w0C = abstractC0727q0.c(abstractC0748t0P.getReference());
                if (w0C == null) {
                    com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.a(abstractC0748t0P, abstractC0727q0, c0684j2B, this.b);
                    if (!z2 || abstractC0748t0P.e().D1().d()) {
                        w0A.a(AbstractC3938n2.a);
                    }
                    a(abstractC0748t0P, w0A.a(abstractC0727q0.a0()));
                    return;
                }
                throw new C1367Tb("Attempt to add forwarding method that conflicts with existing method.", null, abstractC0727q0.c, new MethodPosition(w0C.getReference().E0()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.K k, com.android.tools.r8.graph.W0 w0) {
        k.a(w0.E0());
    }

    private void a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0944Ea abstractC0944Ea = (AbstractC0944Ea) ((Map) this.j.computeIfAbsent(o5Var.a(), new Function() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1002Ga.a((C0729q2) obj);
            }
        })).put(o5Var.getReference(), new C0916Da(abstractC0748t0, o5Var));
        if (!m && abstractC0944Ea != null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0729q2 c0729q2) {
        return new ConcurrentHashMap();
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2, com.android.tools.r8.graph.I4.a aVar) {
        AbstractC0944Ea abstractC0944Ea = (AbstractC0944Ea) ((Map) this.j.computeIfAbsent(o5Var.a(), new Function() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1002Ga.b((C0729q2) obj);
            }
        })).put(o5Var.getReference(), new C0976Fa(o5Var, c0767v2, aVar));
        if (!m && abstractC0944Ea != null) {
            throw new AssertionError();
        }
    }

    private void a(C0684j2 c0684j2, AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2, com.android.tools.r8.graph.I4.a aVar) {
        if (abstractC0727q0.e0()) {
            C0725p4 c0725p4 = (C0725p4) ((C0719o4) C0725p4.O().c(true)).a();
            C0684j2 c0684j2B = c0684j2.b(abstractC0727q0.getType(), this.b);
            com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(c0684j2B).a(c0725p4);
            C0722p1 c0722p1 = this.b;
            a(aVarA.a(new C0753u(c0684j2B.B0(), 2, com.android.tools.r8.utils.M.a(!c0725p4.o()) + c0684j2B.G0().size(), AbstractC1639am.a(new V7(null, c0767v2), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.Y0), false), new V8()))).c().a().a(abstractC0727q0.a0()), c0767v2, aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:103:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:106:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:143:0x0214 A[EDGE_INSN: B:143:0x0214->B:110:0x0214 BREAK  A[LOOP:1: B:93:0x01a4->B:145:?, LOOP_LABEL: LOOP:1: B:93:0x01a4->B:145:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x0208 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:149:0x01f0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:150:0x01f0 A[SYNTHETIC] */
    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.Ca.<init>(com.android.tools.r8.internal.Aa, com.android.tools.r8.internal.xa, com.android.tools.r8.internal.Ca-IA):void, class status: GENERATED_AND_UNLOADED
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
    public com.android.tools.r8.internal.C3297wa a(com.android.tools.r8.internal.C0850Ba r12, com.android.tools.r8.internal.InterfaceC2262is r13, com.android.tools.r8.graph.AbstractC0727q0 r14) {
        /*
            Method dump skipped, instruction units count: 676
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1002Ga.a(com.android.tools.r8.internal.Ba, com.android.tools.r8.internal.is, com.android.tools.r8.graph.q0):com.android.tools.r8.internal.wa");
    }

    private C0887Ca a(AbstractC0727q0 abstractC0727q0) {
        if (!m && abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        if (abstractC0727q0.f0()) {
            ConcurrentHashMap concurrentHashMap = this.h;
            C0635d2 c0635d2D0 = abstractC0727q0.d0();
            Function function = new Function() { // from class: com.android.tools.r8.internal.Ga$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((C0635d2) obj);
                }
            };
            Object objApply = concurrentHashMap.get(c0635d2D0);
            if (objApply == null) {
                synchronized (c0635d2D0) {
                    objApply = concurrentHashMap.get(c0635d2D0);
                    if (objApply == null) {
                        objApply = function.apply(c0635d2D0);
                        concurrentHashMap.put(c0635d2D0, objApply);
                    }
                }
            }
            return (C0887Ca) objApply;
        }
        return C0887Ca.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.Ca.<init>(com.android.tools.r8.internal.Aa, com.android.tools.r8.internal.xa, com.android.tools.r8.internal.Ca-IA):void, class status: GENERATED_AND_UNLOADED
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
    public com.android.tools.r8.internal.C0887Ca a(com.android.tools.r8.graph.C0635d2 r11) {
        /*
            r10 = this;
            com.android.tools.r8.graph.v2 r0 = r11.f
            boolean r1 = r10.e
            r2 = 0
            if (r1 != 0) goto La
            com.android.tools.r8.internal.Ca r0 = com.android.tools.r8.internal.C0887Ca.c
            goto L25
        La:
            if (r0 == 0) goto L1b
            com.android.tools.r8.graph.p1 r1 = r10.b
            com.android.tools.r8.graph.v2 r1 = r1.Z1
            if (r0 != r1) goto L13
            goto L1b
        L13:
            com.android.tools.r8.graph.m r1 = r10.a
            com.android.tools.r8.graph.q0 r0 = r1.c(r0)
            if (r0 != 0) goto L1c
        L1b:
            r0 = r2
        L1c:
            if (r0 != 0) goto L21
            com.android.tools.r8.internal.Ca r0 = com.android.tools.r8.internal.C0887Ca.c
            goto L25
        L21:
            com.android.tools.r8.internal.Ca r0 = r10.a(r0)
        L25:
            com.android.tools.r8.graph.x2 r11 = r11.g
            com.android.tools.r8.graph.v2[] r11 = r11.a
            int r1 = r11.length
            r3 = 0
        L2b:
            if (r3 >= r1) goto Lc4
            r4 = r11[r3]
            com.android.tools.r8.internal.za r5 = com.android.tools.r8.internal.C3514za.d
            if (r4 == 0) goto L42
            com.android.tools.r8.graph.p1 r6 = r10.b
            com.android.tools.r8.graph.v2 r6 = r6.Z1
            if (r4 != r6) goto L3a
            goto L42
        L3a:
            com.android.tools.r8.graph.m r6 = r10.a
            com.android.tools.r8.graph.q0 r4 = r5.a(r6, r4)
            if (r4 != 0) goto L43
        L42:
            r4 = r2
        L43:
            if (r4 != 0) goto L48
            com.android.tools.r8.internal.Ca r4 = com.android.tools.r8.internal.C0887Ca.c
            goto L4c
        L48:
            com.android.tools.r8.internal.Ca r4 = r10.b(r5, r4)
        L4c:
            com.android.tools.r8.internal.Aa r5 = r0.a
            java.util.Set r5 = r5.a
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L60
            com.android.tools.r8.internal.xa r5 = r0.b
            boolean r5 = r5.a()
            if (r5 == 0) goto L60
            r0 = r4
            goto Lc0
        L60:
            com.android.tools.r8.internal.Aa r5 = r4.a
            java.util.Set r5 = r5.a
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L73
            com.android.tools.r8.internal.xa r5 = r4.b
            boolean r5 = r5.a()
            if (r5 == 0) goto L73
            goto Lc0
        L73:
            com.android.tools.r8.internal.Ca r5 = new com.android.tools.r8.internal.Ca
            com.android.tools.r8.internal.Aa r6 = r0.a
            com.android.tools.r8.internal.Aa r7 = r4.a
            com.android.tools.r8.internal.Aa r6 = r6.a(r7)
            com.android.tools.r8.internal.xa r0 = r0.b
            com.android.tools.r8.internal.xa r4 = r4.b
            boolean r7 = r0.a()
            if (r7 == 0) goto L89
            r0 = r4
            goto Lbc
        L89:
            boolean r7 = r4.a()
            if (r7 == 0) goto L90
            goto Lbc
        L90:
            com.android.tools.r8.internal.xa r7 = new com.android.tools.r8.internal.xa
            com.android.tools.r8.internal.Aa r8 = r0.a
            com.android.tools.r8.internal.Aa r9 = r4.a
            com.android.tools.r8.internal.Aa r8 = r8.a(r9)
            com.android.tools.r8.internal.ya r0 = r0.b
            com.android.tools.r8.internal.ya r4 = r4.b
            r0.getClass()
            com.android.tools.r8.internal.Bm r9 = com.android.tools.r8.internal.AbstractC1048Hm.g()
            java.util.Set r0 = r0.a
            r9.a(r0)
            java.util.Set r0 = r4.a
            r9.a(r0)
            com.android.tools.r8.internal.ya r0 = new com.android.tools.r8.internal.ya
            com.android.tools.r8.internal.Hm r4 = r9.a()
            r0.<init>(r4)
            r7.<init>(r8, r0)
            r0 = r7
        Lbc:
            r5.<init>(r6, r0)
            r0 = r5
        Lc0:
            int r3 = r3 + 1
            goto L2b
        Lc4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1002Ga.a(com.android.tools.r8.graph.d2):com.android.tools.r8.internal.Ca");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.internal.Ca.<init>(com.android.tools.r8.internal.Aa, com.android.tools.r8.internal.xa, com.android.tools.r8.internal.Ca-IA):void, class status: GENERATED_AND_UNLOADED
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
    public com.android.tools.r8.internal.C0887Ca a(com.android.tools.r8.internal.C0850Ba r12, com.android.tools.r8.graph.AbstractC0727q0 r13) {
        /*
            r11 = this;
            com.android.tools.r8.internal.Ba r12 = r12.a(r13)
            com.android.tools.r8.internal.Ca r0 = com.android.tools.r8.internal.C0887Ca.c
            com.android.tools.r8.graph.x2 r1 = r13.g
            com.android.tools.r8.graph.v2[] r1 = r1.a
            int r2 = r1.length
            r3 = 0
        Lc:
            if (r3 >= r2) goto La7
            r4 = r1[r3]
            r5 = 0
            if (r4 == 0) goto L25
            com.android.tools.r8.graph.p1 r6 = r11.b
            com.android.tools.r8.graph.v2 r6 = r6.Z1
            if (r4 != r6) goto L1a
            goto L25
        L1a:
            com.android.tools.r8.graph.m r6 = r11.a
            com.android.tools.r8.graph.q0 r6 = r12.a(r6, r4)
            if (r6 != 0) goto L26
            r12.a(r4)
        L25:
            r6 = r5
        L26:
            if (r6 != 0) goto L2b
            com.android.tools.r8.internal.Ca r4 = com.android.tools.r8.internal.C0887Ca.c
            goto L2f
        L2b:
            com.android.tools.r8.internal.Ca r4 = r11.b(r12, r6)
        L2f:
            com.android.tools.r8.internal.Aa r6 = r0.a
            java.util.Set r6 = r6.a
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L43
            com.android.tools.r8.internal.xa r6 = r0.b
            boolean r6 = r6.a()
            if (r6 == 0) goto L43
            r0 = r4
            goto La3
        L43:
            com.android.tools.r8.internal.Aa r6 = r4.a
            java.util.Set r6 = r6.a
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L56
            com.android.tools.r8.internal.xa r6 = r4.b
            boolean r6 = r6.a()
            if (r6 == 0) goto L56
            goto La3
        L56:
            com.android.tools.r8.internal.Ca r6 = new com.android.tools.r8.internal.Ca
            com.android.tools.r8.internal.Aa r7 = r0.a
            com.android.tools.r8.internal.Aa r8 = r4.a
            com.android.tools.r8.internal.Aa r7 = r7.a(r8)
            com.android.tools.r8.internal.xa r0 = r0.b
            com.android.tools.r8.internal.xa r4 = r4.b
            boolean r8 = r0.a()
            if (r8 == 0) goto L6c
            r0 = r4
            goto L9f
        L6c:
            boolean r8 = r4.a()
            if (r8 == 0) goto L73
            goto L9f
        L73:
            com.android.tools.r8.internal.xa r8 = new com.android.tools.r8.internal.xa
            com.android.tools.r8.internal.Aa r9 = r0.a
            com.android.tools.r8.internal.Aa r10 = r4.a
            com.android.tools.r8.internal.Aa r9 = r9.a(r10)
            com.android.tools.r8.internal.ya r0 = r0.b
            com.android.tools.r8.internal.ya r4 = r4.b
            r0.getClass()
            com.android.tools.r8.internal.Bm r10 = com.android.tools.r8.internal.AbstractC1048Hm.g()
            java.util.Set r0 = r0.a
            r10.a(r0)
            java.util.Set r0 = r4.a
            r10.a(r0)
            com.android.tools.r8.internal.ya r0 = new com.android.tools.r8.internal.ya
            com.android.tools.r8.internal.Hm r4 = r10.a()
            r0.<init>(r4)
            r8.<init>(r9, r0)
            r0 = r8
        L9f:
            r6.<init>(r7, r0)
            r0 = r6
        La3:
            int r3 = r3 + 1
            goto Lc
        La7:
            com.android.tools.r8.internal.Zr r12 = r11.c
            com.android.tools.r8.graph.v2 r1 = r13.d
            boolean r12 = r12.e(r1)
            if (r12 == 0) goto Lb6
            com.android.tools.r8.internal.Ca r12 = r11.a(r13, r0)
            goto Lba
        Lb6:
            com.android.tools.r8.internal.Ca r12 = r11.b(r13, r0)
        Lba:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1002Ga.a(com.android.tools.r8.internal.Ba, com.android.tools.r8.graph.q0):com.android.tools.r8.internal.Ca");
    }
}

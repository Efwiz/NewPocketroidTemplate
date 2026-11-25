package com.android.tools.r8.graph;

import com.android.tools.r8.shaking.C3733i;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.r3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0738r3 {
    static final /* synthetic */ boolean c = true;
    private final Map a;
    private final Map b;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.r3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final Map a;
        private final Set b;
        static final /* synthetic */ boolean d = true;
        private static final a c = new a(Collections.emptyMap(), Collections.emptySet());

        private a(Map map, Set set) {
            this.a = map;
            this.b = set;
        }

        public static a a() {
            return c;
        }

        public final boolean b(String str) {
            return this.b.contains(str);
        }

        public final C0706m3.e a(String str) {
            if (d || !this.b.contains(str)) {
                return (C0706m3.e) this.a.get(str);
            }
            throw new AssertionError();
        }

        public a a(final Collection<String> collection) {
            if (collection.isEmpty()) {
                return this;
            }
            HashSet hashSet = new HashSet();
            hashSet.addAll(this.b);
            hashSet.addAll(collection);
            final HashMap map = new HashMap();
            this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.r3$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C0738r3.a.a(collection, map, (String) obj, (C0706m3.e) obj2);
                }
            });
            return new a(map, hashSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Collection collection, HashMap map, String str, C0706m3.e eVar) {
            if (collection.contains(str)) {
                return;
            }
            map.put(str, eVar);
        }

        public a a(final Map<String, C0706m3.e> map) {
            if (map.isEmpty()) {
                return this;
            }
            HashMap map2 = new HashMap();
            map2.putAll(this.a);
            map2.putAll(map);
            final HashSet hashSet = new HashSet();
            this.b.forEach(new Consumer() { // from class: com.android.tools.r8.graph.r3$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0738r3.a.a(map, hashSet, (String) obj);
                }
            });
            return new a(map2, hashSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Map map, HashSet hashSet, String str) {
            if (map.containsKey(str)) {
                return;
            }
            hashSet.add(str);
        }
    }

    private C0738r3(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2) {
        this.a = identityHashMap;
        this.b = identityHashMap2;
    }

    public static C0738r3 a(C0702m<?> c0702m) {
        return a(c0702m, c0702m.f().d());
    }

    public static boolean b(C0702m c0702m, C0767v2 c0767v2, Predicate predicate) {
        if (predicate.test(c0767v2)) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (abstractC0727q0C == null || abstractC0727q0C.I1()) {
            return true;
        }
        abstractC0727q0C.a1().getClass();
        return !abstractC0727q0C.a1().a.isEmpty();
    }

    public static C0738r3 a(C0702m c0702m, Collection collection) {
        if (!c0702m.Y().a1()) {
            return null;
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.graph.r3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0738r3.a(identityHashMap, identityHashMap2, (C0729q2) obj);
            }
        });
        return new C0738r3(identityHashMap, identityHashMap2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final Map map, Map map2, C0729q2 c0729q2) {
        AbstractC0740s abstractC0740sA;
        if (c0729q2.a1().e()) {
            map.put(c0729q2.getReference(), C0745s3.a(c0729q2.r.a));
            c0729q2.l(new Consumer() { // from class: com.android.tools.r8.graph.r3$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0738r3.a(map, (o5) obj);
                }
            });
        }
        J3 j3D1 = c0729q2.d1();
        if (j3D1 != null) {
            map2.put(c0729q2.getType(), j3D1.d());
        }
        O2 o2B1 = c0729q2.b1();
        if (o2B1 != null) {
            C0767v2 type = c0729q2.getType();
            if (o2B1.b() != null) {
                abstractC0740sA = o2B1.b();
            } else {
                abstractC0740sA = o2B1.a();
            }
            map2.put(type, abstractC0740sA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, o5 o5Var) {
        C0706m3.g gVarE1 = o5Var.e().e1();
        gVarE1.getClass();
        map.put(o5Var.getReference(), C0745s3.a(gVarE1.a));
    }

    public static C0738r3 a(final C0702m c0702m, C0729q2 c0729q2) {
        return a(c0702m, com.android.tools.r8.utils.O3.h(c0729q2).a(new BiConsumer() { // from class: com.android.tools.r8.graph.r3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0738r3.a(c0702m, (C0729q2) obj, (com.android.tools.r8.utils.O3) obj2);
            }
        }).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, C0729q2 c0729q2, com.android.tools.r8.utils.O3 o3) {
        AbstractC0727q0 abstractC0727q0C;
        if (c0729q2.b1() != null) {
            abstractC0727q0C = c0702m.c(c0729q2.b1().c());
        } else {
            abstractC0727q0C = c0729q2.d1() != null ? c0702m.c(c0729q2.d1().d()) : null;
        }
        if (abstractC0727q0C == null || !abstractC0727q0C.e0()) {
            return;
        }
        o3.c(abstractC0727q0C.a0());
    }

    public final a a(C0702m c0702m, C0767v2 c0767v2, Predicate predicate) {
        if (!c) {
            c0767v2.getClass();
            if (predicate.test(c0767v2)) {
                throw new AssertionError("Building context for pruned type");
            }
        }
        return a(c0702m, (AbstractC0744s2) c0767v2, predicate, false);
    }

    private a a(C0702m c0702m, AbstractC0744s2 abstractC0744s2, Predicate predicate, boolean z) {
        if (abstractC0744s2 == null) {
            return a.a();
        }
        C0767v2 c0767v2C = abstractC0744s2.C();
        AbstractC0727q0 abstractC0727q0D = c0702m.f().d(c0767v2C);
        boolean z2 = true;
        boolean z3 = z | (abstractC0727q0D == null && c0702m.f().i() && !((C3733i) c0702m.i0().f()).q().a(c0767v2C));
        C0745s3 c0745s3 = (C0745s3) this.a.get(c0767v2C);
        boolean z4 = z3 | ((abstractC0727q0D == null || c0745s3 == null || c0745s3.a.isEmpty() || !abstractC0727q0D.a1().a.isEmpty()) ? false : true);
        AbstractC0744s2 abstractC0744s3 = (AbstractC0744s2) this.b.get(c0767v2C);
        a aVarA = a(c0702m, abstractC0744s3, predicate, z4 || a(c0702m, abstractC0744s3, c0767v2C, predicate));
        aVarA.getClass();
        if (c0745s3 != null) {
            if (z4) {
                aVarA = aVarA.a(c0745s3.a);
            } else {
                aVarA = aVarA.a(c0745s3.a.keySet());
            }
        }
        if (!abstractC0744s2.y0()) {
            return aVarA;
        }
        C0745s3 c0745s4 = (C0745s3) this.a.get(abstractC0744s2);
        if (abstractC0727q0D == null || z4) {
            z2 = z4;
        } else {
            W0 w0B = abstractC0727q0D.k.b(abstractC0744s2.v0());
            if (w0B != null && (c0745s4.a.isEmpty() || !w0B.e1().a.isEmpty())) {
                z2 = false;
            }
        }
        aVarA.getClass();
        if (c0745s4 == null) {
            return aVarA;
        }
        if (z2) {
            return aVarA.a(c0745s4.a);
        }
        return aVarA.a(c0745s4.a.keySet());
    }

    public static boolean a(C0702m c0702m, AbstractC0744s2 abstractC0744s2, C0767v2 c0767v2, Predicate predicate) {
        if (!c && c0767v2 == null) {
            throw new AssertionError();
        }
        if (abstractC0744s2 == null || predicate.test(abstractC0744s2.C()) || predicate.test(c0767v2)) {
            return true;
        }
        AbstractC0727q0 abstractC0727q0D = c0702m.f().d(abstractC0744s2.C());
        AbstractC0727q0 abstractC0727q0D2 = c0702m.f().d(c0767v2);
        if (abstractC0727q0D == null || abstractC0727q0D2 == null) {
            return true;
        }
        if (abstractC0727q0D2.b1() != null) {
            if (abstractC0744s2.y0()) {
                if (abstractC0727q0D2.b1().b() != abstractC0744s2) {
                    return true;
                }
            } else if (abstractC0727q0D2.b1().a() != abstractC0744s2) {
                return true;
            }
            return false;
        }
        J3 j3D1 = abstractC0727q0D2.d1();
        return j3D1 == null || j3D1.d() != abstractC0744s2;
    }
}

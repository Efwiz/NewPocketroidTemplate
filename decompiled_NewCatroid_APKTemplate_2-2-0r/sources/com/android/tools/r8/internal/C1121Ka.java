package com.android.tools.r8.internal;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ka, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1121Ka {
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    private final Map a;
    private final Map b;

    private C1121Ka(IdentityHashMap identityHashMap, Map map) {
        this.a = identityHashMap;
        this.b = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, Map map, C0767v2 c0767v2, FeatureSplit featureSplit) {
        C0767v2 c0767v2A = AbstractC2964s2.a(abstractC1476Wj, c0767v2);
        if (c0767v2A.R0()) {
            return;
        }
        FeatureSplit featureSplit2 = (FeatureSplit) map.put(c0767v2A, featureSplit);
        if (!c && featureSplit2 != null && featureSplit2 != featureSplit) {
            throw new AssertionError();
        }
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    public static C1121Ka a() {
        return new C1121Ka(new IdentityHashMap(), null);
    }

    public static C1121Ka a(C0722p1 c0722p1, C3451yh c3451yh, com.android.tools.r8.utils.O2 o2) {
        if (c3451yh == null) {
            return a();
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        IdentityHashMap identityHashMap2 = new IdentityHashMap();
        for (FeatureSplit featureSplit : c3451yh.b()) {
            Iterator<ProgramResourceProvider> it = featureSplit.getProgramResourceProviders().iterator();
            String str = null;
            while (it.hasNext()) {
                try {
                    Iterator<ProgramResource> it2 = it.next().getProgramResources().iterator();
                    while (it2.hasNext()) {
                        for (String str2 : it2.next().getClassDescriptors()) {
                            identityHashMap.put(c0722p1.e(str2), featureSplit);
                            if (str == null || str2.compareTo(str) > 0) {
                                str = str2;
                            }
                        }
                    }
                } catch (ResourceException e) {
                    throw o2.b(e.getMessage());
                }
            }
            if (str != null) {
                identityHashMap2.put(featureSplit, str);
            }
        }
        return new C1121Ka(identityHashMap, identityHashMap2);
    }

    public final int a(FeatureSplit featureSplit, FeatureSplit featureSplit2) {
        boolean z = c;
        if (!z && featureSplit == null) {
            throw new AssertionError();
        }
        if (!z && featureSplit2 == null) {
            throw new AssertionError();
        }
        if (featureSplit == featureSplit2) {
            return 0;
        }
        if (featureSplit.isBase()) {
            return 1;
        }
        if (featureSplit2.isBase()) {
            return -1;
        }
        return ((String) this.b.get(featureSplit)).compareTo((String) this.b.get(featureSplit2));
    }

    public final IdentityHashMap a(Set set, com.android.tools.r8.utils.A1 a1, AbstractC3135uW abstractC3135uW, com.android.tools.r8.synthesis.D d2) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            FeatureSplit featureSplitA = a(c0729q2.getType(), a1, abstractC3135uW, d2);
            if (featureSplitA != null && !featureSplitA.isBase()) {
                ((Set) identityHashMap.computeIfAbsent(featureSplitA, new Function() { // from class: com.android.tools.r8.internal.Ka$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C1121Ka.a((FeatureSplit) obj);
                    }
                })).add(c0729q2);
            }
        }
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(FeatureSplit featureSplit) {
        return OU.c();
    }

    public final FeatureSplit a(C0767v2 c0767v2, com.android.tools.r8.utils.A1 a1, AbstractC3135uW abstractC3135uW, com.android.tools.r8.synthesis.D d2) {
        FeatureSplit featureSplitA;
        if (d2 == null) {
            if (c || abstractC3135uW.c()) {
                return (FeatureSplit) this.a.getOrDefault(c0767v2, FeatureSplit.BASE);
            }
            throw new AssertionError();
        }
        if (d2.i(c0767v2)) {
            if (d2.a(c0767v2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ka$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.j;
                }
            })) {
                if (!abstractC3135uW.c() && !a1.G0().d()) {
                    return FeatureSplit.BASE_STARTUP;
                }
                return FeatureSplit.BASE;
            }
            featureSplitA = d2.a(c0767v2, FeatureSplit.BASE);
            if (!c && this.a.getOrDefault(c0767v2, featureSplitA) != featureSplitA) {
                throw new AssertionError();
            }
        } else {
            featureSplitA = (FeatureSplit) this.a.getOrDefault(c0767v2, FeatureSplit.BASE);
        }
        if (!featureSplitA.isBase()) {
            return featureSplitA;
        }
        if (abstractC3135uW.b(c0767v2) && !a1.G0().d()) {
            return FeatureSplit.BASE_STARTUP;
        }
        return FeatureSplit.BASE;
    }

    public final C1121Ka a(final AbstractC1476Wj abstractC1476Wj) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ka$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1121Ka.a(abstractC1476Wj, identityHashMap, (C0767v2) obj, (FeatureSplit) obj2);
            }
        });
        return new C1121Ka(identityHashMap, this.b);
    }

    public final C1121Ka a(final com.android.tools.r8.graph.u5 u5Var) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ka$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1121Ka.a(u5Var, identityHashMap, (C0767v2) obj, (FeatureSplit) obj2);
            }
        });
        return new C1121Ka(identityHashMap, this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.u5 u5Var, Map map, C0767v2 c0767v2, FeatureSplit featureSplit) {
        if (u5Var.e().contains(c0767v2)) {
            return;
        }
        map.put(c0767v2, featureSplit);
    }

    public static boolean a(C0702m c0702m, C0729q2 c0729q2) {
        C1121Ka c1121KaP = ((C0680j) c0702m.f()).p();
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        AbstractC3135uW abstractC3135uWC = c0702m.C();
        com.android.tools.r8.synthesis.D D = c0702m.D();
        c1121KaP.getClass();
        return !c1121KaP.a(c0729q2.C(), a1Y, abstractC3135uWC, D).isBase();
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0564k$$ExternalSyntheticLambda22;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1324Rk {
    static final /* synthetic */ boolean d = true;
    private final C0680j a;
    private final IdentityHashMap b = new IdentityHashMap();
    private final Set c = OU.c();

    public C1324Rk(C0680j c0680j) {
        this.a = c0680j;
    }

    public final void a(final C1297Qk c1297Qk, final C0680j c0680j, C2522mD c2522mD, BiConsumer biConsumer) {
        Map<C0767v2, C0767v2> mapI = c1297Qk.i();
        final Set<C0684j2> setG = c1297Qk.g();
        a(c0680j, mapI);
        for (C0767v2 c0767v2 : mapI.keySet()) {
            AbstractC0727q0 abstractC0727q0A = c0680j.a(c0767v2);
            if (abstractC0727q0A == null) {
                this.c.add(c0767v2);
            } else {
                final IdentityHashMap identityHashMap = new IdentityHashMap();
                abstractC0727q0A.b(new Consumer() { // from class: com.android.tools.r8.internal.Rk$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(identityHashMap, c1297Qk, c0680j, (AbstractC0748t0) obj);
                    }
                }, new Predicate() { // from class: com.android.tools.r8.internal.Rk$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C1324Rk.a(setG, (com.android.tools.r8.graph.W0) obj);
                    }
                });
                c2522mD.a(c0767v2, new C2556mg(mapI.get(c0767v2), identityHashMap));
            }
        }
        biConsumer.accept("Missing emulated interfaces: ", this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, com.android.tools.r8.graph.W0 w0) {
        return w0.w1() && !set.contains(w0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, C1297Qk c1297Qk, C0680j c0680j, AbstractC0748t0 abstractC0748t0) {
        AbstractC0727q0 abstractC0727q0C;
        C0684j2 reference = abstractC0748t0.getReference();
        C0684j2 reference2 = abstractC0748t0.getReference();
        C2630ne c2630ne = new C2630ne(reference2, EnumC2687oD.COMPANION_CLASS);
        EnumC2687oD enumC2687oD = null;
        C2630ne c2630ne2 = new C2630ne(c0680j.a().a(c1297Qk.i().get(reference2.B0()), reference2.H0(), reference2.C0()), null);
        C2630ne c2630ne3 = new C2630ne(reference2, EnumC2687oD.EMULATED_INTERFACE_CLASS);
        List list = (List) this.b.get(reference2.B0());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Map mapN = c1297Qk.n();
        for (C0684j2 c0684j2 : mapN.keySet()) {
            if (c0684j2.b(reference2) && (abstractC0727q0C = this.a.c(c0684j2.B0())) != null) {
                C0767v2 c0767v2B0 = reference2.B0();
                com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) Arrays.asList(abstractC0727q0C.g.a));
                while (o3D.c()) {
                    C0767v2 c0767v2 = (C0767v2) o3D.f();
                    if (c0767v2B0 != c0767v2) {
                        AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0767v2);
                        if (abstractC0727q0C2 != null) {
                            C0767v2[] c0767v2Arr = abstractC0727q0C2.g.a;
                            int length = c0767v2Arr.length;
                            int i = 0;
                            while (i < length) {
                                o3D.c(c0767v2Arr[i]);
                                i++;
                                mapN = mapN;
                            }
                            enumC2687oD = null;
                        }
                    } else {
                        C0722p1 c0722p1A = this.a.a();
                        c0722p1A.getClass();
                        linkedHashMap.put(c0684j2.B0(), new C2630ne(this.a.a().a((C0767v2) mapN.get(c0684j2), c0722p1A.a(c0684j2.h, c0684j2.e), c0684j2.C0()), enumC2687oD));
                        break;
                    }
                }
            }
        }
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                AbstractC0727q0 abstractC0727q0C3 = this.a.c((C0767v2) list.get(size));
                boolean z = d;
                if (!z && abstractC0727q0C3 == null) {
                    throw new AssertionError();
                }
                com.android.tools.r8.graph.W0 w0D = abstractC0727q0C3.d(reference2);
                if (w0D != null && !w0D.s1()) {
                    if (!z && !w0D.w1()) {
                        throw new AssertionError();
                    }
                    linkedHashMap.put(abstractC0727q0C3.d, new C2630ne(w0D.getReference(), EnumC2687oD.COMPANION_CLASS));
                }
            }
        } else if (!d && linkedHashMap.size() > 1) {
            throw new AssertionError();
        }
        map.put(reference, new C2404kg(c2630ne2, c2630ne3, c2630ne, linkedHashMap));
    }

    private void a(C0680j c0680j, Map map) {
        Set setC = OU.c();
        ArrayList arrayList = new ArrayList(map.keySet());
        arrayList.sort(new C0564k$$ExternalSyntheticLambda22());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a(c0680j, map, (C0767v2) it.next(), setC);
        }
    }

    private void a(C0680j c0680j, Map map, C0767v2 c0767v2, Set set) {
        if (set.contains(c0767v2)) {
            return;
        }
        this.b.put(c0767v2, new ArrayList());
        set.add(c0767v2);
        AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2);
        if (abstractC0727q0C == null) {
            return;
        }
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) Arrays.asList(abstractC0727q0C.g.a));
        while (o3D.c()) {
            C0767v2 c0767v3 = (C0767v2) o3D.f();
            if (map.containsKey(c0767v3)) {
                a(c0680j, map, c0767v3, set);
                ((List) this.b.get(c0767v3)).add(c0767v2);
                AbstractC0727q0 abstractC0727q0C2 = c0680j.c(c0767v3);
                if (abstractC0727q0C2 != null) {
                    for (C0767v2 c0767v4 : abstractC0727q0C2.g.a) {
                        o3D.c(c0767v4);
                    }
                }
            }
        }
    }
}

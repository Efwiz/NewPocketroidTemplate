package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.C3888e1;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2709oX {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final QX b;
    private final C2409kl c;

    private C2709oX(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.c = c2409kl;
        this.b = new QX(c0702m.a());
    }

    public static void a(C0702m c0702m, C2409kl c2409kl) {
        Map mapEmptyMap;
        C2709oX c2709oX = new C2709oX(c0702m, c2409kl);
        C2928rX c2928rX = new C2928rX(c2709oX.b);
        C0868Bs c0868Bs = new C0868Bs(c0702m, C2855qX.e(), c2409kl, c2928rX, C0842As.b());
        if (c0868Bs.f(c2409kl.j()).a() == null) {
            mapEmptyMap = Collections.emptyMap();
        } else {
            com.android.tools.r8.utils.D3 d3A = new C2469lX(c2709oX, c0868Bs, c2928rX).a(c2409kl.j());
            mapEmptyMap = d3A.f() ? Collections.emptyMap() : ((C2618nX) d3A.c().i()).a;
        }
        IdentityHashMap identityHashMapA = c2709oX.a(mapEmptyMap);
        if (identityHashMapA.isEmpty()) {
            return;
        }
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            InterfaceC2390kX interfaceC2390kX = (InterfaceC2390kX) identityHashMapA.get(abstractC2412ko);
            if (interfaceC2390kX != null) {
                interfaceC2390kX.a(c2709oX.a, c2709oX.c, c2561ml, abstractC2412ko, c2709oX.b);
            }
        }
        c2709oX.c.y();
    }

    private IdentityHashMap a(Map map) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        final Set setC = OU.c();
        final Set setC2 = OU.c();
        final IdentityHashMap identityHashMap3 = new IdentityHashMap();
        final Set setC3 = OU.c();
        final IdentityHashMap identityHashMap4 = new IdentityHashMap();
        final IdentityHashMap identityHashMap5 = new IdentityHashMap();
        IdentityHashMap identityHashMap6 = identityHashMap4;
        IdentityHashMap identityHashMap7 = identityHashMap5;
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.oX$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2709oX.a(identityHashMap3, identityHashMap4, identityHashMap2, identityHashMap5, setC2, setC, setC3, (S10) obj, (IX) obj2);
            }
        });
        NX nx = new NX(identityHashMap, setC3, setC, setC2, identityHashMap3, identityHashMap2, this.b, new Supplier() { // from class: com.android.tools.r8.internal.oX$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a();
            }
        });
        boolean z = true;
        int i = 0;
        while (i < 3 && z) {
            LinkedHashSet<IX> linkedHashSet = new LinkedHashSet();
            IdentityHashMap identityHashMap8 = identityHashMap6;
            IdentityHashMap identityHashMap9 = identityHashMap7;
            new C2543mX(identityHashMap9, identityHashMap8, linkedHashSet).b(map.values());
            boolean zA = false;
            for (IX ix : linkedHashSet) {
                com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(ix);
                while (o3H.c()) {
                    IX ix2 = (IX) o3H.f();
                    zA |= PX.a(ix, ix2, nx);
                    ix2.q().forEach(new oX$$ExternalSyntheticLambda0(o3H));
                }
            }
            i++;
            z = zA;
            identityHashMap6 = identityHashMap8;
            identityHashMap7 = identityHashMap9;
        }
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, Map map2, Map map3, Map map4, Set set, Set set2, Set set3, S10 s10, IX ix) {
        C3363xX c3363xXF;
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(ix);
        Set setC = OU.c();
        map.put(ix, setC);
        while (o3H.c()) {
            IX ix2 = (IX) o3H.f();
            map2.put(ix2, ix);
            ix2.getClass();
            if (ix2 instanceof DX) {
                IX ix3 = (IX) map3.put(ix, ix2.k());
                if (!d && ix3 != null) {
                    throw new AssertionError();
                }
            }
            if (ix2.v() && (c3363xXF = ix2.j().f()) != null) {
                ((Set) map4.computeIfAbsent(ix, C3888e1.a(new NG$$ExternalSyntheticLambda0()))).add(c3363xXF);
            }
            if (ix2 instanceof BX) {
                set.add(ix);
            }
            if (ix2 instanceof C3294wX) {
                set2.add(ix);
                set3.add(ix);
            }
            if ((ix2 instanceof HX) || (ix2 instanceof C3363xX)) {
                setC.add(ix2);
            }
            if (ix2 instanceof AX) {
                set2.add(ix);
            }
            ix2.q().forEach(new oX$$ExternalSyntheticLambda0(o3H));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public S10 a() {
        C2409kl c2409kl = this.c;
        C0702m c0702m = this.a;
        int i = AbstractC3472z00.b;
        return c2409kl.a((AbstractC3472z00) AbstractC3472z00.a(c0702m.a().X1, WH.i(), (C0702m<?>) c0702m).b());
    }
}

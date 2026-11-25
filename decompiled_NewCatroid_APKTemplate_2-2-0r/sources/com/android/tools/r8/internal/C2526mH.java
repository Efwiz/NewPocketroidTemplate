package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2526mH extends SF {
    static final /* synthetic */ boolean c = true;
    private final C0702m b;

    public C2526mH(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!c && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        C2688oE c2688oE2;
        com.android.tools.r8.graph.o5 o5Var;
        com.android.tools.r8.graph.o5 o5Var2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            final HashMap map = new HashMap();
            c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.mH$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2526mH.a(map, (com.android.tools.r8.graph.o5) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.internal.mH$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((com.android.tools.r8.graph.W0) obj).G1();
                }
            });
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    c2688oE2 = null;
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                c2688oE2 = (C2688oE) entry.getKey();
                Map map2 = (Map) entry.getValue();
                Iterator it3 = map.values().iterator();
                do {
                    if (!it3.hasNext()) {
                        Iterator it4 = map2.values().iterator();
                        do {
                            if (!it4.hasNext()) {
                                map2.putAll(map);
                                break;
                            }
                            o5Var = (com.android.tools.r8.graph.o5) it4.next();
                            com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) this.b.f()).a(c0729q2.n1(), o5Var.getReference()).o();
                            if (cVarO != null && !cVarO.r().s1()) {
                                break;
                            }
                        } while (!a(c0729q2.f1(), o5Var));
                    } else {
                        o5Var2 = (com.android.tools.r8.graph.o5) it3.next();
                        if (((com.android.tools.r8.graph.o5) map2.get(o5Var2.z())) == null) {
                        }
                    }
                } while (!a(c2688oE2, o5Var2));
            }
            if (c2688oE2 != null) {
                c2688oE2.a(c0729q2);
            } else {
                C2688oE c2688oE3 = new C2688oE();
                c2688oE3.a(c0729q2);
                linkedHashMap.put(c2688oE3, map);
            }
        }
        return WL.a(linkedHashMap.keySet());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoVirtualMethodMerging";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.o5 o5Var) {
        map.put(o5Var.z(), o5Var);
    }

    private boolean a(C2688oE c2688oE, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) this.b.f()).a(c2688oE.e(), o5Var.getReference()).o();
        return !(cVarO == null || cVarO.r().s1()) || a(com.android.tools.r8.utils.T2.a(com.android.tools.r8.utils.I1.a(c2688oE, new IK$$ExternalSyntheticLambda1())), o5Var);
    }

    private boolean a(Iterable iterable, final com.android.tools.r8.graph.o5 o5Var) {
        return AbstractC2734ot.b(iterable, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.mH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a(o5Var, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2) {
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) this.b.f()).b(c0767v2, o5Var.getReference()).o();
        return (cVarO == null || cVarO.r().s1()) ? false : true;
    }
}

package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BU extends SF {
    static final /* synthetic */ boolean c = true;
    private final C0702m b;

    public BU(C0702m c0702m) {
        this.b = c0702m;
    }

    private static void a(C2688oE c2688oE, final LinkedHashMap linkedHashMap) {
        c2688oE.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.BU$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BU.a(linkedHashMap, (C0729q2) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SamePackageForApiOutline";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, C0729q2 c0729q2) {
        C2688oE c2688oE = (C2688oE) map.get(c0729q2.d.H0());
        if (c2688oE == null) {
            return false;
        }
        c2688oE.a(c0729q2);
        return true;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        C2688oE c2688oE2 = new C2688oE();
        com.android.tools.r8.synthesis.D D = this.b.D();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (!c && !D.i(c0729q2.getType())) {
                throw new AssertionError();
            }
            if (AbstractC2734ot.b(D.e(c0729q2.getType()), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.BU$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return BU.a((com.android.tools.r8.synthesis.L.b) obj);
                }
            })) {
                ((C2688oE) linkedHashMap.computeIfAbsent(c0729q2.getType().H0(), C3888e1.a(new BU$$ExternalSyntheticLambda2()))).a(c0729q2);
            } else {
                c2688oE2.a(c0729q2);
            }
        }
        a(c2688oE2, linkedHashMap);
        WL.a(linkedHashMap.values());
        ArrayList arrayList = new ArrayList(linkedHashMap.size() + 1);
        if (c2688oE2.size() > 1) {
            arrayList.add(c2688oE2);
        }
        arrayList.addAll(linkedHashMap.values());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(com.android.tools.r8.synthesis.L.b bVar) {
        bVar.getClass();
        return ((bVar instanceof com.android.tools.r8.synthesis.O) && bVar.a().j()) ? false : true;
    }
}

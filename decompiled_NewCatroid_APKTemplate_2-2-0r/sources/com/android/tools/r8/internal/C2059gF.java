package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2059gF extends SF {
    static final /* synthetic */ boolean b = true;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Map map, C0729q2 c0729q2) {
        a(map, c0729q2);
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.gF$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(linkedHashMap, (C0729q2) obj);
            }
        });
        final C2688oE c2688oE2 = new C2688oE();
        linkedHashMap.values().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.gF$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2059gF.a(c2688oE2, (C2688oE) obj);
            }
        });
        if (c2688oE2.isEmpty()) {
            return linkedHashMap.values();
        }
        if (!c2688oE2.m()) {
            ArrayList arrayList = new ArrayList(linkedHashMap.values());
            arrayList.add(c2688oE2);
            return arrayList;
        }
        C2688oE c2688oE3 = null;
        for (C2688oE c2688oE4 : linkedHashMap.values()) {
            if (c2688oE3 == null || c2688oE4.size() < c2688oE3.size()) {
                c2688oE3 = c2688oE4;
            }
        }
        if (!b && c2688oE3 == null) {
            throw new AssertionError();
        }
        c2688oE3.a(c2688oE2);
        return linkedHashMap.values();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "MinimizeFieldCasts";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2688oE c2688oE, C2688oE c2688oE2) {
        if (!c2688oE2.m()) {
            return false;
        }
        c2688oE.a(c2688oE2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2688oE a(InterfaceC1600aG interfaceC1600aG) {
        return new C2688oE();
    }

    private static void a(Map map, C0729q2 c0729q2) {
        C3454yk c3454ykF = C3454yk.f();
        Iterator<com.android.tools.r8.graph.T0> it = c0729q2.w1().iterator();
        while (it.hasNext()) {
            c3454ykF.add(it.next().getType());
        }
        ((C2688oE) map.computeIfAbsent(c3454ykF, new Function() { // from class: com.android.tools.r8.internal.gF$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2059gF.a((InterfaceC1600aG) obj);
            }
        })).a(c0729q2);
    }
}

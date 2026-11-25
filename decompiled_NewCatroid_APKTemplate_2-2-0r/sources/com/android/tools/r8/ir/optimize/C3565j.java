package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.utils.G3;
import com.android.tools.r8.utils.H3;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3565j {
    static final /* synthetic */ boolean b = true;
    Map a;

    public C3565j(LinkedHashMap linkedHashMap) {
        this.a = linkedHashMap;
    }

    final void a(G3 g3) {
        Iterator it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) entry.getKey();
            Map map = (Map) entry.getValue();
            Iterator it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                final S10 s10 = (S10) entry2.getKey();
                C3561h c3561h = (C3561h) entry2.getValue();
                AbstractC3559g abstractC3559g = c3561h.a;
                abstractC3559g.getClass();
                if (abstractC3559g instanceof C3567k) {
                    if (!b && !s10.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.j$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C3565j.a(s10, (AbstractC2412ko) obj);
                        }
                    })) {
                        throw new AssertionError();
                    }
                } else {
                    if (!b && !(abstractC3559g instanceof C3575o)) {
                        throw new AssertionError();
                    }
                    AbstractC3559g abstractC3559g2 = (AbstractC3559g) g3.a(abstractC2412ko, s10, c3561h);
                    abstractC3559g2.getClass();
                    if ((!(abstractC3559g2 instanceof C3571m) || s10.H()) && !(abstractC3559g2 instanceof C3575o)) {
                        c3561h.a = abstractC3559g2;
                    } else {
                        it2.remove();
                    }
                }
            }
            if (map.isEmpty()) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(S10 s10, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.b() == s10;
    }

    final void a(IdentityHashMap identityHashMap) {
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.j$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((AbstractC2412ko) obj, (Map) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2412ko abstractC2412ko, Map map) {
        final Map map2 = (Map) this.a.get(abstractC2412ko);
        if (map2 != null) {
            map.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.j$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    map2.remove((S10) obj);
                }
            });
            if (map2.isEmpty()) {
                this.a.remove(abstractC2412ko);
            }
        }
    }

    final void a(H3 h3) {
        Iterator it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) entry.getKey();
            Map map = (Map) entry.getValue();
            Iterator it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                if (h3.a(abstractC2412ko, (S10) entry2.getKey(), (C3561h) entry2.getValue())) {
                    it2.remove();
                }
            }
            if (map.isEmpty()) {
                it.remove();
            }
        }
    }
}

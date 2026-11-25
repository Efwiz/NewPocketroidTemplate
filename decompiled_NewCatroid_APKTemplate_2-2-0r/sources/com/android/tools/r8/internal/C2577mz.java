package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3970u0;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2577mz implements InterfaceC3680a0 {
    private static final List c = AbstractC1639am.i();
    private final C2973sA a;
    private final Map b;

    private C2577mz(C2973sA c2973sA, Map map) {
        this.a = c2973sA;
        this.b = map;
    }

    static C2577mz a(C3321wx c3321wx, C0722p1 c0722p1) {
        return new C2577mz(C2973sA.a(c3321wx.b(), c0722p1, c3321wx.b()), AbstractC2502lz.a(c3321wx.a(), c0722p1));
    }

    static AbstractC1639am a(C0722p1 c0722p1, List list) {
        if (list.isEmpty()) {
            return (AbstractC1639am) c;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c1507XlG.a(a((C3321wx) it.next(), c0722p1));
        }
        return c1507XlG.a();
    }

    final boolean a(final Consumer consumer, final C0702m c0702m) {
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(false);
        k.b(this.a.b(new Consumer() { // from class: com.android.tools.r8.internal.mz$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(k, c0702m, consumer, (String) obj);
            }
        }, c0702m, (String) null));
        return k.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final com.android.tools.r8.utils.K k, final C0702m c0702m, Consumer consumer, String str) {
        if (str == null) {
            k.c(true);
            return;
        }
        String strC = C3970u0.c(str);
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.mz$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2577mz.a(k, linkedHashMap, c0702m, (String) obj, (AbstractC2502lz) obj2);
            }
        });
        consumer.accept(new C3321wx(strC, linkedHashMap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.K k, final Map map, C0702m c0702m, final String str, AbstractC2502lz abstractC2502lz) {
        k.b(abstractC2502lz.a(new Consumer() { // from class: com.android.tools.r8.internal.mz$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2577mz.a(map, str, (AbstractC3467yx) obj);
            }
        }, c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, String str, AbstractC3467yx abstractC3467yx) {
        if (abstractC3467yx != null) {
            map.put(str, abstractC3467yx);
        }
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(final com.android.tools.r8.graph.Q0 q0) {
        this.a.a(q0);
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.mz$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((AbstractC2502lz) obj2).a(q0);
            }
        });
    }
}

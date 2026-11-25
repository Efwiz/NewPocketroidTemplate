package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2502lz implements InterfaceC3680a0 {
    private static final Map a = AbstractC2178hm.s();

    AbstractC2502lz() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static AbstractC2502lz a(AbstractC3467yx abstractC3467yx, C0722p1 c0722p1) {
        izIA izia = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        if (abstractC3467yx instanceof C1028Gx) {
            C1028Gx c1028Gx = (C1028Gx) abstractC3467yx;
            return new C2270iz(C2973sA.a(c1028Gx.b(), c0722p1, c1028Gx.b()), c1028Gx.a());
        }
        if (abstractC3467yx instanceof C0941Dx) {
            C0941Dx c0941Dx = (C0941Dx) abstractC3467yx;
            return new C2345jz(C2973sA.a(c0941Dx.a(), c0722p1, c0941Dx.a()), c0941Dx.b());
        }
        if (abstractC3467yx instanceof C3398xx) {
            return new C2121gz(C2577mz.a(((C3398xx) abstractC3467yx).a(), c0722p1));
        }
        return abstractC3467yx instanceof AbstractC3467yx.a ? C2192hz.a((AbstractC3467yx.a) abstractC3467yx, c0722p1) : new C2426kz(abstractC3467yx);
    }

    abstract boolean a(Consumer consumer, C0702m c0702m);

    static Map a(Map map, final C0722p1 c0722p1) {
        if (map.isEmpty()) {
            return a;
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.lz$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC2502lz.a(linkedHashMap, c0722p1, (String) obj, (AbstractC3467yx) obj2);
            }
        });
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(LinkedHashMap linkedHashMap, C0722p1 c0722p1, String str, AbstractC3467yx abstractC3467yx) {
        linkedHashMap.put(str, a(abstractC3467yx, c0722p1));
    }
}

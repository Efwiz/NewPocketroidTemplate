package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2133h6 {
    private static final C2133h6 b = new C2133h6(Collections.emptyMap());
    private final Map a;

    C2133h6(Map map) {
        this.a = map;
    }

    public static a a() {
        return new a();
    }

    public static C2133h6 b() {
        return b;
    }

    public final C1890e6 a(AbstractC2412ko abstractC2412ko) {
        return (C1890e6) this.a.get(abstractC2412ko);
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.h6$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean b = true;
        private final IdentityHashMap a = new IdentityHashMap();

        public final void a(AbstractC2412ko abstractC2412ko, Consumer consumer) {
            if (!b && this.a.containsKey(abstractC2412ko)) {
                throw new AssertionError();
            }
            consumer.accept((C1817d6) this.a.computeIfAbsent(abstractC2412ko, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.h6$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C1890e6.a();
                }
            })));
        }

        public final C2133h6 a() {
            if (this.a.isEmpty()) {
                return C2133h6.b;
            }
            final IdentityHashMap identityHashMap = new IdentityHashMap(this.a.size());
            this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.h6$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C2133h6.a.a(identityHashMap, (AbstractC2412ko) obj, (C1817d6) obj2);
                }
            });
            return new C2133h6(identityHashMap);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Map map, AbstractC2412ko abstractC2412ko, C1817d6 c1817d6) {
            map.put(abstractC2412ko, c1817d6.a());
        }
    }
}

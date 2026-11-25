package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AH extends AbstractC3274wL {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    private AH(Map map) {
        if (!b && map.isEmpty()) {
            throw new AssertionError();
        }
        this.a = map;
    }

    static AH c() {
        return new AH(AbstractC2178hm.b(C1626ae.b(), B5.l()));
    }

    @Override // com.android.tools.r8.internal.AbstractC3274wL
    final AH a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3274wL
    final AbstractC3274wL b() {
        boolean z = true;
        boolean z2 = true;
        for (AbstractC3204vL abstractC3204vL : this.a.values()) {
            abstractC3204vL.getClass();
            if (!(abstractC3204vL instanceof B5)) {
                z = false;
            }
            if (!(abstractC3204vL instanceof C2660o10)) {
                z2 = false;
            }
        }
        if (z) {
            return C5.c();
        }
        if (z2) {
            return C2748p10.c();
        }
        AbstractC3274wL abstractC3274wLA = a(new BiFunction() { // from class: com.android.tools.r8.internal.AH$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AH.a((U1) obj, (AbstractC3204vL) obj2);
            }
        });
        boolean z3 = b;
        if (!z3 && (abstractC3274wLA instanceof C5)) {
            throw new AssertionError();
        }
        if (z3 || !(abstractC3274wLA instanceof C2748p10)) {
            return abstractC3274wLA;
        }
        throw new AssertionError();
    }

    public final int d() {
        return this.a.size();
    }

    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != AH.class) {
            return false;
        }
        return this.a.equals(((AH) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    final AbstractC3274wL a(AH ah) {
        if (this instanceof C5) {
            return ah;
        }
        ah.getClass();
        if (ah instanceof C5) {
            return this;
        }
        final HashMap map = new HashMap(this.a);
        ah.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.AH$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AH.a(map, (U1) obj, (AbstractC3204vL) obj2);
            }
        });
        return map.isEmpty() ? C5.c() : new AH(map);
    }

    public final boolean a(BiPredicate biPredicate) {
        for (Map.Entry entry : this.a.entrySet()) {
            if (!biPredicate.test((U1) entry.getKey(), (AbstractC3204vL) entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC3204vL a(U1 u1, AbstractC3204vL abstractC3204vL) {
        abstractC3204vL.getClass();
        if (abstractC3204vL instanceof C2660o10) {
            return null;
        }
        return abstractC3204vL.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, U1 u1, AbstractC3204vL abstractC3204vL) {
        AbstractC3204vL abstractC3204vL2 = (AbstractC3204vL) map.getOrDefault(u1, B5.l());
        abstractC3204vL.getClass();
        if (abstractC3204vL instanceof B5) {
            abstractC3204vL = abstractC3204vL2;
        } else {
            abstractC3204vL2.getClass();
            if (!(abstractC3204vL2 instanceof B5)) {
                if (!(abstractC3204vL instanceof C2660o10) && !(abstractC3204vL2 instanceof C2660o10)) {
                    abstractC3204vL = abstractC3204vL.a().a(abstractC3204vL2.a());
                } else {
                    abstractC3204vL = C2660o10.l();
                }
            }
        }
        map.put(u1, abstractC3204vL);
    }

    @Override // com.android.tools.r8.internal.AbstractC3274wL
    public final AbstractC3204vL a(U1 u1) {
        return (AbstractC3204vL) this.a.getOrDefault(u1, C2660o10.l());
    }

    @Override // com.android.tools.r8.internal.AbstractC3274wL
    final AbstractC3274wL a(BiFunction biFunction) {
        C1946em c1946emE = null;
        for (Map.Entry entry : this.a.entrySet()) {
            U1 u1 = (U1) entry.getKey();
            AbstractC3204vL abstractC3204vL = (AbstractC3204vL) entry.getValue();
            AbstractC3204vL abstractC3204vL2 = (AbstractC3204vL) biFunction.apply(u1, abstractC3204vL);
            if (abstractC3204vL2 != null) {
                if (abstractC3204vL2 != abstractC3204vL) {
                    if (c1946emE == null) {
                        c1946emE = AbstractC2178hm.e();
                        for (Map.Entry entry2 : this.a.entrySet()) {
                            U1 u2 = (U1) entry2.getKey();
                            if (u2 == u1) {
                                break;
                            }
                            c1946emE.a(u2, (AbstractC3204vL) entry2.getValue());
                        }
                    }
                    c1946emE.a(u1, abstractC3204vL2);
                } else if (c1946emE != null) {
                    c1946emE.a(u1, abstractC3204vL2);
                }
            }
        }
        if (c1946emE == null) {
            return this;
        }
        AbstractC2178hm abstractC2178hmA = c1946emE.a();
        if (abstractC2178hmA.isEmpty()) {
            return C5.c();
        }
        return new AH(abstractC2178hmA);
    }
}

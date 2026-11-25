package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1513Xr {
    private static final C1513Xr b = new C1513Xr(AbstractC1842dR.a);
    static final /* synthetic */ boolean c = true;
    private final InterfaceC1613aR a;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Xr$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C2537mR a;

        private a() {
            this.a = new C2537mR();
        }

        public a a(C0767v2 c0767v2, final boolean z) {
            this.a.compute(c0767v2, new BiFunction() { // from class: com.android.tools.r8.internal.Xr$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C1513Xr.a.a(z, (C0767v2) obj, (Boolean) obj2);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Boolean a(boolean z, C0767v2 c0767v2, Boolean bool) {
            return Boolean.valueOf((bool == null || bool.booleanValue()) && z);
        }

        public a a(C0767v2 c0767v2) {
            return a(c0767v2, true);
        }

        public C1513Xr a() {
            if (this.a.isEmpty()) {
                return C1513Xr.b;
            }
            return new C1513Xr(this.a);
        }
    }

    private C1513Xr(InterfaceC1613aR interfaceC1613aR) {
        if (!c && interfaceC1613aR == null) {
            throw new AssertionError();
        }
        this.a = interfaceC1613aR;
    }

    public static a a() {
        return new a();
    }

    public static C1513Xr b() {
        return b;
    }

    public static C1513Xr c(C0767v2 c0767v2) {
        return new C1513Xr(AbstractC1842dR.a(c0767v2));
    }

    public C0767v2 d() {
        if (this.a.size() != 1) {
            return null;
        }
        C0767v2 c0767v2 = (C0767v2) this.a.keySet().iterator().next();
        if (this.a.b(c0767v2)) {
            return c0767v2;
        }
        return null;
    }

    public final boolean e() {
        return d() != null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1513Xr) {
            return this.a.equals(((C1513Xr) obj).a);
        }
        return false;
    }

    public final boolean f() {
        return this.a.isEmpty();
    }

    public int g() {
        return this.a.size();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public void a(BiConsumer<C0767v2, Boolean> biConsumer) {
        this.a.forEach(biConsumer);
    }

    public boolean b(C0767v2 c0767v2) {
        return a(c0767v2).d();
    }

    public final ArrayList c() {
        final ArrayList arrayList = new ArrayList(this.a.size());
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Xr$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1513Xr.a(arrayList, (C0767v2) obj, (Boolean) obj2);
            }
        });
        return arrayList;
    }

    public final void a(final Consumer consumer) {
        a(new BiConsumer() { // from class: com.android.tools.r8.internal.Xr$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1513Xr.a(consumer, (C0767v2) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C0767v2 c0767v2, Boolean bool) {
        if (bool.booleanValue()) {
            consumer.accept(c0767v2);
        }
    }

    public final boolean a(Predicate predicate) {
        InterfaceC1912eK it = this.a.h().iterator();
        while (it.hasNext()) {
            YQ yq = (YQ) it.next();
            if (yq.getBooleanValue() && !predicate.test((C0767v2) yq.getKey())) {
                return false;
            }
        }
        return true;
    }

    public boolean a(BiPredicate<C0767v2, Boolean> biPredicate) {
        InterfaceC1912eK it = this.a.h().iterator();
        while (it.hasNext()) {
            YQ yq = (YQ) it.next();
            if (biPredicate.test((C0767v2) yq.getKey(), Boolean.valueOf(yq.getBooleanValue()))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C0767v2 c0767v2, Boolean bool) {
        list.add(new C3979v2(c0767v2, bool));
    }

    public AbstractC3938n2 a(C0767v2 c0767v2) {
        AbstractC2350k1 abstractC2350k1 = (AbstractC2350k1) this.a;
        Boolean boolValueOf = abstractC2350k1.containsKey(c0767v2) ? Boolean.valueOf(abstractC2350k1.b(c0767v2)) : null;
        if (boolValueOf == null) {
            return AbstractC3938n2.b;
        }
        return boolValueOf.booleanValue() ? AbstractC3938n2.a : AbstractC3938n2.c;
    }
}

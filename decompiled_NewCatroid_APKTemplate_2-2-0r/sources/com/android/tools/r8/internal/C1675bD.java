package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1675bD extends AbstractC1596aD {
    static final /* synthetic */ boolean f = true;

    public static /* synthetic */ ConcurrentHashMap $r8$lambda$VNoJvGXwYBJoAaxs0dd0NkDC6MQ(int i) {
        return new ConcurrentHashMap(i);
    }

    private C1675bD(AbstractC1476Wj abstractC1476Wj, IntFunction intFunction, IntFunction intFunction2) {
        super(abstractC1476Wj, intFunction, intFunction2);
    }

    public static C1675bD a(AbstractC1476Wj abstractC1476Wj) {
        return new C1675bD(abstractC1476Wj, new IntFunction() { // from class: com.android.tools.r8.internal.bD$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AN.a(i);
            }
        }, new IntFunction() { // from class: com.android.tools.r8.internal.bD$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return C1675bD.$r8$lambda$VNoJvGXwYBJoAaxs0dd0NkDC6MQ(i);
            }
        });
    }

    public final Object a(final com.android.tools.r8.graph.o5 o5Var, final Function function, AbstractC1476Wj abstractC1476Wj) {
        if (f || AbstractC1596aD.e || this.c == abstractC1476Wj) {
            return ((Map) this.d).computeIfAbsent(o5Var.getReference(), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.bD$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return function.apply(o5Var);
                }
            }));
        }
        throw new AssertionError();
    }

    public final C1675bD a(final BiFunction biFunction, final AbstractC1476Wj abstractC1476Wj) {
        if (abstractC1476Wj == this.c) {
            return this;
        }
        final Map map = (Map) this.b.apply(((Map) this.d).size());
        ((Map) this.d).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.bD$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(abstractC1476Wj, biFunction, map, (C0684j2) obj, obj2);
            }
        });
        this.d = map;
        this.c = abstractC1476Wj;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, BiFunction biFunction, Map map, C0684j2 c0684j2, Object obj) {
        C0684j2 c0684j2C = abstractC1476Wj.c(this.c, c0684j2);
        Object objApply = biFunction.apply(obj, this.c);
        if (!f && map.containsKey(c0684j2C)) {
            throw new AssertionError();
        }
        map.put(c0684j2C, objApply);
    }

    public final AN a(final C0702m c0702m, final Function function) {
        if (!f) {
            AbstractC1476Wj abstractC1476WjE = c0702m.E();
            if (!AbstractC1596aD.e && this.c != abstractC1476WjE) {
                throw new AssertionError();
            }
        }
        final AN an = (AN) this.a.apply(((Map) this.d).size());
        ((Map) this.d).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.bD$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1675bD.a(c0702m, an, function, (C0684j2) obj, obj2);
            }
        });
        return an;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, AN an, Function function, C0684j2 c0684j2, Object obj) {
        com.android.tools.r8.graph.o5 o5VarA = c0684j2.a(C0729q2.a(c0702m.c(c0684j2.B0())));
        if (o5VarA != null) {
            an.a(o5VarA, function.apply(obj));
        } else if (!f) {
            throw new AssertionError();
        }
    }
}

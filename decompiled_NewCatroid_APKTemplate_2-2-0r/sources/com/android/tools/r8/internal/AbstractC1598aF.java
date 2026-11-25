package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1598aF {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    AbstractC1598aF(AbstractMap abstractMap) {
        if (!b && !abstractMap.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.aF$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((YE) obj).d();
            }
        })) {
            throw new AssertionError();
        }
        this.a = abstractMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public YE a(Function function, com.android.tools.r8.utils.y3 y3Var, C0702m c0702m, Object obj, Object obj2, YE ye) {
        if (ye == null) {
            YE ye2 = (YE) function.apply(C3480z5.k());
            if (b || !ye2.d()) {
                return ye2;
            }
            throw new AssertionError();
        }
        boolean z = b;
        if (!z && ye.d()) {
            throw new AssertionError();
        }
        y3Var.a("Join temporary method state");
        YE yeA = ye.a(c0702m, b(obj), FW.b(), function);
        if (!z && yeA.d()) {
            throw new AssertionError();
        }
        y3Var.c();
        return yeA;
    }

    abstract Object a(com.android.tools.r8.graph.o5 o5Var);

    abstract AbstractC0717o2 b(Object obj);

    public void b(com.android.tools.r8.graph.o5 o5Var, YE ye) {
        Object objA = a(o5Var);
        if (ye.d()) {
            this.a.remove(objA);
        } else {
            this.a.put(objA, ye);
        }
    }

    public void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, YE ye) {
        b(c0702m, a(o5Var), ye);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C0702m c0702m, final Object obj, final YE ye) {
        if (ye.isUnknown()) {
            this.a.put(obj, ye);
        } else {
            this.a.compute(obj, new BiFunction() { // from class: com.android.tools.r8.internal.aF$$ExternalSyntheticLambda3
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj2, Object obj3) {
                    return this.f$0.a(ye, c0702m, obj, obj2, (YE) obj3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ YE a(YE ye, C0702m c0702m, Object obj, Object obj2, YE ye2) {
        YE yeA;
        if (ye2 == null) {
            yeA = ye.a();
        } else {
            yeA = ye2.a(c0702m, b(obj), ye, FW.a());
        }
        if (b || !yeA.d()) {
            return yeA;
        }
        throw new AssertionError();
    }

    public void a(final C0702m c0702m, final C0684j2 c0684j2, final Function function, final com.android.tools.r8.utils.y3 y3Var) {
        this.a.compute(c0684j2, new BiFunction() { // from class: com.android.tools.r8.internal.aF$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(function, y3Var, c0702m, c0684j2, obj, (YE) obj2);
            }
        });
    }

    public void a(final C0702m c0702m, AbstractC1598aF abstractC1598aF) {
        abstractC1598aF.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.aF$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c0702m, obj, (YE) obj2);
            }
        });
    }

    public void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    public YE a(Object obj) {
        return (YE) this.a.getOrDefault(obj, C3480z5.k());
    }

    public boolean a() {
        return this.a.isEmpty();
    }

    public YE a(com.android.tools.r8.graph.o5 o5Var, YE ye) {
        YE ye2 = (YE) this.a.remove(a(o5Var));
        return ye2 != null ? ye2 : ye;
    }
}

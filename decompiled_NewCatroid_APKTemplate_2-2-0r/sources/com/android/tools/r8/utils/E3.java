package com.android.tools.r8.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class E3 {
    public static Object a(Function function) {
        return ((D3) function.apply(new Function() { // from class: com.android.tools.r8.utils.E3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D3.a(obj);
            }
        })).a().i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static D3 b(C3932m1 c3932m1, int i) {
        return D3.a(!(c3932m1.d() > i));
    }

    public static boolean a(final int i, Consumer consumer) {
        final C3932m1 c3932m1 = new C3932m1();
        consumer.accept(C3888e1.a(new Supplier() { // from class: com.android.tools.r8.utils.E3$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return E3.a(c3932m1, i);
            }
        }));
        return c3932m1.a() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static D3 a(C3932m1 c3932m1, int i) {
        return D3.a(!(c3932m1.d() > i));
    }

    public static boolean a(Consumer consumer) {
        final C3932m1 c3932m1 = new C3932m1();
        final int i = 2;
        consumer.accept(C3888e1.a(new Supplier() { // from class: com.android.tools.r8.utils.E3$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return E3.b(c3932m1, i);
            }
        }));
        return c3932m1.a() > 2;
    }

    public static D3 a(Collection collection, Object obj, BiFunction biFunction) {
        D3 d3B = D3.b(obj);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            d3B = (D3) biFunction.apply(it.next(), d3B.c().j());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }
}

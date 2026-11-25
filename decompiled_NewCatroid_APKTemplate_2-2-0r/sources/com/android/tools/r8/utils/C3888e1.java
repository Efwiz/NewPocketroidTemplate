package com.android.tools.r8.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.utils.e1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3888e1 {
    public static Object a(Object obj, Object obj2) {
        return obj;
    }

    public static Object a(Object obj, Object obj2, Object obj3) {
        return obj;
    }

    public static Object a(Function function, Object obj) {
        if (obj != null) {
            return function.apply(obj);
        }
        return null;
    }

    public static void a(List list, Function function, Object obj) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Consumer) function.apply(it.next())).accept(obj);
        }
    }

    public static <T, R> Function<T, R> a(final Supplier<R> supplier) {
        return new Function() { // from class: com.android.tools.r8.utils.e1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3888e1.a(supplier, obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Supplier supplier, Object obj) {
        return supplier.get();
    }
}

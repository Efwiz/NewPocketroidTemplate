package com.android.tools.r8.utils;

import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.utils.x2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3988x2 {
    public static Object a(Object[] objArr, Predicate predicate) {
        for (Object obj : objArr) {
            if (predicate.test(obj)) {
                return obj;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static <T> Predicate<T> a(final Predicate<T> predicate) {
        return new Predicate() { // from class: com.android.tools.r8.utils.x2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3988x2.a(predicate, obj);
            }
        };
    }

    public static Predicate a(final Function function) {
        return new Predicate() { // from class: com.android.tools.r8.utils.x2$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3988x2.a(function, obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Function function, Object obj) {
        return function.apply(obj) == null;
    }
}

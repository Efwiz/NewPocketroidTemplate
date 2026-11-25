package com.android.tools.r8.utils;

import java.util.function.BiPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class H {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Object obj, Object obj2) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Object obj, Object obj2) {
        return true;
    }

    public static <S, T> BiPredicate<S, T> a() {
        return new BiPredicate() { // from class: com.android.tools.r8.utils.H$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return H.a(obj, obj2);
            }
        };
    }

    public static <S, T> BiPredicate<S, T> b() {
        return new BiPredicate() { // from class: com.android.tools.r8.utils.H$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return H.b(obj, obj2);
            }
        };
    }
}
